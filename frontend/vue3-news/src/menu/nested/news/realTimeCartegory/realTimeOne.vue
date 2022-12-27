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
            {{ modalNews.regdate }} {{ modalNews.journalist }} {{ modalNews }}
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
import { ref } from "vue-demi";
import axios from "@/utils/axios";
import { useRoute, useRouter } from "vue-router";
import InfiniteLoading from "v3-infinite-loading";
export default {
  components: {
    InfiniteLoading,
  },

  setup() {
    const store = useStockStore();

    let { listCode, stockName, realTimeData } = storeToRefs(store);

    const router = useRouter();
    const route = useRoute();
    let modalOpen = ref(false);
    let modalNews = ref({});
    let realTimeData1 = ref({});
    let seq = ref("");
    let page = 1;
    let comments = ref([]);

    // onMounted(() => {
    //   realTimeOneEvent();
    // });

    const load = async ($state) => {
      console.log("Loading... ");
      await router.isReady();
      listCode.value = route.query.code;
      seq.value = route.query.sd1;

      let reqDto = {
        searchTerm: listCode.value,
        categoryId: seq.value,
        page: page,
      };
      const response = [];

      try {
        axios.post("/api/news/getRealTimeNews", reqDto).then((res) => {
          response.value = res.data;
          console.log("res데이터를 알아보자", response);
          if (response.value.length < 10) $state.complete();
          else {
            comments.value.push(...response.value);
            $state.loaded();
          }
          page++;
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
      realTimeData1,
      realTimeData,
      stockName,
      modalOpen,
      modalOpenFunc,
      modalNews,
      // realTimeOneEvent,
      seq,
      page,
      comments,
      load,
    };
  },
};
</script>

<style></style>
