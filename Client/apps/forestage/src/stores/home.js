import { defineStore } from "pinia";

// useStore 可以是 useUser、useCart 之类的任何东西
// 第一个参数是应用程序中 store 的唯一 id
export const usehomeStore = defineStore("usehomeStore", {
  // other options...
  state: () => {
    return {
      headProduct: null,
      leftCategories: null,
      lowerProducts: null,
    };
  },
  actions: {
    setHeaderProducts(headProduct) {
      this.headProduct = headProduct;
    },
    setLeftCategories(leftCategories) {
      this.leftCategories = leftCategories;
    },
    setLowerProducts(lowerProducts) {
      this.lowerProducts = lowerProducts;
    },
  },
  persist: {
    key: "usehomeStore",
    storage: window.sessionStorage,
    path: ["headProduct", "leftCategories", "lowerProducts"],
  },
});
