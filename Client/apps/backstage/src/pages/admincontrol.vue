<template>
  <el-card shadow="never" class="border-0">
    <el-form :model="searchForm" label-width="80px" class="mb-3" size="small">
      <el-row :gutter="20">
        <el-col :span="8" :offset="0">
          <el-form-item label="关键词">
            <el-input
              v-model="searchForm.keyword"
              placeholder="管理员昵称"
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
            </div>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <div class="flex items-center justify-between mb-4">
      <el-button type="primary" size="small" @click="flag = true"
        >新增</el-button
      >
      <el-tooltip class="box-item" effect="dark" content="刷新" placement="top">
        <el-button text @click="getData()">
          <el-icon size="”20"><Refresh /></el-icon>
        </el-button>
      </el-tooltip>
    </div>
    <el-table :data="list" stripe style="width: 100%">
      <el-table-column prop="userId" label="编号" />
      <el-table-column prop="nickName" label="管理员" />

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
      <el-form :model="form" ref="formRef" :rules="rules" label-width="80px" :inline="false" size="normal">
        
        <el-form-item prop="userName" label="用户名：">
          <el-input v-model="form.userName"></el-input>
        </el-form-item>

        <el-form-item prop="password" label="密码：">
          <el-input v-model="form.password"></el-input>
        </el-form-item>

        <el-form-item prop="nickName" label="昵称：">
          <el-input v-model="form.nickName"></el-input>
        </el-form-item>

        <el-form-item prop="email" label="邮箱：">
          <el-input v-model="form.email"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="newman">提交</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
      
    </el-drawer>
  
  </el-card>
</template>

<script setup>
import { getadminlist } from "~/api/manager";
import { reactive, ref } from "vue";
import { ElMessageBox } from 'element-plus'
import { findadmin, modifystatus, addadmin, deleteAdmin } from "../api/manager";
import { toast } from "~/composables/util";


const list = ref([]);
const formRef = ref(null)

const flag = ref(false)
const searchForm = reactive({
  keyword: "",
});

const form = reactive({
    userName:"",
    password:"",
    nickName:"",
    email:""

})

const rules ={
    userName:[
        { 
            required: true, 
            message: '用户名不能为空', 
            trigger: 'blur' 
        },
        {
            validator: function(rule, value, callback) {
              if (/^[A-Za-z0-9]+$/.test(value) == false) {
                callback(new Error("请输入字母和数字"));
              } else {
                //校验通过
                callback();
              }
            },
            trigger: "blur"
          }
    ],
    password:[
        { 
            required: true, 
            message: '密码不能为空', 
            trigger: 'blur' 
        },
        {
            validator: function(rule, value, callback) {
              if (/^[A-Za-z0-9]+$/.test(value) == false) {
                callback(new Error("请输入字母和数字"));
              } else {
                //校验通过
                callback();
              }
            },
            trigger: "blur"
          }
    ],
    nickName:[
        { 
            required: true, 
            message: '昵称不能为空', 
            trigger: 'blur' 
        },
        
    ],

}

var reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/


const newman =() =>{
  if(!reg.test(form.email)) {toast("邮箱格式不对",'error')
   return false}
  if(!/^[A-Za-z0-9]+$/.test(form.userName)||!/^[A-Za-z0-9]+$/.test(form.password)){
    toast("输入有误",'error')
    return false
  }

  addadmin(form)
  .then((res)=>{
    

    getData()
  }).finally(()=>{
    flag.value = false;
  })


}

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

//重置搜索框
const resetSearchForm = () => {
  searchForm.keyword = "";
  getData;
};

const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

const loading = ref(false);

//模糊查找
function find(keyword) {
  loading.value = true;
  findadmin(currentPage.value, pageSize.value, keyword)
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
  getadminlist(currentPage.value, pageSize.value)
    .then((res) => {
      list.value = res.rows;
      total.value = parseInt(res.total);
    })
    .finally(() => {
      loading.value = false;
    });
}

getData();

//修改状态
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
  deleteAdmin(userIds)
    .then((res) => {
      toast("删除成功");
      getData();
    })
    .finally(() => {
      loading.value = false;
    });
};

</script> 