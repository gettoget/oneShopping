<style lang="less">
  @import "./index.less";
</style>
<template>
  <Card class="NewStockSty box_col">
    <div slot="title">
      <pager-tit title="商品上架"></pager-tit>
    </div>

    <div class="UpBox box_row">
      <div class="box_row_100 boxPadd_R">
        <Form ref="formValidate" :model="formValidate" :rules="ruleValidate">
          <FormItem label="商品类型" prop="proType">
            <Select v-model="formValidate.proType" placeholder="选择商品分类">
              <Option value="computer">电脑</Option>
              <Option value="phone">手机</Option>
              <Option value="pad">平板电脑</Option>
              <Option value="camero">相机</Option>
              <Option value="accessories">数码配件</Option>
              <Option value="other">其他</Option>
            </Select>
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
              <Option value="1">1</Option>
              <Option value="2">2</Option>
            </Select>
          </FormItem>
          <FormItem>
            <div class="box_row rowRight">
              <Button type="success" style="margin-right: 12px"
                      @click="handleSubmit('formValidate')">提交
              </Button>

              <Button @click="handleReset">重置</Button>
            </div>
          </FormItem>
        </Form>
      </div>
      <div class="box_row_1auto boxPadd_L">
        <Row>
          <Col span="12" class-name="coverImgBoxSty boxPadd_LR">
            <h2>封面图</h2>

            <div span="12" class="contentItemSty" v-if="formData.coverImg">
              <img :src="getUrl+formData.coverImg" alt="">
              <div class="ingMask">
                <Icon type="ios-trash" size="60" color="#fff" @click.native="formData.coverImg = ''"/>
              </div>
            </div>
            <div class="" v-else>
              <up-file-img upGroup="cover" @handleSuccess="(url)=>{handleSuccess(url,'coverImg')}">
                <Button type="dashed">
                  <Icon type="md-cloud-upload" size="80"/>
                </Button>
              </up-file-img>
            </div>
          </Col>
          <Col span="12" class-name="tuijianImgBoxSty boxPadd_LR">
            <h2>推荐图</h2>

            <div class="contentItemSty" v-if="formData.tuijianImg">
              <img :src="getUrl+formData.tuijianImg" alt="">
              <div class="ingMask">
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
              <img :src="getUrl+it" alt="">
              <div class="ingMask">
                <Icon type="ios-trash" size="60" color="#fff" @click.native="removeItem(index)"/>
              </div>
            </div>
          </Col>
          <Col span="12" class-name="boxPadd_LR boxPadd_B" v-if="formData.content.length<6">
            <div class="">
              <up-file-img upGroup="urls" @handleSuccess="contentImg">
                <Button type="dashed">
                  <Icon type="md-cloud-upload" size="80"/>
                </Button>
              </up-file-img>
            </div>
          </Col>
        </Row>
      </div>
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
    data() {
      return {
        getUrl: this.apis.GETFILEURL,
        formValidate: {
          proType: "computer",//商品类目(必填)
          proName: "商品",//商品名称(必填)
          proPrice: "123",//商品单价(必填)
          proStore: "123",//商品库存(必填)
          proSign: "6666",//商品标签(选填)  如 你搜索电脑的时候 搜 16G 这样的标签
          rType: "1",//商品抢购类型(必填 , 后期是可以改的)  1 为人类有可能中奖  2 为机器人必中奖
          urls: "refUrl/77f473cf06bf4ecd9e4c8ed769cfee31.jpg",//图片url , 用逗号隔开
          coverUrl: "refUrl/77f473cf06bf4ecd9e4c8ed769cfee31.jpg",//封面url
          refUrl: "refUrl/77f473cf06bf4ecd9e4c8ed769cfee31.jpg",
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
              v.$http.post("/api/probaseinfo/save", this.formValidate).then(res => {
                if (res.code == 200) {
                  v.shopUp(res.result)
                }
              }).catch(err => {
              })
            } else {
            }
          })
        }
      },
      shopUp(shopId) {
        var v = this
        this.swal({
          title: "商品上架？",
          type: "warning",
          showCancelButton: true,
          confirmButtonText: "立即上架",
          cancelButtonText: "稍后操作",
        }).then((isConfirm) => {
          if (isConfirm.value) {
            v.$http.post("/api/proinfo/saveOne",{id:shopId}).then(res=>{
              if(res.code == 200){
                v.$Message.success('商品上架成功')
              }else {
                v.$Message.error('商品上架失败')
              }

            }).catch(err=>{
              v.$Message.error('商品上架失败')
            })
          }
          v.handleReset()
        });
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
