<template>
  <div id="app">
    <div class="topbox">

      HOW TO PAY
        <!--https://wishwish.oss-ap-southeast-5.aliyuncs.com/payment/cimb.png             CIMB-->
        <!--https://wishwish.oss-ap-southeast-5.aliyuncs.com/payment/danamon.png          Danamon-->
        <!--https://wishwish.oss-ap-southeast-5.aliyuncs.com/payment/alfa.png             Alfa-->
        <!--https://wishwish.oss-ap-southeast-5.aliyuncs.com/payment/permata.png          Permata-->
        <!--https://wishwish.oss-ap-southeast-5.aliyuncs.com/payment/BNI.png              BNI-->
        <!--https://wishwish.oss-ap-southeast-5.aliyuncs.com/payment/mandiri.png          Mandirifor="">-->
    </div>
    <div class="buttonBox">
      <div class="bottonGroup box_row">
        <div @click="fileIndex = 'BNI'"
             class="buttonItem box_row_100" :class="fileIndex=='BNI'?'selBut':''">
          <!--BNI-->
          <img src="https://wishwish.oss-ap-southeast-5.aliyuncs.com/payment/BNI.png" alt="">
        </div>
        <div @click="fileIndex = 'alfa'"
             class="buttonItem box_row_100" :class="fileIndex=='alfa'?'selBut':''">
          <!--Alfamart-->
          <img src="https://wishwish.oss-ap-southeast-5.aliyuncs.com/payment/alfa.png" alt="">
        </div>
        <div @click="fileIndex = 'cimb'"
             class="buttonItem box_row_100" :class="fileIndex=='cimb'?'selBut':''">
          <!--CIMB NIAGA-->
          <img src="https://wishwish.oss-ap-southeast-5.aliyuncs.com/payment/cimb.png" alt="">
        </div>
      </div>
      <div class="bottonGroup box_row">
        <div @click="fileIndex = 'mandiri'"
             class="buttonItem box_row_100" :class="fileIndex=='mandiri'?'selBut':''">
          <!--mandiri-->
          <img src="https://wishwish.oss-ap-southeast-5.aliyuncs.com/payment/mandiri.png" alt="">
        </div>
        <div @click="fileIndex = 'permata'"
             class="buttonItem box_row_100" :class="fileIndex=='permata'?'selBut':''">
          <!--PermataBank-->
          <img src="https://wishwish.oss-ap-southeast-5.aliyuncs.com/payment/permata.png" alt="">
        </div>
        <div @click="fileIndex = 'danamon'"
             class="buttonItem box_row_100" :class="fileIndex=='danamon'?'selBut':''">
          <!--Danamon-->
          <img src="https://wishwish.oss-ap-southeast-5.aliyuncs.com/payment/alfa.png" alt="">
        </div>
      </div>
    </div>
    <div class="imgFileBox">
      <img v-if="fileIndex=='BNI'" src="./assets/img/bni.png" alt="">
      <img v-else-if="fileIndex=='alfa'" src="./assets/img/alfa.png" alt="">
      <img v-else-if="fileIndex=='cimb'" src="./assets/img/cimb.png" alt="">
      <img v-else-if="fileIndex=='mandiri'" src="./assets/img/mandlrl.png" alt="">
      <img v-else-if="fileIndex=='permata'" src="./assets/img/premata.png" alt="">
      <img v-else-if="fileIndex=='danamon'" src="./assets/img/danamon.png" alt="">

    </div>
  </div>
</template>

<script>
export default {
  name: 'App',
  data(){
    return{
      fileIndex:'BNI'
    }
  }
}
</script>

<style lang="less">
  @import "./assets/css/box.less";
  @import "./app.less";
</style>
