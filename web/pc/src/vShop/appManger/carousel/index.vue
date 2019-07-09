<template>
  <Card class="carouselBox box_col">
    <div slot="title">
      <pager-tit :title="$t('LBT')"></pager-tit>
    </div>
    <div style="width: 1100px;height: 100%;margin: auto;">
      <Row :gutter="32">
        <Col span="6" v-for="(it,index) in dataList" :key="index" style="margin-bottom: 16px">
          <Card>
            <img :src="it.imgUrl.substring(0,4)==='http'?
                  it.imgUrl:apis.GETFILEURL+it.imgUrl"
                 style="width: 100%;height: 173px"
                 alt="">

            <div class="mask">
              <Icon type="ios-trash-outline" @click.native="handleRemove(it)"></Icon>
            </div>
          </Card>
        </Col>
        <Col span="6">
          <Card>
            <div @click="addCarouse" style="height: 173px;position: relative;cursor: pointer">
              <Icon type="md-add" size="120"
                    style="position: absolute;left: 50%;top: 50%;transform: translate(-50%,-50%)"/>
            </div>
          </Card>
        </Col>
      </Row>
    </div>
    <component :is="compName" @closeMod="closeMod" @saveOK="saveOK"></component>
  </Card>
</template>

<script>
  import addFile from './comp/addMod'
  export default {
    name: "index",
    components:{
      addFile
    },
    data() {
      return {
        compName:"",
        dataList: [],
        param: {
          pageNum: 1,
          pageSize: 999999
        }
      }
    },
    created() {
      this.getDataList()
    },
    methods: {
      closeMod(){
        this.compName = ""
      },
      saveOK(){
        this.getDataList()
        this.closeMod()
      },
      addCarouse(){
        this.compName = "addFile"
      },
      getDataList() {
        this.$http.post('/api/popularimgs/pager', this.param).then(res => {
          if (res.code == 200) {
            this.dataList = res.page.list
          }

        }).catch(err => {
        })
      },
      handleRemove(it){
        var v = this
        this.swal({
          title: "确定删除？",
          type: "warning",
          showCancelButton: true,
          confirmButtonText: "删除",
          cancelButtonText: "取消",
        }).then((isConfirm) => {
          if (isConfirm.value) {
            v.$http.post("/api/popularimgs/remove/"+it.id).then(res=>{
              if(res.code == 200){
                v.$Message.success('删除成功')
              }else {
                v.$Message.error('删除失败')
              }
              v.getDataList()
            }).catch(err=>{
              v.$Message.error('删除失败')
            })
          }
        });
      }
    }
  }
</script>

<style lang="less">
  .carouselBox {
    .ivu-card-body {
      flex: 1;
      overflow: auto;
      position: relative;
      .mask{
        display: none;
        position: absolute;
        left: 16px;
        top: 16px;
        right: 16px;
        bottom: 16px;
        border-radius: 4px;
        background-color: rgba(0,0,0,0.7);
        .ivu-icon-ios-trash-outline{
          color: #fff;
          position: absolute;
          left: 50%;
          top: 50%;
          transform: translate(-50%,-50%);
          font-size: 35px;
          cursor: pointer;
        }
      }
      .ivu-card-body:hover{
        .mask{
          display: block;
        }
      }
    }
  }
</style>
