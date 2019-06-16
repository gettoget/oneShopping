<template>
  <div class="box_col">
    <!--<Card>-->
      <!--<pager-tit title="充值统计"></pager-tit>-->
    <!--</Card>-->
    <div class="box_col_auto">
      <div class="box_row" style="height: 100%">
        <div class="box_row_100">
          <chart-number></chart-number>
        </div>
        <div class="box_row_100">
          <chart-topup></chart-topup>
        </div>
      </div>
    </div>
    <div class="box_col_auto">

    </div>
  </div>
</template>

<script>
  import chartNumber from './comp/chartNumber'
  import chartTopup from './comp/chartTopUp'
  export default {
    name: "index",
    components:{
      chartNumber,chartTopup
    }
  }
</script>

<style scoped>

</style>
