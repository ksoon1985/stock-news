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

    let {
      stockCode,
      listCode,
      stockInformationData,
      stockInformationDataTwo,
      stockInformationDataThree,
      stockInformationDataFour,
    } = storeToRefs(store);

    const informationClick = () => {
      axios
        .get("/api/stock/stock-finance/a/" + listCode.value)
        .then((informationData) => {
          //console.log(informationData.data[0].keys);
          console.log(stockInformationData);
          stockInformationData.value = informationData.data[0];
          stockInformationDataTwo.value = informationData.data[1];
          stockInformationDataThree.value = informationData.data[2];
          stockInformationDataFour.value = informationData.data[3];
        });
    };

    return {
      listCode,
      stockCode,
      informationClick,
    };
  },
};
</script>

<style></style>
