import { defineStore } from "pinia";
import { ref } from "vue";

export const useUserStore = defineStore("storeUser", {
  state: () => {
    return {
      isLogin: ref(false),
      nickName: ref(""),
    };
  },
  actions: {
    setIsLogin(value) {
      this.isLogin = value;
    },
    setNickName(value) {
      this.nickName = value;
    },
  },
  persist: {
    enabled: true,
    storage: localStorage,
    paths: ["isLogin", "nickName"],
  },
});
