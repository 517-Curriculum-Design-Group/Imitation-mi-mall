<template>
       <el-card shadow="never" class="border-0">

        <el-form :model="searchForm"  label-width="80px" class="mb-3" size="small">
            
            <el-row :gutter="20">
                <el-col :span="8" :offset="0">
                    <el-form-item label="关键词">
                <el-input v-model="searchForm.keyword" placeholder="管理员昵称" clearable></el-input>
            </el-form-item>
                </el-col>
                <el-col :span="8" :offset="8">
                    <el-form-item>
                        <div class="flex items-center justify-end">
                            <el-button type="primary" @click="find(searchForm.keyword)">搜索</el-button>
                            <el-button @click="resetSearchForm">重置</el-button>
                        </div>
                     </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        

        <div class = "flex items-center justify-between mb-4">
            <el-button type="primary" size="small" >新增</el-button>
            <el-tooltip
        class="box-item"
        effect="dark"
        content="刷新"
        placement="top"
      >
        <el-button text @click="getData()">
            <el-icon size="”20"><Refresh/></el-icon>
        </el-button>
      </el-tooltip>
        </div> 
        <el-table :data="list" stripe style="width: 100%">
    <el-table-column prop="userId" label="编号" />
    <el-table-column prop="nickName" label="管理员"/>

    <el-table-column prop="status" label="状态">
        <template #default="{row}">
        <el-switch :modelValue="row.status" :active-value="0" :inactive-value="1" :loading="row.statusLoading" @change="handelchange($event,row)">
        </el-switch>
        </template>

       </el-table-column> 


    <el-table-column  label="操作" width="180" align="center">
        <template #default="scope">
            <!-- <el-button type="primary" size="small" text>修改</el-button> -->
            <el-popconfirm title="是否要删除该管理员？" confirmButtonText="确认" cancelButtonText="取消" @confirm="handleDelete(scope.row.userId)">
                <template #reference>
                    <el-button text  type="primary" size="small">
                        删除
                    </el-button>
                </template>
            </el-popconfirm>
        </template>
    </el-table-column>
  </el-table>

        <div class="flex items-center justify-center mt-5">
            <el-pagination background layout="prev,pager,next" :total="total" :current-page="currentPage" :page-size="pageSize" @current-change="getData"/>
        </div>

    </el-card>
</template>

<script setup>
import { getadminlist } from "~/api/manager"
import {reactive, ref} from 'vue'
import { findadmin,modifystatus } from "../api/manager"
import {toast} from "~/composables/util"

const list = ref([])
const newman =reactive([
    
])

const searchForm = reactive({
    keyword:""
})
const resetSearchForm =()=>{
    searchForm.keyword = ""
    getData
}

const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const loading =ref(false)

function find(keyword){

    loading.value = true
    findadmin(currentPage.value,pageSize.value,keyword)
    .then(res=>{
        console.log(keyword)
        console.log(res)
        list.value = res.rows.map(o=>{
            o.statusLoading =false
            return o
        })
        total.value = parseInt(res.total)
    })
    .finally(()=>{
        loading.value = false
    })
}

function getData(p = null){
    if(typeof p == "number"){
        currentPage.value = p
    }

    loading.value = true
    getadminlist(currentPage.value,pageSize.value)
    .then(res=>{
        list.value = res.rows
        total.value = parseInt(res.total)
    })
    .finally(()=>{
        loading.value = false
    })
}

getData()

const handelchange = (status,row)=>{
    console.log(row.userId)
    row.statusLoading = true
    modifystatus(row.userId)
    .then(res=>{
        toast("修改成功")
        row.status = status
    }).finally(()=>{
    row.statusLoading = false
    })
}
</script> 