<style lang="less">
  @import '../../../assets/css/wdPageSty';
</style>
<!--用户管理-->
<template>
  <div class="boxbackborder box_col">
    <pager-tit :title='$t("SYSTEM_USER")'></pager-tit>
    <div class="box_row colCenter rowRight pageFindSty" style="border: none">
      <div>
        <!--<Icon type="md-person" size="34"/>-->
        <Input v-model="param.xmLike"
               :placeholder="$t('YHM')" style="width: 200px"
               @on-keyup.enter="findMessList()"
               @on-change="findMessList"></Input>
      </div>

      <div>
        <!--<Icon type="ios-call" size="34"/>-->
        <Input v-model="param.sjhLike"
               :placeholder="$t('YHDH')" style="width: 200px"
               @on-keyup.enter="findMessList()"
               @on-change="findMessList"></Input>
      </div>
      <Button type="primary" @click="findMessList()">
        <Icon type="md-search"></Icon>
        <!--查询-->
      </Button>
      <Button type="primary" @click="AddDataList()">
        <Icon type="md-add"></Icon>
      </Button>
    </div>
    <div :id="tabBox" class="box_col_100">
      <Table
        size='large' stripe
        :height="tab_H"
        v-if="tab_H>0"
        :columns="tableTiT"
        :data="tableData"></Table>
    </div>
    <Row class="margin-top-10 pageSty">
      <Page :total=pageTotal :current=param.pageNum :page-size=param.pageSize
            :page-size-opts=[8,10,20,30,40,50] @on-page-size-change='(e)=>{param.pageSize=e;pageChange()}'
            show-total show-elevator show-sizer placement='top' @on-change='pageChange'></Page>
    </Row>
    <component
      :is="compName"
      :usermes="usermes"
      :usermesType="userMesType"
      @listF='listF'></component>
  </div>
</template>

<script>
  import i18nTabTit from '@/mixins/i18nTabTit'
  import ukey from './comp/ukey.vue'
  import newmess from './comp/newmes.vue'
  import changemes from './comp/changmes.vue'

  export default {
    name: 'char',
    components: {
      ukey,
      newmess,
      changemes
    },
    mixins: [i18nTabTit],
    data() {
      return {
        //动态组建
        compName: '',
        //动态组建数据
        usermes: {},
        userMesType: true,
        //分页
        //tab高度
        tab_H: 0,
        tabBox:"tabBox",
        tableTiT: [{
          title: "序号",
          i18n:"NUMBER",
          width: 100,
          align: 'center',
          type: 'index'
        },
          {
            title: '帐号',
            i18n:"ACCOUNT_NUMBER",
            align: 'center',
            key: 'zh'
          },
          {
            title: '姓名',
            i18n:"NAME",
            align: 'center',
            key: 'xm'
          },
          {
            title: '证件号码',
            i18n:"IDENTIFICATION_NUMBER",
            align: 'center',
            key: 'zjhm'
          },
          {
            title: '性别',
            align: 'center',
            i18n:"SEX",
            key: 'xb',
            render: (h, params) => {
              return h('div', params.row.xb == '10' ? '男' : '女')
            }
          },
          {
            title: '手机号',
            width: 120,
            align: 'center',
            i18n:"PHONE_NUMBER",
            key: 'sjh'
          },
          {
            title: '职务',
            align: 'center',
            i18n:"ACCOUNT_NUMBER",
            key: 'zw'
          },
          {
            title: '状态',
            align: 'center',
            i18n:"STATE",
            key: 'zt'
          },
          {
            title: '类型',
            align: 'center',
            key: 'lx',
            render: (h, p) => {
              let val = this.dictUtil.getValByCode(this, this.yhlxDictCode, p.row.lx)
              return h('div', val)
            }
          },
          {
            title: '操作',
            key: 'action',
            width: 180,
            align: 'center',
            render: (h, params) => {
              if (params.row.zt === '00') {
                return ''
              }
              return h('div', [
                h('Button', {
                  props: {
                    type: 'success',
                    icon: 'md-key',
                    shape: 'circle',
                    size: 'small'
                  },
                  style: {
                    cursor: "pointer",
                    margin: '0 8px 0 0'
                  },
                  on: {
                    click: () => {
                      this.userMesType = false
                      this.usermes = params.row
                      this.compName = 'ukey'
                    }
                  }
                }),
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
                    click: () => {
                      this.userMesType = false
                      this.usermes = params.row
                      this.compName = 'newmess'
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
                      this.RootShowF(params.row)

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
                      this.listDele(params.row.yhid)
                    }
                  }
                })
              ]);
            }
          }
        ],
        tableData: [],
        //---数据总数
        pageTotal: 2,
        //收索
        param: {
          sjhLike: '',
          xmLike: '',
          timers:'',
          pageNum: 1,
          pageSize: 8
        },
        yhlxDict: [],
        yhlxDictCode: 'ZDCLK0003'
      }
    },
    watch: {},
    created() {
      this.getmess()
      this.getDict()
    },
    methods: {
      getDict() {
        this.yhlxDict = this.dictUtil.getByCode(this, this.yhlxDictCode);
        console.log(this.yhlxDict);
      },
      getmess() {
        var v = this
        this.param.timers = new Date().getTime()
        this.$http.get(this.apis.USER.QUERY, {params: v.param}).then((res) => {
//					log(res)
          v.tableData = res.page.list
          v.pageTotal = res.page.total
        })
      },
      //权限分配
      RootShowF(val) {
        var v = this
        v.compName = 'changemes'
        this.usermes = val;
      },
      //收索事件
      findMessList() {
        var v = this
        this.param.timers = new Date().getTime()
        this.$http.get(this.apis.USER.QUERY, {params: v.param}).then((res) => {
//					log(res)
          v.tableData = res.page.list
        })
      },
      //添加新用户信息
      AddDataList() {
        var v = this
        v.compName = 'newmess'
        v.userMesType = true
        this.usermes = null
      },
      listF() {
        this.getmess()
        this.compName = ''
      },
      //删除数据
      listDele(id) {
        this.util.del(this, this.apis.USER.DELE, [id], () => {
          this.getmess();
        });
      },
      //分页点击事件按
      pageChange(event) {
        var v = this
        v.param.pageNum = event
        v.getmess()
      }
    }
  }
</script>
