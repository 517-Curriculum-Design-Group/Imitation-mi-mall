<script setup>
import { api } from "@/api";
import ProductHead from "./ProductHead.vue";
import { onMounted } from "vue";
import { useRoute } from 'vue-router';

const route = useRoute();
const productId = parseInt(route.params.id)

async function init(){
    const [e, r] = await api.getComment({productId: productId,pageNum: 1,pageSize:10000})
    if(!e && r){
        console.log(r)
    }
    console.log(route.params.id)
}

onMounted(()=>{
   init()
})
</script>

<template>
    <ProductHead class="fixed"></ProductHead>

    <div style="background-color:#f5f5f5;">
        <article class="flex flex-col justify-between">
            <title class=""></title>

        </article>
    </div>
</template>