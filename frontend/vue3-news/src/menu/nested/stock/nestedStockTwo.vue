<template>
  <div class="sub-sub-tab">
    <div>
      <span class="stock-two-title">{{ stockName }}</span>
    </div>
    <div class="two-router-tab">
      <router-link
        class="router-tab"
        exact-active-class="exact-active-link"
        :to="{
          name: 'StockTwoOne',
          query: {
            code: listCode,
          },
        }"
        ><button class="btn-close">
          <span class="sub-title" id="sub-title">연간</span>
        </button></router-link
      >
      <router-link
        class="router-tab"
        exact-active-class="exact-active-link"
        :to="{
          name: 'StockTwoTwo',
          query: {
            code: listCode,
          },
        }"
        ><button class="btn-close" @click="informationTwoClick">
          <span class="sub-title" id="sub-title">분기</span>
        </button></router-link
      >
    </div>
  </div>
  <div class="main-view">
    <Router-view class="router-view"></Router-view>
  </div>
</template>
<script>
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
import axios from "axios";
import { onMounted } from "vue";

export default {
  setup() {
    const store = useStockStore();

    let { listCode, stockName, stockInformationDataQyarter } =
      storeToRefs(store);

    onMounted(() => {
      informationTwoClick();
    });

    const informationTwoClick = () => {
      axios
        .get("/api/stock/stock-finance/q/" + listCode.value)
        .then((QyarterData) => {
          stockInformationDataQyarter.value = QyarterData.data;
        });
    };

    return {
      listCode,
      stockName,
      informationTwoClick,
      stockInformationDataQyarter,
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

/* 재무정보 라우터 */
.sub-sub-tab {
  display: flex;
  margin-top: 10px;
  justify-content: space-between;
}

/* 재무정보 제목 */
.stock-two-title {
  margin: 15px;
  font-size: 1.3rem;
  font-family: "Pretendard-Regular";
}

/* 재무정보 라우터 */
.two-router-tab {
  margin-right: 20px;
  margin-top: 10px;
}
</style>
