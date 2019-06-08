<template>
  <Card class="box_col findOrderBox">
    <div slot="title" class="box_row colCenter">
      <div class="">
        <pager-tit title="订单查询"></pager-tit>
      </div>
      <div class="box_row_100">
        <div class="box_row rowRight">
          <div v-if="param.orderType == '2'">
            <RadioGroup v-model="param.ddzt" type="button"
                        @on-change="getPagerList()" style="margin-right: 20px">
              <Radio label="00">全部</Radio>
              <Radio label="0">待开奖</Radio>
              <Radio label="1">已中奖</Radio>
              <Radio label="2">未中奖</Radio>
              <Radio label="3">待支付</Radio>
              <Radio label="4">已支付</Radio>
              <Radio label="5">取消支付</Radio>
            </RadioGroup>
          </div>
          <div>
            <!--<DatePicker value='yyyy-MM-dd' @on-change="changTime" type="daterange" :options="options2"-->
                        <!--placement="bottom-end" placeholder="Select date" style="width: 200px"></DatePicker>-->
          </div>
          <div>
            <Input
              v-model="param.idLike"
              placeholder="order id" style="width: 200px"/>
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
        <find-order-card-box v-for="(it,index) in tableData" :mess="it" :key="index"></find-order-card-box>
      </div>
      <div class="pagerBoxSty boxMar_T box_row rowRight">
        <one-page :total="total" :size="param.pageSize"
                  :opts="[4,8,12,16]"
                  @chPager="chPager"></one-page>
      </div>
    </div>

    <component
      :is="compName"
      :usermes="usermes"
    ></component>
  </Card>
</template>

<script>
  // import mess from './comp/mess'
  import findOrderCardBox from '../comp/findOrderCardBox'

  export default {
    name: "index",
    components: {
      findOrderCardBox
      // mess
    },
    data() {
      return {
        total: 0,
        usermes: {},
        userMesType: true,
        compName: '',
        tabBox:"tabBox",
        tab_H:0,
        tableData: [],
        param: {
          orderType:"2",// 订单类型   1 直接购买  2 参与抽奖
          ddzt:"00",
          idLike: '',
          cjsjLike: '',
          pageNum: 1,
          pageSize: 8
        }
      }
    },
    created() {
      this.getPagerList()
    },
    mounted(){
      this.$nextTick(() => {
        try {
          this.tab_H = this.AF.getDom_H(this.tabBox)
        } catch (e) {
        }
      })
    },
    methods: {
      chPager(p) {
        this.param.pageNum = p.pageNum
        this.param.pageSize = p.pageSize
        this.getPagerList()
      },
      changTime(value) {
        this.param.cjsjGte = value[0] + ' 00:00:00'
        this.param.cjsjLte = value[1] + ' 23:59:59'
        this.getPagerList()
      },
      getPagerList() {
        let a = JSON.parse(JSON.stringify(this.param))
        if(a.ddzt == "00"){
          a.ddzt = ""
        }
        this.$http.post(this.apis.ORDER.QUERY, a).then((res) => {
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
  .findOrderBox {
    .ivu-card-body {
      flex: 1;
      overflow: auto;
    }
  }
</style>
