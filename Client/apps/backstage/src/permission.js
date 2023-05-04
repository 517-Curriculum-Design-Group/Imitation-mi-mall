import router from "~/router"
import {getToken } from "~/composables/auth"

//全局前置
router.beforeEach((to, from, next) => {
    // to and from are both route objects. must call `next`.
    const token = getToken()

    //未登录跳到登录
    if(!token && to.path!="/login"){
        return next({path:"/login"})
    }
    next()
})