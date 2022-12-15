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
        <div
          class=""
          v-for="(keywordNews, index) in keyWordNewsList"
          :key="index"
        >
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
      <div class="noNewsDiv" v-if="noNews"><h3>키워드기사가 없습니다.</h3></div>
    </div>
  </div>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import { ref, onMounted } from "vue";
import axios from "axios";
export default {
  setup() {
    const store = useStockStore();
    const route = useRoute();
    const router = useRouter();

    let { listCode, keywordOne, keywordTwo, stockName } = storeToRefs(store);

    let KeyWordName = ref("");
    let keyWordNewsList = ref([]);
    let modalNews = ref({});
    let modalOpen = ref(false);
    let noNews = ref(false);

    const keywordClick = () => {
      keywordOne.value = true;
      keywordTwo.value = false;
    };

    onMounted(async () => {
      await router.isReady();
      KeyWordName.value = route.query.keyword;
      // stockName loading issue
      // -> setTimeout 0.3s lazy loading
      keywordNewsSearch();
    });

    const keywordNewsSearch = () => {
      let dateEls = document.querySelectorAll(".highcharts-range-input text");
      let fromDate = dateEls[0].innerHTML;
      let toDate = dateEls[1].innerHTML;

      //let today = new Date();
      // yyyy-mm-dd
      //let year = today.getFullYear();
      //let month = today.getMonth() + 1;
      //let date = today.getDate() - 1;
      //today = year + "-" + month + "-" + date;

      let reqDto = {
        searchTerm: stockName.value,
        themeKeyword: KeyWordName.value,
        fromDate: fromDate,
        toDate: toDate,
      };
      console.log(reqDto);
      axios
        .post("/api/news/getSearchNews", reqDto)
        .then((res) => {
          console.log("res 데이터 넘어오나", res.data);
          keyWordNewsList.value = res.data;
          if (keyWordNewsList.value == 0) {
            noNews.value = true;
          }
        })
        .catch((error) => {
          console.log(error);
        });
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
      keywordNewsSearch,
      KeyWordName,
      keyWordNewsList,
      modalOpenKeyword,
      modalOpen,
      modalNews,
      noNews,
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

.newsSubHeaderWrap {
  margin-left: 1rem;
  margin-top: 1rem;
  height: 5rem;
  border-bottom: 1px solid #e5e5e5;
  display: flex;
}

.newsSubHeaderBtn {
  border: none;
  background-color: #ffffff;
  cursor: pointer;
}

.newsSubHeaderName {
  position: relative;
  left: 17px;
  top: 9px;
  font-family: "Pretendard-Regular";
}

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

.keyword-title-span {
  font-size: 0.7rem;
  color: #999999;
}

.keyword-news-title {
  width: 95%;
  border-bottom: 1px solid #e0e0e0;
  margin-left: 7px;
  margin-top: 15px;
}

.keyword-news-title:hover {
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

.noNewsDiv {
  position: relative;
  top: 10px;
  left: 14rem;
}
</style>
