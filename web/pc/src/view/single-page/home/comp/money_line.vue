<template>
  <div class="box_col">
    <div :id="echartID" class="box_col_100">

    </div>
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
      },
      time:{
        type:String,
        default:"7"
      }
    },
    data() {
      return {}
    },
    watch:{
      time:function (n,o) {
        console.log(n);
      }
    },
    mounted() {
      this.$nextTick(() => {
        this.getData()
        this.buildEchart()
      })
    },
    methods: {
      getData(){
        this.$http.post("/api/statis/rechargeWater",{day:this.time}).then(res=>{
          console.log(res);
        }).catch(err=>{})
      },

      buildEchart(){
        var v = this
        var myChart = echarts.init(document.getElementById(this.echartID));

        let option ={
          title: {
            show:false,
            text: "充值/消费"
          },
          grid: {
            top: 50,
            left: 20,
            right: 20,
            bottom: 18,
            containLabel: true
          },
          tooltip: {
            trigger: "axis"
          },
          legend: {
            data: ["充值", "消费"],
            left:0,
            top:0,
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
              data: ["07.01", "07.02", "07.03", "07.04", "07.05", "07.06", "07.06"],
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
              },
              axisTick: {
                show: false
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
      }
    }
  }
</script>

<style scoped>

</style>
