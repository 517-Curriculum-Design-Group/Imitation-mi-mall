<template>
  <div
    w="234px"
    h="300px"
    class="flex flex-col text-center leading-[21px] card-shadow"
  >
    <img :src="src" :alt="alt" class="object-cover aspect-square w-[160px]" />
    <h3 m="0px 10px 2px">{{ title }}</h3>
    <p class="text-cardDesc" m="0px 10px 10px">{{ desc }}</p>
    <span class="text-orangeColor" m="0px 10px 14px">{{ price }}</span>
  </div>
</template>

<script setup>
// eslint-disable-next-line vue/require-prop-types
const props = defineProps(["src", "alt", "title", "desc", "price"]);
</script>

<style scoped>
.card-shadow:hover {
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
}
</style>
