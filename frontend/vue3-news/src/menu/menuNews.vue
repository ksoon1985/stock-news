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
      ><button class="btn-close">
        <!-- @click="keyWordClickEvent"> -->
        <span class="sub-title" id="sub-title">키워드뉴스</span>
      </button></router-link
    >
    <router-link
      class="router-tab"
      exact-active-class="exact-active-link"
      :to="{
        name: 'NewsTime',
        query: {
          code: listCode,
        },
      }"
      ><button class="btn-close">
        <span class="sub-title" id="sub-title">최신뉴스</span>
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
import { ref, onMounted, watch } from "vue";
export default {
  setup() {
    const store = useStockStore();
    const route = useRoute();
    const stockData = ref(null);
    const router = useRouter();
    let routeTest = ref("");

    let { listCode, keyWordList, realTimeData } = storeToRefs(store);

    // const keyWordClickEvent = () => {
    //   listCode.value = route.query.code;
    //   axios.get("/api/stock/stock-summary/" + listCode.value).then((res) => {
    //     stockData.value = res.data;
    //     keyWordList.value = stockData.value.themeKeywords;
    //   });
    // };

    // const realTimeAllEvent = async () => {
    //   await router.isReady();
    //   routeTest.value = route.query.code;
    //   listCode.value = routeTest.value;

    //   let reqDto = {
    //     searchTerm: listCode.value,
    //   };
    //   axios
    //     .post("/api/news/getRealTimeNews", reqDto)
    //     .then((res) => {
    //       realTimeData.value = res.data;
    //       console.log("res데이터를 알아보자", realTimeData);
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     });
    // };

    // onMounted(() => {
    //   //keyWordClickEvent();
    //   realTimeAllEvent();
    // });

    return {
      listCode,
      useRouter,
      //keyWordClickEvent,
      keyWordList,
      stockData,
      watch,
      // realTimeAllEvent,
      axios,
      onMounted,
      route,
      router,
      routeTest,
      realTimeData,
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
