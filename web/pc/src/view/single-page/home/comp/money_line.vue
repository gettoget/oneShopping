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
      return {
        times:[],
        data1:[],//充值
        data2:[],//消费
        data3:[],
        data4:[],
        data5:[],
      }
    },
    watch:{
      time:function (n,o) {
        console.log(n);
        this.getData()
      }
    },
    mounted() {
      this.$nextTick(() => {
        this.getData()
      })
    },
    methods: {
      getData(){
        this.times=[]
        this.data1=[]
        this.data2=[]
        this.data3=[]
        this.data4=[]
        this.data5=[]

        this.$http.post("/api/statisnew/statisNewWater",{day:this.time}).then(res=>{
          if(res.code == 200){
            res.result.forEach((it,index)=>{
              let a = it.split('|')
              console.log(a);
              this.times.push(a[0])
              this.data1.push(a[1])
              this.data2.push(a[2])

              if(index == res.result.length-1){
                this.buildEchart()
              }
            })

              this.data1.forEach((it,index)=>{
                  let a = it.split(',')
                  console.log(a);
                  this.data3.push(a[0])
                  this.data4.push(a[1])
                  this.data5.push(a[2])

                  if(index == res.result.length-1){
                      this.buildEchart()
                  }
              })
          }
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
              data: v.times,
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
            // {
            //   name: "充值",
            //   type: "line",
            //   data: v.data1
            // },
              {
                  name: "充值失败",
                  type: "line",
                  data: v.data3
              },
              {
                  name: "充值成功",
                  type: "line",
                  data: v.data4
              },
              {
                  name: "系统赠送",
                  type: "line",
                  data: v.data5
              },
            {
              name: "消费",
              type: "line",
              data: v.data2
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
