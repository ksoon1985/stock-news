import axios from "axios";

import cookie from "js-cookie";

//axios.defaults.baseURL = "http://localhost:8089/";

// add a request interceptor
axios.interceptors.request.use(
  (config) => {
    let nickName = localStorage.getItem("nickName");

    // jwt 토큰이 있는데 isLogin 이 false 인 경우
    if (cookie.get("token") != "undefined") {
      localStorage.setItem("isLogin", true);
      localStorage.setItem("nickName", nickName);
    } else {
      localStorage.setItem("isLogin", false);
      localStorage.setItem("nickName", "");
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// add a response interceptor
axios.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default axios;
