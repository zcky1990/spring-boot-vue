import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/pages/index'
import Login from '@/pages/login'
import Admin from '@/pages/admin'
import User from '@/pages/user'
import UserValidation from '@/pages/user-validation'
import UserSignUp from '@/pages/user-sign-up'
import Article from '@/pages/article'

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
