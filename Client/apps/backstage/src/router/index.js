import{
    createRouter,
    createWebHashHistory
} from 'vue-router'

import Index from '~/pages/index.vue'
import Notfound from '~/pages/404.vue'
import Login from '~/pages/login.vue'

const routes = [{
    path:"/",
    component:Index       
},{
    path:"/login",
    component:Login
},{ 
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: Notfound 
},

]

const router = createRouter({
    history:createWebHashHistory(),
    routes
})

export default router