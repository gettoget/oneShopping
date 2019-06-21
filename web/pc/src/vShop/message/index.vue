<template>
  <div>
    <div>
      <Row  :gutter="20">
        <i-col :md="24" :lg="12" :xl="6"
               style="height: 200px;padding-bottom: 10px;">
          <infor-card shadow color="#ff9900" icon="md-pulse" tit="待回复" :icon-size="36">
            <div class="box_col rowAuto" style="padding-left: 18px">
              <div class="box_row colCenter">
                <div class="count-tit-style">今日 :</div>
                <count-to :end="16" count-class="count-style"/>
              </div>
              <div class="box_row colCenter">
                <div class="count-tit-style">本月 :</div>
                <count-to :end="16" count-class="count-style"/>
              </div>
              <div class="box_row colCenter">
                <div class="count-tit-style">累计 :</div>
                <count-to :end="15" count-class="count-style"/>
              </div>
            </div>
          </infor-card>
        </i-col>
        <i-col :md="24" :lg="12" :xl="6"
               style="height: 200px;padding-bottom: 10px;">
          <infor-card shadow color="#DFGHGF" icon="md-pulse" tit="已回复" :icon-size="36">
            <div class="box_col rowAuto" style="padding-left: 18px">
              <div class="box_row colCenter">
                <div class="count-tit-style">今日 :</div>
                <count-to :end="16" count-class="count-style"/>
              </div>
              <div class="box_row colCenter">
                <div class="count-tit-style">本月 :</div>
                <count-to :end="16" count-class="count-style"/>
              </div>
              <div class="box_row colCenter">
                <div class="count-tit-style">累计 :</div>
                <count-to :end="15" count-class="count-style"/>
              </div>
            </div>
          </infor-card>
        </i-col>
      </Row>
    </div>
    <v-list :list="list" @reply="handleReply"></v-list>
  </div>
</template>
<script>
  import vList from './comp/vList'
  import InforCard from '_c/info-card'
  import CountTo from '_c/count-to'
  export default {
    components: {
      vList,InforCard,CountTo
    },
    data () {
      return {
        list: [
          {name:'zhangsan',message:'这个东西很号  不错  下次还来买'},
          {name:'zhangsan',message:'这个东西很号  不错  下次还来买'},
          {name:'zhangsan',message:'这个东西很号  不错  下次还来买'},
          {name:'zhangsan',message:'这个东西很号  不错  下次还来买'},
          {name:'zhangsan',message:'这个东西很号  不错  下次还来买'},
          {name:'zhangsan',message:'这个东西很号  不错  下次还来买'},
        ]
      }
    },

    methods: {
      handleReply(index) {
        this.message = '回复@' + this.list[index].name + ':'
        this.$refs.message.focus()
      }
    }
  }
</script>

<style scoped>
  .message {
    display: flex;
    flex-direction: column;
  }
  .message div{
    margin-bottom: 12px;
    flex-direction: row;
  }
  .message input:focus,
  .message textarea:focus{
    border: 1px solid #3399ff;
    border-radius: 5px;
  }
  .message .btn-con{
    text-align: center;
    display: inline-block;
  }
  .message .btn{
    padding: 6px 15px;
    border: 1px solid #39f;
    border-radius: 4px;
    color: #fff;
    background-color: #39f;
    cursor: pointer;
    outline: none;
  }
</style>
