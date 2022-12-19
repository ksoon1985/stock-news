<template>
  <div class="sub-tab">
    <router-link
      class="router-tab"
      exact-active-class="exact-active-link"
      :to="{
        name: 'StockOne',
        query: {
          code: listCode,
        },
      }"
      ><button class="btn-close">
        <span class="sub-title" id="sub-title">종목요약</span>
      </button></router-link
    >
    <router-link
      class="router-tab"
      exact-active-class="exact-active-link"
      :to="{
        name: 'StockTwo',
        query: {
          code: listCode,
        },
      }"
      ><button class="btn-close">
        <span class="sub-title" id="sub-title" @click="informationClick"
          >재무정보</span
        >
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
import axios from "axios";

export default {
  setup() {
    const store = useStockStore();

    let { stockCode, listCode, stockInformationData } = storeToRefs(store);

    const informationClick = () => {
      axios
        .get("/api/stock/stock-finance/a/" + listCode.value)
        .then((informationData) => {
          stockInformationData.value = informationData.data;
        });
    };

    return {
      listCode,
      stockCode,
      informationClick,
      stockInformationData,
    };
  },
};
</script>

<style></style>
