<script setup>
import { ref, onMounted, reactive } from "vue";
import { useNotification } from "naive-ui";
import { api } from "@/api";
import { userStore } from "@/stores/user.js";
import { message } from 'ant-design-vue';

const Userstore = userStore();

const notification = useNotification();
let user = reactive({
  userId: null,
  nickName: null,
  avatar: null,
  sex: null,
  email: null,
  mobile: null,
});

let copy = reactive({});
let userAvatar = ref(null);
onMounted(async () => {
  const [e, r] = await api.getPersonInfo();
  Object.assign(user, r.data);
  userAvatar.value = user.avatar;
  Object.assign(copy, user);
  console.log(copy);
});

const isEdit = ref(false);

const Verify = (()=>{
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if(!emailRegex.test(user.email)) message.error('邮箱格式错误，请重新输入！')
  else if(Object.values(user).includes(null)) message.error('信息存在空缺')
  else changeInfo()
})

async function changeInfo() {
  const [e, r] = await api.updatePersonInfo(user);
  console.log(r);
  if (r.code === 200) {
    Userstore.setUserInfo(user);
    notify("success");
    isEdit.value = false;
  } else notify("error");
}

function update() {
  Object.assign(user, copy);
  isEdit.value = false;
}

async function beforeUpload(data) {
  console.log(data);
  if (data.file.file?.type !== "image/png") {
    notify("error");
  } else {
    const [e, r] = await api.uploadAvatar(data.file);
    if (r.code === 200) {
      user.avatar = r.data;
      Userstore.setUserInfo({ avater: user.avatar });
      notify("success");
    }
  }
}

function notify(type) {
  if (type === "success") {
    notification["success"]({
      content: "修改成功",
      meta: "成功修改!",
      duration: 2500,
      keepAliveOnHover: true,
    });
  } else {
    notification["error"]({
      content: "修改失败",
      meta: "请仔细核对信息!",
      duration: 2500,
      keepAliveOnHover: true,
    });
  }
}
</script>

<template>
  <div>
    <h1 class="w-[882px] h-[68px] text-gray-500">个人信息</h1>

    <n-form
      v-if="isEdit"
      :mdoel="user"
      class="flex flex-col w-[600px] gap-4 justify-center items-center h-auto m-auto"
      label-align="right"
    >
      <n-form-item
        class="w-[500px] h-[50px] mt-8"
        path="nickName"
        label="昵称"
        label-placement="left"
        label-align="right"
        style="
          --n-color-hover: var(--button-background-color);
          --n-border-hover: var(--n-border-hover);
          --n-boreder-focus: var(--button-background-color);
          --n-ripple-color: var(--button-background-color);
          caret-color: var(--button-background-color);
        "
      >
        <n-input
          v-model:value="user.nickName"
          placeholder="请输入昵称"
        ></n-input>
      </n-form-item>

      <n-form-item
        class="w-[500px] h-[50px]"
        path="sex"
        label="性别"
        label-placement="left"
        label-align="right"
      >
        <n-radio-group v-model:value="user.sex">
          <n-space>
            <n-radio
              value="男"
              style="
                --n-box-shadow: inset 0 0 0 1px var(--button-background-color);
                --n-box-shadow-active: inset 0 0 0 1px
                  var(--button-background-color);
                --n-box-shadow-focus: inset 0 0 0 1px
                  var(--button-background-color);
                --n-box-shadow-hover: inset 0 0 0 1px
                  var(--button-background-color);
                --n-dot-color-active: var(--button-background-color);
              "
            >
              男
            </n-radio>
            <n-radio
              value="女"
              style="
                --n-box-shadow: inset 0 0 0 1px var(--button-background-color);
                --n-box-shadow-active: inset 0 0 0 1px
                  var(--button-background-color);
                --n-box-shadow-focus: inset 0 0 0 1px
                  var(--button-background-color);
                --n-box-shadow-hover: inset 0 0 0 1px
                  var(--button-background-color);
                --n-dot-color-active: var(--button-background-color);
              "
            >
              女
            </n-radio>
          </n-space>
        </n-radio-group>
      </n-form-item>

      <n-form-item
        class="w-[500px] h-[50px]"
        path="user.nickname"
        label="手机号"
        label-placement="left"
        label-align="right"
        style="
          --n-color-hover: var(--button-background-color);
          --n-border-hover: var(--n-border-hover);
          --n-boreder-focus: var(--button-background-color);
          --n-ripple-color: var(--button-background-color);
          caret-color: var(--button-background-color);
        "
      >
        <n-input
          v-model:value="user.mobile"
          placeholder="请输入手机号"
        ></n-input>
      </n-form-item>

      <n-form-item
        class="w-[500px] h-[50px]"
        path="user.nickname"
        label="邮箱"
        label-placement="left"
        label-align="right"
        style="
          --n-color-hover: var(--button-background-color);
          --n-border-hover: var(--n-border-hover);
          --n-boreder-focus: var(--button-background-color);
          --n-ripple-color: var(--button-background-color);
          caret-color: var(--button-background-color);
        "
      >
        <n-input v-model:value="user.email" placeholder="请输入邮箱"></n-input>
      </n-form-item>
      <div class="flex gap-8">
        <n-button
          class="text-light-50 bg-orange-500 w-[260px] h-[40px]"
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
          @click="Verify()"
        >
          保存
        </n-button>

        <n-button
          class="text-light-50 bg-gray-400 w-[260px] h-[40px]"
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
          @click="update()"
        >
          取消
        </n-button>
      </div>
    </n-form>

    <div
      v-if="!isEdit"
      class="flex flex-col justify-center items-center w-[600px] h-auto m-auto"
    >
      <n-upload
        class="flex justify-center"
        :show-file-list="false"
        @before-upload="beforeUpload"
      >
        <n-avatar
          round
          class="h-250px w-250px"
          size="small"
          :src="user.avatar ? user.avatar : 'empty.png'"
          fallback-src="https://www.gstatic.com/android/keyboard/emojikitchen/20201001/u1f924/u1f924_u1f42d.png"
      /></n-upload>
      <span class="w-[500px] h-[70px] text-lg">用户ID:{{ user.userId }}</span>
      <span class="w-[500px] h-[70px] text-lg">昵称:{{ user.nickName }}</span>
      <span class="w-[500px] h-[70px] text-lg"
        >性别:{{ user.sex === "女" ? "女" : "男" }}</span
      >
      <span class="w-[500px] h-[70px] text-lg">手机号:{{ user.mobile }}</span>
      <span class="w-[500px] h-[70px] text-lg">邮箱:{{ user.email }}</span>
      <n-button
        class="text-light-50 bg-orange-500 w-[360px] h-[60px]"
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
        @click="isEdit = true"
      >
        编辑
      </n-button>
    </div>
  </div>
</template>
