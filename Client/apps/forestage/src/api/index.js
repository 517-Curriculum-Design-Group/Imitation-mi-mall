import { userApi } from "./path/UserController";
import { homeApi } from "./path/HomeController";
import { prodApi } from "./path/ProductControlle";

export const api = {
  ...userApi,
  ...homeApi,
  ...prodApi,
};
