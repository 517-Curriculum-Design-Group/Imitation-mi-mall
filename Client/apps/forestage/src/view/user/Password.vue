<script setup>
import { api } from "@/api"
import { ref, reactive } from "vue"
import { useNotification } from 'naive-ui'
import { useRouter } from 'vue-router';
import { message } from 'ant-design-vue';

const router = useRouter();
const notification = useNotification()
let psw = reactive({
    rawPassword: null,
    newPassword: null,
    checkNewPassword: null
})

async function changePsw() {
    if (Object.values(psw).includes(null)) {
        message.error('待填入信息为空，请重新输入')
    }
    else {
        const [e, r] = await api.updatePassword(psw)
        if (r.code === 912) notify('error')
        else if (r.code === 913) notify('info')
        else if (r.code === 914) notify('warning')
        else {
            notify('success')
            await api.postLogout()
            router.push('/login')
        }
    }
}

function notify(type) {
    if (type === 'success') {
        notification['success']({
            content: "修改成功",
            meta: "密码已更改，请重新登录",
            duration: 2500,
            keepAliveOnHover: true
        });
    }
    else if (type === 'error') {
        notification['error']({
            content: "修改失败",
            meta: "原密码错误",
            duration: 2500,
            keepAliveOnHover: true
        });
    }
    else if (type === 'warning') {
        notification['warning']({
            content: "修改失败",
            meta: "新密码与旧密码一致",
            duration: 2500,
            keepAliveOnHover: true
        });
    }
    else if (type === 'info') {
        notification['info']({
            content: "修改失败",
            meta: "两次新密码输入不一致",
            duration: 2500,
            keepAliveOnHover: true
        });
    }
}

function ChangeType(id1, id2) {
  let btn = document.getElementById(id1)
  let icon = document.getElementById(id2)
  if (btn.type == "password") {
    btn.type = "text";
    icon.className = "svg-eye i-ph-eye?mask text-gray-300 hover:text-gray-500"
  } else {
    btn.type = "password";
    icon.className = "svg-eye i-ph-eye-closed-bold?mask text-gray-300 hover:text-gray-500"
  }
}
</script>

<template>
    <div>
        <h1 class="w-[882px] h-[68px] text-gray-500">修改密码</h1>

        <n-form class="flex flex-col justify-center items-center w-[600px] h-auto m-auto">
            <n-form-item class="w-[500px] h-[50px]" path="oldpsw" label="旧密码" label-placement="left" label-align="right"
                style="--n-color-hover:var(--button-background-color); --n-border-hover:1px solid var(--button-background-color);
                --n-boreder-focus:1px solid var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-caret-color:var(--button-background-color)">
                <n-input type="password" v-model:value="psw.rawPassword" placeholder="请输入旧密码"></n-input>
            </n-form-item>

            <n-form-item class="w-[500px] h-[50px]" path="newpsw" label="新密码" label-placement="left" label-align="right"
                style="--n-color-hover:var(--button-background-color); --n-border-hover:1px solid var(--button-background-color);
                --n-boreder-focus:1px solid var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-caret-color:var(--button-background-color)">
                <n-input id="psw2" type="password" v-model:value="psw.newPassword" placeholder="请输入新密码"></n-input>
                <!-- <span id="eye2" class="svg-eye i-ph-eye-closed-bold?mask text-gray-300 hover:text-gray-500"
                    @click="ChangeType('psw2','eye2')"></span> -->
            </n-form-item>

            <n-form-item class="w-[500px] h-[50px]" path="verpsw" label="确认新密码" label-placement="left" label-align="right"
                style="--n-color-hover:var(--button-background-color); --n-border-hover:1px solid var(--button-background-color);
                --n-boreder-focus:1px solid var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-caret-color:var(--button-background-color)">
                <n-input type="password" v-model:value="psw.checkNewPassword" placeholder="确认密码"></n-input>
            </n-form-item>

            <n-button class="text-light-50 bg-orange-500 w-[360px] h-[60px]" attr-type="button"
                style="--n-color-hover:var(--button-background-color);--n-border-hover:var(--n-border-hover);--n-text-color-hover:white;
                --n-boreder-focus:var(--button-background-color);--n-ripple-color:var(--button-background-color);--n-color-focus:gray;--n-text-color-focus:white" @click="changePsw(newpsw)">
                确认修改
            </n-button>

        </n-form>
    </div>
</template>