import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Code from '../views/Code.vue'

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
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
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
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
