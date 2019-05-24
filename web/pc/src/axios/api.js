import config from '@/config'
// const ajaxUrl = process.env.NODE_ENV === 'development' ? config.baseUrl.dev : config.baseUrl.pro
const ajaxUrl = process.env.NODE_ENV === 'development'
  ? ' http://119.23.242.234:8088'
  : ' http://119.23.242.234:8088'
const fileUrl = "http://119.23.242.234:9092/"
export default {
  NETWORK_ERR_STR: "网络加载异常!",
  url:ajaxUrl,
  READFILE:fileUrl,

  LOGIN:"/login"
}
