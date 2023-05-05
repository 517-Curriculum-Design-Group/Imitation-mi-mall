import router from "~/router"
import {getToken } from "~/composables/auth"
import {toast } from "~/composables/util"

//全局前置
router.beforeEach((to, from, next) => {
    // to and from are both route objects. must call `next`.
    const token = getToken()

    //未登录跳到登录
    if(!token && to.path!="/login"){
        toast("请先登录",'warning')
        return next({path:"/login"})
    }
    //防止重复登陆
if(token && to.path == "/login"){
    toast("请勿重复登陆登录",'warning')
    return next({path:from.path ? from.path : "/"})
}
    next()
})