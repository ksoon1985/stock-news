<template>
  <div class="commentMenu">
    <div class="post-bubble-div">
      <button type="button" class="postBtn" @click="comChEvent(childrenId)">
        답글
      </button>
      <span class="commPropsCount">{{ childrenCount }}개</span>
    </div>
    <button class="comMenuBtn" @click="commChildrenChange">답글달기</button>
    <button class="comDelBtn" @click="modalOpen = true" v-if="delBtn">
      삭제
    </button>
  </div>
  <div class="comment-replay-view" v-if="replayViewStatus">
    <div
      class="commentForReplay"
      v-for="(item, index) in replayCommentArray"
      :key="index"
    >
      <div class="childrenHeader">
        <div class="chlidrenNickName">
          <span>{{ item.nickName }}</span>
        </div>
        <span class="childrenDate">{{ item.regDate }}</span>
      </div>
      <div class="chlidrenContent">
        <p>{{ item.content }}</p>
      </div>
    </div>
  </div>
  <form @submit.prevent="childrenInputSubmit(childrenId)">
    <div class="create-reply" v-if="commChildrenStatus">
      <div class="reply-input">
        <input
          type="text"
          class="rp-input"
          v-model="rpInput"
          placeholder="답글 달기"
        />
      </div>
      <button class="rp-btn">게시</button>
    </div>
  </form>

  <teleport to="#teleport-menu-modal">
    <div class="menu-modal-wrap" v-if="modalOpen">
      <div class="menu-modal">
        <div class="menu-modal-close"><h4>삭제</h4></div>
        <div class="menu-modal-p">삭제하시겠습니까?</div>
        <div class="menu-modal-Btn">
          <button class="modalBtnOne" @click="modalOpen = false">취소</button>
          <form @submit.prevent="commDelSubit(childrenId)">
            <button class="modalBtnTwo">확인</button>
          </form>
        </div>
      </div>
    </div>
  </teleport>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { useUserStore } from "@/store/user.js";
import { storeToRefs } from "pinia";
import axios from "axios";
import { useRoute } from "vue-router";
import { ref, onMounted, watch } from "vue";
export default {
  emits: ["event1", "event2"],

  props: {
    childrenCount: Number,
    childrenId: String,
    childrenNick: String,
  },

  setup(props, context) {
    const { emit } = context;
    const store = useStockStore();
    const route = useRoute();
    const userStore = useUserStore();

    let { listCode } = storeToRefs(store);
    let { nickName, isLogin } = storeToRefs(userStore);

    let commChildrenStatus = ref(false);
    let replayViewStatus = ref(false);
    let replayCommentArray = ref(null);
    let rpInput = ref("");
    let delBtn = ref(false);
    let modalOpen = ref(false);

    onMounted(() => {
      delBtnChange();
    });

    watch(isLogin, () => {
      delBtnChange();
    });

    const commChildrenChange = () => {
      commChildrenStatus.value = !commChildrenStatus.value;
    };

    const delBtnChange = () => {
      if (nickName.value == props.childrenNick) {
        delBtn.value = true;
      } else {
        delBtn.value = false;
      }
    };

    const comChEvent = (id) => {
      replayViewStatus.value = !replayViewStatus.value;
      console.log(id);
      const url = "/api/community/comments/" + id;
      let comData = {
        id: id,
      };

      if (replayViewStatus.value == true) {
        axios
          .post(url, comData)
          .then((res) => {
            replayCommentArray.value = res.data;
          })
          .catch((error) => {
            console.log(error);
          });
      }
    };

    const comChEventTwo = (id) => {
      const url = "/api/community/comments/" + id;
      let comData = {
        id: id,
      };

      axios
        .post(url, comData)
        .then((res) => {
          replayCommentArray.value = res.data;
        })
        .catch((error) => {
          console.log(error);
        });
    };

    const childrenInputSubmit = (id) => {
      listCode.value = route.query.code;
      const url = "/api/community/addComment";
      let comData = {
        code: listCode.value,
        content: rpInput.value,
        parentId: id,
      };
      axios
        .post(url, comData)
        .then((res) => {
          replayViewStatus.value = true;
          rpInput.value = "";
          comChEventTwo(id);
          emit("event1");
          console.log(res);
        })
        .catch((error) => {
          console.log(error);
        });
    };

    const commDelSubit = (id) => {
      const url = "/api/community/delComment";
      let comData = {
        id: id,
      };
      axios.post(url, comData).then((res) => {
        console.log(res);
        emit("event1", "event2");
      });
    };

    return {
      listCode,
      axios,
      ref,
      onMounted,
      watch,
      route,
      commChildrenStatus,
      commChildrenChange,
      replayViewStatus,
      comChEvent,
      replayCommentArray,
      childrenInputSubmit,
      rpInput,
      comChEventTwo,
      props,
      context,
      commDelSubit,
      delBtn,
      isLogin,
      delBtnChange,
      modalOpen,
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
.create-reply {
  display: flex;
}
.commentMenu {
  display: flex;
  position: relative;
  left: -1px;
}
.comMenuBtn {
  color: #999999;
  border: none;
  background-color: #ffffff;
  margin-left: 10px;
  cursor: pointer;
}

.create-reply {
  margin-left: 10px;
  margin-top: 5px;
  height: auto;
  border: none;
  border-bottom: 1px solid #e5e5e5;
  width: 100%;
}

.rp-input {
  border: none;
  width: 33rem;
}

.rp-btn {
  font-family: "Pretendard-Regular";
  width: 3.5rem;
  height: 1.5rem;
  border-radius: 4px;
  font-size: 1rem;
  background-color: #d01411;
  border: none;
  color: #fef6f6;
  cursor: pointer;
}

.post-bubble-div {
  position: relative;
  top: -2px;
  left: -3px;
}

.commPropsCount {
  font-size: 0.8rem;
}

.comDelBtn {
  position: relative;
  top: 1px;
  border: none;
  background: #ffffff;
  color: #999999;
  cursor: pointer;
}

.postBtn {
  margin-top: 3px;
  background-color: #ffffff;
  border: none;
  cursor: pointer;
  color: #d01411;
  font-size: 0.8rem;
}

.comment-replay-view {
  width: 100%;
  margin-left: 40px;
  margin-top: 5px;
}

.chlidrenNickName {
  color: #1c1c1c;
  font-size: 0.8rem;
  font-weight: bold;
}

.childrenDate {
  font-size: 0.8rem;
  color: #999999;
}

.chlidrenContent {
  font-size: 0.8rem;
  margin: 8px 0px;
}

.menu-modal-wrap {
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
  z-index: 990;
}

.menu-modal {
  width: 20%;
  height: 15%;
  border: 1px solid #e5e5e5;
  background-color: #ffffff;
  border-radius: 8px;
}

.menu-modal-Btn {
  display: flex;
  justify-content: flex-end;
  margin-top: 5px;
}

.menu-modal-close {
  margin-left: 10px;
}

.menu-modal-p {
  margin-left: 10px;
}

.modalBtnOne {
  background-color: #fde8e7;
  color: #d01411;
  border: none;
  width: 4.5rem;
  height: 1.5rem;
  border-radius: 4px;
  margin-right: 5px;
}

.modalBtnTwo {
  color: #fef6f6;
  background-color: #d01411;
  border: none;
  width: 4.5rem;
  height: 1.5rem;
  border-radius: 4px;
}

.menu-modal-Btn {
  margin-top: 20px;
  margin-right: 15px;
}
</style>
