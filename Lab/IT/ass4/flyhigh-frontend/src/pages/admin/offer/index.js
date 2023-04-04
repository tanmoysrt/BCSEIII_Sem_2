import {Fragment, useEffect, useRef, useState} from "react";
import Navbar from "@/components/navbar";
import {Dialog, Transition} from "@headlessui/react";
import ApiClient from "@/controllers/api";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrash} from "@fortawesome/free-solid-svg-icons";
import toast from "react-hot-toast";


export default function () {

    let [isOpen, setIsOpen] = useState(false)
    let [offers, setOffers] = useState([]);

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

    async function fetchOffers() {
        const response = await apiclient.request('get', '/offer/generic');
        if(response.success) {
            setOffers(response.data);
        }
    }

    async function deleteOffer(id) {
        const response = await apiclient.request('delete', `/offer/${id}`);
        if(response.success) {
            if(response.data.status){
                const newOffers = offers.filter((offer) => offer.id !== id);
                setOffers(newOffers);
                toast.success("Offer deleted successfully");
            }else{
                toast.error("Something went wrong");
            }
        }else{
            toast.error("Something went wrong");
        }
    }

    useEffect(() => {
        fetchOffers();
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
                                ID
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Description
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Date
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Valid Till
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Delete
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            offers.map((offer, index) =>
                                <tr className="bg-white border-b dark:bg-gray-900 dark:border-gray-700" key={offer.id}>
                                    <th scope="row"
                                        className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                        {offer.id}
                                    </th>
                                    <td className="px-6 py-4">
                                        {offer.description}
                                    </td>
                                    <td className="px-6 py-4">
                                        {offer.date}
                                    </td>
                                    <td className="px-6 py-4">
                                        {offer.isLimitedTimeOffer ? offer.validUntil : "N/A"}
                                    </td>
                                    <td className="px-6 py-4">
                                        <button className="bg-transparent hover:bg-red-500 text-red-500 font-semibold hover:text-white py-1 px-4 border border-red-500 hover:border-transparent rounded" onClick={()=>deleteOffer(offer.id)}><FontAwesomeIcon icon={faTrash} className="mr-2"  />Delete</button>
                                    </td>
                                </tr>
                            )
                        }
                        </tbody>
                    </table>
                </div>
            </div>
        </>
    );
}