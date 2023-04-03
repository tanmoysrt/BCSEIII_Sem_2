import { faPlaneDeparture } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {useState} from "react";

export default function Flight(){



    return (
        <>
            <div className="h-32 bg-slate-100 rounded-md flex items-center justify-between text-xl py-5 px-7 relative">
                <div className="flex items-center gap-7">
                    <div className="flex flex-col justify-center items-center">
                        <a className="font-bold">10:45 PM</a>
                        <a>KOAA</a>
                    </div>
                    <a>------------------</a>
                    <div className="flex flex-col justify-center items-center">
                        <a className="font-bold">12:30 PM</a>
                        <a>CCU</a>
                    </div>
                </div>

                <div className="font-bold">
                    <FontAwesomeIcon icon={faPlaneDeparture} size="lg" className="mr-4"/>
                    200 seats
                </div>

                <div className="flex flex-col justify-center items-center">
                    <a className="font-bold">NON-STOP</a>
                    <a>KOAA, BHU</a>
                </div>

                <div className="flex flex-col justify-center items-center text-2xl">
                    <a className="font-bold text-4xl">₹ 6999</a>
                    <span className="text-cred line-through">
                        <span style={{
                            color: "black"
                        }}>&nbsp;12000&nbsp;</span>
                    </span>
                </div>

                {/* Offer */}
                <div className="absolute bottom-0 right-0 bg-cred text-white px-5 py-1 text-sm rounded-br-lg rounded-tl-lg">
                    <a>50% off on total price</a>
                </div>
            </div>
        </>
    );
}