import {useEffect, useRef, useState} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faHeadset, faPlaneDeparture} from "@fortawesome/free-solid-svg-icons";
import {faCircleXmark} from "@fortawesome/free-regular-svg-icons";

/**
 * @param {function} newMessageSubmit
 * @param {Array} messages
 * @returns {JSX.Element}
 * @constructor
 */
const Chat = ({newMessageSubmit, messages}) => {
    const [inputValue, setInputValue] = useState("");
    const [minimized, setMinimized] = useState(true);
    const messageBoxRef = useRef(null);

    const handleSubmit = (e) => {
        e.preventDefault();
        if (inputValue.trim()) {
            newMessageSubmit(inputValue);
            setInputValue("");
        }
    };

    useEffect(() => {
        if(messageBoxRef.current !== null)  messageBoxRef.current.scrollTop = messageBoxRef.current.scrollHeight;
    }, [messages]);

    return (
        minimized ?
        <div className="absolute bottom-8 right-8 bg-yellow-400 p-4 rounded-full" onClick={()=>setMinimized(false)}>
         <FontAwesomeIcon icon={faHeadset} size="3x" />
        </div>
        :  <div className="max-w-md mx-auto bg-white border-2 border-gray shadow-md rounded-lg overflow-hidden absolute bottom-4 right-4">
            <div className="px-4 py-5 sm:px-6 flex justify-between">
                <h1 className="text-lg font-medium leading-6 text-gray-900"><FontAwesomeIcon icon={faPlaneDeparture} className="mr-2" />Airline Sathi</h1>
                <button onClick={()=>setMinimized(true)} >
                    <FontAwesomeIcon icon={faCircleXmark} size="xl" />
                </button>
            </div>
            <div className="px-4 py-5 sm:p-6">
                <div className="overflow-y-scroll h-80 px-2" ref={messageBoxRef}>
                    {messages.map((message, index) => (
                        <div
                            key={index}
                            className={`my-2 ${
                                message.sender === "me"
                                    ? "text-right"
                                    : "text-left"
                            }`}
                        >
                          <p
                              style={{
                                  whiteSpace: "pre",
                              }}
                              className={`inline-block rounded-lg px-3 py-1 ${
                                  message.sender === "me"
                                      ? "bg-green-500 text-white"
                                      : "bg-gray-200 text-gray-700"
                              }`}
                          >
                            {message.text}
                          </p>
                        </div>
                    ))}
                </div>
                <form onSubmit={handleSubmit}>
                    <div className="mt-4 flex">
                        <input
                            type="text"
                            className="form-input rounded-l-md w-full border-gray-300 p-2 border-blue-500 focus:outline-none focus:shadow-outline-blue"
                            placeholder="Type your message here..."
                            value={inputValue}
                            onChange={(e) => setInputValue(e.target.value)}
                        />
                        <button
                            type="submit"
                            className="px-4 py-2 rounded-md bg-blue-500 text-white font-bold hover:bg-blue-700"
                        >
                            Send
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default Chat;
