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
      <all-data></all-data>
    </div>
  </div>
</template>

<script>
  import chartNumber from './comp/chartNumber'
  import chartTopup from './comp/chartTopUp'
  import allData from './comp/allData'
  export default {
    name: "index",
    components:{
      chartNumber,chartTopup,allData
    }
  }
</script>

<style scoped>

</style>
