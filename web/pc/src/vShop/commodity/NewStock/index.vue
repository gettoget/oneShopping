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
          <FormItem label="商品类型" prop="classify">
            <Select v-model="formValidate.proType" placeholder="选择商品分类">
              <Option value="computer">电脑</Option>
              <Option value="phone">手机</Option>
              <Option value="pad">平板电脑</Option>
              <Option value="camero">相机</Option>
              <Option value="accessories">数码配件</Option>
              <Option value="other">其他</Option>
            </Select>
          </FormItem>
          <FormItem label="商品名称" prop="name">
            <Input v-model="formValidate.proName" placeholder="输入商品名称"></Input>
          </FormItem>
          <FormItem label="商品单价" prop="price">
            <Input v-model="formValidate.proPrice" placeholder="输入商品单价"></Input>
          </FormItem>
          <FormItem label="proStore" prop="amount">
            <Input v-model="formValidate.proStore" placeholder="输入proStore"></Input>
          </FormItem>
          <FormItem label="商品标签" prop="desc">
            <Input v-model="formValidate.proSign" type="textarea" :autosize="{minRows: 1,maxRows: 5}"
                   placeholder="输入商品标签"></Input>
          </FormItem>
          <FormItem label="抢购类型" prop="amount">
            <Input v-model="formValidate.rType" placeholder="输选择抢购类型"></Input>
          </FormItem>
        </Form>
      </div>
      <div class="box_row_1auto boxPadd_L">
        <Row>
          <Col span="12" class-name="coverImgBoxSty boxPadd_LR">
            <h2>封面图</h2>

            <div span="12" class="contentItemSty" v-if="formData.coverImg">
              <img :src="formData.coverImg" alt="">
              <div class="ingMask">
                <Icon type="ios-trash" size="60" color="#fff" @click.native="formData.coverImg = ''"/>
              </div>
            </div>
            <div class="" v-else>
              <up-file-img @handleSuccess="(url)=>{handleSuccess(url,'coverImg')}">
                <img v-if="formData.coverImg" :src="formData.coverImg" alt="">
                <Button v-else type="dashed">
                  <Icon type="md-cloud-upload" size="80"/>
                </Button>
              </up-file-img>
            </div>
          </Col>
          <Col span="12" class-name="tuijianImgBoxSty boxPadd_LR">
            <h2>推荐图</h2>

            <div class="contentItemSty" v-if="formData.tuijianImg">
              <img :src="formData.tuijianImg" alt="">
              <div class="ingMask">
                <Icon type="ios-trash" size="60" color="#fff" @click.native="formData.tuijianImg = ''"/>
              </div>
            </div>
            <div class="" v-else>
              <up-file-img @handleSuccess="(url)=>{handleSuccess(url,'tuijianImg')}">
                <img v-if="formData.tuijianImg" :src="formData.tuijianImg" alt="">
                <Button v-else type="dashed">
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
          <Col span="12" class-name="contentImgSty boxPadd_LR boxPadd_B" v-for="(it,index) in formData.content" :key="index">
            <div class="contentItemSty">
              <img :src="it" alt="">
              <div class="ingMask">
                <Icon type="ios-trash" size="60" color="#fff" @click.native="removeItem(index)"/>
              </div>
            </div>
          </Col>
          <Col span="12"  class-name="boxPadd_LR boxPadd_B" v-if="formData.content.length<6">
            <div class="">
              <up-file-img @handleSuccess="contentImg">
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
        formValidate: {
          proType: "",//商品类目(必填)
          proName: "",//商品名称(必填)
          proPrice: "",//商品单价(必填)
          proStore: "",//商品库存(必填)
          proSign: "",//商品标签(选填)  如 你搜索电脑的时候 搜 16G 这样的标签
          rType: "",//商品抢购类型(必填 , 后期是可以改的)  1 为人类有可能中奖  2 为机器人必中奖
          urls: "",//图片url , 用逗号隔开
          coverUrl: "",//封面url
          refUrl: "",
        },
        ruleValidate: {
          name: [
            {required: true, message: '商品名称不能为空', trigger: 'blur'}
          ],
          price: [
            {required: true, message: '售价不能为空', trigger: 'blur'},
            {type: 'number', message: '售价需为数字', trigger: 'blur'}
          ],
          classify: [
            {required: true, message: '选择商品分类', trigger: 'change'}
          ],
          amount: [
            {required: true, message: '销售份额不能为空', trigger: 'blur'},
            {type: 'number', message: '销售份额需为数字', trigger: 'blur'}
          ],
          peopleNum: [
            {required: true, message: '中奖人数不能为空', trigger: 'blur'},
            {type: 'number', message: '中奖人数需为数字', trigger: 'blur'}
          ],
          uploaddate: [
            {required: true, type: 'datetime', message: '选择上架时间', trigger: 'change'}
          ],
          downloaddate: [
            {required: true, type: 'datetime', message: '选择下架时间', trigger: 'change'}
          ],
          desc: [
            {required: true, type: 'string', message: '请填写商品详情', trigger: 'blur'}
          ]
        },
        formData: {
          coverImg: "",//封面图片
          tuijianImg: "",//推荐图
          content: []
        }
      }

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
        this.$refs[name].validate((valid) => {
          if (valid) {
            this.$Message.success('Success!');
          } else {
            this.$Message.error('Fail!');
          }
        })
      },
      handleReset(name) {
        this.$refs[name].resetFields();
      }
    }
  }
</script>
