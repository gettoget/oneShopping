<style lang="less">
  @import "./index.less";
</style>
<template>
  <Card class="UpStockListSty box_col">
    <div slot="title" class="box_row">
      <div class="box_row_100">
        <pager-tit :title="$t('SPLL')"></pager-tit>
      </div>
      <Select v-model="param.proZt" @on-change="proZtChange" style="width:200px;margin-right: 20px">
        <Option value="0">{{$t('QB')}}</Option>
        <Option value="1">{{$t('SMZ')}}</Option>
        <Option value="3">{{$t('DKJ')}}</Option>
        <Option value="4">{{$t('YKJ')}}</Option>
        <Option value="5">{{$t('UNS')}}</Option>
      </Select>

      <DatePicker v-if="param.proZt==4 || param.proZt==5" v-model="timeVal" type="daterange" placement="bottom-end"
                  placeholder="Waktu lotere"
                  @on-change="getDate"
                  style="width: 200px;margin-right: 20px"></DatePicker>
      <!--<RadioGroup v-model="param.proZt" type="button"-->
                  <!--@on-change="getDataList()" style="margin-right: 20px">-->
        <!--<Radio label="0">{{$t('QB')}}</Radio>-->
        <!--<Radio label="1">{{$t('SMZ')}}</Radio>-->
        <!--<Radio label="3">{{$t('DKJ')}}</Radio>-->
        <!--<Radio label="4">{{$t('YKJ')}}</Radio>-->
        <!--<Radio label="5">-->
          <!--{{$t('UNS')}}-->
        <!--</Radio>-->
      <!--</RadioGroup>-->

      <div style="margin-right: 20px">
        <Button :type="it.bol?'primary':'default'"
                v-for="(it,index) in tegList"
                @click.native="tagEvent(it,index)">
          <!--{{it.text}}-->

          {{$t(it.i18n)}}
        </Button>
      </div>
      <Input v-model="param.proNameLike" :placeholder="$t('SPMC')"
             style="width: 200px;margin-right: 20px" @on-clear="getDataList()"
             @on-change="getDataList()" clearable/>
      <Input v-model="param.proPriceGte" type="number" :number="true"
             style="width: 120px" @on-change="getDataList()">
      <span slot="prepend">{{$t('JG')}}</span>
      </Input>
      <div style="padding: 10px 6px">
        ——
      </div>
      <Input v-model="param.proPriceLte" type="number" :number="true"
             style="width: 90px;margin-right: 20px" @on-change="getDataList()">
      </Input>

      <Button type="primary" icon="ios-search" style=";margin-right: 20px"
              @click="proId = '' ,getDataList()"></Button>
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
                     @labSave="getDataList"
                     @getMess="getMess(it)"
                     @eavClose="getDataList"
          ></shop-card>
        </div>
      </div>
      <div class="pagerBoxSty boxMar_T box_row rowRight">
        <one-page :total="total" :size="param.pageSize"
                  :opts="[4,8,12,16]"
                  @chPager="chPager"></one-page>
      </div>
    </div>
    <component :is="compName"
               :mess="itmess" @close="compName=''"></component>
  </Card>
</template>

<script>
  import shopCard from './comp/shopCard'
  import dr from './comp/DrawerMod'
  import i18nTabTit from '@/mixins/i18nTabTit'

  export default {
    name: "index",
    components: {
      shopCard, dr
    },
    mixins: [i18nTabTit],
    data() {
      return {
        compName: "",
        datamess: true,
        itmess: {},
        tegList: [
          {
            bol: false,
            text: "推荐",
            i18n: 'TJ',
            key: "1"
          },
          {
            bol: false,
            text: "上新",
            i18n: 'SX',
            key: "2"
          },
          {
            bol: false,
            text: "热门",
            i18n: 'RM',
            key: "3"
          },
        ],
        shopList: [],
        total: 0,
        timeVal:[],
        param: {
          proPriceGte: null,
          proPriceLte: null,
          kjsjGte:"",
          kjsjLte:"",
          proZt: "0",
          proLx: "",
          pageNum: 1,
          pageSize: 8
        },
        proId:''
      }
    },
    created() {
        let id = this.$route.query.id;
        console.log(' id ---------' , id);
        if(id){
            this.proId = id
        }
        this.getDataList()
    },
    methods: {
      proZtChange(val){
        if(val==0 ||val==1 ||val==3){
          this.param.kjsjGte = ""
          this.param.kjsjLte = ""
        }
        this.proId = ''
        this.getDataList()
      },
      getDate(t){
        console.log(t);
        if(t[0]&&t[1]){
          this.param.kjsjGte = t[0]+" 00:00:00"
          this.param.kjsjLte = t[0]+" 23:59:59"
        }else{
          this.param.kjsjGte = ""
          this.param.kjsjLte = ""
        }
        this.proId = ''
        this.getDataList()
      },
      reset() {
        this.param = {
          proPriceGte: null,
          proPriceLte: null,
          proZt: "0",
          proLx: "",
          pageNum: 1,
          pageSize: 8
        }
        this.proId = ''
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
              this.proId = ''
            this.getDataList()
          }
        })
      },
      chPager(p) {
        this.param.pageNum = p.pageNum
        this.param.pageSize = p.pageSize
          this.proId = ''
        this.getDataList()
      },
      getDataList() {
        let a = JSON.parse(JSON.stringify(this.param))
        if (a.proZt == "0") {
          a.proZt = ""
        } else if (a.proZt == "5") {
          a.proZt = 4
          a.bz2 = 0
        }
        if(this.proId){
            a.id = this.proId
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
