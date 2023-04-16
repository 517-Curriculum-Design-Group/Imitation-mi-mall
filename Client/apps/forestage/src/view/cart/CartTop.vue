<template>
    <div class="top w-full h-6.5rem">
        <img class="h-10 mr-2 ml-9rem mr-3rem" src="../../assets/xiaomilogo.svg" />
        <h1 class="mb-0 text-gray-700">我的购物车</h1>
        <p class="text-xs text-gray-500 pl-15px pt-20px">温馨提示：产品是否购买成功，以最终下单为准哦，请尽快结算</p>
        <span class="ml-auto mr-10.5rem text-gray-500 text-xs">
            <router-link to="/loginPage"><span>登录</span></router-link>
            <span class="seq border-1 border-solid border-gray-400 ml-1.5 mr-1.5"></span>
            <router-link to="/loginPage"><span>注册</span></router-link>
        </span>
    </div>
</template>

<style scoped lang="scss">
.top {
    display: flex;
    align-items: center;
    border-bottom: 2px solid var(--button-background-color);
}
</style>