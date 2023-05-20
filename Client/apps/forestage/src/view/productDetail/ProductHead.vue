<script setup>
import { useDetailStore } from '@/stores/searchAnddetail.js';
import { computed, onBeforeMount, onMounted, ref } from 'vue';
import { api } from "@/api";

const store = useDetailStore();
let product = ref(null)
const init =async ()=>{
   const [e,r] = await api.getProductDetail(2)
   product.value = r.data.productName
   console.log(product)
}

onBeforeMount(()=>{
    init()
})
</script>

<template>
    <article class="w-[1226px] h-[60px] fixed flex justify-between items-center bg-light-50 ml-[147px] mr-[147px]">
        <h2 class="w-auto h-[60px] leading-[60px]">{{ product }}</h2>

        <span class="w-[56px] h-[16px] cursor-pointer hover:text-orange-500">用户评价</span>
    </article>
</template>