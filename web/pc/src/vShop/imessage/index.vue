<template>
  <div class="box_col findOrderBox">
    <div class="box_row_100">

    </div>
    <div class="box_row_100">
      <div class="box_row rowRight">
        <div>
          <Input
            v-model="param.name" @on-change="getPagerList"
            :placeholder="$t('username')" style="width: 160px;margin-right: 18px"/>
        </div>
        <Button type="primary" @click="getPagerList">
          <Icon type="md-search"></Icon>
          <!--查询-->
        </Button>
      </div>
    </div>

    <div  class="box_col" style="padding-top: 20px" v-if="list && list.length >= 0">
      <Row class="box_col_auto">
        <Col span="6" v-for="(item,index) in list" :key=index>
          <Card style="height: 300px">
              <div style="text-align:center">
                <img style="width: 80px;height: 80px"
                     :src='item.himg'>
                <h3>{{item.username}}</h3>
              </div>
                <div>
                  <Tag color="default">{{item.cjsj}}</Tag>
                  {{item.content}}
                </div>
                <div v-if="item.replyList != null" style="color: #ff6600">
                  <Tag color="volcano">{{item.replyList[item.replyList.length-1].cjsj}}</Tag>
                  {{item.replyList[item.replyList.length-1].content}}
                </div>
              <div class="box_col_z" style="text-align: center">
                <Button type="primary" ghost @click="handleReply(item)">点击回复</Button>
              </div>
          </Card>
        </Col>
      </Row>
      <div>
        <div class="pagerBoxSty boxMar_T box_row rowRight">
          <one-page :total="total" :size="param.pageSize"
                    :opts="[4,8,12,16]"
                    @chPager="chPager"></one-page>
        </div>
      </div>
    </div>
    <div class="box_col" style="color: #5d6c7b;font-size: 48px;font-weight: 700;text-align: center" v-else>
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
        compName: '',
        theme1: 'dark',
        total: 0,
        mess: {},
        param: {
          pageSize: 8,
          pageNum: 1,
          name:''
        },
        list: [],
      }
    },
    created() {
      this.getPagerList()
    },
    methods: {
      close() {
        this.compName = ''
        console.log(123);
      },
      chPager(p) {
        this.param.pageNum = p.pageNum
        this.param.pageSize = p.pageSize
        this.getPagerList()
      },
      getPagerList() {
        this.$http.post('/api/question/getPeoGroup', this.param).then((res) => {
          if (res.code == 200) {
            this.list = res.page.list
            this.total = res.page.total
            let a = res.page.list
            for (let i = 0; i < a.length; i++) {
              if (a[i].himg != null) {
                if (!a[i].himg.startsWith('http')) {
                  a[i].himg = this.getUrl + a[i].himg;
                  console.log(a[i].himg);
                }
              }
            }
          }
        })
      },
      saveAns(p) {
        this.$http.post('/api/question/update', p).then((res) => {
          if (res.code == 200) {
            this.$Message.success(res.message);
            this.compName = ''
            this.getPagerList()
          } else {
            this.$Message.error(res.message);
          }
        })
      },
      handleReply(item) {
        this.mess = item
        this.compName = 'ans'
      }
    }
  }
</script>

<style scoped>
  .message_box {
    background: #eee;
    padding: 10px;
  }
</style>
