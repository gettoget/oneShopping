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
          title: {
            text: "未来一周气温变化"
          },
          tooltip: {
            trigger: "axis"
          },
          legend: {
            data: ["最高气温", "最低气温"]
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
                show: false
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
                show: false
              }
            }
          ],
          series: [
            {
              name: "最高气温",
              type: "line",
              data: [11, 11, 15, 13, 12, 13, 10]
            },
            {
              name: "最低气温",
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
