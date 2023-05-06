<script setup>
import { userStore } from "@/stores/user.js";
import { ref } from 'vue'

const store = userStore();
const user = store.userInfo;

const isEdit = ref(false)

function changeInfo(user) {
    store.setUserInfo(user);
    isEdit.value = fasle;
}
</script>

<template>
    <div>
        <h1 class="w-[882px] h-[68px] text-gray-500">个人信息</h1>

        <n-form :mdoel="user" class="flex flex-col w-[600px] gap-4 justify-center items-center h-auto m-auto" label-align="right" v-if="isEdit">

            <n-form-item class="w-[500px] h-[50px]" path="user.avatar" label="头像" label-placement="left" label-align="right" style="--n-color-hover:var(--button-background-color); --n-border-hover:1px solid var(--button-background-color);
                --n-boreder-focus:1px solid var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-caret-color:var(--button-background-color)">
                <n-input v-model:value="user.avatar" placeholder="请输入头像的在线链接"></n-input>
            </n-form-item>

            <n-form-item class="w-[500px] h-[50px]" path="user.nickname" label="昵称" label-placement="left" label-align="right"
                style="--n-color-hover:var(--button-background-color);--n-border-hover:var(--n-border-hover);
                --n-boreder-focus:var(--button-background-color);--n-ripple-color:var(--button-background-color);caret-color:var(--button-background-color)">
                <n-input v-model:value="user.nickname" placeholder="请输入昵称"></n-input>
            </n-form-item>

            <n-form-item class="w-[500px] h-[50px]" path="user.sex" label="性别" label-placement="left" label-align="right">
                <n-radio-group v-model:value="user.sex">
                    <n-space>
                        <n-radio value="male" style="--n-box-shadow:inset 0 0 0 1px var(--button-background-color);--n-box-shadow-active:inset 0 0 0 1px var(--button-background-color);--n-box-shadow-focus:inset 0 0 0 1px var(--button-background-color);
                        --n-box-shadow-hover:inset 0 0 0 1px var(--button-background-color);--n-dot-color-active:var(--button-background-color)">
                            男
                        </n-radio>
                        <n-radio value="female" style="--n-box-shadow:inset 0 0 0 1px var(--button-background-color);--n-box-shadow-active:inset 0 0 0 1px var(--button-background-color);--n-box-shadow-focus:inset 0 0 0 1px var(--button-background-color);
                        --n-box-shadow-hover:inset 0 0 0 1px var(--button-background-color);--n-dot-color-active:var(--button-background-color)">
                            女
                        </n-radio>
                    </n-space>
                </n-radio-group>
            </n-form-item>

            <n-form-item class="w-[500px] h-[50px]" path="user.nickname" label="手机号" label-placement="left" label-align="right"
                style="--n-color-hover:var(--button-background-color);--n-border-hover:var(--n-border-hover);
                --n-boreder-focus:var(--button-background-color);--n-ripple-color:var(--button-background-color);caret-color:var(--button-background-color)">
                <n-input v-model:value="user.phonenumber" placeholder="请输入手机号"></n-input>
            </n-form-item>

            <n-form-item class="w-[500px] h-[50px]" path="user.nickname" label="邮箱" label-placement="left" label-align="right"
                style="--n-color-hover:var(--button-background-color);--n-border-hover:var(--n-border-hover);
                --n-boreder-focus:var(--button-background-color);--n-ripple-color:var(--button-background-color);caret-color:var(--button-background-color)">
                <n-input v-model:value="user.email" placeholder="请输入邮箱"></n-input>
            </n-form-item>

            <n-button class="text-light-50 bg-orange-500 w-[360px] h-[60px]" attr-type="button"
                style="--n-color-hover:var(--button-background-color);--n-border-hover:var(--n-border-hover);--n-text-color-hover:white;
                --n-boreder-focus:var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-color-focus:gray;--n-text-color-focus:white" @click="changeInfo(user)">
                保存
            </n-button>
        </n-form>

        <div class="flex flex-col justify-center items-center w-[600px] h-auto m-auto" v-else>
            <span class="w-[500px] h-[70px]">头像:{{ user.avater }}</span>
            <span class="w-[500px] h-[70px]">昵称:{{ user.nickname || "未填写" }}</span>
            <span class="w-[500px] h-[70px]">性别:{{ user.sex ==male? "男":"女" || "未填写" }}</span>
            <span class="w-[500px] h-[70px]">用户ID:{{ user.userId }}</span>
            <span class="w-[500px] h-[70px]">手机号:{{ user.phone || "未填写" }}</span>
            <span class="w-[500px] h-[70px]">邮箱:{{ user.email || "未填写" }}</span>
            <n-button class="text-light-50 bg-orange-500 w-[360px] h-[60px]" attr-type="button"
                style="--n-color-hover:var(--button-background-color);--n-border-hover:var(--n-border-hover);--n-text-color-hover:white;
                --n-boreder-focus:var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-color-focus:gray;--n-text-color-focus:white" @click="isEdit = true">
                编辑
            </n-button>
    </div>
</div></template>