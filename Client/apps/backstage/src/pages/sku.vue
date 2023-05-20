<template>
  <el-card shadow="never" class="border-0">
    <div class="flex items-center justify-between mb-4">
      <el-button type="primary" size="small" @click="addtypebutton"
        >新增</el-button
      >

      <el-tooltip class="box-item" effect="dark" content="刷新" placement="top">
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
            :type="tag.type"
            effect= tag.effect
            closable
            @close="clickDeleteSku(tag.valueIds)"
          >
            {{ tag.valueName }}
          </el-tag>

          <el-button  class="button-new-tag ml-1" size="small" @click="addsmallskuvalue(row.attributeId)">
            +新规格
           </el-button>
          
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
import { getskulist, deleteSku, deleteSkubig } from "~/api/sku";
import { onMounted,  ref, unref } from "vue";
import { ElMessageBox, ElMessage } from "element-plus";
import { addsku,addsmallsku } from "../api/sku";
import { toast } from "~/composables/util";

const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

// const randomstyle = ref([
//   {effect:}
// ])


const addsmallskuvalue = (attributeId) =>{
  ElMessageBox.prompt("请输入想要添加的规格名", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
  })
  .then(({ value }) => {
      console.log(value);
      if(!value) toast("请勿输入空值",'error')
      else{
      addsmallsku(attributeId,value)
        .then(() => {
          toast("添加成功");
          getData();
        })
        .catch(() => {
          toast("操作失败", "error");
        });}
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "输入取消",
      });
    });
}


//加大
const addtypebutton = () => {
  ElMessageBox.prompt("请输入想要添加的类别名", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
  })
    .then(({ value }) => {
      console.log(value);
      if(!value) toast("请勿输入空值",'error')
      else{
      addsku(value)
        .then(() => {
          toast("添加成功");
          getData();
        })
        .catch(() => {
          toast("操作失败", "error");
        });
      }
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "输入取消",
      });
    });
};

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

const effects = ["dark", "light", "plain"];
const types = ["", "success", "info", "danger", "warning"];

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
      arrData.value.map((item) => {
        item.values.map((i) => {
          let index1 = Math.floor(
            Math.random(Date.parse(new Date())) * effects.length
          );
          let index2 = Math.floor(
            Math.random(Date.parse(new Date())) * types.length
          );
          i.type = types[index2];
          i.effect = effects[index1];
        });
      });
    })
    .finally(() => {
      loading.value = false;
    });
}

const handleDelete = (attributeId) => {
  const temp = { attributeId };
  console.log(attributeId);
  console.log(temp);
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