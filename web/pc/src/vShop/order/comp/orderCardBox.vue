<template>
  <Card style="margin-bottom: 12px">
    <div slot="title" class="box_row rowBetween colCenter">
      <div>
        订单号:{{mess.id}}
      </div>
      <div>购买日期:{{moment(mess.cjsj).format('YYYY-MM-DD HH:mm:ss')}}</div>
      <div>
        <Button type="info" size="small" @click="shopFH">发货</Button>
      </div>
    </div>

    <div class="box_row orderBox rowBetween colTop" style="height: 160px">
      <!--<img :src="mess.imgUrl[0].substring(0,4)==='http'?mess.imgUrl[0]:apis.GETFILEURL+mess.imgUrl[0]"-->
           <!--style="height: 160px;width: 160px" alt="">-->
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
            <Icon type="ios-contact"  size="32" color="#fe5722"/>
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
              <span v-if="mess.addr">{{mess.addr.address}}</span>
            </div>
          </div>
        </div>
      </div>

      <div class="comp-shop-MoneyMess">
        <div class="box_row itemSty">
          <div class="item-tit">
            单价：
          </div>
          <div class="item-val">
              {{mess.singlePrice}}
          </div>
        </div>
        <div class="box_row itemSty">
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
          <!--<div class="item-tit">-->
            <!--合计：-->
          <!--</div>-->
          <!--<div class="item-val">-->
            <!--{{mess.zfje}}-->
          <!--</div>-->
          <div class="styBox">
            支付 ：{{mess.zfje}}
          </div>
        </div>
      </div>

    </div>
  </Card>
</template>

<script>
  import iphone from '@/assets/img/iphone.jpg'

  export default {
    name: "orderCardBox",
    props: {
      mess: {
        type: Object,
        default: {}
      }
    },
    data() {
      return {
        iphone,
        it: {}
      }
    },
    created() {
      // this.mess.imgUrl = this.mess.imgUrl.split(',')
    }
  }
</script>

<style lang="less">
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
    .itemSty{
      font-size: 20px;
      color: #8a8a8a;
      padding-top: 8px;
      .item-tit{
        width: 50px;
      }
      .item-val{
        width: 330px;
        .addressMess{
          width: 300px;
          word-break:break-word;
          text-decoration: underline;
        }
      }
    }
  }
  .comp-shop-MoneyMess{
    .itemSty{
      font-size: 20px;
      font-weight: 600;
      color: #8a8a8a;
      margin-right: 30px;
      padding-top: 8px;
      .item-tit{
      }
      .item-val{
        width: 100px;
        text-align: right;
      }
      .styBox{
        margin-top: 18px;
        padding: 0 20px;
        height: 40px;
        background-color: rgba(255,69,0,0.8);
        text-align: center;
        line-height: 40px;
        border-radius: 4px;
        color: #fff;
      }
    }
  }
</style>
