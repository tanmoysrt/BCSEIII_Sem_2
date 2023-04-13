export default function OffersTab({offers}){
    return (
    <div className="bg-cred text-white my-4 p-2 rounded-md">
        <marquee>
            {
                offers.map((offer, key) => <span className="mx-4">⚪ {offer.description}</span>)
            }

        </marquee>
    </div>
    );
}