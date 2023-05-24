<template >
  <div class="bg-neutral-100 min-w-screen-lg min-h-screen-lg">
    <div class="min-h-1"></div>
    <div class="flex flex-col justify-center items-center">
      <div class="mt-20 text-7xl mb-20 flex font-bold">小米<div class="text-red-500 font-12000">秒</div>杀</div>
      <div class="w-100% px-8%">
        <n-tabs type="segment" animated v-model:value="cursorCard">
          <n-tab-pane name="已结束" tab="已结束">
            <div class="flex flex-wrap gap-15px">
              <div
                class="flex w-400px h-200px bg-gray-200 mt-5"
                v-for="item in product.group1"
                :key="item.seckillId"
              >
                <aside class="aspect-square h-[100%]">
                  <img
                    class="object-cover w-100%"
                    :src="item.productPic"
                    :alt="item.productName"
                  />
                </aside>
                <main class="flex flex-col">
                  <h2>{{ item.productName }}</h2>
                  <p>{{ trans(item.skuName) }}</p>
                  <span>价格：{{ item.seckillPrice }}</span>
                  <div class="flex flex flex-auto items-end justify-center mb-4">
                    <n-button type="error" round disabled> 秒杀已结束 </n-button>
                  </div>
                </main>
              </div>
            </div>
          </n-tab-pane>
          <n-tab-pane name="秒杀中" tab="秒杀中">
            <div class="flex flex-wrap gap-15px">
              <div
                class="flex w-400px h-200px bg-gray-200 mt-5"
                v-for="item in product.group2"
                :key="item.seckillId"
              >
                <aside class="aspect-square h-[100%] bg-light-50">
                  <img
                    class="object-cover w-100%"
                    :src="item.productPic"
                    :alt="item.productName"
                  />
                </aside>
                <main class="flex flex-col ml-5">
                  <h2 >{{ item.productName }}</h2>
                  <p>{{ trans(item.skuName) }}</p>
                  <span>价格：{{ item.seckillPrice }}</span>

                  <div v-if="item.stockCount ==0 " class="flex flex flex-auto items-end justify-center mb-4">
                    <n-button  type="error" round disabled>已售罄</n-button>
                    <span>
                        秒杀已结束
                    </span>
                  </div>

                  <div v-else class="flex flex flex-auto items-end justify-center mb-4">
                   
                    <n-button  type="error" round @click="rushsuccess(item.productId)">立即抢购</n-button>
                    <span>
                      <n-countdown
                        :duration="caltime(item.endTime)"
                        :active="active"
                        :precision="3"
                      />
                    </span>
                  </div>
                </main>
              </div>
            </div>
          </n-tab-pane>
          <n-tab-pane name="未开启" tab="未开启">
            <div class="flex flex-wrap gap-15px">
              <div
                class="flex w-400px h-200px bg-gray-200"
                v-for="item in product.group3"
                :key="item.seckillId"
              >
                <aside class="aspect-square h-[100%] bg-light-50">
                  <img
                    class="object-cover w-100%"
                    :src="item.productPic"
                    :alt="item.productName"
                  />
                </aside>
                <main class="flex flex-col flex-auto ml-5">
                    <h2>{{ item.productName }}</h2>
                    <p>{{ trans(item.skuName) }}</p>
                    <span>价格：{{ item.seckillPrice }}</span>

                  <div class="flex flex flex-auto items-end justify-center mb-4">
                    <n-button type="error" round disabled>等待开始</n-button>
                    <span>
                      <n-countdown
                        class=" text-red-100"
                        :duration="caltime(item.startTime)"
                        :active="active"
                      />
                    </span>
                  </div>
                </main>
              </div>
            </div>
          </n-tab-pane>
        </n-tabs>
      </div>

      <!-- <div class="w-390 mt-10">
        <n-grid :x-gap="5" :y-gap="5" :cols="3">
          <n-grid-item
            class="bg-green-100 h-50"
            v-for="(item, index) in form(cursorCard)"
            :key="index"
          >
            <div>
              <div>
                {{ item.productPic }}
                <n-image :src="item.productPic" width=""> </n-image>
              </div>
            </div>
          </n-grid-item>
        </n-grid>
      </div> -->
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from "vue";
import { api } from "@/api";
import utils from "@/utils";
import { useNotification } from "naive-ui";


const notification = useNotification();

const cursorCard = ref("秒杀中");
const product = reactive({
  group1: [],
  group2: [],
  group3: [],
});

const getData = async () => {
  const [e1, r1] = await api.getFollSecList3();
  if (r1 && !e1) {
    product.group1 = r1.data;
  }
  const [e2, r2] = await api.getFollSecList2();
  if (r2 && !e2) {
    product.group2 = r2.data;
  }
  const [e3, r3] = await api.getFollSecList1();
  if (r3 && !e3) {
    product.group3 = r3.data;
  }
};

function caltime(time) {
  time = Date.parse(new Date(time).toString());
  const time2 = Date.parse(new Date());
  console.log(time, time2);
  return time - time2;
}

const trans = (str) => {
  str = str.replaceAll("{", "");
  str = str.replaceAll("}", " ");
  str = str.replaceAll(",", " ,");
  str = str.replaceAll('"', "");
  return str;
};


const rushsuccess=async(productId)=>{
  const [e, r] = await api.seckill({productId});
  if (r && !e) {
    console.log(r )
    notification["error"]({
      content:r.data,
      duration: 2500,
      keepAliveOnHover: true,
    });
  }
}

onMounted(() => {
  getData();
});
</script>
<style scoped lang="scss">
.red {
  border-color: #f25807;
  background: #f1393a;
}
</style>