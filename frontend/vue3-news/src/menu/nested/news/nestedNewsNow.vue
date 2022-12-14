<template>
  <div class="sub-view-result">
    <p>news now</p>
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
.sub-view-result {
  height: 90%;
  width: 100%;
  -ms-overflow-style: none;
}

.sub-view-result::-webkit-scrollbar {
  display: none;
}
</style>
