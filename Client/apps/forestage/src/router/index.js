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
    children:[
      {
        path: "like",
        name: "Like",
        component: async () => await import("@/view/user/Like.vue")
      },
      {
        path: "order",
        name: "Order",
        component: async () => await import("@/view/user/MyOrder.vue")
      },
      {
        path: "comment",
        name: "Comment",
        component: async () => await import("@/view/user/Comment.vue")
      },
      {
        path: "main",
        name: "Main",
        component: async () => await import("@/view/user/UserMain.vue")
      },
      {
        path: "news",
        name: "News",
        component: async () => await import("@/view/user/News.vue")
      },
      {
        path: "address",
        name: "Address",
        component: async () => await import("@/view/user/Address.vue")
      },
      {
        path: "info",
        name: "Info",
        component: async () => await import("@/view/user/UserInfo.vue")
      },
      {
        path: "password",
        name: "Password",
        component: async () => await import("@/view/user/Password.vue")
      },
      {
        path: "deleteServer",
        name: "deleteServer",
        component: async () => await import("@/view/user/DeleteServer.vue")
      }
    ],
  },
  {
    path:"/page",
    name: "Page",
    component: async () => await import("@/view/pagination/Pagination.vue")
  },
  {
    path:"/product",
    name: "Product",
    component: async () => await import("@/view/productDetail/ProductDetail.vue")
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
