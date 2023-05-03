import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/",
    name: "Index",
    component: async () => await import("@/layouts/main.vue"),
    redirect: "/home",
    children: [
      {
        path: "/category",
        name: "Category",
        component: async () => await import("@/view/category/index.vue"),
      },
      {
        path: "/home",
        name: "Home",
        component: async () => await import("@/view/HomeCom/Home.vue"),
      },
    ],
  },
  {
    path: "/login",
    name: "Login",
    component: async () => await import("@/view/login/index.vue"),
  },
  {
    path: "/cart",
    name: "Cart",
    component: async () => await import("@/view/cart/CartPage.vue"),
  },
  {
    path: "/user",
    name: "User",
    component: async () => await import("@/view/user/UserPage.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
