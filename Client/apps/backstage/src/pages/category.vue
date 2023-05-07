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