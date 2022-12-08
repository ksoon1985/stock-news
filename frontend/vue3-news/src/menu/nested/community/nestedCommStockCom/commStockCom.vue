<template>
  <div class="post-menu">
    <div class="post-bubble-div">
      <button type="button" class="postBtn" @click="commStatEvent">
        <div class="post-close">
          <svg
            width="17"
            height="17"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
            style="display: block"
          >
            <image
              href="@/assets/svg/speech-bubble-svgrepo-com.svg"
              width="17"
              height="17"
            />
          </svg>
        </div>
      </button>
    </div>
    <div class="post-menu-btn">
      <button class="postMenuBtn">삭제하기</button>
    </div>
  </div>
  <div class="con-comment" v-if="commStatus">
    <div class="for-empty">
      <div class="for-empty-wrap">
        <p class="for-empty-text">작성된 댓글이 없습니다.</p>
      </div>
    </div>
    <div class="create-comment">
      <div class="post-people-div">
        <div class="post-close">
          <svg
            width="35"
            height="35"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
            style="display: block"
          >
            <image
              href="@/assets/svg/people-svgrepo-com.svg"
              width="35"
              height="35"
            />
          </svg>
        </div>
      </div>
      <div class="ql-toolbar">
        <input type="text" class="ql-input" placeholder=" 댓글 달기" />
      </div>
      <button class="ql-btn">게시</button>
    </div>
  </div>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
import axios from "axios";
import { useRoute } from "vue-router";
import { ref, onMounted } from "vue";

export default {
  setup() {
    const store = useStockStore();

    let { listCode } = storeToRefs(store);

    let commStatus = ref(false);

    const commStatEvent = () => {
      commStatus.value = !commStatus.value;
    };

    return {
      listCode,
      axios,
      useRoute,
      onMounted,
      commStatus,
      commStatEvent,
    };
  },
};
</script>

<style scoped>
.post-menu {
  border-top: 1px solid #f2f2f2;
  display: flex;
  justify-content: space-between;
  padding: 3px;
}

.postMenuBtn {
  width: 5rem;
  height: 1.5rem;
  border-radius: 4px;
  background-color: #d01411;
  color: #fef6f6;
  border: none;
}

.post-menu-btn {
  margin-right: 1.5rem;
}

.post-bubble-div {
  margin-left: 1rem;
}

.con-comment {
  border-top: 1px solid #f2f2f2;
}

.for-empty-text {
  color: #999999;
}

.for-empty-wrap {
  width: 100%;
  position: relative;
  top: 50%;
  left: 38%;
}

.create-comment {
  width: 100%;
  height: auto;
  display: flex;
  justify-content: flex-start;
  border-top: 1px solid #f2f2f2;
}

.ql-input {
  width: 33rem;
  border: none;
  font-size: 1.1rem;
  margin-top: 7px;
}

.ql-btn {
  width: 3rem;
  height: 1.5rem;
  border-radius: 4px;
  background-color: #d01411;
  color: #fef6f6;
  border: none;
  margin-top: 6px;
}
</style>
