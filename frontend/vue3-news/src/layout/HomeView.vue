<template>
  <div class="sevice">
    <header class="main-header"><NewsHeader></NewsHeader></header>
    <div class="main">
      <section class="left-sidebar"><NewsStock></NewsStock></section>
      <section class="center-content"><NewsContent></NewsContent></section>
      <section class="right-sidebar"><NewsMenu></NewsMenu></section>
    </div>
  </div>
</template>

<script>
import { defineComponent } from "vue";

import NewsHeader from "@/layout/newsHeader.vue";
import NewsStock from "@/layout/newsStockAdd.vue";
import NewsContent from "@/layout/newsContent.vue";
import NewsMenu from "@/layout/newsMenu.vue";

export default defineComponent({
  name: "HomeView",

  components: { NewsContent, NewsHeader, NewsStock, NewsMenu },
});
</script>

<style>
/* 웹 전체 */
.sevice {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  overflow: scroll;
  background-color: #ffffff;
  position: fixed;
}

/* 헤더  */
.main-header {
  border-bottom: 2px solid #d01411;
  height: 5vh;
  display: flex;
  -webkit-box-pack: justify;
  justify-content: space-between;
  align-items: center;
  padding: 0 1.6rem;
}

/* 바디 */
.main {
  display: flex;
  flex: 1;
}

/* 관심목록 왼쪽 사이드바 */
.left-sidebar {
  border-right: 1px solid #e0e0e0;
  flex: 1;
}

/* 차트 중간바디 */
.center-content {
  flex: 5;
}

/* 메뉴 오른쪽 사이드바  */
.right-sidebar {
  border-left: 1px solid #e0e0e0;
  flex: 3;
  overflow: hidden;
}
</style>
