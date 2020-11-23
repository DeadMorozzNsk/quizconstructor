import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import locale from 'element-ui/lib/locale/lang/ru-RU'
import App from './App.vue'
import quizApi from "./plugins/api";
import AxiosPlugin from 'vue-axios-cors';
import vuex from 'vuex'
import '@babel/polyfill'

Vue.config.productionTip = false

Vue.use(ElementUI, {locale});
Vue.use(AxiosPlugin)
Vue.use(quizApi)
Vue.use(vuex)

Vue.directive('focus', {
    inserted: function (el) {
        el.focus()
    }
})

new Vue({
    render: h => h(App),
}).$mount('#app')
