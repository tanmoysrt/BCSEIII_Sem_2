import {Fragment, useEffect, useRef, useState} from "react";
import Navbar from "@/components/navbar";
import {Dialog, Transition} from "@headlessui/react";
import ApiClient from "@/controllers/api";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrash} from "@fortawesome/free-solid-svg-icons";
import toast from "react-hot-toast";


export default function () {

    let [isOpen, setIsOpen] = useState(false)
    const [flights, setFlights] = useState([]);
    const dataRef = useRef({
        "description": "",
        "discountedPrice": 0,
    });
    const apiclient = ApiClient.getInstance();

    function closeModal() {
        setIsOpen(false)
    }

    function openOfferModal(flightRecord) {
        dataRef.current.selectedFlight = flightRecord;
        setIsOpen(true)
    }

    async function fetchFlights() {
        const response = await apiclient.request('get', '/flight/all');
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

    async function deleteFlight(flightId) {
        const response = await apiclient.request('delete', `/flight/${flightId}`);
        if(response.success) {
            if(response.data.status){
                fetchFlights();
                toast.success("Flight deleted successfully");
            }else{
                toast.error("Something went wrong");
            }
        }else{
            toast.error("Something went wrong");
        }
    }

    async function createOffer(){
        let description = dataRef.current.description;
        let discountedPrice = dataRef.current.discountedPrice;
        if(description === "" || discountedPrice === 0){
            toast.error("Please fill all the fields");
            return;
        }
        discountedPrice = parseInt(discountedPrice);
        const response = await apiclient.request('post', `/offer`, {
            "cost": discountedPrice,
            "description": description,
            "flight": {
                "id": dataRef.current.selectedFlight.id
            }
        });
        if(response.success) {
            const flight_id = response.data.flight.id;
            let flights_record = flights;
            for (let i = 0; i < flights_record.length; i++) {
                if(flights_record[i].id === flight_id){
                    flights_record[i].offer = response.data;
                }
            }
            setFlights(flights_record);
            toast.success("Offer added successfully");
        }else{
            toast.error("Something went wrong");
        }
        closeModal();
    }

    useEffect(() => {
        fetchFlights();
    },[])

    return (
        <>
            <Navbar/>
            <div className="p-8">
                <div className="relative overflow-x-auto shadow-md sm:rounded-lg">
                    <table className="w-full text-sm text-left text-gray-500 dark:text-gray-400">
                        <thead
                            className="text-xs text-gray-700 uppercase bg-gray-100 dark:bg-gray-700 dark:text-gray-400">
                        <tr>
                            <th scope="col" className="px-6 py-3">
                                Flight
                            </th>
                            <th scope="col" className="px-6 py-3">
                                From
                            </th>
                            <th scope="col" className="px-6 py-3">
                                To
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Date
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Departure Time
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Arrival Time
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Hops
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Cost
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Offer
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Delete
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            flights.map((flight, index) =>
                                <tr className="bg-white border-b dark:bg-gray-900 dark:border-gray-700" key={flight.id}>
                                    <th scope="row"
                                        className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                        {flight.name}
                                    </th>
                                    <td className="px-6 py-4">
                                        {flight.departureCity}
                                    </td>
                                    <td className="px-6 py-4">
                                        {flight.arrivalCity}
                                    </td>
                                    <td className="px-6 py-4">
                                        {flight.departureDate}
                                    </td>
                                    <td className="px-6 py-4">
                                        {flight.departureTime}
                                    </td>
                                    <td className="px-6 py-4">
                                        {flight.arrivalTime}
                                    </td>
                                    <td className="px-6 py-4">
                                        {flight.hops}
                                    </td>
                                    <td className="px-6 py-4">
                                        {flight.cost} /-
                                    </td>
                                    <td className="px-6 py-4">
                                        {
                                            flight.offer ?
                                                <span>{flight.offer.description}</span> :
                                                <button className="bg-transparent hover:bg-blue-500 text-blue-500 font-semibold hover:text-white py-1 px-4 border border-blue-500 hover:border-transparent rounded" onClick={()=>openOfferModal(flight)}>Add Offer</button>
                                        }
                                        {
                                            flight.offer ?
                                                <span>&nbsp;Rs {flight.offer.cost}</span>
                                                : null
                                        }
                                    </td>
                                    <td className="px-6 py-4">
                                        <button className="bg-transparent hover:bg-red-500 text-red-500 font-semibold hover:text-white py-1 px-4 border border-red-500 hover:border-transparent rounded" onClick={()=>deleteFlight(flight.id)}><FontAwesomeIcon icon={faTrash} className="mr-2"  />Delete</button>
                                    </td>
                                </tr>
                            )
                        }
                        </tbody>
                    </table>
                </div>
            </div>

            {/*  Modal  */}

            <Transition appear show={isOpen} as={Fragment}>
                <Dialog as="div" className="relative z-10" onClose={closeModal}>
                    <Transition.Child
                        as={Fragment}
                        enter="ease-out duration-300"
                        enterFrom="opacity-0"
                        enterTo="opacity-100"
                        leave="ease-in duration-200"
                        leaveFrom="opacity-100"
                        leaveTo="opacity-0"
                    >
                        <div className="fixed inset-0 bg-black bg-opacity-25"/>
                    </Transition.Child>

                    <div className="fixed inset-0 overflow-y-auto">
                        <div className="flex min-h-full items-center justify-center p-4 text-center">
                            <Transition.Child
                                as={Fragment}
                                enter="ease-out duration-300"
                                enterFrom="opacity-0 scale-95"
                                enterTo="opacity-100 scale-100"
                                leave="ease-in duration-200"
                                leaveFrom="opacity-100 scale-100"
                                leaveTo="opacity-0 scale-95"
                            >
                                <Dialog.Panel
                                    className="w-full max-w-md transform overflow-hidden rounded-2xl bg-white p-6 text-left align-middle shadow-xl transition-all">
                                    <Dialog.Title
                                        as="h3"
                                        className="text-lg font-medium leading-6 text-gray-900"
                                    >
                                        Create Offer
                                    </Dialog.Title>
                                    <div className="mt-2">
                                        <div className="mb-4">
                                            <label className="block text-gray-700 text-base font-bold mb-2">Description</label>
                                            <input
                                                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                                placeholder="60% off on booking"
                                                onChange={(e) => dataRef.current.description = e.target.value}
                                            />
                                        </div>
                                        <div className="mb-4">
                                            <label className="block text-gray-700 text-base font-bold mb-2">Discounted Price</label>
                                            <input
                                                type="number"
                                                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                                placeholder="4000"
                                                onChange={(e) => dataRef.current.discountedPrice = e.target.value}
                                            />
                                        </div>
                                    </div>

                                    <div className="mt-4">
                                        <button
                                            type="button"
                                            className="w-full inline-flex justify-center rounded-md border border-transparent bg-blue-100 px-4 py-2 text-sm font-medium text-blue-900 hover:bg-blue-200 focus:outline-none focus-visible:ring-2 focus-visible:ring-blue-500 focus-visible:ring-offset-2"
                                            onClick={createOffer}
                                        >
                                            Submit
                                        </button>
                                    </div>
                                </Dialog.Panel>
                            </Transition.Child>
                        </div>
                    </div>
                </Dialog>
            </Transition>
        </>
    );
}