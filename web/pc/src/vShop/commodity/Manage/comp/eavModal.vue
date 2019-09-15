<template>
  <div>
    <Modal
      v-model="showModal"
      title="晒单"
      :mask-closable="false"
      :closable="false"
      width="600"
    >
      <Input v-model="formInline.content" type="textarea"
             :autosize="{minRows: 4,maxRows: 4}" placeholder="......" />
      <div style="margin-top: 24px">
        <Row :gutter="32">
          <Col span="6" v-if="formInline.img.length>0"
            v-for="(it,index) in formInline.img"
          >
            <div class="eavImgBox">
              <img :src="apis.GETFILEURL+it" alt="">
              <div class="mask" @click="removeItem(index)">
                <Icon type="ios-trash" color="#ffffff" size="32"/>
              </div>
            </div>
          </Col>
          <Col span="6" v-if="formInline.img.length<4">
            <div class="eavUpBox box_row colCenter">
              <up-file-img upGroup="urls" @handleSuccess="contentImg">
                <Button type="text">
                  <Icon type="md-cloud-upload" size="80"/>
                </Button>
              </up-file-img>
            </div>
          </Col>
        </Row>

      </div>
      <div slot="footer" class="box_row rowRight colCenter">
        <Button @click="close">取消</Button>
        <Button type="primary" @click="save">提交</Button>

      </div>

    </Modal>
  </div>
</template>

<script>
  import upFileImg from '_p/upLoadComp/index.vue'

  export default {
    name: "eavModal",
    components: {
      upFileImg
    },
    props:{
      proId:{
        type:String,
        default:''
      }
    },
    data(){
      return {
        showModal:true,
        formInline:{
          proId:this.proId,
          content:'',
          img:[]
        }
      }
    },
    methods:{
      contentImg(url){
        console.log(url);
        this.formInline. img.push(url)
      },
      removeItem(index){
        this.formInline. img.splice(index,1)
      },
      save(){
        let param = JSON.parse(JSON.stringify(this.formInline))
        param. img =param. img.join(',')
        this.$http.post('/api/proeval/saveEval',param).then(res=>{
          if(res.code == 200){
            this.$Message.success(res.message);
            this.close()
          }
        })
      },
      close(){
        this.$emit('close')
      }

    }
  }
</script>

<style lang="less" scoped>
  .eavImgBox{
    /*background-color: orangered;*/
    height: 118px;
    position: relative;
    img{
      height: 100%;
      width: 100%;
    }
    &:hover{
      .mask{
        display: block;
      }
    }
    .mask{
      display: none;
      position: absolute;
      left: 0;
      top: 0;
      bottom: 0;
      right: 0;
      background-color: rgba(0,0,0,0.3);
      cursor: pointer;
      i{
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%,-50%);
      }
    }
  }
  .eavUpBox{
    height: 118px;
  }
</style>
