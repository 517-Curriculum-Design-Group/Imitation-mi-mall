<template>
  <div class="input-wrapper">
    <!-- <span class="placehoder">国家/地区</span>    -->
    <a-select v-model:value="value" show-search placeholder="Select a person" :options="options" class="country">
    </a-select>
  </div>
</template>

<script setup>
import { defineComponent, ref } from 'vue';
//     const filterOption = (input, option) => {
//       return option.value.toLowerCase().indexOf(input.toLowerCase()) >= 0;
//     };
</script>

<style scoped>
/* .placeholder{
  position: absolute;
  top: 50%;
  left: 10px;
  transform: translateY(-50%);
  font-size: 14px;
  color: #b5b5b5;
  pointer-events: none;
  transition: all 0.2s;
} */
.input-wrapper {
  position: flex;
  height: 60px;
  padding-top: 20px;
}

.country {
  height: 60px;
  width: 354px;
}

.country :deep(.ant-select-selection-search-input) {
  height: 60px;
  width: 354px;
}
</style>