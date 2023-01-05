<template>
  <div class="sub-view-result">
    <teleport to="#teleport-news-detail">
      <div class="news-modal-wrap" v-if="modalOpen">
        <div class="news-modal-detail" v-click-outside="onClickOutside">
          <div class="news-modal-logo">
            <img
              :src="require(`@/assets/code_media/${modalNews.office}.png`)"
              class="modalLogoNews"
            />
          </div>
          <div class="news-modal-title">
            <h3>{{ modalNews.title }}</h3>
          </div>
          <div class="news-modal-journalist">
            {{ modalNews.regdate }} {{ modalNews.journalist }}
          </div>
          <div class="news-modal-content">
            <p
              class="newsModalP"
              v-html="modalNews.content.split('다.').join('다.<br /><br />')"
            ></p>
          </div>
          <div class="new-modal-Btn">
            <button class="newsModalBtn" @click="modalOpen = false">
              닫기
            </button>
          </div>
        </div>
      </div>
    </teleport>

    <div v-if="isLoading" class="loading-container">
      <div class="loading">
        <pulse-loader :color="color" />
      </div>
    </div>

    <div class="news-wrap">
      <div v-for="(item, index) in comments" :key="index">
        <div class="news-title" @click="modalOpenFunc(item)">
          <span class="news-title-span">{{ item.registration_date }}</span>
          <div class="item-title-div">
            <h3>{{ item.title }}</h3>
            <div class="news-title-div-p">
              <p class="news-title-p">{{ item.content }}</p>
            </div>
          </div>
        </div>
      </div>
      <InfiniteLoading @infinite="load" />
    </div>
  </div>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
import { onMounted, ref } from "vue";
import axios from "axios";
import { useRoute, useRouter } from "vue-router";
import InfiniteLoading from "v3-infinite-loading";
import PulseLoader from "vue-spinner/src/PulseLoader.vue";
import vClickOutside from "click-outside-vue3";

export default {
  components: {
    PulseLoader,
    InfiniteLoading,
  },

  directives: {
    clickOutside: vClickOutside.directive,
  },

  setup() {
    const store = useStockStore();

    let { listCode, stockName, realTimeData } = storeToRefs(store);

    let modalOpen = ref(false);
    let modalNews = ref({});
    let comments = ref([]);
    const route = useRoute();
    const router = useRouter();
    let page = 0;
    let isLoading = ref(false);

    const onClickOutside = (event) => {
      console.log("클릭 아웃사이더 이벤트", event);
      modalOpen.value = false;
    };

    const load = async ($state) => {
      console.log("Loading... ");
      await router.isReady();
      listCode.value = route.query.code;

      let reqDto = {
        page: page,
        searchTerm: listCode.value,
      };
      const response = [];
      isLoading.value = true;
      try {
        axios
          .post("/api/news/getRealTimeNews", reqDto)
          .then((res) => {
            response.value = res.data;
            if (response.value.length < 50) {
              comments.value.push(...response.value);
              $state.complete();
            } else {
              comments.value.push(...response.value);
              $state.loaded();
            }
            page++;
          })
          .catch((err) => {
            isLoading.value = false;
            console.log(err);
          })
          .finally(() => {
            isLoading.value = false;
          });
      } catch (error) {
        $state.error();
      }
    };

    const modalOpenFunc = (news) => {
      modalNews.value = {
        title: news.title,
        office: news.office_id,
        content: news.content,
        journalist: news.journalist_name,
        regdate: news.registration_date,
      };

      modalOpen.value = true;
    };

    return {
      listCode,
      stockName,
      onMounted,
      ref,
      axios,
      realTimeData,
      modalOpenFunc,
      modalOpen,
      modalNews,
      comments,
      load,
      page,
      isLoading,
      color: "#d01411",
      onClickOutside,
    };
  },
};
</script>

<style>
@font-face {
  font-family: "Pretendard-Regular";
  src: url("https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff")
    format("woff");
  font-weight: 500;
  font-style: normal;
}

/* 실시간 뉴스 for div */
.news-title {
  width: 95%;
  border-bottom: 1px solid #e0e0e0;
  margin-left: 7px;
  margin-top: 15px;
}

/* 실시간 뉴스 span  */
.news-title-span {
  font-size: 0.7rem;
  color: #999999;
}

.item-title-div:hover {
  cursor: pointer;
  text-decoration: underline;
}

/* 실시간 뉴스 p */
.news-title-p {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  word-wrap: break-word;
  line-height: 1.2em;
  height: 2.4em;
}

/* 실시간 뉴스 모달창 */
.news-modal-wrap {
  overflow: scroll;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow-y: auto;
  overflow-x: hidden;
  z-index: 990;
}

/* 실시간 뉴스 모달 div */
.news-modal-detail {
  width: 37%;
  background: white;
  border-radius: 8px;
  padding: 20px;
  overflow-y: auto;
  max-height: 90%;
}

/* 실시간 뉴스 기자/날짜 */
.news-modal-journalist {
  color: #999999;
  font-size: 0.9rem;
  border-bottom: 1px solid #e5e5e5;
  margin-bottom: 1rem;
  height: 2rem;
}

/* 실시간 뉴스 제목 */
.news-modal-title {
  margin: 0px;
  padding: 0px;
}

/* 실시간 뉴스 콘텐트 */
.news-modal-content {
  border-bottom: 1px solid #e5e5e5;
  margin-bottom: 1rem;
  font-size: 0.9rem;
}

/* 실시간 뉴스 닫기 버튼 div */
.new-modal-Btn {
  text-align: right;
  font-family: "Pretendard-Regular";
}

/* 실시간 뉴스 닫기 버튼 */
.newsModalBtn {
  width: 3rem;
  height: 1.8rem;
  border-radius: 4px;
  background-color: #d01411;
  color: #fef6f6;
  border: none;
  margin-top: 6px;
}

/* 실시간 뉴스 언론사 로고 */
.news-modal-logo {
  width: auto;
  height: auto;
  border-radius: 0px;
}

.pagination-container {
  display: flex;
  column-gap: 10px;
}
.paginate-buttons {
  height: 40px;
  width: 40px;
  border-radius: 20px;
  cursor: pointer;
  background-color: rgb(242, 242, 242);
  border: 1px solid rgb(217, 217, 217);
  color: black;
}
.paginate-buttons:hover {
  background-color: #d8d8d8;
}
.active-page {
  background-color: #3498db;
  border: 1px solid #3498db;
  color: white;
}
.active-page:hover {
  background-color: #2988c8;
}

/* 실시간 뉴스 for div */
.news-wrap {
  margin-bottom: 5px;
}

/* 실시간 뉴스 div */
.realTimeDiv {
  text-align: center;
  font-family: "Pretendard-Regular";
}

/* loading 위치 */
.loading {
  z-index: 2;
  position: fixed;
  top: 50%;
  left: 83%;
  transform: translate(-50%, -50%);
  box-shadow: rgba(0, 0, 0, 0) 0 0 0 9999px;
}
</style>
