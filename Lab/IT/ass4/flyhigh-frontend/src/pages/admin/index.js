import {useRef} from "react";
import ApiClient from "@/controllers/api";
import toast from "react-hot-toast";

export default function Index(){
    const dataref = useRef({
        username: "",
        password: ""
    })
    const apiClient = ApiClient.getInstance();

    async function login(){
        const response = await apiClient.request("post", "/auth/login", {
            username: dataref.current.username,
            password: dataref.current.password
        })
        if(response.success){
            const success = response.data.success;
            if(success){
                const token = response.data.token;
                localStorage.setItem("token", token);
                localStorage.setItem("role", response.data.role);
                toast.success("Login successful");
                window.location.href = "/admin/flight";
            }else{
                toast.error("Invalid username or password");
            }
        }else{
            toast.error("Request Failed");
        }
    }


    return (
        <section className="bg-gray-50 dark:bg-gray-900">
        <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
            <div
                className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
                <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
                    <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
                        Sign in to your account
                    </h1>
                    <form className="space-y-4 md:space-y-6" action="#">
                        <div>
                            <label htmlFor="username"
                                   className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Username</label>
                            <input type="text" name="username" id="username"
                                   className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                   required onChange={(e)=>dataref.current.username = e.target.value} />
                        </div>
                        <div>
                            <label htmlFor="password"
                                   className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Password</label>
                            <input type="password" name="password" id="password" placeholder="••••••••"
                                   className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                   required onChange={(e)=>dataref.current.password = e.target.value} />
                        </div>
                        <button type="button" className="w-full text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100 focus:ring-4 focus:ring-gray-200 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 dark:bg-gray-800 dark:text-white dark:border-gray-600 dark:hover:bg-gray-700 dark:hover:border-gray-600 dark:focus:ring-gray-700" onClick={login}>Login Now</button>
                    </form>
                </div>
            </div>
        </div>
    </section>
);
}