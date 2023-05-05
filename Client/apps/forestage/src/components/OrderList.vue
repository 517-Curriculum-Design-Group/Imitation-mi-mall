<script setup>
import { ref } from 'vue'
const checked = ref(false)

const value = ref(null)
const options = [
    {
        value: "广西",
        label: "广西",
        children: [{
            value: "桂林市",
            label: "桂林市"
        },
        {
            value: "南宁市",
            label: "南宁市",
        }
        ]
    }
]
</script>

<template>
    <article class="order-list-box flex justify-between items-center w-[1226px] h-[30px] m-auto mb-[20px]">
        <div class="order-list flex items-center w-auto h-[30px]">
            <li tabindex="1">综合</li>
            <li tabindex="1">新品</li>
            <li tabindex="1">销量</li>
            <li tabindex="1">价格</li>
        </div>

        <form class="flex items-center flex-row-reverse h-[30px] w-[800px]">

            <input type="checkbox" id="btn1">仅看有货
            <label for="btn1"></label>

            <input type="checkbox" id="btn2">促销
            <label for="btn2"></label>

            <input type="checkbox" id="btn3">分期
            <label for="btn3"></label>

            <!-- <span class="address relative w-7rem h-1.8rem border-solid border-1 border-gray-300 ml-0.4rem"></span> -->
            <!-- <div class="address-box  absolute w-38rem h-18rem
             pt-3rem pr-1.5rem pl-1.5rem bg-light-50 top-6.1rem
             border-1 border-solid border-gray-300
             shadow-lg" :class="{show : isShow}"> -->
            <!-- <input class="address-input w-full h-2.5rem border-solid border-gray-300 border-1 pl-3rem hover:border-gray-500"
             placeholder="输入街道、乡镇、小区或商圈名称" > -->

            <n-cascader tabindex="1" v-model:value="value" placeholder="请选择地址" expand-trigger='hover' :options="options"
                check-strategy='child' class="address" style="--n-border-hover:1px solid var(--button-background-color) !important;--n-border-focus:1px solid var(--button-background-color);width:125px;height:30px;"/>

            <!-- </div> -->
            <span class="mr-[8px]">收货地</span>

        </form>


    </article>
</template>

<style scoped lang="scss">
li {
    display: flex;
    justify-content: center;
    border-left: 0.5px solid #e0e0e0;
    padding: 0 2rem;
    cursor: pointer;

    &::marker {
        color: transparent;
    }

    &:nth-child(1) {
        border: none;
    }

    &:hover {
        color: var(--button-background-color);
    }

    &:focus {
        color: var(--button-background-color);
    }

}

input[type="checkbox"] {
    display: none;
}

label {
    position: relative;
    height: 1.4rem;
    width: 1.4rem;
    margin-left: 15px;
    margin-right: 8px;
    border: 1px solid #e3e3e3;
    cursor: pointer;
}

label::before {
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

input:checked+label {
    background-color: var(--button-background-color)
}

input:checked+label::before {
    color: white;
    opacity: 1;
}

// .address-box,.show {
//     visibility: visible;
// }

// .address:hover+.address-box,
// .address-box:hover,
// .address-box:has(.address-input:hover)
// {
//     visibility: visible;
//     z-index: 500;
//     background-color: red;
// }
</style>