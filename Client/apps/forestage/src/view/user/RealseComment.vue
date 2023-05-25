<script setup>
import { ref, onMounted, onBeforeMount, reactive } from 'vue'
import { api } from "@/api";
import { useRouter } from 'vue-router';
import { useDetailStore } from '@/stores/detail.js'
import { userStore } from "@/stores/user.js";
import { message } from 'ant-design-vue';

const router = useRouter()
const store = useDetailStore()
const Userstore = userStore();
let currentComment = reactive({
    commentId: null,
    toCommentUserId: -1,
    toCommentId: -1,
    userId: Userstore.getUserInfo().userId,
    productId: null,
    content: null,
    rate: null,
    parentId: -1,
    delFlag: 0
})
let goods = reactive({})
onBeforeMount(async () => {
    Object.assign(goods, await store.getCurrentProduct())
    currentComment.productId = goods.productId
    console.log(currentComment.productId)
})

const mySkuname = (skuName) => {
    return skuName.replace(/\{|\}/g, "").replace(/"/g, "");
}

const menu = ["好评", "一般", "差评"]

async function update() {
    console.log(currentComment)
    const [e, r] = await api.addComment(currentComment)
    if (r.code === 200) {
        message.success('评论成功')
        router.go(-1)
    }
    else message.error('评论失败，请检查网络')
}

let selectedRate = ref(null)
function selectRate(item) {
    selectedRate.value = item
    currentComment.rate = item

    const buttons = document.querySelectorAll('.mybtn');
    buttons.forEach(button => button.classList.remove('active'));

    // 将选中按钮添加 "active" 类名
    const selectedButton = document.querySelector(`.mybtn[data-value="${item}"]`);
    selectedButton.classList.add('active');
}
</script>

<template>
    <div class="i-mdi-arrow-expand-left w-[30px] h-[30px] text-gray-500 cursor-pointer" @click="router.go(-1)"></div>
    <h1 class="w-[882px] h-[68px] text-gray-500">发表评论</h1>
    <article class="flex flex-col h-auto">
        <h3 class="text-gray-400">当前商品</h3>

        <n-divider />

        <div class="flex h-100px">
            <img class="w-[70px] h-[50px]" :src="goods.skuImage" />
            <p>{{ goods.productName }}<br>{{ mySkuname(goods.skuName) }}</p>
            <span class="ml-auto">{{ goods.skuPrice }}元 x{{ goods.skuQuantity
            }}</span>
        </div>
        <n-divider />

        <div class="flex items-center justify-around">你对此商品满意吗？
            <n-button class="mybtn w-[160px] h-[40px]" attr-type="button"
                style="--n-color-hover:var(--button-background-color);--n-border-hover:var(--button-background-color);--n-text-color-hover:white;--n-boreder-focus:var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-color-focus:var(--button-background-color);--n-text-color-focus:white;ba"
                v-for="(btn, num) in menu" :key="num" :class="{ active: selectedRate === btn }" @click="selectRate(btn)">
                {{ btn }}
            </n-button>
        </div>
        <textarea class="mt-4 h-150px" v-model="currentComment.content"></textarea>

        <n-button class="text-light-50 bg-orange-500 w-[160px] h-[40px] mt-4 ml-auto" attr-type="button" style="
            --n-color-hover: var(--button-background-color);
            --n-border-hover: var(--n-border-hover);
            --n-text-color-hover: white;
            --n-boreder-focus: var(--button-background-color);
            --n-ripple-color: var(--button-background-color);
            --n-color-focus: gray;
            --n-text-color-focus: white;
          " @click="update">
            评价
        </n-button>
    </article>
</template>

<style scoped>
.active {
    background-color: var(--button-background-color);
    color: aliceblue;
}
</style>