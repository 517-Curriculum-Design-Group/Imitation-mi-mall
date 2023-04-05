import { createApp } from "vue";
import App from "./App.vue";

import "./normalize.css";
import "ant-design-vue/dist/antd.css";
import "./style.css";
import Antd from "ant-design-vue";
import router from "./router";
import "uno.css";

createApp(App).use(Antd).use(router).mount("#app");
