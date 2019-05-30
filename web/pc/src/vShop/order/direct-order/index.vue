<template>
  <div class="box_col">
    <pager-tit title="直购订单"></pager-tit>
    <div class="box_row colCenter rowRight pageFindSty" style="border: none">
      <div>
        <Input
          placeholder="order id" style="width: 200px"/>
      </div>
      <div>
        <Input
          placeholder="phone number" style="width: 200px"/>
      </div>
      <Button type="primary">
        <Icon type="md-search"></Icon>
        <!--查询-->
      </Button>
    </div>
    <div style="height: 670px">
      <order-card v-for="item in tableData" :mess="item"></order-card>
    </div>
    <div class="pagerBoxSty boxMar_T box_row rowRight">
      <one-page :total="total" :size="param.pageSize"
                :opts="[4,8,12,16]"
                @chPager="chPager"></one-page>
    </div>
<!--    <div>-->
<!--      <Card style="width:320px">-->
<!--        <div>-->
<!--          <img src="">-->
<!--          <div>-->
<!--            <CellGroup>-->
<!--              <Cell title="商品名称"/>-->
<!--              <Cell title="下单日期" extra="2017-08-12"/>-->
<!--              <Cell title="支付金额" extra="2895"/>-->
<!--              <Cell title="购买数量" extra="125"/>-->
<!--              <Cell title="支付时间" extra="3596"/>-->
<!--              <Cell title="用户名" extra="3"/>-->
<!--              <Cell title="手机号" extra="8"/>-->
<!--              <Cell title="收货地址" extra="1"/>-->
<!--            </CellGroup>-->
<!--          </div>-->
<!--        </div>-->
<!--      </Card>-->
<!--    </div>-->
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
        param:{
          // orderType:1,
          idLike:'',
          cjsjLike:'',
          pageNum: 1,
          pageSize: 4
        },
        total:0,
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
        tableData: [],
      }
    },
    created(){
      this.getPagerList()
    },
    methods:{
      chPager(p){
        this.param.pageNum = p.pageNum
        this.param.pageSize = p.pageSize
        this.getPagerList()
      },
      getPagerList(){
        this.$http.post(this.apis.ORDER.QUERY,this.param).then((res)=>{
          if (res.code == 200){
            this.tableData = res.page.list
            this.param.pageNum = res.page.pageNum
            this.param.pageSize = res.page.pageSize
            this.total = res.page.total
          }
        })
      },
    }
  }
</script>

<style scoped>

</style>
