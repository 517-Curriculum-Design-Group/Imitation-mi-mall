<script setup>
import { ref, onMounted, onBeforeMount, reactive } from 'vue'
import { api } from "@/api";
import { useRouter } from 'vue-router';
import { useDetailStore } from '@/stores/detail.js'
import { userStore } from "@/stores/user.js";
import { message } from 'ant-design-vue';

const store = useDetailStore()

let list = []
let product = []
onMounted(async()=>{
    list = await store.getMostProduct()
    // for(let i = 0; i < list.length; i++){
    //     product.push(list[i].productDetail) 
    // }
    console.log(product)
})
const comments=[]
</script>

<template>
    <h1 class="w-[882px] h-[68px] text-gray-500">订单评价</h1>
    <h2 v-if="!comments.length" class="text-gray-400" style="text-align:center">暂无评价。</h2>
</template>
