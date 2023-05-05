<template>
    <div class="f-header">
        <span class="ml-10">
            小米商城后台
        </span>

    <div class="ml-auto">
                <el-dropdown>

            <span class="el-dropdown-link; flex items-center mr-10">
                <el-avatar :size="25" />
                <span class="text-lg; text-light-100">
                    username
                </span>
            
            <el-icon class="el-icon--right; text-light-100">
                <arrow-down />
            </el-icon>
            </span>

            <template #dropdown>
            <el-dropdown-menu>
                <el-dropdown-item>修改密码</el-dropdown-item>
                <el-dropdown-item divided @click="quite">退出登录</el-dropdown-item>
            </el-dropdown-menu>
            </template>
        </el-dropdown>
    </div>
    </div>
</template>

<script setup>
import{logout} from '~/api/manager'
import { useRouter } from "vue-router"
import { removeToken,removeType,} from '~/composables/auth'
import { toast,showModal } from '~/composables/util'
const router = useRouter()

const quite = () =>{

        showModal("是否要退出登录？").then(res => {
            logout().finally(() => {
                removeToken()
                removeType()
                // 跳转回登录页
                router.push('/login')
                // 提示退出登录成功
                toast("退出登录成功")
            })
        })
    
}
</script>

<style>
.f-header{
    @apply flex items-center bg-indigo-500 text-light-50 fixed top-0 left-0 right-0; 
    height:64px;

}
</style>