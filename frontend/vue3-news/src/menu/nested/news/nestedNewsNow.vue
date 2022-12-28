<template>
  <div class="sub-view-result">
    <div class="newsSelectBtn">
      <button
        class="news-btn"
        @click="keywordMounted"
        :disabled="btnOn === false"
      >
        기간에 일치하는 키워드 조회
      </button>
    </div>

    <div v-if="isLoading" class="loading-container">
      <div class="loading">
        <pulse-loader :color="color" />
      </div>
    </div>

    <div class="topic-news" v-if="keywordOne">
      <div class="keyWordStockName">
        <span class="keyword-stock-name">{{ stockName }}</span>
        <span class="keyword-keyword">키워드</span>
      </div>
      <div class="for-keyword-news">
        <div
          class="forKeyWord"
          v-for="(item, index) in keyWordList"
          :key="index"
        >
          <span class="forKeyWordSpan">{{ index + 1 }}</span>
          <h3 class="themeKeywords">{{ item.keyword }}</h3>
          <div class="forBtn">
            <button class="fotBtnOne" @click="likeKeyword(item.keyword)">
              <span
                class="forBtnSpanOne"
                v-if="likeKeywordList.indexOf(item.keyword) !== -1"
              >
                관심중
              </span>
              <span class="forBtnSpanTwo" v-else> 관심 </span>
            </button>
            <router-link
              class="forkeywordRouter"
              :to="{
                name: 'newsNowkeyword',
                query: {
                  code: listCode,
                  keyword: item.keyword,
                },
              }"
            >
              <button class="forBtnTwo" @click="keywordChangeEvent">
                뉴스조회
              </button>
            </router-link>
          </div>
        </div>
      </div>
    </div>
    <div class="" v-if="keywordTwo">
      <Router-view class=""></Router-view>
    </div>
  </div>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { useUserStore } from "@/store/user.js";
import { storeToRefs } from "pinia";
import axios from "@/utils/axios";
import { useRoute, useRouter } from "vue-router";
import { onMounted, ref } from "vue";
import PulseLoader from "vue-spinner/src/PulseLoader.vue";

export default {
  components: {
    PulseLoader,
  },
  setup() {
    const store = useStockStore();
    const userStore = useUserStore();
    const route = useRoute();
    const router = useRouter();
    let routeTest = ref("");
    const keyWordList = ref([]);

    let likeKeywordList = ref([]);

    let { listCode, stockName, modalData } = storeToRefs(store);
    let { nickName, isLogin, keywordOne, keywordTwo } = storeToRefs(userStore);

    let likeStatus = ref(true);
    let isLoading = ref(false);

    const interestClickEvent = () => {
      if (isLogin.value == false) {
        modalData.value = true;
      }
    };

    const keywordChangeEvent = () => {
      keywordOne.value = false;
      keywordTwo.value = true;
    };

    onMounted(async () => {
      if (isLogin.value) {
        await axios.get("/api/stock/keywords/likes").then((res) => {
          likeKeywordList.value = res.data;
        });
      }
      keywordMounted();
    });
    const keywordMounted = async () => {
      await router.isReady();
      routeTest.value = route.query.code;
      listCode.value = routeTest.value;

      let dateEls = document.querySelectorAll(".highcharts-range-input text");
      let fromDate = dateEls[0].innerHTML;
      let toDate = dateEls[1].innerHTML;

      let today = new Date();

      let year = today.getFullYear();
      let month = ("0" + (today.getMonth() + 1)).slice(-2);
      let day = ("0" + today.getDate()).slice(-2);

      // 처음이나 새로고침할 때 차트의 date를 불러오지 못할 경우
      // fromDate = 2020-01-01 , toDate = 현재날짜 로 셋팅
      isLoading.value = true;
      let reqDto = {
        searchTerm: listCode.value,
        fromDate: fromDate == "" ? "2020-01-01" : fromDate,
        toDate: toDate == "" ? year + "-" + month + "-" + day : toDate,
      };

      axios.post("/api/news/getTopicKeywords", reqDto).then((res) => {
        keyWordList.value = res.data;
        keywordOne.value = true;
        router
          .push({ name: "NewsNow", query: { code: listCode.value } })
          .catch((err) => {
            isLoading.value = false;
            console.log(err);
          })
          .finally(() => {
            isLoading.value = false;
          });
      });
    };

    const likeKeyword = (themeKeyword) => {
      let apiPath = "keyword-like";

      if (!isLogin.value) {
        modalData.value = true;
        return;
      }

      // 관심중이면
      if (likeKeywordList.value.indexOf(themeKeyword) !== -1) {
        apiPath = "keyword-dislike";
      }

      axios.get("/api/stock/" + apiPath + "/" + themeKeyword).then((res) => {
        likeKeywordList.value = res.data;
      });
    };

    return {
      listCode,
      stockName,
      keyWordList,
      keywordOne,
      keywordTwo,
      keywordChangeEvent,
      onMounted,
      axios,
      isLogin,
      nickName,
      modalData,
      interestClickEvent,
      likeKeywordList,
      likeKeyword,
      likeStatus,
      router,
      routeTest,
      keywordMounted,
      isLoading,
      color: "#d01411",
    };
  },
};
</script>

<style>
@font-face {
  font-family: "Pretendard-Regular";
  src: url("https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff")
    format("woff");
  font-weight: 500;
  font-style: normal;
}

.sub-view-result {
  height: 90%;
  width: 100%;
  -ms-overflow-style: none;
}

.sub-view-result::-webkit-scrollbar {
  display: none;
}

.keyWordStockName {
  display: flex;
}

.topic-news {
  margin-left: 15px;
  margin-top: 15px;
}

.keyWordStockName {
  font-family: "Pretendard-Regular";
  margin-bottom: 30px;
}

.keyword-stock-name {
  font-size: 1.2rem;
}

.keyword-keyword {
  margin-left: 5px;
  margin-top: 5px;
  color: #999999;
}

.forKeyWord {
  display: flex;
  font-family: "Pretendard-Regular";
  border-bottom: 1px solid #e5e5e5;
  width: 94%;
  margin-bottom: 1.5rem;
}

.forKeyWordSpan {
  margin-left: 10px;
  font-size: 1.1rem;
  color: #d01411;
  position: relative;
  top: 14px;
  left: 7px;
  width: 22px;
}

.themeKeywords {
  position: relative;
  top: -5px;
  left: 37px;
  width: 50%;
}

.forKeywordBtn {
  position: relative;
  top: 7px;
  left: 100px;
}

.forBtn {
  position: relative;
  top: 12px;
  left: 80px;
}

.fotBtnOne:hover {
  opacity: 0.5;
}

.fotBtnOne {
  width: 5rem;
  height: 1.7rem;
  border-radius: 4px;
  font-size: 1rem;
  font-weight: 500;
  color: #fef6f6;
  border: none;
  margin-right: 10px;
  font-family: "Pretendard-Regular";
  cursor: pointer;
}

.forBtnTwo {
  width: 5rem;
  height: 1.7rem;
  border-radius: 4px;
  background-color: #d01411;
  font-size: 1rem;
  font-weight: 500;
  color: #fef6f6;
  border: none;
  font-family: "Pretendard-Regular";
  cursor: pointer;
}

.forBtnTwo:hover {
  opacity: 0.5;
}

.forBtnSpanTwo {
  display: inline-block;
  background-color: #d01411;
  width: 80px;
  height: 27.19px;
  border-radius: 4px;
  position: relative;
  left: -4px;
  justify-content: center;
  align-items: center;
  display: flex;
}

.forBtnSpanOne {
  display: inline-block;
  background-color: #999999;
  width: 80px;
  height: 27.19px;
  border-radius: 4px;
  position: relative;
  left: -4px;
  justify-content: center;
  align-items: center;
  display: flex;
}
</style>
