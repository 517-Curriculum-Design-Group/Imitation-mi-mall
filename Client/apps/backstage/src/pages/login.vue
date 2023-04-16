<template>
    <el-row class="min-h-screen bg-sky-500">
        <!---左半边--->
        <el-col :span="16" class="flex items-center justify-center flex-col" > 
            <div class="font-bold text-4xl text-light-50">仿小米商场后台</div>
        </el-col>

        <!---右半边--->
        <el-col :span="8" class="bg-light-50 flex items-center justify-center flex-col">
            <h2>登  录</h2>
            <div>
                <span></span>
                <span>账号密码登录</span>
                <span></span>
            </div>
            <el-form ref="formRef" :rules="rules" :model="form" class="w-[250px]">
                <el-form-item prop="username">
                    <el-input v-model="form.username" placeholder="请输入用户名">
                        <template #prefix>
                            <el-icon><user /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" v-model="form.password" placeholder="请输入密码" show-password>
                        <template #prefix>
                            <el-icon><lock /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <el-button round color="#0ea5e9" class="w-[250px]" type="primary" >登 录</el-button>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<script setup>
import{reactive} from 'vue'

const form = reactive({
    username:"",
    password:""
})

</script>