import { defineComponent } from 'vue';
<script setup>
import { ref, defineProps, onMounted } from "vue";
import { getSession } from "@/utils/session";
// eslint-disable-next-line vue/require-prop-types
const props = defineProps(["data"]);

const sessionData = getSession("usehomeStore");
const titles = ref([]);

const init = () => {
  let obj = props?.data;
  if (obj) {
    titles.value = Object.keys(obj);
  } else {
    obj = sessionData?.leftCategories;
    if (obj) {
      titles.value = Object.keys(obj);
    }
  }
};

console.log(props.data);

onMounted(() => {
  init();
});
</script>

<template>
  <div class="flex ml-auto mr-auto w-[1226px] h-[460px] relative">
    <div
      class="w-[226px] h-full absolute top-0 left-0 z-100"
      style="background-color: rgba(105, 101, 101, 0.6)"
    >
      {{ data }}
      <div
        class="flex flex-col w-[100%] h-[100%] px-[30px] py-[20px] text-light-500"
      >
        <nav
          v-for="item in titles"
          :key="item"
          class="flex-1 hover:text-orange-500 cursor-pointer"
        >
          {{ item }}
        </nav>
      </div>
    </div>
    <n-carousel
      show-arrow
      dot-type="dot"
      class="w-[1226px] flex relative"
      autoplay
      dot-placement="right"
    >
      <img
        class="carousel-img"
        src="https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/f9b7e85d4159deccc3f00c3aa96cc5f3.jpg?w=2452&h=920"
      />
      <img
        class="carousel-img"
        src="https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/d24b07f5fb10927b4cdcfce9a57a168a.jpg?thumb=1&w=1533&h=575&f=webp&q=90"
      />
      <img
        class="carousel-img"
        src="https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/46d41dd62cff2259e56e678205616e0f.jpg?thumb=1&w=1533&h=575&f=webp&q=90"
      />
      <img
        class="carousel-img"
        src="https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/b631a8788272ad133da984bd1503f989.jpg?thumb=1&w=1533&h=575&f=webp&q=90"
      />
      <img
        class="carousel-img"
        src="https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/08a9d7eec7b055bac247b5068f817b1d.jpg?thumb=1&w=1533&h=575&f=webp&q=90"
      />
    </n-carousel>
  </div>
</template>

<style scoped>
.carousel-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>
