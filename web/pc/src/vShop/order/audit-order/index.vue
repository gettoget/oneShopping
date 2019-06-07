<template>
  <Card class="box_col findOrderBox">
    <div slot="title" class="box_row colCenter">
      <div class="box_row_100">
        <pager-tit title="订单查询"></pager-tit>
      </div>
      <div>
        <!--<DatePicker value='yyyy-MM-dd' @on-change="changTime" type="daterange" :options="options2"-->
                    <!--placement="bottom-end" placeholder="Select date" style="width: 200px"></DatePicker>-->
      </div>
      <div>
        <Input
          v-model="param.idLike"
          placeholder="order id" style="width: 200px"/>
      </div>
      <Button type="primary" @click="getPagerList">
        <Icon type="md-search"></Icon>
        <!--查询-->
      </Button>
    </div>
    <div class="box_col">
      <div :id="tabBox" class="box_col_auto">
        <Table
        size='large' stripe
        v-if="tab_H>0"
        :height="tab_H"
        :columns="tableTiT"
        :data="tableData">
          <!--<div slot="shopName" slot-scope="{ row, index }">-->
            <!--<Poptip  trigger="hover" :transfer="true">-->
              <!--<div slot="content" style="width: 200px;white-space:normal;">-->
                <!--{{row.proName}}-->
              <!--</div>-->
              <!--<div style="word-break: break-all;text-overflow: ellipsis;display: -webkit-box;-->
                    <!-- -webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;">-->
                <!--{{row.proName}}-->
              <!--</div>-->
            <!--</Poptip>-->
          <!--</div>-->

        </Table>
      </div>
      <div class="pagerBoxSty boxMar_T box_row rowRight">
        <one-page :total="total" :size="param.pageSize"
                  :opts="[4,8,12,16]"
                  @chPager="chPager"></one-page>
      </div>
    </div>

    <component
      :is="compName"
      :usermes="usermes"
    ></component>
  </Card>
</template>

<script>
  // import mess from './comp/mess'

  export default {
    name: "index",
    components: {
      // mess
    },
    data() {
      return {
        total: 0,
        usermes: {},
        userMesType: true,
        compName: '',
        tabBox:"tabBox",
        tab_H:0,
        tableTiT: [
          {
            title: "序号",
            width: 80,
            align: 'center',
            render:(h,p)=>{
              return h("div",p.index+1+((this.param.pageNum-1)*this.param.pageSize))
            }
          },
          {
            title: '订单编号',
            key: 'id',
            width:110
          },
          {
            title: '商品名',
            key: 'proName',
          },
          {
            title: '订单状态',
            width: 120,
            key: 'ddzt',
            render: (h, p) => {
              let str = p.row.ddzt;
              if (str == 0) {
                str = '待开奖';
              } else if (str == 1) {
                str = '已中奖';
              } else if (str == 2) {
                str = '未中奖';
              } else if (str == 3) {
                str = '待支付';
              } else if (str == 4) {
                str = '已支付';
              } else if (str == 5) {
                str = '取消支付'
              }
              return h('Tag', {
                style: {
                  width: '90px'
                },
                props: {
                  color: p.row.ddzt == 3 ? 'error' : 'success'
                },
              }, str);
            }
          },
          {
            title: '下单时间',
            key: 'cjsj',
            render:(h,p)=>{
              return h('div',[
                h('div',this.moment(p.row.cjsj).format('YYYY-MM-DD')),
                h('div',this.moment(p.row.cjsj).format('HH:mm:ss'))
              ])
            }
          },
          {
            title: '用户名',
            key: 'userName'
          },
          {
            title: '支付金额',
            width: 120,
            key: 'zfje'
          },
          // {
          //   title: '支付时间',
          //   align: 'center',
          //   key: 'zfsj'
          // },
          {
            title: '详情',
            key: 'action',
            width: 180,
            align: 'center',
            render: (h, p) => {
              return h('div', [
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
                      this.usermes = p.row
                      this.compName = mess
                    }
                  }
                })
              ]);
            }
          }
        ],
        tableData: [],
        param: {
          idLike: '',
          cjsjLike: '',
          pageNum: 1,
          pageSize: 8
        }
      }
    },
    created() {
      this.getPagerList()
    },
    mounted(){
      this.$nextTick(() => {
        try {
          this.tab_H = this.AF.getDom_H(this.tabBox)
        } catch (e) {
        }
      })
    },
    methods: {
      chPager(p) {
        this.param.pageNum = p.pageNum
        this.param.pageSize = p.pageSize
        this.getPagerList()
      },
      changTime(value) {
        this.param.cjsjGte = value[0] + ' 00:00:00'
        this.param.cjsjLte = value[1] + ' 23:59:59'
        this.getPagerList()
      },
      getPagerList() {
        this.$http.post(this.apis.ORDER.QUERY, this.param).then((res) => {
          if (res.code == 200) {
            this.tableData = res.page.list
            this.total = res.page.total
          }
        })
      },
    }
  }
</script>

<style lang="less">
  .findOrderBox {
    .ivu-card-body {
      flex: 1;
      overflow: auto;
    }
  }
</style>
