<template>
  <div class="sub-view-result">
    <div class="rankWrap">
      <div class="leftRankWrap">
        <div class="leftRankHeader">
          <h4>클릭량 추이</h4>
        </div>
        <div class="leftRankChart">
          <vue-highcharts
            type="chart"
            :options="chartOptions"
            :redrawOnUpdate="true"
            :oneToOneUpdate="false"
            :animateOnUpdate="true"
            @rendered="onRender"
          />
        </div>
        <div class="leftRankBottom">
          <div class="leftRankBottomHeader">
            <h4>성별/연령별 비중</h4>
          </div>
          <div class="leftRankChartWrap">
            <div class="leftRankPieChart">
              <vue-highcharts
                type="chart"
                :options="chartOptionsPie"
                :redrawOnUpdate="true"
                :oneToOneUpdate="false"
                :animateOnUpdate="true"
                @rendered="onRender"
              />
            </div>
            <div class="leftRankColumnChart">
              <vue-highcharts
                type="chart"
                :options="chartOptionsColumn"
                :redrawOnUpdate="true"
                :oneToOneUpdate="false"
                :animateOnUpdate="true"
                @rendered="onRender"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
import VueHighcharts from "vue3-highcharts";
import { onMounted, ref } from "vue-demi";
import axios from "axios";
import { useRoute, useRouter } from "vue-router";

export default {
  name: "SimpleChart",

  components: {
    VueHighcharts,
  },

  setup() {
    const store = useStockStore();

    let { listCode } = storeToRefs(store);

    const route = useRoute();
    const router = useRouter();
    let routeTest = ref("");

    onMounted(() => {
      chartOptionsDraw(); // 클릭량 추이
      chartOptionsPieDraw(); // 성별 통계
    });

    const chartOptionsDraw = async () => {
      await router.isReady();
      routeTest.value = route.query.code;
      listCode.value = routeTest.value;

      axios
        .get("/api/stock/getClickCountProgress/" + listCode.value)
        .then((res) => {
          console.log(res);
          chartOptions.value.xAxis.categories = res.data.dateList;
          chartOptions.value.series[0].data = res.data.countList;
        })
        .catch((err) => {
          console.log(err);
        });
    };

    const chartOptionsPieDraw = async () => {
      await router.isReady();
      routeTest.value = route.query.code;
      listCode.value = routeTest.value;

      axios
        .get("/api/stock/getClickCountGender/" + listCode.value)
        .then((res) => {
          console.log(res);
          chartOptionsPie.value.series[0].data = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    };

    let chartOptions = ref({
      chart: {
        type: "line",
      },
      title: {
        text: "",
      },
      xAxis: {
        categories: [],
      },
      yAxis: {
        title: {
          text: "Number of Interest",
        },
      },
      series: [
        {
          name: "Count",
          data: [],
        },
      ],
    });

    let chartOptionsPie = ref({
      chart: {
        type: "pie",
      },
      title: {
        text: "",
      },
      plotOptions: {
        pie: {
          allowPointSelect: true,
          cursor: "pointer",
          colors: ["#64AAFF", "#FF6EED"],
        },
      },
      series: [
        {
          name: "클릭량 추이",
          data: [],
        },
      ],
    });

    let chartOptionsColumn = ref({
      chart: {
        type: "column",
      },
      title: {
        text: "",
      },
      xAxis: {
        categories: [],
      },
      yAxis: {
        title: {
          text: "Number of stars",
        },
      },
      series: [
        {
          name: "",
          data: [],
        },
      ],
    });

    const onRender = () => {
      console.log("Chart rendered");
    };

    return {
      listCode,
      chartOptions,
      chartOptionsPie,
      chartOptionsColumn,
      onRender,
      router,
      routeTest,
    };
  },
};
</script>

<style>
.rankWrap {
  display: flex;
  margin-left: 25px;
}

.leftRankWrap {
  width: 100%;
  height: 100%;
}

.leftRankChart {
  width: 90%;
}

.leftRankHeader {
  margin-left: 5px;
}

.leftRankBottom {
  margin-left: 5px;
  border-bottom: 1px solid #e0e0e0;
}

.leftRankChartWrap {
  width: 90%;
  display: flex;
}

.leftRankPieChart {
  width: 45%;
}

.leftRankColumnChart {
  width: 45%;
}
</style>
