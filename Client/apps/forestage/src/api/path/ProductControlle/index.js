import { Get, Post, Delete } from "../../server";

export function getProductDetail(id) {
  let params = {
    productId: id,
  };
  return Get(`/getProductDetail/${params.productId}`);
}

export function getProductPrice(data) {
  return Post(`/getProductPrice`, data);
}

export function addSkuToCart(body) {
  return Post("/addSkuToCart", body);
}

export function addProductToFavorite(body) {
  return Post("/addProductToFavorite", body);
}

export function deleteLike(body) {
  return Delete("/deleteProductToFavorite", body);
}

export function getCategoryList() {
  return Get("/getCategoryList");
}

export function getCateProduct(params) {
  return Get("/getCateProduct", params);
}

export function getComment(id, num, size) {
  return Get("/commentList", id, num, size);
}

export function getCateProductAsc(body) {
  return Post("/getCateProductAsc", body);
}

export function getCateProductDesc(body) {
  return Post("/getCateProductDesc", body);
}

export const prodApi = {
  getProductDetail,
  getProductPrice,
  addSkuToCart,
  addProductToFavorite,
  deleteLike,
  getCategoryList,
  getCateProduct,
  getComment,
  getCateProductAsc,
  getCateProductDesc,
};
