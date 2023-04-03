<script setup>
import { defineComponent, onMounted, ref } from 'vue';

let currentgoods = ref([]);
let isShow = ref(false);

const productList = {
  categories: [
    {
      name: '手机',
      product: [
        { name: 'apple' },
        { name: 'huawei' },
      ],
    },
    {
      name: '电脑',
      product: [
        { name: 'apple' },
        { name: 'huawei' },
      ],
    },
    {
      name: '平板',
      product: [
        { name: 'apple' },
        { name: 'huawei' },
      ],
    },
  ],
};

function showProduct(index) {
  const product = productList.categories[index].product;
  if (product.length === 0) {
    return;
  }
  currentgoods.value = productList.categories[index].product.map((p) => p.name);
  isShow.value = true;
}

function showList() {
  isShow.value = false;
}
</script>

<template>
    <div class="container">
        <nav class="left-nav">
            <ul class="left-button" v-for="(item,index) in productList.categories" :key="item.name" @mouseenter="showProduct(index)" @mouseleave="showList()">{{ item.name }}</ul>
        </nav>
        <div class="goodslist" v-show="isShow" @mouseleave="showList()">
            <div class="goods" v-for="(list,index) in currentgoods" :key="index">{{ list }}
            </div>
        </div>
        <a-carousel autoplay class="map">
            <div class="detail" v-for="(item,index) in productList.categories" :key="item.name">{{ item.name }}</div>
        </a-carousel> 
</div> 
</template>

<style scoped>
.container {
    width:1226px;
    height:460px;
    margin-top:20px;
    background-color: gray;
    margin: auto;
}
.left-nav {
    float: left;
    z-index: 1000;
    padding-top: 10px;
    padding-bottom: 10px;
    width: 234px;
    height: 460px;
    background-color: #b0b0b0;
    position: absolute;
}
.left-button{
    padding-left: 30px;
    width: 234px;
    height: 42px;
    text-align: left;
    font-size: 14px;
    color: white;
    background-color: #b0b0b0;
    line-height:42px;
    cursor: pointer;
    margin-bottom: 0px;
}
.left-button:hover{
    background-color:var(--hover--color);
}
.map{
    z-index: 500;
    height: 460px;
}

.detail {
    height: 460px;
    display: flex;
    justify-content: center;
    text-align: center;
}

.goodslist{
    position: absolute;
    display: flex;
    justify-content:flex-start;
    align-items:flex-start;
    z-index: 2000;
    background-color:white;
    height: 460px;
    overflow-x: auto;
    padding: 10px;
    /* width: auto;
    width: 234px; */
    width: fit-content;
    margin-left: 234px;
    flex-wrap: wrap;
    flex-direction: column;
}
.goods{
    z-index: 1;
    width:117px;
    color: red;
    height: 5em;
}
</style>