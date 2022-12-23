<template>
  <div class="sub-view-result">
    <div class="news-wrap">
      <div v-for="(item, index) in realTimeData6" :key="index">
        <div class="news-title" @click="modalOpenFunc(item)">
          <span class="news-title-span">{{ item.registration_date }}</span>
          <h3>{{ item.title }}</h3>
          <div class="news-title-div-p">
            <p class="news-title-p">{{ item.content }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
export default {
  setup() {
    const store = useStockStore();

    let { listCode, stockName, realTimeData6, realTimeData } =
      storeToRefs(store);

    return {
      listCode,
      realTimeData6,
      realTimeData,
      stockName,
    };
  },
};
</script>

<style></style>
