<script setup>
// eslint-disable-next-line vue/require-prop-types
const props = defineProps(["src", "alt"]);
</script>

<template>
  <div w="316px" h="170px" class="card-shadow overflow-hidden relative">
    <img :src="src" :alt="alt" w="100%" h="100%" class="object-cover" />
  </div>
</template>

<style scoped>
.card-shadow:hover {
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
}
</style>
