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
            <chart-bar style="height: 300px;" :value="barData" text="近一周充值人数"/>
          </Card>
        </col>
      </Row>
      <Row>
        <Col style="padding: 15px">
          <Table border :columns="columns1" :data="data1"></Table>
        </Col>
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
            columns1: [
              {
                title: '充值次数',
                key: 'name'
              },
              {
                title: '人数',
                key: 'age'
              },
              {
                title: '金额',
                key: 'address'
              }
            ],
            data1: [
              {
                name: '首次',
                age: 18,
                address: 'N88',
                date: '2016-10-03'
              },
              {
                name: '二次',
                age: 24,
                address: '888',
                date: '2016-10-01'
              },
              {
                name: '三次',
                age: 30,
                address: '84561',
                date: '2016-10-02'
              },
              {
                name: '多次',
                age: 26,
                address: '54654',
                date: '2016-10-04'
              }
            ],
            pieData: [
              { value: 335, name: '第一次充值' },
              { value: 310, name: '第二次充值' },
              { value: 234, name: '第三次充值' },
              { value: 135, name: '多次充值' },
              { value: 1548, name: '已注册未充值' }
            ],
            barData: {
              Mon: 132,
              Tue: 342,
              Wed: 263,
              Thu: 123,
              Fri: 246,
              Sat: 132,
              Sun: 132
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
