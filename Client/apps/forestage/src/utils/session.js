import { userStore } from "@/stores/user";
import pinia from "../stores/store";

const store = userStore(pinia);

export const setSession = (key, value) => {
  window.sessionStorage.setItem(key, value);
};

export const getSession = (key) => {
  return window.sessionStorage.getItem(key);
};

export const removeSession = (key) => {
  return window.sessionStorage.removeItem(key);
};

export const clearSession = () => {
  return window.sessionStorage.clear();
};

export const isLogin = () => {
  return store.getUserInfo() && getSession("token");
};

export const session = {
  setSession,
  getSession,
  removeSession,
  clearSession,
  isLogin,
};
