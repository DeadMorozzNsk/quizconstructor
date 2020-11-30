import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/ru-RU'
import '@babel/polyfill'
import App from './App.vue'

import store from "./store";

Vue.config.productionTip = false

Vue.use(ElementUI, {locale});

Vue.directive('focus', {
    inserted: function (el) {
        el.focus()
    }
})

new Vue({
    store,
    render: h => h(App),
}).$mount('#app')
