<template>
  <div>
    <Modal
      v-model="showModal"
      class-name="vertical-center-modal"
      width="90%"
      :mask-closable="false"
      :footer-hide="true"
      @on-visible-change="visible">
      <div slot="header" class="box_row colCenter">
        <div v-if="itMess.hImg">
          <Avatar :src="itMess.hImg.substring(0,4)==='http'?
          itMess.hImg:apis.GETFILEURL+itMess.hImg"/>
        </div>
        <div v-else>
          <Avatar src="https://i.loli.net/2017/08/21/599a521472424.jpg"/>
        </div>

        <div style="font-weight: 600;font-size: 20px;margin-left: 12px">
          {{itMess.userName}}
        </div>
      </div>

      <div class="box_row">
        <div style="width: 320px">
          <Card>
            <div style="height: 568px">
              <CellGroup>
                <Cell title="最后一次登录" v-if="itMess.lastTime">
                  <div>
                    <Time :time="moment(itMess.lastTime).format('YYYY-MM-DD HH:mm:ss')"/>
                    最后一次登录
                  </div>
                  <div slot="extra">
                    {{moment(itMess.lastTime).format('YYYY-MM-DD')}}
                  </div>
                </Cell>
                <Cell title="用户帐号" :extra="itMess.phone"/>
                <!--itMess.pwd-->
                <Cell title="登录密码" extra="********"/>
                <!--payPwd-->
                <Cell title="支付密码" extra="********"/>
                <Cell title="用户类型" :extra="itMess.source=='0'?'人类':'电脑'"/>
                <Cell title="注册日期" :extra="moment(itMess.cjsj).format('YYYY-MM-DD HH:mm:ss')"/>
                <Cell title="账户余额" :extra="itMess.balance"/>
                <Cell title="累计充值" :extra="itMess.cz"/>
                <Cell title="累计消费" :extra="(parseFloat(itMess.cz)-parseFloat(itMess.balance)).toString()"/>
                <Cell title="参与次数" :extra="itMess.cy"/>
                <Cell title="中奖次数" :extra="itMess.zjcs"/>

                <Cell title="邀请码">
                  <div slot="extra">
                    <div v-if="itMess.refCode">
                      {{itMess.refCode}}
                    </div>
                    <div v-else>
                      ********
                    </div>
                  </div>
                </Cell>
                <Cell title="积分" :extra="itMess.score"/>
              </CellGroup>
            </div>
          </Card>
        </div>

        <div class="box_row_100">
          <Card>
            <div slot="title">
              <pager-tit title="历史订单"></pager-tit>
            </div>
            <Table
              size='small' stripe
              height="458px"
              :columns="tableTiT"
              :data="tableData"></Table>
            <div class="pagerBoxSty boxMar_T box_row rowRight">
              <one-page :total="total" :size="param.pageSize"
                        :opts="[4,8,12,16]"
                        @chPager="chPager"></one-page>
            </div>
          </Card>
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "messMod",
    props: {
      itMess: {
        type: Object,
        default: {}
      }
    },
    data() {
      return {
        showModal: true,
        tableTiT: [
          {
            title: "序号",
            width: 80,
            align: 'center',
            render: (h, params) => {
              return h('span', params.index + (this.param.pageNum - 1) * this.param.pageSize + 1);
            }
          },
          {
            title: '订单编号',
            align: 'center',
            key: 'id'
          },
          {
            title: '下单时间',
            align: 'center',
            key: 'cjsj',
            render:(h,p)=>{

            }
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
            title: '支付金额',
            width: 120,
            align: 'center',
            key: 'zfje'
          },
          {
            title: '支付时间',
            align: 'center',
            key: 'zfsj'
          }
        ],
        tableData: [],
        total: 0,
        param: {
          userId: "",
          pageNum: 1,
          pageSize: 10
        },
      }
    },
    created() {
      this.param.userId = this.itMess.id
      this.getPagerList()
    },
    methods: {
      visible(val) {
        console.log(val);
        this.$emit("closeMod")
      },
      chPager(p) {
        this.param.pageNum = p.pageNum
        this.param.pageSize = p.pageSize
        this.getPagerList()
      },
      getPagerList() {
        this.$http.post("/api/order/pager", this.param).then(res => {
          if (res.code == 200) {
            this.tableData = res.page.list
            this.total = res.page.total
          }
        }).catch(err => {
        })
      }
    }
  }
</script>
