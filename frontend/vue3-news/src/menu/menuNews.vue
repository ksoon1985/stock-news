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
      ><button class="btn-close" @click="keyWordClickEvent">
        <span class="sub-title" id="sub-title">키워드뉴스</span>
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
import { ref } from "vue";
export default {
  setup() {
    const store = useStockStore();
    const route = useRoute();
    const stockData = ref(null);

    let { listCode, keyWordList } = storeToRefs(store);

    const keyWordClickEvent = () => {
      listCode.value = route.query.code;
      axios.get("/api/stock/stock-summary/" + listCode.value).then((res) => {
        stockData.value = res.data;
        keyWordList.value = stockData.value.themeKeywords;
      });
    };

    return {
      listCode,
      useRouter,
      keyWordClickEvent,
      keyWordList,
      stockData,
    };
  },
};
</script>

<style>
.router-tab {
  margin-top: 5px;
}

.sub-title {
  font-size: 1.3rem;
  font-family: "Poppins", sans-serif;
  color: #999999;
}

.router-view {
  overflow-y: scroll;
  overflow-x: hidden;
  max-height: 80vh;
}
</style>
