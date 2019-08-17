import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/pages/Index'
import Login from '@/pages/Login'
import Admin from '@/pages/Admin'
import User from '@/pages/User'
import UserValidation from '@/pages/UserValidation'
import UserSignUp from '@/pages/UserSignUp'
import Article from '@/pages/Article'

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
      path: '/login',
      name: 'Login',
      component: Login
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
    },
    {
      path: '/article/:slug',
      name: 'Article',
      component: Article
    },
    {
      path: '/user/validation/',
      name: 'Validate user Sign up',
      component : UserValidation
    },
    {
      path: '/sign_up/',
      name: 'New User Page',
      component : UserSignUp
    }
  ]
})
