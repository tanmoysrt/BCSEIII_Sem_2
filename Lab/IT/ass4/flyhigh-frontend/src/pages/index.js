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

  const dataRef = useRef({
    "departure": "",
    "destination": "",
    "departureDate": "",
    "maxCost": ""
  });

  const apiclient = ApiClient.getInstance();

  async function searchFlights() {
    if(dataRef.current.maxCost === "") dataRef.current.maxCost = -1;
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

  useEffect(() => {
    getGenericOffers();
  }, [])

  return (
    <div className="p-8">

      <Header dataRef={dataRef} searchFlights={searchFlights} />
      <OffersTab offers={offers} />
      <div className="flex flex-col gap-5">
        {
            flights.map((flight, index) => <Flight data={flight} key={index} />)
        }
        <ChatBot />

      </div>


    </div>);
}