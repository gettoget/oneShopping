<template>
  <div>
    <pager-tit title="Robot中奖订单"></pager-tit>
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
            title: '中奖时间',
            align: 'center',
            key: 'sj'
          },
          {
            title: '商品种类',
            width: 120,
            align: 'center',
            key: 'splb'
          },
          {
            title: '商品名',
            width: 120,
            align: 'center',
            key: 'spm'
          },
          {
            title: '商品数量',
            width: 120,
            align: 'center',
            key: 'spsl'
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
          }
        ],
        tableData: [
          {
            bh:'20180909000123431',
            sj:'2018-04-01',
            sjh:'0908-1678-18766',
            dz:'sjhfjsfkjdakd',
            zt:'已完成',
            spm:'电动车',
            splb:'car',
            spsl:'1'
          }
        ],
      }
    }
  }
</script>

<style scoped>

</style>
