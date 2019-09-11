<template>
  <Card class="box_col moneyAnalysisBox">
    <div id="moneyAnalysis" style="height: 100%"></div>

    <Card class="moneyAnalysis_lineBox">
      <div id="moneyAnalysis_lineBox" style="height: 100%"></div>
    </Card>

    <Card class="qd_money_time_Analysis">
      <div id="qd_money_time_Analysis" style="height: 100%"></div>
    </Card>

    <Card class="qd_rs_Analysis_lineBox">
      <div id="qd_Analysis_lineBox" style="height: 100%"></div>
    </Card>
  </Card>
</template>

<script>
  import echarts from 'echarts'

  export default {
    name: "index",
    mounted() {
      this.$nextTick(() => {

        this.getData()
        this.getMoneyAnalysis()
        this.get_qd_money_time_Data()
        this.get_People_money()
      })
    },
    methods: {
      getData(){
        var v = this
        this.$http.post("/api/statis/statisCz",{day:"30"}).then(res => {
          if (res.code == 200) {
            let dataX = []
            let dataV = []

            res.result.forEach((it, index) => {
              let item = it.split(',')
              dataX.push(item[0])
              dataV.push(item[1])
              if (index == res.result.length - 1) {
                v.buildEchart(dataX,dataV)
              }
            })
          }
        })
      },
      buildEchart(dataX,dataV,data1,data2,data3,data4,data5,data6) {
        var v = this
        var myChart = echarts.init(document.getElementById('moneyAnalysis'));

        let option = {
          title: {
            text: "用户充值走势",
            subtext: "",
            x: "center"
          },
          grid: {
            left: 40,
            top: '200px',
            right: 30,
            bottom: 30
          },
          xAxis: {
            data: dataX,
              // ['07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01', '07/02'],
            axisLabel: {
              inside: false,
              textStyle: {
                color: '#999'
              },
              rotate: -40
            },
            axisTick: {
              show: false
            },
            axisLine: {
              show: false
            },
            z: 10
          },
          yAxis: {
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            axisLabel: {
              textStyle: {
                color: '#999'
              }
            }
          },
          series: [
            {
              type: 'bar',
              label: {
                normal: {
                  show: true,
                  position: "top",
                }
              },
              itemStyle: {
                normal: {
                  color: new echarts.graphic.LinearGradient(
                    0, 0, 0, 1,
                    [
                      {offset: 0, color: '#83bff6'},
                      {offset: 0.5, color: '#188df0'},
                      {offset: 1, color: '#188df0'}
                    ]
                  )
                },
                emphasis: {
                  color: new echarts.graphic.LinearGradient(
                    0, 0, 0, 1,
                    [
                      {offset: 0, color: '#2378f7'},
                      {offset: 0.7, color: '#2378f7'},
                      {offset: 1, color: '#83bff6'}
                    ]
                  )
                }
              },
              barGap: '-100%',
              barCategoryGap: '40%',
              data: dataV
                // [220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 125, 220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 220, 182, 191, 234, 290, 330, 310, 123, 442, 321, 90, 149, 210, 122, 133, 334, 198, 123, 125],
            }
          ]
        };
        myChart.setOption(option);

        myChart.on('click', function (p) {
          console.log(p);
        });
      },

      getMoneyAnalysis(){
        var v = this
        this.$http.post("/api/statisnew/statisCzqd",{day:"30"}).then(res=>{
          let dataX = []
          let YL = []
          let JF = []
            let data1 = [] //CIMB
            let data2 = [] //Danamon
            let data3 = [] //Alfa
            let data4 = [] //Permata
            let data5 = [] //BNI
            let data6 = [] //Mandiri
          if(res.code == 200 ){
            res.result.forEach((it, index) => {
              let item = it.split(',')
              dataX.push(item[0])
              YL.push(item[1])
              JF.push(item[2])
                data1.push(item[1])
                data2.push(item[2])
                data3.push(item[3])
                data4.push(item[4])
                data5.push(item[5])
                data6.push(item[6])
              if (index == res.result.length - 1) {
                v.buildMoneyAnalysis(dataX,YL,JF,'30',data1,data2,data3,data4,data5,data6)
              }
            })
          }

        }).catch(err=>{})
      },
      buildMoneyAnalysis(dataX,YL,JF,time,data1,data2,data3,data4,data5,data6){
        var v = this
        var myChart = echarts.init(document.getElementById('moneyAnalysis_lineBox'));
        let option = {
          title: {
            show:true,
            text: '',
            x: "left",
            textStyle: {
              fontSize: 14,
              color:"#fff"
            }
          },
          legend: {
            data:['CIMB','Danamon',"Alfa","Permata","BNI","Mandiri"],
            x: "right",
            y: "top"
          },
          tooltip: {
            trigger: "axis",
            position: function (pos, params, el, elRect, size) {
              var obj = {top: 2};
              obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 30;
              return obj;
            }
          },
          grid: {
            left: '20px',
            right: '20px',
            bottom: "10px",
            top:"30px",
            containLabel: true
          },
          xAxis: {
            axisLabel: {
              textStyle: {
                color: '#fff'
              }
            },
            axisTick: {
              show: false
            },
            axisLine: {
              show: false
            },
            boundaryGap: false,
            data: dataX
              // ['07/01', '07/02', '07/01', '07/02', '07/01', '07/02', '07/01']
          },
          yAxis: {
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            axisLabel: {
              textStyle: {
                color: '#fff'
              }
            },
          },
          series: [
            {
              name:'CIMB',
              type:'line',
              data:data1
                // [320, 332, 301, 334, 390, 330, 320]
            },
            {
              name:'Danamon',
              type:'line',
              data:data2
                // [150, 232, 201, 154, 190, 330, 410]
            },
              {
                  name:'Alfa',
                  type:'line',
                  data:data3
                  // [150, 232, 201, 154, 190, 330, 410]
              },
              {
                  name:'Permata',
                  type:'line',
                  data:data4
                  // [150, 232, 201, 154, 190, 330, 410]
              },
              {
                  name:'BNI',
                  type:'line',
                  data:data5
                  // [150, 232, 201, 154, 190, 330, 410]
              },
              {
                  name:'Mandiri',
                  type:'line',
                  data:data6
                  // [150, 232, 201, 154, 190, 330, 410]
              },
          ],
          color: ["#ff7f50", "#87cefa", "#da70d6", "#32cd32", "#6495ed", "#ff69b4", "#ba55d3", "#cd5c5c", "#ffa500", "#40e0d0", "#1e90ff", "#ff6347", "#7b68ee", "#00fa9a", "#ffd700", "#6699FF", "#ff6666", "#3cb371", "#b8860b", "#30e0e0"]
        };
        myChart.setOption(option);

      },

      get_qd_money_time_Data(T){
        var v = this
        this.$http.post("/api/statis/statisCzjb",{time:T?T:''}).then(res=>{
          if(res.code == 200){
            console.log('tttttttttt',res.result);

            v.build_qd_money_time_Analysis(res.result,T)
          }

        }).catch(err=>{})
      },

      build_qd_money_time_Analysis(mess,time){
        var v = this
        var myChart = echarts.init(document.getElementById('qd_money_time_Analysis'));

        let option = {
          title: {
            show:true,
            text: time?time:v.moment(new Date()).format('MM-DD')  +'充值人数、次数统计',
            x: "left",
            textStyle: {
              fontSize: 14,
              color:"#fff"
            }
          },
          tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
              type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            position: function (pos, params, el, elRect, size) {
              var obj = {top: 10};
              obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 30;
              return obj;
            },
          },
          legend: {
            data: ['一次', '两次','多次'],
            x: "right",
            top:"2px",
            itemWidth: 12,
            itemHeight: 8,
            textStyle: {
              fontSize: 10
            }
          },
          grid: {
            left: '15px',
            right: '15px',
            bottom: "10px",
            top:"30px",
            containLabel: true
          },
          xAxis:  {
            type: 'value',
            containLabel: true,
            axisLabel: {
              textStyle: {
                color: '#fff'
              }
            },
            axisTick: {
              show: false
            },
            axisLine: {
              show: false
            }
          },
          yAxis: {
            type: 'category',
            data: ['金额'],
            axisLabel: {
              textStyle: {
                color: '#fff'
              }
            },
            axisTick: {
              show: false
            },
            axisLine: {
              show: false
            }
          },
          series: [
            {
              name: '一次',
              type: 'bar',
              stack: '总量',
              label: {
                normal: {
                  show: true,
                  position: 'insideRight',
                  formatter: function () {
                    return mess[0].split(',')[0]+"元\n"+mess[0].split(',')[1]+"人"
                  }
                }
              },
              data: [mess[0].split(',')[0]]
            },
            {
              name: '两次',
              type: 'bar',
              stack: '总量',
              label: {
                normal: {
                  show: true,
                  position: 'insideRight',
                  formatter: function () {
                    return mess[1].split(',')[0]+"元\n"+mess[1].split(',')[1]+"人"
                  }
                }
              },
              data: [mess[1].split(',')[0]]
            },
            {
              name: '多次',
              type: 'bar',
              stack: '总量',
              label: {
                normal: {
                  show: true,
                  position: 'insideRight',
                  formatter: function () {
                    return mess[2].split(',')[0]+"元\n"+mess[2].split(',')[1]+"人"
                  }
                }
              },
              data: [mess[2].split(',')[0]]
            }
          ]
        };

        myChart.setOption(option);
      },

      get_People_money(T){
        var v = this
        this.$http.post("/api/statisnew/statisQdJe").then(res=>{
          let people = []
          let money = []
          if(res.code == 200){
              for (var i in res.result) {
                  money.push(res.result[i].split(',')[0])
                  people.push(res.result[i].split(',')[1])
              }

            v.buildqd_qd_Analysis(people,money)
          }
        }).catch(err=>{})
      },
      buildqd_qd_Analysis(people,money,time){
        var v = this
        var myChart = echarts.init(document.getElementById('qd_Analysis_lineBox'));
        let option = {
          title: {
            show:true,
            text: time?time:v.moment(new Date()).format('YYYY-MM-DD')  +'各渠道充值人数、金额统计',
            x: "left",
            textStyle: {
              fontSize: 14,
              color:"#fff"
            }
          },
          legend: {
            data:['人数','金额'],
            x: "right",
            y: "top"
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            },
            position: function (pos, params, el, elRect, size) {
              var obj = {top: 10};
              obj[['left', 'right'][+(pos[0] < size.viewSize[0] / 2)]] = 30;
              return obj;
            },
          },
          grid: {
            left: '15px',
            right: '15px',
            bottom: "10px",
            top:"30px",
            containLabel: true
          },
          xAxis: [
            {
              data: ["Alfa", "BNI","CIMB","Danamon","Mandiri","Permata"],
              axisLabel: {
                  show:true,
                  rotate:69,
                textStyle: {
                  color: '#fff'
                }
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              }
            }
          ],
          yAxis: [
            {
              type: 'value',
              min: 0,
              max: 99,
              axisLine: {
                show: false
              },
              axisTick: {
                show: false
              },
              axisLabel: {
                formatter: '{value} 人',
                textStyle: {
                  color: '#fff'
                }
              }
            },
            {
              type: 'value',
              min: 0,
              max: 99,
              axisLine: {
                show: false
              },
              axisTick: {
                show: false
              },
              axisLabel: {
                formatter: '{value} 元',
                textStyle: {
                  color: '#fff'
                }
              }
            }
          ],
          series: [
            {
              name:'人数',
              type:'bar',
              data:people,
                barWidth: 10
                // [35,50]
            },
            {
              name:'金额',
              type:'bar',
              data:money,
                barWidth: 10
                // [67,65]
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
  .moneyAnalysisBox {
    .ivu-card-body {
      flex: 1;
      overflow: auto;
      position: relative;
    }
  }
  .moneyAnalysis_lineBox{
    position: absolute;
    left: 55px;
    top: 40px;
    z-index: 200;
    width: 400px;
    height: 180px;
    background: rgba(0,0,0,0.4);
    .ivu-card-body {
      height: 100%;
      padding: 0;
    }
  }
  .qd_rs_Analysis_lineBox{
    position: absolute;
    right: 45px;
    top: 40px;
    z-index: 200;
    width: 400px;
    height: 180px;
    background: rgba(0,0,0,0.4);
    .ivu-card-body {
      height: 100%;
      padding: 0;
    }
  }

  .qd_money_time_Analysis{
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    top: 40px;
    z-index: 200;
    width: 350px;
    height: 180px;
    background: rgba(0,0,0,0.4);
    .ivu-card-body {
      height: 100%;
      padding: 0;
    }
  }

  #moneyAnalysis {

  }
</style>
<!--
渠道 人数
渠道 金额

渠道的 趋势
-->
