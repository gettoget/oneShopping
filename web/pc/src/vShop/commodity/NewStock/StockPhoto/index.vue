<template>
  <div class="oneShop">
    <img :src="shopImg" alt="">
  </div>
</template>

<script>
  import shopImg from '@/assets/images/login-bg.jpg'
    export default {
        name: "index",
      props:{
        mess:{
          type:Number,
          default:{

          }
        }
      },
      data(){
        return {
          shopImg
        }
      }
    }

</script>

<style lang="less">

    .oneShop {
      padding: 0px 5px;

      img {
        width: 100%;
        height: 100%;
      }
    }


</style>
