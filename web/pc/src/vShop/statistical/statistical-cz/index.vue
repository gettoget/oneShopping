<template>
  <div>
    <pager-tit title="充值统计"></pager-tit>
    <div class="box_row colCenter rowRight pageFindSty" style="border: none">
      <div>
        <!--<Icon type="md-person" size="34"/>-->
        <!--<Input-->
          <!--placeholder="请输入用户名" style="width: 200px">-->
        <!--</Input>-->
      </div>

      <div>
        <!--<Icon type="ios-call" size="34"/>-->
        <DatePicker type="daterange" :options="options2" placement="bottom-end" placeholder="Select date" style="width: 200px"></DatePicker>
      </div>
      <Button type="primary">
        <Icon type="md-search"></Icon>
        <!--查询-->
      </Button>
    </div>
    <div>
      <Row>
        <Col span="12" style="padding: 15px">
          <Card shadow>
            <chart-pie style="height: 300px;" :value="pieData" text="充值次数占比"></chart-pie>
          </Card>
        </Col>
        <Col span="12" style="padding: 15px">
          <Card shadow>
            <chart-bar style="height: 300px;" :value="barData" text="充值金额统计"/>
          </Card>
        </col>
      </Row>
    </div>
  </div>
</template>

<script>
  import { ChartPie, ChartBar } from '_c/charts'
    export default {
        name: "index",
      components: {
        ChartPie,
        ChartBar
      },
      data(){
          return{
            pieData: [
              { value: 335, name: '第一次充值' },
              { value: 310, name: '第二次充值' },
              { value: 234, name: '第三次充值' },
              { value: 135, name: '多次充值' },
              { value: 1548, name: '已注册未充值' }
            ],
            barData: {
              Mon: 13253,
              Tue: 34235,
              Wed: 26321,
              Thu: 12340,
              Fri: 24643,
              Sat: 1322,
              Sun: 1324
            },
            options2: {
              shortcuts: [
                {
                  text: '1 week',
                  value () {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                    return [start, end];
                  }
                },
                {
                  text: '1 month',
                  value () {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                    return [start, end];
                  }
                },
                {
                  text: '3 months',
                  value () {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                    return [start, end];
                  }
                }
              ]
            }
          }
      }
    }
</script>

<style scoped>

</style>
