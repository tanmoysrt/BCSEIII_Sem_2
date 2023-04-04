import Navbar from "@/components/navbar";
import React, {useRef, useState} from "react";
import ApiClient from "@/controllers/api";
import toast from "react-hot-toast";
import {data} from "autoprefixer";

export default function AddFlight() {
    const [noOfRecords, setNoOfRecords] = useState(0);
    const apiclient = ApiClient.getInstance();

    const dataRef = useRef({
        "description": "",
        "date": "",
        "isLimitedTimeOffer": false,
        "validUntil": ""
    });


    const submitForm = async() => {
        dataRef.current.isLimitedTimeOffer = dataRef.current.isLimitedTimeOffer === "true";
        if(dataRef.current.description === "" || dataRef.current.date === "" ){
            toast.error("Please Fill All Fields");
            return;
        }
        if(dataRef.current.validUntil === ""){
            dataRef.current.validUntil = null;
        }else{
            dataRef.current.validUntil = dataRef.current.validUntil+":00";
        }
        if(dataRef.current.isLimitedTimeOffer && dataRef.current.validUntil === null){
            toast.error("Please Fill Valid Until Field");
            return;
        }
        const res = await apiclient.request("post", "/offer", dataRef.current);
        if(res.success){
            toast.success("Offer Added Successfully");
            window.location.href = "/admin/offer";
        }else{
            toast.error("Failed to add offer");
        }
    }


    return (
        <>
            <Navbar />
            <div className="p-8">
                <div className="grid gap-6 mb-6 md:grid-cols-2">
                    <div>
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Description</label>
                        <input type="text" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required onChange={(e)=>dataRef.current.description=e.target.value}/>
                    </div>
                    <div>
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Date</label>
                        <input type="date" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required onChange={(e)=>dataRef.current.date=e.target.value.toString()}/>
                    </div>
                    <div>
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Limited Time Offer</label>
                        <select className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required defaultValue='false' onChange={(e)=>dataRef.current.isLimitedTimeOffer=e.target.value}>
                            <option value="true">Yes</option>
                            <option value="false">No</option>
                        </select>
                    </div>
                    <div>
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Valid Until</label>
                        <input type="time" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required onChange={(e)=>dataRef.current.validUntil=e.target.value}/>
                    </div>
                </div>
                <div className="flex gap-5 mt-2 w-[300px]">
                    <button onClick={submitForm} className="w-1/3 bg-blue-500 hover:bg-blue-700 text-white text-sm font-bold py-2 px-2 rounded-md">
                        Submit
                    </button>
                </div>
            </div>
        </>
    );
}