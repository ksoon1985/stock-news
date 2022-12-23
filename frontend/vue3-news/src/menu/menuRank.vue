<template>
  <div class="sub-tab">
    <router-link
      class="router-tab-rank"
      exact-active-class="exact-active-link"
      :to="{
        name: 'RankOne',
        query: {
          code: listCode,
        },
      }"
      ><button class="btn-close">
        <span class="sub-title" id="sub-title">키워드</span>
      </button></router-link
    >
    <!-- <router-link
      class="router-tab"
      exact-active-class="exact-active-link"
      :to="{
        name: 'RankTwo',
        query: {
          code: listCode,
        },
      }"
      ><button class="btn-close">
        <span class="sub-title" id="sub-title">연령별</span>
      </button></router-link
    > -->
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
.router-tab-rank {
  margin-top: 0.5rem;
  margin-left: 31rem;
}
</style>
