<template>
  <div class="sub-view-result">
    <div class="topic-news">
      <div class="keyWordStockName">
        <span class="keyword-stock-name">{{ stockName }}</span>
        <span class="keyword-keyword">키워드</span>
      </div>
      <div class="for-keyword-news">
        <div
          class="forKeyWord"
          v-for="(item, index) in keyWordList"
          :key="index"
        >
          <span class="forKeyWordSpan">{{ index + 1 }}</span>
          <h3 class="themeKeywords">{{ item }}</h3>
          <div class="forBtn">
            <button class="fotBtnOne">관심</button
            ><button class="forBtnTwo" @click="keywordNewsSearch(item)">
              뉴스조회
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useStockStore } from "@/store/Stock.js";
import { storeToRefs } from "pinia";
import { onMounted, ref } from "vue-demi";
import axios from "@/utils/axios";
export default {
  setup() {
    const store = useStockStore();
    const keyWordList = ref([]);
    let { listCode, stockName } = storeToRefs(store);

    onMounted(() => {
      // stockName loading issue
      // -> setTimeout 0.5s lazy loading
      setTimeout(() => {
        axios
          .get("/api/stock/stock-themeKeyword/" + listCode.value)
          .then((res) => {
            keyWordList.value = res.data;
          });
      }, 500);
    });

    const keywordNewsSearch = (themeKeyword) => {
      let today = new Date();
      // yyyy-mm-dd
      let year = today.getFullYear();
      let month = today.getMonth() + 1;
      let date = today.getDate() - 1;

      today = year + "-" + month + "-" + date;

      let reqDto = {
        searchTerm: stockName.value,
        themeKeyword: themeKeyword,
        fromDate: "2020-01-01",
        toDate: today,
      };
      console.log(reqDto);
      axios.post("/api/news/getSearchNews", reqDto).then((res) => {
        console.log(res.data);
      });
    };

    return {
      listCode,
      stockName,
      keyWordList,
      keywordNewsSearch,
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

.sub-view-result {
  height: 90%;
  width: 100%;
  -ms-overflow-style: none;
}

.sub-view-result::-webkit-scrollbar {
  display: none;
}

.keyWordStockName {
  display: flex;
}

.topic-news {
  margin-left: 15px;
  margin-top: 15px;
}

.keyWordStockName {
  font-family: "Pretendard-Regular";
  margin-bottom: 30px;
}

.keyword-stock-name {
  font-size: 1.2rem;
}

.keyword-keyword {
  margin-left: 5px;
  margin-top: 5px;
  color: #999999;
}

.forKeyWord {
  display: flex;
  font-family: "Pretendard-Regular";
  border-bottom: 1px solid #e5e5e5;
  width: 94%;
  margin-bottom: 1.5rem;
}

.forKeyWordSpan {
  margin-left: 10px;
  font-size: 1.1rem;
  color: #d01411;
  position: relative;
  top: 14px;
  left: 7px;
}

.themeKeywords {
  position: relative;
  top: -5px;
  left: 37px;
  width: 50%;
}

.forKeywordBtn {
  position: relative;
  top: 7px;
  left: 100px;
}

.forBtn {
  position: relative;
  top: 12px;
  left: 80px;
}

.fotBtnOne {
  width: 5rem;
  height: 1.7rem;
  border-radius: 4px;
  background-color: #d01411;
  font-size: 1rem;
  font-weight: 500;
  color: #fef6f6;
  border: none;
  margin-right: 10px;
  font-family: "Pretendard-Regular";
  cursor: pointer;
}

.forBtnTwo {
  width: 5rem;
  height: 1.7rem;
  border-radius: 4px;
  background-color: #d01411;
  font-size: 1rem;
  font-weight: 500;
  color: #fef6f6;
  border: none;
  font-family: "Pretendard-Regular";
  cursor: pointer;
}
</style>
