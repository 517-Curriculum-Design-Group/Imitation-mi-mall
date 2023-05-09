<template>
  <el-card shadow="never" class="border-0">
    <div class="flex items-center justify-between mb-4">
      <el-tooltip class="box-item" effect="dark" content="刷新" placement="top">
      </el-tooltip>
    </div>
    <el-table :data="list" stripe style="width: 100%">
      <el-table-column prop="categoryId" label="类别编号" />
      <el-table-column prop="categoryName" label="类别昵称" />
      <el-table-column prop="parentId" label="所属类别id" />

      <el-table-column prop="status" label="状态">
        <template #default="{ row }">
          <el-switch
            :modelValue="row.status"
            :active-value="1"
            :inactive-value="0"
            :loading="row.statusLoading"
            @change="handelchange($event, row)"
          >
          </el-switch>
          {{ row.categoryName }}
          {{ row.status }}
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

    <el-drawer
      v-model="flag"
      title="新增管理员"
      size="45%"
      :direction="direction"
      :before-close="handleClose"
    >
      <!-- <el-form :model="form" ref="formRef" :rules="rules" label-width="80px" :inline="false">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item label="头像" prop="avatar">
          <ChooseImage v-model="form.avatar"/>
        </el-form-item>
        <el-form-item label="所属角色" prop="role_id">
          <el-select v-model="form.role_id" placeholder="选择所属角色">
            <el-option v-for="item in roles"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="content">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0">
          </el-switch>
        </el-form-item>
      </el-form> -->
    </el-drawer>
  </el-card>
</template>

<script setup>
import { gettypelist } from "../api/type";
import { getuserlist } from "~/api/commonadmin";
import { onMounted, reactive, ref } from "vue";
import { ElMessageBox } from "element-plus";
import { finduser, modifystatus, deleteUser } from "../api/commonadmin";
import { toast } from "~/composables/util";

gettypelist().then((res) => {
  console.log(res);
});

const list = ref([]);

const flag = ref(false);
const searchForm = reactive({
  keyword: "",
});

const newman = reactive({});

//关闭抽屉
const handleClose = () => {
  ElMessageBox.confirm("确定要离开吗？", "警告：离开页面数据将不进行保存", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      flag.value = false;
      console.log(flag);
    })
    .catch(() => {
      // catch error
    });
};

//查找
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

const loading = ref(false);

//获取页面数据
function getData(p = null) {
  if (typeof p == "number") {
    currentPage.value = p;
  }

  loading.value = true;
  console.log("!!!");
  gettypelist()
    .then((res) => {
      console.log(res.rows, res);
      list.value = res;
    })
    .finally(() => {
      loading.value = false;
    });
}

//更改状态
const handelchange = (status1, row) => {
  console.log(row.categoryName);
  row.statusLoading = true;
  const arr = {};
  modifystatus(row.status)
    .then((res) => {
      toast("修改成功");
      row.status = status1;
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
});
</script> 