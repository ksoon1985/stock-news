import { defineStore } from "pinia";
import { ref } from "vue";

export const useUserStore = defineStore("storeUser", {
  state: () => {
    return {
      tempCode: ref(""),

      isLogin: ref(false), // 로그인 체크
      nickName: ref(""),

      likeList: ref([]), // 좋아요 리스트

      topicOne: ref(true), // 커뮤니티 토픽 리스트
      topicTwo: ref(false), // 커뮤니티 상세 화면
      keywordOne: ref(true), // 키워드 화면 리스트
      keywordTwo: ref(false), // 키워드 화면 뉴스조회 화면
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
    setkeywordOne(value) {
      this.keywordOne = value;
    },
    setkeywordTwo(value) {
      this.keywordTwo = value;
    },
  },
  persist: {
    enabled: true,
    storage: localStorage,
    paths: [
      "tempCode",
      "isLogin",
      "nickName",
      "topicOne",
      "topicTwo",
      "keywordOne",
      "keywordTwo",
    ],
  },
});
