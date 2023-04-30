import { Get } from "../../server";

export function postLogin(userobj) {
    return Get("/user/login", userobj);
}

export const userApi = {
    postLogin,
};