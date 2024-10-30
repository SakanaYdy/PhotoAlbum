import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'login',
    // component: HomeView
    component: () => import(/* webpackChunkName: "about" */ '../views/LoginView.vue')
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/home',
    name: 'home',
    // component: () => import(/* webpackChunkName: "about" */ '../views/LoginView.vue')
    component: HomeView
  },
  {
    path: '/register',
    name: 'register',
    component: () => import(/* webpackChunkName: "about" */ '../views/RegisterView.vue')
  },
  {
    path: '/person',
    name: 'person',
    component: () => import(/* webpackChunkName: "about" */ '../views/PersonPhotoView.vue')
  },
  {
    path: '/notice',
    name: 'notice',
    component: () => import(/* webpackChunkName: "about" */ '../views/NoticeView.vue')
  },
  {
    path: '/manage',
    name: 'manage',
    component: () => import(/* webpackChunkName: "about" */ '../views/ManageView.vue')
  },
  {
    path: '/favorite',
    name: 'favorite',
    component: () => import(/* webpackChunkName: "about" */ '../views/FavoriteView.vue')
  },
  {
    path: '/manageUser',
    name: 'manageUser',
    component: () => import(/* webpackChunkName: "about" */ '../views/ManageUser.vue')
  },
  {
    path: '/manageLabel',
    name: 'manageLabel',
    component: () => import(/* webpackChunkName: "about" */ '../views/ManageLabel.vue')
  },
  {
    path: '/personalInfo',
    name: 'personalInfo',
    component: () => import(/* webpackChunkName: "about" */ '../views/PersonalInfo.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router

// 后端接口地址
// http://localhost:8085/v3/api-docs/default
