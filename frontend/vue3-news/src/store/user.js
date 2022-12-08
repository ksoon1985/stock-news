import { defineStore } from "pinia";
import { ref } from "vue";

export const useUserStore = defineStore("storeUser", {
  state: () => {
    return {
      tempCode: ref(""),

      isLogin: ref(false),
      nickName: ref(""),

      likeList: ref([]),
    };
  },
  actions: {
    setIsLogin(value) {
      this.isLogin = value;
    },
    setNickName(value) {
      this.nickName = value;
    },

    setTempCode(value) {
      this.listCode = value;
    },
  },
  persist: {
    enabled: true,
    storage: localStorage,
    paths: ["tempCode", "isLogin", "nickName"],
  },
});
