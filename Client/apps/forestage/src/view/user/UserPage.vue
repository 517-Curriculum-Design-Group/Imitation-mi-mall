<script setup>
import { useRouter } from 'vue-router'
const menu = [
    {
        label: "订单中心",
        children: [{
            label: "我的订单",
            name: "order"
        },
        {
            label: "晒单评价",
            name: "commment"
        }
        ]
    },
    {
        label: "个人中心",
        children: [{
            label: "我的个人中心",
            name: "me"
        },
        {
            label: "消息中心",
            name: "news"
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

const router = useRouter()

function TurnTo(item) {
    router.push({
        name: item.name
    })
}
</script>

<template>
    <div class="container bg-gray-100 flex justify-between">
        <div class="flex justify-between w-80% h-full m-auto bg-transparent">
            <nav class="w-15rem  h-auto bg-light-50">
                <ul class="text-lg pb-5 leading-15" v-for="(items, index) in menu" :key="index">
                    {{ items.label }}
                    <li tabindex="1" class="text-base pb-3 cursor-pointer hover:text-gray-700"
                        v-for="item in menu[index].children" :key="item" @click="TurnTo(item)">
                        {{ item.label }}
                    </li>
                </ul>
            </nav>

            <article class="flex-1 ml-1rem">
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