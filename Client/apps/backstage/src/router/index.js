import{
    createRouter,
    createWebHashHistory
} from 'vue-router'

import Admin from "~/layout/admin.vue"
import Index from '~/pages/index.vue'
import Notfound from '~/pages/404.vue'
import Login from '~/pages/login.vue'
import Comment from '~/pages/comment.vue'
import Sku from '~/pages/sku.vue'
import Order from '~/pages/order.vue'
import Category from '~/pages/category.vue'
import List from '~/pages/list.vue'
import Common from '~/pages/common.vue'
import AdminControl from '~/pages/admincontrol.vue'

const routes = [{
    path:"/",
    component:Admin,
    children:[{
        path:"/",
        component:Index,
        meta:{
            title:"首页"
        },
    },{
        path:"/admincontrol",
        component:AdminControl,
        meta:{
            title:"管理员管理"
        },
    },{
        path:"/list",
        component:List,
        meta:{
            title:"商品管理"
        },
    },{
        path:"/category",
        component:Category,
        meta:{
            title:"分类管理"
        },
    },{
        path:"/sku",
        component:Sku,
        meta:{
            title:"规格管理"
        },
    },{
        path:"/common",
        component:Common,
        meta:{
            title:"用户管理"
        },
    },{
        path:"/comment",
        component:Comment,
        meta:{
            title:"评论管理"
        },
    },{
        path:"/order",
        component:Order,
        meta:{
            title:"订单管理"
        },
    }
]     
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