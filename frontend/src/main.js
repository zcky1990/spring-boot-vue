import Vue from 'vue'
import App from './App.vue'
import router from './router'
import VueSession from 'vue-session'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css' 

Vue.use(Vuetify)
Vue.config.productionTip = false
Vue.use(VueSession)
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
