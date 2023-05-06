<!-- eslint-disable vue/multi-word-component-names -->
<script setup>
import CarouselMap from "./CarouselMap.vue";
import HomeFoote from "./index.vue";
import { getCategoryList, getUserHome } from "@/api/path/HomeController/index";
import { onMounted, reactive } from "vue";
import { usehomeStore } from "@/stores/home";

const useStore = usehomeStore();

const detaildata = reactive({});

const getDetail = async () => {
  const [e, r] = await getCategoryList();
  console.log(e, r);
};

const getHome = async () => {
  const [e, r] = await getUserHome();
  detaildata.headerProducts = r.data.headerProducts;
  detaildata.leftCategories = r.data.leftCategories;
  detaildata.lowerProducts = r.data.lowerProducts;
  useStore.setHeaderProducts(r.data.headerProducts);
  useStore.setLeftCategories(r.data.leftCategories);
  useStore.setLowerProducts(r.data.lowerProducts);
};
console.log(detaildata.leftCategories);
onMounted(() => {
  getDetail();
  getHome();
});
</script>

<template>
  <div class="flex flex-col min-w-[1226px]">
    <CarouselMap :data="detaildata.leftCategories"></CarouselMap>
    <HomeFoote />
  </div>
</template>
