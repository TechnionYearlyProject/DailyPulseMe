import Vue from 'vue'
import App from './App'
import router from './router'
import jQuery from 'jquery'
import VueResource from 'vue-resource'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import VeeValidate from 'vee-validate';

Vue.use(VeeValidate);
Vue.use(BootstrapVue);
Vue.use(VueResource)
import VueFormWizard from 'vue-form-wizard'
import 'vue-form-wizard/dist/vue-form-wizard.min.css'
Vue.use(VueFormWizard)
Vue.http.options.emulateJSON = true
// Vue.http.headers.common['Access-Control-Allow-Headers'] = 'access-control-allow-headers'
// Vue.http.headers.common['Content-Type'] = 'application/json'
// Vue.http.headers.common['Access-Control-Allow-Origin'] = '*'
// Vue.http.headers.common['Accept'] = 'application/json, text/plain, */*'

global.jQuery = jQuery
// require('bootstrap')
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({  // eslint-disable-line no-new
  el: '#app',
  router: router,
  render: h => h(App)
})
