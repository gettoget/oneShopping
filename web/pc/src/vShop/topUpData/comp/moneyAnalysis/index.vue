<template>
  <Card class="box_col moneyAnalysisBox">
    <div id="moneyAnalysis" style="height: 100%"></div>
  </Card>
</template>

<script>
  import echarts from 'echarts'

  export default {
    name: "index",
    mounted() {
      this.$nextTick(() => {
        // this.getData()
        this.buildEchart()
      })
    },
    methods: {
      buildEchart() {
        var v = this
        var myChart = echarts.init(document.getElementById('moneyAnalysis'));

        let option = {
          title: {
            text: "用户充值走势",
            subtext: "",
            x: "center"
          },
          grid: {
            left: 40,
            top: 30,
            right: 30,
            bottom: 30
          },
          xAxis: {
            data: ['07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02'],
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
          visualMap: {
            top: 10,
            right: 10,
            pieces: [{
              gt: 0,
              lte: 50,
              color: '#096'
            }, {
              gt: 50,
              lte: 100,
              color: '#ffde33'
            }, {
              gt: 100,
              lte: 150,
              color: '#ff9933'
            }, {
              gt: 150,
              lte: 200,
              color: '#cc0033'
            }, {
              gt: 200,
              lte: 300,
              color: '#660099'
            }, {
              gt: 300,
              color: '#7e0023'
            }],
            outOfRange: {
              color: '#999'
            }
          },
          series: [
            // { // For shadow
            //   type: 'line',
            //   itemStyle: {
            //     normal: {color: '#188df0'}
            //   },
            //   data: [220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 125, 220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 125],
            //   showSymbol: false,
            //   animation: false,
            //   markLine: {
            //     silent: true,
            //     data: [{
            //       yAxis: 50
            //     }, {
            //       yAxis: 100
            //     }, {
            //       yAxis: 150
            //     }, {
            //       yAxis: 200
            //     }, {
            //       yAxis: 300
            //     }]
            //   }
            // },
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
              data: [220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 125, 220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 125],
            }
          ]
        };
        myChart.setOption(option);
      }
    }
  }
</script>

<style lang="less">
  .moneyAnalysisBox {
    .ivu-card-body {
      flex: 1;
      overflow: auto;
      position: relative;
    }
  }

  #moneyAnalysis {

  }
</style>
