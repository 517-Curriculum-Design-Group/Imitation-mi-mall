<script setup>
import { reactive, watch, computed, onMounted } from "vue";
import CartTop from "./CartTop.vue";
import { api } from "@/api";
import { useDialog } from "naive-ui";

const goods = reactive([]);
const dialogWarning = useDialog();

const checkStatus = computed(() => {
  return goods.every((item) => item.checked == true);
});

const sum = computed(() => {
  const totalprice = goods.reduce((pre, now) => {
    if (now.checked == true) return pre + now.sku_quantity * now.sku_price;
    else return pre;
  }, 0);
  return totalprice;
});

const goodsLength = computed(() => {
  const sumCount = goods.reduce((pre, now) => {
    if (now.checked == true) return pre + 1;
    else return pre;
  }, 0);
  return sumCount;
});

watch(goods, sum);

function AddCount(index) {
  goods[index].sku_quantity++;
}

function DecCount(index) {
  if (goods[index].sku_quantity == 0) return 0;
  goods[index].sku_quantity--;
}

function checkAll() {
  if (checkStatus.value) goods.forEach((item) => (item.checked = false));
  else goods.forEach((item) => (item.checked = true));
}

function ChangeChecked(index) {
  goods[index].checked = !goods[index].checked;
}

async function DeleteCartPro(cart_id) {
  let data = {
    cartId: Number(cart_id),
  };
  dialogWarning.warning({
    title: "请确认",
    content: "确认删除所选商品吗？",
    negativeText: "取消",
    positiveText: "确定",
    onPositiveClick: async () => {
      const [e, r] = await api.deleteCartProduct(data);
      if (!e && r) {
        goods.length = 0;
        r.data.forEach((item) => goods.push(item));
        goods.map((item) => (item.checked = true));
      }
    },
  });
}

async function init() {
  const [e, r] = await api.getCartList();
  if (!e && r) {
    r.data.forEach((item) => goods.push(item));
    goods.map((item) => (item.checked = true));
  }
}

onMounted(() => {
  init();
});
</script>

<template>
  <CartTop></CartTop>
  <div class="containers w-100% h-100%">
    <div class="cart grid w-80% bg-light-50 m-auto h-116px">
      <div class="flex flex-row-reverse" :row-span="2">
        <input id="checkbox" type="checkbox" />全选
        <label
          :class="['mr-1rem', checkStatus ? 'checkbox-active' : '']"
          for="checkbox"
          @click="checkAll()"
        ></label>
      </div>
      <div class="name" :row-span="2">商品名称</div>
      <div :row-span="2">单价</div>
      <div :row-span="2">数量</div>
      <div :row-span="2">小计</div>
      <div :row-span="2">操作</div>
    </div>

    <div
      v-for="(items, index) in goods"
      :key="index"
      class="cart grid w-80% bg-light-50 m-auto"
    >
      <div class="flex flex-row-reverse text-light-50" :row-span="2">
        <input
          :id="index"
          type="checkbox"
          :checked="goods[index].checked"
          :name="index"
        />全选
        <label
          :class="{ 'checkbox-active': goods[index].checked, 'mr-1rem': true }"
          :for="index"
          @click="ChangeChecked(index)"
        ></label>
      </div>
      <div class="goodsimg" :row-span="2">
        <img
          class="h-80px aspect-square object-cover"
          :src="items.sku_image"
          :alt="items.product_name"
        />
      </div>
      <div class="goodsname text-lg" :row-span="2">
        {{ items.product_name }}
      </div>
      <div class="goodsprice text-lg" :row-span="2">
        {{ items.sku_price }}元
      </div>

      <div
        :row-span="2"
        class="goodscoun relative w-9rem h-2.5rem border-solid border-1 border-gray-300"
      >
        <button
          class="h-full w-30% cursor-pointer bg-light-50 text-xl font-semibold text-gray-500 pb-5px float-left hover:bg-gray-200"
          @click="DecCount(index)"
        >
          -
        </button>

        <span class="absolute text-lg top-10% left-45%">{{
          items.sku_quantity
        }}</span>

        <button
          class="h-full w-30% cursor-pointer bg-light-50 text-xl font-semibold text-gray-500 pb-5px float-right hover:bg-gray-200"
          @click="AddCount(index)"
        >
          +
        </button>
      </div>

      <div class="goodstotalprice text-lg" :row-span="2">
        {{ items.sku_quantity * items.sku_price }}元
      </div>
      <div class="relative" :row-span="2" @click="DeleteCartPro(items.cart_id)">
        <div
          class="delete i-mdi-window-close relative text-gray-500 cursor-pointer z-100"
        ></div>
        <span
          class="delete-bg absolute w-1.5rem h-1.5rem bg-red-700 rounded-full z-0"
        ></span>
      </div>
    </div>

    <div
      class="sticky flex justify-between w-[80%] h-70px mx-auto leading-70px mt-5 bg-light-50 text-center"
    >
      <div class="text-gray-400 leading-70px space-x-12">
        <span
          class="ml-3rem cursor-pointer goShoppingBut"
          @click="$router.push('/home')"
          >继续购物</span
        >
        <span
          >已选择 <span class="all">{{ goodsLength }}</span
          >件</span
        >
      </div>
      <div>
        <span class="all leading-3rem mr-4rem"
          >合计:
          <span class="text-3xl">{{ sum }}</span>
          元</span
        >
        <button class="sumbutton w-12rem h-full text-light-50 text-lg">
          去结算
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.containers {
  background-color: #f5f5f5;
  padding: 2rem 0;
}

.cart {
  border: none;
  height: fit-content;
  block-size: 96px;
  grid-template-columns: repeat(8, 1fr);
  grid-template-rows: repeat(auto-fill, 4.5rem);
  justify-items: center;
  align-items: center;

  & + .cart {
    border-top: 1px solid #ccc;
  }
}

.name {
  grid-column: 3 / span 2;
}

.goodsname {
  grid-column: 3 / span 2;
}

.goodstotalprice {
  color: var(--text-hover-color);
}

input[type="checkbox"] {
  display: none;
}

label {
  position: relative;
  height: 1.4rem;
  width: 1.4rem;
  border: 1px solid #e3e3e3;
  cursor: pointer;

  &::before {
    position: absolute;
    height: 0.3rem;
    width: 0.7rem;
    border: 3px solid #e3e3e3;
    border-top: none;
    border-right: none;
    top: 30%;
    left: 25%;
    transform: rotate(-45deg);
    content: "";
    opacity: 0;
  }
}

.checkbox-active {
  background-color: var(--button-background-color);

  &::before {
    opacity: 1;
  }
}

.delete-bg {
  opacity: 0;
  top: -20%;
  left: -18%;
}

.delete:hover {
  color: white;

  & + .delete-bg {
    opacity: 1;
  }
}

.cartBottom {
  align-items: center;
}

.all {
  color: var(--text-hover-color);
}

.sumbutton {
  background-color: var(--text-hover-color);

  &:hover {
    background-color: var(--button-background-color);
  }
}

.goShoppingBut:hover {
  color: var(--text-hover-color);
}
</style>
