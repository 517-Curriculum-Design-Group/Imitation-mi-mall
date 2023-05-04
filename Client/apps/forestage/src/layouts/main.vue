<template>
  <main class="min-w-[1226px]">
    <HeadTop></HeadTop>
    <SearchBox></SearchBox>
    <router-view></router-view>
  </main>
</template>

<script setup>
import HeadTop from "./HeadTop.vue";
import SearchBox from "./SearchBox.vue";
</script>
