<template>
  <el-card shadow="never" class="border-0">
    <div class="flex items-center justify-between mb-4">
      <div>
        <el-button type="danger" size="small" @click="deletelist"
          >批量删除</el-button
        >
        <el-button type="success" size="small" @click="sendlist"
          >批量发货</el-button
        >
      </div>

      <div class="ml-1">
        <el-button type="primary" size="small" @click="getData">全部</el-button>
        <el-button type="success" size="small" @click="findx(1)"
          >已支付</el-button
        >
        <el-button type="warning" size="small" @click="findx(2)"
          >已发货</el-button
        >
        <el-button type="danger" size="small" @click="findx(3)"
          >已完成</el-button
        >
        <el-button type="info" size="small" @click="findx(4)">已取消</el-button>
      </div>
    </div>

    <el-table
      :data="list.productListVos"
      stripe
      style=""
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column type="index" label="序号" width="80" />
      <el-table-column
        prop="orderTime"
        label="下单时间"
        :formatter="dateFormat"
      />
      <el-table-column prop="orderId" label="订单编号" />
      <el-table-column prop="userName" label="下单用户" />
      <el-table-column prop="totalPrice" label="订单金额" />

      <el-table-column label="订单状态">
        <template #default="{ row }" class="mr-4">
          <div>
            {{ totrans(row.status) }}
          </div>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="250" align="center">
        <template #default="scope">
          <el-button
            text
            type="primary"
            size="small"
            @click="showdetail(scope.row.orderId)"
          >
            查看详情
          </el-button>

          <el-popconfirm
            title="确定要发货吗？"
            confirmButtonText="确认"
            cancelButtonText="取消"
            @confirm="handleSend(scope.row.orderId)"
          >
            <template #reference>
              <el-button
                v-if="scope.row.status == 1"
                text
                type="primary"
                size="small"
              >
                发货</el-button
              >
            </template>
          </el-popconfirm>

          <el-popconfirm
            title="是否要删除该订单？"
            confirmButtonText="确认"
            cancelButtonText="取消"
            @confirm="handleDelete(scope.row.orderId)"
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
      title="订单详情"
      size="45%"
      :direction="direction"
      :before-close="handleClose"
    >
      <el-card shadow="never" class="mb-3">
        <template #header>
          <b class="text-sm">订单详情</b>
        </template>
        <el-form :model="orderd" label-width="80px">
          <el-form-item label="订单号">
            {{ orderd.orderDetail.orderId }}
          </el-form-item>
          <el-form-item label="创建时间">
            {{ dateTrans( orderd.orderDetail.orderTime ) }}
          </el-form-item>
        </el-form>
      </el-card>


      <el-card shadow="never" class="mb-3">
        <template #header>
            <b class="text-sm">地址详情</b>
        </template>
        
        <el-form :model="orderd" label-width="80px">
          <el-form-item label="地址 ">
            {{ orderd.addressDetail.address }}
          </el-form-item>
          <el-form-item label="收货人">
            {{ orderd.addressDetail.name }}
          </el-form-item>
          <el-form-item label="联系方式">
            {{ orderd.addressDetail.phone }}
          </el-form-item>
        </el-form>

      </el-card>

      <el-card shadow="never" class="mb-3">
        <template #header>
            <b class="text-sm">产品详情</b>
        </template>
        <div class="flex mb-2" v-for="(item,index) in orderd.productDetail.productList" :key="index">
                <el-image :src=skuImage fit="fill" :lazy="true" style="width:60px;height:60px;"></el-image>
                <div class="ml-2 text-sm">
                    <p>{{ item.productName }}</p>
                    <p>
                        <b class="text-rose-500 mr-2">￥{{ item.skuPrice }}</b>
                        <span class="text-xs text-gray-500">x{{ item.skuQuantity }}</span>
                    </p>
                </div>
            </div>
      </el-card>
      
      <el-card shadow="never" class="mb-3">
        <template #header>
            <b class="text-sm">总价</b>
        </template>
            {{ orderd.productDetail.totalPrice }}
      </el-card>

    </el-drawer>
  </el-card>
</template>

<script setup>
import {
  getorderlist,
  deleteorder,
  getorderdetail,
  ordersend,
} from "~/api/order";
import { ref, onMounted, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { toast } from "~/composables/util";

const list = ref([]);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);
const loading = ref(false);

const flag = ref(false);

const orderd = reactive({
  orderDetail: {},
  productDetail: {},
  addressDetail: {},
});

function dateTrans (daterc) {
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

const sstr = `{
  "orderDetail": {
    "orderId": 1,
    "orderTime": "2023-04-10T16:13:38.000+00:00"
  },
  "productDetail": {
    "productList": [
      {
        "productName": "Super紫大米18",
        "skuName": "{\"内存\": \"12GB+512GB\", \"颜色\": \"黑色\"}",
        "skuImage": "https://2a.zol-img.com.cn/product/237_220x165/786/ceiroFTZTY06.jpg",
        "skuPrice": 3999,
        "skuQuantity": 1
      }
    ],
    "totalPrice": 3999
  },
  "addressDetail": {
    "address": "广西桂林灵川县灵田镇桂电",
    "name": "giao哥",
    "phone": "17382654938"
  }
}`;
const showdetail = (id) => {
  flag.value = true;
  getorderdetail(id).then((res) => {
    orderd.addressDetail = res.addressDetail;
    orderd.orderDetail = res.orderDetail;
    orderd.productDetail = res.productDetail;
  });
};

const handleClose = () => {
  flag.value = false;
};

function getData(p = null) {
  if (typeof p == "number") {
    currentPage.value = p;
  }

  loading.value = true;
  console.log("我要全部");
  getorderlist(currentPage.value, pageSize.value, "-1")
    .then((res) => {
      console.log(res);
      list.value = res;
      total.value = res.total;
      console.log(total.value);
    })
    .finally(() => {
      loading.value = false;
    });
}

function findx(status) {
  loading.value = true;
  console.log("我要已支付");
  getorderlist(currentPage.value, pageSize.value, status)
    .then((res) => {
      console.log(res);
      list.value = res;
      total.value = parseInt(res.length);
      console.log(total.value);
    })
    .finally(() => {
      loading.value = false;
    });
}

const orderlist = [];

const handleSend = (id) => {
  orderlist[0] = id;
  console.log(orderlist);
  ordersend(orderlist)
    .then((res) => {
      toast(res);
      getData();
    })
    .finally(() => {
      orderlist.values = [];
    });
};

const sendlist = () => {
  ElMessageBox.confirm(
    "确定要发货选定的商品吗",

    {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    }
  )
    .then(() => {
      if (orderlist.values.length == 0) {
        toast("请先勾选", "warning");
      } else {
        ordersend(orderlist.values).then((res) => {
          toast(res);
          getData();
        });
      }
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "已取消",
      });
    });
};

const orderIds = [];

const handleDelete = (id) => {
  console.log(id);
  orderIds[0] = id;
  console.log(orderIds);
  deleteorder(orderIds)
    .then((res) => {
      toast("删除成功");
      getData();
    })
    .finally(() => {
      orderIds.values = [];
    });
};

const deletelist = () => {
  ElMessageBox.confirm(
    "确定要删除选定的订单吗",

    {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    }
  )
    .then(() => {
      if (orderlist.values.length == 0) {
        toast("请先勾选", "warning");
      } else {
        deleteorder(orderlist.values).then((res) => {
          toast("删除成功");
          getData();
        });
      }
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "已取消",
      });
    });
};

const handleSelectionChange = (e) => {
  orderlist.values = e.map((o) => o.orderId);
};

function totrans(status) {
  switch (status) {
    case 1:
      return "已支付";
    case 2:
      return "已发货";
    case 3:
      return "已完成";
    case 4:
      return "已取消";
  }
}

function dateFormat(row, column, cellValue, index) {
  const daterc = row[column.property];
  if (daterc) {
    if (daterc.indexOf("T") === -1) return daterc;
    const arr = daterc.split("T");
    const d = arr[0];
    const darr = d.split("-");
    const t = arr[1];
    const tarr = t.split(".000");
    const marr = tarr[0].split(":");
    const dd =
      darr[0] +
      "-" +
      darr[1] +
      "-" +
      darr[2] +
      " " +
      marr[0] +
      ":" +
      marr[1] +
      ":" +
      marr[2].substring(0, 2);
    return dd;
  }
}

onMounted(() => {
  getData();
});
</script>