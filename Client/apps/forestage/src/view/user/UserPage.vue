<script setup>
import { ref } from 'vue'

const menu = [
    {
        label: "订单中心",
        children: [{
            label: "我的订单",
            name: "order"
        },
        {
            label: "晒单评价",
            name: "comment"
        }
        ]
    },
    {
        label: "个人中心",
        children: [{
            label: "我的个人中心",
            name: "main"
        },
        {
            label: "消息通知",
            name: "news"
        },
        {
            label: "喜欢的商品",
            name:"like",
        },
        {
            label: "收货地址",
            name: "address"
        }
        ]
    },
    {
        label: "账户管理",
        children: [{
            label: "个人信息",
            name: "info"
        },
        {
            label: "修改密码",
            name: "password"
        },
        {
            label: "注销服务",
            name: "deleteServer"
        }]
    }
]

</script>

<template>
    <div class="container h-100% bg-gray-100 flex justify-between">
        <div class="flex justify-between w-80% h-full m-auto bg-transparent">
            <nav class="w-[234px] h-auto bg-light-50 pt-[36px] pb-[36px]">
                <ul tabindex="1" class="text-xl ml-5 leading-15" v-for="(items, index) in menu" :key="index">
                    {{ items.label }}
                   <li tabindex="1" class="text-lg pb-3 cursor-pointer hover:text-gray-700"
                        v-for="item in menu[index].children" :key="item">
                        <router-link :to="'/user/' + item.name">{{ item.label }}</router-link>
                    </li>
                </ul>
            </nav>

            <article class="flex-1 ml-1rem bg-light-50">
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

article{
    padding: 36px 48px;
}
</style>