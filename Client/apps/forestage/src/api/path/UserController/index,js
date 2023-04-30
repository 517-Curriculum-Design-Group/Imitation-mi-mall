import { Post } from "../../server";

export function postLogin(userobj) {
  return Post("/user/login", userobj);
}

export function postRegister(user) {
  return Post("/register", user);
}

export function postLogout() {
  return Post("/user/logout");
}

export const userApi = {
  postLogin,
  postRegister,
  postLogout,
};
