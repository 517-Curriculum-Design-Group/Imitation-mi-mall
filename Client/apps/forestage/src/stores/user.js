import { defineStore } from "pinia";

const userInfo = JSON.parse(window.sessionStorage.getItem("userId"));

// useStore 可以是 useUser、useCart 之类的任何东西
// 第一个参数是应用程序中 store 的唯一 id
export const userStore = defineStore("user", {
  // other options...
  state: () => {
    return {
      userInfo: userInfo || {},
    };
  },
  actions: {
    getUserInfo() {
      return this.userInfo;
    },
    setUserInfo(user) {
      this.userInfo = user;
    },
    deleteUserInfo() {
      this.userInfo = null;
    },
  },
  persist: {
    key: "userStore",
    storage: window.sessionStorage,
    path: ["userInfo"],
  },
});
