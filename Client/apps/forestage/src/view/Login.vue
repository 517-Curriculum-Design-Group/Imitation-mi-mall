<template>
<a-form
  :model="formState"
  name="basic"
  class="basic"
  :label-col="{ span: 8 }"
  :wrapper-col="{ span: 25 }"
  autocomplete="off"
  @finish="onFinish"
  @finishFailed="onFinishFailed"
>
  <a-form-item
    name="username"
    class="username"
    :rules="[{ required: true , message: '邮箱/手机号/小米ID' }]"
    label=" "
    :label-col="{ span: 0 }"
    :wrapper-col="{ span: 24 }"
  >
  <div class="input-wrapper"><a-input v-model:value="formState.username" class="id"/>
  <span class="placeholder">邮箱/手机号/小米ID</span>
  </div>
  </a-form-item>

  <a-form-item
    name="password"
    class="password"
    :rules="[{ required: true, message: '密码不能为空'  }]"
    label=" "
    :label-col="{ span: 0 }"
    :wrapper-col="{ span: 24 }"
  >
  <div class="input-wrapper"><a-input-password v-model:value="formState.password" class="psw" type="password" @click="ChangeType()"/>
  <span class="placeholder">密码</span>
  </div>
  </a-form-item>
  <!-- <a-form-item name="remember" :wrapper-col="{ offset: 8, span: 16 }">
    <a-checkbox v-model:checked="formState.remember">Remember me</a-checkbox>
  </a-form-item> -->

  <a-form-item :wrapper-col="{ offset: 0, span: 18 }">
    <a-button type="primary" html-type="submit">登录</a-button>
  </a-form-item>
</a-form>
</template>

<script setup>
import { ref,reactive,watch } from 'vue';

const formState = reactive({
  username: '',
  password: '',
  // remember: true,
});

const onFinish = values => {
  console.log('Success:', values);
};

const onFinishFailed = errorInfo => {
  console.log('Failed:', errorInfo);
};

const checked = ref(formState.remember);
watch(checked, (value) => {
  formState.remember = value;
});
function ChangeType(){
  let input = document.getElementsByClassName("psw");
  if (input.type === "password") {
    input.type = "text";
  } else {
    input.type = "password";
  }
}
</script>

<style scoped>
.username{
  padding-top:20px;
}
.input-wrapper {
    position: relative;
    height: 60px;

}
.placeholder {
  position: absolute;
  top: 50%;
  left: 10px;
  transform: translateY(-50%);
  font-size: 14px;
  color: #b5b5b5;
  pointer-events: none;
  transition: all 0.2s;
}
.id:focus + .placeholder,
.psw:focus + .placeholder {
  top: 10px;
  font-size: 12px;
  color: #b0b0b0;
}
/* .a-form-item {
  position: relative;
} */
/* .a-form-item label {
    font-size: 16px;
    color: #333;
    transition: all 0.3s ease-out;
    transform-origin: left bottom;
  }
  .a-form-item .ant-input:focus + label,
  .a-form-item .ant-input-password:focus + label {
    font-size: 12px;
    color: #888;
    transform: translateY(-24px) scale(0.8);
  } */
.ant-input-password,.ant-input{
  height: 60px;
  font-size: 16px;
  border-radius: 8px;
}
.ant-btn{
  height: 60px;
  width: 356px;
  margin:20px 0px 10px;
  padding: 0px 20px;
  background-color: var(--hover--color);
  border-color: var(--hover--color);
  border-radius: 8px;
}
.ant-input:focus, .ant-input-focused{
  border-color: var(--hover--color);
  box-shadow:var(--hover--color);
}
.ant-input:hover{
  border-color: var(--hover--color);
  box-shadow:var(--hover--color);
}
.ant-input-affix-wrapper:hover{
  border-color: var(--hover--color);
  box-shadow:var(--hover--color);
}
</style>