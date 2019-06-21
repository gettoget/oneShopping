import Vue from 'vue'
import App from './App.vue'
import router from './router'
import apis from './axios/config';
import http from './axios/index';
Vue.prototype.apis = apis;
Vue.prototype.$http = http;
import { NavBar } from 'vant';
import { Toast } from 'vant';
import { List } from 'vant';
import { Cell, CellGroup } from 'vant';
import { Row, Col } from 'vant';
import { Icon } from 'vant';
import { Field } from 'vant';
import { Button } from 'vant';

Vue.use(Button);
Vue.use(Field);
Vue.use(Icon);
Vue.use(Row).use(Col);
Vue.use(Cell).use(CellGroup);
Vue.use(List);
Vue.use(Toast);
Vue.use(NavBar);
Vue.config.productionTip = false
//
new Vue({
  router,
  render: h => h(App),
}).$mount('#app')

// new Vue({
//   el: '#app',
//   router: router,
//   // store:store,
//   components: { App },
//   template: '<App/>'
// })
