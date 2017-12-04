// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import jQuery from 'jquery'
import VueResource from 'vue-resource'
Vue.use(VueResource)
Vue.http.options.emulateJSON = true
global.jQuery = jQuery
require('bootstrap')
Vue.config.productionTip = false
/* eslint-disable no-new */
new Vue({  // eslint-disable-line no-new
  el: '#app',
  router: router,
  render: h => h(App)
})
