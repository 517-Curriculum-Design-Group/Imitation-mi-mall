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
    <a-form-item name="username" :rules="[{ message: '邮箱/手机号/小米ID' }]">
      <div class="input-wrapper">
        <a-input
          id="formUsername"
          v-model:value="formState.username"
          class="text-lg font-normal"
          placeholder=" "
        />
        <label for="formUsername">邮箱/手机号/小米ID</label>
      </div>
    </a-form-item>

    <a-form-item name="password" :rules="[{ message: '密码不能为空' }]">
      <div class="input-wrapper">
        <a-input
          id="formUserpws"
          v-model:value="formState.password"
          class="text-lg font-normal"
          placeholder=" "
          type="password"
          @click="ChangeType()"
        />
        <label for="formUserpws">密码</label>
      </div>
    </a-form-item>

    <a-form-item class="mt-2">
      <button
        w="full"
        h="15"
        class="font-normal text-lg text-light-50 align-top rounded-md bg-orange-500"
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
  let input = document.getElementsByClassName("psw");
  if (input.type === "password") {
    input.type = "text";
  } else {
    input.type = "password";
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
</style>
