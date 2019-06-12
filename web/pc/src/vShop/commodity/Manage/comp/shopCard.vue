<template>
  <Card class="shopCardSty">
    <div slot="title" @click="getMess" style="cursor: pointer">
      <div class="bqBox">
        {{mess.proZt | proZt}}
      </div>
      <div class="shopCardSty-selImg">
        <img v-if='mess.coverUrls[0].substring(0,4)==="http"'
             :src="mess.coverUrls" alt="">

        <img v-else :src="apis.GETFILEURL+mess.coverUrls" alt="">
      </div>

    </div>
    <div class="shopMoney">
      ￥{{mess.proPrice}}
    </div>
    <Poptip trigger="hover">
      <div class="shopTit"  @click="getMess">
        {{mess.proName}}
      </div>
      <div slot="content" style="width: 240px;white-space:normal;font-size: 18px">
        {{mess.proName}}
      </div>
    </Poptip>
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
    <div class="shopBq">
      <!--新品、热门、喜欢，-->
      <Tag :color="it.bol?'red':'default'"
           v-for="(it,index) in tegList"
           @click.native="tagEvent(it,index)">
        {{it.text}}
      </Tag>

      <!--<Tag :color="'red'"-->
           <!--v-for="(it,index) in mess.proLx.split(',')"-->
           <!--@click.native="tagVal = index">-->
        <!--{{it | TagVal}}-->
      <!--</Tag>-->
    </div>
  </Card>
</template>

<script>
  import iphone from '../img/iphone.jpg'

  export default {
    name: "shopCard",
    props: {
      inVal: {
        type: Number,
        default: 0
      },
      mess: {
        type: Object,
        default: {}
      }
    },
    filters: {
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
    data() {
      return {
        iphone,
        tagVal: 1,
        tegList:[
          {
            bol:false,
            text:"推荐",
            key:"1"
          },
          {
            bol:false,
            text:"上新",
            key:"2"
          },
          {
            bol:false,
            text:"热门",
            key:"3"
          },
        ]
      }
    },
    created(){
      this.tegList=[
        {
          bol:false,
          text:"推荐",
          key:"1"
        },
        {
          bol:false,
          text:"上新",
          key:"2"
        },
        {
          bol:false,
          text:"热门",
          key:"3"
        },
      ]
      let a = this.mess.proLx.split(',')
      a.forEach((it,index)=>{
        this.tegList[parseInt(it)-1].bol = true
      })
    },
    mounted(){
    },
    methods:{
      getMess(){
        this.$emit("getMess")
      },
      tagEvent(it,index){
        it.bol = !it.bol
        let b = []
        this.tegList.forEach((it,index)=>{
          if(it.bol){
            b.push(it.key)
          }
          if(index == this.tegList.length-1){
            this.upShopMess(b.join(','))
          }
        })
      },
      upShopMess(mes){
        this.$http.post("/api/proinfo/update",{id:this.mess.id,proLx:mes}).then(res=>{
          if(res.code == 200){
            this.$emit('labSave')
          }
        }).catch(err=>{})
      }
    }
  }
</script>

<style lang="less">
  .shopCardSty {
    width: 300px;
    margin: 16px;
    .ivu-card-head {
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
        text-align:center;
      }
    }
    &-selImg {
      text-align: center;
      height: 160px;
      text-align: center;
      img {
        /*width: 140px;*/
        height: 140px;
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
        word-break: break-all;
        text-overflow: ellipsis;
        display: -webkit-box; /** 对象作为伸缩盒子模型显示 **/
        -webkit-box-orient: vertical; /** 设置或检索伸缩盒对象的子元素的排列方式 **/
        -webkit-line-clamp: 2; /** 显示的行数 **/
        overflow: hidden; /** 隐藏超出的内容 **/
      }
      .shopTyp {
        text-align: right;
      }
      .lineBox {
        margin-top: 12px;
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
      .shopBq {
        margin: 12px 0 0 0;
      }
    }
  }
</style>
