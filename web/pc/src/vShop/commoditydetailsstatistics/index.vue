<template>
  <Card class="commoditydetailsstatistics box_col">
    <div slot="title" class="box_row colCenter">
      <div class="box_row_100">
      </div>
      <div>
        <Input
          v-model="param.proName" @on-change="getPagerList" clearable
          :placeholder="$t('SPMC')" style="width: 160px;margin-right: 18px"/>
      </div>
      <div>
        <Select v-model="param.orderBy" @on-change="getPagerList" style="width: 160px;margin-right: 18px" :placeholder="$t('PX')">
          <Option v-for=" item in orderList" :key="item.key" :value="item.key">{{$t(item.val)}}</Option>
        </Select>
      </div>
      <div>
        <DatePicker v-model="pickTime" format='yyyy-MM-dd' @on-change="changTime" type="date"
                    split-panels style="width: 160px;margin-right: 18px" :clearable="false"
                    placement="bottom-end" :placeholder="$t('GMRQ')"></DatePicker>
      </div>
      <div>
        <DatePicker v-model="pickTime1" format='yyyy-MM-dd' @on-change="changEndTime" type="date"
                    split-panels style="width: 160px;margin-right: 18px" :clearable="false"
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

    export default {
        name: "index",
        mixins: [i18nTabTit],
        components: {},
        data() {
            return {
                compName: "",
                itMess: {},
                tab_H: 0,
                tabBox: "tabBox",
                orderList: [{key:'s.sm desc',val:'BYBUY'},{key:'cast(b.bz1 as unsigned) desc',val:'BYWATCH'}],
                tableTiT: [
                    {
                        title: "排行",
                        minWidth: 80,
                        i18n: "PH",
                        render: (h, p) => {
                            let a = p.index + 1 + (this.param.pageNum - 1) * this.param.pageSize
                            return h('div', a)
                        }
                    },
                    {
                        title: "商品名称",
                        key: "proName",
                        minWidth: 100,
                        i18n: 'SPMC',
                        // render: (h,p) => {
                        //     var v = this
                        //     return h('div',[
                        //         h('Button',{
                        //             props:{
                        //                 type:'success',
                        //                 size:'small',
                        //                 shape:'circle'
                        //             },
                        //             on:{
                        //                 click: function () {
                        //                     v.$http.get('/api/')
                        //                     v.$router.push({name:'manage',query:{id:p.row.id}})
                        //                 }
                        //             }
                        //         },p.row.proName)
                        //     ])
                        // }
                    },
                    {
                        title: "用户购买总数量",
                        key: "bz1",
                        minWidth: 100,
                        i18n: 'JRYHGML',
                        render: (h, p) => {
                            let a = 0
                            if (p.row.count) {
                                a = p.row.count
                            }
                            return h('div', a)
                        }
                    },
                    {
                        title: "总浏览数量",
                        key: "bz1",
                        minWidth: 100,
                        i18n: 'ZLLL',
                        render: (h, p) => {
                            let a = 0
                            if (p.row.bz1) {
                                a = p.row.bz1
                            }
                            return h('div', a)
                        }
                    }
                ],
                tableData: [],
                pickTime: '',
                pickTime1: '',
                param: {
                    orderBy: 's.sm desc',
                    end: '',
                    time: "",
                    proName: "",
                    pageNum: 1,
                    pageSize: 12
                },
                total: 0,
            }
        },
        created() {
            this.param.end = this.moment(new Date()).format('YYYY-MM-DD')
            this.pickTime1 = this.moment(new Date()).format('YYYY-MM-DD')
            this.param.time = this.moment(new Date()).format('YYYY-MM-DD')
            this.pickTime = this.moment(new Date()).format('YYYY-MM-DD')
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
            changEndTime(value) {
                console.log(value);
                this.param.end = value
                this.getPagerList()
            },
            getPagerList() {
                this.$http.post("/api/statisnew/kj", this.param).then(res => {
                    if (res.code == 200) {
                        this.tableData = res.page.list
                        this.total = res.page.total
                    }
                }).catch(err => {
                })
            },

            closeMod() {

            }
        }
    }
</script>

<style lang="less">
  .commoditydetailsstatistics {
    .ivu-card-body {
      flex: 1;
      overflow: auto;
    }
  }
</style>
