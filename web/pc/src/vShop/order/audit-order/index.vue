<template>
   <div>
     <pager-tit title="订单审核"></pager-tit>
     <Row style="position: relative;">
       <Table
         size='large' stripe
         :height="AF.getPageHeight()-320"
         :row-class-name="rowClassName"
         :columns="tableTiT"
         :data="tableData"></Table>
     </Row>
   </div>
</template>

<script>
    export default {
        name: "index",
      data(){
          return{
            tableTiT: [{
              title: "序号",
              width: 80,
              align: 'center',
              type: 'index'
            },
              {
                title: '订单编号',
                align: 'center',
                key: 'bh'
              },
              {
                title: '下单时间',
                align: 'center',
                key: 'sj'
              },
              {
                title: '下单手机号',
                align: 'center',
                key: 'sjh'
              },
              {
                title: '收货地址',
                align: 'center',
                key: 'dz',
              },
              {
                title: '订单状态',
                width: 120,
                align: 'center',
                key: 'zt'
              },
              {
                title: '订单类别',
                width: 120,
                align: 'center',
                key: 'ddzt'
              },
              {
                title: '备注',
                align: 'center',
                key: 'bz'
              },
            ],
            tableData: [
              {
                bh:'20180909000123431',
                sj:'2018-04-01',
                sjh:'0908-1678-18766',
                dz:'sjhfjsfkjdakd',
                zt:'待审核',
                bz:'赠品111',
                ddzt:'直购'
              },
              {
                bh:'20180909000123541',
                sj:'2018-04-01',
                sjh:'0908-1678-18766',
                dz:'阿萨德',
                zt:'待审核',
                bz:'赠品211',
                ddzt:'中奖'
              }
            ],
          }
      }
    }
</script>

<style scoped>

</style>
