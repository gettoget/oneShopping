<style lang="less">
  @import "./index.less";
</style>
<template>
  <Card class="UpStockListSty box_col">
    <div slot="title" class="box_row">
      <div class="box_row_100">
        <pager-tit title="商品浏览"></pager-tit>
      </div>
      <RadioGroup v-model="param.proZt" type="button"
                  @on-change="getDataList()" style="margin-right: 20px">
        <Radio label="0">全部</Radio>
        <Radio label="1">售卖中</Radio>
        <Radio label="3">待开奖</Radio>
        <Radio label="4">已开奖</Radio>
      </RadioGroup>
      <div style="margin-right: 20px">
        <Button :type="it.bol?'primary':'default'"
                v-for="(it,index) in tegList"
                @click.native="tagEvent(it,index)">
          {{it.text}}
        </Button>
      </div>
      <Input v-model="param.proNameLike" placeholder="商品名称"
             style="width: 200px;margin-right: 20px" @on-clear="getDataList()"
             @on-change="getDataList()" clearable/>
      <Input v-model="param.proPriceGte" type="number" :number="true"
             style="width: 120px" @on-change="getDataList()">
      <span slot="prepend">价格</span>
      </Input>
      <div style="padding: 10px 6px">
        ——
      </div>
      <Input v-model="param.proPriceLte" type="number" :number="true"
             style="width: 90px;margin-right: 20px" @on-change="getDataList()">
      </Input>

      <Button type="primary" icon="ios-search" style=";margin-right: 20px"
              @click="getDataList()"></Button>
      <Button type="primary" icon="md-refresh"
              @click="reset"></Button>

    </div>
    <!--图  名字 参与分数 剩余分数 状态（售卖中 开奖中  已开奖） 详情-->
    <div class="box_col">
      <div class="box_col_auto">
        <div class="box_row_list" style="width: 1328px;margin: auto">
          <!--:mess="JSON.parse(JSON.stringify(it))"-->
          <shop-card v-if="datamess" :inVal="index+((param.pageNum-1)*param.pageSize)" :mess="it"
                     v-for="(it,index) in shopList" :key="index+((param.pageNum-1)*param.pageSize)"
                     @labSave="getDataList" @getMess="getMess(it)"></shop-card>
        </div>
      </div>
      <div class="pagerBoxSty boxMar_T box_row rowRight">
        <one-page :total="total" :size="param.pageSize"
                  :opts="[4,8,12,16]"
                  @chPager="chPager"></one-page>
      </div>
    </div>
    <component :is="compName" :mess="itmess" @close="compName=''"></component>
  </Card>
</template>

<script>
  import shopCard from './comp/shopCard'
  import dr from './comp/DrawerMod'

  export default {
    name: "index",
    components: {
      shopCard, dr
    },
    data() {
      return {
        compName: "",
        datamess:true,
        itmess: {},
        tegList: [
          {
            bol: false,
            text: "推荐",
            key: "1"
          },
          {
            bol: false,
            text: "上新",
            key: "2"
          },
          {
            bol: false,
            text: "热门",
            key: "3"
          },
        ],
        shopList: [],
        total: 0,
        param: {
          proPriceGte: null,
          proPriceLte: null,
          proZt: "0",
          proLx: "",
          pageNum: 1,
          pageSize:8
        }

      }
    },
    created() {
      this.getDataList()
    },
    methods: {
      reset() {
        this.param = {
          proPriceGte: null,
          proPriceLte: null,
          proZt: "0",
          proLx: "",
          pageNum: 1,
          pageSize: 8
        }
        this.getDataList()
      },
      tagEvent(it, index) {
        it.bol = !it.bol
        let b = []
        this.tegList.forEach((it, index) => {
          if (it.bol) {
            b.push(it.key)
          }
          if (index == this.tegList.length - 1) {
            this.param.proLx = b.join(',')
            this.getDataList()
          }
        })
      },
      chPager(p) {
        this.param.pageNum = p.pageNum
        this.param.pageSize = p.pageSize
        this.getDataList()
      },
      getDataList() {
        let a = JSON.parse(JSON.stringify(this.param))
        if (a.proZt == "0") {
          a.proZt = ""
        }
        this.datamess = false
        this.$http.post("/api/proinfo/pager", a).then(res => {
          if (res.code == 200) {
            this.shopList = res.page.list
            this.total = res.page.total
            this.datamess = true
          }
        }).catch(err => {
        })
      },
      getMess(it) {
        this.compName = "dr"
        this.itmess = it
      }
    }
  }
</script>
