<style lang="less">
  @import "./index.less";
</style>
<template>
  <Card class="NewStockSty box_col">

    <div class="UpBox box_row">
      <div class="box_row_100 boxPadd_R">
        <Form ref="formValidate" :model="formValidate" :rules="ruleValidate">
          <FormItem label="商品类型" prop="proType">
            <Input v-model="formValidate.proType" placeholder="商品类型"></Input>
          </FormItem>
          <FormItem label="商品名称" prop="proName">
            <Input v-model="formValidate.proName" placeholder="输入商品名称"></Input>
          </FormItem>
          <FormItem label="商品单价" prop="proPrice">
            <Input v-model="formValidate.proPrice" placeholder="输入商品单价"/>
          </FormItem>
          <FormItem label="商品库存" prop="proStore">
            <Input v-model="formValidate.proStore" placeholder="输入库存量"/>
          </FormItem>
          <FormItem label="商品标签" prop="proSign">
            <Input v-model="formValidate.proSign" type="textarea" :autosize="{minRows: 1,maxRows: 5}"
                   placeholder="输入商品标签"></Input>
          </FormItem>
          <FormItem label="抢购类型" prop="rType">
            <Select v-model="formValidate.rType" placeholder="选择抢购类型">
              <Option value="1">人类有可能中奖</Option>
              <Option value="2">机器人必中奖</Option>
            </Select>
          </FormItem>
        </Form>
      </div>
      <div class="box_row_1auto boxPadd_L">
        <Row>
          <Col span="12" class-name="coverImgBoxSty boxPadd_LR">
            <h2>封面图</h2>

            <div span="12" class="contentItemSty" v-if="formData.coverImg">
              <img v-if="formData.coverImg.substring(0,4)==='http'" :src="formData.coverImg" alt="">
              <img v-else :src="getUrl+formData.coverImg" alt="">

              <div class="ingMask">
                <Icon type="ios-trash" size="60" color="#fff" @click.native="formData.coverImg = ''"/>
              </div>
            </div>
            <div class="" v-else>
              <up-file-img upGroup="cover" @handleSuccess="(url)=>{handleSuccess(url,'coverImg')}">
                <Button type="text">
                  <Icon type="md-cloud-upload" size="80"/>
                </Button>
              </up-file-img>
            </div>
          </Col>
          <Col span="12" class-name="tuijianImgBoxSty boxPadd_LR">
            <h2>推荐图</h2>

            <div class="contentItemSty" v-if="formData.tuijianImg">
              <img v-if="formData.tuijianImg.substring(0,4)==='http'" :src="formData.tuijianImg" alt="">
              <img v-else :src="getUrl+formData.tuijianImg" alt="">

              <div class="text">
                <Icon type="ios-trash" size="60" color="#fff" @click.native="formData.tuijianImg = ''"/>
              </div>
            </div>
            <div class="" v-else>
              <up-file-img upGroup="refUrl" @handleSuccess="(url)=>{handleSuccess(url,'tuijianImg')}">
                <Button type="dashed">
                  <Icon type="md-cloud-upload" size="80"/>
                </Button>
              </up-file-img>
            </div>
          </Col>
        </Row>
        <div style="margin-top: 12px;padding-left: 18px">
          <h2>内容图</h2>
        </div>
        <Row>
          <Col span="12" class-name="contentImgSty boxPadd_LR boxPadd_B" v-for="(it,index) in formData.content"
               :key="index">
            <div class="contentItemSty">
              <img v-if="it.substring(0,4)==='http'" :src="it" alt="">
              <img v-else :src="getUrl+it" alt="">
              <div class="ingMask">
                <Icon type="ios-trash" size="60" color="#fff" @click.native="removeItem(index)"/>
              </div>
            </div>
          </Col>
          <Col span="12" class-name="boxPadd_LR boxPadd_B" v-if="formData.content.length<6">
            <div class="">
              <up-file-img upGroup="urls" @handleSuccess="contentImg">
                <Button type="text">
                  <Icon type="md-cloud-upload" size="80"/>
                </Button>
              </up-file-img>
            </div>
          </Col>
        </Row>
      </div>
    </div>
    <div>
      <Button type="success" style="margin-right: 12px;width:100%"
              @click="handleSubmit('formValidate')">save
      </Button>
    </div>
  </Card>
</template>

<script>
  import upFileImg from '_p/upLoadComp/index.vue'

  export default {
    name: "index",
    components: {
      upFileImg
    },
    props:{
      itMess:{
        type:Object,
        default:{}
      }
    },
    data() {
      return {
        getUrl: this.apis.GETFILEURL,
        formValidate: {
          id:"",
          proType: "computer",//商品类目(必填)
          proName: "商品",//商品名称(必填)
          proPrice: "123",//商品单价(必填)
          proStore: "123",//商品库存(必填)
          proSign: "6666",//商品标签(选填)  如 你搜索电脑的时候 搜 16G 这样的标签
          rType: "1",//商品抢购类型(必填 , 后期是可以改的)  1 为人类有可能中奖  2 为机器人必中奖
          coverUrl: "refUrl/77f473cf06bf4ecd9e4c8ed769cfee31.jpg",//封面url
          refUrl: "refUrl/77f473cf06bf4ecd9e4c8ed769cfee31.jpg",//推荐
          urls: "refUrl/77f473cf06bf4ecd9e4c8ed769cfee31.jpg",//图片url , 用逗号隔开
        },
        ruleValidate: {
          proType: [
            {required: true, message: '', trigger: 'change'}
          ],
          proName: [
            {required: true, message: '', trigger: 'blur'}
            // {type: 'number', message: '售价需为数字', trigger: 'blur'}
          ],
          proPrice: [
            {required: true, message: '', trigger: 'blur'}
          ],
          proStore: [
            {required: true, message: '', trigger: 'blur'}
          ],
          proSign: [
            {required: true, message: '', trigger: 'blur'}
          ],
          rType: [
            {required: true, message: '', trigger: 'change'}
          ]
        },
        formData: {
          coverImg: "refUrl/77f473cf06bf4ecd9e4c8ed769cfee31.jpg",//封面图片
          tuijianImg: "refUrl/77f473cf06bf4ecd9e4c8ed769cfee31.jpg",//推荐图
          content: ['refUrl/77f473cf06bf4ecd9e4c8ed769cfee31.jpg']
        }
      }
    },
    watch: {
      "formData.coverImg": function (n, o) {
        this.formValidate.coverUrl = n
      },
      "formData.tuijianImg": function (n, o) {
        this.formValidate.refUrl = n
      },
      "formData.content": function (n, o) {
        this.formValidate.urls = n.join(',')
      },
    },
    created(){
      this.formValidate.id = JSON.parse(JSON.stringify(this.itMess)).id
      this.formValidate.proType = JSON.parse(JSON.stringify(this.itMess)).proType
      this.formValidate.proName = JSON.parse(JSON.stringify(this.itMess)).proName
      this.formValidate.proPrice = JSON.parse(JSON.stringify(this.itMess)).proPrice
      this.formValidate.proStore = JSON.parse(JSON.stringify(this.itMess)).proStore
      this.formValidate.proSign = JSON.parse(JSON.stringify(this.itMess)).proSign
      this.formValidate.rType = JSON.parse(JSON.stringify(this.itMess)).rType

      this.formData.coverImg = JSON.parse(JSON.stringify(this.itMess)).coverUrl
      this.formData.tuijianImg = JSON.parse(JSON.stringify(this.itMess)).refUrl
      this.formData.content = JSON.parse(JSON.stringify(this.itMess)).urls.split(',')
    },
    methods: {
      handleSuccess(url, key) {//封面图 推荐图
        this.formData[key] = url
      },
      contentImg(url) {//内容图
        this.formData.content.push(url)
      },
      removeItem(index) {
        this.formData.content.splice(index, 1)
      },
      handleSubmit(name) { //表单提交
        var v = this
        if (this.formValidate.coverUrl == '' ||
          this.formValidate.refUrl == '' ||
          this.formValidate.urls == '') {
          this.$Message.warning({
            content: "请上传商品相关图片！",
          });
        } else {
          this.$refs[name].validate((valid) => {
            if (valid) {
              v.$http.post("/api/probaseinfo/update", this.formValidate).then(res => {
                if (res.code == 200) {
                  this.$Message.success("Information modified successfully")
                  this.$emit("upSuccess")
                }
              }).catch(err => {
              })
            } else {
            }
          })
        }
      },
      handleReset() {
        this.formValidate = {
          proType: "",//商品类目(必填)
          proName: "",//商品名称(必填)
          proPrice: "",//商品单价(必填)
          proStore: "",//商品库存(必填)
          proSign: "",//商品标签(选填)  如 你搜索电脑的时候 搜 16G 这样的标签
          rType: "",//商品抢购类型(必填 , 后期是可以改的)  1 为人类有可能中奖  2 为机器人必中奖
          urls: "",//图片url , 用逗号隔开
          coverUrl: "",//封面url
          refUrl: "",
        }

        this.formData = {
          coverImg: "refUrl/77f473cf06bf4ecd9e4c8ed769cfee31.jpg",//封面图片
          tuijianImg: "refUrl/77f473cf06bf4ecd9e4c8ed769cfee31.jpg",//推荐图
          content: ['refUrl/77f473cf06bf4ecd9e4c8ed769cfee31.jpg']
        }
      }
    }
  }
</script>
