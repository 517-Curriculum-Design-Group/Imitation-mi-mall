<template >
    <div class="bg-neutral-100 min-w-screen-lg min-h-screen-lg">
        <div class="min-h-1"></div>
        <div class="flex flex-col justify-center items-center">
            <div class ="mt-30">小米秒杀</div>
            <div class="flex">
              <div
                v-for="(item, index) in songs" :key="index"
                class=" flex border-1 border-solid border-gray-200 bg-neutral-700 mt-10 w-130 h-20 lowerCard"
                :class="cursorCard === index ? 'red' : ''"
                @click="getbb(index)"
              >
                <nav-card class="w-full h-full flex items-center justify-center  ">
                   <div class="text-3xl text-light-100">{{ item.label }}</div> 
                </nav-card>
              </div>
              </div>

              <div class="w-390 mt-10"> 
                <n-grid :x-gap="5" :y-gap="5" :cols="3">
            <n-grid-item class="bg-green-100 h-50" >
                555
            </n-grid-item>

            <n-grid-item class="bg-green-100 ">
                555
            </n-grid-item>
            <n-grid-item class="bg-green-100 ">
                555
            </n-grid-item>
        </n-grid>
              </div>
        </div>
       
        
        
    </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from "vue";
const cursorCard = ref();

const songs=reactive([
        {
          value: "past",
          label:"已结束"
        },
        {
          value: "now",
          label: "抢购中"
        },
        {
          value: "future",
          label: "未开始"
        }
])

function getbb(index) {
  cursorCard.value = index;
  console.log("我触发了外边框", index);
}

</script>
<style scoped lang="scss">

.red {
  border-color: #f25807;
  background:#f1393a;
}
</style>