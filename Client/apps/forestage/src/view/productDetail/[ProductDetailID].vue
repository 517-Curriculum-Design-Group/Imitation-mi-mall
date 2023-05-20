<script setup>
import { api } from "@/api";
import ProductHead from "./ProductHead.vue";
import { ref, onMounted, reactive, watchEffect } from "vue";
import { useRoute } from "vue-router";

let product = ref({});
const values = reactive({});
const objKeys = reactive([]);
const productPrice = ref("");
const productStock = ref("");
const route = useRoute();

watchEffect(async () => {
  let params = { product_id: 0, attributeValues: [] };
  params["product_id"] = product.value["productId"];
  objKeys.forEach((key) => params.attributeValues.push(values[key]));
  const [e, r] = await api.getProductPrice(params);
  if (!e && r) {
    productPrice.value = r.data.price;
    productStock.value = r.data.stock;
  }
});

const init = async () => {
  console.log(route.params.productDetailID)
  const [e, r] = await api.getProductDetail(
    Number(route.params.productDetailID)
  );
  product.value = r.data;
  product.value.skuList.forEach((element) => {
    values[element.attributeName] = element.attributeValues[0];
    objKeys.push(element.attributeName);
  });
};

onMounted(() => {
  init();
});
</script>

<template>
  <div>
    <ProductHead :product-name="product.productName"></ProductHead>
    <main class="mt-20px flex justify-between w-[80%] mx-auto">
      <img
        class="aspect-square w-560px"
        :src="product.productPic"
        :alt="product.productName"
      />
      <article class="w-600px">
        <h2>{{ product.productName }}</h2>
        <p>{{ product.productDescription }}</p>
        <div class="min-h-100px flex flex-col w-[100%]">
          <template v-for="(item, index) in product.skuList" :key="index">
            <div class="my-8px h-90px">
              <h3>{{ item.attributeName }}</h3>
              <n-radio-group
                v-model:value="values[item.attributeName]"
                :default-value="values[item.attributeName]"
                :name="item.attributeName"
                size="large"
                ><n-radio-button
                  v-for="i in item.attributeValues"
                  :key="i"
                  :value="i"
                  :label="i"
              /></n-radio-group>
            </div>
          </template>
        </div>
        <n-card embedded class="mb-16px">
          <p class="flex">
            {{ product.productName }}
            <span v-for="key in objKeys" :key="key" class="px-1"
              >{{ values[key] }}
            </span>
            <span class="flex-1 text-right">{{ productPrice }}元</span>
          </p>

          <p class="text-orange-500 text-24px pb-auto">
            总计：{{ productPrice }}元
          </p>
          <p class="text-gray-500 text-15px">库存：{{ productStock }}</p>
        </n-card>
        <div class="flex gap-x-12px">
          <n-button strong secondary class="w-300px h-54px">
            加入购物车
          </n-button>
          <n-button strong secondary type="tertiary" class="w-142px h-54px">
            喜欢
          </n-button>
        </div>
      </article>
    </main>
  </div>
</template>
