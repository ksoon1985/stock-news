<template>
  <div class="sub-view-result">
    <div class="iframeHeader">
      <h3>기간별 뉴스 키워드</h3>
      <div class="headerDate">
        <input class="iframeDate" type="date" v-model="fromDate" />
        <span class="data-span">~</span>
        <input class="iframeDate" type="date" v-model="toDate" />
      </div>
    </div>
    <div class="iframeBtnDiv">
      <button class="alignBtn" @click="iframeBtnEventOne">
        <div class="align-icon">
          <svg
            width="24"
            height="24"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
            style="display: block"
            class="btn-icon"
          >
            <image
              href="@/assets/svg/align-justify-svgrepo-com.svg"
              width="24"
              height="24"
              :class="[iframeAll ? 'doneAlign' : '']"
            />
          </svg>
        </div>
        <span class="ifraneSpan" :class="[iframeAll ? 'doneSpan' : '']"
          >전체</span
        ></button
      ><button class="alignBtn" @click="iframeBtnEventTwo">
        <div class="handshake-icon">
          <svg
            width="24"
            height="24"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
            style="display: block"
            class="btn-icon"
          >
            <image
              href="@/assets/svg/handshake-svgrepo-com.svg"
              width="24"
              height="24"
              :class="[iframeTwo ? 'doneAlign' : '']"
            />
          </svg>
        </div>
        <span class="ifraneSpan" :class="[iframeTwo ? 'doneSpan' : '']"
          >정치</span
        >
      </button>
      <button class="alignBtn" @click="iframeBtnEventThree">
        <div class="economy-icon">
          <svg
            width="24"
            height="24"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
            style="display: block"
            class="btn-icon"
          >
            <image
              href="@/assets/svg/economy-svgrepo-com.svg"
              width="24"
              height="24"
              :class="[iframeThree ? 'doneAlign' : '']"
            />
          </svg>
        </div>
        <span class="ifraneSpan" :class="[iframeThree ? 'doneSpan' : '']"
          >경제</span
        >
      </button>
      <button class="alignBtn" @click="iframeBtnEventFour">
        <div class="edges-icon">
          <svg
            width="24"
            height="24"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
            style="display: block"
            class="btn-icon"
          >
            <image
              href="@/assets/svg/edges-svgrepo-com.svg"
              width="24"
              height="24"
              :class="[iframeFour ? 'doneAlign' : '']"
            />
          </svg>
        </div>
        <span class="ifraneSpan" :class="[iframeFour ? 'doneSpan' : '']"
          >사회</span
        >
      </button>
      <button class="alignBtn">
        <div class="world-icon" @click="iframeBtnEventFive">
          <svg
            width="24"
            height="24"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
            style="display: block"
            class="btn-icon"
          >
            <image
              href="@/assets/svg/world-svgrepo-com.svg"
              width="24"
              height="24"
              :class="[iframeFive ? 'doneAlign' : '']"
            />
          </svg>
        </div>
        <span class="ifraneSpan" :class="[iframeFive ? 'doneSpan' : '']"
          >국제</span
        >
      </button>
      <button class="alignBtn" @click="iframeBtnEventSix">
        <div class="computer-icon">
          <svg
            width="24"
            height="24"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
            style="display: block"
            class="btn-icon"
          >
            <image
              href="@/assets/svg/computer-svgrepo-com.svg"
              width="24"
              height="24"
              :class="[iframeSix ? 'doneAlign' : '']"
            />
          </svg>
        </div>
        <span class="ifraneSpan" :class="[iframeSix ? 'doneSpan' : '']"
          >정보</span
        >
      </button>
    </div>
    <div class="iframeDiv">
      <iframe
        v-if="iframeAll"
        :src="iframeAllEvent()"
        height="400"
        width="610"
        class="word-cloud"
        frameborder="0"
      ></iframe>
      <iframe
        v-if="iframeTwo"
        :src="iframeTwoEvent()"
        height="400"
        width="610"
        frameborder="0"
      ></iframe>
      <iframe
        v-if="iframeThree"
        :src="iframeThreeEvent()"
        height="400"
        width="610"
        frameborder="0"
      ></iframe>
      <iframe
        v-if="iframeFour"
        :src="iframeFourEvent()"
        height="400"
        width="610"
        frameborder="0"
      ></iframe>
      <iframe
        v-if="iframeFive"
        :src="iframeFiveEvent()"
        height="400"
        width="610"
        frameborder="0"
      ></iframe>
      <iframe
        v-if="iframeSix"
        :src="iframeSixEvent()"
        height="400"
        width="610"
        frameborder="0"
      ></iframe>
    </div>
  </div>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
import { onMounted, ref } from "vue-demi";
export default {
  setup() {
    const store = useStockStore();

    let { listCode } = storeToRefs(store);

    let iframeAll = ref(true);
    let iframeTwo = ref(false);
    let iframeThree = ref(false);
    let iframeFour = ref(false);
    let iframeFive = ref(false);
    let iframeSix = ref(false);
    let fromDate = ref("2019-12-30");
    let toDate = ref("2022-12-06");

    const iframeBtnEventOne = () => {
      iframeAll.value = true;
      iframeTwo.value = false;
      iframeThree.value = false;
      iframeFour.value = false;
      iframeFive.value = false;
      iframeSix.value = false;
    };

    const iframeBtnEventTwo = () => {
      iframeAll.value = false;
      iframeTwo.value = true;
      iframeThree.value = false;
      iframeFour.value = false;
      iframeFive.value = false;
      iframeSix.value = false;
    };

    const iframeBtnEventThree = () => {
      iframeAll.value = false;
      iframeTwo.value = false;
      iframeThree.value = true;
      iframeFour.value = false;
      iframeFive.value = false;
      iframeSix.value = false;
    };

    const iframeBtnEventFour = () => {
      iframeAll.value = false;
      iframeTwo.value = false;
      iframeThree.value = false;
      iframeFour.value = true;
      iframeFive.value = false;
      iframeSix.value = false;
    };

    const iframeBtnEventFive = () => {
      iframeAll.value = false;
      iframeTwo.value = false;
      iframeThree.value = false;
      iframeFour.value = false;
      iframeFive.value = true;
      iframeSix.value = false;
    };

    const iframeBtnEventSix = () => {
      iframeAll.value = false;
      iframeTwo.value = false;
      iframeThree.value = false;
      iframeFour.value = false;
      iframeFive.value = false;
      iframeSix.value = true;
    };

    // 전체
    const iframeAllEvent = () => {
      let path = `http://49.247.22.59:5601/app/visualize#/edit/6be03bc0-881d-11ed-bb89-cd5d8c15a218?embed=true&_g=(filters%3A!()%2CrefreshInterval%3A(pause%3A!t%2Cvalue%3A0)%2Ctime%3A(from%3A'${fromDate.value}'%2Cto%3A'${toDate.value}'))`;
      return path;
    };

    // 정치
    const iframeTwoEvent = () => {
      let path = `http://49.247.22.59:5601/app/visualize#/edit/14ddff20-8a60-11ed-bb89-cd5d8c15a218?embed=true&_g=(filters%3A!()%2CrefreshInterval%3A(pause%3A!t%2Cvalue%3A0)%2Ctime%3A(from%3A'${fromDate.value}'%2Cto%3A'${toDate.value}'))`;
      return path;
    };

    // 경제
    const iframeThreeEvent = () => {
      let path = `http://49.247.22.59:5601/app/visualize#/edit/026c8f80-8a71-11ed-b0de-718e92fc7f2c?embed=true&_g=(filters%3A!()%2CrefreshInterval%3A(pause%3A!t%2Cvalue%3A0)%2Ctime%3A(from%3A'${fromDate.value}'%2Cto%3A'${toDate.value}'))`;
      return path;
    };

    // 사회
    const iframeFourEvent = () => {
      let path = `http://49.247.22.59:5601/app/visualize#/edit/e26f5330-8a65-11ed-bb89-cd5d8c15a218?embed=true&_g=(filters%3A!()%2CrefreshInterval%3A(pause%3A!t%2Cvalue%3A0)%2Ctime%3A(from%3A'${fromDate.value}'%2Cto%3A'${toDate.value}'))`;
      return path;
    };

    // 국제
    const iframeFiveEvent = () => {
      let path = `http://49.247.22.59:5601/app/visualize#/edit/f6f64ba0-8a70-11ed-b0de-718e92fc7f2c?embed=true&_g=(filters%3A!()%2CrefreshInterval%3A(pause%3A!t%2Cvalue%3A0)%2Ctime%3A(from%3A'${fromDate.value}'%2Cto%3A'${toDate.value}'))`;
      return path;
    };

    const iframeSixEvent = () => {
      let path = `http://49.247.22.59:5601/app/visualize#/edit/0f990ee0-8a71-11ed-b0de-718e92fc7f2c?embed=true&_g=(filters%3A!()%2CrefreshInterval%3A(pause%3A!t%2Cvalue%3A0)%2Ctime%3A(from%3A'${fromDate.value}'%2Cto%3A'${toDate.value}'))`;
      return path;
    };

    return {
      listCode,
      iframeAll,
      iframeTwo,
      iframeThree,
      iframeFour,
      iframeFive,
      iframeSix,
      iframeBtnEventOne,
      iframeBtnEventTwo,
      iframeBtnEventThree,
      iframeBtnEventFour,
      iframeBtnEventFive,
      iframeBtnEventSix,
      fromDate,
      toDate,
      onMounted,
      iframeAllEvent,
      iframeTwoEvent,
      iframeThreeEvent,
      iframeFourEvent,
      iframeFiveEvent,
      iframeSixEvent,
    };
  },
};
</script>

<style scoped>
@font-face {
  font-family: "Pretendard-Regular";
  src: url("https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff")
    format("woff");
  font-weight: 500;
  font-style: normal;
}

/* 워드클라우드 헤더 */
.iframeHeader {
  margin-left: 15px;
  margin-bottom: 30px;
  display: flex;
  border-bottom: 1px solid #e0e0e0;
  width: 93%;
}

/* 워드클라우드 div */
.iframeDiv {
  margin-left: 7px;
}

/* 워드클라우드 메뉴 div */
.iframeBtnDiv {
  margin-bottom: 15px;
}

/* 워드클라우드 메뉴 버튼 */
.alignBtn {
  border: none;
  background-color: #ffffff;
  cursor: pointer;
}

/* 워드 클라우드 메뉴 span */
.ifraneSpan {
  font-family: "Pretendard-Regular";
}

/* 워드 클라우드 버튼 div */
.iframeBtnDiv {
  display: flex;
  justify-content: space-around;
}

/* 워드 클라우드 활성화 */
.doneAlign {
  border: none;
  background-color: #ffffff;
  filter: invert(16%) sepia(89%) saturate(6054%) hue-rotate(358deg)
    brightness(97%) contrast(113%);
}

/* 워드 클라우드 활성화 span */
.doneSpan {
  color: red;
}

/* 워드 클라우드 기간 div */
.headerDate {
  position: relative;
  left: 13rem;
  top: 1.7rem;
}

/* 워드 클라우드 기간 span */
.data-span {
  font-family: "Pretendard-Regular";
  margin-right: 3px;
  margin-left: 3px;
  font-size: 0.8rem;
}

/* 워드 클라우드 기간  */
.iframeDate {
  border: none;
  color: #335cad;
}

/* 워드 클라우드 기간 버튼 */
.iframedateBtn {
  margin-top: 10px;
  text-align: center;
}

/* 종목별 키워드 통계 헤더 */
.stockKeyWordHeader {
  margin-left: 15px;
  margin-bottom: 30px;
  display: flex;
  border-bottom: 1px solid #e0e0e0;
  width: 93%;
}
</style>
