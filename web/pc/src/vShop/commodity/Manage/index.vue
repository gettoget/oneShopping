<template>
  <div class="box_col">
    <div class="box_row rowRight seachSty">
      <div>
        <pager-tit title="商品"></pager-tit>
      </div>
      <div class="box_row_100"></div>
      <Input class="item" value="" placeholder="商品" style="width: 180px"/>
      <Button class="item" type="primary" icon="ios-search"></Button>
    </div>
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
    <div class="box_col_auto">
      <div class="box_row_list" style="width: 1328px;margin: auto">
        <shop-card :inVal="index" v-for="(it,index) in 5" :key="index"></shop-card>
      </div>
    </div>
  </div>
</template>

<script>
  import shopCard from './comp/shopCard'

  export default {
    name: "index",
    components: {
      shopCard
    }
  }
</script>

<style lang="less">
  .seachSty{
    padding: 12px 0;
    margin: 0 18px;
    .item{
      margin-left: 16px;
    }
  }
  .listTiT {
    background-color: #ffba68;
    color: #fff;
    padding: 12px;
    margin: 0 18px ;
    text-align: center
  }
</style>
