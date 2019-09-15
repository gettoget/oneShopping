<template>
  <Card class="chargeOrderBoxSty box_col">
    <div slot="title"  class="box_row colCenter">
      <div class="box_row_100">
<!--        <pager-tit title="充值记录"></pager-tit>-->
      </div>
      <div>
        <Input
          v-model="param.username" @on-change="getPagerList" clearable
          :placeholder="$t('YHM')" style="width: 160px;margin-right: 18px"/>
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
      <div :id="tabBox" class="box_col_auto">
        <Table
          size='large' stripe
          :height="tab_H"
          v-if="tab_H>0"
          :columns="tableTiT"
          :data="tableData"></Table>
      </div>
      <div class="pagerBoxSty boxMar_T box_row rowRight">
        <one-page :total="total" :size="param.pageSize"
                  :opts="[4,8,12,16]" @chPager="chPager"></one-page>
      </div>
    </div>
  </Card>
</template>

<script>
  import i18nTabTit from '@/mixins/i18nTabTit'
  export default {
    name: "index",
    mixins: [i18nTabTit],
    data(){
      return {
        tab_H: 0,
        tabBox: "tabBox",
        tableTiT:[
          {
            title:"#",
            minWidth:80,
            render:(h,p)=>{
              let a = p.index+1+(this.param.pageNum-1)*this.param.pageSize
              return h('div',a)
            }
          },
          {
            title:"用户名称",
            key:"username",
            minWidth:100,
            i18n:'YHM'
          },
          {
            title:"充值时间",
            minWidth:180,
            key:"cjsj",
            i18n:'CZSJ',
            render:(h,p)=>{
              return h('div',this.moment(p.row.cjsj).format("YYYY-MM-DD HH:mm:ss"))
            }
          },
          {
            title:"支付时间",
            minWidth:180,
            key:"cjsj",
            i18n:'ZFSJ',
            render:(h,p)=>{
              return h('div',this.moment(p.row.qrsj).format("YYYY-MM-DD HH:mm:ss"))
            }
          },
          {
            title:"充值金额",
            minWidth:120,
            i18n:'CZJE',
            key:"amonut"
          },
          {
            title:"充值类型",
            minWidth:120,
            i18n:'LX',
            key:"czqd",
            render:(h,p)=>{
              if(p.row.czqd == '1'){
                  return h("div","充值")
              }else {
                  return h("div","奖励")
              }

            }
          },
            {
                title:"充值渠道",
                minWidth:120,
                i18n:'CZQD',
                key:"bz1",
                render:(h,p)=>{
                    if (p.row.bz1 == "51491197"){
                        return h("div","CIMB")
                    }else if (p.row.bz1 == "89220177"){
                        return h("div","Danamon")
                    }else if (p.row.bz1 == "11111029"){
                        return h("div","Alfa")
                    }else if (p.row.bz1 == "88561113"){
                        return h("div","Permata")
                    }else if (p.row.bz1 == "82910053"){
                        return h("div","BNI")
                    }else if (p.row.bz1 == "89022179"){
                        return h("div","Mandiri")
                    }

                }
            },
          {
            title:"充值状态",
            minWidth:120,
            i18n:'CZZT',
            key:"czzt",
            render:(h,p)=>{
              let a={
                "1":{
                  typ:"primary",
                  i18n:"DQR",
                  val:this.$t('DQR')
                },
                "2":{
                  typ:"success",
                  val:this.$t('CZCG')
                },
                "3":{
                  typ:"error",
                  val:this.$t('CZSB')
                },
                "4":{
                  typ:"warning",
                  val:this.$t('CZQX')
                }
              }

              return h("Tag",{
                props:{
                  color:a[p.row.czzt].typ
                }
              },a[p.row.czzt].val)
            }
          }
        ],
        tableData: [],
        param: {
          username: '',
          cjsjGte: '',//开始
          cjsjLte: '',//结束
          pageNum: 1,
          pageSize: 12
        },
        total: 0,
      }
    },
    created(){
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
    methods:{
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
      getPagerList(){
        this.$http.post("/api/recharge/pager",this.param).then(res=>{
          if (res.code == 200) {
            this.tableData = res.page.list
            this.total = res.page.total
          }
        }).catch(err=>{})
      }
    }
  }
</script>

<style lang="less">
.chargeOrderBoxSty{
  .ivu-card-body {
    flex: 1;
    overflow: auto;
  }
}
</style>
