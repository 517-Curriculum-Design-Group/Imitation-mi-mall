import { Post } from "../../server";

export function postLogin(userobj) {
  return Post("/user/login", userobj);
}

export function postLogout(userid) {
  return Post("/user/logout",userid)
}

export function postRegister(userobj) {
  return Post("/register",userobj)
}

export const userApi = {
  postLogin,
  postRegister,
  postLogout,
};
