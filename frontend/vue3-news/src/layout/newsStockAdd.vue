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
            @click="itemTest(), itemStockGet(), contentStockPriceGet()"
          >
            <div class="noLoginWrap">
              <div class="noLogLogo">
                <img class="noLoginImg" src="@/assets/stock_logo/005930.png" />
              </div>
              <router-link
                class="results-list-router"
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
            @click="itemTest(), itemStockGet(), contentStockPriceGet()"
          >
            <div class="noLoginWrap">
              <div class="noLogLogo">
                <img class="noLoginImg" src="@/assets/stock_logo/035720.png" />
              </div>
              <router-link
                class="results-list-router"
                :to="{
                  name: 'StockOne',
                  query: { code: '035720' },
                }"
              >
                <div class="noLogWrap">
                  <div class="cnameWrapTwo">
                    <p>카카오</p>
                  </div>
                  <div class="cstockMark"><p>KOSPI</p></div>
                </div>
                <div class="noLogCode"><p>035720</p></div>
              </router-link>
            </div>
          </button>
        </div>
        <div class="noLoginDiv">
          <button
            class="noLogBtn"
            @click="itemTest(), itemStockGet(), contentStockPriceGet()"
          >
            <div class="noLoginWrap">
              <div class="noLogLogo">
                <img class="noLoginImg" src="@/assets/stock_logo/011200.png" />
              </div>
              <router-link
                class="results-list-router"
                :to="{
                  name: 'StockOne',
                  query: { code: '011200' },
                }"
              >
                <div class="noLogWrap">
                  <div class="cnameWrapThree">
                    <p>HMM</p>
                  </div>
                  <div class="cstockMark"><p>KOSPI</p></div>
                </div>
                <div class="noLogCode"><p>011200</p></div>
              </router-link>
            </div>
          </button>
        </div>
        <div class="koDiv"><p>KOSDAQ</p></div>
        <div class="noLoginDiv">
          <button
            class="noLogBtn"
            @click="itemTest(), itemStockGet(), contentStockPriceGet()"
          >
            <div class="noLoginWrap">
              <div class="noLogLogo">
                <img class="noLoginImg" src="@/assets/stock_logo/304100.png" />
              </div>
              <router-link
                class="results-list-router"
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
            @click="itemTest(), itemStockGet(), contentStockPriceGet()"
          >
            <div class="noLoginWrap">
              <div class="noLogLogo">
                <img class="noLoginImg" src="@/assets/stock_logo/032850.png" />
              </div>
              <router-link
                class="results-list-router"
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
            @click="itemTest(), itemStockGet(), contentStockPriceGet()"
          >
            <div class="noLoginWrap">
              <div class="noLogLogo">
                <img class="noLoginImg" src="@/assets/stock_logo/250060.png" />
              </div>
              <router-link
                class="results-list-router"
                :to="{
                  name: 'StockOne',
                  query: { code: '250060' },
                }"
              >
                <div class="noLogWrap">
                  <div class="cnameWrapSix">
                    <p>모비스</p>
                  </div>
                  <div class="cstockMark"><p class="daqColor">KOSDAQ</p></div>
                </div>
                <div class="noLogCode"><p>250060</p></div>
              </router-link>
            </div>
          </button>
        </div>
      </div>
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

    let { isLogin } = storeToRefs(userStore);

    const onClean = () => {
      searchInput.value = "";
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
      axios.get("/api/stock/stocks/" + searchInput.value).then((data) => {
        resultData.value = data.data;
        console.log(resultData);
      });
    };

    const itemCodeClick = (code) => {
      console.log("코드값을 알아봅시다", code);
      // axios
      //   .get("http://192.168.0.36:8089/api/stock/stock-like/" + code)
      //   .then(() => {});
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
        .get(
          "http://192.168.0.36:8089/api/stock/stock-summary/" + listCode.value,
        )
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
        });
    };

    const itemStockGet = () => {
      listCode.value = route.query.code;
      axios
        .get("http://192.168.0.36:8089/api/stock/stocks/" + listCode.value)
        .then((itemDataStock) => {
          stockNameMarket.value = itemDataStock.data;
          stockName.value = stockNameMarket.value[0].name;
          stockMarket.value = stockNameMarket.value[0].market;
          console.log(stockNameMarket);
        });
    };

    const contentStockPriceGet = () => {
      listCode.value = route.query.code;
      axios
        .get("http://192.168.0.36:8089/api/stock/stock-price/" + listCode.value)
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
    };
  },
};
</script>

<style>
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
  font-size: 1rem;
  color: #999999;
}

/* 종목 추가 글자 호버 */
.btn-text:hover {
  color: #000000;
}

.noLoginImg {
  width: 1.6rem;
  height: 1.6rem;
  border-radius: 50%;
  margin-top: 10px;
  margin-right: 10px;
}

.noLogBtn {
  border: none;
  background-color: #ffffff;
}

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

.noLogLogo {
  margin: 0px;
  padding: 0px;
}

.noLogWrap {
  display: flex;
  justify-content: space-between;
}

.cstockMark {
  margin-left: 1rem;
}

.cnameWrap {
  display: flex;
  font-size: 1rem;
  margin-right: 31px;
  position: relative;
  top: -10px;
  color: #1c1c1c;
}

.cnameWrapTwo {
  display: flex;
  font-size: 1rem;
  margin-right: 46px;
  position: relative;
  top: -10px;
  color: #1c1c1c;
}

.cnameWrapThree {
  display: flex;
  font-size: 1rem;
  margin-right: 56px;
  position: relative;
  top: -10px;
  color: #1c1c1c;
}

.cnameWrapFour {
  display: flex;
  font-size: 1rem;
  margin-right: 18px;
  position: relative;
  top: -10px;
  color: #1c1c1c;
}

.cnameWrapFive {
  display: flex;
  font-size: 1rem;
  margin-right: 2px;
  position: relative;
  top: -10px;
  color: #1c1c1c;
}

.cnameWrapSix {
  display: flex;
  font-size: 1rem;
  margin-right: 34px;
  position: relative;
  top: -10px;
  color: #1c1c1c;
}

.cstockMark {
  display: flex;
  align-items: center;
  color: #ed2926;
}

.noLogCode {
  margin: 0px;
  padding: 0px;
  position: relative;
  top: -37px;
  left: -52px;
  color: #999999;
}

.daqColor {
  color: #2679ed;
}

.koDiv {
  border-bottom: 1px solid #e5e5e5;
  font-size: 0.8rem;
  margin-bottom: 0.5rem;
  color: #525252;
  width: 95%;
}

.noLoginDiv:hover {
  border-left: 4px solid #ed2926;
}

.leftInput {
  position: relative;
  width: 12.6rem;
  height: 1rem;
  border-radius: 8px;
}

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

.leftSearchResults::-webkit-scrollbar {
  display: none;
}

.leftItem-1 {
}

.leftResultsBtn {
  border: none;
  background-color: #ffffff;
  cursor: pointer;
}

.resultsListTwo {
  margin-bottom: 7px;
  list-style: none;
  display: flex;
}

.leftResultsBtn:hover {
  border-left: 4px solid #ed2926;
}
</style>
