<template >
     <el-card shadow="never" class = "border-0">

      <div class="flex items-center justify-between mb-4">
      <div>
        <el-button type="danger" size="small" @click="deletelist"
          >批量删除</el-button
        >
        <el-button type="success" size="small" @click="sendlist"
          >新增商品</el-button
        >
      </div>

    </div>

        <el-table :data="list"  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" />
            <el-table-column type="index" label = "序号" width="80"/>
            <el-table-column  label="商品" class="center" width="130">
        <template #default="{ row }" class="mr-4">
            <div>
            <el-image :src="row.productPic" fit="fill" :lazy="true" style="width: 100px;height: 100px;">
                    
            </el-image>
        </div>
        <div>{{ row.productName }}</div>
        </template>  
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" :formatter="dateFormat">
        </el-table-column>
            <el-table-column prop="leastPrice" label = "最低价"  />
            <el-table-column prop="productDescription" label = "商品描述" width="300" class="text-xs" />
            <el-table-column label="操作" width="80" align="center">
        <template #default="scope">

          <el-button
            text
            type="primary"
            size="small"
            @click="showdetail(scope.row.orderId)"
          >
            查看详情
          </el-button>

          <el-button
            text
            type="primary"
            size="small"
            @click="showdetail(scope.row.orderId)"
          >
            修改规格
          </el-button>
          
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
        :current-page.sync="currentPage"
        :page-size="pageSize"
        @current-change="getData(currentPage)"
      />
          <div>
            {{ currentPage }}
          </div>

    </div>

    <el-drawer       v-model="flag"
      title="规格详情"
      size="45%"
      :direction="direction"
      :before-close="handleClose">


    </el-drawer>
    


    </el-card> 


</template>

<script setup>
import {getproductlist} from '~/api/product'
import {ref,onMounted} from 'vue'

const list = ref([])
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);
const loading = ref(false);

function getData(p = null) {
  if (typeof p == "number") {
    currentPage.value = p;
    console.log(p)
  }

  loading.value = true;
  console.log("我要全部")
  getproductlist(currentPage.value, pageSize.value)
    .then((res) => {
      console.log(res.productListVos)
      list.value = res.productListVos;
      console.log(list.value)
      total.value = parseInt(res.total);
      console.log(total.value)
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

onMounted(()=>{
    getData()
})
</script>
