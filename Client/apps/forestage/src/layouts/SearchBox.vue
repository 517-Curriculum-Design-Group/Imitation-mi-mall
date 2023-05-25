<script setup>
import { ref, onMounted } from "vue";
import { api } from "@/api";
import { useRouter } from "vue-router";

const router = useRouter();
const titles = ref([]);
const value = ref("");

const getHeaderProductsData = async () => {
  const [e, r] = await api.getHeaderProducts();
  if (!e && r) {
    titles.value = r.data;
  }
};

function searchBoxEnter() {
  router.push(`/shop/${value.value}`);
  value.value = "";
}

onMounted(() => {
  getHeaderProductsData();
});
</script>

<template>
  <div class="flex items-center w-[1226px] h-[100px] m-auto relative">
    <img
      class="w-[56px] h-[56px] cursor-pointer"
      src="../../public/vite.svg"
      @click="$router.push('/home')"
    />
    <div
      class="flex ml-[160px] gap-x-4 pl-[20px] text-[16px] justify-center items-center"
    >
    <span class="w-[50px] mr-3 cursor-pointer hover:text-orange-500" @click="$router.push('/page')">全部</span>
      <template v-for="item in titles" :key="item">
        <nav class="nav-titile active w-[100px] h-[88px] cursor-pointer">
          {{ item.cateName }}
          <div
            class="activeBox absolute top-[100px] left-0 drop-shadow flex h-[0] opacity-0 w-[100%] z-0 bg-light-100"
          >
            <template
              v-for="i in item.sameCategoryProducts"
              :key="i.product_id"
            >
              <div
                class="h-[201px] flex flex-col w-[204px] text-center items-center justify-center text-[12px] leading-[20px] cursor-pointer text-black"
                @click="$router.push(`/${i.product_id}`)"
              >
                <img
                  :src="i.product_pic"
                  :alt="i.product_name"
                  class="object-cover w-[160px] h-[140px]"
                />
                <span class="mt-4">{{ i.product_name }}</span>
                <span class="text-orange-500">{{ i.product_price }}</span>
              </div>
            </template>

            <n-empty
              class="absolute top-[20%] left-[50%] text-[22px]"
              :class="{
                'op-0': item.sameCategoryProducts.length !== 0 ? true : false,
              }"
              size="huge"
              description="该商城不卖这商品"
            >
              <template #icon>
                <div>
                  <img
                    class="w-65px aspect-square"
                    src="https://www.gstatic.com/android/keyboard/emojikitchen/20201001/u1f635/u1f635_u1f607.png"
                    alt="找不到呀找不到!!!"
                  />
                </div>
              </template>
            </n-empty>
          </div>
        </nav>
      </template>
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
          @keyup.enter="searchBoxEnter"
        />
        <n-button
          class="w-[52px] h-[50px] search-btn"
          style="--n-border-hover: 1px solid var(--button-background-color)"
          @click="searchBoxEnter"
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

.active > .activeBox {
  z-index: -1;
  transition: all 400ms cubic-bezier(0.4, 0.4, 0.25, 1.35);
}

.active:hover > .activeBox {
  height: 210px;
  opacity: 1;
  transition: all 400ms cubic-bezier(0.4, 0.4, 0.25, 1.35);
  z-index: 200;
}
</style>
