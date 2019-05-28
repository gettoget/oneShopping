<template>
   <div>
     <pager-tit title="订单查询"></pager-tit>
     <div class="box_row colCenter rowRight pageFindSty" style="border: none">
       <div>
         <!--<Icon type="ios-call" size="34"/>-->
         <DatePicker v-model="param.cjsjLike" value='yyyy-MM-dd' @on-change="changTime" type="daterange" :options="options2" placement="bottom-end" placeholder="Select date" style="width: 200px"></DatePicker>
<!--         <DatePicker  v-model="param.cjsjLike" format="yyyy-MM-dd" type="daterange" split-panels placeholder="Select date" style="width: 200px"></DatePicker>-->
       </div>
       <div>
         <!--<Icon type="md-person" size="34"/>-->
         <Input
           v-model="param.idLike"
           placeholder="请输入订单编号" style="width: 200px">
         </Input>
       </div>
       <Button type="primary" @click="getPagerList">
         <Icon type="md-search"></Icon>
         <!--查询-->
       </Button>
     </div>
     <Row style="position: relative;padding: 5px">
       <Table
         size='large' stripe
         :height="AF.getPageHeight()-320"
         :columns="tableTiT"
         :data="tableData"></Table>
     </Row>
     <div class="pagerBoxSty boxMar_T box_row rowRight">
       <one-page :total="total" :size="param.pageSize"
                 :opts="[4,8,12,16]"
                 @chPager="getPagerList"></one-page>
     </div>
     <component
       :is="compName"
       :usermes="usermes"
       ></component>
   </div>
</template>

<script>
  import mess from './comp/mess'
    export default {
      name: "index",
      components:{
        mess
      },
      data(){
          return{
            total:'',
            usermes: {},
            userMesType: true,
            compName:'',
            tableTiT: [{
              title: "序号",
              width: 80,
              align: 'center',
              type: 'index'
            },
              {
                title: '订单编号',
                align: 'center',
                key: 'id'
              },
              {
                title: '下单时间',
                align: 'center',
                key: 'cjsj'
              },
              {
                title: '用户名',
                align: 'center',
                key: 'userName'
              },
              {
                title: '商品名',
                align: 'center',
                key: 'proName',
              },
              {
                title: '订单状态',
                width: 120,
                align: 'center',
                key: 'ddzt'
              },
              {
                title: '支付金额',
                width: 120,
                align: 'center',
                key: 'zfje'
              },
              {
                title: '支付时间',
                align: 'center',
                key: 'zfsj'
              },
              {
                title: '操作',
                key: 'action',
                width: 180,
                align: 'center',
                render: (h, params) => {
                  return h('div', [
                    h('Button', {
                      props: {
                        type: 'success',
                        icon: 'md-checkmark',
                        shape: 'circle',
                        size: 'small'
                      },
                      style: {
                        cursor: "pointer",
                        margin: '0 8px 0 0'
                      },
                      on: {
                        click: () => {
                          this.compName = mess
                        }
                      }
                    }),
                    h('Button', {
                      props: {
                        type: 'primary',
                        icon: 'md-menu',
                        shape: 'circle',
                        size: 'small'
                      },
                      style: {
                        cursor: "pointer",
                        margin: '0 8px 0 0'
                      },
                      on: {
                        click: () => {
                            this.compName = mess
                        }
                      }
                    }),
                    h('Button', {
                      props: {
                        type: 'error',
                        icon: 'md-close',
                        shape: 'circle',
                        size: 'small'
                      },
                      style: {
                        cursor: "pointer",
                        margin: '0 8px 0 0'
                      },
                      on: {
                        click: () => {
                          this.$Modal.warning({
                            title: '驳回订单',
                            content: '确定驳回此订单?',
                          });
                        }
                      }
                    })
                  ]);
                }
              }
            ],
            tableData: [],
            param:{
              idLike:'',
              cjsjLike:'',
              pageNum: 1,
              pageSize: 8
            },
            options2: {
              shortcuts: [
                {
                  text: '1 week',
                  value () {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                    return [start, end];
                  }
                },
                {
                  text: '1 month',
                  value () {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                    return [start, end];
                  }
                },
                {
                  text: '3 months',
                  value () {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                    return [start, end];
                  }
                }
              ]
            }
          }
      },
      created(){
        this.getPagerList()
      },
      methods:{
        changTime(value){
          this.param.cjsjGte = value[0]
          this.param.cjsjLte = value[1]
          this.getPagerList()
        },
        getPagerList(){
          this.$http.post(this.apis.ORDER.QUERY,this.param).then((res)=>{
            if (res.code == 200){
              this.tableData = res.page.list
              this.param.pageNum = res.page.pageNum
              this.param.pageSize = res.page.pageSize
              this.total = res.page.total
            }
          })
        },
      }
    }
</script>

<style scoped>

</style>
