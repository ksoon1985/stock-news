<template>
  <div class="sub-view-result">
    <div class="keyword-rank" v-if="topicOne">
      <div class="keyword-rank-name">
        <h3 class="keyword-rank-h3">인기주제</h3>
      </div>

      <div class="keyword-rank-wrap">
        <div
          class="forkeywordRank"
          v-for="(item, index) in keywordRankList"
          :key="index"
        >
          <span class="forRankSapn">{{ index + 1 }}</span>
          <h3 class="forRankH3">{{ item.keyword }}</h3>
          <div class="forRankSpanDiv">
            <span>관심 {{ item.likeCount }}명</span>
            <span>ㆍ</span>
            <span>게시물 {{ item.commentCount }}개</span>
          </div>
          <div class="forBtn">
            <button class="fotBtnOne" @click="likeKeyword(item.keyword)">
              <span
                class="forBtnSpanOne"
                v-if="likeKeywordList.indexOf(item.keyword) !== -1"
              >
                관심중
              </span>
              <span class="forBtnSpanTwo" v-else> 관심 </span>
            </button>
            <router-link
              class="forkeywordRouter"
              :to="{
                name: 'TopicChildren',
                query: {
                  code: listCode,
                  topic: item.keyword,
                },
              }"
            >
              <button class="forBtnTwo" @click="topicChangeEvent">
                토론하기
              </button>
            </router-link>
          </div>
        </div>
      </div>
    </div>
    <div class="" v-if="topicTwo">
      <Router-view class=""></Router-view>
    </div>
  </div>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { useUserStore } from "@/store/user.js";
import { storeToRefs } from "pinia";
import axios from "@/utils/axios";
import { onMounted, ref } from "vue";
export default {
  setup() {
    const store = useStockStore();
    const userStore = useUserStore();

    let { listCode, modalData } = storeToRefs(store);
    let { nickName, isLogin, topicOne, topicTwo } = storeToRefs(userStore);

    let keywordRankList = ref(null);
    let likeKeywordList = ref([]);

    onMounted(() => {
      if (isLogin.value) {
        axios.get("/api/stock/keywords/likes").then((res) => {
          likeKeywordList.value = res.data;
        });
      }

      topicKeywordRankAxios();
    });

    const likeKeyword = async (themeKeyword) => {
      let apiPath = "keyword-like";

      if (!isLogin.value) {
        modalData.value = true;
        return;
      }

      // 관심중이면
      if (likeKeywordList.value.indexOf(themeKeyword) !== -1) {
        apiPath = "keyword-dislike";
      }

      // 관심 on/off 요청 후 관심 리스트 반환하여 likeKeywordList 에 다시 셋팅
      await axios
        .get("/api/stock/" + apiPath + "/" + themeKeyword)
        .then((res) => {
          likeKeywordList.value = res.data;
        });

      topicKeywordRankAxios();
    };

    const topicChangeEvent = () => {
      topicOne.value = false;
      topicTwo.value = true;
    };

    // 키워드 랭킹 받아오는 함수
    const topicKeywordRankAxios = () => {
      const url = "/api/community/getKeywordsByRanking";
      axios
        .get(url)
        .then((res) => {
          keywordRankList.value = res.data;
          console.log("키워드랭크리스트 값은?", keywordRankList);
        })
        .catch((error) => {
          console.log(error);
        });
    };

    return {
      listCode,
      topicOne,
      topicTwo,
      axios,
      onMounted,
      ref,
      topicChangeEvent,
      topicKeywordRankAxios,
      keywordRankList,
      likeKeyword,
      likeKeywordList,
      nickName,
      isLogin,
    };
  },
};
</script>

<style>
@font-face {
  font-family: "Pretendard-Regular";
  src: url("https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff")
    format("woff");
  font-weight: 500;
  font-style: normal;
}

.keyword-rank {
  margin-left: 15px;
  margin-top: 15px;
}

.keyword-rank-name {
  font-family: "Pretendard-Regular";
  margin-bottom: 30px;
}

.forkeywordRank {
  display: flex;
  font-family: "Pretendard-Regular";
  border-bottom: 1px solid #e5e5e5;
  width: 94%;
  margin-bottom: 1.5rem;
}

.forRankSapn {
  margin-left: 10px;
  font-size: 1.1rem;
  color: #d01411;
  position: relative;
  top: 14px;
  left: 7px;
  width: 22px;
}

.forRankH3 {
  position: relative;
  top: -13px;
  left: 12px;
  width: 170px;
}

.forRankSpanDiv {
  font-size: 0.8rem;
  color: #999999;
  position: relative;
  top: 25px;
  left: -158px;
}

.forRankSapn {
  width: 30px;
}
</style>
