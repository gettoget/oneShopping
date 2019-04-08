<template>
  <Form ref="loginForm" :model="form" :rules="rules" @keydown.enter.native="handleSubmit">
    <FormItem prop="username">
      <Input v-model="form.username" placeholder="请输入用户名" >
      <span slot="prepend">
          <Icon :size="16" type="ios-person"></Icon>
        </span>
      </Input>
    </FormItem>
    <FormItem prop="password">
      <Input type="password" v-model="form.password" placeholder="请输入密码">
      <span slot="prepend">
          <Icon :size="14" type="md-lock"></Icon>
        </span>
      </Input>
    </FormItem>
    <Row>
      <Col span="14">
        <Row>
          <Col span="24">
            <FormItem prop="captcha">
              <Input v-model="form.captcha" placeholder="请输入验证码">
              <span slot="prepend">
              <Icon :size="14" type="md-key"></Icon>
            </span>
              </Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="24">
            <Row>
              <Col span="12" style="padding: 0 16px">
                <Button @click="handleSubmit" type="primary" long>登录</Button>
              </Col>
              <Col span="12" style="padding: 0 16px">
                <Button @click="newMess" type="primary" long>重置</Button>
              </Col>
            </Row>
          </Col>
        </Row>
      </Col>
      <Col span="10">
        <img :src="YzUrl" width="100%" alt="验证码" style="margin-left: 8px;cursor: pointer"  @click="getUrl">
      </Col>
    </Row>
  </Form>
</template>
<script>
export default {
  name: 'LoginForm',
  props: {
    userNameRules: {
      type: Array,
      default: () => {
        return [
          {required: true, message: '账号不能为空', trigger: 'blur'}
        ]
      }
    },
    passwordRules: {
      type: Array,
      default: () => {
        return [
          {required: true, message: '密码不能为空', trigger: 'blur'}
        ]
      }
    },
    captchaRules:{
      type: Array,
      default: () => {
        return [
          {required: true, message: '请填写验证码', trigger: 'blur'}
        ]
      }
    }
  },
  data () {
    return {
      YzUrl: '',
      form: {
        username: '',
        password: '',
        captcha:'',
        codeID:'',
      }
    }
  },
  computed: {
    rules() {
      return {
        username: this.userNameRules,
        password: this.passwordRules,
        captcha:this.captchaRules
      }
    }
  },
  created(){
    this.getUrl()
  },
  methods: {
    newMess(){
      this.form = {
        username: '',
        password: '',
        captcha:'',
        codeID:''
      }
    },
    getRandom(val) {//取随机数
      let line = 1
      if (val && val > 1) {
        line = val
      }
      let num = ''
      for (var i = 0; i < line; i++) {
        num += Math.floor(Math.random() * 10)
      }
      return num
    },
    getUrl() {
      let codeId =this.getRandom(8)
      this.YzUrl = this.apis.url + this.apis.LOGIN.YZ + codeId
      this.form.codeID = codeId
    },
    handleSubmit () {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.$emit('on-success-valid', this.form)
        }
      })
    }
  }
}
</script>
