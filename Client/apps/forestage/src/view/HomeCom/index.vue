<script setup>
import navCard from "@/components/HomeIndex/navCard.vue";

const data = [
  {
    src: "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/0ef4ea18cb2d6ca963d39c2109460e02.jpg?w=632&h=340",
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

const items = [
  {
    name: "保障服务",
    icon: "i-mdi-clock-outline",
  },
  {
    name: "企业团购",
    icon: "i-mdi-city-variant-outline",
  },
  {
    name: "F码通道",
    icon: "i-mdi-alpha-f-circle-outline",
  },
  {
    name: "米粉卡",
    icon: "i-mdi-sim-outline",
  },
  {
    name: "以旧换新",
    icon: "i-mdi-currency-usd-circle-outline",
  },
  {
    name: "话费充值",
    icon: "i-mdi-cellphone-iphone",
  },
];
</script>

<template>
  <div class="flex flex-col items-center">
    <div class="flex mt-[12px] gap-x-4 justify-center align-item">
      <div
        w="234px"
        h="170px"
        class="flex flex-wrap aside cursor-pointer text-gray-300"
      >
        <span
          v-for="(item, index) in items"
          class="things relative flex flex-col w-[78px] h-[85px] justify-center items-center hover:text-gray-200"
          :key="index"
        >
          <span :class="`${item.icon}`" class="h-24px w-24px" id="1"></span>
          <span>{{ item.name }}</span>
        </span>
      </div>

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

<style scoped lang="scss">
.aside {
  background-color: #5f5750;
}

.things {
  &::before {
    position: absolute;
    height: 60px;
    content: "";
    border: 1px solid white;
    left: 1px;
    transform: scaleX(0.05);
  }

  &::after {
    position: absolute;
    height: 60px;
    content: "";
    border: 1px solid #b0b0b0;
    top: -28px;
    transform: rotate(90deg) scaleX(0.05);
  }
}
</style>
