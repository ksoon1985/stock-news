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
      <div class="rightRankWrap">
        <div class="rightRankHeader"><h4>인기검색어</h4></div>
      </div>
    </div>
  </div>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
import VueHighcharts from "vue3-highcharts";
import { computed, ref } from "vue-demi";

export default {
  name: "SimpleChart",

  components: {
    VueHighcharts,
  },

  setup() {
    const store = useStockStore();

    let { listCode } = storeToRefs(store);

    const seriesData = ref([25, 39, 30, 15]);
    const categories = ref(["Jun", "Jul", "Aug", "Sept"]);

    const chartOptions = computed(() => ({
      chart: {
        type: "line",
      },
      title: {
        text: "",
      },
      xAxis: {
        categories: categories.value,
      },
      yAxis: {
        title: {
          text: "Number of stars",
        },
      },
      series: [
        {
          name: "클릭량 추이",
          data: seriesData.value,
        },
      ],
    }));

    const chartOptionsPie = computed(() => ({
      chart: {
        type: "pie",
      },
      title: {
        text: "",
      },
      xAxis: {
        categories: categories.value,
      },
      yAxis: {
        title: {
          text: "Number of stars",
        },
      },
      series: [
        {
          name: "클릭량 추이",
          data: seriesData.value,
        },
      ],
    }));

    const chartOptionsColumn = computed(() => ({
      chart: {
        type: "column",
      },
      title: {
        text: "",
      },
      xAxis: {
        categories: categories.value,
      },
      yAxis: {
        title: {
          text: "Number of stars",
        },
      },
      series: [
        {
          name: "클릭량 추이",
          data: seriesData.value,
        },
      ],
    }));

    const onRender = () => {
      console.log("Chart rendered");
    };

    return {
      listCode,
      chartOptions,
      chartOptionsPie,
      chartOptionsColumn,
      onRender,
    };
  },
};
</script>

<style>
.rankWrap {
  display: flex;
}

.leftRankWrap {
  width: 70%;
  height: 100%;
}

.rightRankWrap {
  width: 30%;
  height: 100%;
}

.leftRankChart {
}

.leftRankHeader {
  margin-left: 5px;
}

.rightRankHeader {
  border-bottom: 1px solid #e0e0e0;
}

.leftRankBottom {
  margin-left: 5px;
  border-bottom: 1px solid #e0e0e0;
}

.leftRankChartWrap {
  display: flex;
}

.leftRankPieChart {
  width: 13rem;
}

.leftRankColumnChart {
  width: 13rem;
}
</style>
