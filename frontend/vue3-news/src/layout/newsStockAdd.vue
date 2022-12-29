<template>
  <div class="left-sidebar-menu">
    <div class="header">
      <div class="stock-list"><h3>관심목록</h3></div>
      <div class="stock-add">
        <button
          type="button"
          class="btn-menu"
          @click="noLogModal"
          v-if="leftInputChange"
        >
          <div class="plus-icon">
            <svg
              width="20"
              height="20"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
              style="display: block"
            >
              <image
                href="@/assets/svg/plus-svgrepo-com.svg"
                width="20"
                height="20"
              />
            </svg>
          </div>
          <span class="btn-text">종목추가</span>
        </button>
        <div class="leftSearchStock" v-if="leftInputChangeTwo">
          <input
            @change="onClean"
            ref="inputTitle"
            class="leftInput"
            type="text"
            spellcheck="false"
            placeholder=" 종목명 또는 코드를 입력해주세요"
            :value="searchInput"
            @input="resultChange"
            @keyup.enter="resultChange"
            @blur="blurClose"
            autofocus
          />
        </div>
        <div class="leftSearchResults" v-if="resultSearch">
          <ul class="ul-list">
            <li
              class="resultsListTwo"
              v-for="(item, index) in resultData"
              :key="index"
              @mousedown="itemCodeClick(item.code)"
            >
              <button class="leftResultsBtn">
                <span class="leftItem-1">{{ item.name }}</span>
                <!-- <span class="leftItem-2">{{ item.code }}</span> -->
                <!-- <span class="leftItem-2">{{ item.market }}</span> -->
              </button>
            </li>
          </ul>
        </div>
      </div>
      <div v-if="leftHeader">
        <div class="koDiv">
          <p>KOSPI</p>
        </div>
        <div class="noLoginDiv">
          <button
            class="noLogBtn"
            @click="
              itemTest(), itemStockGet(), contentStockPriceGet(), addStockLog()
            "
          >
            <div class="noLoginWrap">
              <div class="noLogLogo">
                <img class="noLoginImg" src="@/assets/stock_logo/005930.png" />
              </div>
              <router-link
                class="noresults-list-router"
                :to="{
                  name: 'StockOne',
                  query: { code: '005930' },
                }"
              >
                <div class="noLogWrap">
                  <div class="cnameWrap">
                    <p>삼성전자</p>
                  </div>
                  <div class="cstockMark"><p>KOSPI</p></div>
                </div>
                <div class="noLogCode"><p>005930</p></div>
              </router-link>
            </div>
          </button>
        </div>
        <div class="noLoginDiv">
          <button
            class="noLogBtn"
            @click="
              itemTest(), itemStockGet(), contentStockPriceGet(), addStockLog()
            "
          >
            <div class="noLoginWrap">
              <div class="noLogLogo">
                <img class="noLoginImg" src="@/assets/stock_logo/034730.png" />
              </div>
              <router-link
                class="noresults-list-router"
                :to="{
                  name: 'StockOne',
                  query: { code: '034730' },
                }"
              >
                <div class="noLogWrap">
                  <div class="cnameWrapTwo">
                    <p>SK</p>
                  </div>
                  <div class="cstockMark"><p>KOSPI</p></div>
                </div>
                <div class="noLogCode"><p>034730</p></div>
              </router-link>
            </div>
          </button>
        </div>
        <div class="noLoginDiv">
          <button
            class="noLogBtn"
            @click="
              itemTest(), itemStockGet(), contentStockPriceGet(), addStockLog()
            "
          >
            <div class="noLoginWrap">
              <div class="noLogLogo">
                <img class="noLoginImg" src="@/assets/stock_logo/068270.png" />
              </div>
              <router-link
                class="noresults-list-router"
                :to="{
                  name: 'StockOne',
                  query: { code: '068270' },
                }"
              >
                <div class="noLogWrap">
                  <div class="cnameWrap">
                    <p>셀트리온</p>
                  </div>
                  <div class="cstockMark"><p>KOSPI</p></div>
                </div>
                <div class="noLogCode"><p>068270</p></div>
              </router-link>
            </div>
          </button>
        </div>
        <div class="koDiv"><p>KOSDAQ</p></div>
        <div class="noLoginDiv">
          <button
            class="noLogBtn"
            @click="
              itemTest(), itemStockGet(), contentStockPriceGet(), addStockLog()
            "
          >
            <div class="noLoginWrap">
              <div class="noLogLogo">
                <img class="noLoginImg" src="@/assets/stock_logo/304100.png" />
              </div>
              <router-link
                class="noresults-list-router"
                :to="{
                  name: 'StockOne',
                  query: { code: '304100' },
                }"
              >
                <div class="noLogWrap">
                  <div class="cnameWrapFour">
                    <p>솔트룩스</p>
                  </div>
                  <div class="cstockMark"><p class="daqColor">KOSDAQ</p></div>
                </div>
                <div class="noLogCode"><p>304100</p></div>
              </router-link>
            </div>
          </button>
        </div>
        <div class="noLoginDiv">
          <button
            class="noLogBtn"
            @click="
              itemTest(), itemStockGet(), contentStockPriceGet(), addStockLog()
            "
          >
            <div class="noLoginWrap">
              <div class="noLogLogo">
                <img class="noLoginImg" src="@/assets/stock_logo/032850.png" />
              </div>
              <router-link
                class="noresults-list-router"
                :to="{
                  name: 'StockOne',
                  query: { code: '032850' },
                }"
              >
                <div class="noLogWrap">
                  <div class="cnameWrapFive">
                    <p>비트컴퓨터</p>
                  </div>
                  <div class="cstockMark"><p class="daqColor">KOSDAQ</p></div>
                </div>
                <div class="noLogCode"><p>032850</p></div>
              </router-link>
            </div>
          </button>
        </div>
        <div class="noLoginDiv">
          <button
            class="noLogBtn"
            @click="
              itemTest(), itemStockGet(), contentStockPriceGet(), addStockLog()
            "
          >
            <div class="noLoginWrap">
              <div class="noLogLogo">
                <img class="noLoginImg" src="@/assets/stock_logo/043610.png" />
              </div>
              <router-link
                class="noresults-list-router"
                :to="{
                  name: 'StockOne',
                  query: { code: '043610' },
                }"
              >
                <div class="noLogWrap">
                  <div class="cnameWrapFour">
                    <p>지니뮤직</p>
                  </div>
                  <div class="cstockMark"><p class="daqColor">KOSDAQ</p></div>
                </div>
                <div class="noLogCode"><p>043610</p></div>
              </router-link>
            </div>
          </button>
        </div>
      </div>
      <!-- 로그인 후 즐겨찾기한 목록 영역-->
      <div v-if="!leftHeader">
        <div class="LoginDiv" v-for="(item, index) in likeList" :key="index">
          <button
            class="noLogBtn"
            @click="
              itemTest(), itemStockGet(), contentStockPriceGet(), addStockLog()
            "
          >
            <div class="noLoginWrap">
              <div class="noLogLogo">
                <img
                  class="noLoginImg"
                  :src="require(`@/assets/stock_logo/${item.code}.png`)"
                />
              </div>
              <router-link
                class="Logresults-list-router"
                :to="{
                  name: 'StockOne',
                  query: { code: item.code },
                }"
              >
                <div class="LogWrap">
                  <div class="LogcnameWrapSix">
                    <p class="LogName">{{ item.name }}</p>
                  </div>
                  <div class="LogincstockMark"></div>
                </div>
                <div class="LogCode">
                  <p>{{ item.code }}</p>
                </div>
              </router-link>
            </div>
          </button>
          <div class="loginCloseBtnDiv">
            <button class="loginCloseBtn" @click="loginBtnClose(item.code)">
              <svg
                width="20"
                height="20"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
                style="display: block"
              >
                <image
                  href="@/assets/svg/close-svgrepo-com.svg"
                  width="20"
                  height="20"
                  fill="red"
                />
              </svg>
            </button>
          </div>
        </div>
      </div>
      <!-- 로그인 후 즐겨찾기한 목록 영역 끝 -->
      <div class="log"></div>
    </div>
  </div>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";
import { useUserStore } from "@/store/user.js";
import { onMounted, ref, watch } from "vue";
export default {
  setup() {
    const store = useStockStore();
    const route = useRoute();
    const router = useRouter();
    const userStore = useUserStore();
    let leftHeader = ref(true);
    let leftInputChange = ref(true);
    let leftInputChangeTwo = ref(false);
    const searchInput = ref("");
    let resultSearch = ref(false);
    let resultData = ref([]);
    let routeTest = ref("");

    let {
      stockCode,
      listCode,
      listStockMarketCap,
      listStockMarketRanking,
      listStockNumberOfStocks,
      listStockForeignerRatio,
      listStockIndustry,
      listStockIndustryDetail,
      listLowYear,
      listHighYear,
      listSummaryInfo,
      stockNameMarket,
      stockName,
      stockMarket,
      contentStockPrice,
      stockPrice,
      stockPriceTwo,
      stockMinus,
      stockVolume,
      modalData,
    } = storeToRefs(store);

    let { isLogin, likeList } = storeToRefs(userStore);

    const onClean = () => {
      searchInput.value = "";
    };

    const loginBtnClose = (code) => {
      axios
        .get("/api/stock/stock-dislike/" + code)
        .then((res) => {
          likeList.value = res.data;
        })
        .catch((error) => {
          console.log(error);
        });
    };

    const blurClose = () => {
      resultSearch.value = false;
      leftInputChangeTwo.value = false;
      leftInputChange.value = true;
    };

    const resultChange = (e) => {
      searchInput.value = e.target.value;
      if (searchInput.value !== "") {
        resultSearch.value = true;
        getKeyWord();
      } else resultSearch.value = false;
    };

    const getKeyWord = () => {
      axios
        .get("/api/stock/stocks/" + searchInput.value)
        .then((data) => {
          resultData.value = data.data;
          console.log(resultData);
        })
        .catch((error) => {
          console.log(error);
        });
    };

    const itemCodeClick = (code) => {
      console.log("코드값을 알아봅시다", code);

      axios
        .get("/api/stock/stock-like/" + code)
        .then((res) => {
          likeList.value = res.data;
        })
        .catch((error) => {
          console.log(error);
        });
    };

    onMounted(() => {
      isLoginChange();
    });

    const noLogModal = () => {
      if (isLogin.value == false) {
        modalData.value = true;
      } else if (isLogin.value == true) {
        leftInputChange.value = false;
        leftInputChangeTwo.value = true;
      }
    };

    watch(isLogin, () => {
      if (isLogin.value == true) {
        leftHeader.value = false;
      } else {
        leftHeader.value = true;
      }
      console.log("isLogin 바뀜");
    });

    const isLoginChange = () => {
      if (isLogin.value == true) {
        leftHeader.value = false;
        console.log("leftHeader 값 바뀜", leftHeader.value);
      } else {
        leftHeader.value = true;
      }
    };

    const itemTest = () => {
      listCode.value = route.query.code;
      axios
        .get("/api/stock/stock-summary/" + listCode.value)
        .then((itemData) => {
          stockCode.value = itemData.data;
          listStockMarketCap.value = stockCode.value.marketCap;
          listStockMarketRanking.value = stockCode.value.marketRanking;
          listStockNumberOfStocks.value = stockCode.value.numberOfStocks;
          listStockForeignerRatio.value = stockCode.value.foreignerRatio;
          listStockIndustry.value = stockCode.value.industry;
          listStockIndustryDetail.value = stockCode.value.industryDetail;
          listLowYear.value = stockCode.value.highYear;
          listHighYear.value = stockCode.value.lowYear;
          listSummaryInfo.value = stockCode.value.summaryInfo;
        })
        .catch((error) => {
          console.log(error);
        });
    };

    const itemStockGet = () => {
      listCode.value = route.query.code;
      axios
        .get("/api/stock/stocks/" + listCode.value)
        .then((itemDataStock) => {
          stockNameMarket.value = itemDataStock.data;
          stockName.value = stockNameMarket.value[0].name;
          stockMarket.value = stockNameMarket.value[0].market;
          console.log(stockNameMarket);
        })
        .catch((error) => {
          console.log(error);
        });
    };

    const contentStockPriceGet = () => {
      listCode.value = route.query.code;
      axios
        .get("/api/stock/stock-price/" + listCode.value)
        .then((itemDataPrice) => {
          contentStockPrice.value = itemDataPrice.data;
          stockPrice.value =
            contentStockPrice.value[contentStockPrice.value.length - 1][4];
          stockPriceTwo.value =
            contentStockPrice.value[contentStockPrice.value.length - 2][4];
          stockVolume.value =
            contentStockPrice.value[contentStockPrice.value.length - 1][5];
          stockMinus.value = stockPrice.value - stockPriceTwo.value;
          console.log(stockMinus);
        })
        .catch((error) => {
          console.log(error);
        });
    };

    const addStockLog = async () => {
      await router.isReady();
      routeTest.value = route.query.code;
      listCode.value = routeTest.value;

      axios
        .get("/api/stock/set-stock-log/" + listCode.value)
        .then()
        .catch(() => {
          console.log("stock log add error");
        });
    };

    return {
      stockCode,
      listCode,
      useRoute,
      useRouter,
      axios,
      router,
      itemTest,
      itemStockGet,
      contentStockPriceGet,
      isLogin,
      leftHeader,
      modalData,
      noLogModal,
      isLoginChange,
      leftInputChange,
      leftInputChangeTwo,
      resultChange,
      onClean,
      resultSearch,
      resultData,
      searchInput,
      blurClose,
      itemCodeClick,
      likeList,
      loginBtnClose,
      addStockLog,
      routeTest,
    };
  },
};
</script>

<style scoped>
@font-face {
  font-family: "Pretendard-Regular";
  src: url("https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff")
    format("woff");
  font-weight: 100;
  font-style: normal;
}

/* 관심목록  */
.stock-list {
  border-bottom: 1px solid #e0e0e0;
  margin-bottom: 10px;
}

/* 종목추가 버튼 div */
.stock-add {
  border-bottom: 1px solid #e0e0e0;
  margin-bottom: 10px;
}

/* 종목추가 버튼 */
.btn-menu {
  border: 0;
  cursor: pointer;
  display: flex;
  align-items: center;
  color: #1c1c1c;
  margin-bottom: 10px;
  background: white;
}

/* 플러스 아이콘  */
.plus-icon {
  margin-right: 6px;
}

/* 종목 추가 글자 */
.btn-text {
  margin-top: 3px;
  font-size: 1rem;
  color: #999999;
  font-family: "Pretendard-Regular";
}

/* 종목 추가 글자 호버 */
.btn-text:hover {
  color: #000000;
}

/* 로그인 한 후 즐겨찾기 이미지 */
.noLoginImg {
  width: 1.6rem;
  height: 1.6rem;
  border-radius: 50%;
  margin-top: 10px;
  margin-right: 10px;
}

/* 즐겨찾기 버튼 */
.noLogBtn {
  border: none;
  background-color: #ffffff;
}

/* 즐겨찾기 버튼 div */
.noLoginWrap {
  width: 100%;
  display: flex;
  align-content: flex-start;
  /* justify-content: space-between; */
  /* text-align: left; */
  cursor: pointer;
  flex-wrap: wrap;
  position: relative;
  top: -5px;
  height: 55px;
  /* align-items: center; */
}

/* 즐겨찾기 로고 div */
.noLogLogo {
  margin: 0px;
  padding: 0px;
}

/* 비로그인 즐겨찾기 목록 */
.noLogWrap {
  display: flex;
  justify-content: space-between;
}

/* 즐겨찾기 삼성전자 */
.cnameWrap {
  display: flex;
  font-size: 1rem;
  margin-right: 41px;
  position: relative;
  top: -10px;
  left: -2px;
  color: #1c1c1c;
  font-family: "Pretendard-Regular";
}

/* 즐겨찾기 sk */
.cnameWrapTwo {
  display: flex;
  font-size: 1rem;
  margin-right: 77px;
  position: relative;
  top: -10px;
  color: #1c1c1c;
  font-family: "Pretendard-Regular";
}

/* 즐겨찾기 솔트룩스 */
.cnameWrapFour {
  display: flex;
  font-size: 1rem;
  margin-right: 27px;
  position: relative;
  top: -10px;
  color: #1c1c1c;
  font-family: "Pretendard-Regular";
}

/* 즐겨찾기 비트컴퓨터 */
.cnameWrapFive {
  display: flex;
  font-size: 1rem;
  margin-right: 13px;
  position: relative;
  top: -10px;
  color: #1c1c1c;
  font-family: "Pretendard-Regular";
}

/* 즐겨찾기 코스피 코스닥 */
.cstockMark {
  display: flex;
  align-items: center;
  color: #ed2926;
  font-family: "Pretendard-Regular";
  margin-left: 1rem;
}

/* 즐겨찾기 종목 코드 */
.noLogCode {
  margin: 0px;
  padding: 0px;
  position: relative;
  top: -37px;
  left: -52px;
  color: #999999;
}

/* 즐겨찾기 코스탁 p  */
.daqColor {
  color: #2679ed;
  font-family: "Pretendard-Regular";
}

/* 즐겨찾기 코스피 코스닥 div */
.koDiv {
  border-bottom: 1px solid #e5e5e5;
  font-size: 0.8rem;
  margin-bottom: 0.5rem;
  color: #525252;
  width: 95%;
}

/* 즐겨찾기 버튼 호버 */
.noLoginDiv:hover {
  border-left: 4px solid #ed2926;
}

/* 로그인 후 즐겨찾기 div */
.LoginDiv {
  height: 3.4rem;
}

/* 로그인 후 즐겨찾기 div 호버 */
.LoginDiv:hover {
  border-left: 4px solid #ed2926;
}

/* 종목추가 input */
.leftInput {
  position: relative;
  width: 12.6rem;
  height: 1rem;
  border-radius: 8px;
}

/* 종목추가 input 결과창 */
.leftSearchResults {
  position: absolute;
  top: 148px;
  border: 2px solid #999999;
  width: 12.9rem;
  border-radius: 8px;
  font-size: 0.4rem;
  max-height: 16vh;
  overflow-y: auto;
  z-index: 1;
  -ms-overflow-style: none;
  background-color: #ffffff;
}

/* 종목추가 input 결과창 none 스크롤 */
.leftSearchResults::-webkit-scrollbar {
  display: none;
}

/* 종목추가 input 결과창 버튼 */
.leftResultsBtn {
  border: none;
  background-color: #ffffff;
  cursor: pointer;
}

/* 종목추가 input li  */
.resultsListTwo {
  margin-bottom: 7px;
  list-style: none;
  display: flex;
}

/* 종목추가 input 결과창 버튼 호버 */
.leftResultsBtn:hover {
  border-left: 4px solid #ed2926;
}

/* 즐겨찾기 div */
.LogcnameWrapSix {
  font-size: 1rem;
  position: relative;
  top: -10px;
  color: #1c1c1c;
  width: 5.5rem;
  text-align: left;
  font-family: "Pretendard-Regular";
}

/* 비로그인 즐겨찾기 라우터 */
.noresults-list-router {
  text-decoration: none;
}

/* 로그인 즐겨찾기 라우터 */
.Logresults-list-router {
  text-decoration: none;
  width: 12rem;
  height: 4rem;
  position: relative;
  top: -35px;
  left: 32px;
}

/* 즐겨찾기 종목 코드 */
.LogCode {
  position: relative;
  top: -25px;
  left: -73px;
  color: #999999;
}

/* 즐겨찾기 중간 div */
.LogincstockMark {
  position: relative;
  top: -30px;
  left: 40px;
}

/* 즐겨찾기 삭제 버튼 */
.loginCloseBtn {
  border: none;
  background-color: #ffffff;
  cursor: pointer;
}
/* 즐겨찾기 삭제 버튼 div */
.loginCloseBtnDiv {
  position: relative;
  left: 170px;
  top: -43px;
}

/* 즐겨찾기 삭제 버튼 호버 */
.loginCloseBtn:hover {
  filter: invert(16%) sepia(89%) saturate(10%) hue-rotate(358deg)
    brightness(97%) contrast(113%);
}
</style>
