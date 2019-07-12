<template>
  <div class="box_col">
    <!--<Card>-->
      <!--<pager-tit title="充值统计"></pager-tit>-->
    <!--</Card>-->
    <div class="box_col_auto">
      <div style="height: 400px">
        <user-analysis></user-analysis>
      </div>
      <div style="height: 400px">
        <money-analysis></money-analysis>
      </div>



      <div class="box_row" style="height: 400px">
        <div class="box_row_100">
          <chart-number></chart-number>
        </div>
        <div class="box_row_100">
          <chart-topup></chart-topup>
        </div>
      </div>
      <div class="" style="height: 400px">
        <all-data></all-data>
      </div>
    </div>
  </div>
</template>

<script>
  import chartNumber from './comp/chartNumber'
  import chartTopup from './comp/chartTopUp'
  import allData from './comp/allData'

  import userAnalysis from './comp/userAnalysis'
  import moneyAnalysis from './comp/moneyAnalysis'


  export default {
    name: "index",
    components:{
      chartNumber,chartTopup,allData,userAnalysis,moneyAnalysis
    }
  }
</script>

<style scoped>

</style>
