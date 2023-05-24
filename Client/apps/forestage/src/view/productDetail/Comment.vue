<script setup>
import { api } from "@/api";
import ProductHead from "./ProductHead.vue";
import { onMounted, reactive, ref } from "vue";
import { useRoute } from 'vue-router';
import { userStore } from "@/stores/user.js";

const Userstore = userStore();
const route = useRoute();
const productId = parseInt(route.params.id)

let allComment = reactive({})
let comments = ref([])
let moreCm = {
    commentId: null,
    userId: Userstore.getUserInfo().userId,
    productId: productId,
    content: null,
    rate: null,
    commentTime: null,
    parentId: 1,
    delFlag: 0
}
async function init() {
    const [e, r] = await api.getComment({ productId: productId, pageNum: 1, pageSize: 10000 })
    if (!e && r) {
        Object.assign(allComment, r.data)
        comments.value = allComment.rows
    }
}

let currentTab = ref(0)
onMounted(() => {
    init()
})


const menu = ["全部评价", "好评", "一般", "差评"]

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

let isShow = ref(false)
</script>

<template>
    <ProductHead class="fixed"></ProductHead>

    <div class="w-full h-100vh flex justify-center" style="background-color:#f5f5f5;">
        <article class="flex flex-col w-[1226px] h-full ">
            <div class="bg-light-50 w-full h-[150px] mt-10 p-8">
                <h2 class="text-gray-400">商品评价</h2>
                <span class="flex justify-around">
                    <n-button class="text-light-50 bg-gray-400 w-[160px] h-[40px]" attr-type="button"
                        style="--n-color-hover:var(--button-background-color);--n-border-hover:var(--button-background-color);--n-text-color-hover:white;--n-boreder-focus:var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-color-focus:var(--button-background-color);--n-text-color-focus:white;ba"
                        v-for="(btn, num) in menu" :key="num" :class="{ active: currentTab === num }">
                        {{ btn }}
                    </n-button>
                </span>
            </div>

            <h2 class="text-gray-500 mt-10">商品评价</h2>

            <div class="w-full h-auto">
                <a-comment v-for="(items, index) in comments" :key="index">
                    <template #actions>
                        <div class="text-lg text-orange-500">{{ items.rate }}</div>
                        <span key="comment-nested-reply-to" class="text-lg ml-10">回复</span>


                    </template>
                    <template #author>
                        <span class="text-lg">{{ items.nickName }}</span>
                    </template>
                    <!-- <template #avatar>
                        <a-avatar src="https://joeschmoe.io/api/v1/random" alt="Han Solo" />
                    </template> -->
                    <template #content>
                        <p class="text-2xl">
                            {{ items.content }}
                        </p>
                    </template>
                    <template #datetime>
                        <span class="text-gray-400">{{ dateFormat(items.commentTime) }}</span>
                    </template>

                    <a-comment v-for="(item, sec) in items.children" :key="sec">
                        <template #author>
                            <span class="text-2xl">{{ item.nickName }}</span>
                        </template>
                        <!-- <template #avatar>
                        <a-avatar src="https://joeschmoe.io/api/v1/random" alt="Han Solo" />
                    </template> -->
                        <template #content>
                            <p class="text-2xl">
                                {{ item.content }}
                            </p>
                        </template>
                        <template #datetime>
                            <div class="text-gray-400">
                                {{ dateFormat(item.commentTime) }}
                            </div>
                        </template>
                    </a-comment>
                </a-comment>

                <a-modal v-model:visible=isShow title="Basic Modal" @ok="handleOk">
                    <p>Some contents...</p>
                    <p>Some contents...</p>
                    <p>Some contents...</p>
                </a-modal>
            </div>
        </article>
    </div>
</template>