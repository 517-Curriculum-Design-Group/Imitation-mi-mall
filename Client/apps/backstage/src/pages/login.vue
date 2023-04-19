<template>
    <el-row class="min-h-screen bg-sky-500">
        <!---左半边--->
        <el-col :lg="16" md="12" class="flex items-center justify-center flex-col" > 
            <div class="font-bold text-4xl text-light-50">仿小米商场后台</div>
        </el-col>

        <!---右半边--->
        <el-col :lg="8" md="12" class="bg-light-50 flex items-center justify-center flex-col">
            <h2 class="font-bold text-3xl text-gray-800">登  录</h2>

            <div class="flex items-center justify-center my-5 text-gray-300 space-x-2">
                <span class = "h-[1px] w-16 bg-gray-200"></span>
                <span>账号密码登录</span>
                <span class = "h-[1px] w-16 bg-gray-200"></span>
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
import{ref,reactive} from 'vue'


const form = reactive({
    username:"",
    password:""
})

const rules ={
    username:[
        { 
            required: true, 
            message: '用户名不能为空', 
            trigger: 'blur' 
        },
    ],
    password:[
        { 
            required: true, 
            message: '用户名不能为空', 
            trigger: 'blur' 
        },
    ]
}

const formRef = ref(null)
const loading = ref(false)
const onSubmit = () => {
    formRef.value.validate((valid)=>{
        if(!valid){
            return false
        }
        loading.value = true
        
        store.dispatch("login",form).then(res=>{
            toast("登录成功")
            router.push("/")
        }).finally(()=>{
            loading.value = false
        })
    })
}

// 监听回车事件
function onKeyUp(e){
    if(e.key == "Enter") onSubmit()
}

// 添加键盘监听
onMounted(()=>{
    document.addEventListener("keyup",onKeyUp)
})
// 移除键盘监听
onBeforeUnmount(()=>{
    document.removeEventListener("keyup",onKeyUp)
})
</script>