<template>
  <div class="sub-view-result">
    <teleport to="#teleport-news-detail">
      <div class="news-modal-wrap" v-if="modalOpen">
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

    <div class="newsSelectBtn">
      <button
        class="news-btn"
        @click="searchByDate"
        :disabled="btnOn === false"
      >
        기간별 뉴스 조회
      </button>
    </div>
    <div v-if="isLoading" class="loading-container">
      <div class="loading">
        <pulse-loader :color="color" />
      </div>
    </div>

    <div v-else>
      <div class="clusteringDiv" v-if="comments.length === 0">
        <p>클러스터링 된 뉴스가 없습니다.</p>
      </div>
      <div v-else></div>
    </div>

    <div class="news-wrap">
      <div class="" v-for="(clusteredNews, cIndex) in comments" :key="cIndex">
        <!-- 토픽 뉴스 버전-->
        <div class="news-title" @click="modalOpenFunc(clusteredNews.news)">
          <div class="news-title-header">
            <span class="news-title-span">{{
              clusteredNews.news.registration_date
            }}</span>

            <span class="news-title-label"
              >군집화 키워드 : {{ clusteredNews.label }}</span
            >
            <span class="news-title-count"
              >군집화 된 기사 : {{ clusteredNews.count }}건</span
            >
          </div>
          <h3>{{ clusteredNews.news.title }}</h3>
          <div class="news-title-div-p">
            <p class="news-title-p">{{ clusteredNews.news.content }}</p>
          </div>
        </div>

        <!-- 
          헤드라인 뉴스 버전
          
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
        </div> -->
      </div>
      <InfiniteLoading @infinite="load" />
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
import { ref } from "vue-demi";
import PulseLoader from "vue-spinner/src/PulseLoader.vue";
import { useRoute, useRouter } from "vue-router";
import InfiniteLoading from "v3-infinite-loading";
export default {
  components: {
    PulseLoader,
    InfiniteLoading,
  },
  setup() {
    const store = useStockStore();

    const route = useRoute();
    const router = useRouter();

    let { listCode, searchNewsParams } = storeToRefs(store);

    let clusteredNewsList = ref([]); // 클러스터링 된 뉴스 리스트
    let modalOpen = ref(false); // 모달 on/off 변수
    let modalNews = ref({}); // 모달 상세 뉴스 정보 변수

    let isLoading = ref(false); // 뉴스 리스트 로딩 중 변수
    let page = 0;
    let comments = ref([]);

    // onMounted(() => {
    //   getClusteredNews();
    // });

    // watch(searchNewsParams, () => {
    //   getClusteredNews();
    // });

    // 서버로부터 클러스터링 된 뉴스를 얻어오는 함수
    const load = async ($state) => {
      console.log("Loading... ");
      await router.isReady();
      listCode.value = route.query.code;

      clusteredNewsList.value = [];

      let dateEls = document.querySelectorAll(".highcharts-range-input text");
      let fromDate = dateEls[0].innerHTML;
      let toDate = dateEls[1].innerHTML;

      let today = new Date();

      let year = today.getFullYear();
      let month = ("0" + (today.getMonth() + 1)).slice(-2);
      let day = ("0" + today.getDate()).slice(-2);

      // 처음이나 새로고침할 때 차트의 date를 불러오지 못할 경우
      // fromDate = 2020-01-01 , toDate = 현재날짜 로 셋팅
      let reqDto = {
        searchTerm: listCode.value,
        fromDate: fromDate == "" ? "2020-01-01" : fromDate,
        toDate: toDate == "" ? year + "-" + month + "-" + day : toDate,
      };
      const response = [];
      isLoading.value = true;
      try {
        axios
          .post("/api/news/getClusteredNews", reqDto)
          .then((res) => {
            response.value = res.data;
            console.log("클러스터뉴스리스트", response);
            if (response.value.length < 50) $state.complete();
            else {
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
      isLoading,
      modalOpenFunc,
      color: "#d01411",
      searchNewsParams,
      load,
      page,
      comments,
    };
  },
};
</script>

<style scoped>
@font-face {
  font-family: "Pretendard-Regular";
  src: url("https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff")
    format("woff");
  font-weight: 500;
  font-style: normal;
}

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
  overflow-y: auto;
  overflow-x: hidden;
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

.loading {
  z-index: 2;
  position: fixed;
  top: 50%;
  left: 83%;
  transform: translate(-50%, -50%);
  box-shadow: rgba(0, 0, 0, 0) 0 0 0 9999px;
}

.news-title {
  width: 95%;
  border-bottom: 1px solid #e0e0e0;
  margin-left: 7px;
  margin-top: 15px;
}

.news-title-span {
  font-size: 0.7rem;
  color: #999999;
}

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

.clusteringDiv {
  text-align: center;
  font-family: "Pretendard-Regular";
}

.news-title-label {
  font-size: 0.7rem;
  color: #999999;
}

.news-title-count {
  font-size: 0.7rem;
  color: #999999;
}

.news-title-header {
  display: flex;
  justify-content: space-between;
}
</style>
