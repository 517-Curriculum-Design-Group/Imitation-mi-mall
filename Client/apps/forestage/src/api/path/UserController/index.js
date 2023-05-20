import { Get, Post, Delete } from "../../server";

export function postLogin(userobj) {
  return Post("/user/login", userobj);
}

export function postLogout(userid) {
  return Post("/user/logout", userid);
}

export function postRegister(userobj) {
  return Post("/register", userobj);
}

export function getCartList() {
  return Get("/getCartList");
}

export function deleteCartProduct(data) {
  return Delete("/deleteCartProduct", data);
}

export const userApi = {
  postLogin,
  postRegister,
  postLogout,
  getCartList,
  deleteCartProduct,
};
