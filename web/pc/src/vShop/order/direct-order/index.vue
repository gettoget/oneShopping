<template>
  <div class="box_col" style="background-color:#fff">
    <pager-tit title="直购订单"></pager-tit>
    <div style="width: 1000px;margin:0 auto">
      <order-card></order-card>
    </div>
    <!--<div class="box_row colCenter rowRight pageFindSty" style="border: none">-->
      <!--<div>-->
        <!--<Input-->
          <!--placeholder="请输入订单编号" style="width: 200px"/>-->
      <!--</div>-->
      <!--<div>-->
        <!--<Input-->
          <!--placeholder="请输入手机号码" style="width: 200px"/>-->
      <!--</div>-->
      <!--<Button type="primary">-->
        <!--<Icon type="md-search"></Icon>-->
        <!--&lt;!&ndash;查询&ndash;&gt;-->
      <!--</Button>-->
    <!--</div>-->
    <!--<div>-->
      <!--<Table-->
        <!--size='large' stripe-->
        <!--:height="AF.getPageHeight()-320"-->
        <!--:columns="tableTiT"-->
        <!--:data="tableData"></Table>-->
    <!--</div>-->

  </div>
</template>

<script>
  import orderCard from '../comp/orderCard'

  export default {
    name: "index",
    components: {
      orderCard
    },
    data() {
      return {
        tableTiT: [{
          title: "序号",
          width: 80,
          align: 'center',
          type: 'index'
        },
          {
            title: '订单编号',
            align: 'center',
            key: 'bh',
            width: 180
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
            title: '备注',
            align: 'center',
            key: 'bz'
          },
        ],
        tableData: [
          {
            bh: '20180909000123431',
            sj: '2018-04-01',
            sjh: '0908-1678-18766',
            dz: 'sjhfjsfkjdakd',
            zt: '待审核',
            bz: '赠品111',
            spm: 'iphone',
            splb: '数码',
            spsl: '2'
          }
        ],
      }
    }
  }
</script>

<style scoped>

</style>
