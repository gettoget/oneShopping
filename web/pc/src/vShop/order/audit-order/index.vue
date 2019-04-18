<template>
   <div>
     <pager-tit title="订单审核"></pager-tit>
     <div class="box_row colCenter rowRight pageFindSty" style="border: none">
       <div>
         <!--<Icon type="md-person" size="34"/>-->
         <Input
           placeholder="请输入订单编号" style="width: 200px">
         </Input>
       </div>

       <div>
         <!--<Icon type="ios-call" size="34"/>-->
         <Input
           placeholder="请输入手机号码" style="width: 200px"
         ></Input>
       </div>
       <Button type="primary">
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
                key: 'bh'
              },
              {
                title: '下单时间',
                align: 'center',
                key: 'sj'
              },
              {
                title: '下单手机号',
                align: 'center',
                key: 'sjh'
              },
              {
                title: '收货地址',
                align: 'center',
                key: 'dz',
              },
              {
                title: '订单状态',
                width: 120,
                align: 'center',
                key: 'zt'
              },
              {
                title: '订单类别',
                width: 120,
                align: 'center',
                key: 'ddzt'
              },
              {
                title: '备注',
                align: 'center',
                key: 'bz'
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
                        icon: 'md-create',
                        shape: 'circle',
                        size: 'small'
                      },
                      style: {
                        cursor: "pointer",
                        margin: '0 8px 0 0'
                      },
                      on: {
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

                        }
                      }
                    })
                  ]);
                }
              }
            ],
            tableData: [
              {
                bh:'20180909000123431',
                sj:'2018-04-01',
                sjh:'0908-1678-18766',
                dz:'sjhfjsfkjdakd',
                zt:'待审核',
                bz:'赠品111',
                ddzt:'直购'
              },
              {
                bh:'20180909000123541',
                sj:'2018-04-01',
                sjh:'0908-1678-18766',
                dz:'阿萨德',
                zt:'待审核',
                bz:'赠品211',
                ddzt:'中奖'
              }
            ],
          }
      }
    }
</script>

<style scoped>

</style>
