import Flight from "@/components/flight";
import Header from "@/components/header";
import OffersTab from "@/components/offers";
import axios from "axios";
import { useRef } from "react";
import {API_BASE_URL} from "@/config/route";
import ChatBot from "@/components/chatbot";

export default function Home() {

  const dataRef = useRef({
    "departure": "",
    "destination": "",
    "departureDate": "",
    "maxCost": ""
  });

  async function searchFlights() {
    dataRef.current.maxCost = parseInt(dataRef.current.maxCost);
    const response = await axios({
      method: 'post',
      url: API_BASE_URL + '/flight/search',
      headers: {
        'Content-Type': 'application/json'
      },
      data: JSON.stringify(dataRef.current)
    });
    console.log(response);
  }

  return (
    <div className="p-8">

      <Header dataRef={dataRef} searchFlights={searchFlights} />
      <OffersTab />
      <div className="flex flex-col gap-5">
        <Flight />
        <Flight />
        <Flight />
        <Flight />
        <Flight />
        <Flight />

        <ChatBot />

      </div>


    </div>);
}