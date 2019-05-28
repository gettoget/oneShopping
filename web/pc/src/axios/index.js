import axios from 'axios';
import router from '../router/index';
import qs from 'qs';
import iView from 'iview'
import ajaxUrl from './api'

import {getToken,getUserId} from '@/libs/util'

// 订单分配权限
let url = ajaxUrl.url

let httpInstance = axios.create({
  baseURL: url,
  timeout: 30000,
  headers: {'Content-Type': 'application/x-www-form-urlencoded'},
  withCredentials: true
});
httpInstance.url = url;
// 添加请求拦截器 数据请求之前
httpInstance.interceptors.request.use((config) => {
  // console.log('数据拦截',config);
  if (config.method == 'get') {
    if (config.url.indexOf('?') < 0) {
      config.url += '?t=' + new Date().getTime()
    } else {
      config.url += '&t=' + new Date().getTime()
    }
  } else if (config.method == 'post' && ((config.params && config.params.timers == undefined) || (config.data && config.data.timers == undefined))) {
    if (config.params) {
      config.params['timers'] = new Date().getTime()
    } else if (config.data) {
      config.data['timers'] = new Date().getTime()
    }
  }


  var headers = config.headers;
  var contentType = headers['Content-Type'];

  if (contentType == 'application/x-www-form-urlencoded') {
    config.data = qs.stringify(config.data);
    try {
      // 如果是数组对象，将转换出来的数组字符串中的[]关键字替换，这样方便后台接收数据
      config.data = config.data.replace(/(%5B\d%5D)/g, '');
    } catch (e) {

    }
  }
  // 在发送请求之前做些什么
  if(getToken()){
    config.headers.token = getToken();
  }
  if(getUserId()){
    config.headers.userid = getUserId();
  }
  return config;
}, function (error) {
  console.log(error);
  // 对请求错误做些什么
  return Promise.reject(error);
  // iView.Spin.hide()
});

// 添加响应拦截器 数据响应之后
httpInstance.interceptors.response.use((response) => {
  var v = this;
  // 对响应数据做点什么
  if (response.status === 200) {
    if (response.data.code === 999) {
      router.push({name: 'login'})
      iView.Message.info(response.data.message + ',请重新登录');
      return
    }
    if(response.data.code != 200){
      iView.Message.error(response.data.message);
    }
    return response.data;
  } else {
    router.push({name: 'error-500'});
  }
}, function (error) {
  // iView.Spin.hide()
  // 对响应错误做点什么
  // if (!Cookies.get('result')) {
  // 	router.push({name: 'error-500'});
  // } else if (Cookies.get('result')) {
  // 	router.push({name: 'errorpage_500'});
  // }
  iView.Message.error('http-error');
  return Promise.reject(error);
});
export default httpInstance;
