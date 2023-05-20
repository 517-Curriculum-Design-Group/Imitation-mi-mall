<template>
        <div class="flex items-center justify-between mb-4">
      <el-button type="danger" size="small" @click="deletelist"
        >批量删除</el-button
      >
      <el-tooltip class="box-item" effect="dark" content="刷新" placement="top">
        <el-button text @click="getData()">
          <el-icon size="”20"><Refresh /></el-icon>
        </el-button>
      </el-tooltip>
    </div>
    
     <el-table :data="list" stripe style="" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column prop="commentId" label="商品编号" />
      <el-table-column  label="商品" class="center">
        <template #default="{ row }" class="ml-4">
            <div>
            <el-image :src="row.productPic" fit="fill" :lazy="true" style="width: 100px;height: 100px;">
                    
            </el-image>
        </div>
        <div>{{ row.productName }}</div>
        </template>  
      </el-table-column>
      <el-table-column prop="content" label="评论内容" />
 
      <el-table-column label="操作" width="180" align="center">
        <template #default="scope">
          <!-- <el-button type="primary" size="small" text>修改</el-button> -->
          <el-popconfirm
            title="是否要删除该评论？"
            confirmButtonText="确认"
            cancelButtonText="取消"
            @confirm="handleDelete(scope.row.commentId)"
          >
            <template #reference>
              <el-button text type="primary" size="small"> 删除 </el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
</template>

<script setup>
import {getcommentlist,deletecommentlist} from '~/api/comments'
import{ref,onMounted} from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { toast } from "~/composables/util";

const list = ref()

function getData(){
getcommentlist()
.then((res)=>{
console.log(res)
     list.value = res
})
}

const commentlist = [];

const handleDelete=(id)=>{
    commentlist[0] = id;
  console.log(commentlist);
  deletecommentlist(commentlist)
    .then((res) => {
      toast("删除成功");
      getData();
    })
    .finally(()=>{
        commentlist.values = []
    })
}

const deletelist=()=>{
    ElMessageBox.confirm(
    '确定要删除选定的评论吗',
    
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(()=>{
    if(commentlist.values.length == 0){
        toast("请先勾选",'warning')
    }
    else{
     deletecommentlist(commentlist.values)
    .then((res) => {
      toast("删除成功");
      getData()
      
  })}

})
    .catch(()=>{
        ElMessage({
        type: 'info',
        message: '已取消',
      })
    })
}

const handleSelectionChange =(e)=>{
    commentlist.values = e.map(o=>o.commentId)
}

onMounted(()=>{
    getData();
})

</script>