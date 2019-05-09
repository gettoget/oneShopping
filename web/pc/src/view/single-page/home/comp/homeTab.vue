<template>
  <div>
    <Table :columns="columns1" :data="data1" size="small" height="445"></Table>
  </div>
</template>

<script>
  import iphone from './iphone.jpg'

  export default {
    name: "homeTab",
    data() {
      return {


        // 序号
        // 开奖商品
        // 名称
        // 图
        // 时间
        // 号码
        // 姓名
        // 电话
        columns1: [
          {
            title: "#",
            type: "index",
            width: 60
          },
          {
            title: '中奖商品',
            key: '',
            width:120,
            align:'center',
            render:(h,p)=>{
              return h('div',{
                style:{
                  textAlign:'center',
                  padding:'6px 0'
                }
              },[
                h('Avatar',{
                  props:{
                    size:"large",
                    shape:"square",
                    src:iphone
                  }
                }),
                h('div','iphonX MAX')

              ])

            }
          },
          {
            title: '中奖时间',
            width:100,
            align:'center',
            key: 'time',
            render:(h,p)=>{
              return h('div',[
                h('div','2015-01-01'),
                h('div','15:00:00'),
                h('div','1234')
              ])
            }
          },
          {
            title:"中奖号码",
            render:(h,p)=>{
              return h('div','12345614314324134134123')
            }
          },
          {
            title: '姓名',
            align:'center',
            key: 'name',
          },
          {
            title: '手机号码',
            align:'center',
            key: 'phone'
          }
        ],
        data1: [
          {
            name: 'John Brown',
            phone: '131****1234',
            shop: 'New York No. 1 Lake Park',
            time: '2016-10-03 15:00:00 0000'
          }, {
            name: 'John Brown',
            phone: '131****1234',
            shop: 'New York No. 1 Lake Park',
            time: '2016-10-03'
          }, {
            name: 'John Brown',
            phone: '131****1234',
            shop: 'New York No. 1 Lake Park',
            time: '2016-10-03'
          }, {
            name: 'John Brown',
            phone: '131****1234',
            shop: 'New York No. 1 Lake Park',
            time: '2016-10-03'
          }, {
            name: 'John Brown',
            phone: '131****1234',
            shop: 'New York No. 1 Lake Park',
            time: '2016-10-03'
          }, {
            name: 'John Brown',
            phone: '131****1234',
            shop: 'New York No. 1 Lake Park',
            time: '2016-10-03'
          }, {
            name: 'John Brown',
            phone: '131****1234',
            shop: 'New York No. 1 Lake Park',
            time: '2016-10-03'
          }, {
            name: 'John Brown',
            phone: '131****1234',
            shop: 'New York No. 1 Lake Park',
            time: '2016-10-03'
          }, {
            name: 'John Brown',
            phone: '131****1234',
            shop: 'New York No. 1 Lake Park',
            time: '2016-10-03'
          }, {
            name: 'John Brown',
            phone: '131****1234',
            shop: 'New York No. 1 Lake Park',
            time: '2016-10-03'
          }
        ]
      }
    }
  }
</script>
