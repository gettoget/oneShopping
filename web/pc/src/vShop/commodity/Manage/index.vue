<style lang="less">
  @import "./index.less";
</style>
<template>
  <Card class="UpStockListSty box_col">
    <div slot="title">
      <pager-tit title="商品"></pager-tit>
    </div>
    <!--图  名字 参与分数 剩余分数 状态（售卖中 开奖中  已开奖） 详情-->
    <div class="box_col_auto">
      <div class="box_row_list" style="width: 1328px;margin: auto">
        <shop-card :inVal="index" v-for="(it,index) in shopList" :key="index"></shop-card>
      </div>
    </div>
  </Card>
  <!--<div class="box_col">-->
  <!--<div class="box_row rowRight seachSty">-->
  <!--<div>-->
  <!--<pager-tit title="商品"></pager-tit>-->
  <!--</div>-->
  <!--<div class="box_row_100"></div>-->
  <!--<Input class="item" value="" placeholder="商品" style="width: 180px"/>-->
  <!--<Button class="item" type="primary" icon="ios-search"></Button>-->
  <!--</div>-->
  <!--<div class="box_row rowBetween colCenter listTiT">-->
  <!--<div style="width: 120px;">-->
  <!--</div>-->
  <!--<div class="box_row_100">-->
  <!--<h2>商品</h2>-->
  <!--</div>-->
  <!--<div class="box_row_100">-->
  <!--<h2>参与分数</h2>-->
  <!--</div>-->
  <!--<div class="box_row_100">-->
  <!--<h2>剩余分数</h2>-->
  <!--</div>-->
  <!--<div class="box_row_100">-->
  <!--<h2>商品状态</h2>-->
  <!--</div>-->
  <!--<div class="box_row_100">-->
  <!--<h2>操作</h2>-->
  <!--</div>-->
  <!--</div>-->

  <!--图  名字 参与分数 剩余分数 状态（售卖中 开奖中  已开奖） 详情-->
  <!--<div class="box_col_auto">-->
  <!--<div class="box_row_list" style="width: 1328px;margin: auto">-->
  <!--<shop-card :inVal="index" v-for="(it,index) in 5" :key="index"></shop-card>-->
  <!--</div>-->
  <!--</div>-->
  <!--</div>-->
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
        params:{
          pageNum: 1,
          pageSize: 2
        }

      }
    },
    created(){
      this.getDataList()
    },
    methods:{
      getDataList(){
        this.$http.post("/api/proinfo/pager",this.params).then(res=>{
          console.log(res);
          if(res.code == 200){
            this.shopList = res.page.list
          }
        }).catch(err=>{})
      }
    }
  }
</script>
