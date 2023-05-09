<template>
  <el-card shadow="never" class="border-0">
        <div class="flex items-center justify-between mb-4">
          <el-button type="primary" size="small" @click="addtype"
        >新增</el-button
      >

          <el-tooltip
            class="box-item"
            effect="dark"
            content="刷新"
            placement="top"
          >
          <el-button text @click="getData()">
                    <el-icon size="”20"><Refresh /></el-icon>
                  </el-button>
          </el-tooltip>
        </div>

        <el-table :data="arrData" stripe style="width: 100%">
              <el-table-column prop="attributeName" label="规格名称" />
              <el-table-column prop="values" label="规格值">
                <template #default="{ row }">
                  <el-tag
                    v-for="tag in row.values"
                    :key="tag.name"
                    class="mx-1"
                    closable
                    @close="clickDeleteSku(tag.valueIds)"
                  >
                    {{ tag.valueName }}
                  </el-tag>
                  
                </template>
              </el-table-column>


              <el-table-column label="操作" width="180" align="center">
                <template #default="scope">
                  <!-- <el-button type="primary" size="small" text>修改</el-button> -->
                  <el-popconfirm
                    title="是否要删除该规格？"
                    confirmButtonText="确认"
                    cancelButtonText="取消"
                    @confirm="handleDelete(scope.row.attributeId)"
                  >
                    <template #reference>
                      <el-button text type="primary" size="small">
                        删除
                      </el-button>
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
import { getskulist, deleteSku,deleteSkubig } from "~/api/sku";
import { onMounted, reactive, ref, unref } from "vue";
import { ElMessageBox } from "element-plus";
import { finduser, deleteUser } from "../api/commonadmin";
import { toast } from "~/composables/util";

const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);


const addtype = () => {
  ElMessageBox.prompt('请输入想要添加的类别名', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
  })
    .then(({value}) => {
      console.log(value)
      .log
    })
}

const getObjectKeyName = (obj) => {
  const unknoewThis = Object.keys(obj);
  console.log(unknoewThis);
  return unknoewThis;
};

const getObjectKey = (obj) => {
  const arr = [];
  const unknoewThis = Object.keys(obj);
  const len = unknoewThis.length;
  for (let i = 0; i < len; i++) {
    arr.push(obj[unknoewThis[i]]);
  }
  return arr;
};

const arrData = ref([]);





const loading = ref(false);

const clickDeleteSku = (id) => {
  const arr = [id];

  deleteSku(arr)
    .then((res) => {
      toast("删除成功");
      getData();
    })
    .finally(() => {
      loading.value = false;
    });
};

function getData(p = null) {
  if (typeof p == "number") {
    currentPage.value = p;
  }

  loading.value = true;
  console.log("!!!");
  getskulist()
    .then((res) => {
      // console.log(res.rows,res)
      arrData.value = res;
    })
    .finally(() => {
      loading.value = false;
    });
}




const handleDelete = (attributeId) => {
  const temp = {attributeId}
  console.log(attributeId)
  console.log(temp)
  deleteSkubig(attributeId)
    .then((res) => {
      toast("删除成功");
      getData();
    })
    .finally(() => {
      loading.value = false;
    });
};

function arrToObjArr(arrKey, arrValue) {
  const arr1 = unref(arrKey);
  const arr2 = unref(arrValue);
  const arr = [];
  for (let i = 0; i < arr1.length; i++) {
    let key = arr1[i];
    let value = arr2[i];
    arr.push({ name: key, data: value });
  }
  return arr;
}

onMounted(() => {
  getData();
});
</script> 