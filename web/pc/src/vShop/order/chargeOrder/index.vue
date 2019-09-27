<template>
  <Card class="chargeOrderBoxSty box_col">
    <div slot="title" class="box_row colCenter">
      <div class="box_row_100">
        <!--        <pager-tit title="充值记录"></pager-tit>-->
      </div>
      <div style="float: left;margin-right: 200px" >
        {{$t('MORE') }}  :  {{count}}   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  {{$t('SUM') }}  :  {{amount}}
      </div>
      <div>
        <Input
          v-model="param.username" @on-change="getPagerList" clearable
          :placeholder="$t('YHM')" style="width: 160px;margin-right: 18px"/>
      </div>
      <div>
        <Select v-model="czqd" @on-change="getPagerList" style="width: 160px;margin-right: 18px" :placeholder="$t('CZZT')">
          <Option value="ALL">{{$t('QB')}}</Option>
          <Option value="0">{{$t('SUCCESS')}}</Option>
          <Option value="1">{{$t('FAIL')}}</Option>
          <Option value="2">{{$t('REWARD')}}</Option>
<!--          <Option v-for="item in czcgList" :value="item.key" >{{$t(item.val)}}</Option>-->
        </Select>
      </div>
      <div>
        <Select v-model="param.bz1" @on-change="getPagerList" style="width: 160px;margin-right: 18px" :placeholder="$t('CZQD')">
          <Option v-for="item in czqdList" :value="item.key" >{{item.val}}</Option>
        </Select>
      </div>
      <div>
        <DatePicker format='yyyy-MM-dd' @on-change="changTime" type="daterange"
                    split-panels style="width: 160px;margin-right: 18px"
                    placement="bottom-end" :placeholder="$t('GMRQ')"></DatePicker>
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
          :height="tab_H"
          v-if="tab_H>0"
          :columns="tableTiT"
          :data="tableData"></Table>
      </div>
      <div class="pagerBoxSty boxMar_T box_row rowRight">
        <one-page :total="total" :size="param.pageSize"
                  :opts="[4,8,12,16]" @chPager="chPager"></one-page>
      </div>
    </div>
    <component :is="compName"
               :itMess="itMess"
               @closeMod="closeMod"></component>
  </Card>

</template>

<script>
    import i18nTabTit from '@/mixins/i18nTabTit'
    import messMod from '../../user/usermanagement/comp/messMod'
    export default {
        name: "index",
        mixins: [i18nTabTit],
        components: {
            messMod
        },
        data() {
            return {
                count:0,
                amount:0,
                czqd:'ALL',
                itMess: {},
                compName:'',
                tab_H: 0,
                tabBox: "tabBox",
                czcgList:[
                    {key:'ALL',val:'ALL'},
                    {key:'0',val:'SUCCESS'},
                    {key:'1',val:'FAIL'},
                    {Key:'2',val:'REWARD'}
                ],
                czqdList: [
                    {key:'ALL',val:'ALL'},
                    {key: '51491197', val: 'CIMB'},
                    {key: '89220177', val: 'Danamon'},
                    {key: '11111029', val: 'Alfa'},
                    {key: '88561113', val: 'Permata'},
                    {key: '82910053', val: 'BNI'},
                    {key: '89022179', val: 'Mandiri'}
                ],
                tableTiT: [
                    {
                        title: "#",
                        minWidth: 80,
                        render: (h, p) => {
                            let a = p.index + 1 + (this.param.pageNum - 1) * this.param.pageSize
                            return h('div', a)
                        }
                    },
                    {
                        title: "用户名称",
                        key: "username",
                        minWidth: 100,
                        i18n: 'YHM',
                        render: (h,p) => {
                            var v = this
                            return h('div', [
                                h('Button',{
                                    props:{
                                        type:'success',
                                        shape: 'circle',
                                        size: 'small'
                                    },
                                    on: {
                                        click: function () {
                                            v.$http.post("/api/user/pager", {id:p.row.userId}).then(res => {
                                                if (res.code == 200) {
                                                    console.log(res.page.list);
                                                    v.itMess = res.page.list[0]
                                                    console.log(v.itMess);
                                                    v.compName = "messMod"
                                                }
                                            }).catch(err => {
                                            })

                                        }
                                    }
                                },p.row.username)
                            ])
                        }
                    },
                    {
                        title: "充值时间",
                        minWidth: 180,
                        key: "cjsj",
                        i18n: 'CZSJ',
                        render: (h, p) => {
                            return h('div', this.moment(p.row.cjsj).format("YYYY-MM-DD HH:mm:ss"))
                        }
                    },
                    {
                        title: "支付时间",
                        minWidth: 180,
                        key: "cjsj",
                        i18n: 'ZFSJ',
                        render: (h, p) => {
                            return h('div', this.moment(p.row.qrsj).format("YYYY-MM-DD HH:mm:ss"))
                        }
                    },
                    {
                        title: "充值金额",
                        minWidth: 120,
                        i18n: 'CZJE',
                        key: "amonut"
                    },
                    {
                        title: "充值类型",
                        minWidth: 120,
                        i18n: 'LX',
                        key: "czqd",
                        render: (h, p) => {
                            if (p.row.czqd == '1') {
                                return h("div", "充值")
                            } else {
                                return h("div", "奖励")
                            }

                        }
                    },
                    {
                        title: "充值渠道",
                        minWidth: 120,
                        i18n: 'CZQD',
                        key: "bz1",
                        render: (h, p) => {
                            if (p.row.bz1 == "51491197") {
                                return h("div", "CIMB")
                            } else if (p.row.bz1 == "89220177") {
                                return h("div", "Danamon")
                            } else if (p.row.bz1 == "11111029") {
                                return h("div", "Alfa")
                            } else if (p.row.bz1 == "88561113") {
                                return h("div", "Permata")
                            } else if (p.row.bz1 == "82910053") {
                                return h("div", "BNI")
                            } else if (p.row.bz1 == "89022179") {
                                return h("div", "Mandiri")
                            }

                        }
                    },
                    {
                        title: "充值状态",
                        minWidth: 120,
                        i18n: 'CZZT',
                        key: "czzt",
                        render: (h, p) => {
                            let a = {
                                "1": {
                                    typ: "primary",
                                    i18n: "DQR",
                                    val: this.$t('DQR')
                                },
                                "2": {
                                    typ: "success",
                                    val: this.$t('CZCG')
                                },
                                "3": {
                                    typ: "error",
                                    val: this.$t('CZSB')
                                },
                                "4": {
                                    typ: "warning",
                                    val: this.$t('CZQX')
                                }
                            }

                            return h("Tag", {
                                props: {
                                    color: a[p.row.czzt].typ
                                }
                            }, a[p.row.czzt].val)
                        }
                    }
                ],
                tableData: [],
                param: {
                    bz1:'ALL',
                    username: '',
                    cjsjGte: '',//开始
                    cjsjLte: '',//结束
                    pageNum: 1,
                    pageSize: 12,
                    czzt:'',
                    czqd:''
                },
                total: 0,
            }
        },
        created() {
            this.getPagerList()
            this.getMoreThanTwo()
        },
        mounted() {
            this.$nextTick(() => {
                try {
                    this.tab_H = this.AF.getDom_H(this.tabBox)
                } catch (e) {
                }
            })
        },
        methods: {
            getMoreThanTwo(){
               this.$http.get("/api/recharge/getTwo").then(res => {
                   if(res.code === 200){
                       this.amount = res.result.amount
                       this.count = res.result.count
                   }
               })
            },
            closeMod() {
                this.compName = ''
            },
            chPager(p) {
                this.param.pageNum = p.pageNum
                this.param.pageSize = p.pageSize
                this.getPagerList()
            },
            changTime(value) {
                console.log(value);
                if (value[1] != "") {
                    this.param.cjsjGte = this.moment(value[0]).format('YYYY-MM-DD HH:mm:ss')
                    this.param.cjsjLte = this.moment(value[1]).format('YYYY-MM-DD HH:mm:ss')
                } else {
                    this.param.cjsjGte = ""
                    this.param.cjsjLte = ""
                }
                this.getPagerList()
            },
            getPagerList() {
                console.log(this.czqd);

                if(this.czqd === 'ALL'){
                    this.param.czzt=''
                    this.param.czqd=''
                }else if(this.czqd === '0'){
                    this.param.czzt='2'
                    this.param.czqd='1'
                }else if(this.czqd === '1'){
                    this.param.czzt = '1'
                    this.param.czqd= '1'
                }else if(this.czqd === '2'){
                    this.param.czqd = '2'
                    this.param.czzt = '2'
                }
                if(this.param.bz1 === 'ALL'){
                    this.param.bz1 = ''
                }
                this.$http.post("/api/recharge/pager", this.param).then(res => {
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

<style lang="less">
  .chargeOrderBoxSty {
    .ivu-card-body {
      flex: 1;
      overflow: auto;
    }
  }
</style>
