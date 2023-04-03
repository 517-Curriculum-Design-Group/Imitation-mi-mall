<script setup>
import { defineComponent, ref } from 'vue';
const value = ref('');
</script>

<template>
    <div class="container">
    <a-input-search
      v-model:value="value"
      allowclear
      placeholder="input search text"
      @search="onSearch"
      class="search"
    />
    </div>
</template>

<style scoped>
.container{
    display:flex;
    justify-content:flex-end;
    align-items:center; 
    width: 1226px;
    height: 100px;
    margin: auto;
    padding-top: 10px;
    padding-bottom: 10px;
    
}
.search{
    display: flex;
    float: right;
    width: 245px;
    height: 50px;
    justify-content: center;
    align-items: center;
}
</style>