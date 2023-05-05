<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="6" :offset="0">   

                 <el-card shadow= "hover" class="border-0 " >
                    <template #header>
                    <div class="flex justify-betweens">
                        <span class="text-ms">用户总数</span> 
                        <el-tag class="ml-4" type="danger">总</el-tag>
                    </div>
                    </template>
                    <!-- card body -->
                    <span class="text-3xl font-bond text-gray-500">
                        {{ datas.userSum }}
                    </span>
                </el-card>
                
                </el-col>
           
                
            <el-col :span="6" :offset="0">         
                <el-card shadow= "hover" class="border-0">
                    <template #header>
                    <div class="flex justify-betweens">
                        <span>新增用户</span>
                        <el-tag class="ml-4" type="success">周</el-tag>
                    </div>
                    </template>
                    <!-- card body -->
                    <span class="text-3xl font-bond text-gray-500">
                        {{ datas.newUserCount }}
                    </span>
                </el-card>
                </el-col>

            <el-col :span="6" :offset="0">
                <el-card shadow= "hover" class="border-0">
                    <template #header>
                    <div class="flex justify-betweens">
                        <span>订单总量</span>
                        <el-tag class="ml-4" type="danger">总</el-tag>
                    </div>
                    </template>
                    <!-- card body -->
                    <span class="text-3xl font-bond text-gray-500">
                        {{ datas.orderSum }}
                    </span>
                </el-card>
            </el-col>

            <el-col :span="6" :offset="0">
                <el-card shadow= "hover" class="border-0">
                    <template #header>
                    <div class="flex justify-betweens">
                        <span >新增订单</span>
                        <el-tag  class="ml-4" type="success">周</el-tag>
                    </div>
                    </template>
                    <!-- card body -->
                    <span class="text-3xl font-bond text-gray-500">
                        {{ datas.newOrderCount }}
                    </span>
                </el-card>
            </el-col>
        </el-row>
        
    </div>
</template>

<script setup>
import {home} from "~/api/manager.js"
import{ref} from 'vue'

const datas = ref([])
home()
.then(res=>{
    datas.value = res
    console.log(datas);
    // console.log(res);
})

</script>