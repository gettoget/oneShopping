<style lang="less">
  .vertical-center-modal{
    display: flex;
    align-items: center;
    justify-content: center;
    -webkit-user-select: text;

    .ivu-modal{
      top: 0;
    }
  }
  #vux-scroller-2t6n6.messagebox{
    height: 600px;
    -webkit-user-select: text;
  }
  .messagebox{
    -webkit-user-select: text;
    .xs-container{
      -webkit-user-select: text;
    }
  }
</style>
<template>
  <div>
    <Modal
      :title="'@'+mess.username+' : ' "
      v-model="modal9"
      style="height: 700px"
      cancel-text="close"
      ok-text="close"
      :closable="false"
      @on-ok="OK"
      @on-cancel="cancel"
    >
      <div style="height: 600px;overflow:auto">
        <th-message
          style="height: 600px;-webkit-user-select: text;"
          ref="messgebox"
          :useText="true"
          :usePulldown="true"
          :pulldownConfig="pulldownconfig"
          :topPadding="20"
          :messageData="messageData"
          :funcList="funclist"
          :showInput="false"
          :selfFace="selfFace"
          :otherFace="otherFace"
          :showEmoticon="false"
          @sendOut="senRequest"
          @bigBtnCall="btncall"
          @pulldownCall="pulldowncall">
        </th-message>
      </div>
       <div style="padding: 10px">
         <Row>
           <Col span="20"> <Input v-model="value5" type="textarea" placeholder="Enter something..." /></Col>
           <Col span="4"><Button type="primary" style="padding: 15px;margin-left: 8px" @click="senRequest">{{$t("FS")}}</Button></Col>
         </Row>

       </div>
    </Modal>
  </div>
</template>

<script>
    // import Clipboard from 'clipboard';
    export default {
    props: {
      mess:{}
    },
    data () {
      return {
        value5: '',
        selfFace:'',
        otherFace:'',
        messageData:[],
        message:[],
        pulldownconfig:{default: 0,up: 0,down: 0,loading: 0},
        funclist:["quick"],
        modal9: true,
        ans:''
      }
    },
    created(){
        this.getPagerList()
    },
    methods:{

      senRequest(){
        this.$http.post('/api/question/update', {id:this.mess.id,content:this.value5}).then((res) => {
          if (res.code == 200) {
            this.$Message.success(res.message);
            this.$emit('getPagerList')
            this.value5 = ''
            this.getPagerList()
          } else {
            this.$Message.error(res.message);
          }
        })
      },
      btncall(){},
      pulldowncall(){

      },
      OK(){
        this.$emit("close",{})
      },
      cancel(){
        this.$emit("close",{})
      },
      getPagerList(){
        this.$http.post('/api/question/getOneMess',{userId:this.mess.userId,pageSize:999}).then((res)=>{
          if(res.code == 200){
            var a  = res.page.list
            this.otherFace = " https://www.go-saku.com/api/"+res.page.list[0].himg
            for(let i = a.length-1 ;i>=0;i--){
              var b = {}
              b.text = a[i].content
              if(a[i].type == 1){
                b.type = 2
              } else if(a[i].type == 2){
                b.type = 1
              }else {
                b.type = 3
              }
              b.isimg = false
              this.message.push(b)
            }
            this.messageData = this.message
          }else {
            this.$Message.error(res.message);
          }
        })
      },
    }
  }
</script>

<style scoped>

</style>
