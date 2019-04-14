<template>
      <div class="shopManager box_col" >
        <div style="padding:0 0 10px 0;font-size: 1.2rem; border-bottom:solid 1px #ccc;color: #666;" >
          <Icon type="md-cube" color="#666" size="30"/>

          商品管理
        </div>
        <div class="box_row barShow" style="padding: 11px 16px;border-bottom:solid 1px #ccc;">
          <div class="titleBarShow" style="width: 100px;padding: 0px 5px;" >
            商品图片
          </div>
          <div class="titleBarShow box_row_1auto" >
            商品名称
          </div>
          <div class="titleBarShow box_row_1auto" >
            商品详情
          </div>
          <div class="titleBarShow box_row_1auto" >
            参与人数
          </div>
          <div class="titleBarShow box_row_1auto" >
            中奖ID
          </div>
          <div class="titleBarShow box_row_1auto" >
            开始时间
          </div>
          <div class="titleBarShow box_row_1auto" >
            结束时间
          </div>
          <div class="titleBarShow box_row_1auto" >
            商品状态
          </div>

        </div>
        <div class="box_col_autoY" >
          <shop-card v-for="(it,index) in 3" :mess="index"></shop-card>
        </div>


      </div>
</template>

<script>
  import shopCard from "./comp/shopCard"

    export default {

        name: "index",
        components:{
          shopCard

        }
    }
</script>

<style lang="less">
  .shopManager{
    /*background-color:#eeeeee;*/
    font-size: 0.18rem;

    .barShow{
      background-color: #f8f8f9;

      color: #2d8cf0;
      font-weight: 900;
    }
    .titleBarShow{
      padding: 0 10px;
      text-align: center;
      height: 20px;

    }

  }

</style>
