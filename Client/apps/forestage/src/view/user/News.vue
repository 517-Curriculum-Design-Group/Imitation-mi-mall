<script setup>

const news=[]
</script>

<template>
    <h1 class="w-[882px] h-[68px] text-gray-500">消息通知</h1>
    <div class="flex">
            <li tabindex="1">全部消息</li>
            <li tabindex="1">物流动态</li>
            <li tabindex="1">特惠活动</li>
     </div>
    <h2 v-if="!news.length" class="text-gray-400" style="text-align:center">暂无数据</h2>
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
</style>