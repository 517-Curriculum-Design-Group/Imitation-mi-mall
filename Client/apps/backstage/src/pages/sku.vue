<template>
    <el-card shadow="never" class="border-0">
        <div class = "flex items-center justify-between mb-4">
            <el-button type="primary" size="small" >新增</el-button>
            <el-tooltip
        class="box-item"
        effect="dark"
        content="Top Center prompts info"
        placement="top"
      >
        <el-button>top</el-button>
      </el-tooltip>
        </div> 
        <el-table :data="tableData" stripe style="width: 100%">
    <el-table-column prop="date" label="Date" width="180" />
    <el-table-column prop="name" label="Name" width="180" />
    <el-table-column prop="address" label="Address" />
  </el-table>
    </el-card>
    
</template>

<script setup>
import{ref} from "vue"
import { getskulist } from "../api/manager";
// const tableData = ref([])

// function getData(){
//     tableData.value=[{
//         "id":13
//     }]
// }
const datas = ref([])
getskulist("1","10")
.then(res=>{
    datas.value = res
    console.log(datas);
    // console.log(res);
})

</script>