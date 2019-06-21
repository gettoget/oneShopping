<template>
  <div :class="'box_row rowBetween colCenter shopCardStyLine '+backSty">
    <div>
      <img :src="iphone" alt="" style="width:120px">
    </div>
    <div class="box_row_100">
      <div>
        <h1>iphoneX Max</h1>
      </div>
    </div>
    <div class="box_row_100">
      <h2>1000</h2>
    </div>
    <div class="box_row_100">
      <h2>1000</h2>
    </div>
    <div class="box_row_100">
      <h2>售卖中</h2>
    </div>
    <div class="box_row_100">
      <Button type="success">详情</Button>
    </div>
  </div>
</template>

<script>
  import iphone from '../img/iphone.jpg'

  export default {
    name: "shopCard",
    props: {
      inVal: {
        type: Number,
        default: 0
      }
    },
    data() {
      return {
        iphone,
        backSty: this.inVal % 2 == 0 ? 'backF' : 'backS'
      }
    }
  }
</script>

<style lang="less">
  .shopCardStyLine {
    /*border-bottom: 3px solid #bdbdbd;*/
    padding: 12px;
    margin: 0 18px;
    text-align: center;
  }
  .backF{
    background-color: #fff;
  }
  .backS{
    background-color: #ffba6863;
  }
</style>
