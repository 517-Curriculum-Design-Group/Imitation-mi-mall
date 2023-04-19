import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/",
    name: "Index",
    component: () => import("@/layouts/main.vue"),
    redirect: '/home',
    children: [
      {
        path: "/home",
        name: "Home",
        component: () => import("@/view/Home.vue"),
      },
    ],
  },
  {
    path: "/login",
    name: "Login",
    component: () => import("@/view/login/LoginPage.vue"),
  },
  {
    path: "/cart",
    name: "Cart",
    component: () => import("@/view/cart/CartPage.vue"),
  },
  {
    path: "/user",
    name: "User",
    component: () => import("@/view/user/UserPage.vue")
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
