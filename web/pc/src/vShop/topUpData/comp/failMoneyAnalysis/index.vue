<template>
  <Card class="box_col failMoneyAnalysis">
    <div id="failMoneyAnalysis" style="height: 100%"></div>
  </Card>
</template>

<script>
  import echarts from 'echarts'

  export default {
    name: "index",
    mounted() {
      this.$nextTick(() => {

        this.getData()

      })
    },
    methods: {
      getData(){
        var v = this
        this.$http.post("/api/statis/statisCzsb",{day:"30"}).then(res => {
          if (res.code == 200) {
            let dataX = []
            let dataV = []

            res.result.forEach((it, index) => {
              let item = it.split(',')
              dataX.push(item[0])
              dataV.push(item[1])
              if (index == res.result.length - 1) {
                v.buildEchart(dataX,dataV)
              }
            })
          }
        })
      },
      buildEchart(dataX,dataV,data1,data2,data3,data4,data5,data6) {
          var v = this
          var myChart = echarts.init(document.getElementById('failMoneyAnalysis'));

          let option = {
              title: {
                  text: "用户充值失败走势",
                  subtext: "",
                  x: "center"
              },
              grid: {
                  left: 40,
                  top: '100px',
                  right: '40px',
                  bottom: '60px'
              },
              xAxis: {
                  data: dataX,
                  // ['07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02'],
                  axisLabel: {
                      inside: false,
                      textStyle: {
                          color: '#999'
                      },
                      rotate: -40
                  },
                  axisTick: {
                      show: false
                  },
                  axisLine: {
                      show: false
                  },
                  z: 10
              },
              yAxis: {
                  axisLine: {
                      show: false
                  },
                  axisTick: {
                      show: false
                  },
                  axisLabel: {
                      textStyle: {
                          color: '#999'
                      }
                  }
              },
              series: [
                  {
                      type: 'bar',
                      label: {
                          normal: {
                              show: true,
                              position: "top",
                          }
                      },
                      itemStyle: {
                          normal: {
                              color: new echarts.graphic.LinearGradient(
                                  0, 0, 0, 1,
                                  [
                                      {offset: 0, color: '#83bff6'},
                                      {offset: 0.5, color: '#188df0'},
                                      {offset: 1, color: '#188df0'}
                                  ]
                              )
                          },
                          emphasis: {
                              color: new echarts.graphic.LinearGradient(
                                  0, 0, 0, 1,
                                  [
                                      {offset: 0, color: '#2378f7'},
                                      {offset: 0.7, color: '#2378f7'},
                                      {offset: 1, color: '#83bff6'}
                                  ]
                              )
                          }
                      },
                      barGap: '-100%',
                      barCategoryGap: '40%',
                      data: dataV
                      // [220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 125, 220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 125],
                  }
              ]
          };
          myChart.setOption(option);

          myChart.on('click', function (p) {
              console.log(p);
          });
      }
    }
  }
</script>

<style lang="less">
  .failMoneyAnalysis {
    .ivu-card-body {
      flex: 1;
      overflow: auto;
      position: relative;
    }
  }

  #failMoneyAnalysis {

  }
</style>
<!--
渠道 人数
渠道 金额

渠道的 趋势
-->
