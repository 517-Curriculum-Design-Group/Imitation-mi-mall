import { createApp } from "vue";
import App from "./App.vue";

import "./normalize.css";
import "ant-design-vue/dist/antd.css";
import "./style.css";
import Antd from "ant-design-vue";
import router from "./router";
import naive from "naive-ui";
import "uno.css";
import "@/utils/rem";
import pinia from "./stores/store";

createApp(App).use(Antd).use(naive).use(pinia).use(router).mount("#app");
