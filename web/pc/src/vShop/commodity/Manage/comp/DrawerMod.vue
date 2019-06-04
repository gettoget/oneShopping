<template>
  <div>
    <Drawer
      v-model="showModal"
      width="600"
      class-name="ShopDrawer"
      @on-visible-change="visible">
      <div style="overflow: auto">
        <div class="box_row_z">
          <div v-for="(it,index) in mess.imgUrls" :key="index">
            <img :src="it" style="width:180px;height: 180px " alt="">
          </div>
        </div>
      </div>

      <div style="background: orangered;color: #fff;width:100px;text-align: center;padding: 12px 0;font-weight: 600;font-size: 17px;border-radius: 5px">
        {{mess.proZt | proZt}}
      </div>

      <div class="shopTit">
        {{mess.proName}}
      </div>
      <div class="box_row">
        <div class="proPriceSty">
          ￥{{mess.proPrice}}
        </div>

        <div style="padding-top: 22px;margin-right: 22px">
          <Tag :color="'red'"
               v-for="(it,index) in mess.proLx.split(',')"
               @click.native="tagVal = index">
            {{it | TagVal}}
          </Tag>
        </div>

        <div class="lineBox">
          <div class="lineVal">
            已售:{{(parseInt(mess.proPrice)-parseInt(mess.rePrice))}}
            份/剩余{{mess.rePrice}}份</div>
          <div class="lineback box_row">
            <div class="line" :style="{width:((parseInt(mess.proPrice)-parseInt(mess.rePrice))/parseInt(mess.rePrice))*100+'%'}">
            </div>
            <div class="box_row_100">
            </div>
          </div>
        </div>

      </div>

      <div style="font-size: 18px;font-weight: 500">
        上架时间：{{moment(mess.cjsj).format('YYYY-MM-DD')}}
      </div>
      <div>
        参与人数：{{mess.cyyhs}}
      </div>

      <div v-if="mess.proZt == '4'">
        <div>
          中奖人：{{mess.userName}}
        </div>
        <div>
          中奖号码：{{mess.zjhm}}
        </div>

      </div>
    </Drawer>
  </div>
</template>

<script>
  export default {
    name: "DrawerMod",
    data() {
      return {
        showModal: true,
        value1: 0
      }
    },
    props: {
      mess: {
        type: Object,
        default: {}
      }
    },
    filters:{
      proZt:(val)=>{
        switch (val) {
          case "1":
            return "售卖中"
            break;
          case "2":
            return "----"
            break;
          case "3":
            return "待开奖"
            break;
          case "4":
            return "已开奖"
            break;
          default:
            return "售卖中"
            break;
        }
      },
      TagVal: (val) => {
        switch (val) {
          case "1":
            return "推荐"
            break;
          case "2":
            return "新品"
            break;
          case "3":
            return "热门"
            break;
          default:
            return "新品"
            break;
        }
      }
    },
    methods: {
      visible(val) {
        setTimeout(() => {
          this.$emit("close")
        }, 70)
      }
    }
  }
</script>
<style lang="less">
  .ShopDrawer{
    .shopTit{
      font-size: 20px;
      font-weight: 600;
    }
    .proPriceSty{
      font-size: 28px;
      font-weight: 700;
      color: orangered;
      line-height: 65px;
      margin-right: 22px;
    }
    .lineBox {
      .lineback {
        width: 200px;
        height: 14px;
        border-radius: 6px;
        background-color: #a5a5a57d;
        .line {
          width: 30%;
          height: 14px;
          border-radius: 6px;
          background-color: #fe5722;
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
  }
</style>
