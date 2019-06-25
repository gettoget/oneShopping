import Vue from 'vue'
import App from './App.vue'
import Mint from 'mint-ui'
import 'mint-ui/lib/style.css'


import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(VueAxios, axios)

Vue.use(Mint);
Vue.config.productionTip = false

Date.prototype.format = function (format) {
  var o = {
    "M+": this.getMonth() + 1, //month
    "d+": this.getDate(),    //day
    "h+": this.getHours(),   //hour
    "m+": this.getMinutes(), //minute
    "s+": this.getSeconds(), //second
    "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
    "S": this.getMilliseconds() //millisecond
  }
  if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
      (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o) if (new RegExp("(" + k + ")").test(format))
    format = format.replace(RegExp.$1,
        RegExp.$1.length == 1 ? o[k] :
            ("00" + o[k]).substr(("" + o[k]).length));
  return format;
}
new Vue({
  render: h => h(App),
}).$mount('#app')
