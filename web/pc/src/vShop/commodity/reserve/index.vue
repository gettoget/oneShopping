<style lang="less">
  @import "./index.less";
</style>
<template>
  <Card class="KcPageBox box_col">
    <div slot="title" class="box_row">
      <div class="box_row_100">
        <pager-tit title="库存"></pager-tit>
      </div>
      <Input v-model="param.proNameLike" placeholder="商品名称"
             style="width: 200px;margin-right: 20px"
             @on-clear="getDataList()"
             @on-change="getDataList()" clearable/>
      <Input v-model="param.proPriceGte" type="number" :number="true"
             style="width: 200px" @on-change="getDataList()">
      <span slot="prepend">价格</span>
      </Input>
      <Input v-model="param.proPriceLte" type="number" :number="true"
             style="width: 200px;margin-right: 20px" @on-change="getDataList()">
      </Input>
      <Button type="primary" icon="ios-search" style=";margin-right: 20px"
              @click="getDataList()"></Button>
      <Button type="primary" icon="md-refresh"
              @click="reset"></Button>
    </div>

    <div class="box_col">
      <div class="box_col_auto">
        <div class="box_row_list" style="width: 1186px;margin: auto">
          <reserve-card :inVal="index" :mess="it"
                        v-for="(it,index) in shopList" :key="index"
                        @upSuccess="getDataList"
                        @edit="()=>{edit(it)}"
          ></reserve-card>
        </div>
      </div>

      <div class="pagerBoxSty boxMar_T box_row rowRight">
        <one-page :total="total" :size="param.pageSize"
                  :opts="[4,8,12,16]"
                  @chPager="chPager"></one-page>
      </div>
    </div>

    <component :is="compName"
               :itMess="itMess"
               @closeMod="closeMod"
               @upSuccess="upSuccess"
    ></component>
  </Card>
</template>

<script>
  import reserveCard from './comp/reserveCard'
  import editMod from './comp/editorMod'

  export default {
    name: "index",
    components: {
      reserveCard, editMod
    },
    data() {
      return {
        compName: "",
        itMess: {},
        shopList: [],
        total: 0,
        param: {
          proNameLike: "",
          proPriceGte: null,
          proPriceLte: null,
          pageNum: 1,
          pageSize: 8
        }

      }
    },
    created() {
      this.getDataList()
    },
    methods: {
      reset() {
        this.param.proNameLike = ""
        this.param.proPriceGte = null
        this.param.proPriceLte = null
        this.getDataList()
      },
      closeMod() {
        this.compName = ""
        this.itMess = {}
      },
      edit(it) {
        this.itMess = it
        this.compName = "editMod"
      },
      upSuccess() {
        this.closeMod()
        this.getDataList()
      },
      chPager(p) {
        this.param.pageNum = p.pageNum
        this.param.pageSize = p.pageSize
        this.getDataList()
      },
      getDataList() {
        this.$http.post("/api/probaseinfo/pager", this.param).then(res => {
          console.log(res);
          if (res.code == 200) {
            this.shopList = res.page.list
            this.total = res.page.total
          }
        }).catch(err => {
        })
      }
    }
  }
</script>

<!--<style lang="less">-->
<!--.reserveCardBox{-->
<!--width: 1184px;-->
<!--background-color: #fff;-->
<!--margin:0 auto;-->
<!--}-->
<!--</style>-->
