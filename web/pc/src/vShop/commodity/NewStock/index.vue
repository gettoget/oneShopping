<style lang="less">
  @import "./index.less";
</style>
<template>
  <div class="box_col NewStockSty">
    <div style="padding: 18px 0">
      <h1>商品上架</h1>
    </div>
    <hr style="margin: 18px 0">
    <Row :gutter="16">
      <Col span="4" class-name="coverImgBoxSty">
        <h2>封面图</h2>
        <up-file-img @handleSuccess="(url)=>{handleSuccess(url,'coverImg')}">
          <img v-if="formData.coverImg" :src="formData.coverImg" alt="">
          <Button v-else type="dashed">
            <Icon type="md-cloud-upload" size="120"/>
          </Button>
        </up-file-img>
      </Col>
      <Col span="4" class-name="tuijianImgBoxSty">
        <h2>推荐图</h2>
        <up-file-img @handleSuccess="(url)=>{handleSuccess(url,'tuijianImg')}">
          <img v-if="formData.tuijianImg" :src="formData.tuijianImg" alt="">
          <Button v-else type="dashed">
            <Icon type="md-cloud-upload" size="120"/>
          </Button>
        </up-file-img>
      </Col>
    </Row>
    <div style="margin-top: 24px">
      <h2>内容图</h2>
    </div>
    <Row :gutter="16" class-name="contentImgSty">
      <Col span="4" class-name="contentItemSty" v-for="(it,index) in formData.content" :key="index">
        <img :src="it" alt="">
        <div class="ingMask">
          <Icon type="ios-trash" size="60" color="#fff" @click.native="removeItem(index)"/>
        </div>
      </Col>
      <Col span="4" v-if="formData.content.length<5">
        <up-file-img @handleSuccess="contentImg">
          <Button type="dashed">
            <Icon type="md-cloud-upload" size="120"/>
          </Button>
        </up-file-img>
      </Col>
    </Row>

    <!-------------------------------->
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate">
      <Row :gutter="16">
        <Col span="6">
          <FormItem label="商品类型" prop="classify">
            <Select v-model="formValidate.classify" placeholder="选择商品分类">
              <Option value="computer">电脑</Option>
              <Option value="phone">手机</Option>
              <Option value="pad">平板电脑</Option>
              <Option value="camero">相机</Option>
              <Option value="accessories">数码配件</Option>
              <Option value="other">其他</Option>
            </Select>
          </FormItem>
        </Col>
        <Col span="6">
          <FormItem label="商品名称" prop="name">
            <Input v-model="formValidate.name" placeholder="输入商品名称"></Input>
          </FormItem>
        </Col>
        <Col span="6">
          <FormItem label="商品参数" prop="price">
            <Input v-model="formValidate.price" placeholder="输入商品参数"></Input>
          </FormItem>
        </Col>
        <Col span="6">
          <FormItem label="商品详情" prop="desc">
            <Input v-model="formValidate.desc" type="textarea" :autosize="{minRows: 1,maxRows: 5}"
                   placeholder="输入商品详情."></Input>
          </FormItem>
        </Col>
      </Row>
      <Row :gutter="16">
        <Col span="6">
          <FormItem label="销售份额" prop="amount">
            <Input v-model="formValidate.amount" placeholder="输入商品售价"></Input>
          </FormItem>
        </Col>
        <Col span="6">
          <FormItem label="销售份额" prop="amount">
            <Input v-model="formValidate.amount" placeholder="输入商品售价"></Input>
          </FormItem>
        </Col>
      </Row>


      <FormItem>
        <Button type="primary" @click="handleSubmit('formValidate')">提交</Button>
        <Button @click="handleReset('formValidate')" style="margin-left: 8px">重置</Button>
      </FormItem>
    </Form>
    <!--</div>-->
  </div>
</template>

<script>
  // import StockPhoto from "./StockPhoto"
  import upFileImg from '_p/upLoadComp/index.vue'

  export default {
    name: "index",
    components: {
      // StockPhoto
      upFileImg
    },
    data() {
      return {
        formValidate: {
          name: '',//商品名称
          price: '',//商品参数
          classify: '',//商品类型
          amount: '',
          peopleNum: '',
          uploaddate: '',
          downloaddate: '',
          desc: '',//商品详情
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
      handleSuccess(url, key) {
        this.formData[key] = url
      },
      contentImg(url) {
        this.formData.content.push(url)
      },
      removeItem(index) {
        this.formData.content.splice(index, 1)
      },
      handleSubmit(name) {
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
