import { userApi } from "./path/UserController";
import { homeApi } from "./path/HomeController";
import { prodApi } from "./path/ProductControlle";
import { orderApi } from "./path/Ordercontroller";

export const api = {
  ...userApi,
  ...homeApi,
  ...prodApi,
  ...orderApi,
};
