<template>
  <div class="box_col">
    <div class="box_row">
      <pager-tit :title="$t('YHGL')"></pager-tit>
      <div class="box_row_100">
        <div class="box_row colCenter rowRight pageFindSty" style="border: none">
          <div>
            <Select v-model="param.reNum" style="width: 200px;margin-right: 20px" @on-change="param.pageNum = 1 , getDataList()">
              <Option v-for="item in reList" :value="item.val" :key="item.val">
                {{item.key}}
              </Option>
            </Select>
          </div>
          <div>
            <Input v-model="param.userNameLike" @on-change="param.pageNum = 1,getDataList()"
                   clearable :placeholder="$t('YHM')" style="width: 200px"/>
          </div>

          <div>
            <Input v-model="param.Like" @on-change="param.pageNum = 1,getDataList()"
                   :placeholder="$t('YHDH')" style="width: 200px;margin: 0 12px"/>
          </div>
          <Button type="primary" @click="param.pageNum = 1,getDataList()">
            <Icon type="md-search"></Icon>
            <!--查询-->
          </Button>
        </div>
      </div>
    </div>
    <div class="box_col_auto">
      <div class="box_row_list" :style="{width:'1460px',margin:'0 auto'}">
        <div v-for="(it,index) in tableData" :key="index"
             style="padding: 18px">
          <user-card :inVal="index" :mess="it"
                     @GoMess="()=>{showMess(it)}"
          ></user-card>
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
      userCard, messMod
    },
    data() {
      return {
          reList:[
              {
                key: '一次',
                val: '1'
              },
              {
                  key:'两次',
                  val: '2'
              },
              {
                  key: '两次以上',
                  val: '3'
              }
          ],
        compName: "",
        tableData: [],
        itMess: {},
        total: 0,
        param: {
          userNameLike: "",
          phoneLike: "",
          source:"0",
          pageNum: 1,
          pageSize: 12,
          reNum: ''
        }
      }
    },
    created() {
      this.getDataList()
    },
    methods: {
      showMess(it) {
        this.itMess = it
        this.compName = "messMod"
      },
      closeMod() {
        this.compName = ""
        this.itMess = {}
      },
      chPager(p) {
        this.param.pageNum = p.pageNum
        this.param.pageSize = p.pageSize
        this.getDataList()
      },
      getDataList() {
        this.$http.post("/api/user/pager", this.param).then(res => {
          if (res.code == 200) {
            this.tableData = res.page.list
            this.total = res.page.total
          }
        }).catch(err => {
        })
      }
    }
  }
</script>
