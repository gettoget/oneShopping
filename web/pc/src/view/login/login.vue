<style lang="less">
  @import './login.less';
</style>

<template>
  <div class="login">
    <div class="login-TiT">
      Go - ABC
    </div>
    <div class="login-con">
      <div class="FormBox box_col">
        <div class="FormTit">
          管理
        </div>
        <div class="FormCenter box_row_100">
          <login-form @on-success-valid="handleSubmit"></login-form>
        </div>
      </div>
      <!--<Card icon="log-in" title="欢迎登录" :bordered="false">-->
        <!--<div class="form-con">-->
        <!--</div>-->
      <!--</Card>-->
    </div>
  </div>
</template>

<script>
import LoginForm from '_c/login-form'
import {setToken,setUserId,localSave} from '@/libs/util'
import { mapActions } from 'vuex'
export default {
  components: {
    LoginForm
  },
  data(){
    return {
    }
  },
  created(){
  },
  methods: {
    ...mapActions([
      'handleLogin',
      'getUserInfo'
    ]),
    handleSubmit (form) {
      this.login(form)
    },
    login(form){
      var v = this
      v.$http.post(this.apis.LOGIN, form).then((res) => {
        if (res.code === 200) {
          setToken(res.result.accessToken.token)
          setUserId(res.result.accessToken.userId)

          this.$router.push('home')
        } else {
          this.swal({
            text: res.message,
            type: 'error',
            showCancelButton: false,
            confirmButtonText:'确认',
          });
        }
      })
    },
    initDict(dictList) {
      for (let r of dictList){
        if ('ZDCLK1017' === r.lmdm){
          this.initSchoolPY(r);
          break;
        }
      }
      sessionStorage.setItem('dictMap', dictList)
    },
    initSchoolPY(dict){
      for (let r of dict.zdxmList){
        r.by1 = this.util.parsePY(r.zdmc);
      }
    },
  }
}
</script>

<style>

</style>
