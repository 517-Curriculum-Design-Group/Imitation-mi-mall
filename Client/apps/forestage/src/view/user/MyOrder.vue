<script setup>
import { ref } from 'vue'
const value = ref('')

</script>

<template>
    <h1 class="w-[882px] h-[68px] text-gray-500">我的订单</h1>
    <article class="flex justify-between items-center w-100% h-[56px]">
        <div class="flex">
            <li tabindex="1">全部有效订单</li>
            <li tabindex="1">待收货</li>
        </div>

        <div class="flex w-[296.6px] h-[41.6px] container">
            <n-input class="leading-[41.6px] search" v-model:value="value"
                style="--n-border-hover:1px solid var(--button-background-color);--n-border-focus:1px solid var(--button-background-color);--n-box-shadow-focus: var(--button-background-color);--n-caret-color:var(--button-background-color)" type="text" placeholder="输入商品名称、订单号" />
            <n-button class="w-[42px] h-[42px] search-btn"
                style="--n-border-hover:1px solid var(--button-background-color);--n-padding:0 4px">
                <span class="i-mdi-magnify w-[24px] h-[24px]"></span>
            </n-button>
        </div>
    </article>
</template>

<style scoped lang="scss">
li {
    display: flex;
    justify-content: center;
    border-left: 1px solid #e0e0e0;
    padding: 0 2rem;
    cursor: pointer;

    &::marker {
        color: transparent;
    }

    &:nth-child(1) {
        border: none;
        padding-left: 0;
    }

    &:hover {
        color: var(--button-background-color);
    }

    &:focus {
        color: var(--button-background-color);
    }

}

:deep(.search-btn):hover {
    color: white;
    background-color: var(--button-background-color);
}
</style>