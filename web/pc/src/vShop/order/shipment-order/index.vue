<template>
  <div>
    <pager-tit title="订单发货"></pager-tit>
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
            title: '备注',
            align: 'center',
            key: 'bz'
          },
          {
            title: '审核人',
            align: 'center',
            key: 'shr'
          },
        ],
        tableData: [
          {
            bh:'20180909000123431',
            sj:'2018-04-01',
            sjh:'0908-1678-18766',
            dz:'sjhfjsfkjdakd',
            zt:'已审核',
            bz:'赠品111',
            shr:'losslll'
          }
        ],
      }
    }
  }
</script>

<style scoped>

</style>
