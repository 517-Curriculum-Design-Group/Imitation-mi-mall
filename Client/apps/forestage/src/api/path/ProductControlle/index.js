import { Get, Post } from "../../server";

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

export const prodApi = {
  getProductDetail,
  getProductPrice,
  addSkuToCart,
  addProductToFavorite,
};
