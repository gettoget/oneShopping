<template>
      <div class="shopManager box_col" >
        <div class="barShow" >
          商品管理
        </div>
        <div class="box_row" style="padding: 0 16px">
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

        </div>
        <div class="box_col_autoY" style="background-color: #eeeeee">
          <shop-card v-for="(it,index) in 3"></shop-card>
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
    background-color: #eeeeee;
    .barShow{
      background-color: #dcdcdc;
      height: 20px;
    }
    .titleBarShow{
      padding: 0 10px;
      text-align: center;
      height: 20px;

    }

  }

</style>
