<template>
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
          <button class="newsModalBtn" @click="modalOpen = false">닫기</button>
        </div>
      </div>
    </div>
  </teleport>

  <div class="sub-view-result">
    <div class="news-wrap">
      <div v-for="(item, index) in realTimeData3" :key="index">
        <div class="news-title" @click="modalOpenFunc(item)">
          <span class="news-title-span">{{ item.registration_date }}</span>
          <h3>{{ item.title }}</h3>
          <div class="news-title-div-p">
            <p class="news-title-p">{{ item.content }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
import { ref } from "vue-demi";
export default {
  setup() {
    const store = useStockStore();

    let { listCode, stockName, realTimeData, realTimeData3 } =
      storeToRefs(store);

    let modalOpen = ref(false);
    let modalNews = ref({});

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
      realTimeData,
      realTimeData3,
      modalOpenFunc,
      modalNews,
      modalOpen,
    };
  },
};
</script>

<style></style>
