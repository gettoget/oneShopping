<template>
  <div>
    <div>
      商品上架
    </div>
    <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80" >
    <FormItem label="商品名称" prop="name">
      <Input v-model="formValidate.name" placeholder="输入商品名称"></Input>
    </FormItem>
    <FormItem label="商品售价" prop="price">
      <Input v-model="formValidate.price" placeholder="输入商品售价"></Input>
    </FormItem>
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
      <Row>
        <Col span="10">
         <FormItem label="上架时间" prop="uploaddate">
           <DatePicker type="datetime" placeholder="选择上架日期" v-model="formValidate.uploaddate"></DatePicker>
         </FormItem>
        </Col>
          <Col span="10">
        <FormItem  label="下架时间" prop="downloaddate">
          <DatePicker  type="datetime" placeholder="选择下架时间" v-model="formValidate.uptime"></DatePicker >
        </FormItem>
          </Col>
      </Row>

    <FormItem label="销售份额" prop="amount">

        <Input v-model="formValidate.amount" placeholder="输入商品售价"></Input>

    </FormItem>
      <FormItem label="中奖人数" prop="peopleNum">

        <Input v-model="formValidate.peopleNum" placeholder="设置中奖人数"></Input>

      </FormItem>
    <FormItem label="商品详情" prop="desc">
      <Input v-model="formValidate.desc" type="textarea" :autosize="{minRows: 1,maxRows: 5}" placeholder="输入商品详情."></Input>
    </FormItem>
    <FormItem>
      <Button type="primary" @click="handleSubmit('formValidate')">提交</Button>
      <Button @click="handleReset('formValidate')" style="margin-left: 8px">重置</Button>
    </FormItem>
  </Form>
  </div>
</template>

<script>

  export default {
    name: "index",
    components: {
    },
    data () {
      return {
        formValidate: {
          name: '',
          price: '',
          classify: '',
          amount: '',
          peopleNum: '',
          uploaddate: '',
          downloaddate: '',
          desc: ''
        },
        ruleValidate: {
          name: [
            { required: true, message: '商品名称不能为空', trigger: 'blur' }
          ],
          price: [
            { required: true, message: '售价不能为空', trigger: 'blur' },
            { type: 'number', message: '售价需为数字', trigger: 'blur' }
          ],
          classify: [
            { required: true, message: '选择商品分类', trigger: 'change' }
          ],
          amount: [
            { required: true, message: '销售份额不能为空', trigger: 'blur' },
            { type: 'number', message: '销售份额需为数字', trigger: 'blur' }
          ],
          peopleNum: [
            { required: true, message: '中奖人数不能为空', trigger: 'blur' },
            { type: 'number', message: '中奖人数需为数字', trigger: 'blur' }
          ],
          uploaddate: [
            { required: true, type: 'datetime', message: '选择上架时间', trigger: 'change' }
          ],
          downloaddate: [
            { required: true, type: 'datetime', message: '选择下架时间', trigger: 'change' }
          ],
          desc: [
            { required: true, type: 'string',message: '请填写商品详情', trigger: 'blur' }
          ]
        }
      }

    },
    methods: {
      handleSubmit (name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            this.$Message.success('Success!');
          } else {
            this.$Message.error('Fail!');
          }
        })
      },
      handleReset (name) {
        this.$refs[name].resetFields();
      }
    }
  }
</script>

<style scoped>

</style>
