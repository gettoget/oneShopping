<template>
  <Card class="reserveCardSty">
    <div slot="title">
      <div class="reserveCardSty-selImg">
        <img :src=" mess.imgUrls[selImg]" alt="">
      </div>
      <div class="box_row rowAuto" style="height: 40px">
        <div :class="index==selImg?'selSty reserveCardSty-imgList':'reserveCardSty-imgList'"
             v-for="(it,index) in mess.imgUrls" @click="selImg = index"
            v-if="index<5">
          <img :src="it" alt="" style="" style="width: 40px;height: 40px">
        </div>
      </div>
    </div>

    <div class="shopTit">
      {{mess.proName}}
    </div>

    <div class="box_row rowBetween colCenter">
      <div class="shopMoney">
        ￥{{mess.proPrice}}
      </div>

    </div>

    <div class="box_row rowBetween colCenter">
      <div class="lineVal">库存:{{mess.proStore}}</div>
      <Button type="info" size="small">上架</Button>
    </div>


  </Card>
</template>

<script>
  import iphone from '@/assets/img/iphone.jpg'

  export default {
    name: "reserveCard",
    props:{
      inVal:{
        type:Number,
        default:0
      },
      mess:{
        type:Object,
        default:{}
      }
    },
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
      height: 160px;
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
        /*font-weight: 600;*/
        /*height: 55px;*/
        word-break: break-all;
        text-overflow: ellipsis;
        display: -webkit-box; /** 对象作为伸缩盒子模型显示 **/
        -webkit-box-orient: vertical; /** 设置或检索伸缩盒对象的子元素的排列方式 **/
        -webkit-line-clamp: 2; /** 显示的行数 **/
        overflow: hidden;  /** 隐藏超出的内容 **/
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
