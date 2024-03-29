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

export function getComment(params) {
  return Get("/comment/commentList", params);
}

export function addComment(params) {
  return Post("/comment/addcomment", params);
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
  addComment,
  getCateProductAsc,
  getCateProductDesc,
};
