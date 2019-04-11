let Met={
}

Met.TabTit=(v)=>{
  v.tabTit.forEach((it,index)=>{
    it.title = v.$t(it.tit)
  })
}

Met.getBro_H =  ()=>{
  return document.documentElement.clientHeight
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
