<template>
  <Card class="usercardSty usercard">
    <div slot="title" class="box_row">
      <div class="userTypBox">
        <div class="state">
          {{mess.zt | userZT}}
        </div>
      </div>
      <div class="goMessIcon">
        <Tooltip content="Go user message" placement="top" :transfer="true">
          <Icon type="md-exit" size="22"
                color="#fe5722" @click.native="GoMess"/>
        </Tooltip>
      </div>
      <div class="" style="padding-right: 16px;">
        <img class="titleImg" :src="iph" alt="">
      </div>
      <div class="box_row_100 userMess">
        <div class="messItem userName box_row">
          <Icon type="md-person" size="22"/>

          <Tooltip :content="mess.userName" placement="top-start">
            <div style="width: 150px;overflow:hidden;
            text-overflow:ellipsis;white-space:nowrap">
              {{mess.userName}}
            </div>
          </Tooltip>
        </div>
        <div class="messItem userPhone">
          <Icon type="ios-call" size="22"/>
          {{mess.phone}}
        </div>
      </div>
    </div>

    <div class="box_row rowBetween">
      <div>
        余 : {{mess.balance}}
      </div>
      <div>
        充 : {{mess.cz}}
      </div>
      <div>
        参 : {{mess.cy }}
      </div>
      <div>
        中 : {{mess.zjcs}}
      </div>
    </div>
  </Card>
</template>

<script>
  import defAva from './007.png'
  export default {
    name: "userCard",
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
    filters:{
      userZT:(val)=>{
        switch (val) {
          case "0":
            return "正常"
            break;
          case "1":
            return "禁用"
            break;
          default:
            return "正常"
            break;
        }
      }
    },
    data() {
      return {
        getUrl: this.apis.GETFILEURL,
        iph: defAva
      }
    },
    created(){
      if(this.mess.hImg !=  null){
        if(!this.mess.hImg.startsWith('http')){
          this.iph = this.getUrl + this.mess.hImg;
        }else{

          this.iph = this.mess.hImg;
        }
      }
    },
    methods:{
      GoMess(){
        this.$emit("GoMess")
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
          text-align: center;
          width: 40px;
          position: absolute;
          left: -30px;
          top: -18px;
          color: #fff;
          font-size: 16px;
          font-weight: 900;
          transform: rotate(-45deg);
        }
      }
      .goMessIcon{
        position: absolute;
        right: 6px;
        top: 3px;
        cursor: pointer;
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
