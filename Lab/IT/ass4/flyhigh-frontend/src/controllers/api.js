import axios from "axios";
import {API_BASE_URL} from  "../config/route";


class ApiClient{
    static instance = null;
    /** @type {axios} */
    static axiosClient = null;
    static #token = "";
    static getInstance() {
        if(ApiClient.instance !== null) return ApiClient.instance;
        ApiClient.axiosClient = axios;
        ApiClient.instance = new ApiClient();
        return  ApiClient.instance;
    }

    fetchToken(){
        if(ApiClient.#token !== "") return;
        ApiClient.#token = localStorage.getItem("token");
    }

    setToken(token){
        if(!token) return false;
        ApiClient.#token = token;
        return true;
    }

    // route will always start with / and dont ends with /
    async request(method, route, body={}){
        this.fetchToken();
        if(!["get", "put", "post", "delete", "patch"].includes(method.toLowerCase())) {
            return {
                success: false,
                message: "Invalid method",
                data: null
            }
        }
        const config = {
            method: method.toLowerCase(),
            url: `${API_BASE_URL}${route}`,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': ApiClient.#token
            },
            data: JSON.stringify(body)
        }
        try{
            let res = await ApiClient.axiosClient(config);
            return {
                success: true,
                message: "Success",
                data: res.data
            }
        }catch (e) {
            return {
                success: false,
                message: e.message,
                data: null
            }
        }
    }
}

module.exports =  ApiClient;