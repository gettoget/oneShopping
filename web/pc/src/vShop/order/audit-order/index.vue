<template>
  <Card class="box_col findOrderBox">
    <div slot="title" class="box_row colCenter">
      <div class="">
        <!--<pager-tit title="订单查询"></pager-tit>-->
        <RadioGroup v-model="param.ddzt" type="button"
                    @on-change="getPagerList()" style="margin-right: 20px">
          <Radio label="00">{{$t('QB')}}</Radio>
          <Radio label="0">{{$t('DKJ')}}</Radio>
          <Radio label="1">{{$t('YZJ')}}</Radio>
          <Radio label="2">{{$t('WZJ')}}</Radio>
          <Radio label="3">{{$t('DZF')}}</Radio>
          <Radio label="4">{{$t('YZF')}}</Radio>
          <Radio label="5">{{$t('QXZF')}}</Radio>
        </RadioGroup>
      </div>
      <div class="box_row_100">
        <div class="box_row rowRight">
          <div>
            <Input
              v-model="param.proNameLike" @on-change="getPagerList"
              :placeholder="$t('SPMC')" style="width: 160px;margin-right: 18px"/>
          </div>
          <div>
            <Input
              v-model="param.userNameLike" @on-change="getPagerList"
              :placeholder="$t('YHM')" style="width: 160px;margin-right: 18px"/>
          </div>
          <div>
            <Input
              v-model="param.phoneLike" @on-change="getPagerList"
              :placeholder="$t('YHDH')" style="width: 160px;margin-right: 18px"/>
          </div>
          <div>
            <DatePicker format='yyyy-MM-dd' @on-change="changTime" type="daterange"
                        split-panels style="width: 160px;margin-right: 18px"
                        placement="bottom-end" :placeholder="$t('GMRQ')"></DatePicker>
          </div>
          <Button type="primary" @click="getPagerList">
            <Icon type="md-search"></Icon>
            <!--查询-->
          </Button>
        </div>
      </div>
    </div>
    <div class="box_col">
      <div :id="tabBox" class="box_col_auto">
        <find-order-card-box v-for="(it,index) in tableData"
                             :mess="it" :key="index"
                             @shopFH="shopFH"
                             @getMess="()=>{getMess(it)}"
        ></find-order-card-box>
      </div>
      <div class="pagerBoxSty boxMar_T box_row rowRight">
        <one-page :total="total" :size="param.pageSize"
                  :opts="[4,8,12,16]"
                  @chPager="chPager"></one-page>
      </div>
    </div>
    <component
      :is="compName"
      :orderMess="orderMess"
      @close="close"
    ></component>
  </Card>
</template>

<script>
  // import mess from './comp/mess'
  import FH from '@/componentsShop/logisticsMess'
  import findOrderCardBox from '../comp/findOrderCardBox'
  import orderMess from '@/vShop/order/comp/orderMess'
  import i18nTabTit from '@/mixins/i18nTabTit'
  export default {
    name: "index",
    components: {
      findOrderCardBox,
      FH,orderMess
    },
    mixins: [i18nTabTit],
    data() {
      return {
        // pageGjh:[
        //   {spmc:'请输入商品名称',i18n:'SPMC'}
        // ],
        total: 0,
        orderMess: {},
        userMesType: true,
        compName: '',
        tabBox: "tabBox",
        tableData: [],
        param: {
          orderType: "2",// 订单类型   1 直接购买  2 参与抽奖
          ddzt: "00",
          proNameLike: '',
          userNameLike: '',
          phoneLike: "",
          cjsjGte: '',//开始
          cjsjLte: '',//结束
          pageNum: 1,
          pageSize: 8
        }
      }
    },
    created() {
      this.getPagerList()
    },
    mounted() {
    },
    methods: {
      chPager(p) {
        this.param.pageNum = p.pageNum
        this.param.pageSize = p.pageSize
        this.getPagerList()
      },
      changTime(value) {
        console.log(value);
        if (value[1] != "") {
          this.param.cjsjGte = this.moment(value[0]).format('YYYY-MM-DD HH:mm:ss')
          this.param.cjsjLte = this.moment(value[1]).format('YYYY-MM-DD HH:mm:ss')
        } else {
          this.param.cjsjGte = ""
          this.param.cjsjLte = ""
        }
        this.getPagerList()
      },
      getPagerList() {
        let a = JSON.parse(JSON.stringify(this.param))
        if (a.ddzt == "00") {
          a.ddzt = ""
        }
        this.$http.post(this.apis.ORDER.QUERY, a).then((res) => {
          if (res.code == 200) {
            this.tableData = res.page.list
            this.total = res.page.total
          }
        })
      },
      close(){
        this.compName = ''
        this.orderMess = ""
      },
      shopFH(){
        this.compName = "FH"
      },
      getMess(mes){
        this.orderMess = mes
        this.compName = "orderMess"
      }
    }
  }
</script>

<style lang="less">
  .findOrderBox {
    .ivu-card-body {
      flex: 1;
      overflow: auto;
    }
  }
</style>
