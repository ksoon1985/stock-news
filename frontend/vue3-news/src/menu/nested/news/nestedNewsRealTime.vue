<template>
  <div class="sub-view-result">
    <div class="realTimeWrap">
      <div class="realTimeHeader">
        <span class="realtime-stock-name">{{ stockName }}</span>
        <span class="realtime-keyword">카테고리 뉴스</span>
      </div>
      <div class="realTimeBtnDiv">
        <router-link
          class="router-tab"
          :to="{
            name: 'realTimeAll',
            query: {
              code: listCode,
            },
          }"
        >
          <button class="realTimeBtn"><span id="title">전체</span></button>
        </router-link>

        <router-link
          class="router-tab"
          :to="{
            name: 'realTimeOne',
            query: {
              code: listCode,
              sd1: 100,
            },
          }"
        >
          <button class="realTimeBtn"><span id="title">정치</span></button>
        </router-link>
        <router-link
          class="router-tab"
          :to="{
            name: 'realTimeTwo',
            query: {
              code: listCode,
              sd1: 101,
            },
          }"
        >
          <button class="realTimeBtn"><span id="title">경제</span></button>
        </router-link>
        <router-link
          class="router-tab"
          :to="{
            name: 'realTimeThree',
            query: {
              code: listCode,
              sd1: 102,
            },
          }"
        >
          <button class="realTimeBtn"><span id="title">사회</span></button>
        </router-link>
        <router-link
          class="router-tab"
          :to="{
            name: 'realTimeFour',
            query: {
              code: listCode,
              sd1: 103,
            },
          }"
        >
          <button class="realTimeBtn"><span id="title">문화</span></button>
        </router-link>
        <router-link
          class="router-tab"
          :to="{
            name: 'realTimeFive',
            query: {
              code: listCode,
              sd1: 104,
            },
          }"
        >
          <button class="realTimeBtn"><span id="title">세계</span></button>
        </router-link>
        <router-link
          class="router-tab"
          :to="{
            name: 'realTimeSix',
            query: {
              code: listCode,
              sd1: 105,
            },
          }"
        >
          <button class="realTimeBtn"><span id="title">IT/과학</span></button>
        </router-link>
      </div>
    </div>
    <div class="main-view">
      <Router-view class="router-view"></Router-view>
    </div>
  </div>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
import { onMounted } from "vue-demi";

export default {
  setup() {
    const store = useStockStore();

    let { listCode, stockName } = storeToRefs(store);

    return {
      listCode,
      stockName,
      onMounted,
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

/* 실시간 뉴스 헤더 */
.realTimeHeader {
  display: flex;
  font-family: "Pretendard-Regular";
  margin-left: 15px;
  margin-bottom: 10px;
}

/* 실시간 뉴스 종목 이름 */
.realtime-stock-name {
  font-size: 1.2rem;
  margin-top: 10px;
}

/* 실시간 뉴스 span */
.realtime-keyword {
  margin-left: 5px;
  margin-top: 15px;
  color: #999999;
}

/* 실시간 뉴스 카테고리 버튼 */
.realTimeBtn {
  border: none;
  background-color: #ffffff;
  font-family: "Pretendard-Regular";
  font-size: 1rem;
  cursor: pointer;
}

/* 실시간 뉴스 카테고리 div */
.realTimeBtnDiv {
  display: flex;
  justify-content: space-around;
  margin-bottom: 20px;
}

/* 실시간 뉴스 전체  */
.realTimeWrap {
  position: sticky;
  top: 0px;
  z-index: 10;
  background-color: #ffffff;
  height: 90px;
}
</style>
