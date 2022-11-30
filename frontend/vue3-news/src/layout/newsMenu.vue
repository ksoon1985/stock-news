<template>
  <div class="main-tab">
    <Router-link
      class="router-tab"
      id="router-tab"
      :to="{
        name: 'News',
        query: {
          code: listCode,
        },
      }"
      ><button type="button" class="tab">
        <div class="news-icon">
          <svg
            width="24"
            height="24"
            xmlns="http://www.w3.org/2000/svg"
            style="display: block"
          >
            <image
              id="icon"
              class="icon"
              href="@/assets/svg/news-svgrepo-com.svg"
              width="24"
              height="24"
            />
          </svg>
        </div>
        <span class="title" id="title">뉴스정보</span>
      </button></Router-link
    >
    <Router-link
      class="router-tab"
      id="router-tab"
      :to="{
        name: 'Stock',
        query: {
          code: listCode,
        },
      }"
      ><button class="tab">
        <div class="stock-icon">
          <svg
            width="24"
            height="24"
            xmlns="http://www.w3.org/2000/svg"
            style="display: block"
          >
            <image
              id="icon"
              class="icon"
              href="@/assets/svg/information-o-svgrepo-com.svg"
              width="24"
              height="24"
            />
          </svg>
        </div>
        <span class="title" id="title">종목정보</span>
      </button></Router-link
    >
    <Router-link
      class="router-tab"
      id="router-tab"
      :to="{
        name: 'Sns',
        query: {
          code: listCode,
        },
      }"
      ><button class="tab">
        <div class="sns-icon">
          <svg
            width="24"
            height="24"
            xmlns="http://www.w3.org/2000/svg"
            style="display: block"
          >
            <image
              id="icon"
              class="icon"
              href="@/assets/svg/twitter-svgrepo-com.svg"
              width="24"
              height="24"
            />
          </svg>
        </div>
        <span class="title" id="title">SNS여론</span>
      </button></Router-link
    >
    <Router-link
      class="router-tab"
      id="router-tab"
      :to="{
        name: 'Community',
        query: {
          code: listCode,
        },
      }"
      ><button class="tab">
        <div class="community-icon">
          <svg
            width="24"
            height="24"
            xmlns="http://www.w3.org/2000/svg"
            style="display: block"
          >
            <image
              id="icon"
              class="icon"
              href="@/assets/svg/ask-svgrepo-com.svg"
              width="24"
              height="24"
            />
          </svg>
        </div>
        <span class="title" id="title">커뮤니티</span>
      </button></Router-link
    >
    <Router-link
      class="router-tab"
      id="router-tab"
      :to="{
        name: 'Rank',
        query: {
          code: listCode,
        },
      }"
      ><button class="tab">
        <div class="rank-icon">
          <svg
            width="24"
            height="24"
            xmlns="http://www.w3.org/2000/svg"
            style="display: block"
          >
            <image
              id="icon"
              class="icon"
              href="@/assets/svg/statistics-svgrepo-com.svg"
              width="24"
              height="24"
            />
          </svg>
        </div>
        <span class="title" id="title">통계정보</span>
      </button></Router-link
    >
  </div>
  <div class="main-view">
    <Router-view></Router-view>
  </div>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
export default {
  setup() {
    const store = useStockStore();

    let { stockCode, listCode } = storeToRefs(store);

    return {
      stockCode,
      listCode,
    };
  },
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Poppins&display=swap");

/* 메뉴 메인  */
.main-tab {
  width: 100%;
  display: flex;
  /* overflow-x: hidden;
  overflow-y: hidden; */
  height: 3.9rem;
  border-bottom: 1px solid #e0e0e0;
  cursor: pointer;
  text-decoration: none;
}

/* 메뉴 탭 */
.tab {
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  display: flex;
  border: 0;
  -webkit-box-align: center;
  -webkit-box-pack: 1;
  justify-content: center;
  flex: none;
  cursor: pointer;
  margin-left: 5px;
  margin-top: 13px;
  text-decoration: none;
  background: white;
}

/* 메뉴 span  */
.title {
  margin-left: 7px;
  font-size: 1.2rem;
  font-family: "Poppins", sans-serif;
}

/* 라우터 링크  */
.router-tab {
  text-decoration: none;
}

/* 자식 라우터 탭 */
.sub-tab {
  border-bottom: 1px solid #e0e0e0;
  width: 100%;
  display: flex;
  /* overflow-x: hidden;
  overflow-y: hidden; */
  height: 2.7rem;
  cursor: pointer;
  justify-content: space-around;
}

/* 메인 탭 아이콘 */
.icon:hover {
  filter: invert(16%) sepia(89%) saturate(6054%) hue-rotate(358deg)
    brightness(97%) contrast(113%);
}

/* 메인 메뉴 활성화 */
.exact-active-link {
  border-bottom: 3px solid red;
}

/* 서브 메뉴 활성화 */
.router-link-active #sub-title {
  color: #000000;
}

/* 메인 메뉴 아이콘 활성화  */
.router-link-active #icon {
  filter: invert(16%) sepia(89%) saturate(6054%) hue-rotate(358deg)
    brightness(97%) contrast(113%);
}

/* 메인 메뉴 span 활성화  */
.router-link-active #title {
  color: red;
}
</style>
