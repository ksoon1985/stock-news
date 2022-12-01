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
        <span class="sub-title" id="sub-title">과거뉴스</span>
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
        <span class="sub-title" id="sub-title">현재뉴스</span>
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
export default {
  setup() {
    const store = useStockStore();

    let { listCode } = storeToRefs(store);

    return {
      listCode,
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
