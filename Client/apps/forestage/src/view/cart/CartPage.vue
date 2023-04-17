<script setup>
import { ref, reactive, watch, computed } from 'vue'
import CartTop from './CartTop.vue';

const goods = reactive([
    {
        name: "1",
        price: 500,
        count: 1,
        checked: false,
    },
    {
        name: "2",
        price: 1000,
        count: 2,
        checked: false,
    },
    {
        name: "3",
        price: 200,
        count: 3,
        checked: false,
    }
])

const checkStatus = computed(() => {
    return goods.every(item => item.checked == true)
})

const sum = computed(() => {
   const totalprice = goods.reduce((pre, now) => {
        if (now.checked == true)
            return pre + now.count * now.price
        else
            return pre
    }, 0)
    return totalprice
})

const goodsLength = computed(() => {
   const sumCount = goods.reduce((pre, now) =>{
    if(now.checked == true)
    return pre + 1;
    else
    return pre;
},0)
   return sumCount
})

watch(goods, sum)

function AddCount(index) {
    goods[index].count++;
}

function DecCount(index) {
    if (goods[index].count == 0) return 0;
    goods[index].count--;
}

function checkAll() {
    if (checkStatus.value) goods.forEach(item => item.checked = false)
    else goods.forEach(item => item.checked = true)
}

function ChangeChecked(index) {
    goods[index].checked = !goods[index].checked
}
</script>

<template>
    <CartTop></CartTop>

    <div class="container w-100% h-100%">
        <div class="cart grid w-80% bg-light-50 m-auto">
            <div class="flex flex-row-reverse">
                <input id="checkbox" type="checkbox">全选
                <label :class="['mr-1rem',checkStatus?'checkbox-active':'']" for="checkbox" @click="checkAll()"></label>
            </div>
            <div class="name">商品名称</div>
            <div>单价</div>
            <div>数量</div>
            <div>小计</div>
            <div>操作</div>
        </div>

        <div class="cart grid w-80% bg-light-50 m-auto" v-for="(items, index) in goods" :key="index">
            <div class="flex flex-row-reverse text-light-50">
                <input :id=index type="checkbox"  :checked="goods[index].checked" :name="index">全选
                <label :class="{'checkbox-active': goods[index].checked,'mr-1rem': true}" :for="index" @click="ChangeChecked(index)"></label>
            </div>
            <div class="goodsimg"></div>
            <div class="goodsname text-lg">{{ items.name }}</div>
            <div class="goodsprice text-lg">{{ items.price }}元</div>

            <div class="goodscoun relative w-9rem h-2.5rem border-solid border-1 border-gray-300">
                <button class="h-full w-30% cursor-pointer bg-light-50 
                                text-xl font-semibold text-gray-500 pb-5px float-left
                                hover:bg-gray-200" @click="DecCount(index)">-</button>

                <span class="absolute text-lg top-10% left-45%">{{ items.count }}</span>

                <button class="h-full w-30% cursor-pointer bg-light-50 
                                text-xl font-semibold text-gray-500 pb-5px float-right
                                hover:bg-gray-200" @click="AddCount(index)">+</button>
            </div>

            <div class="goodstotalprice text-lg">{{ items.count * items.price }}元</div>
            <div class="relative">
                <div class="delete i-mdi-window-close relative text-gray-500 cursor-pointer z-100"></div>
                <span class="delete-bg absolute w-1.5rem h-1.5rem bg-red-700 rounded-full z-0"></span>
            </div>
        </div>

        <div class="cartBottm sticky flex justify-between w-77rem h-3rem m-auto mt-5 bg-light-50">
            <div class="text-gray-400 leading-3rem space-x-12"> 
                <span class="ml-3rem">继续购物</span>
                <span>已选择 <span class="all">{{ goodsLength }}</span>件</span>
            </div>
            <div>
                <span class="all leading-3rem mr-4rem">合计:
                <span class="text-3xl">{{ sum
                }}</span>
                元</span>
                <button class="sumbutton w-12rem h-full text-light-50 text-lg">去结算</button>
            </div>
        </div>
    </div>
</template>

<style scoped lang="scss">
.container {
    background-color: #f5f5f5;
    padding: 2rem 0;
}

.cart {
    border: none;
    height: fit-content;
    grid-template-columns: repeat(8, 1fr);
    grid-template-rows: repeat(auto-fill, 4.5rem);
    justify-items: center;
    align-items: center;

    &+.cart {
        border-top: 1px solid #ccc;
    }
}

.name {
    grid-column: 2/span 3;
}

.goodsname {
    grid-column: 3/span 2;
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
    top: -30%;
    left: -35%;
}

.delete:hover {
    color: white;

    &+.delete-bg {
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

</style>