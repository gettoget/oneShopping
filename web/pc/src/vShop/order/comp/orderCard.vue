<template>
  <div class="orderCardSty">
    <div class="orderCardSty-tit">
      <span>{{mess.cjsj}}</span>
      <span style="margin-left: 30px">订单号:</span>
      <span class="orderNum"> {{mess.id}}</span>
    </div>
    <div class="orderCardSty-center box_row colCenter">
      <img :src="iphone" alt="">
      <div class="shopName">
        {{mess.proName}}
      </div>
      <div class="shopNum">
        X 1
      </div>
      <div class="buyer">
        <div class="buyer-name">
          {{mess.userName}}
          <span class="buyer-phone">121****9876</span>
        </div>
        <div class="buyer-address">
          {{mess.addr}}
        </div>
      </div>
      <div class="orderTyp">
        <div class="TypBox">
          {{mess.ddzt}}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import iphone from '@/assets/img/iphone.jpg'

  export default {
    name: "orderCard",
    props:{
      mess:{}
    },
    data() {
      return {
        iphone,
        pager:{}
      }
    },
    created() {
    }

  }
</script>

<style lang="less">
  .orderCardSty {
    border: 1px solid #e5e5e5;
    margin: 8px 0;
    &-tit {
      padding: 0 0 0 16px;
      background-color: #f5f5f5;
      line-height: 30px;
      font-size: 14px;
      color: #aaa;
      .orderNum {
        color: #000;
      }
    }
    &-center {
      padding: 16px;
      img {
        border: 1px solid #efefef;
        width: 80px;
        height: 80px;
      }
      .shopName {
        font-size: 20px;
        font-weight: 600;
        color: #c3c3c3;
        margin: 0 22px;
      }
      .shopNum{
        font-size: 18px;
        font-weight: 600;
        color: #636363;
        margin: 0 22px;
      }
      .buyer{
        margin: 0 22px;
        &-name{
          font-size: 16px;
          font-weight: 600;
          color: #636363;
        }
        &-phone{
          margin-left: 8px;
          font-size: 15px;
          color: #c3c3c3;
        }
        &-address{
          margin-top: 8px;
          font-size: 16px;
          color: #c3c3c3;
        }
      }
      .orderTyp{
        margin: 0 22px;
        .TypBox{
          color: #fff;
          background-color: #57c5f7;
          border-radius: 8px;
          padding: 14px;
          font-size: 18px;
          font-weight: 600;
        }
      }
    }
  }
</style>
