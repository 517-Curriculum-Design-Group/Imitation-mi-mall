import { createApp } from "vue";
import App from "./App.vue";

import "./normalize.css";
import "ant-design-vue/dist/antd.css";
import "./style.css";
import Antd from "ant-design-vue";
import router from "./router";
import naive from "naive-ui";
import "uno.css";
import { createPinia } from "pinia";

createApp(App)
  .use(Antd)
  .use(naive)
  .use(router)
  .use(createPinia())
  .mount("#app");
