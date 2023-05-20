<script setup>
import { usehomeStore } from "@/stores/home.js"
import { api } from "@/api";
import { onMounted, ref } from "vue";

const store = usehomeStore()
let category = ref([])
const categories = async () => {
    const [e, r] = await api.getLeftCategories()
    if (!e && r) {
        category.value = r.data
    }
    let isShow = ref(true)
    for(let i = 0; i < category.value.length; i++) {
        category.value[i].isShow = isShow.value
    }
}

onMounted(async () => {
   await categories()
   console.log(category.value)
})


</script>

<template>
    <article class="w-[1226px] h-auto flex flex-col gap-4 m-auto">
        <div class="w-[1226px] h-auto flex flex-wrap items-center" v-for="(items, index) in category" :key="index">
            <div class="flex w-[1226px] h-[74px] items-center">
                <span
                    class="i-mdi-arrow-down-drop-circle-outline w-[25px] h-[25px] mr-[18px] cursor-pointer text-gray-400 mt-1"
                    @click="items.isShow = !items.isShow" v-if="items.isShow"></span>
                <span
                    class="i-mdi-arrow-up-drop-circle-outline w-[25px] h-[25px] mr-[18px] cursor-pointer text-orange-400 mt-1"
                    @click="items.isShow = !items.isShow" v-else></span>
                <span class="text-orange-500 text-2xl">{{ items.parentCategoryName }}</span>
            </div>

            <div class="goods relative w-[245px] h-[161px] flex flex-wrap items-center justify-center"
                v-for="(item, num) in items.categoryNames" :key="num" v-if="items.isShow">
                <span class="w-[245px] h-[20px] hover:text-orange-500" style="text-align: center;"
                    v-if="!item.categoryNames">{{ item.category_name
                    }}</span>
                <span class="w-[1226px] h-[161px]" v-else>
                    <n-empty description="暂无子分类">

                    </n-empty>
                </span>
            </div>
        </div>
    </article>
</template>

<style scoped lang="scss">
.goods {
    &::before {
        position: absolute;
        content: '';
        width: 245px;
        height: 161px;
        bottom: 0;
        border: 1px solid #b0b0b0;
        border-left: 0px;
    }
}</style>

