<!--订单发货-->
<template>
  <Card style="margin-bottom: 12px">
    <div slot="title" class="box_row rowBetween colCenter" style="position: relative">
      <div class="dispatchType">
        发货状态
      </div>
      <div style="margin-left: 100px">
        订单号:
        {{mess.id}}
      </div>
      <div>购买日期:
        {{moment(mess.cjsj).format('YYYY-MM-DD HH:mm:ss')}}
      </div>
    </div>

    <div class="box_row orderBox rowBetween colTop" style="height: 160px">
      <img :src="mess.imgUrl[0].substring(0,4)==='http'?mess.imgUrl[0]:apis.GETFILEURL+mess.imgUrl[0]"
      style="height: 160px;width: 160px" alt="">
      <div>
        <Poptip placement="left-start" :transfer="true" trigger="hover">
          <div class="comp-shop-name">
            {{mess.proName}}
          </div>
          <div slot="content" style="max-width: 240px;white-space:normal;font-size: 18px;">
            {{mess.proName}}
          </div>
        </Poptip>
      </div>

      <div class="comp-shop-UserMess">
        <div class="box_row itemSty">
          <div class="item-tit">
            <!--UserName:-->
            <Icon type="ios-contact" size="32" color="#fe5722"/>
          </div>
          <div class="item-val">
            {{mess.userName}}
          </div>
        </div>
        <div class="box_row itemSty">
          <div class="item-tit">
            <!--phone:-->
            <Icon type="ios-call" size="32" color="#ff9776"/>
          </div>
          <div class="item-val">
            {{mess.phone}}
          </div>
        </div>
        <div class="box_row itemSty">
          <div class="item-tit">
            <!--address:-->
            <Icon type="md-pin" size="32" color="#ff855f"/>
          </div>
          <div class="item-val">
            <div class="addressMess">
              {{mess.addr.address}}
            </div>
          </div>
        </div>
      </div>

      <div class="comp-shop-MoneyMess">
        <div v-if="mess.orderType == '2'" class="itemSty">
          <Poptip trigger="hover" :transfer="true" placement="bottom-end">
            <div class="box_row rowRight colCenter">
              <div v-if="index<2" v-for="(it,index) in mess.orderLists" :key="index">
                <Tag color="cyan">{{it.num}}</Tag>
              </div>
              <div style="font-size: 18px;margin-left: 8px;color: #13c2c2;cursor: pointer">
                》
              </div>
            </div>
            <div slot="content" style="max-height: 300px">
              <div class="box_row_list" style="width:265px;">
                <Tag color="cyan" v-for="(it,index) in mess.orderLists" :key="index" style="margin: 8px">{{it.num}}
                </Tag>
              </div>
            </div>
          </Poptip>
        </div>

        <div v-else-if="mess.orderType == '1'" class="box_row rowRight itemSty">
          <div class="item-tit">
            单价：
          </div>
          <div class="item-val">
            {{mess.singlePrice}}
          </div>
        </div>

        <div class="box_row rowRight itemSty">
          <div class="item-tit">
            数量：
          </div>
          <div class="item-val">
            <span style="font-size: 22px;font-weight: 700">x</span>
            <span v-if="mess.orderLists">{{mess.orderLists.length}}</span>
            <span v-else>1</span>
          </div>
        </div>
        <div class="box_row rowRight itemSty">
          <div class="styBox">
            支付 ：
            {{mess.zfje}}
          </div>
        </div>
      </div>
    </div>

    <div class="box_row wlBoxSty">
      <div class="box_row_100">
        <Collapse accordion>
          <Panel name="1">
            <span>中通快递:123123123123123</span>
            <span style="margin-left: 50px">当前物流信息</span>
            <div slot="content">
              <Steps :current="2" direction="vertical">
                <Step title="已完成" content="物流信息"></Step>
                <Step title="已完成" content="物流信息"></Step>
                <Step title="进行中" content="物流信息"></Step>
                <Step title="待进行" content="物流信息"></Step>
              </Steps>
            </div>
          </Panel>
        </Collapse>
      </div>
    </div>
    <component :is="compName" @close="close"></component>
  </Card>
</template>

<script>
  import iphone from '@/assets/img/iphone.jpg'

  export default {
    name: "orderCardBox",
    components: {
    },
    props: {
      mess: {
        type: Object,
        default: () => {
          return {}
        }
      }
    },
    data() {
      return {
        iphone,
        compName: "",
        it: {}
      }
    },
    created() {
    },
    mounted() {
      this.$nextTick(() => {
        // if(this.mess.imgUrl){
          this.mess.imgUrl = this.mess.imgUrl.split(',')
        // }else {
        //   this.mess.imgUrl = []
        // }
      })
    },
    methods: {
      shopFk() {
        this.compName = "logistics"
      },
      close() {
        this.compName = ""
      }
    }
  }
</script>

<style lang="less">
  .dispatchType {
    position: absolute;
    left: -54px;
    top: 5px;
    border-bottom: 40px solid rgba(255, 69, 0, 0.8);
    border-left: 40px solid transparent;
    border-right: 40px solid transparent;
    width: 160px;
    height: 40px;
    transform: rotate(-45deg);
    line-height: 40px;
    text-align: center;
    font-weight: 700;
    color: #fff;
  }

  .comp-shop-name {
    padding-top: 8px;
    width: 220px;
    height: 160px;
    margin: 0 26px;
    font-size: 18px;
    font-weight: 600;
    color: #8a8a8a;
    word-break: break-all;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 6;
    overflow: hidden;
  }

  .comp-shop-UserMess {
    .itemSty {
      font-size: 20px;
      color: #8a8a8a;
      padding-top: 8px;
      .item-tit {
        width: 50px;
      }
      .item-val {
        width: 330px;
        .addressMess {
          width: 300px;
          word-break: break-word;
          text-decoration: underline;
        }
      }
    }
  }

  .comp-shop-MoneyMess {
    min-width: 200px;
    .itemSty {
      font-size: 20px;
      font-weight: 600;
      color: #8a8a8a;
      margin-right: 30px;
      padding-top: 8px;
      .ivu-poptip{
        width: 100%;
      }
      .ivu-poptip-rel{
        width: 100%;
      }
      .item-tit {
      }
      .item-val {
        text-align: right;
        min-width: 60px;
      }
      .styBox {
        margin-top: 18px;
        padding: 0 20px;
        height: 40px;
        background-color: rgba(255, 69, 0, 0.8);
        text-align: center;
        line-height: 40px;
        border-radius: 4px;
        color: #fff;
      }
    }
  }

  .wlBoxSty {
    border-top: 1px solid #dcdee2;
    padding-top: 16px;
  }
</style>
