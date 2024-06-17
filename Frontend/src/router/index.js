import { createRouter, createWebHistory } from 'vue-router'
import AuthPage from '../views/AuthPage.vue'
import MainPage from "@/views/MainPage.vue";
import NotFound404 from "@/views/errors/NotFound404.vue";
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/:pathMatch(.*)',
      name: '404',
      component: () => NotFound404
    },
    {
      path: '/auth',
      name: 'auth',
      component: () => AuthPage
    },
    {
      path: '/',
      name: 'index',
      component: () => AuthPage
    },
    {
      path: '/main',
      name: 'main',
      component: () => MainPage,
    },
],
})

export default router


