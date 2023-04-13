import { faGlasses, faPaperPlane } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export default function BlogCard(){
    return (
        <div className="max-w-sm overflow-hidden shadow-md hover:shadow-2xl cursor-pointer border border-gray-200 rounded-md">
            <img className="aspect-video object-fill" src="https://cdn.hashnode.com/res/hashnode/image/upload/v1671775779794/f4799720-8bb6-46b0-82ba-34a6ff39f07a.png?w=1600&h=840&fit=crop&crop=entropy&auto=compress,format&format=webp" alt="Sunset in the mountains" />
                <div className="px-6 py-4">
                    <div className="font-bold text-xl mb-2">The Coldest Sunset</div>
                    <p className="text-black-700 text-base mb-2">
                        Written by <i>Tanmoy Sarkar</i>
                    </p>
                    <p className="text-gray-700 text-base mb-0">
                        <FontAwesomeIcon icon={faPaperPlane} /> Published on 12th June, 2021
                    </p>
                    <p className="text-gray-700 text-base mb-0">
                        <FontAwesomeIcon icon={faGlasses} /> 4 min read
                    </p>
                </div>
                <div className="px-6 pb-2">
                    <span className="inline-block bg-gray-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700 mr-2 mb-2">#photography</span>
                    <span className="inline-block bg-gray-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700 mr-2 mb-2">#travel</span>
                    <span className="inline-block bg-gray-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700 mr-2 mb-2">#winter</span>
                </div>
        </div>
    );
}