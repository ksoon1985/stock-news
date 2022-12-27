import { createApp } from "vue";
import { createPinia } from "pinia";
import piniaPersist from "pinia-plugin-persist";
import App from "./App.vue";
import router from "@/router";

import HighchartsVue from "highcharts-vue";
import Highcharts from "highcharts";
import VueHighcharts from "vue3-highcharts";
import stockInit from "highcharts/modules/stock";
import VueAwesomePaginate from "vue-awesome-paginate";
import InfiniteLoading from "v3-infinite-loading";

const pinia = createPinia();
pinia.use(piniaPersist);

stockInit(Highcharts);

createApp(App)
  .use(pinia)
  .use(HighchartsVue)
  .use(router)
  .use(VueHighcharts)
  .use(VueAwesomePaginate)
  .use(InfiniteLoading)
  .mount("#app");
