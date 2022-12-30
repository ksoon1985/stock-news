<template>
  <div class="stock-nav">
    <div class="stock-nav-info">
      <div class="logo-name-wrap">
        <div class="stock-logo">
          <img :src="noImage(listCode)" class="content-logo-image" />

          <h2 class="stock-name">{{ stockName }}</h2>
        </div>
        <p class="stock-code">{{ listCode }}</p>
        <p class="stock-market">{{ stockMarket }}</p>
        <span class="middle-point">·</span>
        <div class="stockPriceWrap" :class="[stockChange ? 'doneTwo' : '']">
          <h2 class="price-close">{{ stockPrice.toLocaleString() }}</h2>
          <p class="price-percent">
            <span v-if="!stockChange" class="plus-percent">+</span
            >{{ stockPercentTwo }}%
          </p>
          <p class="price-minus">
            <span v-if="!stockChange" class="plus-percent">+</span
            >{{ stockMinus }}
          </p>
        </div>

        <span class="middle-point">·</span>
        <p class="stockVolume">거래량</p>
        <p class="stock-volume">{{ stockVolume.toLocaleString() }}</p>
      </div>
    </div>

    <!-- 즐겨찾기 아이콘 추가 -->
    <div class="star-icon">
      <svg
        width="30"
        height="30"
        xmlns="http://www.w3.org/2000/svg"
        style="display: block"
        @click="stockLike"
      >
        <image
          id="icontwo"
          class="icontwo"
          :class="[isActive ? 'active' : '']"
          href="@/assets/svg/star-sign-svgrepo-com.svg"
          width="30"
          height="30"
        ></image>
      </svg>
    </div>
  </div>
  <div>
    <highcharts
      ref="highcharts"
      class="stock_charts"
      :constructor-type="'stockChart'"
      :options="stockOptions"
      :redrawOnUpdate="true"
      :animateOnUpdate="false"
    ></highcharts>
  </div>
</template>

<script>
import { onMounted, onUpdated, ref, watch } from "vue";
import { useStockStore } from "@/store/Stock.js";
import { useUserStore } from "@/store/user.js";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";

export default {
  setup() {
    const route = useRoute();
    const router = useRouter();
    let stockChange = ref(false);
    let stockPercent = ref(0);
    let stockPercentTwo = ref(0);
    const store = useStockStore();
    const userStore = useUserStore();
    let routeTest = ref("");

    let {
      stockCode,
      listCode,
      stockName,
      stockMarket,
      stockNameMarket,
      stockPrice,
      stockPriceTwo,
      stockMinus,
      stockVolume,
      contentStockPrice,
      modalData,
      searchNewsParams,
    } = storeToRefs(store);

    let { isLogin, likeList } = storeToRefs(userStore);

    /* star icon 색상 변경 부분  */
    let isActive = ref(false);

    onMounted(async () => {
      await router.isReady();
      routeTest.value = route.query.code;
      listCode.value = routeTest.value;
      changeStarColor(likeList.value);
    });

    onUpdated(() => {
      changeStarColor(likeList.value);
    });

    watch(likeList, () => {
      changeStarColor(likeList.value);
    });

    const noImage = (code) => {
      let defaultImage = require(`@/assets/stock_logo/000000.png`);
      let path = require(`@/assets/stock_logo/${code}.png`);
      try {
        return path;
      } catch (e) {
        return defaultImage;
      }
    };

    const changeStarColor = (likelist) => {
      let tmp = false;

      likelist.forEach((item) => {
        if (item.code == listCode.value) {
          tmp = true;
        }
      });

      isActive.value = tmp;
    };

    /* star icon 색상 변경 부분 끝*/

    watch(stockMinus, () => {
      if (stockMinus.value < 0) {
        stockChange.value = true;
      } else {
        stockChange.value = false;
      }
    });
    watch(stockPrice, () => {
      stockPercent.value =
        ((stockPrice.value - stockPriceTwo.value) / stockPriceTwo.value) * 100;
      stockPercentTwo.value = Math.round(stockPercent.value * 100) / 100;
    });

    const stockLike = () => {
      // 로그인한 회원인지 확인
      // 안했으면 -> 로그인 모달창 생성
      if (!isLogin.value) {
        modalData.value = true;
        return;
      }

      // 현재 즐겨찾기 하려는 종목이 좋아요 목록에 있는지 체크
      // 있으면 -> 즐겨찾기 해제
      // 없으면 -> 즐겨찾기 요청
      let apiPath = "stock-like";
      likeList.value.forEach((item) => {
        if (item.code == listCode.value) {
          apiPath = "stock-dislike";
        }
      });

      axios.get("/api/stock/" + apiPath + "/" + listCode.value).then((res) => {
        likeList.value = res.data;
      });
    };

    return {
      stockCode,
      listCode,
      stockName,
      stockMarket,
      stockNameMarket,
      stockPrice,
      stockPriceTwo,
      stockVolume,
      contentStockPrice,
      stockMinus,
      stockChange,
      stockPercent,
      stockPercentTwo,
      stockLike,
      isActive,
      changeStarColor,
      searchNewsParams,
      noImage,
    };
  },

  data() {
    return {
      stockOptions: {
        plotOptions: {
          candlestick: {
            color: "#2679ed",
            upColor: "#ed2926",
          },
        },

        rangeSelector: {
          selected: 5,
          inputDateFormat: "%Y-%m-%d",
        },

        title: {},

        yAxis: [
          {
            labels: {
              align: "right",
              x: -3,
            },
            title: {},
            height: "63%",
            lineWidth: 2,
            resize: {
              enabled: true,
            },
          },
          {
            labels: {
              align: "right",
              x: -3,
            },
            title: {},
            top: "65%",
            height: "35%",
            offset: 0,
            lineWidth: 2,
          },
        ],

        tooltip: {
          split: true,
        },

        series: [
          {
            type: "candlestick",
            name: "candlestick",
            data: null,
            dataGrouping: {
              units: null,
            },
          },
          {
            type: "column",
            name: "Volume",
            data: null,
            yAxis: 1,
            dataGrouping: {
              units: null,
            },
          },
        ],
      },

      // btn on/off
      btnOn: false,
    };
  },

  // template 이 dom에 부착된 후
  mounted() {
    console.log("mounted");
    this.chartDraw();
  },

  // 템플릿에 데이터가 변경이 일어난 후
  updated() {
    console.log("updated");
    console.log(this.listCode);
    this.chartDraw();
  },

  methods: {
    searchByDate() {},

    chartDraw() {
      console.log("chartDraw");
      axios
        .get("/api/stock/stock-price/" + this.listCode)
        .then((data) => {
          // split the data set into ohlc and volume
          data = data.data;
          let dataLength = data.length;
          let ohlc = [];
          let volume = [];

          // set the allowed units for data grouping
          let groupingUnits = [
            [
              "week", // unit name
              [1], // allowed multiples
            ],
            ["month", [1, 2, 3, 4, 6]],
          ];

          let i = 0;

          for (i; i < dataLength; i += 1) {
            ohlc.push([
              data[i][0], // the date
              data[i][1], // open
              data[i][2], // high
              data[i][3], // low
              data[i][4], // close
            ]);

            volume.push([
              data[i][0], // the date
              data[i][5], // the volume
            ]);
          }

          this.stockOptions.series[0].data = ohlc;
          this.stockOptions.series[1].data = volume;

          this.stockOptions.series[0].name = this.stockName;
          this.stockOptions.series[1].name = this.stockName;

          this.stockOptions.series[0].dataGrouping.units = groupingUnits;
          this.stockOptions.series[1].dataGrouping.units = groupingUnits;
        })
        .finally(() => {
          this.searchByDate = () => {
            let dateEls = document.querySelectorAll(
              ".highcharts-range-input text",
            );
            let fromDate = dateEls[0].innerHTML;
            let toDate = dateEls[1].innerHTML;

            this.searchNewsParams = {
              searchTerm: this.stockName,
              fromDate: fromDate,
              toDate: toDate,
            };

            console.log(this.searchNewsParams);

            this.$router.push({
              name: "NewsPast",
              query: { code: this.listCode },
            });
          };

          this.btnOn = true;
        });
    },
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

/* 종목표시 전체  */
.stock-nav {
  width: 100%;
  height: 3.9rem;
  border-bottom: 1px solid #e0e0e0;
  position: relative;
  display: flex;
  justify-content: space-between;
}

/* 종목표시 첫번째 바  */
.stock-nav-info {
  height: 3.9rem;
  margin: 0 1rem;
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  /* overflow-x: auto;
  overflow-y: hidden; */
  white-space: nowrap;
  scroll-behavior: smooth;
}

/* 종목 첫번째바 로고 */
.stock-logo {
  display: flex;
}

/* 종목 로고 이름  */
.logo-name-wrap {
  display: flex;
  font-family: "Pretendard-Regular";
}

/* 종목 코드 */
.stock-code {
  margin-left: 10px;
  margin-top: 25px;
  color: #999999;
}

/* 종목 코드 거래량 표시 */
.stockVolume {
  margin-left: 10px;
  margin-top: 27px;
  color: #999999;
}

/* 종목 코드 거래량 */
.stock-volume {
  margin-left: 10px;
  margin-top: 26px;
  color: #525252;
  font-size: 1.1rem;
}

/* 종목 유형 */
.stock-market {
  margin-left: 10px;
  margin-top: 25px;
  color: #999999;
}

/* 중간 점  */
.middle-point {
  margin-left: 10px;
  margin-top: 25px;
  color: #999999;
}

/* 종목 종가 가격  */
.price-close {
  margin-top: 20px;
  margin-left: 10px;
}

/* 종목표시 두번째 바  */
.loding-nav {
  width: 100%;
  height: 2.7rem;
  border-bottom: 1px solid #e0e0e0;
}

/* 종목표시 첫번째 바 로고 */
.content-logo-image {
  width: 3rem;
  height: 3rem;
  border-radius: 50%;
  margin-top: 10px;
  margin-right: 10px;
}

/* 종목 하이차트 */
.stock_charts {
  width: 100%;
  height: 52rem;
  margin-top: 0.1rem;
}

/* 종목 구성 모음 */
.stockPriceWrap {
  display: flex;
  color: #ed2926;
}

/* 종목 구성 (삼항연산자) */
.doneTwo {
  display: flex;
  color: #2679ed;
}

/* 종목 금일 - 전날 퍼센트 */
.price-percent {
  margin-top: 27px;
  margin-left: 8px;
}

/* 종목 금일 - 전날  */
.price-minus {
  margin-top: 27px;
  margin-left: 8px;
}

/* 즐겨찾기 아이콘 */
.star-icon {
  display: flex;
  float: right;
  cursor: pointer;
}

/* 즐겨찾기 아이콘 이미지 */
.star-icon svg {
  margin-top: 15px;
  margin-right: 20px;
}

/* 즐겨찾기 아이콘 호버 */
.icontwo:hover {
  filter: invert(58%) sepia(96%) saturate(801%) hue-rotate(358deg)
    brightness(100%) contrast(106%);
}

/* 즐겨찾기 아이콘 활성화 */
.icontwo.active {
  filter: invert(58%) sepia(96%) saturate(801%) hue-rotate(358deg)
    brightness(100%) contrast(106%);
}

/* 토픽뉴스 키워드뉴스 기간 조회 버튼 */
.news-btn {
  width: 80%;
  height: 2rem;
  border-radius: 6px;
  background-color: #2679ed;
  color: #fef6f6;
  border: none;
  margin-top: 1.5rem;
  margin-left: 4rem;
  font-family: "Pretendard-Regular";
  cursor: pointer;
  margin-bottom: 1.5rem;
}

/* 토픽뉴스 키워드뉴스 기간 조회 div */
.newsSelectBtn {
  border-bottom: 1px solid #e0e0e0;
}

/* 기간 조회 버튼 호버 */
.news-btn:hover {
  opacity: 0.7;
}
</style>
