<template>
  <Card class="reserveCardSty">
    <div slot="title">
      <div class="reserveCardSty-selImg">
        <img :src="imgList[selImg].img" alt="">
      </div>
      <div class="box_row rowAuto">
        <div :class="index==selImg?'selSty reserveCardSty-imgList':'reserveCardSty-imgList'"
             v-for="(it,index) in imgList" @click="selImg = index">
          <img :src="it.img" alt="">
        </div>
      </div>
    </div>

    <div class="box_row rowBetween colCenter">
      <div class="shopMoney">
        ￥8999
      </div>
      <div class="shopTit">
        iphoneX Max
      </div>
    </div>

    <div class="box_row rowBetween colCenter">
      <div class="lineVal">库存:10,000</div>
      <Button type="info" size="small">上架</Button>
    </div>


  </Card>
</template>

<script>
  import iphone from '@/assets/img/iphone.jpg'

  export default {
    name: "reserveCard",
    data() {
      return {
        selImg: 0,
        imgList: [
          {img: iphone},
          {img: iphone},
          {img: iphone},
          {img: iphone},
          {img: iphone},
        ]
      }
    }
  }
</script>

<style lang="less">
  .reserveCardSty {
    width: 280px;
    margin: 8px;
    &-selImg {
      text-align: center;
      img {
        width: 140px;
        height: 140px;
      }
    }
    .selSty {
      border: crimson 1px solid;
    }
    &-imgList {
      cursor: pointer;
      border-radius: 2px;
      border: #fff0 2px solid;
      margin: 0 6px;
      img {
        width: 100%;
      }
    }

    .ivu-card-body {
      .shopMoney {
        font-size: 22px;
        font-weight: 700;
        color: orangered;
      }
      .shopTit {
        font-size: 18px;
        font-weight: 600;
      }
      .lineVal {
        font-size: 18px;
        color: #c3c3c3;
        margin: 12px 0;
        width: 200px;
      }
    }
  }

</style>
