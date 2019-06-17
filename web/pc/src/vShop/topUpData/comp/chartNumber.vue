<template>
  <Card class="box_col chartNumberBox">
    <div slot="title" class="box_row">
      <div class="box_row_100">
        <pager-tit title="今日充值统计"></pager-tit>
      </div>
      <div class="box_row_100">
        <pager-tit title="累计充值统计"></pager-tit>
      </div>
    </div>
    <div id="chartNumber" style="height: 100%">

    </div>
  </Card>
</template>

<script>
  import echarts from 'echarts'
  export default {
    name: "chartNumber",
    data() {
      return {}
    },
    mounted() {
      this.$nextTick(() => {
        // try {
        //   this.GetTabTitLang()
        //   this.tab_H = this.AF.getDom_H(this.tabBox)
        // } catch (e) {}
        this.buildEchart()
      })
    },
    methods: {
      buildEchart() {
        var v = this
        var myChart = echarts.init(document.getElementById('chartNumber'));

        let option = {
          legend: {
            orient: "horizontal",
            x: "right",
            y: "bottom",
            data: ["一次", "二次", "多次"],
            selectedMode: false
          },
          toolbox: {
            show: false
          },
          calculable: false,
          series: [
            {
              name: "",
              type: "pie",
              radius: ["0%", "50%"],
              center: ["25%", "50%"],
              itemStyle: {
                normal: {
                  label: {
                    show: true,
                    formatter: "{b}:\n{c}\n({d}%)",
                    textStyle: {
                      fontSize: 16
                    }
                  },
                  labelLine: {
                    show: true
                  }
                }
              },
              data: [
                {
                  value: 335,
                  name: "一次"
                },
                {
                  value: 310,
                  name: "二次"
                },
                {
                  value: 234,
                  name: "多次"
                }
              ],
              animation:false,
              startAngle: 135,
              minAngle: 0,
              selectedMode: false
            },
            {
              name: "",
              type: "pie",
              radius: ["0%", "50%"],
              center: ["75%", "50%"],
              itemStyle: {
                normal: {
                  label: {
                    show: true,
                    formatter: "{b}:\n{c}\n({d}%)",
                    textStyle: {
                      fontSize: 16
                    }
                  },
                  labelLine: {
                    show: true
                  }
                }
              },
              data: [
                {
                  value: 335,
                  name: "一次"
                },
                {
                  value: 310,
                  name: "二次"
                },
                {
                  value: 234,
                  name: "多次"
                }
              ],
              animation:false,
              startAngle: 180,
              minAngle: 0,
              selectedMode: false
            }
          ],

          color: [ "rgb(136, 173, 218)", "rgb(239, 157, 119)", "rgb(153, 206, 152)","#ff7f50", "#87cefa", "#da70d6", "#32cd32", "#6495ed", "#ff69b4", "#ba55d3", "#cd5c5c", "#ffa500", "#40e0d0", "#1e90ff", "#ff6347", "#7b68ee", "#00fa9a", "#ffd700", "#6699FF", "#ff6666", "#3cb371", "#b8860b", "#30e0e0"]
        }
        myChart.setOption(option);
      }
    }
  }
</script>

<style lang="less">
  .chartNumberBox {
    .ivu-card-body {
      flex: 1;
      overflow: auto;
    }
  }
</style>
