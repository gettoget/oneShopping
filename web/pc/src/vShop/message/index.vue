<template>
  <div>
    <Menu mode="horizontal" :theme="theme1" active-name="1" @on-select="changeMenu">
      <MenuItem name="1">
        <Icon type="ios-paper"/>
        待回复
      </MenuItem>
      <MenuItem name="2">
        <Icon type="ios-people"/>
        已回复
      </MenuItem>
    </Menu>
        <div class="message_box" v-if="list && list.length>0">
          <Row>
            <Col span="6" v-for="(item,index) in list" :key="index">
              <Card style="width: 340px;height: 300px">
                <div style="text-align:center">
                  <img style="width: 80px;height: 80px"
                       :src='item.himg'>
                  <h3>{{item.username}}</h3>
                </div>
                <div style="padding: 20px">
                  <div style="width: 260px">{{item.que}}</div>
                  <div style="text-align: right">
                    <Tag color="default">{{item.cjsj}}</Tag>
                    <Button v-if="item.ans == null" type="primary" ghost @click="handleReply(item)">点击回复</Button>
<!--                    <Tag v-if="item.ans != null" color="volcano" >{{item.ans}}</Tag>-->
                    <div style="color: #ff6600">
                      {{item.ans}}
                    </div>
                  </div>
                </div>
              </Card>
            </Col>
          </Row>
      </div>
    <div style="color: #5d6c7b;font-size: 48px;font-weight: 700;text-align: center" v-else>
       暂无数据
    </div>
    <!--    <div>-->
    <!--      <v-list :list="list" @reply="handleReply"></v-list>-->
    <!--    </div>-->
    <component
      :is="compName"
      :mess="mess"
      @saveAns="saveAns"
      @close="close"
    ></component>
    <div class="pagerBoxSty boxMar_T box_row rowRight">
      <one-page :total="total" :size="param.pageSize"
                :opts="[4,8,12,16]"
                @chPager="chPager"></one-page>
    </div>
  </div>
</template>
<script>
  // import vList from './comp/vList'
  // import InforCard from '_c/info-card'
  import ans from './comp/ans'
  export default {
    components: {
      ans
    },
    data() {
      return {
        getUrl: this.apis.GETFILEURL,
        compName:'',
        theme1: 'dark',
        total: 0,
        mess:{},
        param: {
          pageSize: 8,
          pageNum: 1,
          reply: 2,
        },
        list: [],
      }
    },
    created() {
      this.getPagerList()
    },
    methods: {
      close(){
        this.compName = ' '
        this.mess = ''
      },
      changeMenu(name){
        if(name == '1'){
          this.param.reply = 2
          this.getPagerList()
        }else {
          this.param.reply = 1
          this.getPagerList()
        }
      },
      chPager(p) {
        this.param.pageNum = p.pageNum
        this.param.pageSize = p.pageSize
        this.getPagerList()
      },
      getPagerList() {
        this.$http.post('/api/question/pager', this.param).then((res) => {
            if(res.code == 200){
              this.list = res.page.list
              this.total = res.page.total
              let a = res.page.list
              for (let i = 0 ;i<a.length;i++){
                if(a[i].himg !=  null){
                  if(!a[i].himg.startsWith('http')){
                    a[i].himg = this.getUrl + a[i].himg;
                    console.log(a[i].himg);
                  }
                }
              }
            }
        })
      },
      saveAns(p){
        this.$http.post('/api/question/update', p).then((res) => {
          if(res.code == 200){
            this.$Message.success(res.message);
            this.compName=''
            this.getPagerList()
          }else {
            this.$Message.error(res.message);
          }
        })
      },
      handleReply(id) {
        this.mess = id
        this.compName = 'ans'
      }
    }
  }
</script>

<style scoped>
  .message_box {
     background:#eee;
      padding: 10px;
      display:inline;
      width: 1200px;
  }
  .message_box_v{
    width: 300px;
  }

</style>
