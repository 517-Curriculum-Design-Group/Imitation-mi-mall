<script setup>
import navCard from "@/components/HomeIndex/navCard.vue";
import { api } from "@/api";
import { onMounted, ref } from "vue";

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

const lowerData = ref([]);
const getLowerProductsData = async () => {
  const [e, r] = await api.getLowerProducts();
  if (!e && r) {
    lowerData.value = r.data;
  }
};

onMounted(() => {
  getLowerProductsData();
});
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
          :key="index"
          class="things relative flex flex-col w-[78px] h-[85px] justify-center items-center hover:text-gray-200"
        >
          <span id="1" :class="`${item.icon}`" class="h-24px w-24px"></span>
          <span>{{ item.name }}</span>
        </span>
      </div>

      <template v-for="item in data" :key="item.src">
        <navCard :src="item.src" :alt="item.alt" />
      </template>
    </div>
    <div class="flex flex-col items-center bg-[#f5f5f5] w-100% mt-24px">
      <div class="mt-[24px]">
        <img
          class="w-[1226px]"
          src="//cdn.cnbj1.fds.api.mi-img.com/mi-mall/864aa0927000c3d717eca08955589d62.jpg?thumb=1&w=1533&h=150&f=webp&q=90"
          alt="广告4"
        />
      </div>
      <template v-for="itemss in lowerData" :key="itemss.categoryName">
        <div class="flex h-[684px] w-[1246px] relative">
          <n-tabs type="bar" animated trigger="hover" justify-content="end">
            <template #prefix>
              <span class="text-[22px]">{{ itemss.categoryName }}</span>
            </template>
            <n-tab-pane
              v-for="obj in itemss.eachCategoryProduct"
              :key="obj.category_name"
              class="flex flex-wrap gap-14px min-h-[400px]"
              :name="obj.category_name"
              :tab="obj.category_name"
            >
              <template v-for="item in obj.products" :key="item.productId">
                <n-card
                  class="w-234px h-300px m-2px text-14px flex flex-col text-center justify-center items-center text-black cursor-pointer"
                  hoverable
                  @click="$router.push(`${item.productId}`)"
                >
                  <img
                    class="aspect-square w-160px"
                    :src="item.productPic"
                    :alt="item.productName"
                  />
                  <div>{{ item.productName }}</div>
                  <div class="truncate h-18px w-209px text-gray-400 text-12px">
                    {{ item.productDescription }}
                  </div>
                  <span class="text-orange-500 inline-block mt-10px">{{
                    item.leastPrice
                  }}</span>
                </n-card>
              </template>
              <n-empty
                class="absolute top-[50%] left-[50%] z-0 text-[22px]"
                :class="{
                  'op-0': obj.products.length !== 0 ? true : false,
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
            </n-tab-pane>
          </n-tabs>
          <n-empty
            class="absolute top-[50%] left-[50%] z-0 text-[22px]"
            :class="{
              'op-0': itemss.eachCategoryProduct.length !== 0 ? true : false,
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
      </template>
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
