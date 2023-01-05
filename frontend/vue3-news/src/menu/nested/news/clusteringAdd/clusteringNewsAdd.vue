<template>
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
          <button class="newsModalBtn" @click="modalOpen = false">닫기</button>
        </div>
      </div>
    </div>
  </teleport>

  <div>
    <button class="clusteringBtn" @click="clusteringVifEvent">
      군집화 기사 보기
    </button>
  </div>
  <div v-if="clusteringVif">
    <ul>
      <li v-for="(item, index) in clusteringNewsList" :key="index">
        <span class="clusteringSpan" @click="modalOpenFunc(item)">{{
          item.title
        }}</span>
      </li>
    </ul>
  </div>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
import { ref } from "vue-demi";
import vClickOutside from "click-outside-vue3";
export default {
  directives: {
    clickOutside: vClickOutside.directive,
  },

  props: {
    clusteringNewsList: Array,
  },
  setup() {
    const store = useStockStore();

    let clusteringVif = ref(false);

    let { listCode } = storeToRefs(store);

    let modalOpen = ref(false);
    let modalNews = ref({});

    const clusteringVifEvent = () => {
      clusteringVif.value = !clusteringVif.value;
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

    const onClickOutside = (event) => {
      console.log("클릭 아웃사이더 이벤트", event);
      modalOpen.value = false;
    };

    return {
      listCode,
      clusteringVif,
      clusteringVifEvent,
      modalOpen,
      modalOpenFunc,
      onClickOutside,
      modalNews,
    };
  },
};
</script>

<style>
.clusteringBtn {
  font-size: 0.8rem;
  border: 1px solid #d01411;
  color: #d01411;
  cursor: pointer;
  border-radius: 5px;
  background-color: white;
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
  font-family: "Pretendard-Regular";
}

.news-modal-logo {
  width: auto;
  height: auto;
  border-radius: 0px;
}

.clusteringSpan:hover {
  cursor: pointer;
  text-decoration: underline;
}

li::marker {
  color: #d01411;
}
</style>
