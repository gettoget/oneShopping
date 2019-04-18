<template>
  <div>
    <pager-tit title="Robot中奖订单"></pager-tit>
    <div class="box_row colCenter rowRight pageFindSty" style="border: none">
      <div>
        <!--<Icon type="md-person" size="34"/>-->
        <Input
          placeholder="请输入订单编号" style="width: 200px">
        </Input>
      </div>

      <div>
        <!--<Icon type="ios-call" size="34"/>-->
        <Input
          placeholder="请输入手机号码" style="width: 200px"
        ></Input>
      </div>
      <Button type="primary">
        <Icon type="md-search"></Icon>
        <!--查询-->
      </Button>
    </div>
    <Row style="position: relative;padding-top: 5px">
      <Table
        size='large' stripe
        :height="AF.getPageHeight()-320"
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
            width:120,
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
