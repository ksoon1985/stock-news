<template>
  <div class="sub-view-result">
    <teleport to="#teleport-news-detail">
      <div class="news-modal-wrap" v-if="modalOpen === true">
        <div class="news-modal-detail">
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

    <div class="news-wrap">
      <div v-if="clusteredNewsList.length > 0">
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
      <div v-else>잠시만 기다려 주세요</div>
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
        office: news.office_id,
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
  z-index: 990;
}

.news-modal-detail {
  width: 37%;
  background: white;
  border-radius: 8px;
  padding: 20px;
  overflow-y: auto;
  max-height: 90%;
}

.news-modal-journalist {
  color: #999999;
  font-size: 0.9rem;
  border-bottom: 1px solid #e5e5e5;
  margin-bottom: 1rem;
  height: 2rem;
}

.news-modal-title {
  margin: 0px;
  padding: 0px;
}

.news-modal-content {
  border-bottom: 1px solid #e5e5e5;
  margin-bottom: 1rem;
  font-size: 0.9rem;
}

.new-modal-Btn {
  text-align: right;
}

.newsModalBtn {
  width: 3rem;
  height: 1.8rem;
  border-radius: 4px;
  background-color: #d01411;
  color: #fef6f6;
  border: none;
  margin-top: 6px;
}

.news-modal-logo {
  width: auto;
  height: auto;
  border-radius: 0px;
}
</style>
