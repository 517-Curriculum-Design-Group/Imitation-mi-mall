<script setup>
import { ref, onMounted, onBeforeMount, reactive } from 'vue'
import { api } from "@/api";
import { useRouter } from 'vue-router';
import { useNotification } from 'naive-ui'
import { useDetailStore } from '@/stores/detail.js'

const store = useDetailStore()
const router = useRouter();
const orderId = parseInt(router.currentRoute.value.params.orderId)

const orderList = ref(null)
let addressDetail = reactive({
    newName: null,
    newPhone: null,
    newAddress: null,
    orderId: orderId,
})

const copy = reactive({})
onBeforeMount(async () => {
    const [e, r] = await api.getOrderDetails(orderId)
    orderList.value = r.data
    console.log(r.data)
    addressDetail.newName = r.data.addressDetail.name
    addressDetail.newPhone = r.data.addressDetail.phone
    addressDetail.newAddress = r.data.addressDetail.address
    Object.assign(copy, addressDetail)
})

function clearStatus(status) {
    switch (status) {
        case 0: return "未支付"
        case 1: return "未发货"
        case 2: return "已发货"
        case 3: return "已完成"
        case 4: return "已取消"
    }
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

const mySkuname = (skuName) => {
    return skuName.replace(/\{|\}/g, "").replace(/"/g, "");
}

let isShow = ref(false)
const notification = useNotification()
const update = async (addressDetail) => {
    console.log(addressDetail)
    const [e, r] = await api.updateOrderDetails(addressDetail)
    if (r.code === 200) {
        notify('success')
        isShow.value = false
    }
    else {
        Object.assign(addressDetail, copy)
        notify('error')
    }
}

function anotherUpdate() {
    Object.assign(addressDetail, copy)
    isShow.value = false
}

function notify(type) {
    if (type === 'success') {
        notification['success']({
            content: "修改成功",
            meta: "收货信息已变更",
            duration: 2500,
            keepAliveOnHover: true
        });
    }
    else {
        notification['error']({
            content: "修改失败",
            meta: "请仔细核对收货信息",
            duration: 2500,
            keepAliveOnHover: true
        });
    }
}

const thefalse = false

function toComment(items){
    console.log(items)
    store.setCurrentProduct(items)
    console.log(store.getCurrentProduct())
    router.push("/user/realseComment")
}
</script>

<template>
    <div class="w-full h-auto flex flex-col">

        <span class="i-mdi-arrow-expand-left w-[30px] h-[30px] text-gray-500 cursor-pointer" @click="router.go(-1)"></span>
        <h1 class="w-[120px] h-[50px] text-gray-500">订单详情</h1>
        <div class="text-black">订单号： {{ orderList.orderDetail.orderId }}</div>
        <n-divider />
        <div class="text-orange-500 text-2xl">{{ clearStatus(orderList.orderDetail.orderStatus) }}</div>
        <div class="text-gray-500">{{ dateFormat(orderList.orderDetail.orderTime) }}</div>

        <div class="flex flex-col w-full h-auto" v-for="(items, index) in orderList.productDetail.productList" :key="index">
            <div class="flex items-center w-full h-[100px]">
                <img class="w-[70px] h-[50px]" :src="items.skuImage" />
                <div class="flex flex-col ml-4">
                    <span>{{ items.productName }}</span>
                    <span>{{ mySkuname(items.skuName) }}</span>
                </div>
                <span class="m-auto">{{ items.skuPrice }}元 x{{ items.skuQuantity }}</span>
                <n-button class="text-light-50 bg-orange-500 w-[120px] h-[34px]" attr-type="button" style="
            --n-color-hover: var(--button-background-color);
            --n-border-hover: var(--n-border-hover);
            --n-text-color-hover: white;
            --n-boreder-focus: var(--button-background-color);
            --n-ripple-color: var(--button-background-color);
            --n-color-focus: gray;
            --n-text-color-focus: white;
          " @click="toComment(items)">
                    立即评价
                </n-button>
            </div>

            <n-divider style="margin-top:5px;margin-bottom: 10px;" />
        </div>

        <div class="flex justify-between mt-15 mb-4">
            <span class="text-2xl">收货信息</span>
            <n-button type="tertiary" class="w-[120px] h-[34px]"
                style="--n-text-color-hover:gray;--n-color-disabled:gray;--n-ripple-color:gray;--n-border-hover:1px solid gray;--n-border-pressed: 1px solid gray;--n-border-focus: 1px solid gray;--n-border-disabled: 1px solid gray;"
                @click="isShow = true">
                修改
            </n-button>
        </div>

        <span class="text-gray-500">姓名：{{ addressDetail.newName }}</span>
        <span class="text-gray-500">联系电话：{{ addressDetail.newPhone }}</span>
        <span class="text-gray-500">收货地址：{{ addressDetail.newAddress }}</span>
        <n-divider />

        <span class="text-2xl">支付方式</span>
        <span class="text-gray-500 mt-6">支付方式：在线支付</span>
        <n-divider />

        <span class="text-2xl">发票信息</span>
        <span class="text-gray-500 mt-6">发票类型：电子普通发票</span>
        <span class="text-gray-500">发票内容：商品购买明细</span>
        <span class="text-gray-500">发票抬头：个人</span>
        <n-divider />

        <div class="flex flex-row-reverse">
            <div class="flex flex-col">
                <span class="flex text-sm text-gray-500 w-[200px] h-auto">商品总价:<span class="text-orange-500 ml-auto">{{
                    orderList.productDetail.totalPrice }}元</span></span>
                <span class="flex text-sm text-gray-500 w-[200px] h-auto">运费:<span
                        class="text-orange-500 ml-auto">0元</span></span>
                <span class="flex text-sm text-gray-500 w-[200px] h-auto mt-3">应付金额:<span
                        class="text-orange-500 text-4xl ml-auto">{{ orderList.productDetail.totalPrice }}元</span></span>
            </div>
        </div>
    </div>

    <n-modal v-model:show="isShow" :trap-focus=thefalse :mask-closable="false">
        <n-form class="flex userForm flex-col w-[660px] h-auto shadow-lg bg-light-50 p-4 t-[100px]">
            <div class="flex justify-between w-[635px] h-[60px] pt-[14px] pb-[14px] pl-[20px] pr-[20px] bg-gray-100">
                <h2>修改收货地址</h2>
            </div>
            <n-space tabindex="1">
                <n-form-item path="newName" label="姓名" :rule="{
                    required: true,
                    message: '请输入姓名',
                    trigger: ['blur']
                }">
                    <n-input v-model:value="addressDetail.newName" placeholder="姓名" clearable
                        style="width:303px;height:40px" />
                </n-form-item>

                <n-form-item path="phone" label="手机号" placeholder="手机号" :rule="{
                    required: true,
                    message: '请输入号码',
                    trigger: ['blur']
                }">
                    <n-input v-model:value="addressDetail.newPhone" clearable placeholder="手机号"
                        style="width:303px;height:40px" />
                </n-form-item>
                <n-form-item path="address" label="收货地址" placeholder="手机号" :rule="{
                    required: true,
                    message: '请输入收货地址',
                    trigger: ['input', 'blur']
                }">
                    <n-input v-model:value="addressDetail.newAddress" clearable placeholder="收货地址"
                        style="width:606px;height:40px" />
                </n-form-item>
            </n-space>

            <div class="flex justify-center gap-8 w-[660px] h-[81px] mt-4">
                <n-button class="text-light-50 bg-orange-500 w-[160px] h-[40px]" attr-type="button"
                    style="--n-color-hover:var(--button-background-color);--n-border-hover:var(--n-border-hover);--n-text-color-hover:white;
                --n-boreder-focus:var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-color-focus:gray;--n-text-color-focus:white" @click="update(addressDetail)">
                    确定
                </n-button>
                <n-button class="text-light-50 bg-gray-400 w-[160px] h-[40px]" attr-type="button"
                    style="--n-color-hover:gray;--n-border-hover:gray;--n-text-color-hover:white;--n-boreder-focus:var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-color-focus:gray;--n-text-color-focus:white"
                    @click="anotherUpdate()">
                    取消
                </n-button>
            </div>
        </n-form>
    </n-modal>
</template>

            <!-- <n-cascader tabindex="1" v-model:value="value" placeholder="请选择地址" expand-trigger='hover' :options="options"
                check-strategy='child' class="address"
                style="--n-border-hover:1px solid var(--button-background-color) !important;--n-border-focus:1px solid var(--button-background-color);width:620px;height:40px;" /> -->