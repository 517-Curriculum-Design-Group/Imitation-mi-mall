<template >
  <div class="top w-full h-6.5rem">
    <img class="h-10 mr-2 ml-9rem mr-3rem" src="../../assets/xiaomilogo.svg" />
    <h1 class="mb-0 text-gray-700">确认订单</h1>
    <p class="text-xs text-gray-500 pl-15px pt-20px">
      温馨提示：产品是否购买成功，以最终下单为准哦
    </p>
    <span class="ml-auto mr-10.5rem text-gray-500 text-xs">
      <router-link to="/user/main"
        ><span>{{ usermsg.nickName }}</span></router-link
      >
      <span
        class="seq border-1 border-solid border-gray-400 ml-1.5 mr-1.5"
      ></span>
      <router-link to="/user/order"><span>订单中心</span></router-link>
    </span>
  </div>

  <div class="bg-neutral-100 h-[100%] w-[100%]">
    <div class="ml-20 mr-20 mb-3">
      <div class="bg-neutral-100 h-full w-full min-h-15.5"></div>

      <n-card size="medium">
        <!-- 收货地址部分 -->
        <div>
          <div class="text-3xl font-medium mt-10">收货地址</div>

          <n-grid :x-gap="10" :y-gap="5" :cols="3">
            <n-grid-item v-for="(item, index) in allAddresses" :key="index">
              <div
                class="border-1 border-solid border-gray-200 mt-10 w-100 h-70 lowerCard"
                :class="cursorCard === index ? 'red' : ''"
                @click="getbb(index)"
              >
                <n-card
                  :bordered="false"
                  class="flex w-full h-full flex-col justify-start items-center"
                >
                  <div class="text-3xl">{{ item.recipientName }}</div>
                  <div class="mt-5 text-gray-500">
                    {{ item.recipientPhone }}
                  </div>
                  <div class="text-gray-500">
                    {{ item.province }} {{ item.city }} {{ item.district }}
                    {{ item.zhen }}
                  </div>
                  <div class="text-gray-500">{{ item.detail }}</div>

                  <div class="flex justify-end mt-5 z-100">
                    <n-button
                      text
                      type="primary"
                      size="medium"
                      @click="getdetail(index)"
                      >修改</n-button
                    >
                  </div>
                </n-card>
              </div>
            </n-grid-item>

            <n-grid-item>
              <n-card
                class="flex mt-10 w-100 h-70 flex-col justify-start items-center"
                @click="addnew"
              >
                <div class="flex flex-col justify-center items-center mt-15">
                  <span
                    class="icon i-mdi-plus-circle text-gray-200 text-5xl mb-2 hover:text-gray-400"
                  ></span>
                  <h4 class="text-gray-400">添加新地址</h4>
                </div>
              </n-card>
            </n-grid-item>
          </n-grid>
        </div>

        <div>
          <div class="text-3xl font-medium mt-10">商品</div>
          <div v-for="(item, index) in goodslist" :key="index" class="w-full">
            <div class="flex justify-between">
              <div :row-span="2" class="flex items-center mt-10">
                <img
                  class="h-40px aspect-square object-cover"
                  :src="item.sku_image"
                  :alt="item.product_name"
                />
                <div class="ml-10">{{ item.product_name }}</div>
                <div
                  v-for="(sku, skuidex) in item.attributeValueList"
                  :key="skuidex"
                  class="ml-3"
                >
                  {{ sku.value }}
                </div>
              </div>

              <div class="mr-30 text-center items-center mt-15 w-45">
                {{ item.sku_price }}元 X {{ item.sku_quantity }}
              </div>
            </div>
          </div>
        </div>

        <n-divider />

        <div class="flex items-center mt-10">
          <div class="text-3xl font-medium min-w-60">配送方式</div>

          <div class="text-xl font-medium ml-2 text-orange-500">包邮</div>
        </div>

        <n-divider />

        <div class="flex items-center mt-10">
          <div class="text-3xl font-medium min-w-60">发票</div>

          <div class="text-xl font-medium ml-2 text-orange-500">
            电子普通发票
          </div>
        </div>

        <div class="flex justify-end items-center font-normal">
          <div class="items-center text-right mr-5">
            <div>商品件数:</div>
            <div class="mt-2">商品总价:</div>
            <div class="mt-2">运费:</div>
            <div class="mt-10">应付总额:</div>
          </div>

          <div class="items-center text-right text-orange-500">
            <div class="mt-5">{{ count }}件</div>
            <div class="mt-2">{{ sum }}元</div>
            <div class="mt-2">0元</div>
            <div class="mt-10 text-5xl font-semibold">
              {{ sum }}<span class="font-normal text-2xl">元</span>
            </div>
          </div>
        </div>
        <n-divider />

        <div class="flex justify-end">
          <div>
            <n-button
              :focusable="false"
              class="mr-13 w-158px h-38px"
              @click="goshopping"
              >返回购物车</n-button
            >
            <!-- <button class=" bg-light-50 border-current  mr-13 w-158px h-38px">
         返回购物车
        </button> -->
          </div>
          <div class="">
            <n-button
              type="primary"
              class="w-158px h-38px"
              backgroundcolor="#f25807"
              @click="orderput"
            >
              去结算
            </n-button>
          </div>
        </div>
      </n-card>
    </div>
  </div>

  <n-modal
    v-model:show="userAdress.isShow"
    :trap-focus="false"
    :mask-closable="false"
  >
    <n-form
      :model="userAdress.address"
      class="flex userForm flex-col w-[660px] h-auto shadow-lg bg-light-50 p-4 t-[100px]"
    >
      <div
        class="flex justify-between w-[635px] h-[60px] pt-[14px] pb-[14px] pl-[20px] pr-[20px] bg-gray-100"
      >
        <h2>修改收货地址</h2>
      </div>
      <n-space tabindex="1">
        <n-form-item label="收货人">
          <n-input
            v-model:value="userAdress.address.recipientName"
            type="text"
            placeholder="姓名"
            clearable
            style="width: 303px; height: 40px"
          />
        </n-form-item>

        <n-form-item label="收货人手机号" type="text" placeholder="手机号">
          <n-input
            v-model:value="userAdress.address.recipientPhone"
            clearable
            style="width: 303px; height: 40px"
          />
        </n-form-item>

        <n-form-item label="省">
          <n-input
            v-model:value="userAdress.address.province"
            type="text"
            clearable
            style="width: 303px; height: 40px"
          />
        </n-form-item>

        <n-form-item label="市">
          <n-input
            v-model:value="userAdress.address.city"
            type="text"
            clearable
            style="width: 303px; height: 40px"
          />
        </n-form-item>

        <n-form-item label="区">
          <n-input
            v-model:value="userAdress.address.district"
            type="text"
            clearable
            style="width: 303px; height: 40px"
          />
        </n-form-item>

        <n-form-item label="镇">
          <n-input
            v-model:value="userAdress.address.zhen"
            type="text"
            clearable
            style="width: 303px; height: 40px"
          />
        </n-form-item>

        <n-form-item path="address" label="收货地址" placeholder="手机号">
          <n-input
            v-model:value="userAdress.address.detail"
            clearable
            placeholder="收货地址"
            style="width: 606px; height: 40px"
          />
        </n-form-item>
      </n-space>

      <div class="flex justify-center gap-8 w-[660px] h-[81px] mt-4">
        <n-button
          class="text-light-50 bg-orange-500 w-[160px] h-[40px]"
          attr-type="button"
          style="
            --n-color-hover: var(--button-background-color);
            --n-border-hover: var(--n-border-hover);
            --n-text-color-hover: white;
            --n-boreder-focus: var(--button-background-color);
            --n-ripple-color: var(--button-background-color);
            --n-color-focus: gray;
            --n-text-color-focus: white;
          "
          @click="update"
        >
          确定
        </n-button>
        <n-button
          class="text-light-50 bg-gray-400 w-[160px] h-[40px]"
          attr-type="button"
          style="
            --n-color-hover: gray;
            --n-border-hover: gray;
            --n-text-color-hover: white;
            --n-boreder-focus: var(--button-background-color);
            --n-ripple-color: var(--button-background-color);
            --n-color-focus: gray;
            --n-text-color-focus: white;
          "
          @click="cancel()"
        >
          取消
        </n-button>
      </div>
    </n-form>
  </n-modal>

  <!-- 新增 -->
  <n-modal
    v-model:show="userAdress.flag"
    :trap-focus="false"
    :mask-closable="false"
  >
    <n-form
      :model="userAdress.address"
      class="flex userForm flex-col w-[660px] h-auto shadow-lg bg-light-50 p-4 t-[100px]"
    >
      <div
        class="flex justify-between w-[635px] h-[60px] pt-[14px] pb-[14px] pl-[20px] pr-[20px] bg-gray-100"
      >
        <h2>新增收货地址</h2>
      </div>
      <n-space tabindex="1">
        <n-form-item label="收货人"
        :rule="{
                    required: false,
                    message: '请输入姓名',
                    trigger: ['blur']
                }">
          <n-input
            v-model:value="userAdress.address.recipientName"
            type="text"
            placeholder="姓名"
            clearable
            style="width: 303px; height: 40px"
          />
        </n-form-item>

        <n-form-item label="收货人手机号" type="text" :rule="{
                    required: false,
                    message: '请输入手机号',
                    trigger: ['blur']
                }">
          <n-input
            v-model:value="userAdress.address.recipientPhone"
            clearable
            placeholder="手机号"
            style="width: 303px; height: 40px"
          />
        </n-form-item>

        <n-form-item label="省"
        :rule="{
                    required: false,
                    message: '请输入省份',
                    trigger: ['blur']
                }">
          <n-input
            v-model:value="userAdress.address.province"
            placeholder="省份"
            type="text"
            clearable
            style="width: 303px; height: 40px"
          />
        </n-form-item>

        <n-form-item label="市"
        :rule="{
                    required: false,
                    message: '请输入城市',
                    trigger: ['blur']
                }">
          <n-input
            v-model:value="userAdress.address.city"
            placeholder="城市"
            type="text"
            clearable
            style="width: 303px; height: 40px"
          />
        </n-form-item>

        <n-form-item label="区"
        :rule="{
                    required: false,
                    message: '请输入区/县',
                    trigger: ['blur']
                }">
          <n-input
            v-model:value="userAdress.address.district"
            placeholder="区/县"
            type="text"
            clearable
            style="width: 303px; height: 40px"
          />
        </n-form-item>

        <n-form-item label="镇"
        :rule="{
                    required: false,
                    message: '请输入乡/镇',
                    trigger: ['blur']
                }">
          <n-input
            v-model:value="userAdress.address.zhen"
            type="text"
            placeholder="镇/乡"
            clearable
            style="width: 303px; height: 40px"
          />
        </n-form-item>

        <n-form-item path="address" label="收货地址" placeholder="手机号"
        :rule="{
                    required: false,
                    message: '请输入详细地址',
                    trigger: ['blur']
                }"
        >
          <n-input
            v-model:value="userAdress.address.detail"
            clearable
            placeholder="收货地址"
            style="width: 606px; height: 40px"
          />
        </n-form-item>
      </n-space>

      <div class="flex justify-center gap-8 w-[660px] h-[81px] mt-4">
        <n-button
          class="text-light-50 bg-orange-500 w-[160px] h-[40px]"
          attr-type="button"
          style="
            --n-color-hover: var(--button-background-color);
            --n-border-hover: var(--n-border-hover);
            --n-text-color-hover: white;
            --n-boreder-focus: var(--button-background-color);
            --n-ripple-color: var(--button-background-color);
            --n-color-focus: gray;
            --n-text-color-focus: white;
          "
          @click="add"
        >
          确定
        </n-button>
        <n-button
          class="text-light-50 bg-gray-400 w-[160px] h-[40px]"
          attr-type="button"
          style="
            --n-color-hover: gray;
            --n-border-hover: gray;
            --n-text-color-hover: white;
            --n-boreder-focus: var(--button-background-color);
            --n-ripple-color: var(--button-background-color);
            --n-color-focus: gray;
            --n-text-color-focus: white;
          "
          @click="cancel()"
        >
          取消
        </n-button>
      </div>
    </n-form>
  </n-modal>
</template>

<script setup>
import { useNotification } from "naive-ui";
import utils from "@/utils";
import { ref, reactive, onMounted, computed } from "vue";
import { api } from "@/api";
import { cartStore } from "@/stores/cart";
import { userStore } from "@/stores/user";
import { useRouter } from "vue-router";

const cursorCard = ref();
const notification = useNotification();
const cartstore = cartStore();
const userstore = userStore();
const router = useRouter();

const goodslist = cartstore.getGoods();
const usermsg = userstore.getUserInfo();

const props = defineProps(["data"]);
console.log(props.data);
const data = utils.isLogin() || "123";
const isShow = ref(false);
const flag = ref(false);
const allAddresses = ref(null);

const userAdress = reactive({
  flag: false,
  isShow: false,
  address: [],
  addressId: "",
});

const update =  async() => {
  // console.log(userAdress.address);
  const obj = { ...userAdress.address };
  console.log(obj, typeof obj);
  obj.userId = usermsg.userId
  const [e, r] = await api.updateAddress(obj)
  .then((r)=>{
    if(r[1].code == 200){

    notification["success"]({
      content: "添加成功",
      meta: r[1].data,
      duration: 2500,
      keepAliveOnHover: true,
    });      
    }
    else{
      notification["error"]({
      content: "操作有误",
      meta:"请输入完整信息",
      duration: 2500,
      keepAliveOnHover: true,
    });  
    }
    init();
    userAdress.isShow = false
  })
};

const add = async () => {
  console.log(userAdress.address);
  const obj = { ...userAdress.address };
  console.log(obj, typeof obj);
  obj.userId = usermsg.userId
  const [e, r] = await api.addAddress(obj)
  .then((r)=>{
    if(r[1].code == 200){

    notification["success"]({
      content: "添加成功",
      meta: r[1].data,
      duration: 2500,
      keepAliveOnHover: true,
    });      
    }
    else{
      notification["error"]({
      content: "操作有误",
      meta:"请输入完整信息",
      duration: 2500,
      keepAliveOnHover: true,
    });  
    }
    init();
    userAdress.flag = false
  })
};

const orderput = async () => {
  if (userAdress.address.length == 0) {
    notification["error"]({
      content: "提交失败",
      meta: "请先选择地址",
      duration: 2500,
      keepAliveOnHover: true,
    });
  } else {
    console.log("goods", goodslist);
    let commits = [];
    goodslist.map((item) => {
      let o = {
        skuId: item.sku_id,
        productName: item.product_name,
        commitCount: item.sku_quantity,
        cartId : item.cart_id
      };
      commits.push(o);
    });
    console.log(commits);
    let obj = {
      addressId: userAdress.addressId,
      commits,
    };
    console.log(obj);
    const [e, r] = await api.generateOrder(obj).then((r) => {
      console.log(r[1].code);
      if (r[1].code != 200) {
        notification["warning"]({
          content: "您选中的商品已下架",
          duration: 2500,
          keepAliveOnHover: true,
        });
      } else {
        notification["success"]({
          content: "购买成功",
          duration: 2500,
          keepAliveOnHover: true,
        });
        router.push("/home")
      }

      console.log(r);
    });
  }
};

const userInfo = reactive({
  address: [],
});

function goshopping() {
  router.push("/cart");
}

function addnew() {
  userAdress.address = [];
  userAdress.flag = true;
}

function cancel() {
  userAdress.flag = false;
  userAdress.isShow = false;
}

const getdetail = (i) => {
  userAdress.address = allAddresses.value[i];

  userAdress.isShow = true;
  console.log("1212321", allAddresses.value[i]);
};

function getbb(index) {
  cursorCard.value = index;
  userAdress.address = allAddresses.value[index];
  userAdress.addressId = userAdress.address.addressId;
  console.log(userAdress.address);
  console.log("我触发了外边框", index);
}

const count = ref(0);
const countf = () => {
  for (let i = 0; i < goodslist.length; i++) {
    count.value = count.value + goodslist[i].sku_quantity;
  }
};

const sum = computed(() => {
  const totalprice = goodslist.reduce((pre, now) => {
    if (now.checked == true) return pre + now.sku_quantity * now.sku_price;
    else return pre;
  }, 0);
  return totalprice;
});

const value = ref(null);
const options = [
  {
    value: "广西",
    label: "广西",
    children: [
      {
        value: "桂林市",
        label: "桂林市",
      },
      {
        value: "南宁市",
        label: "南宁市",
      },
    ],
  },
];

const init = async () => {
  console.log(api.getAllAddresses);
  let params = {
    userId: usermsg.userId,
  };
  const [e, r] = await api.getAllAddresses(params);
  if (!e && r) {
    console.log(r);
  }
  allAddresses.value = r.data;
};

onMounted(() => {
  countf();
  init();
});
</script>

<style scoped lang="scss">
.top {
  display: flex;
  align-items: center;
  border-bottom: 2px solid var(--button-background-color);
}

.red {
  border-color: #f25807;
}
</style>