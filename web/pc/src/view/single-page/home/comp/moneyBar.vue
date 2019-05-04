<template>
  <div :id="echartID" class="box" style="height: 100%">

  </div>
</template>

<script>
  import echarts from 'echarts'

  export default {
    name: "moneyBar",
    props: {
      echartID: {
        type: String,
        default: 'echartID'
      },
      echartTiT:{
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
            text: v.echartTiT,
            x: "center",
            y: "top"
          },
          tooltip: {
            trigger: "axis"
          },
          legend: {
            data: ["充值", "消费"],
            selectedMode: false,
            y: "top",
            x: "left",
            orient: "vertical"
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
              data: ["今日", "本月", "总量"],
              axisLine: {
                show: true
              },
              axisLabel: {
                show: true
              },
              axisTick: {
                show: false
              },
              splitArea: {
                show: false
              },
              splitLine: {
                show: false
              }
            }
          ],
          yAxis: [
            {
              type: "value",
              axisLine: {
                show: true
              },
              axisTick: {
                show: false
              },
              splitArea: {
                show: false
              },
              splitLine: {
                show: false
              }
            }
          ],
          series: [
            {
              name: "充值",
              type: "bar",
              data: [2, 4.9, 7],
              barGap: "0%",
              barCategoryGap: "15%",
              barMinHeight: 0,
              itemStyle: {
                normal: {
                  label: {
                    show: true
                  }
                }
              }
            },
            {
              name: "消费",
              type: "bar",
              data: [2.6, 5.9, 9],
              itemStyle: {
                normal: {
                  label: {
                    show: true
                  }
                }
              }
            }
          ],
          color: ["#ff7f50", "#87cefa", "#da70d6", "#32cd32", "#6495ed", "#ff69b4", "#ba55d3", "#cd5c5c", "#ffa500", "#40e0d0", "#1e90ff", "#ff6347", "#7b68ee", "#00fa9a", "#ffd700", "#6699FF", "#ff6666", "#3cb371", "#b8860b", "#30e0e0"]
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
