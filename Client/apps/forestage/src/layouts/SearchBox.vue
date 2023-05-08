<script setup>
import { ref, onMounted } from "vue";
import { api } from "@/api";

const titles = ref([]);
const value = ref("");

const getHeaderProductsData = async () => {
  const [e, r] = await api.getHeaderProducts();
  if (!e && r) {
    titles.value = r.data;
  }
};

onMounted(() => {
  getHeaderProductsData();
});
</script>

<template>
  <div class="flex items-center w-[1226px] h-[100px] m-auto relative">
    <img class="w-[56px] h-[56px]" src="../assets/xiaomilogo.svg" />
    <div
      class="flex ml-[160px] gap-x-4 pl-[20px] text-[16px] justify-center items-center"
    >
      <template v-for="item in titles" :key="item">
        <nav class="nav-titile w-[100px] h-[88px] cursor-pointer">
          {{ item.cateName }}
        </nav>
      </template>
      <div
        class="absolute top-[100px] left-0 flex h-[210px] w-[100%] z-200 bg-light-100"
      >
        <div
          v-for="item in titles[0].sameCategoryProducts"
          :key="item.product_id"
          class="h-[201px] flex flex-col w-[204px] text-center items-center justify-center text-[12px] leading-[20px] cursor-pointer"
        >
          <img
            :src="item.product_pic"
            :alt="item.product_name"
            class="object-cover w-[160px] h-[110px]"
          />
          <span class="mt-4">{{ item.product_name }}</span>
          <span class="text-orange-500">{{ item.product_price }}</span>
        </div>
      </div>
      <div class="flex w-[296.6px] h-[49.6px] container ml-[120px]">
        <n-input
          v-model:value="value"
          class="leading-[49.6px] search"
          style="
            --n-border-hover: 1px solid var(--button-background-color);
            --n-border-focus: 1px solid var(--button-background-color);
            --n-box-shadow-focus: var(--button-background-color);
            --n-caret-color: var(--button-background-color);
          "
          type="text"
          placeholder="搜索"
        />
        <n-button
          class="w-[52px] h-[50px] search-btn"
          style="--n-border-hover: 1px solid var(--button-background-color)"
        >
          <span class="i-mdi-magnify w-[24px] h-[24px]"></span>
        </n-button>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
:deep(.search-btn):hover {
  color: white;
  background-color: var(--button-background-color);
}

.nav-titile {
  line-height: 88px;
}
.nav-titile:hover {
  color: var(--button-background-color);
}
</style>
