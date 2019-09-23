<template>
  <Card class="userdetailsstatistics box_col">
    <div slot="title" class="box_row colCenter">
      <div class="box_row_100">
      </div>
      <div>
        <Input
          v-model="param.name" @on-change="getPagerList" clearable
          :placeholder="$t('YHM')" style="width: 160px;margin-right: 18px"/>
      </div>
      <div>
        <DatePicker v-model="tableDatatime"
                    format='yyyy-MM-dd' @on-change="changTime" type="date"
                    split-panels style="width: 160px;margin-right: 18px"
                    placement="bottom-end" :placeholder="$t('GMRQ')"></DatePicker>
        <DatePicker v-model="tableDatatime1"
                    format='yyyy-MM-dd' @on-change="changEndTime" type="date"
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
          @on-row-click="clickrow"
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
    import messMod from '../user/usermanagement/comp/messMod'

    export default {
        name: "index",
        mixins: [i18nTabTit],
        components: {
            messMod
        },
        data() {
            return {
                tableDatatime: '',
                tableDatatime1:'',
                compName: "",
                itMess: {},
                tab_H: 0,
                tabBox: "tabBox",
                tableTiT: [
                    {
                        title: "排行",
                        i18n: 'PH',
                        minWidth: 80,
                        render: (h, p) => {
                            let a = p.index + 1 + (this.param.pageNum - 1) * this.param.pageSize
                            return h('div', a)
                        }
                    },
                    {
                        title: "用户名",
                        // key: "userName",
                        minWidth: 100,
                        i18n: 'YHM',
                        align: 'center',
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
                                            v.$http.post("/api/user/pager", {id:p.row.id}).then(res => {
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
                                },p.row.userName)
                            ])
                        }
                    },
                    // {
                    //     title: "充值时间",
                    //     minWidth: 180,
                    //     key: "cjsj",
                    //     i18n: 'CZSJ',
                    //     align: 'center',
                    //     render: (h, p) => {
                    //         return h('div', this.moment(p.row.cjsj).format("YYYY-MM-DD HH:mm:ss"))
                    //     }
                    // },
                    // {
                    //   title:"支付时间",
                    //   minWidth:180,
                    //   key:"cjsj",
                    //   i18n:'ZFSJ',
                    //     align:'center',
                    //   render:(h,p)=>{
                    //     return h('div',this.moment(p.row.qrsj).format("YYYY-MM-DD HH:mm:ss"))
                    //   }
                    // },
                    {
                        title: "购买号码数",
                        minWidth: 120,
                        i18n: 'GMCS',
                        key: "count",
                        align: 'center'
                    },
                ],
                tableData: [],
                param: {
                    time: "",
                    end:"",
                    name: "",
                    pageNum: 1,
                    pageSize: 12
                },
                total: 0,
            }
        },
        created() {
            console.log(this.moment().format('YYYY-MM-DD'));
            this.param.time = this.moment().format('YYYY-MM-DD')
            this.param.end = this.moment().format('YYYY-MM-DD')
            this.tableDatatime = this.moment().format('YYYY-MM-DD')
            this.tableDatatime1 = this.moment().format('YYYY-MM-DD')
            console.log(this.param.time);
            this.getPagerList()
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
            closeMod() {
                this.compName = ''
            },
            clickrow(a, b) {
                this.itMess = a
                this.compName = 'messMod'
            },
            chPager(p) {
                this.param.pageNum = p.pageNum
                this.param.pageSize = p.pageSize
                this.getPagerList()
            },
            changTime(value) {
                console.log(value);
                this.param.time = value
                this.getPagerList()
            },
            changEndTime(value){
                this.param.end = value
                this.getPagerList()
            },
            getPagerList() {
                this.$http.post("/api/statisnew/yhgm", this.param).then(res => {
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
  .userdetailsstatistics {
    .ivu-card-body {
      flex: 1;
      overflow: auto;
    }
  }
</style>
