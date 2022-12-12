<template>
  <div class="commentMenu">
    <div class="post-bubble-div">
      <button type="button" class="postBtn" @click="comChEvent(childrenId)">
        답글
      </button>
      <span class="commPropsCount">{{ childrenCount }}개</span>
    </div>
    <button class="comMenuBtn" @click="commChildrenChange">답글달기</button>
    <button class="comDelBtn" @click="commChildAlert(childrenId)" v-if="delBtn">
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
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { useUserStore } from "@/store/user.js";
import { storeToRefs } from "pinia";
import axios from "axios";
import { useRoute } from "vue-router";
import { ref, onMounted, watch } from "vue";
export default {
  emits: ["event1"],

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

    onMounted(() => {
      delBtnChange();
    });

    watch(isLogin, () => {
      delBtnChange();
    });

    const commChildAlert = (id) => {
      alert("정말로 삭제하시겠습니까?");
      commDelSubit(id);
    };

    const commChildrenChange = () => {
      commChildrenStatus.value = !commChildrenStatus.value;
    };

    const delBtnChange = () => {
      if (nickName.value == props.childrenNick) {
        delBtn.value = true;
      } else {
        delBtn.value = false;
        console.log("닉네임 값은", props.childrenNick);
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
        emit("event1");
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
      commChildAlert,
    };
  },
};
</script>

<style scoped>
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
  width: 3.5rem;
  height: 1.5rem;
  border-radius: 4px;
  font-size: 1rem;
  background-color: #d01411;
  border: none;
  color: #fef6f6;
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
</style>
