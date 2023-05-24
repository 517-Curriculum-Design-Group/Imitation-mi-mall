<template>
  <el-card shadow="never" class="border-0">
    <div class="flex items-center justify-between mb-4">
      <div>
        <el-button type="danger" size="small" @click="deletelist"
          >批量删除</el-button
        >
        <el-button type="success" size="small" @click="newProduct.isShow = true"
          >新增商品</el-button
        >
      </div>
    </div>

    <el-table :data="list" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column type="index" label="序号" width="80" />
      <el-table-column label="商品" class="center" width="130">
        <template #default="{ row }">
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
      <el-table-column
        prop="createTime"
        label="创建时间"
        :formatter="dateFormat"
      >
      </el-table-column>
      <el-table-column prop="leastPrice" label="最低价" />
      <el-table-column
        prop="productDescription"
        label="商品描述"
        width="300"
        class="text-xs"
      />
      <el-table-column
        prop="status"
        label="商品状态"
        align="center"
        width="200"
      >
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            active-text="上架"
            inactive-text="不上架"
            @change="clickSwitch(scope.row)"
        /></template>
      </el-table-column>
      <el-table-column
        class="text-center flex flex-col justify-center items-center"
        label="操作"
        width="140"
        align="center"
      >
        <template #default="scope">
          <el-button
            text
            type="primary"
            size="small"
            @click="showProduct(scope.row.productId)"
          >
            <span>查看详情</span>
          </el-button>

          <el-button
            class="p-0 m-0 mr-2"
            text
            type="primary"
            size="small"
            @click="showdetail(scope.row.productId)"
          >
            <span>修改规格</span>
          </el-button>

          <el-popconfirm
            title="是否要删除该商品？"
            confirm-button-text="确认"
            cancel-button-text="取消"
            @confirm="handleDelete(scope.row)"
          >
            <template #reference>
              <el-button class="p-0 m-0 mr-2" text type="primary" size="small">
                <span>删除</span>
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

    <el-drawer
      v-model="formData.isShow"
      title="规格详情"
      class="overflow-y-hidden"
      size="45%"
      destroy-on-close="true"
      :direction="direction"
      :before-close="handleClose"
      :close-on-click-modal="false"
      @close="clearData"
    >
      <div
        class="w-100% h-580px flex flex-col gap-y-3 overflow-y-hidden relative"
      >
        <el-select
          v-model="formData.option1Value"
          placeholder="选择规格1"
          value-key="attributeId"
          @change="formData.check1List = []"
        >
          <el-option
            v-for="item in options"
            :key="item.attributeId"
            :label="item.attributeName"
            :value="item"
            :disabled="
              item.attributeId === formData.option2Value.attributeId
                ? true
                : false
            "
          />
        </el-select>
        <div class="w-100% max-h-100px overflow-y-auto">
          <el-checkbox-group v-model="formData.check1List">
            <el-checkbox
              v-for="i in formData.option1Value.values"
              :key="i"
              :label="i.valueName"
            />
          </el-checkbox-group>
        </div>
        <el-select
          v-model="formData.option2Value"
          placeholder="选择规格2"
          value-key="attributeId"
          @change="formData.check2List = []"
        >
          <el-option
            v-for="item in options"
            :key="item.attributeId"
            :label="item.attributeName"
            :value="item"
            :disabled="
              item.attributeId === formData.option1Value.attributeId
                ? true
                : false
            "
          />
        </el-select>
        <div class="w-100% max-h-100px overflow-y-auto">
          <el-checkbox-group v-model="formData.check2List">
            <el-checkbox
              v-for="i in formData.option2Value.values"
              :key="i"
              :label="i.valueName"
            />
          </el-checkbox-group>
        </div>
        <div class="max-h-290px overflow-auto">
          <el-table :data="tableData" style="width: 100%">
            <el-table-column label="规格名" width="280">
              <template #default="scope">
                <div style="display: flex; align-items: center">
                  <span>{{ scope.row.label }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="价格" width="140">
              <template #default="scope">
                <input v-model="scope.row.skuPrice" />
              </template>
            </el-table-column>
            <el-table-column label="库存" width="140">
              <template #default="scope">
                <input v-model="scope.row.skuStock" />
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="h-38px fixed bottom-0 flex justify-center">
          <el-button
            class="w-140px"
            type="primary"
            size="medium"
            :loading="formData.loading"
            @click="clickSubmit"
            >确认</el-button
          >
        </div>
      </div>
    </el-drawer>
  </el-card>

  <el-drawer
    v-model="newProduct.isShow"
    title="新增商品"
    direction="ltr"
    destroy-on-close="true"
    :close-on-click-modal="false"
    :before-close="closeNewPro"
  >
    <el-form
      label-width="100px"
      :model="newProduct.form"
      :close-on-click-modal="false"
    >
      <el-form-item label="类别">
        <el-select v-model="newProduct.form.categoryId" placeholder="Select">
          <el-option
            v-for="item in newProduct.options"
            :key="item.categoryId"
            :label="item.categoryName"
            :value="item.categoryId"
            :disabled="item.parentId === -1 ? true : false"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="商品名">
        <el-input v-model="newProduct.form.productName" />
      </el-form-item>
      <el-form-item label="商品图片 URL">
        <el-input v-model="newProduct.form.productPic" />
      </el-form-item>
      <el-form-item label="商品描述">
        <el-input v-model="newProduct.form.productDescription" />
      </el-form-item>
    </el-form>
    <el-form-item>
      <el-button class="w-[30%]" type="primary" @click="clickNewPro"
        ><span>确认</span></el-button
      >
    </el-form-item>
  </el-drawer>

  <el-drawer
    v-model="productDetail.isShow"
    size="45%"
    title="商品详情"
    destroy-on-close="true"
    :close-on-click-modal="false"
  >
    <el-table :data="productDetail.data" style="width: 100%">
      <el-table-column prop="skus" label="规格值" />
      <el-table-column prop="skuPrice" label="现在的价格" />
      <el-table-column label="要修改的价格">
        <template #default="scope">
          <input v-model="scope.row.price" />
        </template>
      </el-table-column>
      <el-table-column prop="skuStock" label="现在的库存" />
      <el-table-column label="要修改的库存">
        <template #default="scope">
          <input v-model="scope.row.stock" />
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
            class="w-[30%]"
            type="primary"
            @click="clickNewSku(scope.row)"
            ><span>确认</span></el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </el-drawer>
</template>

<script setup>
import {
  getproductlist,
  getproductsku,
  updateproductsku,
  addnewproduct,
  addstock,
  updateproductstatus,
  deleteSingleProduct,
} from "~/api/product";
import { gettypelist } from "~/api/type";
import { getskulist } from "~/api/sku";
import { ref, onMounted, reactive, computed } from "vue";
import { ElNotification } from "element-plus";

const list = ref([]);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);
const loading = ref(false);

const formData = reactive({
  isShow: false,
  productId: 0,
  option1Value: "",
  option2Value: "",
  check1List: [],
  check2List: [],
  options: [],
  tableData: [],
  loading: false,
});

const newProduct = reactive({
  isShow: false,
  options: [],
  form: {
    categoryId: "",
    productName: "",
    productPic: "",
    productDescription: "",
  },
});

const productDetail = reactive({
  isShow: false,
  data: [],
});

const options = computed({
  get() {
    return formData.options;
  },
  set(newValue) {
    formData.options = newValue;
  },
});

const tableData = computed(() => {
  let arr = [formData.check1List, formData.check2List];
  if (formData.check1List.length === 0 || formData.check2List.length === 0) {
    if (formData.check1List.length === 0) {
      let swap = [].concat(formData.check2List);
      let arr1 = [];
      swap.forEach((item) => {
        let o = {
          skuNames: [
            formData.option1Value.values,
            formData.option2Value.values,
          ],
          skuValues: item,
          label: item,
          skuPrice: 0,
          skuStock: 0,
        };
        arr1.push(o);
      });
      return arr1;
    }
    if (formData.check2List.length === 0) {
      let swap = [].concat(formData.check1List);
      let arr2 = [];
      swap.forEach((item) => {
        let o = {
          skuNames: [
            formData.option1Value.values,
            formData.option2Value.values,
          ],
          skuValues: item,
          label: item,
          skuPrice: 0,
          skuStock: 0,
        };
        arr2.push(o);
      });
      return arr2;
    }
  }
  return [].reduce.call(arr, function (col, set) {
    let res = [];
    col.forEach((c) => {
      set.forEach((s) => {
        let t = [].concat(Array.isArray(c) ? c : [c]);
        t.push(s);
        let o = {
          skuNames: [
            formData.option1Value.values,
            formData.option2Value.values,
          ],
          skuValues: t,
          label: t.join(" "),
          skuPrice: 0,
          skuStock: 0,
        };
        res.push(o);
      });
    });
    return res;
  });
});

function getData(p) {
  if (typeof p == "number") {
    currentPage.value = p;
  }
  loading.value = true;
  getproductlist(currentPage.value, pageSize.value)
    .then((res) => {
      list.value = res.productListVos;
      total.value = parseInt(res.total);
    })
    .finally(() => {
      loading.value = false;
    });
  gettypelist().then((r) => {
    newProduct.options = r;
  });
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

function showdetail(id) {
  formData.isShow = true;
  getskulist().then((r) => {
    options.value = r;
  });
  formData.productId = id;
}

function clickSubmit() {
  let body = {
    productId: formData.productId,
    skuList: [],
    skuDetailList: [],
  };
  if (formData.check1List.length !== 0) {
    let obj = {};
    obj["skuName"] = formData.option1Value.attributeName;
    obj["skuValues"] = formData.check1List;
    body.skuList.push(obj);
  }
  if (formData.check2List.length !== 0) {
    let obj = {};
    obj["skuName"] = formData.option2Value.attributeName;
    obj["skuValues"] = formData.check2List;
    body.skuList.push(obj);
  }
  tableData.value.forEach((item) => {
    let obj = {
      skuValues: Array.isArray(item.skuValues)
        ? item.skuValues
        : [item.skuValues],
      skuPrice: item.skuPrice,
      skuStock: item.skuStock,
    };
    body.skuDetailList.push(obj);
  });
  formData.loading = true;
  updateproductsku(body)
    .then((r) => {
      ElNotification({
        title: "成功",
        message: "修改 SKU 成功",
        type: "success",
      });
    })
    .finally(() => {
      formData.isShow = false;
      formData.loading = false;
    });
}

function clickNewPro() {
  addnewproduct(newProduct.form)
    .then((r) => {
      ElNotification({
        title: "成功",
        message: "添加新商品成功",
        type: "success",
      });
    })
    .finally(() => {
      newProduct.isShow = false;
      getData();
      closeNewPro();
    });
}

function showProduct(id) {
  productDetail.isShow = true;
  getproductsku(id).then((r) => {
    productDetail.data = r;
    productDetail.data.forEach((item) => {
      item.price = 0;
      item.stock = 0;
    });
  });
}

function clickNewSku(newObj) {
  let obj = {
    skuId: newObj.skuId,
    price: newObj.price,
    stock: newObj.stock,
  };
  addstock(obj)
    .then((r) => {
      ElNotification({
        title: "成功",
        message: "添加 SKU 成功",
        type: "success",
      });
    })
    .finally(() => {
      productDetail.isShow = false;
    });
}

function clickSwitch(obj) {
  let o = {
    productId: obj.productId,
    status: obj.status ? 1 : 0,
  };
  updateproductstatus(o).then((r) => {
    ElNotification({
      title: "成功",
      message: r || "修改 SKU 成功",
      type: "success",
    });
  });
}

function handleDelete(o) {
  let obj = {
    productId: o.productId,
  };
  deleteSingleProduct(obj)
    .then((r) => {
      console.log(r);
      ElNotification({
        title: "成功",
        message: r || "删除成功",
        type: "success",
      });
    })
    .finally(() => {
      getData();
    });
}

function closeNewPro() {
  newProduct.form = {};
  newProduct.isShow = false;
}

function clearData() {
  formData.option1Value = "";
  formData.option2Value = "";
  formData.check1List = [];
  formData.check2List = [];
}

onMounted(() => {
  getData();
});
</script>
