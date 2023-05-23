<template>
  <div class="flex flex-col divBg">
    <n-breadcrumb
      separator=">"
      class="w-full mx-[8%] text-[12px] leading-[40px] h-48px"
      style="--n-font-size: 12px"
    >
      <n-breadcrumb-item href="/home"> 首页</n-breadcrumb-item>
      <n-breadcrumb-item> 全部结果</n-breadcrumb-item>
    </n-breadcrumb>
    <div
      class="bg-light-50 px-[8%] h-84px flex text-center leading-86px overflow-hidden transition-all"
      :class="moreAbout ? 'isExpand' : ''"
    >
      <span class="text-[#b0b0b0]">分类：</span>
      <div
        class="h-84px inline-block w-[1150px] flex flex-wrap my-[12px] pl-[100px]"
      >
        <span
          v-for="item in search.categorys"
          :key="item"
          class="w-148px h-54px text-[14px] leading-[66px] text-left cursor-pointer hover:text-orange-500"
          :class="active === item.categoryName ? 'text-orange-500' : ''"
          @click="$router.push(`/shop/${item.categoryName}`)"
          >{{ item.categoryName }}</span
        >
      </div>
      <span
        class="cursor-pointer h-48px"
        @click="moreAbout = moreAbout ? false : true"
        >更多</span
      >
    </div>
    <RouterView></RouterView>
  </div>
</template>

<script setup>
import { api } from "@/api";
import { onMounted, reactive, ref, computed } from "vue";
import { useRoute, onBeforeRouteUpdate } from "vue-router";

const route = useRoute();
const moreAbout = ref(false);
const active = ref(route.params.search);

const search = reactive({
  categorys: [],
});

const categoryName = computed(() => {
  let arr = [];
  search.categorys.forEach((item) => {
    arr.push(item.categoryName);
  });
  return arr;
});

async function init() {
  const [e, r] = await api.getCategoryList();
  if (!e && r) {
    search.categorys = r.data;
    let o = {
      categoryName: "全部",
    };
    search.categorys.unshift(o);
  }
}

onBeforeRouteUpdate(async (to, from) => {
  const Rex = /[\u4e00-\u9fa5\s]+/g;
  let n = to.path.match(Rex);
  let flag = categoryName.value.includes(n[0]);
  active.value = flag ? n[0] : "全部";
});

onMounted(() => {
  init();
});
</script>

<style scoped>
.divBg {
  background-color: rgb(245, 245, 245);
}

.isExpand {
  height: 302px;
  transition: height 200ms linear;
}

.navsC :deep(.n-tabs-nav-scroll-content) {
  margin-top: 18px;
  line-height: 2;
}
</style>
