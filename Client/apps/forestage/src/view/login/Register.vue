<template>
  <a-form :model="formState" name="basic" :span-col="{ span: 8 }" :wrapper-col="{ span: 25 }" autocomplete="off"
    class="relative">
    <a-form-item name="username" :rules="[{ required: false, message: '请输入你的用户名' }]">
      <div class="input-wrapper">
        <a-input id="formUsername" v-model:value="formState.userName"
          class="text-lg font-normal bg-gray-100 border-transparent" placeholder=" " />
        <label for="formUsername">请输入你的用户名</label>
      </div>
    </a-form-item>

    <a-form-item name="username" :rules="[{ required: false, message: '请输入你的邮箱' }]">
      <div class="input-wrapper">
        <a-input id="formUsernum" v-model:value="formState.email"
          class="text-lg font-normal bg-gray-100 border-transparent" placeholder=" " />
        <label for="formUsername">请输入你的邮箱</label>
      </div>
    </a-form-item>

    <a-form-item name="password" :rules="[{ required: false, message: '密码不能为空' }]">
      <div class="input-wrapper">
        <a-input id="formUserpws" v-model:value="formState.password"
          class="text-lg font-normal bg-gray-100 border-transparent" placeholder=" " type="password" />
        <label for="formUserpws">密码</label>
        <span id="eye" class="svg-eye i-ph-eye-closed-bold?mask text-gray-300 hover:text-gray-500"
          @click="ChangeType('formUserpws', 'eye')"></span>
      </div>
    </a-form-item>

    <a-form-item name="password" :rules="[{ required: false, message: '密码不能为空',tr }]">
      <div class="input-wrapper">
        <a-input id="formUsercpws" v-model:value="confirmpsw" class="text-lg font-normal bg-gray-100 border-transparent"
          placeholder=" " type="password" />
        <label for="formUsercpws">确认密码</label>
        <span id="ceye" class="svg-eye i-ph-eye-closed-bold?mask text-gray-300 hover:text-gray-500"
          @click="ChangeType('formUsercpws', 'ceye')"></span>
      </div>
    </a-form-item>
    <span class="errormsg text-red-600" v-show="Verify">两次输入的密码不一致 ！</span>

    <a-form-item class="mt-2">
      <button w="full" h="15"
        class="r-btn font-normal text-lg text-light-50 align-top rounded-md bg-orange-500 cursor-pointer"
        html-type="submit" @click="Verifypsw()">
        注 册
      </button>
    </a-form-item>
  </a-form>
</template>

<script setup>
import { ref, reactive, watch } from "vue";
import { api } from "@/api";
import { message } from 'ant-design-vue';
import { useNotification } from "naive-ui";

const notification = useNotification();

const formState = reactive({
  userName: "",
  nickName: "",
  password: "",
  // mobile: "325644848",
  email:""
});

const Verify = ref(false)
const confirmpsw = ref(null)

// const onFinish = (values) => {
//   console.log("Success:", values);
// };

// const onFinishFailed = (errorInfo) => {
//   console.log("Failed:", errorInfo);
// };

const checked = ref(formState.remember);

// watch(checked, (value) => {
//   formState.remember = value;
// });

function ChangeType(id1, id2) {
  let btn = document.getElementById(id1)
  let icon = document.getElementById(id2)
  console.log(btn + '' + icon)
  if (btn.type == "password") {
    btn.type = "text";
    icon.className = "svg-eye i-ph-eye?mask text-gray-300 hover:text-gray-500"
  } else {
    btn.type = "password";
    icon.className = "svg-eye i-ph-eye-closed-bold?mask text-gray-300 hover:text-gray-500"
  }
}

const register = async () => {
  console.log(formState)
  const [e, r] = await api.postRegister(formState)
  if(r.code === 200) window.location.reload();
  else{
    notify("error", "通知", "邮箱或用户名已被注册");
  }
}

function notify(type = "info", content, meta, options = {}) {
  notification[type]({
    content,
    meta,
    duration: 1500,
    keepAliveOnHover: true,
    ...options,
  });
}

function Verifypsw() {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if(!emailRegex.test(formState.email)) message.error('邮箱格式错误，请重新输入！')
  else if (formState.password == confirmpsw.value && formState.userName && formState.email) {
    Verify.value = false;
    formState.nickName = formState.userName
    register()
  }
  else if(formState.password == null || formState.userName == null ) message.error('用户名或密码为空，请重新输入！')
  else if(formState.password != confirmpsw.value) Verify.value = true;
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

  &:hover,
  &:focus {
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

.input-wrapper input:focus+label,
.input-wrapper input:not(:placeholder-shown)+label {
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

.errormsg {
  position: absolute;
  left: 60%;
  top: 75%;
}

.ant-form-item-has-error :not(.ant-input-disabled):not(.ant-input-borderless).ant-input {
  border-color: #ff5c00;
  box-shadow: 0 0 0 2px rgba(255, 92, 0, .2);

  &+label {
    color: #ff4d4f;
  }
}

.ant-form-item-has-error~.ant-form-item-has-error {
  margin-top: 10px;
}
</style>
