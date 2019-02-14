import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Pages/User/Hello'
import Admin from '@/components/Pages/Admin/Admin'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/admin',
      name: 'Admin',
      component: Admin
    }
  ]
})
