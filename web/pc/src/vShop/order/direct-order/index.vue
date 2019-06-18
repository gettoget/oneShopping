<template>
  <Card class="directIndexBox box_col">
    <div slot="title" class="box_row colCenter">
      <div class="box_row_100">
        <pager-tit :title="$t('ZGDD')"></pager-tit>
      </div>
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
    <div class="box_col">
      <div class="box_col_auto">
        <order-card-box v-for="(it,index) in tableData" v-if="tableData.length>0"
                        :mess="it" :key="index"
                        @shopFH="shopFH"
        ></order-card-box>
      </div>
      <div class="pagerBoxSty boxMar_T box_row rowRight">
        <one-page :total="total" :size="param.pageSize"
                  :opts="[4,8,12,16]" @chPager="chPager"></one-page>
      </div>
    </div>
    <component
      :is="compName"
      :usermes="usermes"
      @close="compName = ''"
    ></component>
  </Card>
</template>

<script>
  import orderCard from '../comp/orderCard'
  import orderCardBox from '../comp/orderCardBox'
  import i18nTabTit from '@/mixins/i18nTabTit'
  import FH from '@/componentsShop/logisticsMess'

  export default {
    name: "index",
    mixins: [i18nTabTit],
    components: {
      orderCard, orderCardBox, FH
    },
    data() {
      return {
        usermes:{},
        compName:"",
        tableTiT: [
          {
            title: "#",
            width: 80,
            align: 'center',
            type: 'index'
          },
          {
            title: '订单编号',
            align: 'center',
            key: 'bh',
            width: 180,
            i18n:'ORDERNUMBER'
          },
          {
            title: '下单时间',
            align: 'center',
            key: 'sj',
            i18n:'GMRQ'
          },
          {
            title: '下单手机号',
            align: 'center',
            key: 'sjh',
            i18n:'YHDH'
          },
          {
            title: '收货地址',
            align: 'center',
            key: 'dz',
            i18n:'SHDZ'
          },
          {
            title: '订单状态',
            width: 120,
            align: 'center',
            key: 'zt',
            i18n:'DDZT'
          },
          {
            title: '商品种类',
            width: 120,
            align: 'center',
            key: 'splb',
            i18n:'SPZL'
          },
          {
            title: '商品名',
            width: 120,
            align: 'center',
            key: 'spm',
            i18n:'SPMC'
          },
          {
            title: '商品数量',
            width: 120,
            align: 'center',
            key: 'spsl',
            i18n:'SL'
          },
          {
            title: '备注',
            align: 'center',
            key: 'bz',
            i18n:'BZ'
          },
        ],
        tableData: [],
        param: {
          orderType: 1,
          proNameLike: '',
          userNameLike: '',
          phoneLike: "",
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
        this.$http.post(this.apis.ORDER.QUERY, this.param).then((res) => {
          if (res.code == 200) {
            this.tableData = res.page.list
            this.total = res.page.total
          }
        })
      },
      shopFH() {
        this.compName = "FH"
      }
    }
  }
</script>

<style lang="less">
  @import "./index.less";
</style>
