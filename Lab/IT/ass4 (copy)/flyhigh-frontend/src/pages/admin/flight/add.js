import Navbar from "@/components/navbar";
import React, {useRef, useState} from "react";
import ApiClient from "@/controllers/api";
import toast from "react-hot-toast";

export default function AddFlight() {
    const [noOfRecords, setNoOfRecords] = useState(0);
    const apiclient = ApiClient.getInstance();

    const dataRef = useRef({
        "name": "",
        "date": "",
        "seats": 0,
        "records": []
    });

    const addRecord = () => {
        let records = dataRef.current.records;
        let departureCity = "";
        let departureTime = "";
        if(records.length !== 0){
            departureCity = records[records.length-1].arrivalCity;
            departureTime = records[records.length-1].arrivalTime;
        }
        records.push({
            "departureCity": departureCity,
            "arrivalCity": "",
            "departureTime": departureTime,
            "arrivalTime": "",
            "cost": 0
        })
        console.log(records);
        dataRef.current.records = records;
        setNoOfRecords(noOfRecords + 1);
    }

    const submitForm = async() => {
        let data = dataRef.current;
        let records = data.records;
        for (let i = 0; i < records.length; i++) {
            records[i].departureTime = records[i].departureTime + ":00";
            records[i].arrivalTime = records[i].arrivalTime + ":00";
        }

        let final_records = [];
        for (let i = 0; i < records.length; i++) {
            final_records.push({
                "departureCity": records[i].departureCity,
                "arrivalCity": records[i].arrivalCity,
                "departureDate": data.date,
                "departureTime": records[i].departureTime,
                "arrivalTime": records[i].arrivalTime,
                "seats": parseInt(data.seats),
                "cost": parseInt(records[i].cost),
                "hops": "",
                "name" : data.name
            })
        }

        for (let i = 0; i < records.length; i++) {
            for (let j = i+1; j < records.length; j++) {
                let arrivalTime = records[j].arrivalTime;
                let departureTime = records[i].departureTime;
                let arrivalCity = records[j].arrivalCity;
                let departureCity = records[i].departureCity;
                let cost = 0;
                let hops = "";
                for (let k = i; k <= j; k++) {
                    cost += parseInt(records[k].cost);
                    if(k !== i){
                        hops += records[k].departureCity + ", ";
                    }
                }
                hops = hops.trimEnd();
                if(hops.endsWith(",")){
                    hops = hops.substring(0, hops.length-1);
                }
                final_records.push({
                    "departureCity": departureCity,
                    "arrivalCity": arrivalCity,
                    "departureDate": data.date,
                    "departureTime": departureTime,
                    "arrivalTime": arrivalTime,
                    "seats": parseInt(data.seats),
                    "cost": cost,
                    "hops": hops,
                    "name" : data.name
                })
            }
        }

        for (let i = 0; i < final_records.length; i++) {
            const response = await apiclient.request("post", "/flight", final_records[i]);
            if(response.success){
                toast.success(`${final_records[i].departureCity} -> ${final_records[i].arrivalCity} Flight Added Successfully`);
            }
        }

        toast.success("All Flights Added Successfully");
        window.location.href = "/admin/flight";
    }


    return (
        <>
            <Navbar />
            <div className="p-8">
                <div className="grid gap-6 mb-6 md:grid-cols-2">
                    <div>
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Flight Name</label>
                        <input type="text" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required onChange={(e)=>dataRef.current.name=e.target.value}/>
                    </div>
                    <div>
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Date</label>
                        <input type="date" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required onChange={(e)=>dataRef.current.date=e.target.value.toString()}/>
                    </div>
                    <div>
                        <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">No Of Seats</label>
                        <input type="number" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required onChange={(e)=>dataRef.current.seats=e.target.value}/>
                    </div>
                </div>
                <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400 mt-8">
                    <thead
                        className="text-xs text-gray-700 uppercase bg-gray-100 dark:bg-gray-700 dark:text-gray-400">
                        <tr>
                            <th scope="col" className="px-6 py-3">
                                From
                            </th>
                            <th scope="col" className="px-6 py-3">
                                To
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Departure Time
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Arrival Time
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Cost
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                    {
                        noOfRecords > 0 ? dataRef.current.records.map((record, index) =>
                            <tr>
                                <td scope="col" className="px-6 py-3">
                                    <input className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" defaultValue={record.departureCity} onChange={(e)=>dataRef.current.records[index].departureCity = e.target.value}  />
                                </td>
                                <td scope="col" className="px-6 py-3">
                                    <input className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" defaultValue={record.arrivalCity} onChange={(e)=>dataRef.current.records[index].arrivalCity = e.target.value}/>
                                </td>
                                <td scope="col" className="px-6 py-3">
                                    <input className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" type="time" defaultValue={record.departureTime} onChange={(e)=>dataRef.current.records[index].departureTime = e.target.value} />
                                </td>
                                <td scope="col" className="px-6 py-3">
                                    <input className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" type="time" defaultValue={record.arrivalTime} onChange={(e)=>dataRef.current.records[index].arrivalTime = e.target.value} />
                                </td>
                                <td scope="col" className="px-6 py-3">
                                    <input className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" type="number" defaultValue={record.cost} onChange={(e)=>dataRef.current.records[index].cost = e.target.value} />
                                </td>
                            </tr>
                        ) : null
                    }

                    </tbody>
                </table>
                <div className="flex gap-5 mt-2 w-full">
                    <button onClick={addRecord} className="bg-orange-500 hover:bg-blue-700 text-white text-sm font-bold py-1 px-2 rounded-md">
                        Add Another Stop
                    </button>
                    <button onClick={submitForm} className="bg-blue-500 hover:bg-blue-700 text-white text-sm font-bold py-1 px-2 rounded-md">
                        Submit
                    </button>
                </div>
            </div>
        </>
    );
}