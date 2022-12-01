<template>
  <div class="stock-nav">
    <div class="stock-nav-info">
      <div class="logo-name-wrap">
        <div class="stock-logo">
          <img
            :src="require(`@/assets/stock_logo/${listCode}.png`)"
            class="content-logo-image"
          />
          <h2 class="stock-name">{{ stockName }}</h2>
        </div>
        <p class="stock-code">{{ listCode }}</p>
        <p class="stock-market">{{ stockMarket }}</p>
        <span class="middle-point">·</span>
        <div class="stockPriceWrap" :class="[stockChange ? 'doneTwo' : '']">
          <h2 class="price-close">{{ stockPrice }}</h2>
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
        <p class="stock-volume">{{ stockVolume }}</p>
      </div>
    </div>
  </div>
  <!-- <div class="loding-nav"></div> -->
  <div>
    <highcharts
      ref="highcharts"
      class="stock_charts"
      :constructor-type="'stockChart'"
      :options="stockOptions"
      :redrawOnUpdate="true"
      :animateOnUpdate="false"
    ></highcharts>
    <button @click="searchByDate" :disabled="btnOn === false">조회</button>
  </div>
</template>

<script>
import { ref, watch } from "vue";
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
import axios from "axios";

export default {
  setup() {
    let stockChange = ref(false);
    let stockPercent = ref(0);
    let stockPercentTwo = ref(0);
    const store = useStockStore();
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
    } = storeToRefs(store);

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
            name: this.stockName,
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
        .get("http://192.168.0.36:8089/api/stock/stock-price/" + this.listCode)
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

            console.log(fromDate, toDate);

            axios
              .post("http://192.168.0.36:8089/api/news/getSearchNews", {
                searchTerm: this.stockName,
                fromDate: fromDate,
                toDate: toDate,
              })
              .then((res) => {
                console.log(res.data);
              });
          };

          this.btnOn = true;
        });
    },
  },
};
</script>

<style>
/* 종목표시 전체  */
.stock-nav {
  width: 100%;
  height: 3.9rem;
  border-bottom: 1px solid #e0e0e0;
  position: relative;
  display: flex;
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
  margin-top: 25px;
  color: #999999;
}

/* 종목 코드 거래량 */
.stock-volume {
  margin-left: 10px;
  margin-top: 24px;
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
  margin-top: 18px;
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
  height: 50rem;
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
  margin-top: 25px;
  margin-left: 8px;
}

/* 종목 금일 - 전날  */
.price-minus {
  margin-top: 25px;
  margin-left: 8px;
}
</style>
