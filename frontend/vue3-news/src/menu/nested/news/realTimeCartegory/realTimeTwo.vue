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

    const router = useRouter();
    const route = useRoute();
    let modalOpen = ref(false);
    let modalNews = ref({});
    let realTimeData2 = ref({});
    let seq = ref("");
    let comments = ref([]);
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
      seq.value = route.query.sd1;

      let reqDto = {
        searchTerm: listCode.value,
        categoryId: seq.value,
        page: page,
      };
      const response = [];
      isLoading.value = true;
      try {
        axios
          .post("/api/news/getRealTimeNews", reqDto)
          .then((res) => {
            response.value = res.data;
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
      realTimeData2,
      realTimeData,
      stockName,
      modalOpen,
      modalOpenFunc,
      modalNews,
      seq,
      comments,
      page,
      load,
      isLoading,
      color: "#d01411",
      onClickOutside,
    };
  },
};
</script>

<style></style>
