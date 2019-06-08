<template>
  <Card class="box_col findOrderBox">
    <div slot="title" class="box_row colCenter">
      <div class="">
        <pager-tit title="发货订单"></pager-tit>
      </div>
      <div class="box_row_100">
        <div class="box_row rowRight">
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
        <goods-order-card-box v-for="(it,index) in tableData" :mess="it" :key="index"></goods-order-card-box>
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
  import goodsOrderCardBox from '../comp/goodsOrderCardBox'


  export default {
    name: "index",
    components: {
      goodsOrderCardBox
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
          ddztIn:"1",
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
