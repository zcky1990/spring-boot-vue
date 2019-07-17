import Vue from 'vue'
import App from './App.vue'
import router from './router'
import VueSession from 'vue-session'
//import BootstrapVue from 'bootstrap-vue'
//import 'bootstrap/dist/css/bootstrap.css'
//import 'bootstrap-vue/dist/bootstrap-vue.css'
//require('./assets/sass/main.scss');

Vue.config.productionTip = false

Vue.use(VueSession)
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
