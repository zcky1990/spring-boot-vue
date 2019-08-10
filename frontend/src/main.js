import Vue from 'vue'
import Vuetify from 'vuetify'
import VueSession from 'vue-session'
import 'vuetify/dist/vuetify.min.css' 
import App from './App.vue'
import router from './router'
import CKEditor from '@ckeditor/ckeditor5-vue';
import '@fortawesome/fontawesome-free/css/all.css' 


require('./assets/sass/main.scss');

Vue.use(Vuetify)
Vue.config.productionTip = false
Vue.use(VueSession)
Vue.use(CKEditor)
new Vue({
  icons: {
    iconfont: 'fa',
  },
  router,
  render: h => h(App)
}).$mount('#app')
