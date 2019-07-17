import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/pages/Index'
import Admin from '@/pages/Admin'
import User from '@/pages/User'

Vue.use(Router)
export default new Router({
  mode:"history",
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index
    },
    {
      path: '/admin',
      name: 'Admin',
      component: Admin
    },
    {
      path: '/user',
      name: 'User',
      component: User
    }
  ]
})
