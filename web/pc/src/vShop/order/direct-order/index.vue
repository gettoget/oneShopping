<template>
  <Card class="directIndexBox box_col">
    <div slot="title" class="box_row colCenter">
      <div class="box_row_100">
        <pager-tit title="直购订单"></pager-tit>
      </div>
      <div>
        <Input
          v-model="param.proNameLike" @on-change="getPagerList"
          placeholder="商品名称" style="width: 160px;margin-right: 18px"/>
      </div>
      <div>
        <Input
          v-model="param.userNameLike" @on-change="getPagerList"
          placeholder="用户姓名" style="width: 160px;margin-right: 18px"/>
      </div>
      <div>
        <Input
          v-model="param.phoneLike" @on-change="getPagerList"
          placeholder="用户电话" style="width: 160px;margin-right: 18px"/>
      </div>
      <div>
        <DatePicker format='yyyy-MM-dd' @on-change="changTime" type="daterange"
                    split-panels style="width: 160px;margin-right: 18px"
                    placement="bottom-end" placeholder="购买时间"></DatePicker>
      </div>
      <Button type="primary" @click="getPagerList">
        <Icon type="md-search"></Icon>
        <!--查询-->
      </Button>
    </div>
    <div class="box_col">
      <div class="box_col_auto">
        <order-card-box v-for="(it,index) in tableData" :mess="it" :key="index"></order-card-box>
      </div>
      <div class="pagerBoxSty boxMar_T box_row rowRight">
        <one-page :total="total" :size="param.pageSize"
                  :opts="[4,8,12,16]" @chPager="chPager"></one-page>
      </div>
    </div>

  </Card>
</template>

<script>
  import orderCard from '../comp/orderCard'
  import orderCardBox from '../comp/orderCardBox'

  export default {
    name: "index",
    components: {
      orderCard, orderCardBox
    },
    data() {
      return {
        tableTiT: [
          {
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
        param: {
          orderType:1,
          proNameLike: '',
          userNameLike: '',
          phoneLike:"",
          cjsjGte: '',//开始
          cjsjLte: '',//结束
          pageNum: 1,
          pageSize: 8
        },
        total: 0,
      }
    },
    created() {
      this.getPagerList()
    },
    methods: {
      chPager(p) {
        this.param.pageNum = p.pageNum
        this.param.pageSize = p.pageSize
        this.getPagerList()
      },
      changTime(value) {
        console.log(value);
        if(value[1] != ""){
          this.param.cjsjGte = this.moment(value[0]).format('YYYY-MM-DD HH:mm:ss')
          this.param.cjsjLte = this.moment(value[1]).format('YYYY-MM-DD HH:mm:ss')
        }else{
          this.param.cjsjGte = ""
          this.param.cjsjLte = ""
        }
        this.getPagerList()
      },
      getPagerList() {
        this.$http.post(this.apis.ORDER.QUERY, this.param).then((res) => {
          if (res.code == 200) {
            this.tableData = res.page.list
            this.total = res.page.total
          }
        })
      },
    }
  }
</script>

<style lang="less">
  @import "./index.less";
</style>
