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
        <div style="font-weight: 600;font-size: 18px;margin-left: 22px">
          用户类型：{{itMess.source=="0"?"人":"电脑"}}
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

        <div class="box_row_1auto">
          <Card>
            <div slot="title" class="box_row">
              <div class="box_row_100">
                <pager-tit title="历史订单"></pager-tit>
              </div>
              <div>
                <Input v-model="param.proNameLike" placeholder="商品名称"
                       style="width: 200px;margin-right: 20px"
                       @on-clear="getPagerList()"
                       @on-change="getPagerList()" clearable />
                <DatePicker type="daterange" split-panels placement="bottom-end"
                            format="yyyy-MM-dd" placeholder="下单时间"
                            style="width: 200px" @on-change="PickerChange"
                ></DatePicker>
              </div>
            </div>
            <Table
              size='small' stripe
              height="458px"
              :columns="tableTiT"
              :data="tableData">
              <div slot="shopName" slot-scope="{ row, index }">
                <Poptip  trigger="hover" >
                  <div slot="content">
                    {{row.proName}}
                  </div>
                  <div style="word-break: break-all;text-overflow: ellipsis;display: -webkit-box;
                    -webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;">
                    {{row.proName}}
                  </div>
                </Poptip>
              </div>

              <div slot="numList" slot-scope="{ row, index }">
                <div style="height:80px;overflow: auto">
                  <Tag color="cyan" v-for="(it,index) in row.orderLists">{{it.num}}</Tag>
                </div>
              </div>
            </Table>
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
            title: "中奖号码",
            align: 'left',
            width: 140,
            key: 'zjhm',
            render: (h, p) => {
              let orderZt = {}
              let str = p.row.ddzt;
              if (str == 0) {
                orderZt = {
                  zt: '待开奖',
                  col: 'magenta'
                };
              } else if (str == 1) {
                orderZt = {
                  zt: '已中奖',
                  col: 'green'
                };
              } else if (str == 2) {
                orderZt = {
                  zt: '未中奖',
                  col: 'volcano'
                };
              } else if (str == 3) {
                orderZt = {
                  zt: '待支付',
                  col: 'cyan'
                };
              } else if (str == 4) {
                orderZt = {
                  zt: '已支付',
                  col: 'gold'
                };
              } else if (str == 5) {
                orderZt = {
                  zt: '取消支付',
                  col: 'default'
                }
              }
              return h('div', [
                h('div', [
                  h('Tag', {
                    props: {
                      color: orderZt.col
                    }
                  }, orderZt.zt)
                ]),
                h('div', {
                  style: {
                    padding: "4px 0"
                  }
                }, [
                  h('Tag', {
                    props: {
                      color: '#FFA2D3'
                    }
                  }, p.row.zjhm)
                ])
              ])
            }
          },
          {
            title: '时间',
            align: 'left',
            width: 220,
            key: 'cjsj',
            render: (h, p) => {
              let kjTime = p.row.kjsj ? p.row.kjsj : '------------------'
              return h("div", [
                h('div', {style: {padding: "4px 0"}}, '下单：' + p.row.cjsj),
                h('div', {style: {padding: "4px 0"}}, '支付：' + p.row.zfsj),
                h('div', {style: {padding: "4px 0"}}, '开奖：' + kjTime)
              ])
            }
          },
          {
            title: '商品名',
            align: 'left',
            key: 'proName',
            slot: 'shopName',
            width: 200,
          },
          {
            title: '份数 / 金额',
            width: 100,
            align: 'center',
            key: 'gmfs',
            render: (h, p) => {
              return h('div', p.row.gmfs + ' / ' + p.row.zfje)
            }
          },
          {
            title: '购买号码',
            slot: 'numList',
            minWidth: 200
          }
        ],
        tableData: [],
        total: 0,
        param: {
          proNameLike:"",
          userId: "",
          cjsjGte:"",
          cjsjLte:"",
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
      PickerChange(t){
        this.param.cjsjGte = t[0]
        this.param.cjsjLte = t[1]
        this.getPagerList()
      },
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
