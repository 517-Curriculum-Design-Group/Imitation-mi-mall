<!-- eslint-disable vue/multi-word-component-names -->
<script setup>
import CarouselMap from "./CarouselMap.vue";
import HomeFoote from "./index.vue";
import { api } from "@/api";
import { onMounted, reactive } from "vue";

const detaildata = reactive({});

const getDetail = async () => {
  const [e, r] = await api.getCategoryList();
};

onMounted(() => {
  getDetail();
});
</script>

<template>
  <div class="flex flex-col min-w-[1226px]">
    <CarouselMap></CarouselMap>
    <HomeFoote />
  </div>
</template>
