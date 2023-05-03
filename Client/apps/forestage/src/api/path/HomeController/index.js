import { Get } from "../../server";

export function getCategoryList() {
  return Get("/getCategoryList");
}

export function getCateProduct() {
  return Get("/getCateProduct");
}

export const userApi = {
  getCategoryList,
  getCateProduct,
};
