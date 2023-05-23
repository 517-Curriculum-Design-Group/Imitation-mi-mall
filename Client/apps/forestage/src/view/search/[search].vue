<template>
  <n-tabs
    v-model:value="searchs.currentNav"
    class="navsC px-[8%]"
    type="line"
    animated
  >
    <n-tab-pane display-directive="show" name="综合" tab="综合">
      <div class="w-full flex flex-wrap gap-14px">
        <n-card
          v-for="item in searchs.products"
          :key="item.productId"
          class="w-296px h-430px flex flex-col text-center"
          hoverable
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
    </n-tab-pane>
    <n-tab-pane display-directive="show" name="价格升序" tab="价格升序">
      价格升序
    </n-tab-pane>
    <n-tab-pane display-directive="show" name="价格降序" tab="价格降序">
      价格降序
    </n-tab-pane>
  </n-tabs>
  {{ searchs.products }}
</template>

<script setup>
import { reactive, onMounted } from "vue";
import { api } from "@/api";
import { onBeforeRouteUpdate } from "vue-router";

const props = defineProps(["search"]);

const searchs = reactive({
  currentNav: "综合",
  products: [],
});

async function init(body) {
  const [e, r] = await api.getCateProduct(body);
  if (!e && r) {
    searchs.products = r.data;
  }
}

onBeforeRouteUpdate(async (to, from) => {
  const Rex = /[\u4e00-\u9fa5]+/g;
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
