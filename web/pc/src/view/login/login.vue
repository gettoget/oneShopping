<style lang="less">
  @import './login.less';
</style>

<template>
  <div class="login">
    <div class="login-con">
      <Card icon="log-in" title="欢迎登录" :bordered="false">
        <div class="form-con">
          <login-form @on-success-valid="handleSubmit"></login-form>
        </div>
      </Card>
    </div>
  </div>
</template>

<script>
import LoginForm from '_c/login-form'
import Cookies from 'js-cookie';
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
      // this.$router.push({
      //   name: this.$config.homeName
      // })

      // this.handleLogin({ userName, password }).then(res => {
      //   this.getUserInfo().then(res => {
      //   })
      // })
    },
    login(form){
      var v = this
      v.$http.post(this.apis.LOGIN.QUERY, form).then((res) => {
        if (res.code === 200) {
        //   localStorage.setItem('user',this.form.username)
        //   Cookies.set('usermess', this.form.username);
          Cookies.set('accessToken', res.result.accessToken);
        //
        //   sessionStorage.setItem("userInfo", JSON.stringify(res.result.userInfo));
        //   localStorage.setItem('menuList', JSON.stringify(res.result.menuTree))
        //   this.setMenuList()
          v.initDict(res.result.dictList);
        //
          this.$router.push('home')
        // } else {
        //   this.swal({
        //     text: res.message,
        //     type: 'error',
        //     showCancelButton: false,
        //     confirmButtonText:'确认',
        //   });
        }
        // this.getUrl()
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
