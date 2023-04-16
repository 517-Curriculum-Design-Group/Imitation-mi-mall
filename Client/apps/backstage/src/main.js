import { createApp } from 'vue'
import './normalize.css'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './style.css'
import 'virtual:windi.css'
import App from './App.vue'
import router from './router'

createApp(App).use(ElementPlus).use(router).mount('#app')
