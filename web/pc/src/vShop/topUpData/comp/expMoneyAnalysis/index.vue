<template>
  <Card class="box_col expMoneyAnalysisBox">
    <div id="expMoneyAnalysis" style="height: 100%"></div>

    <Card class="expMoneyAnalysis_lineBox">
      <div id="expMoneyAnalysis_lineBox" style="height: 100%"></div>
    </Card>

    <Card class="exp_qd_rs_Analysis_lineBox">
      <div id="exp_qd_Analysis_lineBox" style="height: 100%"></div>
    </Card>

    <Card class="exp_qd_money_time_Analysis">
      <div id="exp_qd_money_time_Analysis" style="height: 100%"></div>
    </Card>
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
        this.buildMoneyAnalysis()
        this.buildqd_qd_Analysis()

        this.build_qd_money_time_Analysis()
      })
    },
    methods: {
      buildEchart() {
        var v = this
        var myChart = echarts.init(document.getElementById('expMoneyAnalysis'));

        let option = {
          title: {
            text: "用户消费走势",
            subtext: "",
            x: "center"
          },
          grid: {
            left: 40,
            top: '200px',
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
          // visualMap: {
          //   top: 10,
          //   left: 10,
          //   pieces: [{
          //     gt: 0,
          //     lte: 50,
          //     color: '#096'
          //   }, {
          //     gt: 50,
          //     lte: 100,
          //     color: '#ffde33'
          //   }, {
          //     gt: 100,
          //     lte: 150,
          //     color: '#ff9933'
          //   }, {
          //     gt: 150,
          //     lte: 200,
          //     color: '#cc0033'
          //   }, {
          //     gt: 200,
          //     lte: 300,
          //     color: '#660099'
          //   }, {
          //     gt: 300,
          //     color: '#7e0023'
          //   }],
          //   outOfRange: {
          //     color: '#999'
          //   }
          // },
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

        myChart.on('click', function (p) {
          console.log(p);
        });
      },

      buildMoneyAnalysis(){
        var v = this
        var myChart = echarts.init(document.getElementById('expMoneyAnalysis_lineBox'));
        let option = {
          title: {
            show:true,
            text: '渠道消费走势',
            x: "left",
            textStyle: {
              fontSize: 14,
              color:"#fff"
            }
          },
          legend: {
            data:['银联','微信'],
            x: "right",
            y: "top"
          },
          grid: {
            left: '20px',
            right: '5px',
            bottom: "10px",
            top:"30px",
            containLabel: true
          },
          xAxis: {
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
            boundaryGap: false,
            data: ['07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01']
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
            {
              name:'微信',
              type:'line',
              data:[150, 232, 201, 154, 190, 330, 410]
            },
            {
              name:'银联',
              type:'line',
              data:[320, 332, 301, 334, 390, 330, 320]
            }
          ],
          color: ["#ff7f50", "#87cefa", "#da70d6", "#32cd32", "#6495ed", "#ff69b4", "#ba55d3", "#cd5c5c", "#ffa500", "#40e0d0", "#1e90ff", "#ff6347", "#7b68ee", "#00fa9a", "#ffd700", "#6699FF", "#ff6666", "#3cb371", "#b8860b", "#30e0e0"]
        };
        myChart.setOption(option);

      },

      buildqd_qd_Analysis(){
        var v = this
        var myChart = echarts.init(document.getElementById('exp_qd_Analysis_lineBox'));

        let option = {
          title: {
            show:true,
            text: '2019/09/09 各渠道消费人数、金额统计',
            x: "left",
            textStyle: {
              fontSize: 14,
              color:"#fff"
            }
          },
          legend: {
            data:['人数','金额'],
            x: "right",
            y: "top"
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            },
            position: function (pos, params, el, elRect, size) {
              var obj = {top: 10};
              obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 30;
              return obj;
            },
          },
          grid: {
            left: '15px',
            right: '15px',
            bottom: "10px",
            top:"30px",
            containLabel: true
          },
          xAxis: [
            {
              data: ['支付宝','微信','银联'],
              // axisPointer: {
              //   type: 'shadow'
              // },
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
              }
            }
          ],
          yAxis: [
            {
              type: 'value',
              min: 0,
              max: 99,
              axisLine: {
                show: false
              },
              axisTick: {
                show: false
              },
              axisLabel: {
                formatter: '{value} 人',
                textStyle: {
                  color: '#fff'
                }
              }
            },
            {
              type: 'value',
              min: 0,
              max: 99,
              axisLine: {
                show: false
              },
              axisTick: {
                show: false
              },
              axisLabel: {
                formatter: '{value} 元',
                textStyle: {
                  color: '#fff'
                }
              }
            }
          ],
          series: [
            {
              name:'人数',
              type:'bar',
              data:[35,50,66]
            },
            {
              name:'金额',
              type:'bar',
              data:[67,65,54]
            }
          ],
          color: [ "rgb(136, 173, 218)", "rgb(239, 157, 119)", "rgb(153, 206, 152)", "#cd5c5c", "#ffa500", "#40e0d0", "#1e90ff", "#ff6347", "#7b68ee", "#00fa9a", "#ffd700", "#6699FF", "#ff6666", "#3cb371", "#b8860b", "#30e0e0"]
        }
        myChart.setOption(option);
      },

      build_qd_money_time_Analysis(){
        var v = this
        var myChart = echarts.init(document.getElementById('exp_qd_money_time_Analysis'));

        let option = {
          title: {
            show:true,
            text: '09/09 消费人数、次数统计',
            x: "left",
            textStyle: {
              fontSize: 14,
              color:"#fff"
            }
          },
          tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
              type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            position: function (pos, params, el, elRect, size) {
              var obj = {top: 10};
              obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 30;
              return obj;
            },
          },
          legend: {
            data: ['一次', '两次','多次'],
            x: "right",
            top:"2px",
            itemWidth: 12,
            itemHeight: 8,
            textStyle: {
              fontSize: 10
            }
          },
          grid: {
            left: '15px',
            right: '15px',
            bottom: "10px",
            top:"30px",
            containLabel: true
          },
          xAxis:  {
            type: 'value',
            containLabel: true,
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
            }
          },
          yAxis: {
            type: 'category',
            data: ['金额'],
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
            }
          },
          series: [
            {
              name: '一次',
              type: 'bar',
              stack: '总量',
              label: {
                normal: {
                  show: true,
                  position: 'insideRight',
                  formatter: "{c} 元\n30人"
                }
              },
              data: [320]
            },
            {
              name: '两次',
              type: 'bar',
              stack: '总量',
              label: {
                normal: {
                  show: true,
                  position: 'insideRight',
                  formatter: "{c} 元\n30人"
                }
              },
              data: [120]
            },
            {
              name: '多次',
              type: 'bar',
              stack: '总量',
              label: {
                normal: {
                  show: true,
                  position: 'insideRight',
                  formatter: "{c} 元\n30人"
                }
              },
              data: [220]
            }
          ]
        };

        myChart.setOption(option);
      }
    }
  }
</script>

<style lang="less">
  .expMoneyAnalysisBox {
    .ivu-card-body {
      flex: 1;
      overflow: auto;
      position: relative;
    }
  }
  .expMoneyAnalysis_lineBox{
    position: absolute;
    right: 45px;
    top: 40px;
    z-index: 200;
    width: 400px;
    height: 160px;
    background: rgba(0,0,0,0.4);
    .ivu-card-body {
      height: 100%;
      padding: 0;
    }
  }
  .exp_qd_rs_Analysis_lineBox{
    position: absolute;
    left: 55px;
    top: 40px;
    z-index: 200;
    width: 400px;
    height: 160px;
    background: rgba(0,0,0,0.4);
    .ivu-card-body {
      height: 100%;
      padding: 0;
    }
  }

  .exp_qd_money_time_Analysis{
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    top: 40px;
    z-index: 200;
    width: 350px;
    height: 160px;
    background: rgba(0,0,0,0.4);
    .ivu-card-body {
      height: 100%;
      padding: 0;
    }
  }

  #moneyAnalysis {

  }
</style>
<!--
渠道 人数
渠道 金额

渠道的 趋势
-->
