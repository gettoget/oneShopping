<template>
  <div>
    <pager-tit title="晒单审核"></pager-tit>
    <Row :gutter="16">
      <Col :span="4" :xxl="4" :xl="6" :lg="8" :md="12" :sm="24" :xs="24"
           v-for="(it,index) in dataList" :key="index">
        <shaidan :mess="it"></shaidan>
      </Col>
    </Row>
  </div>
</template>

<script>
  import shaidan from './comp/shaidan'

  export default {
    name: "index",
    components: {
      shaidan
    },
    data() {
      return {
        value2: 0,
        dataList: []
      }
    },
    created(){
      this.getDataList()
    },
    methods: {
      getDataList(){
        this.$http.post("/api/proeval/pager").then(res=>{
          if(res.code == 200){
            console.log(res);
            this.dataList = res.page.list
          }
        }).catch(err=>{})
      }
    }
  }
</script>
