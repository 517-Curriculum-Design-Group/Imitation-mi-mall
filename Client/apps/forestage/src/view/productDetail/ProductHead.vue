<script setup>
import { useDetailStore } from '@/stores/searchAnddetail.js';

const store = useDetailStore();
const product = store.getCurrentProduct()
</script>

<template>
    <article class="w-[1226px] h-[60px] fixed flex justify-between items-center bg-light-50 ml-[147px] mr-[147px]">
        <h2 class="w-auto h-[60px]">{{ product.productName }}</h2>

        <span class="w-[56px] h-[16px] hover:text-orange-500">用户评价</span>
    </article>
</template>