<template>
  <div>
    <pager-tit title="用户中奖订单"></pager-tit>
    <div class="box_row colCenter rowRight pageFindSty" style="border: none">
      <div>
        <!--<Icon type="md-person" size="34"/>-->
        <Input
          placeholder="请输入订单编号" style="width: 200px">
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
    <div class="pagerBoxSty boxMar_T box_row rowRight">
      <one-page :total="total" :size="param.pageSize"
                :opts="[4,8,12,16]"
                @chPager="chPager"></one-page>
    </div>
    <component
      :is="compName"
      :usermes="usermes"
    ></component>
  </div>
</template>

<script>
  import mess from './comp/mess'
  export default {
    name: "index",
    components:{
      mess
    },
    data(){
      return{
        compName:'',
        usermes: {},
        userMesType: true,
        total:0,
        param:{
          orderType:2,
          idLike:'',
          cjsjLike:'',
          pageNum: 1,
          pageSize: 8
        },
        tableTiT: [{
          title: "序号",
          width: 80,
          align: 'center',
          type: 'index'
        },
          {
            title: '订单编号',
            align: 'center',
            key: 'id'
          },
          {
            title: '下单时间',
            align: 'center',
            key: 'cjsj'
          },
          {
            title: '用户名',
            align: 'center',
            key: 'userName'
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
              }else if(str == 5){
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
          },
          {
            title: '操作',
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
                }),
              ]);
            }
          }
        ],
        tableData: [],
      }
    },
    created(){
      this.getPagerList()
    },
    methods:{
      chPager(p){
        this.param.pageNum = p.pageNum
        this.param.pageSize = p.pageSize
        this.getPagerList()
      },
      getPagerList(){
        this.$http.post(this.apis.ORDER.QUERY,this.param).then((res)=>{
          if (res.code == 200){
            this.tableData = res.page.list
            this.param.pageNum = res.page.pageNum
            this.param.pageSize = res.page.pageSize
            this.total = res.page.total
          }
        })
      },
    }
  }
</script>

<style scoped>

</style>
