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
import NestedRankOne from "@/menu/nested/rank/nestedRankOne.vue";
import NestedRankTwo from "@/menu/nested/rank/nestedRankTwo.vue";
import NestedStockOne from "@/menu/nested/stock/nestedStockOne.vue";
import NestedStockTwo from "@/menu/nested/stock/nestedStockTwo.vue";
import stockTwoYear from "@/menu/nested/stock/stockTwoNested/stockTwoYear.vue";
import stockTwoQyarter from "@/menu/nested/stock/stockTwoNested/stockTwoQyarter.vue";
// import MenuSns from "@/menu/menuSns.vue";
// import NestedSnsNow from "@/menu/nested/sns/nestedSnsNow.vue";
// import NestedSnsPast from "@/menu/nested/sns/nestedSnsPast.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    redirect: "/news/now?code=005930",
    component: HomeView,
  },
  {
    path: "/news",
    name: "News",
    redirect: "/news/now",
    component: MenuNews,
    children: [
      {
        path: "now",
        name: "NewsNow",
        component: NestedNewsNow,
      },
      {
        path: "past",
        name: "NewsPast",
        component: NestedNewsPast,
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
