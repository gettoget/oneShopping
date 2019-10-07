<template>
  <Card class="box_col userAnalysisBox3">
    <div id="userAnalysis_lineBox3" style="height: 100%;padding-top: 20px">
    </div>
  </Card>
</template>

<script>
  import echarts from 'echarts'

  export default {
        name: "index",
      data() {
        return {
          dataX: [],
          dataV: []
        }
      },
      mounted() {
        this.$nextTick(() => {
          this.getData();
        })
      },
      methods: {
        getData() {
          var v = this
          this.$http.post("/api/statisnew/getHyqd").then(res => {
            if (res.code == 200) {
              this.dataX = []
              this.dataV = []
              res.result.forEach((it, index) => {
                let item = it.split(',')
                this.dataX.push(item[0])
                this.dataV.push(item[1])
                if (index == res.result.length - 1) {
                  v.buildEchart_line()
                }
              })
              console.log(this.dataV);
            }
          })
        },
        buildEchart_line() {
          var v = this
          var myChart = echarts.init(document.getElementById('userAnalysis_lineBox3'));

          let option = {
            title: {
              show: true,
              text: "当日注册用户活跃强度",
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
                  color: '#1283ff'
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
                  color: '#ffca22'
                }
              },
            },
            series: [
              { // For shadow
                type: 'line',
                itemStyle: {
                  normal: {
                    show: true,
                    color: '#f03f3d'
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
  .userAnalysisBox3 {
  .ivu-card-body {
    flex: 1;
    overflow: auto;
    position: relative;
  }
  }
</style>
