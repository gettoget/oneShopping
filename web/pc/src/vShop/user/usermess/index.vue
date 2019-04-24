<template>
    <div>
      <pager-tit title="用户查询"></pager-tit>
      <div class="box_row colCenter rowRight pageFindSty" style="border: none">
        <div>
          <!--<Icon type="md-person" size="34"/>-->
          <Input
            placeholder="请输入用户名" style="width: 200px">
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
      <div>
        <Row style="position: relative;padding: 5px">
          <Table
            size='large' stripe border
            :height="AF.getPageHeight()-320"
            :columns="tableTiT"
            :data="tableData"></Table>
        </Row>
      </div>
    </div>
</template>

<script>
    export default {
      name: "index",
      data(){
          return{
            tableTiT: [{
              title: "序号",
              width: 80,
              align: 'center',
              type: 'index'
            },
              {
                title: '基本信息',
                align: 'center',
                children: [
              {
                title: '订单编号',
                align: 'center',
                key: 'bh'
              },
              {
                title: '下单时间',
                align: 'center',
                key: 'sj'
              }
             ]},
              {
                title: '中奖信息',
                align: 'center',
                children: [
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
             ]
            },
            {
              title: '充值信息',
              align: 'center',
              children: [
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
              }
              ]},

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
