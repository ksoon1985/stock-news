<template>
  <div class="sub-view-result">
    <div class="header-contents">
      <div class="header-logo">
        <img :src="noImage(listCode)" class="one-logo-image" />
      </div>
      <div class="stock-one-info-wrap">
        <h3>{{ stockName }}</h3>
      </div>
      <div class="stock-one-info">
        <span class="stock-market">{{ stockMarket }}</span>
        <span class="stock-code">{{ listCode }}</span>
      </div>
    </div>
    <div class="information-contents">
      <div class="one-div">
        <div class="block-div">
          <p class="one-title">시가총액</p>
          <p class="one-contents">{{ listStockMarketCap }}</p>
        </div>
        <div class="block-div">
          <p class="one-title">기업순위</p>
          <p class="one-contents">{{ listStockMarketRanking }}</p>
        </div>
      </div>
      <div class="one-div">
        <div class="block-div">
          <p class="one-title">주식수</p>
          <p class="one-contents">{{ listStockNumberOfStocks }}</p>
        </div>
        <div class="block-div">
          <p class="one-title">외국인비중</p>
          <p class="one-contents">{{ listStockForeignerRatio }}</p>
        </div>
      </div>
      <div class="one-div">
        <div class="block-div">
          <p class="one-title">산업군</p>
          <p class="one-contents">{{ listStockIndustry }}</p>
        </div>
        <div class="block-div">
          <p class="one-title">세부산업군</p>
          <p class="one-contents">{{ listStockIndustryDetail }}</p>
        </div>
      </div>
      <div class="one-div">
        <div class="block-div">
          <p class="one-title">52주 최저</p>
          <p class="one-contents-blue">{{ listLowYear }}</p>
        </div>
        <div class="block-div">
          <p class="one-title">52주 최고</p>
          <p class="one-contents-red">{{ listHighYear }}</p>
        </div>
      </div>
    </div>
    <div class="company-description">
      <h2 class="description-title">기업개요</h2>
      <div class="description-for">
        <ul>
          <li
            class="description-li"
            v-for="(item, index) in listSummaryInfo"
            :key="index"
          >
            <p>{{ item }}</p>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
import { ref } from "vue";

export default {
  setup() {
    const store = useStockStore();
    const logoOff = ref(false);

    let {
      stockName,
      stockMarket,
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
    // const logoChange = () => {
    //   if (listCode.value !== null) {
    //     logoOff.value = true;
    //   }
    // };

    return {
      stockName,
      stockMarket,
      listCode,
      listStockMarketRanking,
      listStockMarketCap,
      listStockNumberOfStocks,
      listStockForeignerRatio,
      listStockIndustry,
      listStockIndustryDetail,
      listLowYear,
      listHighYear,
      listSummaryInfo,
      logoOff,
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

.header-contents {
  width: 100%;
  border-bottom: 1px solid #e0e0e0;
  display: flex;
  align-items: center;
  margin-bottom: 1.6rem;
}

/* 종목요약 전체 div */
.header-logo {
  width: 4rem;
  height: 4rem;
  border-radius: 50%;
  margin-left: 0.8rem;
  margin-top: 0.9rem;
}

/* 종목요약  로고 */
.one-logo-image {
  width: 3rem;
  height: 3rem;
  border-radius: 50%;
}

/* 종목요약 헤더 종목정보 */
.stock-one-info-wrap {
  position: relative;
  margin-bottom: 18px;
  font-size: 1rem;
  margin: 0;
  padding: 0;
  font-family: "Pretendard-Regular";
}

/* 헤더 코스피 코더 div */
.stock-one-info {
  left: 84.9rem;
  bottom: 46rem;
  font-family: "Pretendard-Regular";
}

/* 종목정보 */
.information-contents {
  border-bottom: 1px solid #e0e0e0;
  display: flex;
  flex-wrap: wrap;
  font-family: "Pretendard-Regular";
  /* margin-left: 7px; */
}

/* 종목정보 세부 div wrap */
.one-div {
  display: flex;
  width: 100%;
  margin-bottom: 10px;
  margin-left: 10px;
}

/* 종목정보 세부 div */
.block-div {
  display: block;
  width: 100%;
}

/* 기업개요 */
.company-description {
  margin-top: 35px;
  margin-left: 7px;
  font-family: "Pretendard-Regular";
}

/* 52주 최저 */
.one-contents-blue {
  color: #2679ed;
}

/* 52주 최고 */
.one-contents-red {
  color: #ed2926;
}

/* 재무정보 데이터 */
.description-li {
  list-style: none;
  margin-right: 50px;
}

/* 부제목 */
.one-title {
  color: #999999;
}
</style>
