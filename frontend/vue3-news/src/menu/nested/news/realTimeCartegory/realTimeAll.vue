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

    <div class="news-wrap">
      <div v-for="(item, index) in comments" :key="index">
        <div class="news-title" @click="modalOpenFunc(item)">
          <span class="news-title-span">{{ item.registration_date }}</span>
          <h3>{{ item.title }}</h3>
          <div class="news-title-div-p">
            <p class="news-title-p">{{ item.content }}</p>
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
import InfiniteLoading from "v3-infinite-loading";
export default {
  setup() {
    const store = useStockStore();

    let { listCode, stockName, realTimeData } = storeToRefs(store);

    let modalOpen = ref(false);
    let modalNews = ref({});

    let comments = ref([]);
    const load = async ($state) => {
      console.log("loading...");

      try {
        if (realTimeData.length < 10) $state.complete();
        else {
          comments.value.push(...realTimeData);
          $state.loaded();
        }
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
      load,
      InfiniteLoading,
    };
  },
};
</script>

<style>
.news-title {
  width: 95%;
  border-bottom: 1px solid #e0e0e0;
  margin-left: 7px;
  margin-top: 15px;
}

.news-title:hover {
  cursor: pointer;
  text-decoration: underline;
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
</style>
