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
                        <CountTo :value="datas.userSum" />
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
                        <CountTo :value="datas.newUserCount " />
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
                        <CountTo :value="datas.orderSum" />
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
                        <CountTo :value="datas.newOrderCount" /> 
                    </span>
                </el-card>
            </el-col>
        </el-row>
        
        <el-row :gutter="20" class="mt-5">
            <el-col :span="12" :offset="0">
                <indexChar/>
            </el-col>

            <el-col :span="12" :offset="0">
                <el-card shadow="never">
        <template #header>
            <div class="flex justify-between">
                <span class="text-sm">店铺及商品提示</span>
                <el-tag type="danger" effect="plain">
                    店铺及商品提示
                </el-tag>
            </div>
        </template>
        <el-row :gutter="20">

            <el-col :span="8" :offset="0" class="flex justify-between">
                <el-card shadow="hover" class="border-0 bg-light-400">
                    <div class="flex flex-col items-center justify-center">
                        <span class="text-xl mb-2"><CountTo :value="datas.onSaleProductCount
" /></span>
                        <span class="text-xs text-gray-500">销售中</span>
                    </div>
                </el-card>
            </el-col>

            <el-col :span="8" :offset="0" class="flex justify-between">
                <el-card shadow="hover" class="border-0 bg-light-400">
                    <div class="flex flex-col items-center justify-center">
                        <span class="text-xl mb-2"><CountTo :value="datas.notSaleProductCount" /></span>
                        <span class="text-xs text-gray-500">已下架</span>
                    </div>
                </el-card>
            </el-col>

            <el-col :span="8" :offset="0" class="flex justify-between">
                <el-card shadow="hover" class="border-0 bg-light-400">
                    <div class="flex flex-col items-center justify-center">
                        <span class="text-xl mb-2"><CountTo :value="datas.productStockLowCount" /></span>
                        <span class="text-xs text-gray-500">库存预警 </span>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </el-card>

    <el-card shadow="never">
        <template #header>
            <div class="flex justify-between">
                <span class="text-sm">交易提示</span>
                <el-tag type="danger" effect="plain">
                    需要立即处理的交易订单
                </el-tag>
            </div>
        </template>
        <el-row :gutter="20">
            <el-col :span="6" :offset="0" >
                <el-card shadow="hover" class="border-0 bg-light-400">
                    <div class="flex flex-col items-center justify-center">
                        <span class="text-xl mb-2"><CountTo :value="datas.noPayOrder" /></span>
                        <span class="text-xs text-gray-500 ">待支付</span>
                    </div>
                </el-card>
            </el-col>

            <el-col :span="6" :offset="0" >
                <el-card shadow="hover" class="border-0 bg-light-400">
                    <div class="flex flex-col items-center justify-center">
                        <span class="text-xl mb-2"><CountTo :value="datas.notSendOrder" /></span>
                        <span class="text-xs text-gray-500">待发货</span>
                    </div>
                </el-card>
            </el-col>

            <el-col :span="6" :offset="0" >
                <el-card shadow="hover" class="border-0 bg-light-400">
                    <div class="flex flex-col items-center justify-center">
                        <span class="text-xl mb-2"><CountTo :value="datas.alreadySendOrder" /></span>
                        <span class="text-xs text-gray-500">已发货</span>
                    </div>
                </el-card>
            </el-col>

            <el-col :span="6" :offset="0" >
                <el-card shadow="hover" class="border-0 bg-light-400">
                    <div class="flex flex-col items-center justify-center">
                        <span class="text-xl mb-2"><CountTo :value="datas.cancelOrder" /></span>
                        <span class="text-xs text-gray-500">退款中</span>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </el-card>

            </el-col>
        </el-row>
        

    </div>
</template>

<script setup>
import {home} from "~/api/manager.js"
import{ref} from 'vue'
import CountTo from "~/components/CountTo.vue";
import indexChar from '~/layout/components/indexChar.vue';

const datas = ref([])
home()
.then(res=>{
    datas.value = res
    console.log(datas);
    // console.log(res);
})

</script>