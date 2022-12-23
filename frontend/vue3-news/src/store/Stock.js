import { defineStore } from "pinia";
import { ref } from "vue";

export const useStockStore = defineStore("stock", () => {
  let stockCode = ref(null); //종목요약 api 배열
  let stockNameMarket = ref(null); //종목 이름 마켓 api 배열
  let stockName = ref(null); //종목 이름
  let stockMarket = ref(null); //종목 마켓
  let listCode = ref("005930"); //쿼리스트링 종목코드
  let contentStockPrice = ref(null); //컨텐츠 종목 일일 가격 배열
  let stockPrice = ref(0); // 종목 금일 종가 가격
  let stockPriceTwo = ref(0); // 종목 전일 종가 가격
  let stockMinus = ref(null); // 종목 금일 - 전일 종가 가격
  let stockAddStockPrice = ref(null); // 종목 추가 일일 가격 배열
  let stockAddPrice = ref([]); // 종목 추가 금일 종가 가격
  let stockAddPriceTwo = ref([]); // 종목 추가 전일 종가 가격
  let stockAddMinus = ref([]);
  let stockVolume = ref(null); // 종목 종가 거래량
  let listStockMarketCap = ref(null); //종목요약 마켓금액
  let listStockMarketRanking = ref(null); // 종목요약 기업순위
  let listStockNumberOfStocks = ref(null); // 종목요약 주식수
  let listStockForeignerRatio = ref(null); // 종목요약 외국인 비중
  let listStockIndustry = ref(null); //종목요약 산업군
  let listStockIndustryDetail = ref(null); //종목요약 세부산업군
  let listLowYear = ref(null); // 종목요약 52주 최저
  let listHighYear = ref(null); // 종목요약 52주 최고
  let listSummaryInfo = ref(null); // 종목요약 기업개요
  let stockInformationData = ref(null); //재무정보 연간 데이터 []
  let stockInformationDataQyarter = ref(null); //재무정보 분기 데이터 []
  let modalData = ref(false); //모달창 전체
  let searchNewsParams = ref({}); // 뉴스 검색 조건
  let keyWordList = ref(null); // 키워드 리스트
  // let keywordOne = ref(true); // 키워드 화면 리스트
  // let keywordTwo = ref(false); // 키워드 화면 뉴스조회 화면
  // let topicOne = ref(true); // 주제토론 화면 리스트
  // let topicTwo = ref(false); // 주제토론 토론 채팅 화면
  let topicName = ref(null); // 토픽이름 pinia
  let realTimeData = ref(null); // 실시간뉴스 데이터들

  return {
    stockCode,
    stockName,
    stockMarket,
    listCode,
    listStockMarketCap,
    stockNameMarket,
    contentStockPrice,
    stockPrice,
    stockPriceTwo,
    stockMinus,
    stockVolume,
    stockAddStockPrice,
    stockAddPrice,
    stockAddPriceTwo,
    stockAddMinus,
    listStockMarketRanking,
    listStockNumberOfStocks,
    listStockForeignerRatio,
    listStockIndustry,
    listStockIndustryDetail,
    listLowYear,
    listHighYear,
    listSummaryInfo,
    stockInformationData,
    stockInformationDataQyarter,
    modalData,
    searchNewsParams,
    keyWordList,
    // keywordOne,
    // keywordTwo,
    // topicOne,
    // topicTwo,
    topicName,
    realTimeData,
  };
});
