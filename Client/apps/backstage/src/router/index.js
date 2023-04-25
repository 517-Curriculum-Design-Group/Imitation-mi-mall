import{
    createRouter,
    createWebHashHistory
} from 'vue-router'

import Admin from "~/layout/admin.vue"
import Index from '~/pages/index.vue'
import Notfound from '~/pages/404.vue'
import Login from '~/pages/login.vue'

const routes = [{
    path:"/",
    component:Admin,
    children:[{
        path:"/",
        component:Index,
        meta:{
            title:"首页"
        }
    }]     
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