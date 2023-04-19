<script setup>
import { ref } from 'vue'
const checked = ref(false)
</script>

<template>
    <article class="order-list-box w-79.8% h-5.5rem m-auto">
        <div class="order-list float-left w-40% h-100%">
            <li tabindex="1" >综合</li>
            <li tabindex="1" >新品</li>
            <li tabindex="1" >销量</li>
            <li tabindex="1" >价格</li>
        </div>

        <form class="type-list float-right h-100% w-50%">

            <input type="checkbox" class="w-1.2rem h-1.2rem border-gray-400 mr-0.4rem" id="btn1">仅看有货
            <label class="mr-0.4rem ml-2rem" for="btn1"></label>

            <input type="checkbox" class="w-1.2rem h-1.2rem mr-0.4rem" id="btn2">促销
            <label class="mr-0.4rem ml-2rem" for="btn2"></label>

            <input type="checkbox" class="w-1.2rem h-1.2rem mr-0.4rem" id="btn3">分期
            <label class="mr-0.4rem ml-2rem" for="btn3"></label>

            <span class="address relative w-7rem h-1.8rem border-solid border-1 border-gray-300 ml-0.4rem"></span>
            <div class="address-box absolute w-38rem h-18rem
             pt-3rem pr-1.5rem pl-1.5rem bg-light-50 top-6.1rem
             border-1 border-solid border-gray-300
             shadow-lg">
             <input class="address-input w-full h-2.5rem border-solid border-gray-300 border-1 pl-3rem hover:border-gray-500"
             placeholder="输入街道、乡镇、小区或商圈名称" >
            </div>
            <span>收货地</span>

        </form>


    </article>
</template>

<style scoped lang="scss">
.order-list {
    display: flex;
    align-items: center;
}

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

    &:focus{
        color: var(--button-background-color);
    }

}

.type-list {
    display: flex;
    align-items: center;
    flex-direction: row-reverse;
    flex: 1;
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
.address-box{
    visibility: hidden;
}

.address:hover + .address-box,
.address-box:hover,.address-inpuut:hover{
    visibility: visible;
    z-index: 500;
}

.address-input{
    background: url(https://api.iconify.design/mdi:magnify.svg) no-repeat 100%;
    background-position: 1rem;
    z-index:1000;
}

</style>