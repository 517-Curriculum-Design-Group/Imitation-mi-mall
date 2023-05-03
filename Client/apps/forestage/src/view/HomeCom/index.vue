<script setup>
import navCard from "@/components/HomeIndex/navCard.vue";
import { onMounted } from "vue";
import {
  getCategoryList,
  getCateProduct,
} from "@/api/path/HomeController/index";

const data = [
  {
    src: "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/d7d4be1a9e701e16de498f89b1865867.jpg?w=632&h=340",
    alt: "广告1",
  },
  {
    src: "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/6dd2f3e0de4e6cbba98fd3799cfa5bf7.jpg?w=632&h=340",
    alt: "广告2",
  },
  {
    src: "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/0cf5e958bc88727b50c5c5fba7a8f47a.jpg?w=632&h=340",
    alt: "广告3",
  },
];

const getDetail = async () => {
  const [e, r] = await getCategoryList();
  console.log(e, r);
};

onMounted(() => {
    getDetail()
});
</script>

<template>
  {{}}
  <div class="flex flex-col items-center">
    <div class="flex mt-[12px] gap-x-4 justify-center align-item">
      <div w="234px" h="170px" class="bg-red-500"></div>
      <template v-for="item in data" :key="item.src">
        <navCard :src="item.src" :alt="item.alt" />
      </template>
    </div>
    <div class="mt-[24px]">
      <img
        class="w-[1226px]"
        src="//cdn.cnbj1.fds.api.mi-img.com/mi-mall/864aa0927000c3d717eca08955589d62.jpg?thumb=1&w=1533&h=150&f=webp&q=90"
        alt="广告4"
      />
    </div>
  </div>
</template>
