<style lang="less">
  .searchItem {
    padding: 0 8px;
  }
</style>
<template>
  <div>
    <div class="box_row">
      <pager-tit title="用户查询"></pager-tit>
      <div class="box_row_100">
        <div class="box_row rowRight">
          <div class="searchItem">
            <Input
              placeholder="请输入用户名" style="width: 200px"/>
          </div>
          <div class="searchItem">
            <Input
              placeholder="请输入手机号码" style="width: 200px"/>
          </div>
          <div class="searchItem">
            <Button type="primary">
              <Icon type="md-search"></Icon>
              <!--查询-->
            </Button>
          </div>
        </div>
      </div>

    </div>

    <div class="box_row" style="margin-top: 22px">
      <div style="width:380px;margin-right: 16px">
        <Card>
          <div
            style="height:220px;text-align:center;color: white;background:url(http://bpic.588ku.com/back_pic/05/81/20/255c34496468dd2.jpg)">
            <div style="padding-top: 15px">
              <img width="120" height="120" style="border-radius: 60px"
                   src="http://img3.duitang.com/uploads/item/201508/18/20150818202351_8CBUT.jpeg">
            </div>
            <br>
            <h3>用户名</h3>
            <h6>zhanghao</h6>
          </div>
          <div>
            <CellGroup>
              <Cell title=" 个人账号信息"/>
              <Cell title="注册日期" extra="2017-08-12"/>
              <Cell title="消费金额" extra="2895"/>
              <Cell title="钱包余额" extra="125"/>
              <Cell title="累计充值金额" extra="3596"/>
              <Cell title="充值次数" extra="3"/>
              <Cell title="参与次数" extra="8"/>
              <Cell title="中奖次数" extra="1"/>
              <Cell title="直接购买次数" extra="1"/>
            </CellGroup>
          </div>
        </Card>
      </div>

      <div class="box_row_100">
        <Card>
          <Row>
            <Table
              stripe border
              :columns="tableTiT"
              :data="tableData"></Table>
          </Row>
        </Card>
        <Card style="margin: 18px 0">
          <Row>
            <Table
              stripe border
              :columns="tableTiT"
              :data="tableData"></Table>
          </Row>
        </Card>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "index",
    data() {
      return {
        tableTiT: [
          {
            title: "序号",
            width: 80,
            align: 'center',
            type: 'index'
          },
          {
            title: '中奖信息',
            align: 'center',
            children: [
              {
                title: '中奖单号',
                align: 'center',
                minWidth: 120,
                key: 'sjh'
              },
              {
                title: '中奖商品',
                align: 'center',
                key: 'dz',
                minWidth: 120,
              },
            ]
          },
          {
            title: '参与商品信息',
            align: 'center',
            children: [
              {
                title: '商品名称',
                minWidth: 120,
                align: 'center',
                key: 'zt'
              },
              {
                title: '规格',
                minWidth: 120,
                align: 'center',
                key: 'ddzt'
              }
            ]
          },

          {
            title: '备注',
            align: 'center',
            minWidth: 120,
            key: 'bz'
          },
          // {
          //   title: '操作',
          //   key: 'action',
          //   width: 180,
          //   align: 'center',
          //   render: (h, params) => {
          //     return h('div', [
          //       h('Button', {
          //         props: {
          //           type: 'success',
          //           icon: 'md-checkmark',
          //           shape: 'circle',
          //           size: 'small'
          //         },
          //         style: {
          //           cursor: "pointer",
          //           margin: '0 8px 0 0'
          //         },
          //         on: {
          //           click: () => {
          //             this.compName = mess
          //           }
          //         }
          //       }),
          //       h('Button', {
          //         props: {
          //           type: 'primary',
          //           icon: 'md-menu',
          //           shape: 'circle',
          //           size: 'small'
          //         },
          //         style: {
          //           cursor: "pointer",
          //           margin: '0 8px 0 0'
          //         },
          //         on: {
          //           click: () => {
          //             this.compName = mess
          //           }
          //         }
          //       }),
          //       h('Button', {
          //         props: {
          //           type: 'error',
          //           icon: 'md-close',
          //           shape: 'circle',
          //           size: 'small'
          //         },
          //         style: {
          //           cursor: "pointer",
          //           margin: '0 8px 0 0'
          //         },
          //         on: {
          //           click: () => {
          //             this.$Modal.warning({
          //               title: '驳回订单',
          //               content: '确定驳回此订单?',
          //             });
          //           }
          //         }
          //       })
          //     ]);
          //   }
          // }
        ],
        tableData: [
          {
            bh: '20180909000123431',
            sj: '201jd',
            sjh: '0908-1678-18766',
            dz: 'sjhfjsfkjdakd',
            zt: '3',
            bz: '赠品111',
            ddzt: '7500'
          },
          {
            bh: '20180909000123541',
            sj: 'askjf',
            sjh: '0908-1678-18766',
            dz: '阿萨德',
            zt: '2',
            bz: '赠品211',
            ddzt: '1500'
          }
        ],
      }
    }
  }
</script>
