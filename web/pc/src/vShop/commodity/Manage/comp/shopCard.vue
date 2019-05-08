<template>
  <Card class="shopCardSty">
    <div slot="title">
      <div class="bqBox">
        售卖中
      </div>
      <img :src="iphone" alt="">
    </div>
    <div class="shopMoney">
      ￥8999
    </div>
    <div class="shopTit">
      iphoneX Max
    </div>
    <div class="lineBox">
      <div class="lineVal">已售:5000份/剩余3999份</div>
      <div class="lineback">
        <div class="line"></div>
      </div>
    </div>
    <div class="shopBq">
      <!--新品、热门、喜欢，-->
      <Tag :color="index==tagVal?'red':'default'"
           v-for="(it,index) in ['新品','热门','喜欢']"
           @click.native="tagVal = index">{{it}}
      </Tag>
    </div>
  </Card>
</template>

<script>
  import iphone from '../img/iphone.jpg'

  export default {
    name: "shopCard",
    data() {
      return {
        iphone,
        tagVal: 1
      }
    }
  }
</script>

<style lang="less">
  .shopCardSty {
    width: 300px;
    margin: 16px;
    .ivu-card-head {
      text-align: center;
      position: relative;
      .bqBox {
        position: absolute;
        left: -30px;
        top: 20px;
        border-bottom: 30px solid orangered;
        border-left: 30px solid transparent;
        border-right: 30px solid transparent;
        width: 130px;
        height: 30px;
        transform: rotate(-45deg);
        line-height: 30px;
        color: #fff;
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
      }
      .shopTyp {
        text-align: right;
      }
      .lineBox {
        margin-top: 12px;
        .lineback {
          width: 200px;
          height: 12px;
          border-radius: 6px;
          background-color: #a5a5a57d;
          .line {
            width: 30%;
            height: 12px;
            border-radius: 6px;
            background-color: #2db7f5;
            text-align: center;
            line-height: 16px;
            color: #fff;
          }
        }
        .lineVal {
          font-size: 12px;
          color: #c3c3c3;
          margin: 12px 0;
          width: 200px;
        }
      }
      .shopBq{
        margin: 12px 0 0 0;
      }
    }
  }
</style>
