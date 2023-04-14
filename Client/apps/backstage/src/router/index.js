import{
    createRouter,
    createWebHashHistory
} from 'vue-router'

import Index from '~/pages/index.vue'
import Notfound from '~/pages/404.vue'

const routes = [{
    path:"/",
    component:Index       
},

]

const router = createRouter({
    history:createWebHashHistory(),
    routes
})

export default router