<script setup>
import { userStore } from "@/stores/user.js";
import { ref } from "vue"

const store = userStore();
const user = store.getUserInfo();

const oldpsw = user.password
const newpsw = ''
const verpsw = ''

function changePsw(psw) {
    if(psw != verpsw) {
        alert("两次输入的新密码必须相同！")
    }
    else user.password = psw
}
</script>

<template>
    <div>
        <h1 class="w-[882px] h-[68px] text-gray-500">修改密码</h1>

        <n-form class="flex flex-col justify-center items-center w-[600px] h-auto m-auto">
            <n-form-item class="w-[500px] h-[50px]" path="oldpsw" label="旧密码" label-placement="left" label-align="right"
                style="--n-color-hover:var(--button-background-color); --n-border-hover:1px solid var(--button-background-color);
                --n-boreder-focus:1px solid var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-caret-color:var(--button-background-color)">
                <n-input v-model:value="oldpsw" placeholder="请输入旧密码"></n-input>
            </n-form-item>

            <n-form-item class="w-[500px] h-[50px]" path="newpsw" label="新密码" label-placement="left" label-align="right"
                style="--n-color-hover:var(--button-background-color); --n-border-hover:1px solid var(--button-background-color);
                --n-boreder-focus:1px solid var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-caret-color:var(--button-background-color)">
                <n-input v-model:value="newpsw" placeholder="请输入新密码"></n-input>
            </n-form-item>

            <n-form-item class="w-[500px] h-[50px]" path="verpsw" label="确认新密码" label-placement="left" label-align="right"
                style="--n-color-hover:var(--button-background-color); --n-border-hover:1px solid var(--button-background-color);
                --n-boreder-focus:1px solid var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-caret-color:var(--button-background-color)">
                <n-input v-model:value="verpsw" placeholder="确认密码"></n-input>
            </n-form-item>

            <n-button class="text-light-50 bg-orange-500 w-[360px] h-[60px]" attr-type="button"
                style="--n-color-hover:var(--button-background-color);--n-border-hover:var(--n-border-hover);--n-text-color-hover:white;
                --n-boreder-focus:var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-color-focus:gray;--n-text-color-focus:white" @click="changePsw(newpsw)">
                确认修改
            </n-button>

        </n-form>
    </div>
</template>