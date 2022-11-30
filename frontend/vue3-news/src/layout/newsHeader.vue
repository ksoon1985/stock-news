<template>
  <div class="logo">logo</div>
  <div class="search-wrap">
    <div class="main-search">
      <div class="header-empty">
        <div class="input-wrap">
          <!-- <div class="search-icon">
            <svg
              width="24"
              height="24"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
              style="display: block"
            >
              <image
                href="@/assets/svg/magnifying-glass-svgrepo-com.svg"
                width="24"
                height="24"
              />
            </svg>
          </div> -->
          <input
            @change="onClean"
            ref="inputTitle"
            class="search-input"
            type="text"
            spellcheck="false"
            placeholder="                    종목명 또는 코드를 입력해주세요"
            :value="searchInput"
            @input="resultChange"
            @keyup.enter="resultChange"
          />
        </div>
        <button type="button" class="btn-close" @click="onClean">
          <div class="close-icon">
            <svg
              width="24"
              height="24"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
              style="display: block"
              class="btn-icon"
            >
              <image
                href="@/assets/svg/x-svgrepo-com.svg"
                width="24"
                height="24"
                @click="onFalse"
              />
            </svg>
          </div>
        </button>
      </div>
      <div class="search-results" v-if="resultSearch">
        <ul class="ul-list">
          <li
            class="results-list"
            v-for="(item, index) in resultData"
            :key="index"
            ref="listData"
            @click="
              itemTest(), itemStockGet(), contentStockPriceGet(), onFalse()
            "
          >
            <router-link
              class="results-list-router"
              :to="{
                name: 'StockOne',
                query: {
                  code: item.code,
                },
              }"
            >
              <span class="item-1">{{ item.name }}</span>
              <span class="item-2">{{ item.code }}</span>
              <span class="item-2">{{ item.market }}</span>
            </router-link>
          </li>
        </ul>
      </div>
    </div>
  </div>
  <teleport to="#teleport-area"
    ><div class="modal" v-if="modalData">
      <div class="modalForm" v-if="modalFormData">
        <div class="modalLogo">
          logo
          <button type="button" class="btn-close" @click="modalChanges">
            <div class="close-icon">
              <svg
                width="24"
                height="24"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
                style="display: block"
                class="btn-icon"
              >
                <image
                  href="@/assets/svg/x-svgrepo-com.svg"
                  width="24"
                  height="24"
                />
              </svg>
            </div>
          </button>
        </div>
        <div class="modalWrap">
          <button @click="modalLoginChange">로그인</button
          ><button @click="modalJoinChange">회원가입</button>
        </div>
        <div class="signin">
          <form @submit.prevent="modalLoginSubmit">
            <div class="id">
              <input
                type="email"
                name="email"
                id="username"
                class="modalEmail"
                v-model="modalLoginEmail"
              />
              <label class="label">이메일</label>
            </div>
            <div class="password">
              <input
                type="password"
                name="password"
                id="password"
                class="modalPassword"
                v-model="modalLoginPassword"
              />
              <label class="label">비밀번호</label>
            </div>
            <button type="submit" class="modalLogin">로그인</button>
          </form>
        </div>
      </div>
      <div class="modalJoin" v-if="modalJoinData">
        <div class="modalLogoClose">
          <div class="modalLogo">logo</div>
          <div class="modalClose">
            <button type="button" class="btn-close" @click="modalChanges">
              <div class="close-icon">
                <svg
                  width="24"
                  height="24"
                  fill="none"
                  xmlns="http://www.w3.org/2000/svg"
                  style="display: block"
                  class="btn-icon"
                >
                  <image
                    href="@/assets/svg/x-svgrepo-com.svg"
                    width="24"
                    height="24"
                  />
                </svg>
              </div>
            </button>
          </div>
        </div>
        <div class="modalWrap">
          <button @click="modalLoginChange">로그인</button
          ><button @click="modalJoinChange">회원가입</button>
        </div>
        <div class="join">
          <form @submit.prevent="modalJoinSubmit">
            <div class="input-box">
              <input
                type="email"
                name="email"
                id="email"
                class="modalJoinEmail"
                v-model="modalJoinEmail"
              />
              <label class="label">이메일</label>
            </div>
            <div class="input-box">
              <input
                type="text"
                name="nickName"
                id="nickName"
                class="modalJoinNickName"
                v-model="modalJoinNickName"
              />
              <label class="label">닉네임</label>
            </div>
            <div class="input-box">
              <input
                type="password"
                name="password"
                id="password"
                class="modalJoinPassword"
                v-model="modalJoinPassword"
              />
              <label class="label">비밀번호</label>
            </div>
            <div class="input-box">
              <input
                type="password"
                name="password"
                class="modalJoinPassword"
              />
              <label class="label">비밀번호 확인</label>
            </div>
            <div class="gender">
              남성
              <input
                type="radio"
                name="gender"
                value="M"
                v-model="modalJoinGender"
              />
              여성
              <input
                type="radio"
                name="gender"
                value="F"
                v-model="modalJoinGender"
              />
            </div>
            <div class="birthday">
              <label class="label">생년월일</label>
              <input type="date" id="birthDay" v-model="modalJoinbirthDay" />
            </div>
            <button type="submit" class="modalJoinBtn">회원가입</button>
          </form>
        </div>
      </div>
    </div></teleport
  >
  <div class="support-menu">
    <button type="button" class="btn-login" @click="modalChange">로그인</button>
  </div>
</template>

<script>
import { ref } from "vue";
import axios from "axios";
import { useRoute } from "vue-router";
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";

export default {
  setup() {
    let resultSearch = ref(false);
    const searchInput = ref("");
    let resultData = ref([]);
    // let stockData = ref([]);
    const route = useRoute();
    const store = useStockStore();
    let modalData = ref(false);
    let modalFormData = ref(true);
    let modalJoinData = ref(false);
    let modalJoinEmail = ref("");
    let modalJoinNickName = ref("");
    let modalJoinPassword = ref("");
    let modalJoinGender = ref("");
    let modalJoinbirthDay = ref("");
    let modalLoginEmail = ref("");
    let modalLoginPassword = ref("");

    let {
      stockName,
      stockCode,
      stockMarket,
      listCode,
      stockNameMarket,
      contentStockPrice,
      stockPrice,
      stockVolume,
      listStockMarketCap,
      listStockMarketRanking,
      listStockNumberOfStocks,
      listStockForeignerRatio,
      listStockIndustry,
      listStockIndustryDetail,
      listLowYear,
      listHighYear,
      listSummaryInfo,
    } = storeToRefs(store);

    let sendEventBus = () => {
      sendEventBus.$emit("name", resultData);
    };
    const onClean = () => {
      searchInput.value = "";
    };

    const onFalse = () => {
      resultSearch.value = false;
    };

    const modalChange = () => {
      modalData.value = true;
    };

    const modalChanges = () => {
      modalData.value = false;
      console.log("실행");
    };

    const modalLoginChange = () => {
      modalFormData.value = true;
      modalJoinData.value = false;
    };

    const modalJoinChange = () => {
      modalFormData.value = false;
      modalJoinData.value = true;
    };

    const modalJoinSubmit = () => {
      console.log(
        modalJoinEmail.value,
        modalJoinNickName.value,
        modalJoinPassword.value,
        modalJoinGender.value,
        modalJoinbirthDay.value,
      );
      const url = "http://192.168.0.36:8089/api/member/signUp";
      let joinData = {
        email: modalJoinEmail.value,
        nickName: modalJoinNickName.value,
        password: modalJoinPassword.value,
        gender: modalJoinGender.value,
        birthDay: modalJoinbirthDay.value,
      };
      axios
        .post(url, joinData)
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    };

    const modalLoginSubmit = () => {
      console.log(modalLoginEmail.value, modalLoginPassword.value);
      const loginUrl = "http://192.168.0.36:8089/api/member/login";
      let loginData = {
        username: modalLoginEmail.value,
        password: modalLoginPassword.value,
      };
      axios
        .post(loginUrl, loginData)
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    };

    const getKeyWord = () => {
      axios
        .get("http://192.168.0.36:8089/api/stock/stocks/" + searchInput.value)
        .then((data) => {
          // console.log(data);
          resultData.value = data.data;
          console.log(resultData);
        });
    };

    const resultChange = (e) => {
      searchInput.value = e.target.value;
      if (searchInput.value !== "") {
        resultSearch.value = true;
        getKeyWord();
      } else resultSearch.value = false;
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
          console.log(stockCode);
          console.log(listSummaryInfo);
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
          stockVolume.value =
            contentStockPrice.value[contentStockPrice.value.length - 1][5];
          console.log(contentStockPrice);
          console.log(stockPrice);
        });
    };

    return {
      onClean,
      searchInput,
      resultSearch,
      resultChange,
      getKeyWord,
      resultData,
      sendEventBus,
      itemTest,
      store,
      stockName,
      stockCode,
      stockMarket,
      listCode,
      listStockMarketCap,
      itemStockGet,
      stockNameMarket,
      contentStockPrice,
      stockPrice,
      stockVolume,
      contentStockPriceGet,
      listStockMarketRanking,
      listStockNumberOfStocks,
      listStockForeignerRatio,
      listStockIndustry,
      listStockIndustryDetail,
      listLowYear,
      listHighYear,
      listSummaryInfo,
      onFalse,
      modalData,
      modalChange,
      modalChanges,
      modalFormData,
      modalJoinData,
      modalLoginChange,
      modalJoinChange,
      modalJoinEmail,
      modalJoinNickName,
      modalJoinPassword,
      modalJoinGender,
      modalJoinbirthDay,
      modalJoinSubmit,
      modalLoginEmail,
      modalLoginPassword,
      modalLoginSubmit,
    };
  },
};
</script>

<style>
/* 헤더 메인 */
.main-search {
  overflow: hidden;
  border-radius: 8px;
  background-color: #ffffff;
  margin-bottom: 3px;
}

/* 헤더 비트윈 설정  */
.header-empty {
  display: flex;
  -webkit-box-pack: justify;
  justify-content: space-between;
  -webkit-box-align: center;
  align-items: center;
}

/* 검색창 div */
.input-wrap {
  border-radius: 8px;
  display: flex;
  -webkit-box-pack: justify;
  justify-content: space-between;
  -webkit-box-align: center;
  align-items: center;
  background-color: #ffffff;
  position: relative;
}

/* 검색창  */
.search-input {
  width: 27rem;
  height: 2rem;
  font-size: 1rem;
  line-height: 2rem;
  background: transparent;
  border-radius: 8px;
}

/* X 버튼  */
.btn-close {
  border: 0;
  cursor: pointer;
  background: white;
}

.btn-icon:hover {
  filter: invert(16%) sepia(89%) saturate(6054%) hue-rotate(358deg)
    brightness(97%) contrast(113%);
}

/* 로그인 버튼  */
.btn-login {
  font-size: 1rem;
  border: 1px solid #d01411;
  color: #d01411;
  cursor: pointer;
  border-radius: 5px;
  background-color: white;
}

.btn-login:hover {
  background-color: #e5e5e5;
}

/* 검색 결과창 */
.search-results {
  position: absolute;
  top: 40px;
  border: 2px solid #999999;
  width: 27.3rem;
  border-radius: 8px;
  font-size: 1.2rem;
  max-height: 16vh;
  overflow-y: auto;
  z-index: 1;
  -ms-overflow-style: none;
  background-color: #ffffff;
}
/* 검색 결과창 스크롤 */
.search-results::-webkit-scrollbar {
  display: none;
}

/* 검색 결과창 리스트 */
.results-list {
  display: flex;
  list-style: none;
  margin-bottom: 7px;
}

/* 검색 결과창 Ul */
.ul-list {
  text-decoration: none;
}

/* 검색 결과창 종목이름 */
.item-1 {
  font-size: 1.2rem;
}

/* 검색 결과창 종목코드 마켓 */
.item-2 {
  font-size: 0.9rem;
  margin-left: 5px;
  margin-top: 5px;
  color: #999999;
  text-align: right;
}

/* 검색 결과창 라우터 */
.results-list-router {
  text-decoration: none;
  color: black;
}

.header-logo-image {
  width: 3rem;
  height: 3rem;
  border-radius: 50%;
  margin-top: 10px;
  margin-right: 10px;
}

/* 모달창 */
.modal {
  position: fixed;
  background: rgba(0, 0, 0, 0.5);
  color: #fff;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
}

/* 모달 로그인 */
.modalForm {
  position: absolute;
  width: 408px;
  height: 30rem;
  border: 1px solid #e5e5e5;
  border-radius: 4px;
  background-color: #ffffff;
  color: red;
}

/* 모달 회원가입 */
.modalJoin {
  position: absolute;
  width: 420px;
  height: 40rem;
  border: 1px solid #e5e5e5;
  border-radius: 4px;
  background-color: #ffffff;
  color: red;
}

.modalLogoClose {
  display: flex;
  justify-content: center;
  margin: 25px;
}

.modalLoginChange {
  width: 18rem;
  line-height: 2.4rem;
  font-size: 1.6rem;
  font-weight: 500;
  color: #999999;
}

.modalJoinChange {
}

.input-box {
  position: relative;
  margin: 20px 20px;
}

.input-box > input {
  background: transparent;
  border: none;
  border-bottom: solid 1px #ccc;
  padding: 20px 0px 5px 0px;
  font-size: 14pt;
  width: 90%;
}

.input::placeholder {
  color: transparent;
}

input:placeholder-shown + label {
  color: #aaa;
  font-size: 14pt;
  top: 15px;
}

input:focus + label,
label {
  color: #8aa1a1;
  font-size: 10pt;
  pointer-events: none;
  position: absolute;
  left: 0px;
  top: 0px;
  transition: all 0.2s ease;
  -webkit-transition: all 0.2s ease;
  -moz-transition: all 0.2s ease;
  -o-transition: all 0.2s ease;
}

input:focus,
input:not(:placeholder-shown) {
  border-bottom: solid 1px #8aa1a1;
  outline: none;
}
</style>
