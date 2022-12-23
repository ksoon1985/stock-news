import axios from "axios";

//axios.defaults.baseURL = "http://localhost:8089/";

// add a request interceptor
axios.interceptors.request.use(
  (config) => {
    //console.log("request interceptor");

    //config.headers.Authorazation = localStorage.getItem("jwt");
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// add a response interceptor
axios.interceptors.response.use(
  (response) => {
    //console.log("response  interceptor");
    return response;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default axios;
