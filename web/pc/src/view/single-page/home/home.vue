<template>
  <div>
    <!--用户 今日 本月 累计-->
    <!--商品 今日 上架&#45;&#45;开奖-&#45;&#45;参与人事-->
    <!--钱 今日 本月 累计-->
    <!--消费-->


    <Row :gutter="20">
      <i-col :md="24" :lg="12" :xl="4"
             style="height: 200px;padding-bottom: 10px;">
        <infor-card shadow color="#E46CBB" icon="md-person-add" tit="新增用户" :icon-size="36">
          <div class="box_col rowAuto" style="padding-left: 18px">
            <div class="box_row colCenter">
              <div class="count-tit-style">今日 :</div>
              <!--<count-to :end="statisUser.today" count-class="count-style"/>-->
              <h1>{{AF.Format_Num(statisUser.today,3)}}</h1>
            </div>
            <div class="box_row colCenter">
              <div class="count-tit-style">本月 :</div>
              <!--<count-to :end="statisUser.mon" count-class="count-style"/>-->
              <h1>{{AF.Format_Num(statisUser.mon,3)}}</h1>
            </div>
            <div class="box_row colCenter">
              <div class="count-tit-style">累计 :</div>
              <!--<count-to :end="statisUser.total" count-class="count-style"/>-->
              <h1>{{AF.Format_Num(statisUser.total,3)}}</h1>
            </div>
          </div>
        </infor-card>
      </i-col>
      <i-col :md="24" :lg="12" :xl="4"
             style="height: 200px;padding-bottom: 10px;">
        <infor-card shadow color="#2d8cf0" icon="md-cube" tit="今日商品" :icon-size="36">
          <div class="box_col rowAuto" style="padding-left: 18px">
            <div class="box_row colCenter">
              <div class="count-tit-style">上架 :</div>
              <!--<count-to :end="statisPro.sj" count-class="count-style"/>-->
              <h1>{{AF.Format_Num(statisPro.sj,3)}}</h1>
            </div>
            <div class="box_row colCenter">
              <div class="count-tit-style">开奖 :</div>
              <!--<count-to :end="statisPro.kj" count-class="count-style"/>-->
              <h1>{{AF.Format_Num(statisPro.kj,3)}}</h1>
            </div>
            <div class="box_row colCenter">
              <div class="count-tit-style">参与 :</div>
              <!--<count-to :end="statisPro.cyyh" count-class="count-style"/>-->
              <h1>{{AF.Format_Num(statisPro.cyyh,3)}}</h1>
            </div>
          </div>
        </infor-card>
      </i-col>
      <i-col :md="24" :lg="12" :xl="5"
             style="height: 200px;padding-bottom: 10px;">
        <infor-card shadow color="#9A66E4" icon="md-pulse" tit="今日充值" :icon-size="36">
          <div class="box_col rowAuto" style="padding-left: 18px">
            <div class="box_row colCenter">
              <div class="count-tit-style">充值失败 :</div>
              <!--<count-to :end="statisRecharge.today" count-class="count-style"/>-->
              <h1>{{AF.Format_Num(statisRecharge.czsb,3)}}</h1>

            </div>
            <div class="box_row colCenter">
              <div class="count-tit-style">充值成功 :</div>
              <!--<count-to :end="statisRecharge.mon" count-class="count-style"/>-->
              <h1>{{AF.Format_Num(statisRecharge.czcg,3)}}</h1>
            </div>
            <div class="box_row colCenter">
              <div class="count-tit-style">系统赠送 :</div>
              <!--<count-to :end="statisRecharge.total" count-class="count-style"/>-->
              <h1>{{AF.Format_Num(statisRecharge.zsjb,3)}}</h1>
            </div>
          </div>
        </infor-card>
      </i-col>
      <i-col :md="24" :lg="12" :xl="5"
             style="height: 200px;padding-bottom: 10px;">
        <infor-card shadow color="#9A66E4" icon="md-pulse" tit="本月充值" :icon-size="36">
          <div class="box_col rowAuto" style="padding-left: 18px">
            <div class="box_row colCenter">
              <div class="count-tit-style">充值失败 :</div>
              <!--<count-to :end="statisRecharge.today" count-class="count-style"/>-->
              <h1>{{AF.Format_Num(statisRecharge1.czsb,3)}}</h1>

            </div>
            <div class="box_row colCenter">
              <div class="count-tit-style">充值成功 :</div>
              <!--<count-to :end="statisRecharge.mon" count-class="count-style"/>-->
              <h1>{{AF.Format_Num(statisRecharge1.czcg,3)}}</h1>
            </div>
            <div class="box_row colCenter">
              <div class="count-tit-style">系统赠送 :</div>
              <!--<count-to :end="statisRecharge.total" count-class="count-style"/>-->
              <h1>{{AF.Format_Num(statisRecharge1.zsjb,3)}}</h1>
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
              <!--<count-to :end="statisCharge.today" count-class="count-style"/>-->
              <h1>{{AF.Format_Num(statisCharge.today,3)}}</h1>
            </div>
            <div class="box_row colCenter">
              <div class="count-tit-style">本月 :</div>
              <!--<count-to :end="statisCharge.mon" count-class="count-style"/>-->
              <h1>{{AF.Format_Num(statisCharge.mon,3)}}</h1>
            </div>
            <div class="box_row colCenter">
              <div class="count-tit-style">累计 :</div>
              <!--<count-to :end="statisCharge.total" count-class="count-style"/>-->
              <h1>{{AF.Format_Num(statisCharge.total,3)}}</h1>
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
                        @click="ButtonGroupVal='7'">7天
                </Button>
                <Button :type="ButtonGroupVal=='15'?'primary':'default'"
                        @click="ButtonGroupVal='15'">15天
                </Button>
                <Button :type="ButtonGroupVal=='30'?'primary':'default'"
                        @click="ButtonGroupVal='30'">30天
                </Button>
              </ButtonGroup>
            </div>
          </div>
          <div style="height: 430px">
            <money-line :time="ButtonGroupVal"></money-line>
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
        ButtonGroupVal: '7',
        statisUser: {
          mon: 0,
          today: 0,
          total: 0
        },
        statisPro: {
          sj: 0,            // 上架
          cyyh: 0,         // 参与
          kj: 0             // 开奖
        },
        statisRecharge: {
            czsb: 0,      // 总计
            czcg: 0,           // 当天
            zsjb: 0          // 当月
        },
          statisRecharge1: {
              czsb: 0,      // 总计
              czcg: 0,           // 当天
              zsjb: 0         // 当月
          },
        statisCharge:{
          total: 0,       // 总计
          today: 0,       // 当天
          mon: 0          // 当月
        }
      }
    },
    created() {
      this.getUtatisUser()
      this.getStatisPro()
      this.getStatisRecharge()
      this.getStatisRecharge1()
      this.getSstatisCharge()
    },
    mounted() {
    },
    methods: {
      getUtatisUser() {
        this.$http.post('/api/statis/statisUser').then(res => {
          if (res.code == 200) {
            this.statisUser = res.result
          }
        }).catch(err => {
        })
      },
      getStatisPro() {
        this.$http.post('/api/statis/statisPro').then(res => {
          if (res.code == 200) {
            this.statisPro = res.result
          }
        }).catch(err => {
        })
      },
      getStatisRecharge() {
          let a = (new Date().getMonth()+1)
          if (a<10){
              a = "0"+a
          }
          let time = new Date().getFullYear() +'-'+a+"-"+new Date().getDate()
        this.$http.post('/api/statisnew/statisNewRecharge',{time:time}).then(res => {
          if (res.code == 200) {
            this.statisRecharge = res.result
          }
        }).catch(err => {
        })
      },
        getStatisRecharge1() {
            let a = (new Date().getMonth()+1)
            if (a<10){
                a = "0"+a
            }
            let time = new Date().getFullYear() +'-'+a
            this.$http.post('/api/statisnew/statisNewRecharge',{time:time}).then(res => {
                if (res.code == 200) {
                    this.statisRecharge1 = res.result
                }
            }).catch(err => {
            })
        },
      getSstatisCharge() {
        this.$http.post('/api/statis/statisCharge').then(res => {
          if (res.code == 200) {
            this.statisCharge = res.result
          }
        }).catch(err => {
        })
      }
    }
  }
</script>

<style lang="less">
  .count-tit-style {
    font-size: 18px;
  }

  .count-style {
    font-size: 26px;
  }

  .count-style-M {
    font-size: 22px;
  }
</style>
