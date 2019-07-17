import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/pages/Index'
import Admin from '@/pages/Admin'
import Login from '@/pages/Login'
import User from '@/pages/User'

Vue.use(Router)

function guard(to, from, next){
  if(store.state.auth.loggedIn) {
      next(); // allow to enter route
  } else{
      next('/login'); // go to '/login';
  }
}

export default new Router({
  mode: 'history',
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
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/user',
      name: 'User',
      component: User,
      meta: {  
        requiresAuth: true  
      } 
    }
  ]
})
