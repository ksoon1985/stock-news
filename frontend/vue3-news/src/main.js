import { createApp } from "vue";
import { createPinia } from "pinia";
import App from "./App.vue";
import router from "@/router";

import HighchartsVue from "highcharts-vue";
import Highcharts from "highcharts";
import stockInit from "highcharts/modules/stock";

const pinia = createPinia();

stockInit(Highcharts);

createApp(App).use(pinia).use(HighchartsVue).use(router).mount("#app");
