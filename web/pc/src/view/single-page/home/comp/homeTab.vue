<template>
  <div>
    <Table :columns="tableTiT" :data="tableData" size="small" height="445">
      <div slot="shopName" slot-scope="{ row, index }">
        <Poptip  trigger="hover" :transfer="true">
          <div slot="content" style="width: 200px;white-space:normal;">
            {{row.proName}}
          </div>
          <div style="word-break: break-all;text-overflow: ellipsis;display: -webkit-box;
                    -webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;">
            {{row.proName}}
          </div>
        </Poptip>
      </div>
    </Table>
  </div>
</template>

<script>
  import iphone from './iphone.jpg'

  export default {
    name: "homeTab",
    data() {
      return {
        // 序号
        // 开奖商品
        // 名称
        // 图
        // 时间
        // 号码
        // 姓名
        // 电话
        tableTiT: [
          {
            title: "#",
            type: "index",
            width: 60
          },
          {
            title: '开奖商品',
            slot: 'shopName',
            key: 'proName',
            minWidth:160,
            align:'center',
            // render:(h,p)=>{
            //   return h('div',{
            //     style:{
            //       textAlign:'center',
            //       padding:'6px 0'
            //     }
            //   },[
            //     h('Avatar',{
            //       props:{
            //         size:"large",
            //         shape:"square",
            //         src:iphone
            //       }
            //     }),
            //     h('div','iphonX MAX')
            //   ])
            // }
          },
          {
            title: '中奖时间',
            width:110,
            align:'left',
            key: 'kjsj',
            render:(h,p)=>{
              return h('div',[
                h('div',p.row.cjsj.substring(0,10)),
                h('div',p.row.cjsj.substring(11,p.row.cjsj.length))
              ])
            }
          },
          {
            title:"中奖号码",
            width:110,
            render:(h,p)=>{
              return h('div',[
                h('Tag', {
                  props: {
                    color: '#FFA2D3'
                  }
                }, p.row.zjhm)
              ])
            }
          },
          {
            title: '姓名',
            minWidth:110,
            align:'left',
            key: 'userName',
          },
          {
            title: '手机号码',
            align:'left',
            minWidth:120,
            key: 'phone'
          }
        ],
        tableData: [
        ],
        param: {
          orderType: 2,
          pageNum: 1,
          pageSize: 10
        },
      }
    },
    created(){
      this.getPagerList()
    },
    methods:{
      getData(){
        this.$http.post('')
      },
      getPagerList() {
        this.$http.post("/api/order/pager", this.param).then(res => {
          if (res.code == 200) {
            this.tableData = res.page.list
          }
        }).catch(err => {
        })
      }
    }
  }
</script>
