import { Get } from "../../server";

export function getCategoryList() {
  return Get("/getCategoryList");
}

export function getCateProduct() {
  return Get("/getCateProduct");
}

export function getHeaderProducts() {
  return Get("/getHeaderProducts");
}

export function getLeftCategories() {
  return Get("/getLeftCategories");
}

export function getLowerProducts() {
  return Get("/getLowerProducts");
}

export function getCategorybyName(){
  return 
}
export const homeApi = {
  getCategoryList,
  getCateProduct,
  getHeaderProducts,
  getLeftCategories,
  getLowerProducts,
};
