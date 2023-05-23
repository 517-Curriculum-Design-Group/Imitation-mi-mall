import { Get, Post, Delete,Put } from "../../server";

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

export function deleteCartProduct(params) {
  return Delete("/deleteCartProduct", undefined, params);
}

export function getPersonInfo() {
  return Get("/getPersonInfo");
}

export function updatePersonInfo(obj){
  return Put("/updatePersonInfo",obj)
}

export function getAllAddresses(id) {
  return Get("/getAllAddress",id);
}

export function getLike(){
  return Get("/getFavoriteList")
}

export function uploadAvatar(url){
  return Post("/upload", url);
} 

export function updatePassword(psw){
  return Put("/updatePassword",psw)
}

export const userApi = {
  postLogin,
  postRegister,
  postLogout,
  getCartList,
  deleteCartProduct,
  getPersonInfo,
  getAllAddresses,
  getLike,
  uploadAvatar,
  updatePersonInfo,
  updatePassword,
};
