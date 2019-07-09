// Vue.prototype.AF = comFun;
import { localRead,localSave, } from '@/libs/util'
let Met={
}

Met.Format_Num = (s, n)=>{
  n = n > 0 && n <= 20 ? n : 2;
  s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
  var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
  t = "";
  for (i = 0; i < l.length; i++) {
    t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
  }
  return t.split("").reverse().join("") + "." + r;
}

Met.WindowListener = (callback)=>{
  // window.onresize = function(val) {
  //   // 浏览器窗口变化后需要做的事情
  //   //
  //   //
  // }

  window.addEventListener('resize', function(val) {
    // 变化后需要做的事
    callback && callback()
  })
}

Met.getBody_W = ()=>{
  return document.body.clientWidth
}

Met.getPageHeight= () => {//获取浏览器页面高度
  var windowHeight = window.innerHeight
  return windowHeight
}
Met.getDom_H = (id)=>{
  return document.getElementById(id).offsetHeight
}
Met.getDom_W = (id)=>{
  return document.getElementById(id).offsetWidth
}
/**
 网页可见区域宽： document.body.clientWidth
 网页可见区域高： document.body.clientHeight
 网页可见区域宽： document.body.offsetWidth (包括边线的宽)
 网页可见区域高： document.body.offsetHeight (包括边线的高)
 网页正文全文宽： document.body.scrollWidth
 网页正文全文高： document.body.scrollHeight
 网页被卷去的高： document.body.scrollTop
 网页被卷去的左： document.body.scrollLeft
 元素的实际高度：document.getElementById("div").offsetHeight
 元素的实际宽度：document.getElementById("div").offsetWidth
 元素的实际距离左边界的距离：document.getElementById("div").offsetLeft
 元素的实际距离上边界的距离：document.getElementById("div").offsetTop
 */

export default Met
/**
{

  getRandom(val) {//取随机数
    let line = 1
    if (val && val > 1) {
      line = val
    }
    let num = ''
    for (var i = 0; i < line; i++) {
      num += Math.floor(Math.random() * 10)
    }
    return num
  },
  DX: (n) => {//金额大写转换
    if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
      return "数据非法";
    var unit = "仟佰拾亿仟佰拾万仟佰拾元角分", str = "";
    n += "00";
    var p = n.indexOf('.');
    if (p >= 0)
      n = n.substring(0, p) + n.substr(p + 1, 2);
    unit = unit.substr(unit.length - n.length);
    for (var i = 0; i < n.length; i++)
      str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
    return str.replace(/零(仟|佰|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
  },
  getTime: () => {
    var NowDate = new Date()
    const Year = NowDate.getFullYear()
    const Month = NowDate.getMonth() + 1
    const Day = NowDate.getDate()
    const Hours = NowDate.getHours()
    const Minutes = NowDate.getMinutes()
    const Seconds = NowDate.getSeconds()
    // if (Month < 10) {
    //   Month = '0' + Month
    // }
    // if (Day < 10) {
    //   Day = '0' + Day
    // }
    // function  minNum(num) {
    //   if(num<10){
    //     num = '0' + num
    //   }
    // }
    function minNum(val) {
      // console.log(val);
      if (val < 10) {
        return '0' + val
      } else {
        return val
      }
    }

    let time = Year + '-' + minNum(Month) + '-' + minNum(Day) + ' ' + minNum(Hours) + ':' + minNum(Minutes) + ':' + minNum(Seconds)
    return time
  },
  trimTime: (val) => {
    if (val) {
      var newDate = new Date(val);
    } else {
      var newDate = new Date();
    }
    var newDate = new Date(val);
    let Year = newDate.getFullYear();
    let Month = newDate.getMonth() + 1;
    let Day = newDate.getDate();
    let Hours = newDate.getHours();
    let Minutes = newDate.getMinutes();
    let Seconds = newDate.getSeconds();

    function minNum(num) {
      if (num < 10) {
        return '0' + num
      } else {
        return num
      }
    }

    // if (Month < 10) {
    //   Month = '0' + Month
    // }
    // if (Day < 10) {
    //   Day = '0' + Day
    // }
    let time = Year + '-' + minNum(Month) + '-' + minNum(Day) + ' ' + minNum(Hours) + ':' + minNum(Minutes) + ':' + minNum(Seconds)
    return time
  },
  trimDate: (val) => {
    if (val) {
      var newDate = new Date(val);
    } else {
      var newDate = new Date();
    }
    let Year = newDate.getFullYear();
    let Month = newDate.getMonth() + 1;
    let Day = newDate.getDate();
    if (Month < 10) {
      Month = '0' + Month
    }

    if (Day < 10) {
      Day = '0' + Day
    }
    let time = Year + '-' + Month + '-' + Day
    return time
  },
//  Date 转换 时间戳
  Num_Date(time) {
    return Date.parse(new Date(time))
  },
  getBrowserTyp() {
    var Typ = navigator.userAgent; //取得浏览器的userAgent字符串
    return Typ
    //判断是否Opera浏览器
    // if (userAgent.indexOf("Opera") > -1) {
    //     return "Opera"
    // };
    // //判断是否Firefox浏览器
    // if (userAgent.indexOf("Firefox") > -1) {
    //     return "FF";
    // }
    // //判断是否chorme浏览器
    // if (userAgent.indexOf("Chrome") > -1){
		// return "Chrome";
    // }
    // //判断是否Safari浏览器
    // if (userAgent.indexOf("Safari") > -1) {
    //     return "Safari";
    // }
    // //判断是否IE11浏览器 !!
    // if (userAgent.indexOf("Trident") > -1) {
    //     return "IE";
    // }
    // //判断是否IE10浏览器
    // if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && userAgent.indexOf("Opera") == -1) {
    //     return "IE";
    // }
    // //判断是否Edge浏览器
    // if (userAgent.indexOf("Trident") > -1) {
    //     return "Edge";
    // };

  },
  getYear() {
    let date = new Date();
    let year = date.getFullYear();
    return year
  },
  getMonth() {
    let date = new Date();
    let month = date.getMonth();
    return month + 1
  }
}
*/
/*
this.$nextTick(() => {
        $(document).keypress(function (event) {//keypress 单件
          console.log(event.keyCode);
          // $(document).keyup(function(event){  keyup 组合件
          if (event.keyCode === 44) {
            alert('你按下了<,');
          }else if (event.keyCode === 45) {
            alert('你按下了>.');
          }else if (event.keyCode === 47) {
            alert('你按下了?/');
          }else if (event.keyCode === 112) {
            alert('你按下了__p');
          }
        });
      })
* */
