// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
//  "@vue/cli-plugin-eslint": "^3.0.1",语法检测
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import iView from 'iview'
import i18n from '@/locale'
import config from '@/config'
import importDirective from '@/directive'
import { directive as clickOutside } from 'v-click-outside-x'
import installPlugin from '@/plugin'
import './index.less'
import '@/assets/icons/iconfont.css'
import '@/assets/css/box.less'
import '@/assets/css/boxDistance.less'
import '@/assets/css/oneShop.less'



import TreeTable from 'tree-table-vue'
import VOrgTree from 'v-org-tree'
import 'v-org-tree/dist/v-org-tree.css'

//网络数据请求 参数配置
import apis from '@/axios/api';
Vue.prototype.apis = apis;
//网络数据请求
import http from '@/axios/index';
Vue.prototype.$http = http;
//提示插件引入
import swal from 'sweetalert2';
Vue.prototype.swal = swal;

// 时间转换
import moment from 'moment'
Vue.prototype.moment = moment

//自定义util全局方法
import util from '@/libsCustom/util'
//内封装字典存储与获取
Vue.prototype.util = util;
import dictUtil from '@/libsCustom/dictUtil'
Vue.prototype.dictUtil = dictUtil;

import pagerTit from './components/header'
Vue.component('pagerTit',pagerTit)

import onePage from './components/onePage'
Vue.component('onePage',onePage)


Vue.use(iView, {
  i18n: (key, value) => i18n.t(key, value),
  size:"large"
})
iView.Message.config({
  top: 130,
  duration: 3
});
Vue.use(TreeTable)
Vue.use(VOrgTree)
/**
 * @description 注册admin内置插件
 */
installPlugin(Vue)
/**
 * @description 生产环境关掉提示
 */
Vue.config.productionTip = false
/**
 * @description 全局注册应用配置
 */
Vue.prototype.$config = config
/**
 * 注册指令
 */
import comFun from './libs/comFun.js'
Vue.prototype.AF = comFun;

importDirective(Vue)
Vue.directive('clickOutside', clickOutside)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  i18n,
  store,
  render: h => h(App)
})
