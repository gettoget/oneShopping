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
          <Avatar :src="defAva"/>
        </div>

        <div style="font-weight: 600;font-size: 20px;margin-left: 12px">
          {{itMess.userName}}
        </div>
      </div>

      <div class="box_row">
        <div style="width: 320px">
          <Card>
            <div style="height: 568px;overflow-y: auto;">
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
                <Cell title="注册日期" :extra="moment(itMess.cjsj).format('YYYY-MM-DD HH:mm:ss')"/>
                <Cell title="账户余额" :extra="itMess.balance"/>
                <Cell title="累计充值" :extra="itMess.cz"/>
                <Cell title="充值失败" :extra="(parseFloat(itMess.cz) - parseFloat(itMess.czcg)).toString()"/>
                <Cell title="累计消费" :extra="(parseFloat(itMess.czcg)-parseFloat(itMess.balance)).toString()"/>
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
            <div slot="title" class="box_row rowRight">
              <div style="width: 282px">
                <Tabs v-model="TabsVal" type="card">
                  <TabPane name="1" label="消费记录"></TabPane>
                  <TabPane name="2" label="充值成功"></TabPane>
                  <TabPane name="3" label="充值失败"></TabPane>
                </Tabs>
              </div>
              <div class="box_row_100">

              </div>
              <div v-if="TabsVal=='1'">
                <Input v-model="param.proNameLike" placeholder="商品名称"
                       style="width: 200px;margin-right: 20px"
                       @on-clear="getPagerList()"
                       @on-change="getPagerList()" clearable/>
                <DatePicker type="daterange" split-panels placement="bottom-end"
                            format="yyyy-MM-dd" placeholder="下单时间"
                            style="width: 200px" @on-change="PickerChange"
                ></DatePicker>
              </div>
              <div v-else-if="TabsVal=='2'  ">
                <DatePicker type="daterange" split-panels placement="bottom-end"
                            format="yyyy-MM-dd" placeholder="充值时间"
                            style="width: 200px" @on-change="PickerChange2"
                ></DatePicker>
              </div>
              <div v-else-if="TabsVal=='3'  ">
                <DatePicker type="daterange" split-panels placement="bottom-end"
                            format="yyyy-MM-dd" placeholder="充值时间"
                            style="width: 200px" @on-change="PickerChange3"
                ></DatePicker>
              </div>
            </div>
            <div v-if="TabsVal=='1'">
              <Table
                size='small' stripe
                :columns="tableTiT"
                :data="tableData">
                <div slot="shopName" slot-scope="{ row, index }">
                  <Poptip trigger="hover">
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
                  <Poptip trigger="hover" :transfer="true">
                    <div style="height:60px;overflow: auto">
                      <Tag color="cyan" v-if="index<6" v-for="(it,index) in row.orderLists" :key="index">{{it.num}}
                      </Tag>
                    </div>
                    <div slot="content">
                      <div class="box_row_list" style="width:240px">
                        <Tag color="cyan" v-for="(it,index) in row.orderLists" :key="index">{{it.num}}</Tag>
                      </div>
                    </div>
                  </Poptip>
                </div>
              </Table>
              <div class="pagerBoxSty boxMar_T box_row rowRight">
                <one-page :total="total" :size="param.pageSize"
                          :opts="[4,8,10,12,16]"
                          @chPager="chPager" ></one-page>
              </div>
            </div>
            <div v-else-if="TabsVal=='2'">
              <Table
                size='small' stripe
                :columns="tableTiT2"
                :data="tableData2">
              </Table>
              <div class="pagerBoxSty boxMar_T box_row rowRight">
                <one-page :total="total2" :size="param2.pageSize"
                          :opts="[4,8,10,12,16]"
                          @chPager="chPager1"></one-page>
              </div>
            </div>
            <div v-else-if="TabsVal=='3'">
              <Table
                size='small' stripe
                :columns="tableTiT3"
                :data="tableData3">
              </Table>
              <div class="pagerBoxSty boxMar_T box_row rowRight">
                <one-page :total="total3" :size="param3.pageSize"
                          :opts="[4,8,10,12,16]"
                          @chPager="chPager2"
                          ></one-page>
              </div>
            </div>
          </Card>
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
    import defAva from './007.png'

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
                defAva,
                showModal: true,
                TabsVal: "1",
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
                        title: '商品名',
                        align: 'center',
                        // key: 'proName',
                        slot: 'shopName',
                        width: 300,
                        render: (h,p) => {
                            var v = this
                            return h('div' , [
                                h('Button', {
                                    props:{
                                        type: 'success',
                                        shape: 'circle',
                                        size: 'small'
                                    },
                                    on: {
                                        click: function () {
                                            console.log(p.row.proId)
                                            v.$router.push({name: 'manage',query: {id:p.row.proId}})
                                        }
                                    }
                                },p.row.proName)
                                ]
                            )

                        }
                    },
                    {
                        title: '订单状态',
                        align: 'center',
                        key: 'proName',
                        slot: 'shopName',
                        minWidth: 80,
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
                                ])
                            ])
                        }
                    },
                    {
                        title: "中奖号码",
                        align: 'center',
                        width: 140,
                        key: 'zjhm',
                        render: (h, p) => {
                            let a = []
                            if (p.row.ddzt == '1' || p.row.ddzt == '2') {
                                a = [
                                    h('Tag', {
                                        props: {
                                            color: '#FFA2D3'
                                        }
                                    }, p.row.zjhm)
                                ]
                            }
                            return h('div', {
                                style: {
                                    padding: "4px 0"
                                }
                            }, a)
                        }
                    },
                    {
                        title:'开奖时间',
                        align: 'center',
                        width: 220,
                        key:'kjsj',
                        render: (h, p) => {
                            if(p.row.kjsj){
                                return h("div", this.moment(p.row.kjsj).format('YYYY-MM-DD HH:mm:ss'))
                            }
                        }
                    },
                    {
                        title: '支付时间',
                        align: 'center',
                        width: 120,
                        key: 'cjsj',
                        render: (h, p) => {
                            return h("div", this.moment(p.row.zfsj).format('YYYY-MM-DD'))
                        }
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
                        align:'center',
                        minWidth: 280
                    }
                ],
                tableData: [],
                total: 0,
                param: {
                    proNameLike: "",
                    userId: "",
                    cjsjGte: "",
                    cjsjLte: "",
                    pageNum: 1,
                    pageSize: 10
                },
                total2: 0,
                tableTiT2: [
                    {
                        title: "充值时间",
                        key: "cjsj",
                        render: (h, p) => {
                            return h("div", this.moment(p.row.cjsj).format('YYYY-MM-DD HH:mm:ss'))
                        }
                    },
                    {
                        title: "充值金额",
                        key: "amonut"
                    },
                    {
                        title: "充值数量",
                        key: "amonut"
                    },
                    {
                        title: "金币余额",
                        key: "czhjbs"
                    },
                    {
                        title: "充值渠道",
                        key: "czqd",
                        render: (h, p) => {
                            if (p.row.czqd == '1' && p.row.czzt == '2') {
                                return h("div", "充值成功")
                            } else if(p.row.czqd == '2' ) {
                                return h("div", "活动奖励")
                            }else{
                                return h("div","充值失败")
                            }

                        }
                    }
                ],
                tableData2: [],
                param2: {
                    czzt: '2',
                    userId: "",
                    cjsjGte: "",
                    cjsjLte: "",
                    pageNum: 1,
                    pageSize: 10
                },
                total3: 0,
                tableTiT3: [
                    {
                        title: "充值时间",
                        key: "cjsj",
                        render: (h, p) => {
                            return h("div", this.moment(p.row.cjsj).format('YYYY-MM-DD HH:mm:ss'))
                        }
                    },
                    {
                        title: "充值金额",
                        key: "amonut"
                    },
                    {
                        title: "充值数量",
                        key: "amonut"
                    },
                    {
                        title: "充值渠道",
                        key: "czqd",
                        render: (h, p) => {
                            if (p.row.czqd == '1' && p.row.czzt == '2') {
                                return h("div", "充值成功")
                            } else if(p.row.czqd == '2' ) {
                                return h("div", "活动奖励")
                            }else{
                                return h("div","充值失败")
                            }

                        }
                    }
                ],
                tableData3: [],
                param3: {
                    userId: "",
                    cjsjGte: "",
                    cjsjLte: "",
                    pageNum: 1,
                    pageSize: 10,
                    czzt: "1"
                }
            }
        },
        created() {
            this.param.userId = this.itMess.id
            this.param2.userId = this.itMess.id
            this.param3.userId = this.itMess.id
            this.getPagerList()
            this.getCZlist()
            this.getCZlist2()
        },
        methods: {
            PickerChange(t) {
                this.param.cjsjGte = t[0]
                this.param.cjsjLte = t[1]
                this.getPagerList()
            },
            PickerChange2(t) {
                this.param2.cjsjGte = t[0]
                this.param2.cjsjLte = t[1]
                this.getCZlist2()
            },
            PickerChange3(t) {
                this.param3.cjsjGte = t[0]
                this.param3.cjsjLte = t[1]
                this.getCZlist2()
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
            chPager1(p) {
                this.param2.pageNum = p.pageNum
                this.param2.pageSize = p.pageSize
                this.getCZlist()
            },
            chPager2(p) {
                this.param3.pageNum = p.pageNum
                this.param3.pageSize = p.pageSize
                this.getCZlist2()
            },
            getPagerList() {
                this.$http.post("/api/order/pager", this.param).then(res => {
                    if (res.code == 200) {
                        this.tableData = res.page.list
                        this.total = res.page.total
                    }
                }).catch(err => {
                })
            },
            getCZlist() {
                this.$http.post("/api/recharge/pager", this.param2).then(res => {
                    if (res.code == 200) {
                        this.tableData2 = res.page.list
                        this.total2 = res.page.total
                    }
                }).catch(err => {
                })
            },
            getCZlist2() {
                this.$http.post("/api/recharge/pager", this.param3).then(res => {
                    if (res.code == 200) {
                        this.tableData3 = res.page.list
                        this.total3 = res.page.total
                    }
                }).catch(err => {
                })
            }
        }
    }
</script>
