<template>
  <div :id="echartID" class="box" style="height: 100%">

  </div>
</template>

<script>
  import echarts from 'echarts'

  export default {
    name: "newShow_D",
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
            x: "center"
          },
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
          },
          legend: {
            orient: "vertical",
            data: ["已开奖", "未开奖"],
            x: "left",
            y: "top"
          },
          series: [
            {
              name: "访问来源",
              type: "pie",
              radius: "55%",
              center: ["50%", "60%"],
              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                normal: {
                  label: {
                    show: false,
                    formatter: "{b}: {c} ({d}%)"
                  }
                }
              },
              data: [
                {
                  value: 335,
                  name: "已开奖"
                },
                {
                  value: 1548,
                  name: "未开奖"
                }
              ]
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
