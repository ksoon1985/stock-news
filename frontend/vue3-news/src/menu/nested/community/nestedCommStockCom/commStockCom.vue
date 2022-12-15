<template>
  <div class="post-menu">
    <div class="post-bubble-div">
      <button type="button" class="postBtnOne" @click="commStatEvent(subId)">
        <div class="post-close">
          <svg
            width="17"
            height="17"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
            style="display: block"
            class="postBtnSvg"
          >
            <image
              href="@/assets/svg/speech-bubble-svgrepo-com.svg"
              width="17"
              height="17"
              fill="red"
            />
          </svg>
        </div>
      </button>
      <span v-if="SubCountChange" class="commPropsCount">{{ subCount }}</span>
    </div>

    <div class="post-menu-btn">
      <button class="postMenuBtn" v-if="delBtn" @click="modalCloseEvent">
        삭제하기
      </button>
    </div>
  </div>
  <div class="con-comment" v-if="commStatus">
    <div class="for-empty" v-if="forEmpty">
      <div class="for-empty-wrap">
        <p class="for-empty-text">작성된 댓글이 없습니다.</p>
      </div>
    </div>
    <div class="for-comment">
      <div
        class="forComment"
        v-for="(item, index) in subCommentArray"
        :key="index"
      >
        <div class="commentHeader">
          <div class="commentNickName">
            <span>{{ item.nickName }}</span>
          </div>
          <span class="commentDate">{{ item.regDate }}</span>
        </div>
        <div class="commentContent">
          <p>{{ item.content }}</p>
        </div>
        <comChildren
          :childrenCount="item.subCount"
          :childrenId="item._id"
          :childrenNick="item.nickName"
          @event1="commStatEvent()"
          @event2="countChangeEvent()"
        />
      </div>
    </div>
    <form @submit.prevent="comInputSubmit(subId)">
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
          <input
            type="text"
            class="ql-input"
            v-model="qlInput"
            placeholder=" 댓글 달기"
            @click="commClickEvent"
          />
        </div>
        <button class="ql-btn" @click="countChangeClick">게시</button>
      </div>
    </form>
  </div>

  <teleport to="#teleport-menu-modal">
    <div class="menu-modal-wrap" v-if="modalOpen">
      <div class="menu-modal">
        <div class="menu-modal-close"><h4>삭제</h4></div>
        <div class="menu-modal-p">삭제하시겠습니까?</div>
        <div class="menu-modal-Btn">
          <button class="modalBtnOne" @click="modalOpen = false">취소</button>
          <form @submit.prevent="comDelSubit(subId)">
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
import comChildren from "@/menu/nested/community/nestedCommStockCom/commStockComChildren.vue";

export default {
  components: { comChildren },

  emits: ["event1"],

  props: {
    subCount: Number,
    subId: String,
    subNickName: String,
  },
  setup(props, context) {
    const { emit } = context;
    const store = useStockStore();
    const route = useRoute();
    const userStore = useUserStore();

    let { listCode, commSubCount, modalData } = storeToRefs(store);
    let { nickName, isLogin } = storeToRefs(userStore);

    let commStatus = ref(false);
    let SubCountChange = ref(false);
    let subCommentArray = ref(null);
    let forEmpty = ref(true);
    let forComment = ref(false);
    let forCommentName = ref("");
    let forCommentDate = ref("");
    let forCommentContent = ref("");
    let qlInput = ref("");
    let rpInput = ref("");
    let delBtn = ref(false);
    let modalOpen = ref(false);

    onMounted(() => {
      countChangeEvent();
      delBtnChange();
    });

    watch(isLogin, () => {
      delBtnChange();
    });

    const commClickEvent = () => {
      if (isLogin.value == false) {
        modalData.value = true;
      }
    };

    const modalCloseEvent = () => {
      modalOpen.value = true;
    };

    const delBtnChange = () => {
      if (nickName.value == props.subNickName) {
        delBtn.value = true;
      } else {
        delBtn.value = false;
      }
    };

    const countChangeEvent = () => {
      if (props.subCount !== 0) {
        SubCountChange.value = true;
        forEmpty.value = false;
        forComment.value = true;
      } else if (props.subCount === 0) {
        forEmpty.value = true;
        forComment.value = false;
        SubCountChange.value = false;
      }
    };

    watch(props.subCount, () => {
      countChangeEvent();
    });

    const countChangeClick = () => {
      SubCountChange.value = true;
    };

    const commStatEvent = (id) => {
      commStatus.value = !commStatus.value;

      const url = "/api/community/comments/" + id;
      let comData = {
        id: id,
      };

      if (commStatus.value == true) {
        axios
          .post(url, comData)
          .then((res) => {
            subCommentArray.value = res.data;
            emit("event1");
          })
          .catch((error) => {
            console.log(error);
          });
      }
    };

    const commStatEventTwo = (id) => {
      const url = "/api/community/comments/" + id;
      let comData = {
        id: id,
      };

      axios
        .post(url, comData)
        .then((res) => {
          subCommentArray.value = res.data;
          emit("event1");
        })
        .catch((error) => {
          console.log(error);
        });
    };

    const comInputSubmit = (id) => {
      listCode.value = route.query.code;
      const url = "/api/community/addComment";
      let comData = {
        code: listCode.value,
        content: qlInput.value,
        parentId: id,
      };
      axios
        .post(url, comData)
        .then((res) => {
          forEmpty.value = false;
          forComment.value = true;
          qlInput.value = "";
          commStatEventTwo(id);
          emit("event1");
          console.log(res);
        })
        .catch((error) => {
          console.log(error);
        });
    };

    const comDelSubit = (id) => {
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
      useRoute,
      onMounted,
      commStatus,
      commStatEvent,
      commSubCount,
      props,
      SubCountChange,
      subCommentArray,
      countChangeEvent,
      forEmpty,
      forComment,
      forCommentName,
      forCommentDate,
      forCommentContent,
      qlInput,
      route,
      comInputSubmit,
      watch,
      commStatEventTwo,
      comDelSubit,
      emit,
      countChangeClick,
      rpInput,
      nickName,
      delBtn,
      delBtnChange,
      isLogin,
      commClickEvent,
      modalOpen,
      modalCloseEvent,
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

.post-menu {
  border-top: 1px solid #f2f2f2;
  display: flex;
  justify-content: space-between;
  padding: 3px;
}

.postMenuBtn {
  font-family: "Pretendard-Regular";
  width: 5rem;
  height: 1.5rem;
  border-radius: 4px;
  background-color: #d01411;
  color: #fef6f6;
  border: none;
  cursor: pointer;
}

.postMenuBtn:hover {
  opacity: 0.5;
}

.post-menu-btn {
  margin-right: 1.5rem;
}

.post-bubble-div {
  margin-left: 1rem;
}

.postBtnOne {
  margin-top: 3px;
  background-color: #ffffff;
  border: none;
  cursor: pointer;
}

.postBtnSvg:hover {
  filter: invert(16%) sepia(89%) saturate(6054%) hue-rotate(358deg)
    brightness(97%) contrast(113%);
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

.forComment {
  width: 100%;
  height: auto;
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
  font-family: "Pretendard-Regular";
  width: 3rem;
  height: 1.5rem;
  border-radius: 4px;
  background-color: #d01411;
  color: #fef6f6;
  border: none;
  margin-top: 6px;
  cursor: pointer;
}

.for-comment {
  margin-top: 20px;
  margin-left: 10px;
  margin-bottom: 20px;
}

.forComment {
  margin-bottom: 20px;
}

.commentNickName {
  color: #1c1c1c;
  font-size: 0.8rem;
  font-weight: bold;
}

.commentDate {
  font-size: 0.8rem;
  color: #999999;
}

.commentContent {
  font-size: 0.8rem;
  margin: 8px 0px;
}

.commPropsCount {
  font-size: 1rem;
  position: relative;
  top: -1px;
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
