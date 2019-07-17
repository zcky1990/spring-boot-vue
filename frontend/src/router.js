import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/pages/Index'
import Admin from '@/pages/Admin'

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
    }
  ]
})
