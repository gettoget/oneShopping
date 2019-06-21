import config from '@/config'
/**
 *字段收索规则
 * keyGte  大于等于
 * keyLte  小于等于
 * keyIn   多值查询  “,”  分割
 * keyLike 模糊查询
 * key     精确查询
 **/



const ajaxUrl = process.env.NODE_ENV === 'development'//development开发环境
  ? ' http://119.23.242.234:8088'
  : ' http://119.23.242.234:8088'
const fileUrl = "http://119.23.242.234:9092/"
export default {
  NETWORK_ERR_STR: "网络加载异常!",
  url:ajaxUrl,
  GETFILEURL:fileUrl,
  UPFILE:ajaxUrl+'/upload?targetPath=',
  LOGIN:"/login",
  USER: {
    QUERY: '/api/yh/pager',//用户管理,查询
    ADD: '/api/yh/save', //新增用户
    CHANGE: '/api/yh/update',//修改
    DELE: '/api/yh/remove',//  api/yh/remove/{pkid}  pkid为用户的id 例 :  /api/yh/remove/1   即可删除用户id 为 1 的用户信息
  },
  ORDER:{
    QUERY:'/api/order/pager',//订单查询

  }
}
