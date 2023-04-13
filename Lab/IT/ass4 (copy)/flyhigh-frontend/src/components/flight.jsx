import { faPlaneDeparture } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {useState} from "react";

export default function Flight({data, key}){
    return (
        <div key={key}>
            <div className="h-32 bg-slate-100 rounded-md flex items-center justify-between text-xl py-5 px-7 relative">
                <div className="font-bold">
                    {data.name}
                </div>

                <div className="flex items-center gap-7">
                    <div className="flex flex-col justify-center items-center">
                        <a className="font-bold">{data.departureTime}</a>
                        <a>{data.departureCity}</a>
                    </div>
                    <a>------------------</a>
                    <div className="flex flex-col justify-center items-center">
                        <a className="font-bold">{data.arrivalTime}</a>
                        <a>{data.arrivalCity}</a>
                    </div>
                </div>

                <div className="font-bold">
                    <FontAwesomeIcon icon={faPlaneDeparture} size="lg" className="mr-4"/>
                    {data.seats} seats
                </div>

                <div className="flex flex-col justify-center items-center">
                    {
                        data.hops === "" ? <a className="font-bold">NON-STOP</a> : <a className="font-bold">STOPS</a>
                    }
                    {
                        data.hops !== "" ? <a>{data.hops}</a> : ''
                    }

                </div>

                <div className="flex flex-col justify-center items-center text-2xl">
                    <a className="font-bold text-4xl">₹ {data.offer ?  data.offer.cost : data.cost}</a>
                    {
                        data.offer && <span className="text-cred line-through">
                        <span style={{
                            color: "black"
                        }}>&nbsp;{data.cost}&nbsp;</span>
                    </span>
                    }

                </div>
                {
                    data.offer &&
                    <div className="absolute bottom-0 right-0 bg-cred text-white px-5 py-1 text-sm rounded-br-lg rounded-tl-lg">
                        <a>{data.offer.description}</a>
                    </div>
                }
            </div>
        </div>
    );
}