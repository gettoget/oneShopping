<style lang="less">
  @import "./index.less";
</style>
<template>
  <Card class="KcPageBox box_col">
    <div slot="title">
      <pager-tit title="库存"></pager-tit>
    </div>

    <div class="box_col">
      <div class="box_col_auto">
        <div class="box_row_list" style="width: 1186px;margin: auto">
          <reserve-card  :inVal="index" :mess="it"
                         v-for="(it,index) in shopList" :key="index"></reserve-card>
        </div>
      </div>

      <div class="pagerBoxSty boxMar_T box_row rowRight">
        <one-page :total="total" :size="param.pageSize"
                  :opts="[4,8,12,16]"
                  @chPager="chPager"></one-page>
      </div>
    </div>
  </Card>
</template>

<script>
  import reserveCard from './comp/reserveCard'

  export default {
    name: "index",
    components: {
      reserveCard
    },
    data() {
      return {
        shopList: [],
        total: 0,
        param: {
          pageNum: 1,
          pageSize: 8
        }

      }
    },
    created() {
      this.getDataList()
    },
    methods: {
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
