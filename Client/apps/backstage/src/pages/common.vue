<template>
  <el-card shadow="never" class="border-0">
    <el-form :model="searchForm" label-width="80px" class="mb-3" size="small">
      <el-row :gutter="20">
        <el-col :span="8" :offset="0">
          <el-form-item label="关键词">
            <el-input
              v-model="searchForm.keyword"
              placeholder="用户昵称"
              clearable
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8" :offset="8">
          <el-form-item>
            <div class="flex items-center justify-end">
              <el-button type="primary" @click="find(searchForm.keyword)"
                >搜索</el-button
              >
              <el-button @click="resetSearchForm">重置</el-button>
              <el-button text @click="getData()">
          <el-icon size="”20"><Refresh /></el-icon>
        </el-button>
            </div>

          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <div class="flex items-center justify-between mb-4">
      <el-tooltip class="box-item" effect="dark" content="刷新" placement="top">

      </el-tooltip>
    </div>
    <el-table :data="list" stripe style="width: 100%">
      <el-table-column prop="userId" label="编号" />
      <el-table-column prop="nickName" label="用户昵称" />
      <el-table-column prop="mobile" label="手机号" />
      <el-table-column prop="createTime" label="注册时间" />

      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-switch
            :modelValue="row.status"
            :active-value="0"
            :inactive-value="1"
            :loading="row.statusLoading"
            @change="handelchange($event, row)"
          >
          </el-switch>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="180" align="center">
        <template #default="scope">
          <!-- <el-button type="primary" size="small" text>修改</el-button> -->
          <el-popconfirm
            title="是否要删除该管理员？"
            confirmButtonText="确认"
            cancelButtonText="取消"
            @confirm="handleDelete(scope.row.userId)"
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
import { getuserlist } from "~/api/commonadmin";
import { onMounted, reactive, ref } from "vue";
import { ElMessageBox } from 'element-plus'
import { finduser, modifystatus,  deleteUser } from "../api/commonadmin";
import { toast } from "~/composables/util";

console.log("47685468486")
const list = ref([]);

const flag = ref(false)
const searchForm = reactive({
  keyword: "",
});

const newman = reactive({

});

//关闭抽屉
const handleClose = () => {
  ElMessageBox.confirm("确定要离开吗？",
    "警告：离开页面数据将不进行保存",
    {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: 'warning',
    })
    .then(() => {
      flag.value = false
      console.log(flag)
    })
    .catch(() => {
      // catch error
    })
}
//重置按钮
const resetSearchForm = () => {
  searchForm.keyword = "";
  getData;
};

//查找
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

const loading = ref(false);

function find(keyword) {
  loading.value = true;
  finduser(currentPage.value, pageSize.value, keyword)
    .then((res) => {
      console.log(keyword);
      console.log(res);
      list.value = res.rows.map((o) => {
        o.statusLoading = false;
        return o;
      });
      total.value = parseInt(res.total);
    })
    .finally(() => {
      loading.value = false;
    });
}

//获取页面数据
function getData(p = null) {
  if (typeof p == "number") {
    currentPage.value = p;
  }

  loading.value = true;
  console.log("!!!")
  getuserlist(currentPage.value, pageSize.value)
    .then((res) => {
      console.log(res.rows,res)
      list.value = res.rows;
      total.value = parseInt(res.total);
    })
    .finally(() => {
      loading.value = false;
    });
}


//更改状态
const handelchange = (status, row) => {
  console.log(row.userId);
  row.statusLoading = true;
  modifystatus(row.userId)
    .then((res) => {
      toast("修改成功");
      row.status = status;
    })
    .finally(() => {
      row.statusLoading = false;
    });
};

const userIds = [];

//删除数据
const handleDelete = (id) => {
  userIds[0] = id;
  console.log(userIds);
  deleteUser(userIds)
    .then((res) => {
      toast("删除成功");
      getData();
    })
    .finally(() => {
      loading.value = false;
    });
};


onMounted(() => {
  getData();
})
</script> 