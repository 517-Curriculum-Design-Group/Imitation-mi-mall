<script setup>
import { ref, onMounted, onBeforeMount, reactive } from 'vue'
import { api } from "@/api";
import { useRouter } from 'vue-router';
import { useDetailStore } from '@/stores/detail.js'
import { userStore } from "@/stores/user.js";
import { message } from 'ant-design-vue';

const store = useDetailStore()
const router = useRouter();

let list = ref([])
let product = ref([])

async function init() {
    list.value = await store.getMostProduct()
    list.value.forEach(items => {
        // product.value.push(items.value.productDetail)
        console.log(items.productDetail)
    })
    product.value = list.value.productList
    console.log(product.value)
}

onMounted(async () => {
    await init()
})

const mySkuname = (skuName) => {
    return skuName.replace(/\{|\}/g, "").replace(/"/g, "");
}

function toComment(items) {
    console.log(items)
    store.setCurrentProduct(items)
    console.log(store.getCurrentProduct())
    router.push("/user/realseComment")
}
</script>

<template>
    <h1 class="w-[882px] h-[68px] text-gray-500">订单评价</h1>
    <h2 v-if="list.length === 0" class="text-gray-400" style="text-align:center">暂无评价。</h2>

    <div v-else class="flex">
        
        <div class="flex flex-col h-auto">

            <div class="flex flex-col w-full h-auto p-4" v-for="(items, index) in list" :key="index">
                
                <div class="w-[882px] h-[100px] flex items-center" v-for="(item, num) in items.productDetail.productList"
                    :key="num">
                    <img class="h-[40px] w-[40px]" :src="item.skuImage" />
                    <p>{{ item.productName }}<br>{{ mySkuname(item.skuName) }}</p>
                    <span class="m-auto">{{ item.skuPrice }}元 x{{ item.skuQuantity
                    }}</span>
                    <n-button class="text-light-50 bg-orange-500 w-[120px] h-[34px]" attr-type="button" style="
            --n-color-hover: var(--button-background-color);
            --n-border-hover: var(--n-border-hover);
            --n-text-color-hover: white;
            --n-boreder-focus: var(--button-background-color);
            --n-ripple-color: var(--button-background-color);
            --n-color-focus: gray;
            --n-text-color-focus: white;
          " @click="toComment(item)">
                        立即评价
                    </n-button>
                </div>
            </div>
            <n-divider />
        </div>
    </div>
</template>
