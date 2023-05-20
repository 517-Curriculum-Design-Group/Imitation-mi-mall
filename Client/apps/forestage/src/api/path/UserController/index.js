import { Post } from "../../server";
import { Get } from "../../server";

export function postLogin(userobj) {
  return Post("/user/login", userobj);
}

export function postLogout(userid) {
  return Post("/user/logout",userid)
}

export function postRegister(userobj) {
  return Post("/register",userobj)
}

export function getPersonInfo(){
  return Get("/getPersonInfo")
}

export function getAllAddresses(){
  return Get("/getAllAddresses")
}

export const userApi = {
  postLogin,
  postRegister,
  postLogout,
  getPersonInfo,
  getAllAddresses
};
