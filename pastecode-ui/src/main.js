import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import Base from './base'
import Print from 'vue-print-nb'
import ace from 'ace-builds'

Vue.prototype.$axios = axios
Vue.config.productionTip = false
Vue.prototype.$url = "http://127.0.0.1:9090"

Vue.use(ElementUI);
Vue.use(Base);
Vue.use(Print)
Vue.use(ace)


new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
