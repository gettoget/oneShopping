<template>
  <div>
    <Table :columns="columns1" :data="data1" size="small"></Table>
  </div>
</template>

<script>
  export default {
    name: "homeTab",
    data(){
      return{


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
            title: '姓名',
            key: 'name'
          },
          {
            title: '手机号码',
            key: 'phone'
          },
          {
            title: '中奖号码',
            key: 'shop'
          },
          {
            title: '中奖商品',
            key: '',
            render:(h,p)=>{

            }
          },
          {
            title: '中奖时间',
            key: 'time'
          }
        ],
        data1: [
          {
            name: 'John Brown',
            phone: '131****1234',
            shop: 'New York No. 1 Lake Park',
            time: '2016-10-03'
          },{
            name: 'John Brown',
            phone: '131****1234',
            shop: 'New York No. 1 Lake Park',
            time: '2016-10-03'
          },{
            name: 'John Brown',
            phone: '131****1234',
            shop: 'New York No. 1 Lake Park',
            time: '2016-10-03'
          },{
            name: 'John Brown',
            phone: '131****1234',
            shop: 'New York No. 1 Lake Park',
            time: '2016-10-03'
          },{
            name: 'John Brown',
            phone: '131****1234',
            shop: 'New York No. 1 Lake Park',
            time: '2016-10-03'
          },{
            name: 'John Brown',
            phone: '131****1234',
            shop: 'New York No. 1 Lake Park',
            time: '2016-10-03'
          },{
            name: 'John Brown',
            phone: '131****1234',
            shop: 'New York No. 1 Lake Park',
            time: '2016-10-03'
          },{
            name: 'John Brown',
            phone: '131****1234',
            shop: 'New York No. 1 Lake Park',
            time: '2016-10-03'
          },{
            name: 'John Brown',
            phone: '131****1234',
            shop: 'New York No. 1 Lake Park',
            time: '2016-10-03'
          },{
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

<style scoped>

</style>
