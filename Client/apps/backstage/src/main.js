import { createApp } from 'vue'
import './normalize.css'

import store from './store'
import ElementPlus from 'element-plus'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import 'element-plus/dist/index.css'
import './style.css'
import 'virtual:windi.css'
import App from './App.vue'
import router from './router'
import './permission'

const app=createApp(App)
app.use(ElementPlus).use(router).use(store).use(SkuForm)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }

app.mount('#app')
