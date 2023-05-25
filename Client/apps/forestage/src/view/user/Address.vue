<script setup>
import { useNotification } from "naive-ui";
import { ref, reactive, onMounted } from "vue";
import { api } from "@/api";
import { cartStore } from "@/stores/cart";
import { userStore } from "@/stores/user";

const cursorCard = ref();
const notification = useNotification();
const cartstore = cartStore();
const userstore = userStore();

const goodslist = cartstore.getGoods();
const usermsg = userstore.getUserInfo();

const props = defineProps(["data"]);
console.log(props.data);
const allAddresses = ref(null);

const userAdress = reactive({
  flag: false,
  isShow: false,
  address: [],
  addressId: "",
});

const update = async () => {
  // console.log(userAdress.address);
  const obj = { ...userAdress.address };
  console.log(obj, typeof obj);
  obj.userId = usermsg.userId;
  const [e, r] = await api.updateAddress(obj).then((r) => {
    if (r[1].code == 200) {
      notification["success"]({
        content: "添加成功",
        meta: r[1].data,
        duration: 2500,
        keepAliveOnHover: true,
      });
    } else {
      notification["error"]({
        content: "操作有误",
        meta: "请输入完整信息",
        duration: 2500,
        keepAliveOnHover: true,
      });
    }
    init();
    userAdress.isShow = false;
  });
};
const setadress = async (index) => {
  const obj = { ...userAdress.address };
  const [e, r] = await api.setDefaultAddress(obj);
  if (r && !e) {
    notification["success"]({
      content: "设置成功",
      duration: 2500,
      keepAliveOnHover: true,
    });
  }

  init();
};

const add = async () => {
  console.log(userAdress.address);
  const obj = { ...userAdress.address };
  console.log(obj, typeof obj);
  obj.userId = usermsg.userId;
  const [e, r] = await api.addAddress(obj).then((r) => {
    if (r[1].code == 200) {
      notification["success"]({
        content: "添加成功",
        meta: r[1].data,
        duration: 2500,
        keepAliveOnHover: true,
      });
    } else {
      notification["error"]({
        content: "操作有误",
        meta: "请输入完整信息",
        duration: 2500,
        keepAliveOnHover: true,
      });
    }
    init();
    userAdress.flag = false;
  });
};

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
async function set2() {
  setadress();
  await init();
  init();
}

function getbb(index) {
  
  cursorCard.value = index;
  userAdress.address = allAddresses.value[index];
  userAdress.addressId = userAdress.address.addressId;
  console.log(userAdress.address);
  console.log("我触发了外边框", index);
  setadress(index)
}

const count = ref(0);
const countf = () => {
  for (let i = 0; i < goodslist.length; i++) {
    count.value = count.value + goodslist[i].sku_quantity;
  }
};

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

<template>
  <h1 class="w-[882px] h-[68px] text-gray-500">收货地址</h1>

  <div>
    <n-grid :x-gap="10" :y-gap="5" :cols="3">
      <n-grid-item>
        <n-card
          class="flex mt-10 w-80 h-70 flex-col justify-start items-center"
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
      <n-grid-item v-for="(item, index) in allAddresses" :key="index">
        <div
          class="border-1 border-solid border-gray-200 mt-10 w-80 h-70 lowerCard"
          :class="cursorCard === index ? 'red' : ''"
          @click="getbb(index)"
        >
          <n-card
            :bordered="false"
            class="flex w-full h-full flex-col justify-start items-center"
          >
            <div class="flex justify-between">
              <div class="text-3xl">{{ item.recipientName }}</div>
              <div v-if="(item.isDefault == 1)" class="text-orange-500">
                默认
              </div>
            </div>
            <div class="mt-5 text-gray-500">
              {{ item.recipientPhone }}
            </div>
            <div class="text-gray-500">
              {{ item.province }} {{ item.city }} {{ item.district }}
              {{ item.zhen }}
            </div>
            <div class="text-gray-500">{{ item.detail }}</div>

            <div class="flex justify-between mt-5 w-70 z-100">
              <n-button
                text
                type="primary"
                size="medium"
                >设为秒杀默认地址</n-button
              >

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
    </n-grid>
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
        <n-form-item
          label="收货人"
          :rule="{
            required: true,
            message: '请输入姓名',
            trigger: ['blur'],
          }"
        >
          <n-input
            v-model:value="userAdress.address.recipientName"
            type="text"
            placeholder="姓名"
            clearable
            style="width: 303px; height: 40px"
          />
        </n-form-item>

        <n-form-item
          label="收货人手机号"
          type="text"
          :rule="{
            required: true,
            message: '请输入手机号',
            trigger: ['blur'],
          }"
        >
          <n-input
            v-model:value="userAdress.address.recipientPhone"
            clearable
            placeholder="手机号"
            style="width: 303px; height: 40px"
          />
        </n-form-item>

        <n-form-item
          label="省"
          :rule="{
            required: true,
            message: '请输入省份',
            trigger: ['blur'],
          }"
        >
          <n-input
            v-model:value="userAdress.address.province"
            placeholder="省份"
            type="text"
            clearable
            style="width: 303px; height: 40px"
          />
        </n-form-item>

        <n-form-item
          label="市"
          :rule="{
            required: true,
            message: '请输入城市',
            trigger: ['blur'],
          }"
        >
          <n-input
            v-model:value="userAdress.address.city"
            placeholder="城市"
            type="text"
            clearable
            style="width: 303px; height: 40px"
          />
        </n-form-item>

        <n-form-item
          label="区"
          :rule="{
            required: true,
            message: '请输入区/县',
            trigger: ['blur'],
          }"
        >
          <n-input
            v-model:value="userAdress.address.district"
            placeholder="区/县"
            type="text"
            clearable
            style="width: 303px; height: 40px"
          />
        </n-form-item>

        <n-form-item
          label="镇"
          :rule="{
            required: true,
            message: '请输入乡/镇',
            trigger: ['blur'],
          }"
        >
          <n-input
            v-model:value="userAdress.address.zhen"
            type="text"
            placeholder="镇/乡"
            clearable
            style="width: 303px; height: 40px"
          />
        </n-form-item>

        <n-form-item
          path="address"
          label="收货地址"
          placeholder="手机号"
          :rule="{
            required: true,
            message: '请输入详细地址',
            trigger: ['blur'],
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


<style scoped lang="scss">
// .userForm{
//     visibility: hidden;
//     transition: all 0.2s ease-in-out;
// }

// .userForm-active{
//     visibility: visible;
//     transition: all 0.2s ease-in-out;
// }

.top {
  display: flex;
  align-items: center;
  border-bottom: 2px solid var(--button-background-color);
}

.red {
  border-color: #f25807;
}
</style>