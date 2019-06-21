import axios from 'axios'
import router from '../router/index';
import qs from 'qs';
//订单分配权限
let httpInstance = axios.create({
  baseURL: 'http://119.23.242.234:8088',
  // baseURL: 'http://47.98.39.45:9099',
  timeout: 300000,
  headers: {'Content-Type':'application/x-www-form-urlencoded'},
  withCredentials:true
});
// 添加请求拦截器 数据请求之前
httpInstance.interceptors.request.use((config) => {
  config.headers.imei = "008796749369364";
  config.headers.token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3Y3BtcyIsImF1ZCI6IndjcG1zIiwibG9naW5OYW1lIjoiMTU2MDc3NTYxNTQ5NiIsImlzcyI6IndjcG1zIiwidXNlcklkIjoiNTY3MjkzNDE4NDM1MjQ4MTI4In0.ieviYth1WH0vvP9Cv0fBe-_ru6Am9POQcazBtpKgI6w";
  config.headers.userId = "567293418435248128";
  config.headers.lang = "en_US";
  var headers = config.headers;
  var contentType = headers['Content-Type'];
  if (contentType == "application/x-www-form-urlencoded"){
    config.data = qs.stringify(config.data);
    try{
      //如果是数组对象，将转换出来的数组字符串中的[]关键字替换，这样方便后台接收数据
      config.data = config.data.replace(/(%5B\d%5D)/g,"");
    }catch(e){

    }
  }
  // 在发送请求之前做些什么
  // if(Cookies.get('user')){
  //   let user = JSON.parse(Cookies.get('user'))
  //   if (user != null){
  //     let userInfo = JSON.parse(user.userInfo)
  //     config.headers.token = user.token;
  //     config.headers.userId = userInfo.sfzhm;
  //   }
  // }
  return config;
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
});

// 添加响应拦截器 数据响应之后
httpInstance.interceptors.response.use((response) => {
  var v = this
//     对响应数据做点什么
  return response.data;
}, function (error) {
  // 对响应错误做点什么
  return Promise.reject(error);
});
export default httpInstance;
