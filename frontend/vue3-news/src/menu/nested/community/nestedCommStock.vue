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
        <div class="create-post-header">
          <p class="nickname">{{ nickName }}</p>
          <div class="post-close-div">
            <button type="button" class="postBtn">
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
          <textarea>안녕하세요</textarea>
        </div>
      </div>
      <div></div>
    </div>

    <div v-for="(item, index) in comments" :key="index">
      <span>{{ item.content }}</span>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from "vue";
import { useStockStore } from "@/store/Stock.js";
import { useUserStore } from "@/store/user.js";
import { storeToRefs } from "pinia";
import axios from "axios";
export default {
  setup() {
    const store = useStockStore();
    const userStore = useUserStore();

    let { stockName, listCode, modalData } = storeToRefs(store);
    let { nickName, isLogin } = storeToRefs(userStore);

    let comments = ref([]);
    let totalCount = ref(0);
    let comNoClick = ref(false);
    let comClick = ref(true);

    onMounted(() => {
      commentData();
    });

    const comClickEvent = () => {
      if (isLogin.value == false) {
        modalData.value = true;
      } else {
        comNoClick.value = false;
        comClick.value = true;
      }
    };

    const commentData = () => {
      axios.get("/api/community/comments/" + listCode.value).then((res) => {
        console.log(res);
        comments.value = res.data.comments;
        totalCount.value = res.data.totalCount;
      });
    };

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
    };
  },
};
</script>

<style scoped>
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

.quill-editor {
  display: flex;
  justify-content: center;
}
</style>
