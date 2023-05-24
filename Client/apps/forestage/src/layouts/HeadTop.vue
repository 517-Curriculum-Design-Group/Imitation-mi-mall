<script setup>
import utils from "@/utils";
import { userStore } from "@/stores/user.js";
import { postLogout } from "@/api/path/UserController/index.js";
import SvgVue from "@/components/TwemojiFaceSavoringFood.vue";

const Userstore = userStore();
// const goods = reactive([{ name: "1", price: 100, count: 1 }, { name: "2", price: 500, count: 2 }])
const goods = [];
const user = Userstore.getUserInfo();
const userid = user.userId;

const totalprice = goods.reduce((pre, now) => {
  return pre + now.count * now.price;
}, 0);

//登出接口
const logout = async () => {
  const isLogout = await postLogout(userid);
  Userstore.userInfo.userId = null;
  utils.clearSession();
  if (isLogout) window.location.reload();
};
</script>

<template>
  <div class="containers">
    <n-button quaternary type="info" class="w-130px">
      <SvgVue class="h-20px w-20px mr-5px" />
      去秒杀
    </n-button>
    <a-button
      type="primary"
      class="cart flex justify-center"
      @click="$router.push('/cart')"
    >
      <span
        v-if="!goods.length"
        class="i-mdi-shopping-search w-1rem h-1rem mr-1"
      ></span>
      <span v-else class="i-mdi-shopping w-1rem h-1rem mr-1"></span>
      购物车 ({{ goods.length }})
    </a-button>
    <div v-if="!goods.length" class="cartdetail bg-light-50 text-xs">
      购物车中还没有商品，赶紧选购吧！
    </div>
    <div v-else class="cartdetail bg-light-50 text-xs">
      <div v-for="(items, index) in goods" :key="index" class="goodslist">
        {{ items.name }}
      </div>
      <div class="total">
        <span class="detail">
          <span class="text-xs">共{{ goods.length }}件商品</span>
          <span class="price text-lg">¥{{ totalprice }}</span>
        </span>
        <a-button type="primary" size="large" class="cart-btn"
          >去购物车结算</a-button
        >
      </div>
    </div>
    <span class="space"></span>
    <router-link to="/user/order"
      ><a-button v-if="userid" type="default" class="order"
        >我的订单</a-button
      ></router-link
    >
    <span v-if="userid" class="seq"></span>
    <router-link to="/user/news">
      <a-button type="default" class="news">消息通知</a-button></router-link
    >
    <span v-if="!userid" class="seq"></span>
    <router-link to="/login"
      ><a-button v-if="!userid" type="default" class="register"
        >注册</a-button
      ></router-link
    >
    <span v-if="!userid" class="seq"></span>
    <a-button
      v-if="!userid"
      type="default"
      class="login"
      @click="$router.push('/login')"
      >登录</a-button
    >
    <span v-if="userid" class="seq"></span>
    <div v-if="userid" class="user flex relative w-auto h-full cursor-pointer">
      <n-avatar
        round
        class="mr-12px"
        size="small"
        :src="user.avatar ? user.avatar : 'empty.png'"
        fallback-src="https://www.gstatic.com/android/keyboard/emojikitchen/20201001/u1f924/u1f924_u1f42d.png"
      />
      {{ user.nickName }}
      <span class="i-mdi-chevron-down ml-4px w-1.2rem h-1.2rem"></span>
    </div>
    <div class="user-name flex absolute text-xs w-[110px] shadow-lg z-300">
      <router-link to="/user/main"
        ><span class="user-info w-full h-[30px] cursor-pointer"
          >个人中心</span
        ></router-link
      >
      <router-link to="/user/comment"
        ><span class="user-info w-full h-[30px] cursor-pointer"
          >晒单评价</span
        ></router-link
      >
      <router-link to="/user/like"
        ><span class="user-info w-full h-[30px] cursor-pointer"
          >我的喜欢</span
        ></router-link
      >
      <span class="user-info w-full h-[30px] cursor-pointer" @click="logout"
        >退出登录</span
      >
    </div>
  </div>
</template>

<style scoped lang="scss">
.containers {
  display: flex;
  flex-direction: row-reverse;
  align-items: center;
  width: 100%;
  height: 40px;
  background-color: #333333;
}

.cart {
  align-items: center;
  font-size: 11px;
  width: 120px;
  height: 40px;
  background-color: #424242;
  border-color: #333333;
}

.cart:hover,
.cart:has(+ .cartdetail:hover) {
  background-color: white;
  color: var(--text-hover-color);
  border-top-color: #b0b0b0;
  border-bottom-color: white;
}

.register,
.login,
.news,
.order {
  width: fit-content;
  height: 40px;
  text-align: center;
  font-size: 10px;
  color: #b0b0b0;
  background-color: #333333;
  border-color: #333333;
  text-align: center;
  padding: 4px 8px;
}

.register:focus,
.login:focus,
.news:focus,
.order:focus {
  background-color: #333333;
  border-color: #333333;
  color: #b0b0b0;
}

.register:active,
.register:hover,
.login:active,
.login:hover,
.news:hover,
.news:active,
.order:hover,
.order:active {
  background-color: #333333;
  border-color: #333333;
  color: white;
}

.seq {
  height: 40%;
  border: 1px solid #424242;
}

.space {
  background-color: transparent;
  height: 100%;
  width: 1%;
}

.cartdetail {
  box-sizing: border-box;
  display: flex;
  position: absolute;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  overflow: hidden;
  top: 40px;
  right: 140px;
  width: 316px;
  height: 0;
  min-height: 0;
  z-index: 300;
  box-shadow: rgba(0, 0, 0, 0.15) 0px 5px 15px 0px;
  transition: all 250ms linear;
}

.cart:hover + .cartdetail,
.cartdetail:hover {
  min-height: 100px;
  height: fit-content;
  /* max-height: fit-content; */
}

.goodslist {
  padding: 5%;
  width: 100%;
}

.total {
  padding: 5%;
  width: 100%;
}

.detail {
  display: flex;
  flex-direction: column;
  float: left;
  height: 100%;
  width: 40%;
}

.price {
  flex: 1;
  color: var(--text-hover-color);
}

.cart-btn {
  float: right;
  background-color: var(--button-background-color);
  border-color: var(--button-background-color);
}

.user {
  color: #b0b0b0;
  align-items: center;
  padding: 4px 12px;

  &:hover,
  &:has(+ .user-name:hover) {
    color: var(--button-background-color);
    background-color: white;
  }

  &:hover + .user-name {
    visibility: visible;
    height: 164px;
  }
}

.user-name {
  justify-content: space-around;
  align-items: center;
  flex-direction: column;
  top: 40px;
  right: 410px;
  visibility: hidden;
  height: 0;
  padding: 7px 0px;
  overflow: hidden;
  background-color: white;
  transition: all 250ms linear;

  &:hover {
    visibility: visible;
    height: 164px;
  }
}

.user-info {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;

  &:hover {
    color: var(--button-background-color);
    background-color: #f5f5f5;
  }
}
</style>
