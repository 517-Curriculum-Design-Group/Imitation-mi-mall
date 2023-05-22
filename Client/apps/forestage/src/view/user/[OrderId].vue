<script setup>
import { ref,onMounted } from 'vue'
import { api } from "@/api";
import { useRouter } from 'vue-router';

const router = useRouter();
console.log(router.currentRoute.value.params)
</script>

<template>
    {{ 123 }}
</template>