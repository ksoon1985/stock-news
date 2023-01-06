import axios from "axios";

import cookie from "js-cookie";

//axios.defaults.baseURL = "http://localhost:8089/";

// add a request interceptor
axios.interceptors.request.use(
  (config) => {
    //console.log("axios interceptors request", cookie.get("token"));
    let nickName = localStorage.getItem("nickName");

    // jwt 토큰이 있는데 isLogin 이 false 인 경우
    if (hasToken(cookie.get("token"))) {
      console.log("토큰 있음");

      localStorage.setItem("isLogin", true);
      localStorage.setItem("nickName", nickName);
    } else {
      console.log("토큰 없음");
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

const hasToken = (token) => {
  if (typeof token == undefined || token == null || token == "") return false;
  else return true;
};

export default axios;
