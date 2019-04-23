<template>
    <div>
      <pager-tit title="用户管理"></pager-tit>
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
      <Row style="position: relative;padding-top: 5px">
        <Table
          size='large' stripe
          :height="AF.getPageHeight()-320"
          :columns="tableTiT"
          :data="tableData"></Table>
      </Row>
    </div>
</template>

<script>
   import usermessage from './comp/usermessage'
    export default {
      name: "index",
      components:{
        usermessage
      },
      data(){
          return{
            tableTiT: [{
              title: "序号",
              width: 80,
              align: 'center',
              type: 'index'
            },
              {
                title: '用户名',
                align: 'center',
                key: 'yhm'
              },
              {
                title: '账号',
                align: 'center',
                key: 'zh'
              },
              {
                title: '参与次数',
                align: 'center',
                key: 'cycs'
              },
              {
                title: '累计充值',
                align: 'center',
                key: 'ljcz'
              },
              {
                title: '累计消费',
                align: 'center',
                key: 'ljxf'
              },
              {
                title: '余额',
                align: 'center',
                key: 'ye',
              },
              {
                title: '状态',
                width: 120,
                align: 'center',
                key: 'zt'
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
                        type: 'warning',
                        icon: 'ios-lock',
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
                yhm:'slu',
                zh:'123123244444',
                cycs:'4',
                ljcz:'6000',
                ljxf:'5400',
                ye:'600',
                zt:'正常'
              },
              {
                yhm:'zha',
                zh:'15321213212',
                cycs:'18',
                ljcz:'1',
                ljxf:'1',
                ye:'0',
                zt:'锁定'
              }
            ]
          }
      }
    }
</script>

<style scoped>

</style>
