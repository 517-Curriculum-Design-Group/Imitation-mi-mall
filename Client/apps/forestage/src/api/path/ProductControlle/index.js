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

export const prodApi = {
  getProductDetail,
  getProductPrice,
};
