<template>
  <div class="sub-view-result">
    <div v-if="topicOne">
      <router-link
        class="forTopicRouter"
        :to="{
          name: 'TopicChildren',
          query: {
            code: listCode,
            topic: '스마트홈',
          },
        }"
      >
        <button class="forTopicBtn" @click="topicChangeEvent">토론하기</button>
      </router-link>
    </div>
    <div class="" v-if="topicTwo">
      <Router-view class=""></Router-view>
    </div>
  </div>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
import axios from "@/utils/axios";
import { onMounted, ref } from "vue";
export default {
  setup() {
    const store = useStockStore();

    let { listCode, topicOne, topicTwo } = storeToRefs(store);

    const topicChangeEvent = () => {
      topicOne.value = false;
      topicTwo.value = true;
    };

    return {
      listCode,
      topicOne,
      topicTwo,
      axios,
      onMounted,
      ref,
      topicChangeEvent,
    };
  },
};
</script>

<style></style>
