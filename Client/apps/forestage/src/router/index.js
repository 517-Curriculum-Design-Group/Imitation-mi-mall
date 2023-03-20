import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'Home',
        component: () => import('../view/Home.vue')
    },
    { 
        path: '/loginPage',
        name: 'LoginPage',
        component: () => import('../view/LoginPage.vue')
    },
]

const router = createRouter({
    history:createWebHistory(),
    routes
})

export default router