import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/",
    name: "Index",
    component: () => import("@/layouts/main.vue"),
  },
  {
    path: "/login",
    name: "Login",
    component: () => import("@/view/login/LoginPage.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
