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

const mySkuname = (skuName) => {
    return skuName.replace(/\{|\}/g, "").replace(/"/g, "");
}

function dateFormat(daterc) {

    if (daterc) {
        if (daterc.indexOf('T') === -1) return daterc
        const arr = daterc.split('T')
        const d = arr[0]
        const darr = d.split('-')
        const t = arr[1]
        const tarr = t.split('.000')
        const marr = tarr[0].split(':')
        const dd = darr[0] + '-' + darr[1] + '-' + darr[2] + ' ' + marr[0] + ':' + marr[1] + ':' + marr[2].substring(0, 2)
        return dd
    }
}

function clearStatus(status) {
    switch(status){
        case 0: return "未支付"
        case 1: return "已支付"
        case 2: return "已发货"
        case 3: return "已完成"
    }
}
</script>

<template>
    <h1 class="w-[882px] h-[68px] text-gray-500">我的订单</h1>
    <article class="flex justify-between items-center w-100% h-[56px]">
        <div class="flex">
            <li tabindex="1">全部订单</li>
            <li tabindex="1">待支付</li>
            <li tabindex="1">待收货</li>
            <li tabindex="1">订单回收站</li>
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

    <article class="flex flex-col w-full h-auto">
        <!-- <div class="w-full h-auto flex justify-between border-1 border-solid border-orange-500 mt-4 p-2"
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
        </div> -->
        <div class="flex flex-col w-full h-auto border-1 border-solid border-orange-500 mt-4"
            v-for="(items, index) in orderDetail" :key="index">

            <div
                class="order-top w-full h-[100px] flex flex-col border-r-0 border-t-0 border-l-0 border-b-1 border-solid border-orange-500 p-[20px]">
                <span class="w-full h-[27px] text-2xl text-orange-500">{{ clearStatus(items.orderDetail.orderStatus) }}</span>
                <div class="flex justify-between mt-4">
                    <div class="flex items-center">
                        <span class="flex items-center text-gray-500">{{ dateFormat(items.orderDetail.orderTime) }}</span>
                        <span class="w-[1px] h-[14px] border-1 border-gray-300 border-solid ml-2 mr-2"></span>
                        <span class="flex items-center text-gray-500">订单号:{{ items.orderDetail.orderId }}</span>
                        <span class="w-[1px] h-[14px] border-1 border-gray-300 border-solid ml-2 mr-2"></span>
                        <span class="flex items-center text-gray-500">在线支付</span>
                    </div>

                    <div class="text-gray-500">应付金额：<span class="text-4xl text-black">{{ items.productDetail.totalPrice
                    }}</span>元</div>
                </div>
            </div>

            <div class="flex justify-between w-full h-auto p-8">
                <div class="flex flex-col h-[80px]" v-for="(item, num) in items.productDetail.productList" :key="num">
                    <div class="w-auto h-[14px] text-lg">{{ item.productName }} {{ mySkuname(item.skuName) }}</div>
                    <div class="w-auto h-[14px] text-lg mt-2">{{ item.skuPrice }}元 x{{ item.skuQuantity }}</div>
                </div>

                <div class="flex flex-col h-[auto]">
                    <n-button type="error" class="w-[120px] h-[34px] mb-4" style="--n-color:var( --text-hover-color);--n-color-hover:var(--button-background-color);--n-color-pressed:var(--button-background-color);--n-color-focus:var(--button-background-color);--n-color-disabled:var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-border:1px solid var( --text-hover-color);--n-border-hover:1px solid var( --text-hover-color);--n-border-pressed: 1px solid var( --text-hover-color);--n-border-focus: 1px solid var( --text-hover-color);--n-border-disabled: 1px solid var( --text-hover-color);">
                        立即支付
                    </n-button>
                    <n-button type="tertiary" class="w-[120px] h-[34px]"  style="--n-text-color-hover:gray;--n-color-disabled:gray;--n-ripple-color:gray;--n-border-hover:1px solid gray;--n-border-pressed: 1px solid gray;--n-border-focus: 1px solid gray;--n-border-disabled: 1px solid gray;">
                        订单详情
                    </n-button>
                </div>

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

.order-top {
    background-color: #fffaf7;
}
</style>