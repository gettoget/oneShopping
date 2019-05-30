<template>
  <div class="box_col">
    <div class="box_row">
      <pager-tit title="用户管理"></pager-tit>
      <div class="box_row_100">
        <div class="box_row colCenter rowRight pageFindSty" style="border: none">
          <div>
            <Input v-model="param.userName" @on-change="param.pageNum = 1,getDataList()"
                   clearable placeholder="请输入用户名" style="width: 200px"/>
          </div>

          <div>
            <Input v-model="param.phone" @on-change="param.pageNum = 1,getDataList()"
              placeholder="请输入手机号码" style="width: 200px;margin: 0 12px"/>
          </div>
          <Button type="primary" @click="param.pageNum = 1,getDataList()">
            <Icon type="md-search"></Icon>
            <!--查询-->
          </Button>
        </div>
      </div>
    </div>
    <div class="box_col_auto">
      <div class="box_row_list" :style="{width: 356*4+'px',margin:'0 auto'}">
        <div v-for="(it,index) in tableData" :key="index" @click="showMess(it)"
             style="padding: 18px;cursor: pointer">
          <user-card :inVal="index" :mess="it"></user-card>
        </div>
      </div>
    </div>
    <div class="pagerBoxSty boxMar_T box_row rowRight">
      <one-page :total="total" :size="param.pageSize"
                :opts="[12,24]"
                @chPager="chPager"></one-page>
    </div>
    <component :is="compName"
               :itMess="itMess"
               @closeMod="closeMod"></component>
  </div>
</template>

<script>
  import userCard from './comp/userCard'
  import messMod from './comp/messMod'
  export default {
    name: "index",
    components: {
      userCard,messMod
    },
    data() {
      return {
        compName:"",
        tableData: [],
        itMess:{},
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
    methods:{
      showMess(it){
        this.itMess = it
        this.compName = "messMod"
      },
      closeMod(){
        this.compName = ""
        this.itMess = {}
      },
      chPager(p) {
        this.param.pageNum = p.pageNum
        this.param.pageSize = p.pageSize
        this.getDataList()
      },
      getDataList(){
        this.$http.post("/api/user/pager",this.param).then(res=>{
          if(res.code == 200){
            this.tableData = res.page.list
            this.total = res.page.total
          }
        }).catch(err=>{
        })
      }
    }
  }
</script>
