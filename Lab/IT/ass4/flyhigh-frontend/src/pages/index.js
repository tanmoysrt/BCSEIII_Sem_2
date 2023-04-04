import Flight from "@/components/flight";
import Header from "@/components/header";
import OffersTab from "@/components/offers";
import axios from "axios";
import {useEffect, useRef, useState} from "react";
import {API_BASE_URL} from "@/config/route";
import ChatBot from "@/components/chatbot";
import ApiClient from "../controllers/api";

export default function Home() {

  const [flights, setFlights] = useState([]);
  const [offers, setOffers] = useState([]);
  const [messages, setMessages] = useState([]);

  const dataRef = useRef({
    "departure": "",
    "destination": "",
    "departureDate": "",
    "maxCost": ""
  });

  const chatBotRef = useRef({
    "step": 0,
    "departure": "",
    "destination": "",
    "departureDate": "",
    "maxCost": ""
  });

  /**
   * step 0: nothing
   * step 1: departure input coming
   * step 2: destination input coming
   * step 3: departure date input coming
   * step 4: max cost input coming
   */

  const apiclient = ApiClient.getInstance();

  async function searchFlights() {
    if(dataRef.current.maxCost === "") dataRef.current.maxCost = -1;
    if(dataRef.current.departure === "" || dataRef.current.destination === "" || dataRef.current.departureDate === "" ) return;
    else dataRef.current.maxCost = parseInt(dataRef.current.maxCost);
    const response = await apiclient.request('post', '/flight/search', {
      "departure": dataRef.current.departure,
      "destination": dataRef.current.destination,
      "departureDate": dataRef.current.departureDate,
      "maxCost": dataRef.current.maxCost
    });
    if(response.success) {
      let tmp = response.data;
      for (let i = 0; i < tmp.length; i++) {
        tmp[i].offer = null;
        const response = await apiclient.request('get', `/flight/${tmp[i].id}/offer`);
        if(response.success) {
          tmp[i].offer = response.data;
        }
      }
      setFlights(tmp);
    }
  }

  async function getGenericOffers(){
    const response = await apiclient.request('get', '/offer/generic');
    if(response.success) {
        setOffers(response.data);
    }
  }

  /**
   * @param {String} message
   * @returns {Promise<void>}
   */
  async function newMessageFromChatBot(message) {
    const tmp = messages;
    tmp.push({text: message, sender: "me"});
    if(message.includes("offer") || message.includes("Offer") || message.includes("OFFER")) {
      let offersDescription = "";
      for (let i = 0; i < offers.length; i++) {
        offersDescription += `${i + 1}. ${offers[i].description}\n`;
      }
      offersDescription = offersDescription.trimEnd();
      tmp.push({text: offersDescription, sender: "bot"});
    }
    else if(message.includes("flight") || message.includes("Flight") || message.includes("FLIGHT")) {
      tmp.push({text: "From where you will depart ?", sender: "bot"});
      chatBotRef.current.step = 1;
    }else{
      if(chatBotRef.current.step === 1) {
        chatBotRef.current.departure = message;
        tmp.push({text: "Where you will arrive ?", sender: "bot"});
        chatBotRef.current.step = 2;
      }
      else if(chatBotRef.current.step === 2) {
        chatBotRef.current.destination = message;
        tmp.push({text: "When you will depart ?\n[e.g. 2023-07-24]", sender: "bot"});
        chatBotRef.current.step = 3;
      }
      else if(chatBotRef.current.step === 3) {
        chatBotRef.current.departureDate = message;
        tmp.push({text: "What is your max cost ?\nEnter N/A if you have no issue with cost", sender: "bot"});
        chatBotRef.current.step = 4;
      }
      else if(chatBotRef.current.step === 4) {
        chatBotRef.current.maxCost = message;
        let mm = "Searching Flights For ...";
        mm += `\nDeparture: ${chatBotRef.current.departure}`;
        mm += `\nDestination: ${chatBotRef.current.destination}`;
        mm += `\nDeparture Date: ${chatBotRef.current.departureDate}`;
        mm += `\nMax Cost: ${chatBotRef.current.maxCost}`;
        tmp.push({text: mm, sender: "bot"});
        chatBotRef.current.step = 0;
        searchFlightsFromChatBot();
      }
    }
    setMessages([...tmp]);
  }

  async function searchFlightsFromChatBot() {
    if(chatBotRef.current.maxCost === "" || chatBotRef.current.maxCost === "N/A" || chatBotRef.current.maxCost === "NA") chatBotRef.current.maxCost = -1;
    if(chatBotRef.current.departure === "" || chatBotRef.current.destination === "" || chatBotRef.current.departureDate === "" ) return;
    else chatBotRef.current.maxCost = parseInt(chatBotRef.current.maxCost);
    const response = await apiclient.request('post', '/flight/search', {
      "departure": chatBotRef.current.departure,
      "destination": chatBotRef.current.destination,
      "departureDate": chatBotRef.current.departureDate,
      "maxCost": chatBotRef.current.maxCost
    });
    if(response.success) {
      let tmp = response.data;
      for (let i = 0; i < tmp.length; i++) {
        tmp[i].offer = null;
        const response = await apiclient.request('get', `/flight/${tmp[i].id}/offer`);
        if(response.success) {
          tmp[i].offer = response.data;
        }
      }
    //   Create a new message
      let tmp2 = messages;
      let reply;
      reply = `Found ${tmp.length} flights\n`;
      tmp2.push({text: reply, sender: "bot"});
      for (let i = 0; i < tmp.length; i++) {
        reply = "";
        reply += `${i + 1}. ${tmp[i].departureCity} -> ${tmp[i].destinationCity} (${tmp[i].departureDate})\n`;
        reply += `${tmp[i].departureTime} -> ${tmp[i].arrivalTime}\n`;
        reply += `Airline : ${tmp[i].name}\n`;
        reply += `Cost: ${tmp[i].offer ? tmp[i].offer.cost : tmp[i].cost}\n`;
        if(tmp[i].offer) reply += `Offer: ${tmp[i].offer.description}\n`;
        reply += `Seats: ${tmp[i].seats}\n`;
        if(tmp[i].hops === "") reply += `Hops: Direct\n`;
        else reply += `Hops: ${tmp[i].hops}\n`;
        tmp2.push({text: reply, sender: "bot"});
      }
      setMessages([...tmp2]);
    }
  }

  useEffect(() => {
    getGenericOffers();
    setInterval(() => {
      getGenericOffers();
    }, 20000)
    setMessages([
      {text: "How can I help you?\n1. Search Flights\n2. Show Offers", sender: "bot"}
    ])
  }, [])


  return (
    <div className="p-8">
      <Header dataRef={dataRef} searchFlights={searchFlights} />
      <OffersTab offers={offers} />
      <div className="flex flex-col gap-5">
        {
            flights.map((flight, index) => <Flight data={flight} key={index} />)
        }
        <ChatBot messages={messages} newMessageSubmit={newMessageFromChatBot} />
      </div>


    </div>);
}