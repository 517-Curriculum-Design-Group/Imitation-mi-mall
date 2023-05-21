<template>
  <el-card shadow="never" class="border-0">
    <el-form :model="searchForm" label-width="80px" class="mb-3" size="small">
      

    </el-form>

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

    <el-table :data="list" stripe style="" @selection-change="handleSelectionChange"  >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="commentId" label="评论编号" />
      <el-table-column  label="商品" class="center">
        <template #default="{ row }" class="mr-4">
            <div>
            <el-image :src="row.productPic" fit="fill" :lazy="true" style="width: 100px;height: 100px;">
                    
            </el-image>
        </div>
        <div>{{ row.productName }}</div>
        </template>  
      </el-table-column>
      <el-table-column prop="content" label="评论内容" />
      <el-table-column prop="commentTime" label="评论时间" :formatter="dateFormat">
        </el-table-column>

      <el-table-column prop="rate" label="评论类别" />
 
      <el-table-column label="操作" width="180" align="center">
        <template #default="scope">
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

    <div class="flex items-center justify-center mt-5">
      <el-pagination
        background
        layout="prev,pager,next"
        :total="total"
        :current-page="currentPage"
        :page-size="pageSize"
        @current-change="getData"
      />
    </div>
   
  </el-card>
</template>

<script setup>
import {getcommentlist,deletecommentlist} from '~/api/comments'
import{ref,onMounted} from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { toast } from "~/composables/util";

const list = ref()

const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);
const loading = ref(false);

//页面数据
function getData(p = null) {
  if (typeof p == "number") {
    currentPage.value = p;
  }

  loading.value = true;
  console.log("!!!")
  getcommentlist(currentPage.value, pageSize.value)
    .then((res) => {
      console.log(res)
      list.value = res;
      total.value = parseInt(res.length);
    })
    .finally(() => {
      loading.value = false;
    });
}

function dateFormat (row, column, cellValue, index) {
      const daterc = row[column.property]
      if (daterc) {
        if (daterc.indexOf('T') === -1) return daterc
        const arr = daterc.split('T')
        const d = arr[0]
        const darr = d.split('-')
        const t = arr[1]
        const tarr = t.split('.000')
        const marr = tarr[0].split(':')
        const dd = darr[0] + '-' + darr[1] + '-' + darr[2] + ' ' + marr[0] + ':' + marr[1] + ':' + marr[2].substring(0, 2)
        return dd
      }
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