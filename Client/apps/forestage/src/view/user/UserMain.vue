<script setup>
import { userStore } from "@/stores/user.js";
import { onMounted, ref, reactive } from "vue";
import { api } from "@/api";

const store = userStore()
const user = store.getUserInfo()

let person = reactive({})
onMounted(async () => {
    const [e, r] = await api.getPersonInfo()
    console.log(r.data)
    Object.assign(person, r.data)
    console.log(person)
})
</script>

<template>
    <article class="flex flex-col">
        <div class="flex justify-between">
            <div class="flex">
                <img :src="person.avatar" class="w-[150px] h-[150px] mr-8" style="border-radius:50%;overflow: hidden;"/>
                <div class="flex flex-col">
                    <span class="text-4xl">{{ person.userId }}</span>
                    <span class="text-gray-400">你好~</span>
                    <router-link to="/user/info">
                        <span class="text-orange-500 cursor-pointer mt-4 text-sm">修改个人信息>></span></router-link>
                </div>
            </div>
            <div class="flex flex-col">
                <span class="w-[358px] h-[33px]">账户安全：<span class="text-yellow-500">普通</span></span>
                <span class="w-[358px] h-[33px]">绑定手机：{{ user.phonenumber || "未填写" }}</span>
                <span class="w-[358px] h-[33px]">绑定邮箱：{{ user.email || "未填写" }}</span>
            </div>
        </div>
        <n-divider />

        <div class="flex flex-wrap ">
            <div class="flex mr-50 mt-4">
                <img src="https://s01.mifile.cn/i/user/portal-icon-1.png" class="mr-4" />
                <div class="flex flex-col items-center pt-8">
                    <span class="text-2xl text-gray-500">待支付的订单:0</span>
                    <router-link to="/user/order">
                        <span class="text-gray-500">查看待支付订单></span></router-link>
                </div>
            </div>
            <div class="flex ml-4 mt-4">
                <img src="https://s01.mifile.cn/i/user/portal-icon-2.png" class="mr-4" />
                <div class="flex flex-col items-center pt-8">
                    <span class="text-2xl text-gray-500">待收货的订单:0</span>
                    <router-link to="/user/order">
                        <span class="text-gray-500">查看待收货订单></span></router-link>
                </div>
            </div>
            <div class="flex mr-50 mt-4">
                <img src="https://s01.mifile.cn/i/user/portal-icon-3.png" class="mr-4" />
                <div class="flex flex-col items-center pt-8">
                    <span class="text-2xl text-gray-500">待评价商品数</span>
                    <router-link to="/user/comment">
                        <span class="text-gray-500">查看待评价商品></span></router-link>
                </div>
            </div>
            <div class="flex ml-10 mt-4">
                <img src="https://s01.mifile.cn/i/user/portal-icon-4.png" class="mr-4" />
                <div class="flex flex-col items-center pt-8">
                    <span class="text-2xl text-gray-500">喜欢的商品:0</span>
                    <router-link to="/user/like">
                        <span class="text-gray-500">查看喜欢的商品></span></router-link>
                </div>
            </div>
        </div>
    </article>
</template>