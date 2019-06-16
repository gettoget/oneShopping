<template>
  <Card class="box_col allDataBox">
    <!--<div slot="title" class="box_row">-->
      <!--<div class="box_row_100">-->
        <!--<pager-tit title="今日充值渠道统计"></pager-tit>-->
      <!--</div>-->
      <!--<div class="box_row_100">-->
        <!--<pager-tit title="累计充值渠道统计"></pager-tit>-->
      <!--</div>-->
    <!--</div>-->
    <div id="allData" style="height: 100%">

    </div>
  </Card>
</template>

<script>
  import echarts from 'echarts'
  export default {
    name: "allData",
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
        var myChart = echarts.init(document.getElementById('allData'));

        let option = {
          title: {
            text: "充值趋势图",
            y: "top",
            x: "center"
          },
          tooltip: {
            trigger: "axis",
            show: true
          },
          legend: {
            data: ["1次", "2次", ">2次"],
            x: "right",
            y: "center",
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
              data: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
              axisLine: {
                lineStyle: {
                  color: "rgb(0, 0, 0)",
                  width: 1
                }
              },
              splitLine: {
                show: false
              },
              axisLabel: {
                show: true
              },
              axisTick: {
                show: true,
                inside: false
              }
            }
          ],
          yAxis: [
            {
              type: "value",
              axisTick: {
                show: false
              },
              axisLine: {
                lineStyle: {
                  color: "rgb(0, 0, 0)",
                  width: 1
                }
              }
            }
          ],
          series: [
            {
              name: "1次",
              type: "bar",
              data: [2, 4.9, 7, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20, 6.4, 3.3]
            },
            {
              name: "2次",
              type: "bar",
              data: [2.6, 5.9, 9, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6, 2.3]
            },
            {
              type: "bar",
              name: ">2次",
              data: [2, 4.9, 7, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20, 6.4, 3.3]
            }
          ],
          color: ["rgb(100, 207, 219)", "rgb(243, 114, 23)", "rgb(162, 76, 224)"]
        }
        myChart.setOption(option);
      }
    }
  }
</script>

<style lang="less">
  .allDataBox {
    .ivu-card-body {
      flex: 1;
      overflow: auto;
    }
  }
</style>
