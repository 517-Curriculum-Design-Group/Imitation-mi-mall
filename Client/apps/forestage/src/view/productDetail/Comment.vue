<script setup>
import { api } from "@/api";
import ProductHead from "./ProductHead.vue";
import { onMounted } from "vue";
import { useRouter } from 'vue-router';

// const router = useRouter();
// const productId = parseInt(router.currentRoute.value.params.producId)
onMounted(async()=>{
    const [e,r] = await api.getComment(5,1,10000)
    console.log(r)
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