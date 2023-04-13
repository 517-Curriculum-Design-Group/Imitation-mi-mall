<script setup>
import { defineComponent, ref } from 'vue';

const value = ref('');
</script>


<template>
    <div class="container">
        <a-input-search v-model:value="value" allowclear placeholder="input search text" @search="onSearch" size="large"
            class="search w-75" />
    </div>
</template>

<style scoped lang="scss">
.container {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    width: 79.8%;
    height: 5.5rem;
    margin: auto;
}
</style>