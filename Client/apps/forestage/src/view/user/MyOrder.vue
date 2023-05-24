<script setup>
import { ref, onMounted, onBeforeMount } from 'vue'
import { api } from "@/api";
import { useRouter } from 'vue-router';
import { useDetailStore } from '@/stores/detail.js'

const store = useDetailStore()
const router = useRouter();
const value = ref('')

let OrderId = ref([])
let orderList = ref([])
let orderDetail = ref([])
let copy = ref([])

let noPay = ref([])
let pay = ref([])
let out = ref([])
let cancel = ref([])
let complete = ref([])

onMounted(async () => {
    const [e, r] = await api.getOrderList()
    orderList.value = r.data
    OrderId.value = r.data.map((item) => item.order_id)
    for (let i = 0; i < orderList.value.length; i++) {
        const [e, r] = await api.getOrderDetails(OrderId.value[i])
        console.log(r.data.orderDetail.orderStatus)
        if (r.data.orderDetail.orderStatus === 0) noPay.value.push(r.data)
        else if (r.data.orderDetail.orderStatus === 1) pay.value.push(r.data)
        else if (r.data.orderDetail.orderStatus === 2) out.value.push(r.data)
        else if (r.data.orderDetail.orderStatus === 3) complete.value.push(r.data)
        else cancel.value.push(r.data)
        store.setMostProduct(out.value)
        orderDetail.value.push(r.data)
    }
    copy.value = orderDetail.value
    console.log(copy.value)
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
    switch (status) {
        case 0: return "未支付"
        case 1: return "未发货"
        case 2: return "已发货"
        case 3: return "已完成"
        case 4: return "已取消"
    }
}
const orderId = ref('')
function order(id) {
    orderId.value = id
    router.push(`/user/${orderId.value}`)
}

let currentTab = ref(0)
const menu = ["全部订单", "未支付", "未发货", "已发货", "已完成", "订单回收站"]

function update(index) {
    if (index === 0) {
        orderDetail.value = copy.value
    }

    else if (index === 1) {
        orderDetail.value = noPay.value
    }

    else if (index === 3) {
        orderDetail.value = out.value
    }

    else if (index === 4) {
        orderDetail.value = complete.value
    }

    else if (index === 2) {
        orderDetail.value = pay.value
    }

    else orderDetail.value = cancel.value
}

</script>

<template>
    <h1 class="w-[882px] h-[68px] text-gray-500">我的订单</h1>
    <article class="flex justify-between items-center w-100% h-[56px]">
        <div class="flex">
            <!-- <li tabindex="1">全部订单</li>
            <li tabindex="1">待支付</li>
            <li tabindex="1">待收货</li> -->
            <li tabindex="1" v-for="(items, index) in menu" :key="index" :class="{ active: currentTab === index }"
                @click="update(index)">{{ items }}</li>
        </div>

        <!-- <div class="flex w-[296.6px] h-[41.6px] container">
            <n-input class="leading-[41.6px] search" v-model:value="value"
                style="--n-border-hover:1px solid var(--button-background-color);--n-border-focus:1px solid var(--button-background-color);--n-box-shadow-focus: var(--button-background-color);--n-caret-color:var(--button-background-color)"
                type="text" placeholder="输入商品名称、订单号" />
            <n-button class="w-[42px] h-[42px] search-btn"
                style="--n-border-hover:1px solid var(--button-background-color);--n-padding:0 4px">
                <span class="i-mdi-magnify w-[24px] h-[24px]"></span>
            </n-button>
        </div> -->
    </article>

    <article class="flex flex-col w-full h-auto">
        <h2 v-if="!orderDetail.length" class="text-gray-400 text-center">暂无符合订单。</h2>
        <div class="flex flex-col w-full h-auto border-1 border-solid border-orange-500 mt-4"
            v-for="(items, index) in orderDetail" :key="index" v-else>

            <div
                class="order-top w-full h-[100px] flex flex-col border-r-0 border-t-0 border-l-0 border-b-1 border-solid border-orange-500 p-[20px]">
                <span class="w-full h-[27px] text-2xl text-orange-500">{{ clearStatus(items.orderDetail.orderStatus)
                }}</span>
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

                <div class="flex flex-col">
                    <div class="flex h-[80px]" v-for="(item, num) in items.productDetail.productList" :key="num">
                        <img class="w-[60px] h-[40px]" :src="item.skuImage" />
                        <div class="flex flex-col">
                            <div class="w-auto h-[14px] text-lg">{{ item.productName }} {{ mySkuname(item.skuName) }}</div>
                            <div class="w-auto h-[14px] text-lg mt-2">{{ item.skuPrice }}元 x{{ item.skuQuantity }}</div>
                        </div>
                    </div>
                </div>

                <div class="flex flex-col h-[auto]">
                    <n-button type="error" class="w-[120px] h-[34px] mb-4"
                        style="--n-color:var( --text-hover-color);--n-color-hover:var(--button-background-color);--n-color-pressed:var(--button-background-color);--n-color-focus:var(--button-background-color);--n-color-disabled:var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-border:1px solid var( --text-hover-color);--n-border-hover:1px solid var( --text-hover-color);--n-border-pressed: 1px solid var( --text-hover-color);--n-border-focus: 1px solid var( --text-hover-color);--n-border-disabled: 1px solid var( --text-hover-color);"
                        v-if="!items.orderDetail.orderStatus">
                        立即支付
                    </n-button>

                    <n-button type="tertiary" class="w-[120px] h-[34px]"
                        style="--n-text-color-hover:gray;--n-color-disabled:gray;--n-ripple-color:gray;--n-border-hover:1px solid gray;--n-border-pressed: 1px solid gray;--n-border-focus: 1px solid gray;--n-border-disabled: 1px solid gray;"
                        @click="order(items.orderDetail.orderId)">
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

    &:active {
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