<template>
  <div class="ShopDrawer">
    <div class="modalMessBox box_col">
      <div class="shopImgBox">
        <div style="width: 240px;margin: auto">
          <swiper :globalOptions="globalOptions">
            <swiper-slide v-for="(it,index) in mess.imgUrls" :key="index">
              <div class="swiperItemBox">
                <img :src="it" alt="">
              </div>
            </swiper-slide>
          </swiper>
        </div>
      </div>
      <div class="closeMod">
        <Icon type="md-close" size="26" @click.native="close"/>
      </div>
      <div class="proZtBoxSty">
        {{mess.proZt | proZt}}
      </div>

      <div class="shopTitBox">
        <div class="box_row colCenter">
          <div class="proPriceSty">
            ￥{{mess.proPrice}}
          </div>
          <div>
            <Tag :color="'red'"
                 v-for="(it,index) in mess.proLx.split(',')"
                 @click.native="tagVal = index">
              {{it | TagVal}}
            </Tag>
          </div>
          <div class="box_row_100" style="text-align: right;font-size: 18px;font-weight: 500;color: orangered">
            {{$t('SJSJ')}}：{{moment(mess.cjsj).format('YYYY-MM-DD')}}
          </div>
        </div>

        <div class="shopTit">
          {{mess.proName}}
        </div>
      </div>
      <div class="">

        <div class="lineBox">
          <div class="lineVal box_row colCenter">
            <div class="box_row_100" style="cursor: pointer">
              <Poptip placement="left" :transfer="false">
                <Icon type="md-menu" size="24"/>
                {{$t('CYRS')}}：{{mess.cyyhs}}
                <div slot="content" class="purchaserListBox">
                  <Table
                    size='small' stripe
                    :height="500"
                    :columns="tableTiT"
                    :data="tableData"></Table>
                </div>
              </Poptip>
            </div>
            <div>
              {{$t('YS')}}:{{(parseInt(mess.proPrice)-parseInt(mess.rePrice))}}{{$t('F')}} / {{$t('SY')}}
              <span style="color: orangered;font-weight: 700">{{mess.rePrice}}</span>{{$t('F')}}
            </div>
          </div>
          <div class="lineback box_row">
            <div class="line"
                 :style="{width:((parseInt(mess.proPrice)-parseInt(mess.rePrice))/parseInt(mess.rePrice))*100+'%'}">
            </div>
          </div>
        </div>

      </div>
      <Card style="width:100%;margin-top: 12px">
        <div slot="title" class="box_row">
          <div>
            <h4>
              <Icon type="ios-film-outline"></Icon>
              {{$t('SQHJZ')}}
            </h4>
          </div>
          <div class="box_row_100" style="text-align: right">
            <h3>{{$t('ZJHM')}}:{{winList.num}}</h3>
          </div>
        </div>
        <div class="box_row colCenter">
          <Avatar v-if="winList.himg"
                  :src="winList.himg.substring(0,4)==='http'?winList.himg:apis.GETFILEURL+winList.himg"/>
          <Avatar v-else :src="defAva"/>
          <div style="margin-left: 22px">
            <div class="winUserItemSty">
              {{$t('HJZ')}}：<span style="color: orangered">{{winList.userName}}</span>
            </div>
            <div class="winUserItemSty">
              {{$t('CYSL')}}：{{winList.zjfs}}
            </div>
            <div class="winUserItemSty">
              {{$t('CYSJ')}}：{{moment(winList.cjsj).format('YYYY-MM-DD')}}
            </div>
          </div>

        </div>
      </Card>

      <div :id="tabBox" class="box_col_auto" style="margin-top: 12px">
        <Table
          size='large' stripe
          :height="tab_H"
          v-if="tab_H>0"
          :columns="tableTiT"
          :data="tableData"></Table>
      </div>
    </div>
    <div class="userListBox" @click="close">
    </div>

  </div>
</template>

<script>
  import 'swiper/dist/css/swiper.css'
  import {swiper, swiperSlide} from 'vue-awesome-swiper'
  import defAva from '@/assets/images/007.png'
  import i18nTabTit from '@/mixins/i18nTabTit'
  export default {
    name: "DrawerMod",
    mixins: [i18nTabTit],
    components: {
      swiper,
      swiperSlide
    },
    props: {
      mess: {
        type: Object,
        default: {}
      }
    },
    data() {
      return {
        defAva,
        showModal: true,
        globalOptions: {
          loop: true,
          slidesPerView: 1,
          centeredSlides: true,
          autoplay: {
            delay: 4000,
            stopOnLastSlide: false,//如果设置为true，当切换到最后一个slide时停止自动切换。
            disableOnInteraction: true,//拖拽后自动轮播停止
            reverseDirection: false,//反向轮播
          }
        },
        winList: {
          // cjsj: "2019-06-05 22:20:13.394",
          // himg: "http://cdnoss.luno.id/cashcash/users/header/2018-09-26/c470e962cd86d5cef9e11b510a684092.png",
          // id: "585956069029707776",
          // num: "10004808",
          // nums: null,
          // proId: "585410180749983744",
          // proName: "惠普（HP）战66 二代",
          // userId: "567293823873449984",
          // userName: "maryono",
          // zjfs: "19",
          // zjlx: "0",
        },
        tab_H: 0,
        tabBox: "tabBox",
        tableTiT: [
          {
            type: "index",
            width: 80,
          },
          {
            title: "头像",
            width: 100,
            i18n:'TX',
            slot: 'action',
            render: (h, p) => {
              return h('Avatar', {
                props: {
                  src: p.row.user.hImg
                }
              })
            }
          },
          {
            title: "用户姓名",
            minWidth: 140,
            i18n:'YHM',
            render: (h, p) => {
              return h('div', p.row.user.userName)
            }
          },
          {
            title: "购买时间",
            i18n:'GMRQ',
            minWidth: 140,
            render: (h, p) => {
              let a = this.moment(p.row.cjsj).format('YYYY-MM-DD HH:mm:ss')
              let b = this.moment(p.row.cjsj).format('HH:mm:ss')
              return h('div', a)
            }
          },
          {
            title: "购买数量",
            width: 120,
            i18n:'SL',
            key: "gmfs"
          }
        ],
        tableData: [],
      }
    },
    filters: {
      proZt: (val) => {
        switch (val) {
          case "1":
            return "售卖中"
            break;
          case "2":
            return "----"
            break;
          case "3":
            return "待开奖"
            break;
          case "4":
            return "已开奖"
            break;
          default:
            return "售卖中"
            break;
        }
      },
      TagVal: (val) => {
        switch (val) {
          case "1":
            return "推荐"
            break;
          case "2":
            return "新品"
            break;
          case "3":
            return "热门"
            break;
          default:
            return "新品"
            break;
        }
      }
    },
    mounted() {
      this.getUserList()
      this.getWinList()
      this.$nextTick(() => {
        try {
          this.tab_H = this.AF.getDom_H(this.tabBox)
        } catch (e) {
        }
      })
      this.getBuyUserList()
    },
    methods: {
      close() {
        this.$emit("close")
      },
      visible(val) {
        setTimeout(() => {
          this.$emit("close")
        }, 200)
      },
      getWinList() {
        this.$http.post("/api/proinfo/getWinRecord", {id: this.mess.id, pageNum: 1, pageSize: 1}).then(res => {
          if (res.success) {
            this.winList = res.page.list[0]
          }
        }).catch(err => {
        })
      },
      getUserList() {
        this.$http.post("/api/order/pager", {
          proId: this.mess.id, pageNum: 1,
          pageSize: this.mess.proPrice
        }).then(res => {

        }).catch(err => {
        })
      },
      getBuyUserList() {
        this.$http.post('/api/proinfo/getUsers', {
          id: this.mess.id,
          pageNum: 1,
          pageSize: this.mess.proPrice
        }).then(res => {
          console.log(res);
          if (res.code == 200) {
            this.tableData = res.page.list
          }
        }).catch(err => {
        })
      }
    }
  }
</script>
<style lang="less">
  @import "./DrawerMod.less";
</style>
