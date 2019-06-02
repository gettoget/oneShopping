<template>
  <Card class="carouselBox box_col">
    <div slot="title">
      <pager-tit title="轮播图"></pager-tit>
    </div>
    <div style="width: 1100px;height: 100%;margin: auto;">
      <Row :gutter="32">
        <Col span="6" v-for="(it,index) in dataList" :key="index">
          <Card>
            <img :src="it.imgUrl.substring(0,4)==='http'?
                  it.imgUrl:apis.GETFILEURL+it.imgUrl"
                 style="width: 100%;height: 173px"
                 alt="">
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
    <component :is="compName"></component>
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
        compName:"addFile",
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
      addCarouse(){
        alert(1)
      },
      getDataList() {
        this.$http.post('/api/popularimgs/pager', this.param).then(res => {
          if (res.code == 200) {
            this.dataList = res.page.list
          }

        }).catch(err => {
        })
      }
    }
  }
</script>

<style lang="less">
  .carouselBox {
    .ivu-card-body {
      flex: 1;
      overflow: auto;
    }
  }
</style>
