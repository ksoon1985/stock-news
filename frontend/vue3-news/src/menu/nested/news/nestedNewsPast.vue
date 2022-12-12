<template>
  <div class="sub-view-result">
    <teleport to="#teleport-news-detail">
      <div class="news-modal-wrap" v-if="modalOpen === true">
        <div class="news-modal-detail">
          <div class="news-modal-title">제목 : {{ modalNews.title }}</div>
          <div class="news-modal-content">내용 : {{ modalNews.content }}</div>
          <div class="news-modal-journalist">
            기자 : {{ modalNews.journalist }}
          </div>
          <div class="news-modal-regdate">등록일 : {{ modalNews.regdate }}</div>
          <hr />
          <button @click="modalOpen = false">닫기</button>
        </div>
      </div>
    </teleport>

    <div v-if="clusteredNewsList.length > 0"></div>
    <div v-else></div>

    <div class="news-wrap">
      <div
        class=""
        v-for="(clusteredNews, cIndex) in clusteredNewsList"
        :key="cIndex"
      >
        <div class="news-headline">
          <h1>{{ clusteredNews.label }}</h1>
        </div>
        <div
          class="news-title"
          v-for="(news, nIndex) in clusteredNews.news"
          :key="nIndex"
          @click="modalOpenFunc(news)"
        >
          {{ news.title }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
import { onMounted, onUpdated, ref, watch } from "vue-demi";
export default {
  setup() {
    const store = useStockStore();

    let { searchNewsParams, listCode, stockName } = storeToRefs(store);

    let clusteredNewsList = ref([]); // 클러스터링 된 뉴스 리스트
    let modalOpen = ref(false); // 모달 on/off 변수
    let modalNews = ref({}); // 모달 상세 뉴스 정보 변수

    //let isLoading = ref(false); // 뉴스 리스트 로딩 중 변수

    onMounted(() => {
      console.log("news onMounted");
      let today = new Date();
      // yyyy-mm-dd
      let year = today.getFullYear();
      let month = today.getMonth() + 1;
      let date = today.getDate() - 1;

      today = year + "-" + month + "-" + date;

      // stockName loading issue
      // -> setTimeout 0.3s lazy loading
      setTimeout(() => {
        searchNewsParams.value = {
          searchTerm: stockName.value,
          fromDate: "2020-01-01",
          toDate: today,
        };

        console.log(searchNewsParams.value);
        getClusteredNews();
      }, 300);
    });

    watch(searchNewsParams, () => {
      console.log("news watched");
      getClusteredNews();
    });

    onUpdated(() => {});

    // 서버로부터 클러스터링 된 뉴스를 얻어오는 함수
    const getClusteredNews = () => {
      axios
        .post("/api/news/getClusteredNews", searchNewsParams.value)
        .then((res) => {
          clusteredNewsList.value = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    };

    // 뉴스 제목 클릭 시 상세 뉴스 정보 모달 팝업 함수
    const modalOpenFunc = (news) => {
      modalNews.value = {
        title: news.title,
        content: news.content,
        journalist: news.journalist_name,
        regdate: news.registration_date,
      };

      modalOpen.value = true;
    };

    return {
      listCode,
      clusteredNewsList,
      modalOpen,
      modalNews,
      searchNewsParams,
      modalOpenFunc,
      getClusteredNews,
    };
  },
};
</script>

<style scoped>
.news-title:hover {
  cursor: pointer;
  text-decoration: underline;
}

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
}

.news-modal-detail {
  width: 45%;
  max-height: 80%;
  overflow: scroll;
  background: white;
  border-radius: 8px;
  padding: 20px;
}
</style>
