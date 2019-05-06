<template>
  <div>
    用户   今日  本月  累计
    商品   今日   上架--开奖---参与人事
    钱     今日  本月  累计
    消费


    <Row :gutter="20">
      <i-col :xs="12" :md="8" :lg="4" v-for="(infor, i) in addUser" :key="`infor-${i}`"
             style="height: 130px;padding-bottom: 10px;">
        <infor-card shadow :color="infor.color" :icon="infor.icon" :icon-size="36">
          <count-to :end="infor.count" count-class="count-style"/>
          <p>{{ infor.title }}</p>
        </infor-card>
      </i-col>

      <i-col :xs="12" :md="8" :lg="4" style="height: 130px;padding-bottom: 10px;">
        <div class="box_col">
          <div class="box_col_100">
            <infor-card shadow :color="'#E46CBB'" :icon="'md-cube'" :icon-size="36">
              <count-to :end="100" count-class="count-style-M"/>
              <p>今日上架商品</p>
            </infor-card>
          </div>
          <div style="height: 4px"></div>
          <div class="box_col_100">
            <infor-card shadow :color="'#E46CBB'" :icon="'md-ribbon'" :icon-size="36">
              <count-to :end="100" count-class="count-style-M"/>
              <p>今日中奖商品</p>
            </infor-card>
          </div>
        </div>
      </i-col>

      <i-col :xs="12" :md="8" :lg="4" style="height: 130px;padding-bottom: 10px;">
        <div class="box_col">
          <div class="box_col_100">
            <infor-card shadow :color="'#9A66E4'" :icon="'md-cube'" :icon-size="36">
              <count-to :end="100" count-class="count-style-M"/>
              <p>本月上架商品</p>
            </infor-card>
          </div>
          <div style="height: 4px"></div>
          <div class="box_col_100">
            <infor-card shadow :color="'#9A66E4'" :icon="'md-ribbon'" :icon-size="36">
              <count-to :end="100" count-class="count-style-M"/>
              <p>本月中奖商品</p>
            </infor-card>
          </div>
        </div>
      </i-col>

      <i-col :xs="12" :md="8" :lg="4" style="height: 130px;padding-bottom: 10px;">
        <div class="box_col">
          <div class="box_col_100">
            <infor-card shadow :color="'#2d8cf0'" :icon="'md-cube'" :icon-size="36">
              <count-to :end="100" count-class="count-style-M"/>
              <p>累计上架商品</p>
            </infor-card>
          </div>
          <div style="height: 4px"></div>
          <div class="box_col_100">
            <infor-card shadow :color="'#2d8cf0'" :icon="'md-ribbon'" :icon-size="36">
              <count-to :end="100" count-class="count-style-M"/>
              <p>累计中奖商品</p>
            </infor-card>
          </div>
        </div>
      </i-col>
    </Row>
    <Row :gutter="20" style="margin-top: 20px;">
      <i-col :md="24" :lg="12">
        <Card>
          <div slot="title" style="overflow: hidden">
            <h2 style="float: left">现金流水统计</h2>
            <div class="box_row rowRight" style="float: right">
              <Button type="info" size="small" style="margin-left: 8px">7天</Button>
              <Button type="info" size="small" style="margin-left: 8px">15天</Button>
              <Button type="info" size="small" style="margin-left: 8px">30天</Button>
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

    <!--<Row :gutter="20">-->
    <!--<i-col :xs="12" :md="8" :lg="4" v-for="(infor, i) in inforCardData" :key="`infor-${i}`"-->
    <!--style="height: 120px;padding-bottom: 10px;">-->
    <!--<infor-card shadow :color="infor.color" :icon="infor.icon" :icon-size="36">-->
    <!--<count-to :end="infor.count" count-class="count-style"/>-->
    <!--<p>{{ infor.title }}</p>-->
    <!--</infor-card>-->
    <!--</i-col>-->
    <!--</Row>-->
    <!--<Row :gutter="20" style="margin-top: 10px;">-->
    <!--<i-col :md="24" :lg="8" style="margin-bottom: 20px;">-->
    <!--<Card shadow>-->
    <!--<chart-pie style="height: 300px;" :value="pieData" text="用户访问来源"></chart-pie>-->
    <!--</Card>-->
    <!--</i-col>-->
    <!--<i-col :md="24" :lg="16" style="margin-bottom: 20px;">-->
    <!--<Card shadow>-->
    <!--<chart-bar style="height: 300px;" :value="barData" text="每周用户活跃量"/>-->
    <!--</Card>-->
    <!--</i-col>-->
    <!--</Row>-->
    <!--<Row>-->
    <!--<Card shadow>-->
    <!--<example style="height: 310px;"/>-->
    <!--</Card>-->
    <!--</Row>-->
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
  import {ChartPie, ChartBar} from '_c/charts'
  import Example from './example.vue'

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
      ChartPie,
      ChartBar,
      Example
    },
    data() {
      return {
        addUser: [
          {title: '今日新增用户', icon: 'md-person-add', count: 803, color: '#E46CBB'},
          {title: '本月新增用户', icon: 'md-person-add', count: 232, color: '#9A66E4'},
          {title: '累计新增用户', icon: 'md-person-add', count: 142, color: '#2d8cf0'},
        ],
        inforCardData: [
          {title: '新增用户', icon: 'md-person-add', count: 803, color: '#2d8cf0'},
          {title: '累计点击', icon: 'md-locate', count: 232, color: '#19be6b'},
          {title: '新增问答', icon: 'md-help-circle', count: 142, color: '#ff9900'},
          {title: '分享统计', icon: 'md-share', count: 657, color: '#ed3f14'},
          {title: '新增互动', icon: 'md-chatbubbles', count: 12, color: '#E46CBB'},
          {title: '新增页面', icon: 'md-map', count: 14, color: '#9A66E4'}
        ],
        pieData: [
          {value: 335, name: '直接访问'},
          {value: 310, name: '邮件营销'},
          {value: 234, name: '联盟广告'},
          {value: 135, name: '视频广告'},
          {value: 1548, name: '搜索引擎'}
        ],
        barData: {
          Mon: 13253,
          Tue: 34235,
          Wed: 26321,
          Thu: 12340,
          Fri: 24643,
          Sat: 1322,
          Sun: 1324
        }
      }
    },
    mounted() {
      //
    }
  }
</script>

<style lang="less">
  .count-style {
    font-size: 50px;
  }

  .count-style-M {
    font-size: 25px;
  }
</style>
