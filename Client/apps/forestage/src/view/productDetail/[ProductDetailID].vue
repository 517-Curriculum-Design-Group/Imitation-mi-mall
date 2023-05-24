<script setup>
import { api } from "@/api";
import ProductHead from "./ProductHead.vue";
import { ref, onMounted, reactive, watchEffect, nextTick } from "vue";
import { useRoute, useRouter, onBeforeRouteUpdate } from "vue-router";
import { useDialog } from "naive-ui";
import utils from "@/utils";

let product = ref({});
const productPrice = ref("");
const productStock = ref("");
const skuId = ref("");

const values = reactive({});
const objKeys = reactive([]);

const router = useRouter();
const route = useRoute();
const dialogSuccess = useDialog();
const dialogWarning = useDialog();
const favorite = ref(false);

watchEffect(async () => {
  let params = { product_id: 0, attributeValues: [] };
  params["product_id"] = product.value["productId"];
  objKeys.forEach((key) => params.attributeValues.push(values[key]));

  const [e, r] = await api.getProductPrice(params);
  if (!e && r) {
    productPrice.value = r.data.price;
    productStock.value = r.data.stock;
    skuId.value = r.data.sku_id;
    favorite.value = r.data.favorite;
  }
});

async function shoppingClick() {
  if (utils.isLogin()) {
    let body = { sku_id: skuId.value };
    const [e, r] = await api.addSkuToCart(body);
    if (!e && r) {
      dialogSuccess.success({
        title: "加入购物车成功!",
        content: "去购物车结账？",
        positiveText: "结账",
        negativeText: "继续浏览",
        onPositiveClick: () => {
          router.push("/cart");
        },
      });
    }
  } else {
    dialogWarning.warning({
      title: "尚未登录",
      content: "是否登录？",
      positiveText: "确定",
      onPositiveClick: () => {
        router.push("/login");
      },
    });
  }
}

async function addFavorite(product_id) {
  if (utils.isLogin()) {
    const id = {
      productId: product_id,
    };
    if (favorite.value) {
      const [e, r] = await api.deleteLike(id);
      if (!e && r) {
        favorite.value = false;
      }
    } else {
      const [e, r] = await api.addProductToFavorite(id);
      if (!e && r) {
        favorite.value = true;
      }
    }
  } else {
    dialogWarning.warning({
      title: "尚未登录",
      content: "是否登录？",
      positiveText: "确定",
      onPositiveClick: () => {
        router.push("/login");
      },
    });
  }
}

const init = async (ID) => {
  const [e, r] = await api.getProductDetail(ID);
  product.value = r.data;
  objKeys.length = 0;
  product.value.skuList.forEach((element) => {
    values[element.attributeName] = element.attributeValues[0];
    if (!objKeys.includes(element.attributeName)) {
      objKeys.push(element.attributeName);
    }
  });
};

onBeforeRouteUpdate(async (to, from) => {
  const Rex = /\d+/;
  let n = to.path.match(Rex);
  init(Number(n[0]));
});

onMounted(() => {
  init(Number(route.params.productDetailID));
});
</script>

<template>
  <div>
    <ProductHead :product-name="product.productName" :product-id="product.productId"></ProductHead>
    <main class="mt-20px flex justify-between w-[80%] mx-auto">
      <img
        class="aspect-square w-560px h-560px object-cover pr-30px"
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
        <div class="flex gap-x-12px my-12px text-16px">
          <n-button
            type="primary"
            class="w-300px h-54px"
            :disabled="productStock === 0 ? true : false"
            @click="shoppingClick"
          >
            加入购物车
          </n-button>
          <n-button
            type="info"
            class="w-142px h-54px"
            @click="addFavorite(product.productId)"
          >
            <div
              v-if="favorite"
              class="i-ic-sharp-favorite?mask text-red-500 w-20px h-20px"
            />
            <div
              v-else
              class="i-ic-round-favorite-border?mask text-red-500 w-20px h-20px"
            />
            喜欢
          </n-button>
        </div>
      </article>
    </main>
  </div>
</template>
