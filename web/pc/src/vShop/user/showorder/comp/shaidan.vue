<template>
  <Card>
    <div slot="title" class="box_row colCenter">
      <!--<Avatar :src="apis.GETFILEURL+mess.himg" />-->
      <Avatar :src="mess.himg"/>
      <div style="margin:0 16px">{{mess.userName}}</div>

      <Icon type="ios-thumbs-up" size="26" color="#fe5722"/>
      <div style="font-weight: 600;font-size: 22px;padding: 0 12px;color: rgba(254,87,34,0.5)">
        X{{mess.bz1}}
      </div>
      <div class="box_col_100">
        <div style="float: right;font-size: 16px;font-weight: 600;color: rgba(254,87,34,1);">
          {{mess.cjsj}}
        </div>
      </div>
    </div>

    <div style="overflow: auto;border-bottom: solid 2px #ededed;padding-bottom: 16px">
      <div class="box_row_z">
        <div v-for="(it,index) in mess.img.split(',')" style="margin: 0 12px">
          <img style="width: 75px;height: 75px" :src="apis.GETFILEURL+it">
        </div>
      </div>
    </div>
    <div class="box_row" style="margin-top: 16px">
      <div class="box_row_100">
        <h3>{{$t('PJ')}}：</h3>
        <div style="color: #a7a0a0">
          {{mess.content}}
        </div>
      </div>
      <Button type="error" @click="delet" size="small">{{$t("SC")}}</Button>
    </div>
  </Card>
</template>

<script>
  export default {
    name: "shaidan",
    data() {
      return {
        value2: 0
      }
    },
    props: {
      mess: {
        type: Object,
        default: () => {
          return {}
        }
      }
    },
    created() {
      console.log(this.mess);
    },
    methods: {
      Shtg() {
        this.swal({
            title: "审核成功！",
            text: "已成功审核数据",
            type: "success"
          },
          function () {
            window.location = "/"
          }
        )
      },
      delet() {
        var v = this
        this.swal({
          title: "您确定要删除这条数据吗",
          text: "删除后将无法恢复，请谨慎操作！",
          type: "warning",
          showCancelButton: true,
          confirmButtonColor: "#DD6B55",
          confirmButtonText: "确定删除！",
          cancelButtonText: "取消",
          closeOnConfirm: false,
          closeOnCancel: false
        }).then((val)=>{
          console.log(val);
          if(val.value){
            v.$http.post("/api/proeval/remove/"+v.mess.id).then(res=>{
              if(res.code == 200){
                v.$parent.getDataList()
              }
            }).catch(err=>{})
          }
        })

      }
    }
  }
</script>

<style scoped>

</style>
