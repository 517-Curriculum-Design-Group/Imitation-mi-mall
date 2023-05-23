<script setup>
import { ref, onMounted, onBeforeMount, reactive } from 'vue'
import { api } from "@/api";

const like = ref([])
onMounted(async () => {
    const [e, r] = await api.getLike()
    like.value = r.data
    console.log(like.value)
})

async function deleteLike(categoryId) {
    let deleteId = {
        productId: categoryId
    }
    const [e, r] = await api.deleteLike(deleteId)
    if (r.code === 200) window.location.reload();
}

</script>

<template>
    <h1 class="w-[882px] h-[68px] text-gray-500">喜欢的商品</h1>
    <n-empty v-if="!like.value" description="暂无喜爱的商品"></n-empty>
    <div class="w-full h-auto flex flex-wrap">
        <div class="flex flex-col w-[250px] h-auto justify-center" v-for="(items, index) in like">
            <img :src="items.productPic" />
            <span class="text-center">{{ items.categoryName }}</span>
            <span class="text-center text-orange-500 mt-2">{{ items.leastPrice }}</span>

            <div class="h-[30px] mt-8 flex justify-center items-center">
                <n-button type="tertiary" class="w-[80px] h-[30px]"
                    style="--n-text-color-hover:gray;--n-color-disabled:gray;--n-ripple-color:gray;--n-border-hover:1px solid gray;--n-border-pressed: 1px solid gray;--n-border-focus: 1px solid gray;--n-border-disabled: 1px solid gray;--n-text-color-focus:gray;--n-text-color-pressed:gray;"
                    @click="deleteLike(items.productId)">
                    删除
                </n-button>
                <router-link :to="'/' + items.productId">
                    <n-button type="error" class="w-[80px] h-[30px] ml-8"
                        style="--n-color:var( --text-hover-color);--n-color-hover:var(--button-background-color);--n-color-pressed:var(--button-background-color);--n-color-focus:var(--button-background-color);--n-color-disabled:var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-border:1px solid var( --text-hover-color);--n-border-hover:1px solid var( --text-hover-color);--n-border-pressed: 1px solid var( --text-hover-color);--n-border-focus: 1px solid var( --text-hover-color);--n-border-disabled: 1px solid var( --text-hover-color);">
                        查看详情
                    </n-button></router-link>
            </div>
        </div>
    </div>
</template>