import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/layout/HomeView.vue";
import MenuNews from "@/menu/menuNews.vue";
import MenuStock from "@/menu/menuStock.vue";
import MenuCommunity from "@/menu/menuCommunity.vue";
import MenuRank from "@/menu/menuRank.vue";
import NestedCommStock from "@/menu/nested/community/nestedCommStock.vue";
import NestedCommTopic from "@/menu/nested/community/nestedCommTopic.vue";
import NestedNewsNow from "@/menu/nested/news/nestedNewsNow.vue";
import NestedNewsPast from "@/menu/nested/news/nestedNewsPast.vue";
import NestedNewsRealTime from "@/menu/nested/news/nestedNewsRealTime.vue";
import NestedNewsAdd from "@/menu/nested/news/nestedNewsAdd.vue";
import realTimeAll from "@/menu/nested/news/realTimeCartegory/realTimeAll.vue";
import realTimeOne from "@/menu/nested/news/realTimeCartegory/realTimeOne.vue";
import realTimeTwo from "@/menu/nested/news/realTimeCartegory/realTimeTwo.vue";
import realTimeThree from "@/menu/nested/news/realTimeCartegory/realTimeThree.vue";
import realTimeFour from "@/menu/nested/news/realTimeCartegory/realTimeFour.vue";
import realTimeFive from "@/menu/nested/news/realTimeCartegory/realTimeFive.vue";
import realTimeSix from "@/menu/nested/news/realTimeCartegory/realTimeSix.vue";
import NestedRankOne from "@/menu/nested/rank/nestedRankOne.vue";
import NestedRankTwo from "@/menu/nested/rank/nestedRankTwo.vue";
import NestedStockOne from "@/menu/nested/stock/nestedStockOne.vue";
import NestedStockTwo from "@/menu/nested/stock/nestedStockTwo.vue";
import stockTwoYear from "@/menu/nested/stock/stockTwoNested/stockTwoYear.vue";
import stockTwoQyarter from "@/menu/nested/stock/stockTwoNested/stockTwoQyarter.vue";
import newsNow from "@/menu/nested/news/nestedNewsnow/newsNow.vue";
import commTopicChildren from "@/menu/nested/community/nestedCommTopic/commTopic.vue";
import keywordNewsAll from "@/menu/nested/news/keywordNews/keywordNewsAll.vue";
import keywordNewsOne from "@/menu/nested/news/keywordNews/keywordNewsOne.vue";
import keywordNewsTwo from "@/menu/nested/news/keywordNews/keywordNewsTwo.vue";
import keywordNewsThree from "@/menu/nested/news/keywordNews/keywordNewsThree.vue";
import keywordNewsFour from "@/menu/nested/news/keywordNews/keywordNewsFour.vue";
import keywordNewsFive from "@/menu/nested/news/keywordNews/keywordNewsFive.vue";
import keywordNewsSix from "@/menu/nested/news/keywordNews/keywordNewsSix.vue";

// import MenuSns from "@/menu/menuSns.vue";
// import NestedSnsNow from "@/menu/nested/sns/nestedSnsNow.vue";
// import NestedSnsPast from "@/menu/nested/sns/nestedSnsPast.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    redirect: "/news/past?code=005930",
    component: HomeView,
  },
  {
    path: "/news",
    name: "News",
    redirect: "/news/past",
    component: MenuNews,
    children: [
      {
        path: "now",
        name: "NewsNow",
        component: NestedNewsNow,
        redirect: "/news/now/keywordAll",
        children: [
          {
            path: "keywordAll",
            name: "keywordAll",
            component: keywordNewsAll,
          },
          {
            path: "keywordOne",
            name: "keywordOne",
            component: keywordNewsOne,
          },
          {
            path: "keywordTwo",
            name: "keywordTwo",
            component: keywordNewsTwo,
          },
          {
            path: "keywordThree",
            name: "keywordThree",
            component: keywordNewsThree,
          },
          {
            path: "keywordFour",
            name: "keywordFour",
            component: keywordNewsFour,
          },
          {
            path: "keywordFive",
            name: "keywordFive",
            component: keywordNewsFive,
          },
          {
            path: "keywordSix",
            name: "keywordSix",
            component: keywordNewsSix,
          },
          {
            path: "keyword",
            name: "newsNowkeyword",
            component: newsNow,
          },
        ],
      },
      {
        path: "time",
        name: "NewsTime",
        component: NestedNewsRealTime,
        redirect: "/news/time/all",
        children: [
          {
            path: "all",
            name: "realTimeAll",
            component: realTimeAll,
          },
          {
            path: "one",
            name: "realTimeOne",
            component: realTimeOne,
          },
          {
            path: "two",
            name: "realTimeTwo",
            component: realTimeTwo,
          },
          {
            path: "three",
            name: "realTimeThree",
            component: realTimeThree,
          },
          {
            path: "four",
            name: "realTimeFour",
            component: realTimeFour,
          },
          {
            path: "five",
            name: "realTimeFive",
            component: realTimeFive,
          },
          {
            path: "six",
            name: "realTimeSix",
            component: realTimeSix,
          },
        ],
      },
      {
        path: "past",
        name: "NewsPast",
        component: NestedNewsPast,
      },
      {
        path: "add",
        name: "NewsAdd",
        component: NestedNewsAdd,
      },
    ],
  },
  {
    path: "/stock",
    name: "Stock",
    component: MenuStock,
    redirect: "/stock/one",
    children: [
      {
        path: "one",
        name: "StockOne",
        component: NestedStockOne,
      },
      {
        path: "two",
        name: "StockTwo",
        component: NestedStockTwo,
        redirect: "/stock/two/year",
        children: [
          {
            path: "year",
            name: "StockTwoOne",
            component: stockTwoYear,
          },
          {
            path: "qyarter",
            name: "StockTwoTwo",
            component: stockTwoQyarter,
          },
        ],
      },
    ],
  },
  // {
  //   path: "/sns",
  //   name: "Sns",
  //   component: MenuSns,
  //   redirect: "/sns/now",
  //   children: [
  //     {
  //       path: "now",
  //       name: "SnsNow",
  //       component: NestedSnsNow,
  //     },
  //     {
  //       path: "past",
  //       name: "SnsPast",
  //       component: NestedSnsPast,
  //     },
  //   ],
  // },
  {
    path: "/community",
    name: "Community",
    component: MenuCommunity,
    redirect: "/community/stock",
    children: [
      {
        path: "stock",
        name: "CommStock",
        component: NestedCommStock,
      },
      {
        path: "topic",
        name: "CommTopic",
        component: NestedCommTopic,
        children: [
          {
            path: "TopicChildren",
            name: "TopicChildren",
            component: commTopicChildren,
          },
        ],
      },
    ],
  },
  {
    path: "/rank",
    name: "Rank",
    component: MenuRank,
    redirect: "/rank/one",
    children: [
      {
        path: "one",
        name: "RankOne",
        component: NestedRankOne,
      },
      {
        path: "two",
        name: "RankTwo",
        component: NestedRankTwo,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory("/"),
  routes,
});

export default router;
