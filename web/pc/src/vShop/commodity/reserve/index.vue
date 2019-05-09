<template>
  <div class="box_col">
    <pager-tit title="库存"></pager-tit>
    <div class="box_col_auto">
      <div class="box_row_list reserveCardBox">
        <reserve-card v-for="(it,index) in 10"></reserve-card>
      </div>
    </div>
  </div>
</template>

<script>
  import reserveCard from './comp/reserveCard'

  export default {
    name: "index",
    components: {
      reserveCard
    }
  }
</script>

<style lang="less">
  .reserveCardBox{
    width: 1184px;
    background-color: #fff;
    margin:0 auto;
  }
</style>
