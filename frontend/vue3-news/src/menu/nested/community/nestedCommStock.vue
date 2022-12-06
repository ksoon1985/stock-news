<template>
  <div class="comm-stock-wrap">
    <div class="comm-stock-panel">
      <h2 class="panel-tag">
        <span class="panel-name"> ${{ stockName }} </span>
      </h2>
      <p class="panel-count">게시물 {{ totalCount }}개</p>
    </div>

    <div class="comm-create-comments">
      <div class="create-comments-header">
        <img class="profile-img" src="" />
        <p class="nickname">{{ nickName }}</p>
      </div>
    </div>

    <div v-for="(item, index) in comments" :key="index">
      <span>{{ item.content }}</span>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import { useStockStore } from "@/store/Stock.js";
import { useUserStore } from "@/store/user.js";
import { storeToRefs } from "pinia";
import axios from "axios";
export default {
  setup() {
    const store = useStockStore();
    const userStore = useUserStore();

    let { stockName, listCode } = storeToRefs(store);
    let { nickName } = storeToRefs(userStore);

    let comments = ref([]);
    let totalCount = ref(0);

    return {
      stockName,
      listCode,
      nickName,
      comments,
      totalCount,
    };
  },

  mounted() {
    axios.get("/api/community/comments/" + this.listCode).then((res) => {
      console.log(res);
      this.comments = res.data.comments;
      this.totalCount = res.data.totalCount;
    });
  },
};
</script>

<style scoped>
.div {
  margin: 0;
  padding: 0;
  border: 0;
  font: inherit;
  font-family: AlphaSans, sans-serif !important;
  text-decoration: none;
}
/* 커뮤니티 - 종목 토론 - 최상단 영역 */
.comm-stock-panel {
  width: 100%;
  height: auto;
  padding: 1rem 1.4rem;
  border-bottom: 4px solid #f2f2f2;
}

/* 커뮤니티 - 종목 토론 - 종목 이름 h2 */
.panel-tag {
  height: 2.4rem;
  line-height: 2.4rem;
  font-size: 1.6rem;
  margin-top: 4px;
  margin-bottom: 4px;
}

/* 커뮤니티 - 종목 토론 - 종목 이름 span */
.panel-name {
  width: 100%;
  display: block;
  font-weight: 500;
  font-size: 1.5rem;
}

/* 커뮤니티 - 종목 토론 - 게시글 총 개수 p */
.panel-count {
  width: 30rem;
  height: 2rem;
  line-height: 2rem;
  font-size: 1rem;
  margin-top: 0;
  margin-bottom: 0;
  color: #999999;
}

/* 코멘트 작성 영역 */
.comm-create-comments {
  width: 100%;
  height: auto;
  padding: 1.6rem 2.4rem 1.2rem;
  border-bottom: 4px solid #f2f2f2;
  -webkit-transition: height 0.3s ease-out;
  transition: height 0.3s ease-out;
}

/* 코멘트 헤더 영역 */
.create-comments-header {
  height: 3.2rem;
  display: flex;
  -webkit-box-pack: start;
  -ms-flex-pack: start;
  justify-content: flex-start;
  position: relative;
}
</style>
