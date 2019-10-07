<template>
  <Card class="box_col chartTopUpBox">
    <div id="chartTopUp" class="box_row_1auto" style="height: 100%;"></div>
  </Card>
</template>

<script>
  import echarts from 'echarts'
    export default {
        name: "index",
      data(){
        return {

        }
      },
      mounted() {
        this.$nextTick(() => {
          this.getData();
        })
      },
      methods: {
        getData(){
          this.$http.post("/api/statisnew/getCzfb").then(res => {
            if (res.code == 200) {
              let a = [
                {
                  value: res.result[0],
                  name: "今天注册"
                },
                {
                  value: res.result[1],
                  name: "注册一天"
                },
                {
                  value: res.result[2],
                  name: "注册两天"
                },{
                  value: res.result[3],
                  name: "三天以上"
                }
              ]

              this.buildEchart(a)
            }
          })
        },
        buildEchart(a) {
          var v = this
          var myChart = echarts.init(document.getElementById('chartTopUp'));

          let option = {
            title:{
              text: '注册用户今日充值分布'
            },
            legend: {
              orient: "vertical",
              x: "left",
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
                radius: "90%",
                center: ["50%", "50%"],
                itemStyle: {
                  normal: {
                    label: {
                      show: true,
                      formatter: "{b}:{c}({d}%)",
                      textStyle: {
                        fontSize: 10
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
                animation: false,
                startAngle: 0,
                minAngle: 16,
                selectedMode: false,

              },
            ],
            color: ["rgb(136, 173, 218)", "rgb(239, 157, 119)", "rgb(153, 206, 152)", "#cd5c5c", "#ffa500", "#40e0d0", "#1e90ff", "#ff6347", "#7b68ee", "#00fa9a", "#ffd700", "#6699FF", "#ff6666", "#3cb371", "#b8860b", "#30e0e0"]
          }
          myChart.setOption(option);
        },
      }
    }
</script>

<style scoped>
  .chartTopUpBox {
  .ivu-card-body {
    flex: 1;
    overflow: auto;
  }
  }
</style>
