<script setup>
import { userStore } from "@/stores/user.js";
import { ref } from "vue"

const store = userStore();
const isShow = ref(false)

const deleteUser = () => {
    isShow.value = false
    store.deleteUserInfo()
}
</script>

<template>
    <h1 class="w-[882px] h-[68px] text-gray-500">注销个人账户</h1>
    <div class="flex justify-center">
        <n-button class="text-light-50 bg-orange-500 w-[360px] h-[80px]" attr-type="button"
            style="--n-color-hover:var(--button-background-color);--n-border-hover:var(--n-border-hover);--n-text-color-hover:white;
                --n-boreder-focus:var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-color-focus:gray;--n-text-color-focus:white" @click="isShow = true">
            注销账户
        </n-button>

        <n-modal v-model:show="isShow" :mask-closable="false" preset="dialog" title="确认" content="你确认要注销账号吗？"
            positive-text="确认" negative-text="取消" @positive-click="deleteUser()" @negative-click="isShow = false" />
    </div>
</template>