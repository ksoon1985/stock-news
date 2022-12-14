<template>
  <div class="sub-view-result">
    <div class="comm-stock-panel">
      <h2 class="panel-tag">
        <span class="panel-name"> ${{ stockName }} </span>
      </h2>
      <p class="panel-count">게시물 {{ totalCount }}개</p>
    </div>

    <div class="comm-create-comments">
      <div class="create-comments-click" v-if="comNoClick">
        <div class="comments-icon">
          <svg
            width="30"
            height="30"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
            style="display: block"
          >
            <image
              href="@/assets/svg/group-of-people-svgrepo-com.svg"
              width="30"
              height="30"
            />
          </svg>
        </div>
        <div class="comments-btn">
          <button class="commentBtn" @click="comClickEvent">
            이 종목에 대해 어떻게 생각하시나요?
          </button>
        </div>
      </div>
      <div class="create-comments-header" v-if="comClick">
        <form @submit.prevent="comContentSubmit">
          <div class="create-post-header">
            <div class="heraderNickName">
              <p class="nickname">{{ nickName }}</p>
            </div>
            <div class="post-close-div">
              <button type="button" class="postBtn" @click="comCloseEvent">
                <div class="post-close">
                  <svg
                    width="20"
                    height="20"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                    style="display: block"
                  >
                    <image
                      href="@/assets/svg/x-svgrepo-com.svg"
                      width="20"
                      height="20"
                    />
                  </svg>
                </div>
              </button>
            </div>
          </div>
          <div class="quill-editor">
            <textarea class="quillTextArea" v-model="comTextarea"></textarea>
          </div>
          <div class="quill-btn">
            <button class="quillBtn" @click="commentData">게시</button>
          </div>
        </form>
      </div>
    </div>

    <div class="comments-wrap">
      <div class="stock-post">
        <div v-for="(item, index) in comments" :key="index" class="contentDiv">
          <div class="nickNameWrap">
            <h3>{{ item.nickName }}</h3>
          </div>
          <p class="contentRegDate">{{ item.regDate }}</p>
          <div class="postContent">
            <p>{{ item.content }}</p>
          </div>
          <StockCom
            :subCount="item.subCount"
            :subId="item._id"
            :subNickName="item.nickName"
            @event1="commentData()"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, onUpdated, watch } from "vue";
import { useStockStore } from "@/store/Stock.js";
import { useUserStore } from "@/store/user.js";
import { useRoute } from "vue-router";
import { storeToRefs } from "pinia";
import axios from "axios";
import StockCom from "@/menu/nested/community/nestedCommStockCom/commStockCom.vue";

export default {
  components: { StockCom },

  setup() {
    const store = useStockStore();
    const userStore = useUserStore();
    const route = useRoute();

    let { stockName, listCode, modalData, commSubCount } = storeToRefs(store);
    let { nickName, isLogin } = storeToRefs(userStore);

    let comments = ref([]);
    let totalCount = ref(0);
    let comNoClick = ref(true);
    let comClick = ref(false);
    let comTextarea = ref("");
    let commentsStatus = ref(false);

    onMounted(() => {
      commentData();
    });

    const comContentSubmit = () => {
      listCode.value = route.query.code;
      const url = "/api/community/addComment";
      let comData = {
        code: listCode.value,
        content: comTextarea.value,
      };
      axios
        .post(url, comData)
        .then((res) => {
          commentData();
          comNoClick.value = true;
          comClick.value = false;
          console.log(res);
        })
        .catch((error) => {
          console.log(error);
        });
    };

    const comStatusClickEvent = () => {
      commentsStatus.value = !commentsStatus.value;
    };

    const comClickEvent = () => {
      if (isLogin.value == false) {
        modalData.value = true;
      } else {
        comNoClick.value = false;
        comClick.value = true;
      }
    };

    const comCloseEvent = () => {
      comNoClick.value = true;
      comClick.value = false;
    };

    const commentData = () => {
      listCode.value = route.query.code;
      axios.get("/api/community/comments/" + listCode.value).then((res) => {
        console.log(res);
        comments.value = res.data.comments;
        totalCount.value = res.data.totalCount;
      });
    };
    watch(() => {});

    return {
      stockName,
      listCode,
      nickName,
      comments,
      totalCount,
      commentData,
      comNoClick,
      comClick,
      modalData,
      isLogin,
      comClickEvent,
      comCloseEvent,
      comTextarea,
      comContentSubmit,
      commentsStatus,
      comStatusClickEvent,
      commSubCount,
      onUpdated,
      watch,
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

.comm-stock-wrap {
  width: 100%;
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
  font-family: "Pretendard-Regular";
}

/* 커뮤니티 - 종목 토론 - 종목 이름 span */
.panel-name {
  width: 100%;
  display: block;
  font-weight: 500;
  font-size: 1.5rem;
  font-family: "Pretendard-Regular";
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
  font-family: "Pretendard-Regular";
}

/* 코멘트 작성 영역 */
.comm-create-comments {
  width: 90%;
  padding: 1.6rem 2.4rem 1.2rem;
  border-bottom: 4px solid #f2f2f2;
  text-align: center;
  word-break: break-all;
  height: auto;
  font-family: "Pretendard-Regular";
}

/* 코멘트 헤더 영역 */
.create-comments-header {
  height: auto;
  display: flex;
  justify-content: flex-start;
  position: relative;
  overflow-x: hidden;
  overflow-y: auto;
  word-break: break-all;
  align-items: center;
  flex-wrap: wrap;
}

.create-comments-click {
  display: flex;
}

.commentBtn {
  margin-left: 25px;
  border-radius: 1.6rem;
  font-size: 1rem;
  color: #525252;
  background-color: #f2f2f2;
  width: 30rem;
  height: 2rem;
  border: none;
}

.commentBtn:hover {
  background-color: #d2d2d2;
}

.create-post-header {
  display: flex;
  justify-content: space-between;
}

.post-close-div {
  margin-left: 30rem;
}

.postBtn {
  border: none;
  background: #ffffff;
}

.postBtn:hover {
  background-color: #d2d2d2;
}

.heraderNickName {
  width: auto;
  position: relative;
  top: -19px;
  font-size: 1.2rem;
  color: #1c1c1c;
  font-family: "Pretendard-Regular";
}

.quillTextArea {
  width: 35rem;
  height: 5rem;
  border: none;
  border-bottom: 2px solid #f2f2f2;
}

.quill-btn {
  margin-top: 10px;
  margin-right: 1rem;
  width: 100%;
  text-align: right;
}

.quillBtn {
  width: 5rem;
  height: 2rem;
  border-radius: 4px;
  background-color: #d01411;
  color: #fef6f6;
  border: none;
  cursor: pointer;
}

.quillBtn:hover {
  color: #1c1c1c;
}

/* .postContent {
  border-bottom: 2px solid #f2f2f2;
} */

.contentRegDate {
  position: relative;
  top: -16px;
  left: 12px;
  color: #999999;
  font-size: 0.9rem;
  font-family: "Pretendard-Regular";
}

.contentDiv {
  height: auto;
  border-bottom: 4px solid #f2f2f2;
  font-family: "Pretendard-Regular";
}

.nickNameWrap {
  margin-left: 10px;
}

.postContent {
  margin-left: 10px;
  margin-bottom: 1.6rem;
}
</style>
