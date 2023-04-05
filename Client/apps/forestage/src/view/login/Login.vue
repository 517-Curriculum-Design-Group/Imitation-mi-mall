<template>
  <a-form
    :model="formState"
    name="basic"
    :span-col="{ span: 8 }"
    :wrapper-col="{ span: 25 }"
    autocomplete="off"
    @finish="onFinish"
    @finishFailed="onFinishFailed"
  >
    <a-form-item name="username" :rules="[{ required:true, message: '请输入账号' }]">
      <div class="input-wrapper">
        <a-input
          id="formUsername"
          v-model:value="formState.username"
          class="text-lg font-normal bg-gray-100 border-transparent"
          placeholder=" "
        />
        <label for="formUsername">邮箱/手机号/小米ID</label>
      </div>
    </a-form-item>

    <a-form-item name="password" :rules="[{ required:true, message: '请登录输入密码' }]">
      <div class="input-wrapper">
        <a-input
          id="formUserpws"
          v-model:value="formState.password"
          class="text-lg font-normal bg-gray-100 border-transparent"
          placeholder=" "
          type="password"
        />
        <label for="formUserpws">密码</label>
        <span id="eye" class="svg-eye i-ph-eye-closed-bold?mask text-gray-300 hover:text-gray-500" @click="ChangeType()"></span>     
      </div>
    </a-form-item>
   
    <a-form-item class="mt-2">
      <button
        w="full"
        h="15"
        class="font-normal text-lg text-light-50 align-top rounded-md bg-orange-500 cursor-pointer"
        html-type="submit"
      >
        登 录
      </button>
    </a-form-item>
    <a-form-item>
      <a class="text-lg text-orange-500">忘记密码 ？</a>
      <a class="ml-47% text-lg text-orange-500">手机号登录</a>
    </a-form-item>
    <a-form-item>
      <div class="flex justify-center items-center">
        <span>其他方式登录</span>
      </div>
    </a-form-item>
  </a-form>
</template>

<script setup>
import { ref, reactive, watch } from "vue";

const formState = reactive({
  username: "",
  password: "",
});

const onFinish = (values) => {
  console.log("Success:", values);
};

const onFinishFailed = (errorInfo) => {
  console.log("Failed:", errorInfo);
};

const checked = ref(formState.remember);

watch(checked, (value) => {
  formState.remember = value;
});

function ChangeType() {
  let btn = document.getElementById("formUserpws");
  let icon = document.getElementById("eye")
  if (btn.type == "password") {
    btn.type = "text";
    icon.className = "svg-eye i-ph-eye?mask text-gray-300 hover:text-gray-500"
  } else {
    btn.type = "password";
    icon.className = "svg-eye i-ph-eye-closed-bold?mask text-gray-300 hover:text-gray-500"
  }
}

</script>

<style scoped lang="scss">
.username {
  padding-top: 20px;
}
.input-wrapper input {
  position: relative;
  height: 60px;
  padding-top: 20px;
  border-radius: 4px;
  &:hover,&:focus{
    border-color: var(--button-hover-background-color);
    box-shadow: var(--button-hover-background-color);
  }
}
.input-wrapper label,
.input-wrapper label:placeholder-shown {
  position: absolute;
  top: 25%;
  left: 12px;
  cursor: text;
  font-size: 18px;
  color: #b5b5b5;
  transition: all 200ms ease-in-out;
}
.input-wrapper input:focus + label,
.input-wrapper input:not(:placeholder-shown) + label {
  top: 0;
  left: 12px;
  font-size: 14px;
}

.login-button {
  background-color: var(--button-hover-background-color);
}

.svg-eye {
  height: 1.5rem;
  width: 1.5rem;
  position: absolute;
  top: 32%;
  right: 4%;
  cursor: pointer;
  transition: color 500ms;
}

.ant-form-item-has-error :not(.ant-input-disabled):not(.ant-input-borderless).ant-input {
    border-color: #ff5c00;
    box-shadow: 0 0 0 2px rgba(255,92,0,.2);  
  & + label {
    color: #ff4d4f;
  }
}
.ant-form-item-has-error ~ .ant-form-item-has-error {
  margin-top: 10px;
}
</style>
