<template>
  <div class="logo">STOCKNEWS</div>
  <div class="search-wrap">
    <div class="main-search">
      <div class="header-empty">
        <div class="input-wrap">
          <input
            @change="onClean"
            ref="inputTitle"
            class="search-input"
            type="text"
            spellcheck="false"
            placeholder="                    종목명 또는 코드를 입력해주세요"
            :value="searchInput"
            @input="resultChange"
            @keyup.enter="resultChange()"
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
              itemTest(),
                itemStockGet(),
                contentStockPriceGet(),
                onFalse(),
                addStockLog()
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
              <div class="result-stock-logo">
                <img :src="noImage(item.code)" class="result-logo-image" />

                <span class="item-1">{{ item.name }}</span>
                <span class="item-2">{{ item.code }}</span>
                <span class="item-2">{{ item.market }}</span>
              </div>
            </router-link>
          </li>
        </ul>
      </div>
    </div>
  </div>
  <teleport to="#teleport-area"
    ><div class="modal" v-if="modalData">
      <div class="modalForm" v-if="modalFormData">
        <div class="modalLogoClose">
          <div class="modalLogo">
            <h5 class="modal-logo-h5">STOCKNEWS</h5>
            <button type="button" class="btn-close-one" @click="modalChanges">
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
          <button
            @click="modalLoginChange"
            :class="[modalFormData ? 'done' : '']"
            class="modalBtn"
          >
            로그인</button
          ><button
            @click="modalJoinChange"
            :class="[modalJoinData ? 'done' : '']"
            class="modalBtn"
          >
            회원가입
          </button>
        </div>
        <div class="signin">
          <form @submit.prevent="modalLoginSubmit">
            <div class="input-box">
              <input
                type="email"
                name="email"
                id="username"
                class="modalEmail"
                v-model="modalLoginEmail"
              />
              <label class="label">이메일</label>
            </div>
            <div class="input-box">
              <input
                type="password"
                name="password"
                id="password"
                class="modalPassword"
                v-model="modalLoginPassword"
                maxlength="16"
              />
              <label class="label">비밀번호</label>
              <span class="loginFalseSpan" v-if="loginFalseSpan">
                <br />로그인에 실패하였습니다. <br />
                이메일과 비밀번호를 다시 확인해주세요.</span
              >
            </div>
            <button
              type="submit"
              class="modalLogin"
              :disabled="!modalLoginEmail || !modalLoginPassword"
            >
              로그인
            </button>
          </form>
        </div>
      </div>
      <div class="modalJoin" v-if="modalJoinData">
        <div class="modalLogoClose">
          <div class="modalLogo"><h5 class="modal-join-h5">STOCKNEWS</h5></div>
          <div class="modalClose">
            <button type="button" class="btn-close-two" @click="modalChanges">
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
          <button
            @click="modalLoginChange"
            :class="[modalFormData ? 'done' : '']"
            class="modalBtn"
          >
            로그인</button
          ><button
            @click="modalJoinChange"
            :class="[modalJoinData ? 'done' : '']"
            class="modalBtn"
          >
            회원가입
          </button>
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
              <div class="joinDiv">
                <span v-if="modalEmailCheck" class="passChk"
                  >이메일을 확인해주세요.</span
                >
                <span v-if="modalchkEamilFalse" class="passChk"
                  >동일한 이메일이 존재합니다.</span
                >
                <span v-if="modalchkEmailTrue" class="chkEmailTrue"
                  >사용가능한 이메일입니다.</span
                >
              </div>
            </div>
            <div class="input-box">
              <input
                type="text"
                name="nickName"
                id="nickName"
                class="modalJoinNickName"
                :value="nickInput"
                @input="modalNickChange"
                maxlength="15"
              />
              <label class="label">닉네임</label>
              <div class="joinDiv">
                <span v-if="modalchkNickFalse" class="passChk"
                  >동일한 닉네임이 존재합니다.</span
                >
                <span v-if="modalchkNickTrue" class="chkEmailTrue"
                  >사용가능한 닉네임입니다.
                </span>
              </div>
            </div>
            <div class="input-box">
              <input
                type="password"
                name="password"
                id="password"
                class="modalJoinPassword"
                v-model="modalJoinPassword"
                maxlength="16"
              />
              <label class="label">비밀번호</label>
              <div class="joinDiv"></div>
            </div>
            <div class="input-box">
              <input
                type="password"
                name="password"
                class="modalJoinPassword"
                v-model="modalJoinPasswordTwo"
                maxlength="16"
              />
              <label class="label">비밀번호 확인</label>
              <div class="joinDiv">
                <span v-if="modalPasswordCheck" class="passChk"
                  >비밀번호가 서로 일치하지 않습니다.</span
                >
              </div>
            </div>
            <div class="genderBirthday">
              <span class="genderBirthSpan">성별</span>
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
              <span class="genderBirthSpan">생년/월/일</span>
              <div class="birthday">
                <input
                  class="modalBirtDate"
                  type="date"
                  id="birthDay"
                  v-model="modalJoinbirthDay"
                />
              </div>
            </div>
            <button
              type="submit"
              class="modalJoinBtn"
              :disabled="
                !modalJoinPassword ||
                !modalJoinPasswordTwo ||
                !resNickCheck ||
                !modalJoinGender
              "
            >
              회원가입
            </button>
          </form>
        </div>
      </div>
      <div class="joinComplate" v-if="joinComChk">
        <div class="comSpan">
          <span>가입이 완료 되었습니다. 서비스를 이용해 보세요.</span>
        </div>
        <button class="complateBtn" @click="modalChanges">확인</button>
      </div>
    </div>
    <div class="modal" v-if="modalUserIcon">
      <div class="modalUser">
        <div class="UserProfill"><h3>프로필</h3></div>
        <div class="userIconClose">
          <button class="userIconBtn" @click="modalUserIcon = false">
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
          </button>
        </div>
        <div class="userNickNameWrap">
          <p class="userNick">닉네임</p>
          <div class="UserNickName">{{ nickName }}</div>
        </div>
        <div class="userMy">
          <p class="userso">내소개</p>
          <div class="userMyText">
            <textarea class="userMyTextarea"></textarea>
          </div>
        </div>
        <div class="userBtnWrap">
          <div class="userBtn"><button class="userBtnSave">저장</button></div>
          <div class="userDelBtn">
            <button class="userBtnDel">회원탈퇴</button>
          </div>
        </div>
      </div>
    </div>
  </teleport>

  <div class="support-menu">
    <button :hidden="!isLogin" class="hiddenBtn" @click="modalUserIcon = true">
      <svg
        width="30"
        height="30"
        xmlns="http://www.w3.org/2000/svg"
        style="display: block"
      >
        <image
          id="icon"
          class="icon"
          href="@/assets/svg/people-svgrepo-comTwo.svg"
          width="30"
          height="30"
        />
      </svg>
    </button>

    <button
      :hidden="isLogin"
      type="button"
      class="btn-login"
      @click="modalChange"
    >
      로그인
    </button>

    <button
      :hidden="!isLogin"
      type="button"
      class="btn-login-two"
      @click="modalLogoutSubmit"
    >
      로그아웃
    </button>
  </div>
</template>

<script>
import { onMounted, ref, watch } from "vue";
import axios from "axios";
import { useRoute, useRouter } from "vue-router";
import { useStockStore } from "@/store/Stock.js";
import { useUserStore } from "@/store/user.js";
import { storeToRefs } from "pinia";

export default {
  setup() {
    let resultSearch = ref(false);
    const searchInput = ref("");
    let resultData = ref([]);
    const route = useRoute();
    const router = useRouter();
    const store = useStockStore();
    const userStore = useUserStore();
    let modalFormData = ref(true);
    let modalJoinData = ref(false);
    let modalJoinEmail = ref("");
    let modalJoinNickName = ref("");
    let modalJoinPassword = ref("");
    let modalJoinGender = ref("");
    let modalJoinbirthDay = ref("1995-01-01");
    let modalLoginEmail = ref("");
    let modalLoginPassword = ref("");
    let modalJoinPasswordTwo = ref("");
    let modalPasswordCheck = ref(null);
    let modalchkEmailTrue = ref(false);
    let modalchkEamilFalse = ref(false);
    let modalEmailCheck = ref(false);
    let resEmailCheck = ref(null);
    let resNickCheck = ref(null);
    let modalchkNickTrue = ref(false);
    let modalchkNickFalse = ref(false);
    let nickInput = ref("");
    let joinComChk = ref(false);
    let routeTest = ref("");
    let modalUserIcon = ref(false);
    let loginFalseSpan = ref(false);

    let { tempCode, isLogin, nickName, likeList } = storeToRefs(userStore);
    let setTimeoutHandler = ref(null);

    let {
      stockName,
      stockCode,
      stockMarket,
      listCode,
      stockNameMarket,
      contentStockPrice,
      stockPrice,
      stockPriceTwo,
      stockMinus,
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
      modalData,
    } = storeToRefs(store);

    const noImage = (code) => {
      let defaultImage = require(`@/assets/stock_logo/000000.png`);
      let path = require(`@/assets/stock_logo/${code}.png`);
      try {
        return path;
      } catch (e) {
        return defaultImage;
      }
    };

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
      joinComChk.value = false;
    };

    const modalChanges = () => {
      modalData.value = false;
    };

    const modalLoginChange = () => {
      modalFormData.value = true;
      modalJoinData.value = false;
    };

    const modalJoinChange = () => {
      modalFormData.value = false;
      modalJoinData.value = true;
    };

    const modalNickChange = (e) => {
      nickInput.value = e.target.value;
    };

    onMounted(async () => {
      await router.isReady();
      routeTest.value = route.query.code;
      listCode.value = routeTest.value;
      itemTest(), itemStockGet(), contentStockPriceGet();
    });

    watch(modalJoinPasswordTwo, () => {
      if (modalJoinPasswordTwo.value == "") {
        modalPasswordCheck.value = false;
      } else if (modalJoinPassword.value !== modalJoinPasswordTwo.value) {
        modalPasswordCheck.value = true;
      } else if (modalJoinPassword.value === modalJoinPasswordTwo.value) {
        modalPasswordCheck.value = false;
      }
    });

    watch(modalJoinEmail, () => {
      if (modalJoinEmail.value == "") {
        modalEmailCheck.value = false;
      }
    });

    watch(modalJoinEmail, () => {
      if (modalJoinEmail.value.length < 13) {
        modalEmailCheck.value = true;
        modalchkEmailTrue.value = false;
      } else if (
        resEmailCheck.value == true ||
        modalJoinEmail.value.length > 10
      ) {
        modalEmailCheck.value = false;
        modalchkEmailTrue.value = true;
      }
    });

    watch(resEmailCheck, () => {
      if (resEmailCheck.value == false) {
        modalEmailCheck.value = false;
        modalchkEmailTrue.value = false;
        modalchkEamilFalse.value = true;
      } else {
        modalchkEamilFalse.value = false;
      }
    });

    watch(modalJoinEmail, () => {
      axios
        .get("/api/member/chkEmail/" + modalJoinEmail.value)
        .then((emailchk) => {
          resEmailCheck.value = emailchk.data;
        })
        .catch((error) => {
          console.error(error);
        });
    });

    watch(nickInput, () => {
      if (nickInput.value == "") {
        modalchkNickTrue.value = false;
        modalchkNickFalse.value = false;
      } else {
        modalchkNickTrue.value = true;
      }
    });

    watch(resNickCheck, () => {
      if (resNickCheck.value == false) {
        modalchkNickFalse.value = true;
        modalchkNickTrue.value = false;
      } else if (resNickCheck.value == true) {
        modalchkNickFalse.value = false;
        modalchkNickTrue.value = true;
      }
    });

    watch(nickInput, () => {
      axios
        .get("/api/member/chkNickName/" + nickInput.value)
        .then((nickchk) => {
          resNickCheck.value = nickchk.data;
        })
        .catch((error) => {
          console.error(error);
        });
    });

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

    const modalJoinSubmit = () => {
      const url = "/api/member/signUp";
      let joinData = {
        email: modalJoinEmail.value,
        nickName: nickInput.value,
        password: modalJoinPassword.value,
        gender: modalJoinGender.value,
        birthDay: modalJoinbirthDay.value,
      };
      axios
        .post(url, joinData)
        .then((response) => {
          console.log(response);
          modalFormData.value = false;
          modalJoinData.value = false;
          joinComChk.value = true;
        })
        .catch((error) => {
          console.log(error);
        });
    };

    const modalLoginSubmit = () => {
      console.log(modalLoginEmail.value, modalLoginPassword.value);
      const loginUrl = "/api/member/login";
      let loginData = {
        username: modalLoginEmail.value,
        password: modalLoginPassword.value,
      };
      axios
        .post(loginUrl, loginData)
        .then((res) => {
          if (res.status === 200) {
            localStorage.setItem("isLogin", true);
            localStorage.setItem("nickName", res.data.nickName);
            isLogin.value = true;
            nickName.value = res.data.nickName;

            modalData.value = false;

            axios.get("/api/stock/stocks/likes").then((res) => {
              likeList.value = res.data;

              setTimeoutHandler.value = setTimeout(() => {
                modalLogoutSubmit();
                window.location.reload();
              }, 5 * 60 * 60 * 1000); //5 시간 후 jwt 만료시간에 맞게 로그아웃 처리

              window.location.reload();
            });
          } else {
            console.log(res.status);
          }
        })
        .catch((err) => {
          console.log(err);
          loginFalseSpan.value = true;
        });
    };

    const modalLogoutSubmit = () => {
      axios
        .get("/api/member/logout")
        .then((res) => {
          console.log(res);
          localStorage.removeItem("isLogin");
          localStorage.removeItem("nickName");
          isLogin.value = false;
          nickName.value = "";

          likeList.value = [];

          clearTimeout(setTimeoutHandler.value);

          window.location.reload();
        })
        .catch((err) => {
          console.log(err);
        });
    };

    const getKeyWord = () => {
      axios.get("/api/stock/stocks/" + searchInput.value).then((data) => {
        resultData.value = data.data;
      });
    };

    const resultChange = (e) => {
      searchInput.value = e.target.value;
      if (searchInput.value !== "") {
        resultSearch.value = true;
        setTimeout(() => {
          getKeyWord();
        }, 500);
      } else resultSearch.value = false;
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
        });
    };

    const itemStockGet = () => {
      listCode.value = route.query.code;
      axios.get("/api/stock/stocks/" + listCode.value).then((itemDataStock) => {
        stockNameMarket.value = itemDataStock.data;
        stockName.value = stockNameMarket.value[0].name;
        stockMarket.value = stockNameMarket.value[0].market;
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
      stockPriceTwo,
      stockMinus,
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
      modalJoinPasswordTwo,
      modalJoinGender,
      modalJoinbirthDay,
      modalJoinSubmit,
      modalLoginEmail,
      modalLoginPassword,
      modalLoginSubmit,
      modalPasswordCheck,
      modalchkEmailTrue,
      modalchkEamilFalse,
      modalEmailCheck,
      resEmailCheck,
      resNickCheck,
      modalchkNickTrue,
      modalchkNickFalse,
      modalNickChange,
      nickInput,
      joinComChk,
      isLogin,
      nickName,
      modalLogoutSubmit,
      route,
      routeTest,
      tempCode,
      router,
      modalUserIcon,
      setTimeoutHandler,
      loginFalseSpan,
      addStockLog,
      noImage,
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

.logo {
  font-family: "Pretendard-Regular";
  color: #d01411;
}

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

/* X 버튼 호버 */
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

/* 로그인 버튼 호버 */
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
  width: 330px;
}

.results-list:hover {
  border-bottom: 2px solid #d2d2d2;
}

/* 검색 결과창 Ul */
.ul-list {
  text-decoration: none;
}

/* 검색 결과창 종목이름 */
.item-1 {
  font-size: 1.2rem;
  width: 200px;
  height: 5px;
  margin-top: 2px;
  display: block;
}

/* 검색 결과창 종목코드 마켓 */
.item-2 {
  font-size: 0.9rem;
  margin-left: 5px;
  margin-top: 5px;
  color: #999999;
  position: relative;
}

/* 검색 결과창 라우터 */
.results-list-router {
  text-decoration: none;
  color: black;
}

.results-list-router:hover {
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
  width: 420px;
  height: 35rem;
  border: 1px solid #e5e5e5;
  border-radius: 4px;
  background-color: #ffffff;
  color: red;
}

/* 모달 회원가입 */
.modalJoin {
  position: absolute;
  width: 420px;
  height: 46rem;
  border: 1px solid #e5e5e5;
  border-radius: 4px;
  background-color: #ffffff;
  color: red;
}

/* 모달창 로고  */
.modalLogoClose {
  display: flex;
  justify-content: center;
  margin: 25px;
  font-size: 1.5rem;
}

/* 로그인 회원가입 버튼 */
.modalBtn {
  width: 13rem;
  line-height: 2.4rem;
  font-size: 1.1rem;
  font-weight: 500;
  color: #999999;
  border: none;
  border-bottom: 2px solid #e5e5e5;
  background: #ffffff;
}

/* 로그인 회원가입 버튼 호버 */
.modalBtn:hover {
  color: #d01411;
}

/* 로그인 회원가입 활성화 (삼항연산자) */
.done {
  width: 13rem;
  line-height: 2.4rem;
  font-size: 1.1rem;
  font-weight: 500;
  color: #d01411;
  border: none;
  border-bottom: 2px solid #d01411;
  background: #ffffff;
}

/* 성별 체크박스  */
.gender {
  color: #8aa1a1;
  margin-right: 40px;
  margin-bottom: 30px;
}

/* 성별 생년월일 div  */
.genderBirthday {
  display: flex;
  justify-content: center;
  margin-top: 5px;
}

/* 모달 회원가입 버튼 */
.modalJoinBtn {
  background: #d01411;
  width: 23rem;
  height: 3rem;
  border-radius: 4px;
  border: 1px solid #ffffff;
  color: #ffffff;
  margin-left: 1.6rem;
  cursor: pointer;
  font-family: "Pretendard-Regular";
}

.modalJoinBtn:disabled {
  background: #f4b7b7;
  width: 23rem;
  height: 3rem;
  border-radius: 4px;
  border: 1px solid #ffffff;
  color: #ffffff;
  margin-left: 1.6rem;
  cursor: auto;
}

/* 모달 로그인 버튼 */
.modalLogin {
  background: #d01411;
  width: 23rem;
  height: 3rem;
  border-radius: 4px;
  border: 1px solid #ffffff;
  color: #ffffff;
  margin-left: 1.6rem;
  cursor: pointer;
  font-family: "Pretendard-Regular";
}

/* 모달 로그인 버튼 disabled  */
.modalLogin:disabled {
  background: #f4b7b7;
  width: 23rem;
  height: 3rem;
  border-radius: 4px;
  border: 1px solid #ffffff;
  color: #ffffff;
  margin-left: 1.6rem;
  cursor: auto;
}

/* input div */
.input-box {
  position: relative;
  margin: 40px 20px;
  text-align: center;
}

/* input div input 상속 */
.input-box > input {
  background: transparent;
  border: none;
  border-bottom: solid 1px #ccc;
  padding: 20px 0px 5px 0px;
  font-size: 14pt;
  width: 90%;
}

/* 모달창 placeholder  */
.input::placeholder {
  color: transparent;
}

/* 모달창 input */
input:placeholder-shown + label {
  color: #aaa;
  font-size: 14pt;
  top: 15px;
}

/* 모달창 input label  */
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
  font-family: "Pretendard-Regular";
}

/* 모달창 input focus  */
input:focus,
input:not(:placeholder-shown) {
  /* border-bottom: solid 1px #8aa1a1; */
  outline: none;
}

/* 회원가입 유효성 문구 */
.joinDiv {
  font-size: 6px;
  width: 380px;
  height: 10px;
}

/* 회원가입 유효성 문구 true */
.chkEmailTrue {
  color: #2679ed;
}

/* 회원가입 유효성 문구 false */
.passChk {
  color: #e07900;
}

/* 회원가입 완료 모달창 */
.joinComplate {
  position: absolute;
  width: 420px;
  height: 8rem;
  border: 1px solid #e5e5e5;
  border-radius: 8px;
  background-color: #ffffff;
  color: #1c1c1c;
}

/* 회원가입 완료 문단 */
.comSpan {
  margin-top: 23px;
  margin-left: 63px;
  font-family: "Pretendard-Regular";
}

/* 회원가입 완료 확인 버튼 */
.complateBtn {
  background: #d01411;
  color: #fef6f6;
  border-radius: 4px;
  cursor: pointer;
  border: none;
  width: 7.8rem;
  height: 2rem;
  margin-top: 30px;
  margin-left: 154px;
  font-size: 1.1rem;
  font-family: "Pretendard-Regular";
}

/* 모달 로고  */
.modalLogo {
  display: flex;
}

/* 모달 닫기 버튼  */
.btn-close-one {
  position: relative;
  top: -10px;
  left: 110px;
  border: none;
  cursor: pointer;
  background: white;
  height: 2rem;
}

/* 로그인 모달 로그 h5태그  */
.modal-logo-h5 {
  position: relative;
  left: 22px;
  font-family: "Pretendard-Regular";
  color: #d01411;
}

/* 회원가입 모달 닫기 버튼 */
.btn-close-two {
  position: relative;
  top: -10px;
  left: 110px;
  border: none;
  cursor: pointer;
  background: white;
  height: 2rem;
}

/* 회원가입 모달 로그 h5 태그 */
.modal-join-h5 {
  position: relative;
  left: 19px;
  font-family: "Pretendard-Regular";
  color: #d01411;
}

/* 로그인 후 회원 아이콘 */
.hiddenBtn {
  border: none;
  background-color: #ffffff;
  position: relative;
  left: -1px;
  top: 2px;
  cursor: pointer;
}

/* 로그아웃 버튼 */
.btn-login-two {
  font-size: 1rem;
  border: 1px solid #d01411;
  color: #d01411;
  cursor: pointer;
  border-radius: 5px;
  background-color: white;
  position: relative;
  top: -7px;
}

/* 로그아웃 버튼 호버 */
.btn-login-two:hover {
  background-color: #e5e5e5;
}

/* 회원 아이콘 div */
.support-menu {
  position: relative;
  left: 1px;
}

/* 회원 상세보기 모달 */
.modalUser {
  width: 30%;
  height: 40%;
  background: white;
  border-radius: 8px;
  padding: 20px;
  font-family: "Pretendard-Regular";
}

/* 회원 상세보기 프로필 */
.UserProfill {
  color: #1c1c1c;
}

/* 회원 상세보기 닉네임 */
.userNick {
  font-size: 0.9rem;
}

/* 회원 상세보기 닉네임 */
.UserNickName {
  color: #1c1c1c;
}

/* 유저 아이콘 버튼 */
.userIconBtn {
  border: none;
  background-color: white;
  cursor: pointer;
}

/* 유저 상세보기 닫기 */
.userIconClose {
  position: relative;
  top: -3rem;
  margin-left: 33rem;
}

/* 유저 닉네임 div */
.userNickNameWrap {
  color: #1c1c1c;
  position: relative;
  top: -2rem;
}

/* 유저 소개 */
.userso {
  font-size: 0.9rem;
}

/* 유저 소개 div */
.userMy {
  color: #1c1c1c;
}

/* 유저 소개 텍스트 */
.userMyTextarea {
  border: 1px solid #e5e5e5;
  border-radius: 4px;
  overflow-y: auto;
  padding: 1.2rem;
  width: 93%;
  height: 3rem;
}

/* 유저 중요 버튼 div */
.userBtnWrap {
  display: flex;
  justify-content: flex-end;
  margin-top: 3rem;
}

/* 유저 소개 세이브 버튼 */
.userBtnSave {
  font-size: 1rem;
  border: 1px solid #d01411;
  color: #fef6f6;
  cursor: pointer;
  border-radius: 5px;
  background-color: #d01411;
  width: 7rem;
  height: 2rem;
  margin-right: 5px;
  font-family: "Pretendard-Regular";
}

/* 유저 탈퇴 버튼 */
.userBtnDel {
  font-size: 1rem;
  border: 1px solid #d01411;
  color: #fef6f6;
  cursor: pointer;
  border-radius: 5px;
  background-color: #d01411;
  width: 7rem;
  height: 2rem;
  margin-right: 5px;
  font-family: "Pretendard-Regular";
}

/* 로그인 실패 span */
.loginFalseSpan {
  font-size: 0.8rem;
}

/* 결과 로고 */
.result-stock-logo {
  display: flex;
}

/* 결과 로고 이미지 */
.result-logo-image {
  width: 2rem;
  height: 2rem;
  border-radius: 50%;
  margin-right: 10px;
  margin-bottom: 5px;
}

/* 회원 가입 성별 생년월일 span */
.genderBirthSpan {
  color: #8aa1a1;
  font-size: 0.8rem;
  display: block;
  position: relative;
  top: -20px;
  left: -5px;
}

/* 회원가입 생년월일  */
.genderBirthday {
  margin-right: 8px;
}
</style>
