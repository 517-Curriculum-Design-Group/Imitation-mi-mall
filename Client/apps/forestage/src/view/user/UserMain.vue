<script setup>
import { userStore } from "@/stores/user.js";

const store = userStore()
const user = store.getUserInfo()
</script>

<template>
    <article class="flex flex-col">

        <div class="flex justify-between">
            <div class="flex flex-col">
                <span class="text-lg">{{ store.userid }}</span>
                <span class="text-gray-400">你好~</span>
            </div>

            <div class="flex flex-col">
                <span class="w-[358px] h-[33px]">绑定手机：{{ user.phonenumber|| "未填写" }}</span>
                <span class="w-[358px] h-[33px]">绑定邮箱：{{  user.email|| "未填写"}}</span>
            </div>
        </div>
        <n-divider />

        
    </article>
</template>