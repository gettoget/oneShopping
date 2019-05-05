<template>
  <div :id="echartID" class="box" style="height: 100%">

  </div>
</template>

<script>
  import echarts from 'echarts'

  export default {
    name: "money_line",
    props: {
      echartID: {
        type: String,
        default: 'echartID'
      },
      echartTiT: {
        type: String,
        default: 'TiT'
      }
    },
    data() {
      return {}
    },
    mounted() {
      var v = this
      this.$nextTick(() => {
        var myChart = echarts.init(document.getElementById(this.echartID));


        let option = {
          grid: {
            top: 30,
            left: 18,
            right: 0,
            bottom: 18,
            containLabel: true
          },
          title: {
            text: "消费走势"
          },
          tooltip: {
            trigger: "axis"
          },
          legend: {
            data: [{
              name: '充值',
              // 强制设置图形为圆。
              icon: 'circle'
            },{
              name: '消费',
              // 强制设置图形为圆。
              icon: 'circle'
            },
            ],
            orient:'vertical',

          },
          toolbox: {
            show: false,
            feature: {
              mark: {
                show: true
              },
              dataView: {
                show: true,
                readOnly: true
              },
              magicType: {
                show: false,
                type: ["line", "bar"]
              },
              restore: {
                show: true
              },
              saveAsImage: {
                show: true
              }
            }
          },
          calculable: true,
          xAxis: [
            {
              type: "category",
              boundaryGap: false,
              data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"],
              axisLine: {
                show: true
              },
              axisTick: {
                show: false
              }
            }
          ],
          yAxis: [
            {
              type: "value",
              position: "left",
              nameLocation: "end",
              axisLine: {
                show: true
              }
            }
          ],
          series: [
            {
              name: "充值",
              type: "line",
              data: [11, 11, 15, 13, 12, 13, 10]
            },
            {
              name: "消费",
              type: "line",
              data: [1, 6, 2, 5, 3, 2, 0]
            }
          ],
          color: [
            "#1e90ff",
            "#32cd32",
            "#ba55d3",
            "#cd5c5c",
            "#da70d6",
            "#ff7f50",
            "#87cefa",
            "#6495ed",
            "#ff69b4",
            "#ffa500",
            "#40e0d0",
            "#ff6347", "#7b68ee", "#00fa9a", "#ffd700", "#6699FF", "#ff6666", "#3cb371", "#b8860b", "#30e0e0"],
          animation: false
        }
        myChart.setOption(option);
      })
    },
    methods: {
    }
  }
</script>

<style scoped>

</style>
