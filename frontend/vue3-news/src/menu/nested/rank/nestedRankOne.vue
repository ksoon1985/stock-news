<template>
  <div class="sub-view-result">
    <div><h3>뉴스 키워드</h3></div>
    <div class="iframeDiv">
      <iframe
        src="http://192.168.0.47:5601/app/visualize#/edit/03c9ed90-7d34-11ed-b287-13ab45793f2b?embed=true&_g=(filters%3A!()%2CrefreshInterval%3A(pause%3A!t%2Cvalue%3A0)%2Ctime%3A(from%3A'2020-01-01T10%3A46%3A30.819Z'%2Cto%3Anow))"
        height="400"
        width="610"
        class="word-cloud"
        frameborder="0"
      ></iframe>
    </div>
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

<style scoped></style>
