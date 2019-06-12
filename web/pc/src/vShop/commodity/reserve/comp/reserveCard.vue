<template>
  <Card class="reserveCardSty">
    <div slot="title">
      <div class="sj" @click="edit">
        <Icon type="ios-brush" color="#fe5722" size="28"/>
      </div>
      <div class="reserveCardSty-selImg">
        <img  v-if='mess.imgUrls[selImg].substring(0,4)==="http"'
          :src="mess.imgUrls[selImg]" alt="">
        <img v-else :src="apis.GETFILEURL+mess.imgUrls[selImg]" alt="">

      </div>
      <div class="box_row rowAuto" style="height: 40px">
        <div :class="index==selImg?'selSty reserveCardSty-imgList':'reserveCardSty-imgList'"
             v-for="(it,index) in mess.imgUrls" @click="selImg = index"
            v-if="index<5">
          <img v-if='it.substring(0,4)==="http"' :src="it" alt="" style="width: 40px;height: 40px">
          <img v-else :src="apis.GETFILEURL+it" alt="" style="" style="width: 40px;height: 40px">
        </div>
      </div>
    </div>

    <Poptip trigger="hover">
      <div class="shopTit">
        {{mess.proName}}
      </div>
      <div slot="content" style="width: 240px;white-space:normal;font-size: 18px">
        {{mess.proName}}
      </div>
    </Poptip>

    <div class="box_row rowBetween colCenter">
      <div class="shopMoney">
        ￥{{mess.proPrice}}
      </div>

    </div>

    <div class="box_row rowBetween colCenter">
      <div class="lineVal">库存:{{mess.proStore}}</div>
      <Button type="info" size="small" @click="upShop">上架</Button>
    </div>


  </Card>
</template>

<script>

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
      }
    },
    created(){
    },
    methods:{
      edit(){
        this.$emit("edit")
      },
      upShop(){
        this.$http.post("/api/proinfo/saveOne",{id:this.mess.id}).then(res=>{
          if(res.success){
            this.$Message.success("Data updated successfully")
            this.$emit("upSuccess")
          }
        })
      }
    }

  }
</script>

<style lang="less">
  .reserveCardSty {
    width: 280px;
    margin: 8px;
    .ivu-card-head{
      position: relative;
      .sj{
        cursor: pointer;
        position: absolute;
        top: -7px;
        right: -27px;
        border-bottom: 40px solid rgba(255,69,0,0.1);
        border-left: 40px solid transparent;
        border-right: 40px solid transparent;
        width: 80px;
        height: 40px;
        transform: rotate(45deg);
        i{
          cursor: pointer;
          position: absolute;
          left: 50%;
          top: 20px;
          transform: translate(-50%,-50%) rotate(-45deg);
        }
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
