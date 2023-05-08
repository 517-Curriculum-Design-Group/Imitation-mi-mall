<script setup>
import { useDetailStore } from '@/stores/searchAnddetail.js';
import { userStore } from '@/stores/user.js';
import { ProductHeader } from './ProductHead.vue'

const store = useDetailStore();
const product = store.getCurrentProduct()

const UserStore = userStore();
const address = UserStore.getUserInfo().address

const CurrentSku = {
    attr:'',
    color:'',
}
</script>

<template>
    <div>
        <Productdetail></Productdetail>

        <article class="flex w-[1226px] h-[1226px] ml-[147px] mr-[147px]">
            <div class="w-[560px] h-[560px]" :img="product.productPic"></div>

            <form class="flex flex-col items-center justify-center flex-1 ml-4">
                <h2 class="w-full h-[36px]">{{ product.productName }}</h2>
                <p class="text-gray-400 w-full h-[36px]">{{ product.productDescription }}</p>
                <span class="w-full h-[60px] text-orange-500 text-xl mt-[14px]">{{ "eee" || product.skuVoList[0].skuPrice
                }}元</span>
                <n-divider />

                <div class="w-full h-[104px] flex border-1 border-solid border-gray-300 bg-light-50">
                    <span class="i-mdi-map-marker w-[20px] h-[20px]"></span>
                    <span>{{ address }}</span>
                </div>

                <title class="text-xl w-full h-[25px]">选择版本</title>
                <div v-for="(items, index) in product.skuList[0].attributeValues" :key="index">
                    <span
                        class="w-[292px] h-[42px] bg-light-50 border-1 border-solid border-gray-300 text-gray-300 ml-2 mb-4 hover:text-orange-500 border-orange-500">{{
                            items }}</span>
                </div>

                <title class="text-xl w-full h-[25px]">选择颜色</title>
                <div v-for="(items, index) in product.skuList[1].attributeValues" :key="index">
                    <span
                        class="w-[292px] h-[42px] bg-light-50 border-1 border-solid border-gray-300 text-gray-300 ml-2 mb-4 hover:text-orange-500 border-orange-500">{{
                            items }}</span>
                </div>

                <n-spcae class="w-full h-[54px] flex items-center gpa-4">
                    <n-button class="text-light-50 bg-orange-500 w-[300px] h-[54px]" attr-type="button"
                        style="--n-color-hover:var(--button-background-color);--n-border-hover:var(--n-border-hover);--n-text-color-hover:white;
                --n-boreder-focus:var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-color-focus:gray;--n-text-color-focus:white">
                        加入购物车
                    </n-button>

                    <n-button class="text-light-50 bg-gray-400 w-[142px] h-[54px]" attr-type="button"
                        style="--n-color-hover:gray;--n-border-hover:gray;--n-text-color-hover:white;--n-boreder-focus:var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-color-focus:gray;--n-text-color-focus:white">
                        喜欢
                    </n-button>
                </n-spcae>
            </form>
        </article>
    </div>
</template>