<script setup>
import { ref, onMounted } from "vue";
import { usehomeStore } from "@/stores/home";

const titles = ref([]);
const useStore = usehomeStore();

const value = ref("");

const init = () => {
  const obj = useStore.headProduct;
  if (obj) {
    titles.value = Object.keys(obj);
  }
};

onMounted(() => {
  console.log("init");
  init();
});
</script>

<template>
  <div class="flex items-center justify-between w-[1226px] h-[100px] m-auto">
    <img class="w-[56px] h-[56px]" src="../assets/xiaomilogo.svg" />
    <div class="flex gap-x-4 pl-[20px] text-[16px] justify-center items-center">
      <template v-for="item in titles" :key="item">
        <nav class="nav-titile w-[100px] h-[88px] cursor-pointer">
          {{ item }}
        </nav>
      </template>
      <div
        class="flex items-center justify-between w-[1226px] h-[100px] m-auto"
      >
        <img class="w-[56px] h-[56px]" src="../assets/xiaomilogo.svg" />
        <div class="flex w-[296.6px] h-[49.6px] container">
          <n-input
            class="leading-[49.6px] search"
            v-model:value="value"
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
