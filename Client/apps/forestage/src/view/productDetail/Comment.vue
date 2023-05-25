<script setup>
import { api } from "@/api";
import ProductHead from "./ProductHead.vue";
import { onMounted, reactive, ref, nextTick } from "vue";
import { useRoute } from 'vue-router';
import { userStore } from "@/stores/user.js";
import { message } from 'ant-design-vue';

const Userstore = userStore();
const route = useRoute();
const productId = parseInt(route.params.id)

const avatar = Userstore.getUserInfo().avatar
let allComment = reactive({})
let comments = ref([])
let copy = ref([])

let good = ref([])
let common = ref([])
let bad = ref([])
let productName = ref('')

async function init() {
    const [e, r] = await api.getComment({ productId: productId, pageNum: 1, pageSize: 10000 })
    if (!e && r) {
        Object.assign(allComment, r.data)
        comments.value = allComment.rows
        copy.value = comments.value
        console.log(comments.value)
        comments.value.forEach(items => {
            if (items.rate === "好评") good.value.push(items)
            productName.value = items.productName
        })
        comments.value.forEach(items => {
            if (items.rate === "一般") common.value.push(items)
        })
        comments.value.forEach(items => {
            if (items.rate === "差评") bad.value.push(items)
        })

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
let currentComment = reactive({
    commentId: null,
    toCommentUserId: null,
    userId: Userstore.getUserInfo().userId,
    toCommentId: null,
    productId: productId,
    content: null,
    rate: "好评",
    parentId: null,
    delFlag: 0
})

function realseComment(comment) {
    currentComment.toCommentId = comment.commentId
    currentComment.toCommentUserId = comment.userId
    currentComment.parentId = comment.commentId
    console.log(currentComment)
    // Object.assign(currentComment, comment)
    isShow.value = true
}

async function commitComment() {
    console.log(currentComment)
    const [e, r] = await api.addComment(currentComment)
    console.log(r)
    if (r.code === 200) {
        nextTick(() => {
            init()
        })
        message.success('评论成功');
        isShow.value = false
    }
    else {
        message.error('评论失败，请检查网络')
        isShow.value = false
    }
}

function howLong(num) {
    if (num === 0) return copy.value.length
    else if (num === 1) return good.value.length
    else if (num === 0) return common.value.length
    else return bad.value.length
}

function selectList(index) {
    if (index === 0) comments.value = copy.value
    else if (index === 1) comments.value = good.value
    else if (index === 2) comments.value = common.value
    else comments.value = bad.value
}
</script>

<template>
    <ProductHead class="fixed" :product-name="productName"></ProductHead>

    <div class="w-full h-full flex justify-center" style="background-color:#f5f5f5;">

        <article class="flex flex-col w-[1226px] h-full ">
            <div class="bg-light-50 w-full h-[150px] mt-10 p-8">
                <h2 class="text-gray-400">商品评价</h2>
                <span class="flex justify-around">
                    <n-button class="w-[160px] h-[40px]" attr-type="button"
                        style="--n-color-hover:var(--button-background-color);--n-border-hover:var(--button-background-color);--n-text-color-hover:white;--n-boreder-focus:var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-color-focus:var(--button-background-color);--n-text-color-focus:white;ba"
                        v-for="(btn, num) in menu" :key="num" :class="{ active: currentTab === num }"
                        @click="selectList(num)">
                        {{ btn }}{{ "(" + howLong(num) + ")" }}
                    </n-button>
                </span>
            </div>

            <h2 class="text-gray-500 mt-10">商品评价</h2>

            <h2 class="text-gray-400 text-center" v-if="!comments.length">未找到评价。</h2>

            <div class="w-full h-auto bg-light-50 p-6" v-else>
                <a-comment v-for="(items, index) in comments" :key="index">
                    <template #actions>
                        <div class="text-lg text-orange-500">{{ items.rate }}</div>
                        <span key="comment-nested-reply-to" class="text-lg ml-10" @click="realseComment(items)">回复</span>

                    </template>
                    <template #author>
                        <span class="text-lg">{{ items.nickName }}</span>
                    </template>
                    <template #avatar>
                        <a-avatar :src="items.userAvatar" alt="图片无法加载" />
                    </template>
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
                        <template #avatar>
                            <a-avatar :src="item.userAvatar" alt="暂无" />
                        </template>
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

                <a-modal v-model:visible=isShow title="写下你的评论" @ok="commitComment">
                    <a-comment>
                        <template #avatar>
                            <a-avatar :src="avatar" alt="暂无图片" />
                        </template>
                        <template #content>
                            <a-form-item>
                                <a-textarea v-model:value="currentComment.content" :rows="4" />
                            </a-form-item>
                            <!-- <a-form-item>
                                <a-button html-type="submit" :loading="submitting" type="primary" @click="handleSubmit">
                                    Add Comment
                                </a-button>
                            </a-form-item> -->
                        </template>
                    </a-comment>
                </a-modal>
            </div>
        </article>



    </div>
</template>

<style scoped lang="scss">
.ant-modal-footer+.ant-btn-primary {
    color: #fff;
    border-color: var(--button-background-color) !important;
    background: var(--button-background-color);
    text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.12);
    box-shadow: 0 2px 0 rgba(0, 0, 0, 0.045);
}
</style>