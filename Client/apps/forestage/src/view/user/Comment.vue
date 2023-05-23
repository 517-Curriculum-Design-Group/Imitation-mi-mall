<script setup>
import { api } from "@/api"
import { onMounted } from "vue";

onMounted(async()=>{
    const [e,r] = await api.getComment(2,1,2)
    console.log(r.data)
})
const comments=[]
</script>

<template>
    <h1 class="w-[882px] h-[68px] text-gray-500">订单评价</h1>
    <h2 v-if="!comments.length" class="text-gray-400" style="text-align:center">暂无评价。</h2>
</template>
