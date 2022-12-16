import { defineStore } from "pinia";
import { ref } from "vue";

export const useUserStore = defineStore("storeUser", {
  state: () => {
    return {
      tempCode: ref(""),

      isLogin: ref(false),
      nickName: ref(""),

      likeList: ref([]),

      topicOne: ref(true),
      topicTwo: ref(false),
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
    setTopicOne(value) {
      this.topicOne = value;
    },
    setTopicTwo(value) {
      this.topicTwo = value;
    },
  },
  persist: {
    enabled: true,
    storage: localStorage,
    paths: ["tempCode", "isLogin", "nickName", "topicOne", "topicTwo"],
  },
});
