<template>
  <div class="sub-tab">
    <router-link
      class="router-tab"
      exact-active-class="exact-active-link"
      :to="{
        name: 'NewsPast',
        query: {
          code: listCode,
        },
      }"
      ><button class="btn-close">
        <span class="sub-title" id="sub-title">토픽뉴스</span>
      </button></router-link
    >
    <router-link
      class="router-tab"
      exact-active-class="exact-active-link"
      :to="{
        name: 'NewsNow',
        query: {
          code: listCode,
        },
      }"
      ><button class="btn-close">
        <span class="sub-title" id="sub-title">키워드뉴스</span>
      </button></router-link
    >
    <router-link
      class="router-tab"
      exact-active-class="exact-active-link"
      :to="{
        name: 'NewsTime',
        query: {
          code: listCode,
        },
      }"
      ><button class="btn-close">
        <span class="sub-title" id="sub-title">최신뉴스</span>
      </button></router-link
    >
  </div>
  <div class="main-view">
    <Router-view class="router-view"></Router-view>
  </div>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";
import { ref, onMounted, watch } from "vue";
export default {
  setup() {
    const store = useStockStore();
    const route = useRoute();
    const stockData = ref(null);
    const router = useRouter();
    let routeTest = ref("");

    let { listCode, keyWordList, realTimeData } = storeToRefs(store);

    return {
      listCode,
      useRouter,
      keyWordList,
      stockData,
      watch,
      axios,
      onMounted,
      route,
      router,
      routeTest,
      realTimeData,
    };
  },
};
</script>

<style>
/* 메뉴 라우터  */
.router-tab {
  margin-top: 5px;
}

/* 메뉴 span */
.sub-title {
  font-size: 1.3rem;
  font-family: "Poppins", sans-serif;
  color: #999999;
}

/* 라우터 뷰 전체 */
.router-view {
  overflow-y: auto;
  overflow-x: hidden;
  max-height: 80vh;
}
</style>
