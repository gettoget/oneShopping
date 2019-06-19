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
    <div id="chartTopUp" style="height: 100%">

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
      })
    },
    methods: {
      getData() {
        this.$http.post('/api/statis/rechargeChannel').then(res => {
          if(res.code == 200){
            res.result.all.forEach((it,index)=>{
              it.name=it.czqd=="1"?'微信支付':'银联支付',
                it.value=it.cz
            })

            res.result.today.forEach((it,index)=>{
              it.name=it.czqd=="1"?'微信支付':'银联支付',
                it.value=it.cz
            })

            this.buildEchart(res.result.today,res.result.all)
          }
        }).catch(err => {})
      },
      buildEchart(a,b) {
        var v = this
        var myChart = echarts.init(document.getElementById('chartTopUp'));

        let option = {
          legend: {
            orient: "horizontal",
            x: "lfte",
            y: "bottom",
            data: ["微信支付", "银联支付"],
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
                      fontSize: 16
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
              data:b,
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
              startAngle: 45,
              minAngle: 0,
              selectedMode: false
            }
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
