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
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

createApp(App)
  .use(Antd)
  .use(naive)
  .use(router)
  .use(pinia)
  .mount("#app");
