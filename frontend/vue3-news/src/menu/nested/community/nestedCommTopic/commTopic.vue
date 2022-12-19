<template>
  <div class="sub-view-result">
    <div class="topicChat">
      <div class="topicHeaderWrap">
        <button
          type="button"
          class="topicHeaderBtn"
          @click="$router.go(-1), topicChangeClick()"
        >
          <div>
            <svg
              width="24"
              height="24"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
              style="display: block"
            >
              <image
                href="@/assets/svg/left-arrow-svgrepo-com.svg"
                width="24"
                height="24"
              />
            </svg>
          </div>
        </button>
        <div class="topicHeaderName">
          <h3>{{ topicName }}</h3>
          <p class="panel-count">게시물 {{ totalCount }}개</p>
        </div>
      </div>
      <div class="comm-create-comments">
        <div class="create-comments-click" v-if="topicNoClick">
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
            <button class="commentBtn" @click="topicClickEvent">
              이 주제에 대해 어떻게 생각하시나요?
            </button>
          </div>
        </div>
        <div class="create-comments-header" v-if="topicClick">
          <form @submit.prevent="comTopicContentSubmit">
            <div class="create-post-header">
              <div class="heraderNickName">
                <p class="nickname">{{ nickName }}</p>
              </div>
              <div class="post-close-div">
                <button type="button" class="postBtn" @click="topicCloseEvent">
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
    </div>

    <div class="rank-comments-wrap">
      <div class="stock-post">
        <div
          v-for="(item, index) in comments"
          :key="index"
          class="contentDivrank"
        >
          <div class="nickNameWrapRank">
            <h3>{{ item.nickName }}</h3>
          </div>
          <p class="contentRegDateRank">{{ item.regDate }}</p>
          <div class="postContentRank">
            <p>{{ item.content }}</p>
          </div>
          <topicClose
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
import { useStockStore } from "@/store/Stock.js";
import { useUserStore } from "@/store/user.js";
import { storeToRefs } from "pinia";
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "@/utils/axios";
import topicClose from "@/menu/nested/community/nestedCommTopic/nestedTopicChildren/commTopicClose.vue";

export default {
  components: { topicClose },

  setup() {
    const store = useStockStore();
    const userStore = useUserStore();
    const route = useRoute();
    const router = useRouter();

    let { listCode, modalData } = storeToRefs(store);
    let { nickName, isLogin, topicOne, topicTwo } = storeToRefs(userStore);

    let topicName = ref("");
    let totalCount = ref(0);
    let topicNoClick = ref(true);
    let topicClick = ref(false);
    let comTextarea = ref("");
    let comments = ref([]);

    const topicChangeClick = () => {
      topicOne.value = true;
      topicTwo.value = false;
    };

    const topicClickEvent = () => {
      if (isLogin.value == false) {
        modalData.value = true;
      } else {
        topicNoClick.value = false;
        topicClick.value = true;
      }
    };

    const topicCloseEvent = () => {
      topicNoClick.value = true;
      topicClick.value = false;
      comTextarea.value = "";
    };

    onMounted(async () => {
      await router.isReady();
      topicName.value = route.query.topic;
      commentData();
    });

    const comTopicContentSubmit = () => {
      topicName.value = route.query.topic;
      const url = "/api/community/addKeywordComment";
      let topicData = {
        keyword: topicName.value,
        content: comTextarea.value,
      };
      axios.post(url, topicData).then((res) => {
        commentData();
        topicClick.value = false;
        topicNoClick.value = true;
        comTextarea.value = "";
        window.location.reload();
        console.log(res);
      });
    };

    const commentData = () => {
      topicName.value = route.query.topic;
      axios
        .get("/api/community/keyword-comments/" + topicName.value)
        .then((res) => {
          comments.value = res.data.keywordComments;
          totalCount.value = res.data.commentCount;
          console.log("게시글 리스트", comments);
        });
    };

    return {
      listCode,
      topicChangeClick,
      ref,
      onMounted,
      route,
      router,
      topicName,
      totalCount,
      topicNoClick,
      topicClick,
      nickName,
      isLogin,
      topicClickEvent,
      topicCloseEvent,
      comTopicContentSubmit,
      comTextarea,
      commentData,
      comments,
      topicOne,
      topicTwo,
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

.topicHeaderWrap {
  margin-left: 1rem;
  margin-top: 1rem;
  height: 5rem;
  border-bottom: 1px solid #e5e5e5;
  display: flex;
}

.topicHeaderBtn {
  border: none;
  background-color: #ffffff;
  cursor: pointer;
}

.topicHeaderName {
  position: relative;
  top: -7px;
  left: 10px;
  font-family: "Pretendard-Regular";
}

.topicHeaderName {
  font-size: 1.1rem;
}

.panel-count {
  position: relative;
  top: -11px;
  color: #999999;
  font-size: 0.9rem;
}

.comm-create-comments {
  width: 90%;
  padding: 1.6rem 2.4rem 1.2rem;
  border-bottom: 4px solid #f2f2f2;
  text-align: center;
  word-break: break-all;
  height: auto;
  font-family: "Pretendard-Regular";
}

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

.contentRegDateRank {
  position: relative;
  top: -16px;
  left: 12px;
  color: #999999;
  font-size: 0.9rem;
  font-family: "Pretendard-Regular";
}

.contentDivrank {
  height: auto;
  border-bottom: 4px solid #f2f2f2;
  font-family: "Pretendard-Regular";
}

.nickNameWrapRank {
  margin-left: 10px;
}

.postContentRank {
  margin-left: 10px;
  margin-bottom: 1.6rem;
}
</style>
