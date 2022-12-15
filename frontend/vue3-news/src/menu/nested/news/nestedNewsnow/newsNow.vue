<template>
  <div class="sub-view-result">
    <div class="newsSubRouter">
      <div class="newsSubHeaderWrap">
        <button
          type="button"
          class="newsSubHeaderBtn"
          @click="$router.go(-1), keywordClick()"
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
        <div class="newsSubHeaderName"></div>
      </div>
    </div>
  </div>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
import { useRoute } from "vue-router";
export default {
  setup() {
    const store = useStockStore();
    const route = useRoute();

    let { listCode, keywordOne, keywordTwo } = storeToRefs(store);

    const keywordClick = () => {
      keywordOne.value = true;
      keywordTwo.value = false;
      console.log("키워드 화면의 값은", keywordOne.value);
    };

    return {
      listCode,
      keywordOne,
      keywordTwo,
      keywordClick,
      useRoute,
      route,
    };
  },
};
</script>

<style scoped>
.newsSubHeaderWrap {
  margin-left: 1rem;
  margin-top: 1rem;
  height: 5rem;
  border-bottom: 1px solid #e5e5e5;
}

.newsSubHeaderBtn {
  border: none;
  background-color: #ffffff;
}
</style>
