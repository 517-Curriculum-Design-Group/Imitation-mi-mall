<!-- eslint-disable vue/require-prop-types -->
<script setup>
defineProps(['productName'])
</script>

<template>
    <article class="w-[1226px] h-[60px] sticky top-0 flex justify-between items-center bg-light-50 ml-[147px] mr-[147px]">
        <h2 class="w-auto h-[60px] leading-[60px]">{{ productName }}</h2>
        <span class="w-[56px] h-[16px] cursor-pointer hover:text-orange-500">用户评价</span>
    </article>
</template>