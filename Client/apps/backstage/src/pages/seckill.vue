<template>
  <el-card shadow="never" class="border-0">
    <div class="flex items-center justify-between mb-4">
      <div>
        <el-button type="success" size="small" @click="newseckill.isShow = true"
          >新增商品</el-button
        >
      </div>
    </div>

    <el-table
      :data="list"
      stripe
      style=""
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="index" label="序号" width="80" />
      <el-table-column label="商品" class="center">
        <template #default="{ row }" class="mr-4">
          <div>
            <el-image
              :src="row.productPic"
              fit="fill"
              :lazy="true"
              style="width: 100px; height: 100px"
            >
            </el-image>
          </div>
          <div>{{ row.productName }}</div>
        </template>
      </el-table-column>

      <el-table-column label="规格">
        <template #default="{ row }" class="mr-4">
          <div>{{ trans(row.skuName) }}</div>
        </template>
      </el-table-column>
      <el-table-column prop="seckillPrice" label="价格" />
      <el-table-column prop="stockCount" label="库存" />
      <el-table-column
        prop="startTime"
        label="开始时间"
        :formatter="dateFormat"
      />
      <el-table-column
        prop="endTime"
        label="结束时间"
        :formatter="dateFormat"
      />
    </el-table>
  </el-card>

  <el-drawer
    ref="drawerRef"
    v-model="newseckill.isShow"
    title="选择商品"
    :before-close="handleClose"
    :close-on-click-modal="false"
    direction="ltr"
    size="45%"
    destroy-on-close="true"
    class="demo-drawer"
  >
    <el-form :model="newseckill" label-width="80px">
      <el-form-item label="产品Id:" >
        <div>
        <div class="flex justify-between">
          <el-input
            v-model="newseckill.Seckill.productId"
            disabled
            placeholder="点击右侧选择"
          />
          <el-button size="default" class="ml-2" @click="newseckill.flag=true"> . . . </el-button>
        </div>
        <div>
          <el-card  v-show="newseckill.Seckill.productId" shadow="never" class="flex-col w-40 h-50 mt-3 justify-center items-center" >
              <div class="flex-col justify-center items-center">
              <div>
            <el-image :src="king.pic" fit="fill" :lazy="true" style="width: 100px;height: 100px;">
                    
            </el-image>
        </div>
        <div class="flex justify-center items-center">{{ king.name }}</div>
              </div>
        </el-card>
        </div>
        </div>
      </el-form-item>

      <el-form-item label="产品规格:">
        <el-radio-group v-model="newseckill.Seckill.skuId" v-for="(item,index) in skus.skulist">

        </el-radio-group>
      </el-form-item>

      <el-form-item label="价格:">
        <el-input v-model="newseckill.Seckill.seckillPrice" />
      </el-form-item>

      <el-form-item label="库存:">
        <el-input v-model="newseckill.Seckill.stockCount" />
      </el-form-item>

      <el-form-item label="开始时间">
        <el-date-picker
          v-model="newseckill.Seckill.startTime"
          type="datetime"
          placeholder="请选择开始时间"
        />
      </el-form-item>

      <el-form-item label="结束时间">
        <el-date-picker
          v-model="newseckill.Seckill.endTime"
          type="datetime"
          placeholder="请选择开始时间"
        />
      </el-form-item>
    </el-form>
  </el-drawer>

<el-drawer
    ref="drawerRef"
    v-model="newseckill.flag"
    title="添加商品"
    :before-close="handleClose"
    direction="rtl"
    size="45%"

    class="demo-drawer">
    
    <div>
      <el-row :gutter="24">
        <el-col :span="8" :offset="0" v-for="(item,index) in product" :key="index">
            <el-card shadow="never" class="flex-col w-40 h-60 mt-3 justify-center items-center" >
              <div class="flex-col justify-center items-center">
              <div>
            <el-image :src="item.productPic" fit="fill" :lazy="true" style="width: 100px;height: 100px;">
                    
            </el-image>
        </div>
        <div class="flex justify-center items-center">{{ item.productName }}</div>
        <div class="flex justify-center items-center">
          <el-button  size="default" @click="choose(item.productId,item.productPic,item.productName)">选择</el-button>
        </div>
        </div>
            </el-card>
            
        </el-col>

      </el-row>
      
    </div>

</el-drawer>


</template>

<script setup>
import { getSeckillList,getSeckillProd,getSeckillSku } from "~/api/seckill";
import { ref, reactive,onMounted } from "vue";

const tt = ref();
const list = ref();
const product =ref();
const king = reactive({
    pic:"",
    name:""
})
const skus=reactive({
  skulist:""
})

const newseckill = reactive({
  isShow: false,
  flag:false,
  Seckill: {
    productId: "",
    skuId: "",
    seckillPrice: "",
    stockCount: "",
    startTime: "",
    endTime: "",
  },
});

const getData=()=>{
  getSeckillList().then((r) => {
  list.value = r;
});
}

const choose=(id,pic,name)=>{
  newseckill.Seckill.productId = id;
  king.pic = pic
  king.name = name
  console.log(king)
  getSeckillSku(id)
  .then((res)=>{
    console.log("res",res)
    skus.skulist = res
  })
}

const getproduct=()=>{
  getSeckillProd().then((r)=>{
    product.value=r
    console.log(product)
    console.log(r)
  })
}



const trans = (str) => {
  str = str.replaceAll("{", "");
  str = str.replaceAll("}", " ");
  str = str.replaceAll(",", " ,");
  str = str.replaceAll('"', "");
  return str;
};

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

onMounted(()=>{
  getData()
  getproduct()
})
</script>