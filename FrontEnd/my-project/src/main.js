import Vue from 'vue'
import App from './App'
import router from './router'
import jQuery from 'jquery'
import VueResource from 'vue-resource'
// import axios from 'axios'

Vue.use(VueResource)
Vue.http.options.emulateJSON = true
global.jQuery = jQuery
require('bootstrap')
// axios.defaults.baseURL = 'http://localhost:8081'
Vue.config.productionTip = false
/* eslint-disable no-new */
new Vue({  // eslint-disable-line no-new
  el: '#app',
  router: router,
  render: h => h(App)
})
