<template>
  <Card class="usercardSty usercard">
    <div slot="title" class="box_row">
      <div class="userTypBox">
        <div class="state">state</div>
      </div>
      <div class="" style="padding-right: 16px;">
        <img class="titleImg" :src="iph" alt="">
      </div>
      <div class="box_row_100 userMess">
        <div class="messItem userName">
          <Icon type="md-person" size="22"/>
          David James
        </div>
        <div class="messItem userPhone">
          <Icon type="ios-call" size="22"/>
          (11) 191****1234
        </div>
      </div>
    </div>

    <!--ivu-card-body-->
    <div class="box_row rowBetween">
      <div>
        余 : 800
      </div>
      <div>
        充 : 1000
      </div>
      <div>
        参 : 200
      </div>
      <div>
        中 : 0
      </div>
    </div>
  </Card>
</template>

<script>

  export default {
    name: "userCard",
    data() {
      return {
        iph: "https://i.loli.net/2017/08/21/599a521472424.jpg"
      }
    }
  }
</script>

<style lang="less">
  .usercardSty {
    width: 320px;
    .ivu-card-head {
      position: relative;
      .userTypBox {
        position: absolute;
        left: 0;
        top: 0;
        border: 30px;
        border-style: solid;
        border-color: #ffa2d3 #fff0 #fff0 #ffa2d3;
        .state {
          position: absolute;
          left: -30px;
          top: -18px;
          color: #fff;
          font-size: 18px;
          font-weight: 900;
          transform: rotate(-45deg);
        }
      }
      .titleImg {
        width: 90px;
        height: 90px;
        border-radius: 50%;
      }
      .userMess {
        .messItem {
          font-size: 18px;
        }
        .userName {
          font-weight: 600;
          padding-top: 16px;
        }
        .userPhone {
          padding-top: 16px;
          color: #9a9a9a;
        }
      }
    }
    .ivu-card-body {

    }
  }
</style>
