<script setup>

const comments=[]
</script>

<template>
    <h1 class="w-[882px] h-[68px] text-gray-500">订单评价</h1>
    <h2 v-if="!comments.length" class="text-gray-400" style="text-align:center">暂时没有符合条件的商品。</h2>
</template>
