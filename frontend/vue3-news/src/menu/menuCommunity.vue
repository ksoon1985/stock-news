<template>
  <div class="sub-tab">
    <router-link
      class="router-tab"
      exact-active-class="exact-active-link"
      :to="{
        name: 'CommStock',
        query: {
          code: listCode,
        },
      }"
      ><button class="btn-close">
        <span class="sub-title" id="sub-title">종목토론</span>
      </button></router-link
    >
    <router-link
      class="router-tab"
      exact-active-class="exact-active-link"
      :to="{
        name: 'CommTopic',
        query: {
          code: listCode,
        },
      }"
      ><button class="btn-close">
        <span class="sub-title" id="sub-title">주제토론</span>
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

<style></style>
