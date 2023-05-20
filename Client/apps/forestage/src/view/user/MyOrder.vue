<script setup>
import { ref } from 'vue'
import { api } from "@/api";
import { onMounted } from "vue";

const value = ref('')

let OrderId = ref([])
let orderList = ref([])
let orderDetail = ref([])

onMounted(async () => {
    const [e, r] = await api.getOrderList()
    orderList.value = r.data
    OrderId.value = r.data.map((item) => item.order_id)
    for (let i = 0; i < orderList.value.length; i++) {
        const [e, r] = await api.getOrderDetails(OrderId.value[i])
        orderDetail.value.push(r.data)
    }
    console.log(orderDetail.value)
})

const mySkuname = (skuName)=>{
    return skuName.replace(/\{|\}/g, "").replace(/"/g, "");
}
</script>

<template>
    <h1 class="w-[882px] h-[68px] text-gray-500">我的订单</h1>
    <article class="flex justify-between items-center w-100% h-[56px]">
        <div class="flex">
            <li tabindex="1">全部有效订单</li>
            <li tabindex="1">待收货</li>
        </div>

        <div class="flex w-[296.6px] h-[41.6px] container">
            <n-input class="leading-[41.6px] search" v-model:value="value"
                style="--n-border-hover:1px solid var(--button-background-color);--n-border-focus:1px solid var(--button-background-color);--n-box-shadow-focus: var(--button-background-color);--n-caret-color:var(--button-background-color)"
                type="text" placeholder="输入商品名称、订单号" />
            <n-button class="w-[42px] h-[42px] search-btn"
                style="--n-border-hover:1px solid var(--button-background-color);--n-padding:0 4px">
                <span class="i-mdi-magnify w-[24px] h-[24px]"></span>
            </n-button>
        </div>
    </article>

    <article class="flex flex-col h-auto">
        <div class="w-full h-auto flex justify-between border-1 border-solid border-gray-400 mt-4 p-2"
            v-for="(items, index) in orderDetail" :key="index">

            <div class="flex gap-8">
                <span class="flex flex-col items-center">
                    <span class="text-2xl" v-for="(item, num) in items.productDetail.productList" :key="num">{{ item.productName }}</span>
                </span>
                <span class="flex flex-col  mt-4">
                    <span class="text-sm text-gray-400" v-for="(item, num) in items.productDetail.productList" :key="num">{{ mySkuname(item.skuName) }}</span>
                </span>
            </div>

            <div class="flex flex-row-reverse">
                <span class="text-2xl text-orange-500">¥{{ items.productDetail.totalPrice }}</span>
                <span class="flex items-center text-sm mr-5">订单状态</span>
                <span class="flex items-center text-sm text-gray-400 mr-2">{{ items.orderDetail.orderTime }}</span>
                <span class="flex items-center mr-4">{{ items.addressDetail.address }}</span>
            </div>
        </div>
    </article>
</template>


<style scoped lang="scss">
li {
    display: flex;
    justify-content: center;
    border-left: 1px solid #e0e0e0;
    padding: 0 2rem;
    cursor: pointer;

    &::marker {
        color: transparent;
    }

    &:nth-child(1) {
        border: none;
        padding-left: 0;
    }

    &:hover {
        color: var(--button-background-color);
    }

    &:focus {
        color: var(--button-background-color);
    }

}

:deep(.search-btn):hover {
    color: white;
    background-color: var(--button-background-color);
}
</style>