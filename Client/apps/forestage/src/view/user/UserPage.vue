<script setup>
const menu = [
    {
        label: "订单中心",
        children: ["我的订单", "评价晒单"]
    },
    {
        label: "个人中心",
        children: ["我的个人中心", "消息中心", "喜欢的商品", "收获地址"]
    },
    {
        label: "账户管理",
        children: ["个人信息", "修改密码", "注销服务"]
    }
]
</script>

<template>
    <div class="container bg-gray-100 flex justify-between">
        <div class="flex justify-between w-80% h-full m-auto bg-transparent">
            <nav class="w-15rem  h-auto bg-light-50">
                <ul class="text-lg pb-5 leading-15" v-for="(items, index) in menu" :key="index">
                    {{ items.label }}
                    <li tabindex="1" class="text-base pb-3 cursor-pointer hover:text-gray-700"
                        v-for="(item, num) in menu[index].children" :key="num">
                        {{ item }}
                    </li>
                </ul>
            </nav>

            <article class="flex-1 bg-red-300 ml-1rem">
            <router-view></router-view>
        </article>
        </div>
    </div>
</template>

<style scoped lang="scss">
li {
    color: var(--text-default-color);

    &::marker {
        color: transparent;
    }

    &:focus {
        color: var(--button-background-color);
    }
}
</style>