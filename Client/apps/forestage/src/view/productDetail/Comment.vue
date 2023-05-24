<script setup>
import { api } from "@/api";
import ProductHead from "./ProductHead.vue";
import { onMounted } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();

async function init() {
  let obj = {
    productId: Number(route.params.id),
    pageNum: 1,
    pageSize: 10000,
  };
  const [e, r] = await api.getComment(obj);
  if (!e && r) {
    console.log(r);
  }
}

onMounted(() => {
  init();
});
</script>

<template>
  <ProductHead class="fixed"></ProductHead>

  <div style="background-color: #f5f5f5">
    <article class="flex flex-col justify-between">
      <title class=""></title>
    </article>
  </div>
</template>
