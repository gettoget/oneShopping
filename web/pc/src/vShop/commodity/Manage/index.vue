<style lang="less">
  @import "./index.less";
</style>
<template>
  <Card class="UpStockListSty box_col">
    <div slot="title">
      <pager-tit title="商品"></pager-tit>
    </div>
    <!--图  名字 参与分数 剩余分数 状态（售卖中 开奖中  已开奖） 详情-->
    <div class="box_col">
      <div class="box_col_auto" style="height: 200px">
        <div class="box_row_list" style="width: 1328px;margin: auto">
        <shop-card :inVal="index" :mess="it"
                   v-for="(it,index) in shopList" :key="index"></shop-card>
        </div>
      </div>
      <div class="pagerBoxSty boxMar_T box_row rowRight">
        <one-page :total="total" :size="param.pageSize"
                  :opts="[4,8,12,16]"
                  @chPager="chPager"></one-page>
      </div>
    </div>

  </Card>
</template>

<script>
  import shopCard from './comp/shopCard'

  export default {
    name: "index",
    components: {
      shopCard
    },
    data(){
      return {
        shopList:[],
        total:0,
        param:{
          pageNum: 1,
          pageSize: 8
        }

      }
    },
    created(){
      this.getDataList()
    },
    methods:{
      chPager(p){
        this.param.pageNum = p.pageNum
        this.param.pageSize = p.pageSize
        this.getDataList()
      },
      getDataList(){
        this.$http.post("/api/proinfo/pager",this.param).then(res=>{
          console.log(res);
          if(res.code == 200){
            this.shopList = res.page.list
            this.total = res.page.total
          }
        }).catch(err=>{})
      }
    }
  }
</script>
