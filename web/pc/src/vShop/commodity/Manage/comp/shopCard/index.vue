<template>

    <div class="shopcarStyle">
      <Card>


        <!--<div slot="title">-->
          <!--<img :src="shopImg" alt="">-->
        <!--</div>-->
        <div class="ivu-card-body-photo box_row">
          <div class="shopimg">
            <img :src="shopImg" alt="">
          </div>
          <div class="shoptxt box_row_1auto">
            iphone XR 128G

          </div>
          <div class="shoptxt box_row_1auto">
            <div>品牌: Apple/苹果</div>
            <div>内存： 128G</div>
            <div>颜色：白色</div>

          </div>
          <div class="shoptxt box_row_1auto">
            9990
          </div>
          <div class="shoptxt box_row_1auto">
            13812340000
          </div>
          <div class="shoptxt box_row_1auto">
            <div>2019-04-20</div>
            <div>19：00</div>
          </div>
          <div class="shoptxt box_row_1auto">
            <div>2019-04-21</div>
            <div>19：00</div>
          </div>


        </div>
      </Card>
    </div>
</template>

<script>
  import shopImg from '@/assets/images/login-bg.jpg'
  export default {
    name: "index",
    data(){
      return {
        shopImg
      }
    }

  }
</script>

<style lang="less">
 @import "./index.less";

</style>
