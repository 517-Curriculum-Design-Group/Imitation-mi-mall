<template>
  <n-tabs
    v-model:value="searchs.currentNav"
    class="navsC px-[8%] mb-18px"
    type="line"
    animated
  >
    <n-tab-pane display-directive="show" name="综合" tab="综合">
      <div
        v-if="searchs.products.length !== 0 ? true : false"
        class="w-full flex flex-wrap gap-14px"
      >
        <n-card
          v-for="item in searchs.products"
          :key="item.productId"
          class="w-296px h-430px flex flex-col text-center"
          hoverable
          @click="$router.push(`/${item.productId}`)"
        >
          <img
            class="aspect-square w-200px mt-50px"
            :src="item.productPic"
            :alt="item.productName"
          />
          <h4 class="my-8px hover:text-orange-500">
            {{ item.productName }}
          </h4>
          <p class="text-orange-500 text-14px leading-21px">
            {{ item.leastPrice }}
          </p>
        </n-card>
      </div>
      <div class="my-100px" v-else>
        <n-empty description="你什么也找不到">
          <template #extra>
            <n-button
              size="small"
              type="primary"
              @click="$router.push('/home')"
            >
              看看别的
            </n-button>
          </template>
        </n-empty>
      </div>
    </n-tab-pane>
    <n-tab-pane display-directive="show" name="asc" tab="价格升序">
      <div
        v-if="searchs.products.length !== 0 ? true : false"
        class="w-full flex flex-wrap gap-14px"
      >
        <n-card
          v-for="item in searchs.ascDatas"
          :key="item.productId"
          class="w-296px h-430px flex flex-col text-center"
          hoverable
          @click="$router.push(`/${item.productId}`)"
        >
          <img
            class="aspect-square w-200px mt-50px"
            :src="item.productPic"
            :alt="item.productName"
          />
          <h4 class="my-8px hover:text-orange-500">
            {{ item.productName }}
          </h4>
          <p class="text-orange-500 text-14px leading-21px">
            {{ item.leastPrice }}
          </p>
        </n-card>
      </div>
      <div class="my-100px" v-else>
        <n-empty description="你什么也找不到">
          <template #extra>
            <n-button
              size="small"
              type="primary"
              @click="$router.push('/home')"
            >
              看看别的
            </n-button>
          </template>
        </n-empty>
      </div>
    </n-tab-pane>
    <n-tab-pane display-directive="show" name="desc" tab="价格降序">
      <div
        v-if="searchs.products.length !== 0 ? true : false"
        class="w-full flex flex-wrap gap-14px"
      >
        <n-card
          v-for="item in searchs.descDatas"
          :key="item.productId"
          class="w-296px h-430px flex flex-col text-center"
          hoverable
          @click="$router.push(`/${item.productId}`)"
        >
          <img
            class="aspect-square w-200px mt-50px"
            :src="item.productPic"
            :alt="item.productName"
          />
          <h4 class="my-8px hover:text-orange-500">
            {{ item.productName }}
          </h4>
          <p class="text-orange-500 text-14px leading-21px">
            {{ item.leastPrice }}
          </p>
        </n-card>
      </div>
      <div class="my-100px" v-else>
        <n-empty description="你什么也找不到">
          <template #extra>
            <n-button
              size="small"
              type="primary"
              @click="$router.push('/home')"
            >
              看看别的
            </n-button>
          </template>
        </n-empty>
      </div>
    </n-tab-pane>
  </n-tabs>
</template>

<script setup>
import { reactive, onMounted } from "vue";
import { api } from "@/api";
import { onBeforeRouteUpdate } from "vue-router";

const props = defineProps(["search"]);

const searchs = reactive({
  currentNav: "综合",
  products: [],
  ascDatas: [],
  descDatas: [],
});

async function init(body) {
  const [e, r] = await api.getCateProduct(body);
  if (!e && r) {
    searchs.products = r.data;
  }
  let arr = [...searchs.products];
  const [ea, ra] = await api.getCateProductAsc(arr);
  if (!ea && ra) {
    searchs.ascDatas = ra.data;
  }
  const [eas, ras] = await api.getCateProductDesc(arr);
  if (!eas && ras) {
    searchs.descDatas = ras.data;
  }
}

onBeforeRouteUpdate(async (to, from) => {
  const Rex = /[\u4e00-\u9fa5\s]+/g;
  let n = to.path.match(Rex);
  let body = {
    search: n[0],
  };
  init(body);
});

onMounted(() => {
  let body = {
    search: props.search,
  };
  init(body);
});
</script>

<style scoped></style>
