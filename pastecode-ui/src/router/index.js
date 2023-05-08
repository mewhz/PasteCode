import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Code from '../views/Code.vue'
import {NotFoundError} from "core-js/internals/dom-exception-constants";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/About.vue')
  },
  {
    path: '/admin',
    name: 'Admin:view',
    redirect: '/admin',
    component: () => import(/* webpackChunkName: "about" */ '../views/Admin.vue'),
    children:[
      {
        path: '/admin/',
        name: 'Home',
        component: () => import('../views/admin/Home')
      },
      {
        path: '/admin/userManagement',
        name: 'UserManagement',
        component: () => import('../views/admin/UserManagement')
      },
      {
        path: '/admin/codeManagement',
        name: 'CodeManagement',
        component: () => import('../views/admin/CodeManagement')
      },
      {
        path: '/admin/logManagement',
        name: 'LogManagement',
        component: () => import('../views/admin/LogManagement')
      },
      {
        path: '/admin/runManagement',
        name: 'RunManagement',
        component: () => import('../views/admin/RunManagement')
      },
      {
        path: '/admin/systemSetting',
        name: 'SystemSetting',
        component: () => import('../views/admin/SystemSetting')
      },
    ]
  },
  {
    /*
      :id 代表传递 id 参数
     */
    path: '/code/id/:id',
    name: 'Code',
    component: Code
  },
  {
    path: '/code/title/:codeTitle',
    name: 'Title',
    component: () => import('../views/Title.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login')
  },
  {
    path: '*',
    name: "NotFound",
    component: () => import('../views/error/NotFound')
  },
  {
    path: '/space/:account',
    name: "Space",
    component: () => import('../views/Space')
  },
  {
    path: '/register',
    name: "Register",
    component: () => import('../views/Register')
  },
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('../views/Admin')
  },
  {
    path: '/list',
    name: 'List',
    component: () => import('../views/List')
  },
  {
    path: '/run',
    name: 'Run',
    component: () => import('../views/Run')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
