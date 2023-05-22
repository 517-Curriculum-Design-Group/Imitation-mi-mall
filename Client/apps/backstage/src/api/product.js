import axios from "~/axios";

export function getproductlist(pageNum, pageSize) {
  return axios.get("/getProductList", {
    params: {
      pageNum,
      pageSize,
    },
  });
}

export function addnewproduct(Product) {
  return axios.post("/addNewProduct", Product);
}

//商品上下架
//body:{productId,status}

export function updateproductstatus(Product) {
  return axios.post("/ModifyProductStatus", Product);
}

//补货
//body{ List<Integer> skuIds，List<Integer> stocks }
export function addstock(Product) {
  return axios.post("/addProductStock", Product);
}

//查看商品sku
export function getproductsku(productId) {
  return axios.get(`/getProductSku/${productId}`);
}

//修改商品SKU接口
//body{productId,List<ModifySku> skuList,List<ModifySkuDetail> skuDetailList}
export function updateproductsku(Product) {
  return axios.post("/modifyProductSku", Product);
}

//删除商品
//body{数组}
export function deletproductlist(productId) {
  return axios.delete(
    "/deleteProduct",
    {
      data: productId,
    },

    {
      headers: {
        "Content-Type": "application/json",
      },
    }
  );
}

export function deleteSingleProduct(productId) {
  return axios.delete("/deleteSingleProduct", {
    data: productId,
  });
}
