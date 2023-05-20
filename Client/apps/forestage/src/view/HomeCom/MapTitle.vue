<script setup>
import { ref, onMounted, computed } from "vue";
import { api } from "@/api";

const titles = ref([]);
const [e, r] = await api.getLeftCategories();
if (!e && r) {
  titles.value = r.data;
}
</script>

<template>
  <div class="flex flex-col w-[100%] h-[100%] py-[25px] text-light-800">
    <nav
      v-for="item in titles"
      :key="item"
      class="active flex-1 flex justify-between items-center w-[100%] h-[42px] leading-[42px] hover:text-light-100 hover:bg-orange-500 px-[25px] cursor-pointer"
    >
      {{ item.parentCategoryName }}
      <div class="i-ic-round-keyboard-arrow-right w-[24px] h-[24px]" />
      <div
        class="activeBox absolute top-0 left-[226px] flex flex-col flex-wrap h-full bg-light-50 drop-shadow w-[400px] text-black leading-[76px] pl-[25px]"
      >
        <div
          class="w-[265] h-[76px] hover:text-orange-500"
          v-for="i in item.categoryNames"
          :key="i.category_id"
        >
          {{ i.category_name }}
        </div>
        <n-empty
          class="absolute top-[40%] left-[40%] z-0"
          :class="{
            'op-0': item.categoryNames.length !== 0 ? true : false,
          }"
          description="你什么也找不到"
        >
        </n-empty>
      </div>
    </nav>
  </div>
</template>

<style scoped>
.active .activeBox {
  width: 0;
  opacity: 0;
  z-index: 0;
  transition: all 400ms cubic-bezier(0.4, 0.4, 0.25, 1.35);
}
.active:hover .activeBox {
  width: 400px;
  opacity: 1;
  z-index: 200;
  transition: all 400ms cubic-bezier(0.4, 0.4, 0.25, 1.35);
}
</style>
