<template>
  <Form ref="loginForm" :model="form" :rules="rules" @keydown.enter.native="handleSubmit">
    <FormItem prop="username">
      <Input v-model="form.username" clearable
             placeholder="请输入用户名">
        <Icon :size="16" type="ios-person" slot="prefix"></Icon>
      </Input>
    </FormItem>
    <FormItem prop="password">
      <Input type="password" v-model="form.password" clearable
             placeholder="请输入密码">
        <Icon :size="14" type="md-lock" slot="prefix"></Icon>
      </Input>
    </FormItem>
    <FormItem>
      <div class="submitBut">
        <Button @click="handleSubmit" type="primary" long>登录</Button>
      </div>
    </FormItem>
    <FormItem>
      <Checkbox value="">remember me</Checkbox>
    </FormItem>
    <!--<Row>-->
      <!--<Col span="24">-->
        <!--<Row>-->
          <!--<Col span="12" style="padding: 0 16px">-->
            <!--<Button @click="handleSubmit" type="primary" long>登录</Button>-->
          <!--</Col>-->
          <!--<Col span="12" style="padding: 0 16px">-->
            <!--<Button @click="newMess" type="primary" long>重置</Button>-->
          <!--</Col>-->
        <!--</Row>-->
      <!--</Col>-->
    <!--</Row>-->
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
      }
    },
    data() {
      return {
        YzUrl: '',
        form: {
          username: 'admini',
          password: '123456',
        }
      }
    },
    computed: {
      rules() {
        return {
          username: this.userNameRules,
          password: this.passwordRules,
        }
      }
    },
    created() {
    },
    methods: {
      newMess() {
        this.form = {
          username: 'admini',
          password: '123456',
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
      handleSubmit() {
        this.$refs.loginForm.validate((valid) => {
          if (valid) {
            this.$emit('on-success-valid', this.form)
          }
        })
      }
    }
  }
</script>
