<template>
  <Card class="box_col chartNumberBox">
    <div slot="title" class="box_row">
      <div class="box_row_100">
        <pager-tit :title="$t('JRCZTJ')"></pager-tit>
      </div>
      <div class="box_row_100">
        <pager-tit :title="$t('LJCZTJ')"></pager-tit>
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
        this.getData()
      })
    },
    methods: {
      getData() {
        this.$http.post('/api/statis/rechargeNum').then(res => {
          if (res.code == 200) {
            let a = [
              {
                value: res.result.one,
                name: "一次"
              },
              {
                value: res.result.two,
                name: "二次"
              },
              {
                value: res.result.more,
                name: "多次"
              }
            ]

            let b = [
              {
                value: res.result.allone,
                name: "一次"
              },
              {
                value: res.result.alltwo,
                name: "二次"
              },
              {
                value: res.result.allmore,
                name: "多次"
              }
            ]
            this.buildEchart(a, b)
          }
        }).catch(err => {
        })
      },
      buildEchart(a, b) {
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
              data: a,
              //   [
              //   {
              //     value: 335,
              //     name: "一次"
              //   },
              //   {
              //     value: 310,
              //     name: "二次"
              //   },
              //   {
              //     value: 234,
              //     name: "多次"
              //   }
              // ],
              animation: false,
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
              data: b,
              //   [
              //   {
              //     value: 335,
              //     name: "一次"
              //   },
              //   {
              //     value: 310,
              //     name: "二次"
              //   },
              //   {
              //     value: 234,
              //     name: "多次"
              //   }
              // ],
              animation: false,
              startAngle: 180,
              minAngle: 0,
              selectedMode: false
            }
          ],

          color: ["rgb(136, 173, 218)", "rgb(239, 157, 119)", "rgb(153, 206, 152)", "#ff7f50", "#87cefa", "#da70d6", "#32cd32", "#6495ed", "#ff69b4", "#ba55d3", "#cd5c5c", "#ffa500", "#40e0d0", "#1e90ff", "#ff6347", "#7b68ee", "#00fa9a", "#ffd700", "#6699FF", "#ff6666", "#3cb371", "#b8860b", "#30e0e0"]
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
