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
    component: Home,
    meta: {
      title: '贴代码'
    }
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
        component: () => import('../views/admin/Home'),
        meta: {
          title: '系统管理'
        }
      },
      {
        path: '/admin/userManagement',
        name: 'UserManagement',
        component: () => import('../views/admin/UserManagement'),
        meta: {
          title: '用户管理'
        }
      },
      {
        path: '/admin/codeManagement',
        name: 'CodeManagement',
        component: () => import('../views/admin/CodeManagement'),
        meta: {
          title: '代码管理'
        }
      },
      {
        path: '/admin/logManagement',
        name: 'LogManagement',
        component: () => import('../views/admin/LogManagement'),
        meta: {
          title: '日志管理'
        }
      },
      {
        path: '/admin/runManagement',
        name: 'RunManagement',
        component: () => import('../views/admin/RunManagement'),
        meta: {
          title: '运行管理'
        }
      },
    ]
  },
  {
    /*
      :id 代表传递 id 参数
     */
    path: '/code/id/:id',
    name: 'Code',
    component: Code,
    meta: {
      title: '查看代码'
    }
  },
  {
    path: '/code/title/:codeTitle',
    name: 'Title',
    component: () => import('../views/Title.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login'),
    meta: {
      title: '用户登录'
    }
  },
  {
    path: '*',
    name: "NotFound",
    component: () => import('../views/error/NotFound'),
    meta: {
      title: '404 Not Found'
    }
  },
  {
    path: '/space/:account',
    name: "Space",
    component: () => import('../views/Space'),
    meta: {
      title: '个人空间'
    }
  },
  {
    path: '/register',
    name: "Register",
    component: () => import('../views/Register'),
    meta: {
      title: '用户注册'
    }
  },
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('../views/Admin')
  },
  {
    path: '/list',
    name: 'List',
    component: () => import('../views/List'),
    meta: {
      title: '列表'
    }
  },
  {
    path: '/run',
    name: 'Run',
    component: () => import('../views/Run'),
    meta: {
      title: '运行'
    }
  },
  {
    path: '/diff',
    name: 'Diff',
    component: () => import('../views/Diff'),
    meta: {
      title: '比对'
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
