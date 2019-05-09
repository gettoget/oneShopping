<template>
  <div>
    <!--用户 今日 本月 累计-->
    <!--商品 今日 上架&#45;&#45;开奖-&#45;&#45;参与人事-->
    <!--钱 今日 本月 累计-->
    <!--消费-->


    <Row :gutter="20">
      <i-col :md="24" :lg="12" :xl="6"
             style="height: 200px;padding-bottom: 10px;">
        <infor-card shadow color="#E46CBB" icon="md-person-add" tit="新增用户" :icon-size="36">
          <div class="box_col rowAuto" style="padding-left: 18px">
            <div class="box_row colCenter">
              <div class="count-tit-style">今日 :</div>
              <count-to :end="803" count-class="count-style"/>
            </div>
            <div class="box_row colCenter">
              <div class="count-tit-style">本月 :</div>
              <count-to :end="803" count-class="count-style"/>
            </div>
            <div class="box_row colCenter">
              <div class="count-tit-style">累计 :</div>
              <count-to :end="803" count-class="count-style"/>
            </div>
          </div>
        </infor-card>
      </i-col>
      <i-col :md="24" :lg="12" :xl="6"
             style="height: 200px;padding-bottom: 10px;">
        <infor-card shadow color="#2d8cf0" icon="md-cube" tit="今日商品" :icon-size="36">
          <div class="box_col rowAuto" style="padding-left: 18px">
            <div class="box_row colCenter">
              <div class="count-tit-style">上架 :</div>
              <count-to :end="803" count-class="count-style"/>
            </div>
            <div class="box_row colCenter">
              <div class="count-tit-style">开奖 :</div>
              <count-to :end="803" count-class="count-style"/>
            </div>
            <div class="box_row colCenter">
              <div class="count-tit-style">参与 :</div>
              <count-to :end="803" count-class="count-style"/>
            </div>
          </div>
        </infor-card>
      </i-col>
      <i-col :md="24" :lg="12" :xl="6"
             style="height: 200px;padding-bottom: 10px;">
        <infor-card shadow color="#9A66E4" icon="md-pulse" tit="充值金额" :icon-size="36">
          <div class="box_col rowAuto" style="padding-left: 18px">
            <div class="box_row colCenter">
              <div class="count-tit-style">今日 :</div>
              <count-to :end="803" count-class="count-style"/>
            </div>
            <div class="box_row colCenter">
              <div class="count-tit-style">本月 :</div>
              <count-to :end="803" count-class="count-style"/>
            </div>
            <div class="box_row colCenter">
              <div class="count-tit-style">累计 :</div>
              <count-to :end="803" count-class="count-style"/>
            </div>
          </div>
        </infor-card>
      </i-col>
      <i-col :md="24" :lg="12" :xl="6"
             style="height: 200px;padding-bottom: 10px;">
        <infor-card shadow color="#ff9900" icon="md-pulse" tit="消费金额" :icon-size="36">
          <div class="box_col rowAuto" style="padding-left: 18px">
            <div class="box_row colCenter">
              <div class="count-tit-style">今日 :</div>
              <count-to :end="803" count-class="count-style"/>
            </div>
            <div class="box_row colCenter">
              <div class="count-tit-style">本月 :</div>
              <count-to :end="803" count-class="count-style"/>
            </div>
            <div class="box_row colCenter">
              <div class="count-tit-style">累计 :</div>
              <count-to :end="803" count-class="count-style"/>
            </div>
          </div>
        </infor-card>
      </i-col>
    </Row>
    <Row :gutter="20" style="margin-top: 20px;">
      <i-col :md="24" :lg="12">
        <Card>
          <div slot="title" style="overflow: hidden">
            <h2 style="float: left">现金流水统计</h2>
            <div class="box_row rowRight" style="float: right">
              <ButtonGroup>
                <Button :type="ButtonGroupVal=='7'?'primary':'default'"
                        @click="ButtonGroupVal='7'">7天</Button>
                <Button :type="ButtonGroupVal=='15'?'primary':'default'"
                        @click="ButtonGroupVal='15'">15天</Button>
                <Button :type="ButtonGroupVal=='30'?'primary':'default'"
                        @click="ButtonGroupVal='30'">30天</Button>
              </ButtonGroup>
            </div>
          </div>
          <div style="height: 430px">
            <money-line></money-line>
          </div>

        </Card>
      </i-col>
      <i-col :md="24" :lg="12">
        <Card>
          <div slot="title"><h2>开奖信息</h2></div>
          <table-box></table-box>
        </Card>
      </i-col>
    </Row>
  </div>
</template>

<script>
  import newShopD from './comp/newShop_D'
  import moneyBar from './comp/moneyBar'
  import moneyLine from './comp/money_line'
  import tableBox from './comp/homeTab'

  import TCard from './comp/T_Card'

  import InforCard from '_c/info-card'
  import CountTo from '_c/count-to'

  export default {
    name: 'home',
    components: {
      newShopD,
      moneyBar,
      moneyLine,
      tableBox,
      TCard,
      InforCard,
      CountTo,
    },
    data() {
      return {
        ButtonGroupVal:'7',
        inforCardData: [
          { title: '新增用户', icon: 'md-person-add', count: 803, color: '#2d8cf0' },
          { title: '累计点击', icon: 'md-locate', count: 232, color: '#19be6b' },
          { title: '新增问答', icon: 'md-help-circle', count: 142, color: '#ff9900' },
          { title: '分享统计', icon: 'md-share', count: 657, color: '#ed3f14' },
          { title: '新增互动', icon: 'md-chatbubbles', count: 12, color: '#E46CBB' },
          { title: '新增页面', icon: 'md-map', count: 14, color: '#9A66E4' }
        ],
      }
    },
    mounted() {
    }
  }
</script>

<style lang="less">
  .count-tit-style {
    font-size: 26px;
  }

  .count-style {
    font-size: 30px;
  }

  .count-style-M {
    font-size: 25px;
  }
</style>
