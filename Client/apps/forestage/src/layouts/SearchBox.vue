<script setup>
import { defineComponent, ref } from 'vue';

const value = ref('');
</script>


<template>
    <div class="flex items-center justify-between w-[1226px] h-[100px] m-auto">
        <img class="w-[56px] h-[56px]" src="../assets/xiaomilogo.svg" />

        <div class="flex w-[296.6px] h-[49.6px] container">
            <n-input class="leading-[49.6px] search" v-model:value="value" style="--n-border-hover:1px solid var(--button-background-color)" type="text" placeholder="搜索" />
            <n-button class="w-[52px] h-[50px] search-btn" style="--n-border-hover:1px solid var(--button-background-color)">
                <span class="i-mdi-magnify w-[24px] h-[24px]"></span>
            </n-button>
        </div>

    </div>
</template>

<style scoped lang="scss">
:deep(.search-btn):hover {
    color: white;
    background-color: var(--button-background-color);
}
</style>