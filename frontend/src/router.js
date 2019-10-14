import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/pages/index'
import Login from '@/pages/login'
import User from '@/pages/user'
import UserValidation from '@/pages/user-validation'
import UserSignUp from '@/pages/user-sign-up'
import Article from '@/pages/article'
import Search from '@/pages/search'

import Admin from '@/pages/admin/admin'
import Category from '@/pages/admin/category'
import AccessLevel from '@/pages/admin/access-list'
import Roles from '@/pages/admin/roles'
import UsersAdmin from '@/pages/admin/users'

import DetailProfile from '@/pages/detail-profile'

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
      path: '/user',
      name: 'User Profile',
      component: User
    },
    {
      path: '/article/:slug',
      name: 'Article',
      component: Article
    },
    {
      path: '/detail/:id',
      name: 'Detail',
      component: DetailProfile
    },
    {
      path: '/user/validation/:id',
      name: 'Validate user Sign up',
      component : UserValidation
    },
    {
      path: '/sign_up/',
      name: 'New User Page',
      component : UserSignUp
    },
    {
      path: '/search/:query',
      name: 'Search Article Page',
      component : Search
    },
    {
      path: '/admin',
      name: 'Admin',
      component: Admin
    },
    {
      path: '/admin/category',
      name: 'AdminCategory',
      component: Category
    },
    {
      path: '/admin/access_level',
      name: 'AdminAccessLevel',
      component: AccessLevel
    },
    {
      path: '/admin/roles',
      name: 'AdminRoles',
      component: Roles
    },
    {
      path: '/admin/users',
      name: 'AdminUsers',
      component: UsersAdmin
    },
  ]
})
