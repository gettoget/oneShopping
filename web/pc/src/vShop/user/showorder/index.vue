<template>
  <div class="box_col">
    <pager-tit title="晒单审核"></pager-tit>
    <div class="box_col_auto">
      <div v-if="dataList.length >0" v-for="(it,index) in dataList" :key="index">
        <shaidan :mess="it"></shaidan>
      </div>
    </div>
    <div class="pagerBoxSty boxMar_T box_row rowRight">
      <one-page :total="total" :size="param.pageSize"
                :opts="[12,24]"
                @chPager="chPager"></one-page>
    </div>
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
        dataList: [],
        total:0,
        param: {
          userName:"",
          phone:"",
          pageNum: 1,
          pageSize: 12
        }
      }
    },
    created(){
      this.getDataList()
    },
    methods: {
      chPager(p) {
        this.param.pageNum = p.pageNum
        this.param.pageSize = p.pageSize
        this.getDataList()
      },
      getDataList(){
        this.$http.post("/api/proeval/pager").then(res=>{
          if(res.code == 200){
            this.dataList = res.page.list
            this.total = res.page.total
          }
        }).catch(err=>{})
      }
    }
  }
</script>
