<template>
  <div class="sub-view-resultThree">
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

    <div class="newsSubRouter">
      <div class="newsSubHeaderWrap">
        <button
          type="button"
          class="newsSubHeaderBtn"
          @click="$router.go(-1), keywordClick()"
        >
          <div>
            <svg
              width="24"
              height="24"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
              style="display: block"
            >
              <image
                href="@/assets/svg/left-arrow-svgrepo-com.svg"
                width="24"
                height="24"
              />
            </svg>
          </div>
        </button>
        <div class="newsSubHeaderName">
          <h3>{{ KeyWordName }}</h3>
        </div>
      </div>
      <div class="news-wrap">
        <div class="" v-for="(keywordNews, index) in comments" :key="index">
          <div class="news-title">
            <div
              class="keyword-news-title"
              @click="modalOpenKeyword(keywordNews)"
            >
              <span class="keyword-title-span">
                {{ keywordNews.registration_date }}
              </span>
              <h3>{{ keywordNews.title }}</h3>
              <div class="keyword-title-div-p">
                <p class="keyword-title-p">
                  {{ keywordNews.content }}
                </p>
              </div>
            </div>
          </div>
        </div>
        <InfiniteLoading @infinite="load" />
      </div>
      <div class="noNewsDiv" v-if="noNews"><h3>키워드기사가 없습니다.</h3></div>
    </div>
  </div>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { useUserStore } from "@/store/user.js";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import { ref } from "vue";
import axios from "axios";
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
    const userStore = useUserStore();

    let { listCode } = storeToRefs(store);
    let { keywordOne, keywordTwo } = storeToRefs(userStore);

    const route = useRoute();
    const router = useRouter();
    let page = 0;
    let comments = ref([]);

    let KeyWordName = ref("");
    let keyWordNewsList = ref([]);
    let modalNews = ref({});
    let modalOpen = ref(false);
    let noNews = ref(false);
    let isLoading = ref(false);

    const onClickOutside = (event) => {
      console.log("클릭 아웃사이더 이벤트", event);
      modalOpen.value = false;
    };

    const keywordClick = () => {
      keywordOne.value = true;
      keywordTwo.value = false;
    };

    const load = async ($state) => {
      console.log("Loading... ");
      await router.isReady();
      listCode.value = route.query.code;
      KeyWordName.value = route.query.keyword;

      let dateEls = document.querySelectorAll(".highcharts-range-input text");
      let fromDate = dateEls[0].innerHTML;
      let toDate = dateEls[1].innerHTML;

      let today = new Date();

      let year = today.getFullYear();
      let month = ("0" + (today.getMonth() + 1)).slice(-2);
      let day = ("0" + today.getDate()).slice(-2);

      let reqDto = {
        searchTerm: listCode.value,
        themeKeyword: KeyWordName.value,
        fromDate: fromDate == "" ? "2020-01-01" : fromDate,
        toDate: toDate == "" ? year + "-" + month + "-" + day : toDate,
        page: page,
      };
      const response = [];
      isLoading.value = true;
      try {
        axios
          .post("/api/news/getSearchNews", reqDto)
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

    const modalOpenKeyword = (news) => {
      modalNews.value = {
        title: news.title,
        office: news.office_id,
        content: news.content,
        journalist: news.journalist_name,
        regdate: news.registration_date,
      };
      console.log(modalNews.value);
      modalOpen.value = true;
    };

    return {
      listCode,
      keywordOne,
      keywordTwo,
      keywordClick,
      useRoute,
      route,
      KeyWordName,
      keyWordNewsList,
      modalOpenKeyword,
      modalOpen,
      modalNews,
      noNews,
      page,
      comments,
      load,
      isLoading,
      color: "#d01411",
      onClickOutside,
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

/* 키워드 뉴스 헤더 */
.newsSubHeaderWrap {
  margin-top: 1rem;
  height: 5rem;
  border-bottom: 1px solid #e5e5e5;
  display: flex;
  position: sticky;
  top: 0px;
  background-color: #ffffff;
}

/* 키워드 뉴스 뒤로 가기 버튼 */
.newsSubHeaderBtn {
  border: none;
  background-color: #ffffff;
  cursor: pointer;
  margin-left: 1rem;
}

/* 키워드 뉴스 헤더 종목이름 */
.newsSubHeaderName {
  position: relative;
  left: 17px;
  top: 9px;
  font-family: "Pretendard-Regular";
}

/* 키워드 뉴스 콘텐트 */
.keyword-title-p {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  word-wrap: break-word;
  line-height: 1.2em;
  height: 2.4em;
}

/* 키워드 뉴스 제목 */
.keyword-title-span {
  font-size: 0.7rem;
  color: #999999;
}

/* 키워드 뉴스 div */
.keyword-news-title {
  width: 95%;
  border-bottom: 1px solid #e0e0e0;
  margin-left: 7px;
  margin-top: 15px;
}

/* 키워드 뉴스 호버 */
.keyword-news-title:hover {
  cursor: pointer;
  text-decoration: underline;
}

/* 키워드 뉴스 전체 모달  */
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

/* 키워드 뉴스 모달 div */
.news-modal-detail {
  width: 37%;
  background: white;
  border-radius: 8px;
  padding: 20px;
  overflow-y: auto;
  max-height: 90%;
}

/* 키워드 뉴스 기자/날짜 */
.news-modal-journalist {
  color: #999999;
  font-size: 0.9rem;
  border-bottom: 1px solid #e5e5e5;
  margin-bottom: 1rem;
  height: 2rem;
}

/* 키워드 뉴스 제목 */
.news-modal-title {
  margin: 0px;
  padding: 0px;
}

/* 키워드 뉴스 콘텐트 */
.news-modal-content {
  border-bottom: 1px solid #e5e5e5;
  margin-bottom: 1rem;
  font-size: 0.9rem;
}

/* 키워드 뉴스 닫기 버튼 div */
.new-modal-Btn {
  text-align: right;
}

/* 키워드 뉴스 닫기 버튼 */
.newsModalBtn {
  width: 3rem;
  height: 1.8rem;
  border-radius: 4px;
  background-color: #d01411;
  color: #fef6f6;
  border: none;
  margin-top: 6px;
}

/* 키워드 뉴스 언론사 로고 */
.news-modal-logo {
  width: auto;
  height: auto;
  border-radius: 0px;
}

/* 키워드 뉴스 빈 array */
.noNewsDiv {
  position: relative;
  top: 10px;
  left: 14rem;
}

.news-title {
  width: 95%;
  border-bottom: 1px solid #e0e0e0;
  margin-left: 7px;
  margin-top: 15px;
}

.sub-view-resultThree {
  height: 75vh;
  -ms-overflow-style: none;
}

.sub-view-resultThree::-webkit-scrollbar {
  display: none;
}
</style>
