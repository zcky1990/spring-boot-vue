import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/pages/Index'
import Admin from '@/components/pages/Admin'
import CKEditor from '@ckeditor/ckeditor5-vue';

Vue.use( CKEditor );
Vue.use(Router)

export default new Router({
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
