<template>
  <div class="sub-view-resultTwo">
    <div v-if="isLoading" class="loading-container">
      <div class="loading">
        <pulse-loader :color="color" />
      </div>
    </div>

    <div class="for-keyword-news">
      <div class="forKeyWord" v-for="(item, index) in keyWordList" :key="index">
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

        <div class="keywordRankDiv" v-if="item.new"><span>NEW</span></div>
        <div v-else>
          <div class="keywordRank" v-if="item.rank == 0">-</div>
          <div class="keywordRankOne" v-else-if="item.rank > 0">
            <div class="keywordFlex">
              <div class="keywordIconOne">
                <svg
                  width="30"
                  height="30"
                  xmlns="http://www.w3.org/2000/svg"
                  style="display: block"
                >
                  <image
                    id="icontwo"
                    class="keywordOne"
                    href="@/assets/svg/arrow-drop-up-1-svgrepo-com.svg"
                    width="30"
                    height="30"
                  ></image>
                </svg>
              </div>
              <span>{{ item.rank }}</span>
            </div>
          </div>
          <div class="keywordRankTwo" v-else-if="item.rank < 0">
            <div class="keywordFlex">
              <div class="keywordIconTwo">
                <svg
                  width="30"
                  height="30"
                  xmlns="http://www.w3.org/2000/svg"
                  style="display: block"
                >
                  <image
                    id="icontwo"
                    class="keywordTwo"
                    href="@/assets/svg/arrow-drop-down-svgrepo-com.svg"
                    width="30"
                    height="30"
                  ></image>
                </svg>
              </div>
              <span>{{ Math.abs(item.rank) }}</span>
            </div>
          </div>
        </div>

        <!-- <div class="keywordRank" v-if="item.rank === 0">-</div>
        <div class="keywordRankOne" v-else-if="item.rank >= 0">
          <div class="keywordFlex">
            <div class="keywordIconOne">
              <svg
                width="30"
                height="30"
                xmlns="http://www.w3.org/2000/svg"
                style="display: block"
              >
                <image
                  id="icontwo"
                  class="keywordOne"
                  href="@/assets/svg/arrow-drop-up-1-svgrepo-com.svg"
                  width="30"
                  height="30"
                ></image>
              </svg>
            </div>
            <span>{{ item.rank }}</span>
          </div>
        </div>
        <div class="keywordRankTwo" v-else-if="item.rank <= 0">
          <div class="keywordFlex">
            <div class="keywordIconTwo">
              <svg
                width="30"
                height="30"
                xmlns="http://www.w3.org/2000/svg"
                style="display: block"
              >
                <image
                  id="icontwo"
                  class="keywordTwo"
                  href="@/assets/svg/arrow-drop-down-svgrepo-com.svg"
                  width="30"
                  height="30"
                ></image>
              </svg>
            </div>
            <span>{{ Math.abs(item.rank) }}</span>
          </div>
        </div> -->
        <!-- <div class="keywordRank" v-if="item.new == true && item.rank == 0">
          new
        </div> -->
      </div>
    </div>

    <div class="newsSelectBtn">
      <button class="news-btn" @click="keywordMounted">
        기간에 일치하는 키워드 조회
      </button>
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
import { onMounted, ref, watch } from "vue";
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

    // watch(keyWordList, () => {
    //   keywordMounted();
    // });

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
          .push({ name: "keywordAll", query: { code: listCode.value } })
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
      watch,
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

/* 라우터 뷰 전체 div */
.sub-view-resultTwo {
  height: 68vh;
  width: 100%;
  -ms-overflow-style: none;
}

/* 라우터 뷰 none 스크롤 */
.sub-view-resultTwo::-webkit-scrollbar {
  display: none;
}

/* 키워드 뉴스 전체 div */
.topic-news {
  margin-left: 15px;
  margin-top: 15px;
}

/* 키워드 타이틀 div */
.keyWordStockName {
  font-family: "Pretendard-Regular";
  margin-bottom: 30px;
  display: flex;
}

/* 키워드 종목 이름  */
.keyword-stock-name {
  font-size: 1.2rem;
}

/* 키워드 키워드  */
.keyword-keyword {
  margin-left: 5px;
  margin-top: 5px;
  color: #999999;
}

/* 키워드 for div */
.forKeyWord {
  display: flex;
  font-family: "Pretendard-Regular";
  border-bottom: 1px solid #e5e5e5;
  width: 94%;
  margin-bottom: 1.5rem;
}

/* 키워드 순번 */
.forKeyWordSpan {
  margin-left: 10px;
  font-size: 1.1rem;
  color: #d01411;
  position: relative;
  top: 14px;
  left: 7px;
  width: 22px;
}

/* 키워드 키워드 이름 */
.themeKeywords {
  position: relative;
  top: -5px;
  left: 37px;
  width: 50%;
}

/* 키워드 뉴스조회 */
.forKeywordBtn {
  position: relative;
  top: 7px;
  left: 100px;
}

/* 키워드 관심중 버튼 */
.forBtn {
  position: relative;
  top: 12px;
  left: 80px;
}

/* 키워드 관심중 호버 */
.fotBtnOne:hover {
  opacity: 0.5;
}

/* 키워드 관심등록 */
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

/* 키워드 관심중 */
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

/* 키워드 관심중 호버 */
.forBtnTwo:hover {
  opacity: 0.5;
}

/* 키워드 관심 span */
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

/* 키워드 관심중 span */
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

/* 랭킹 값이 0일때  */
.keywordRank {
  position: relative;
  left: -130px;
  top: 17px;
}

/* 화살표 & 랭킹 값 */
.keywordFlex {
  display: flex;
}

/* 랭킹 값이 0보다 클 때   */
.keywordRankOne {
  position: relative;
  left: -155px;
  top: 17px;
}

/* 랭킹 값이 0보다 작을 때 */
.keywordRankTwo {
  position: relative;
  left: -155px;
  top: 17px;
}

/* 위쪽 화살표 */
.keywordOne {
  filter: invert(34%) sepia(63%) saturate(6608%) hue-rotate(350deg)
    brightness(98%) contrast(89%);
}

/* 아래쪽 화살표 */
.keywordTwo {
  filter: invert(39%) sepia(65%) saturate(2399%) hue-rotate(201deg)
    brightness(94%) contrast(98%);
}

/* 위쪽 화살표 svg */
.keywordIconOne {
  position: relative;
  top: -5px;
}

/* 아래쪽 화살표 svg */
.keywordIconTwo {
  position: relative;
  top: -5px;
}

/* New Div  */
.keywordRankDiv {
  position: relative;
  left: -145px;
  top: 17px;
  font-family: "Pretendard-Regular";
  font-size: 0.8rem;
}
</style>
