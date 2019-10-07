<template>
  <div class="box_col">
    <div class="box_col_auto">
    <div style="height: 200px">
      <order-in  > </order-in>
    </div>
    <div style="height: 200px">
    <recharge ></recharge>
    </div>
      <div style="height: 200px">
        <czl></czl>
      </div>
      <div style="height: 200px">
        <hyd></hyd>
      </div>
      <div style="height: 200px">
        <hyqd></hyqd>
      </div>
  </div>
  </div>
</template>

<script>
  import orderIn from './comp/orderIn'
  import recharge from './comp/recharge'
  import czl from './comp/czl'
  import hyd from './comp/hyd'
  import hyqd from './comp/hyqd'
    export default {
        name: "index",
      components:{
        orderIn,recharge,czl,hyd,hyqd
      }
    }
</script>

<style scoped>

</style>
