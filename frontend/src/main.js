import Vue from 'vue'
import vuetify from '@/plugins/vuetify' // path to vuetify export
import VueSession from 'vue-session'
import 'vuetify/dist/vuetify.min.css' 
import App from './App.vue'
import router from './router'
import CKEditor from '@ckeditor/ckeditor5-vue';
import '@fortawesome/fontawesome-free/css/all.css' 
import  Util from "./util";
import FBSignInButton from 'vue-facebook-signin-button'
import GSignInButton from 'vue-google-signin-button'

require('./assets/sass/main.scss');

Vue.config.productionTip = false
Vue.use(VueSession)
Vue.use(CKEditor)
Vue.use(Util)
Vue.use(FBSignInButton)
Vue.use(GSignInButton)

new Vue({
  vuetify: vuetify,
  icons: {
    iconfont: 'fa',
  },
  router,
  render: h => h(App)
}).$mount('#app')
