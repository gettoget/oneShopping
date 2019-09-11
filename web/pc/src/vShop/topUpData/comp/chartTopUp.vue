<template>
  <Card class="box_col chartTopUpBox">
    <div slot="title" class="box_row">
      <div class="box_row_100">
        <pager-tit :title="$t('JRCZQDTJ')"></pager-tit>
      </div>
      <div class="box_row_100">
        <pager-tit :title="$t('LJCZQDTJ')"></pager-tit>
      </div>
    </div>
    <div class="box_row" style="height:100%">
      <div id="chartTopUp" class="box_row_1auto" style="height: 100%">

      </div>
      <div id="chartTopUp1" class="box_row_1auto" style="height: 100%">

      </div>
    </div>

  </Card>
</template>

<script>
  import echarts from 'echarts'
  export default {
    name: "chartTopUp",
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
          this.getData1()
      })
    },
    methods: {
      getData() {

        this.$http.post('/api/statisnew/statisAllQd').then(res => {
          if(res.code == 200){
              let a = []
            for(let i in res.result){
                let b = {}
                b.value = res.result[i]
                b.name = i
                a.push(b)
            }
              console.log(a);
              this.buildEchart(a)
          }
        }).catch(err => {})
      },
        getData1() {
            this.$http.post('/api/statisnew/statisAllQd',{day:'1'}).then(res => {
                if(res.code == 200){
                    let a = []
                    for(let i in res.result){
                        let b = {}
                        b.value = res.result[i]
                        b.name = i
                        a.push(b)
                    }
                    console.log(a);
                    this.buildEchart1(a)
                }
            }).catch(err => {})
        },
      buildEchart(a) {
        var v = this
        var myChart = echarts.init(document.getElementById('chartTopUp'));

        let option = {
          legend: {
            orient: "horizontal",
            x: "lfte",
            y: "bottom",
            data: ["Alfa", "BNI","CIMB","Danamon","Mandiri","Permata"],
            selectedMode: false
          },
          toolbox: {
            show: false,
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
                      fontSize: 12
                    }
                  },
                  labelLine: {
                    show: true
                  }
                }
              },
              data:a,
              //   [
              //   {
              //     value: 335,
              //     name: "微信"
              //   },
              //   {
              //     value: 310,
              //     name: "支付宝"
              //   },
              //   {
              //     value: 234,
              //     name: "银联"
              //   }
              // ],
              animation:false,
              startAngle: 0,
              minAngle:16,
              selectedMode: false,

            },
          ],
          color: [ "rgb(136, 173, 218)", "rgb(239, 157, 119)", "rgb(153, 206, 152)", "#cd5c5c", "#ffa500", "#40e0d0", "#1e90ff", "#ff6347", "#7b68ee", "#00fa9a", "#ffd700", "#6699FF", "#ff6666", "#3cb371", "#b8860b", "#30e0e0"]
        }
        myChart.setOption(option);
      },
        buildEchart1(a) {
            var v = this
            var myChart = echarts.init(document.getElementById('chartTopUp1'));

            let option = {
                legend: {
                    orient: "horizontal",
                    x: "lfte",
                    y: "bottom",
                    data: [""],
                    selectedMode: false
                },
                toolbox: {
                    show: false,
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
                                        fontSize: 12
                                    }
                                },
                                labelLine: {
                                    show: true
                                }
                            }
                        },
                        data:a,
                        //   [
                        //   {
                        //     value: 335,
                        //     name: "微信"
                        //   },
                        //   {
                        //     value: 310,
                        //     name: "支付宝"
                        //   },
                        //   {
                        //     value: 234,
                        //     name: "银联"
                        //   }
                        // ],
                        animation:false,
                        startAngle: 0,
                        minAngle:16,
                        selectedMode: false,

                    },
                ],
                color: [ "rgb(136, 173, 218)", "rgb(239, 157, 119)", "rgb(153, 206, 152)", "#cd5c5c", "#ffa500", "#40e0d0", "#1e90ff", "#ff6347", "#7b68ee", "#00fa9a", "#ffd700", "#6699FF", "#ff6666", "#3cb371", "#b8860b", "#30e0e0"]
            }
            myChart.setOption(option);
        }
    }
  }
</script>

<style lang="less">
  .chartTopUpBox {
    .ivu-card-body {
      flex: 1;
      overflow: auto;
    }
  }
</style>
