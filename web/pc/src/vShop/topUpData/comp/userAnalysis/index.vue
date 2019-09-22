<template>
  <Card class="box_col userAnalysisBox">
    <div id="userAnalysis" style="height: 100%"></div>
    <Card class="userAnalysis_lineBox">
      <div id="userAnalysis_lineBox" style="height: 100%"></div>
    </Card>
    <div class="dateoickBox">
      <DatePicker :value="value2" format="yyyy/MM/dd" type="daterange"
                  :split-panels="true" :options="dateOptions"
                  placement="bottom-end" placeholder="Select date" style="width: 100%"></DatePicker>
    </div>
  </Card>
</template>

<script>
  import echarts from 'echarts'

  export default {
    name: "index",
    data() {
      return {
        value2: [],
        dateOptions: {
          disabledDate(date) {
            return date && date.valueOf() > Date.now();
          }
        },
        dataX: [],
        dataV: []
      }
    },
    mounted() {
      this.$nextTick(() => {
        this.getData()
        // this.buildEchart()
        // this.buildEchart_line()
      })
    },
    methods: {
      getData() {
        var v = this
        this.$http.post("/api/statis/statisNewUser").then(res => {
          if (res.code == 200) {
            this.dataX = []
            this.dataV = []
            res.result.forEach((it, index) => {
              let item = it.split(',')
              this.dataX.push(item[0])
              this.dataV.push(item[1])
              if (index == res.result.length - 1) {
                v.buildEchart()
                v.buildEchart_line()
              }
            })
              console.log(this.dataV);
          }

        })
      },
      buildEchart() {
        var v = this
        var myChart = echarts.init(document.getElementById('userAnalysis'));

        let option = {
          title: {
            text: "用户新增走势",
            subtext: "",
            x: "center"
          },
          grid: {
            left: "40px",
            top: "30px",
            right: "40px",
            bottom: "60px"
          },
          xAxis: {
            data: v.dataX,
              // ['07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02'],
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
            // { // For shadow
            //   type: 'line',
            //   itemStyle: {
            //     normal: {color: '#188df0'}
            //   },
            //   data: [220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 125, 220],
            //   smooth: true,
            //   // smoothMonotone:'x',
            //   // sampling: 'min',
            //   animation: false
            // },
            {
              type: 'bar',
                label: {
                  normal: {
                      show: true,
                      position: 'top'
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
              data: v.dataV,
              // data: [220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 125, 220]
            }
          ]
        };
        myChart.setOption(option);
      },
      buildEchart_line() {
        var v = this
        var myChart = echarts.init(document.getElementById('userAnalysis_lineBox'));

        let option = {
          title: {
            show: false,
            text: "用户新增走势",
            subtext: "",
            x: "center"
          },
          grid: {
            left: "40px",
            top: '14px',
            right: '10px',
            bottom: '20px'
          },
          xAxis: {
            data: v.dataX,
              // [220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 125, 220],
            axisLabel: {
              textStyle: {
                color: '#fff'
              }
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
                color: '#fff'
              }
            },
          },
          series: [
            { // For shadow
              type: 'line',
              itemStyle: {
                normal: {
                    show: true ,
                    color: '#188df0'
                }
              },
              data: v.dataV,
                // [220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 125, 220],
              animation: false
            }
          ]
        };
        myChart.setOption(option);
      }
    }
  }
</script>

<style lang="less">
  .userAnalysisBox {
    .ivu-card-body {
      flex: 1;
      overflow: auto;
      position: relative;
    }
  }

  .userAnalysis_lineBox {
    position: absolute;
    left: 60px;
    top: 20px;
    z-index: 200;
    width: 300px;
    height: 140px;
    background: rgba(0, 0, 0, 0.4);
    .ivu-card-body {
      height: 100%;
      padding: 0;
    }
  }

  .dateoickBox {
    width: 200px;
    position: absolute;
    right: 45px;
    top: 10px;
    z-index: 200;
  }

  #userAnalysis {

  }
</style>
