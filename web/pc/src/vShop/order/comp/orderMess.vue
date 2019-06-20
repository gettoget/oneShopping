<template>
  <div>
    <Drawer title="Basic Drawer"
            v-model="showDrawer"
            width="594"
            :closable="false"
            @on-visible-change="visible"
    >
            <!--:mask-closable="false"-->
      <div slot="header" class="box_row rowBetween colCenter">
        <h2>{{$t('ORDERNUMBER')}}:{{orderMess.id}}</h2>
        <div>
          <Button type="primary">{{$t('DDZT')}}</Button>
        </div>
      </div>
      <div class="box_col">

        <div class="box_row">
          <div style="width: 180px;margin-right: 22px">

            <img  :src="orderMess.imgUrl[0].substring(0,4)==='http'?orderMess.imgUrl[0]:apis.GETFILEURL+orderMess.imgUrl[0]"
                 width="100%" alt="">
          </div>
          <div class="box_row_100" style="font-size: 20px;font-weight: 600;color: #495060">
           {{orderMess.proName}}
          </div>
        </div>

        <Card>
          <div class="box_row">
            <div class="box_row_100">
              {{$t('SL')}}：<span style="font-size: 18px;font-weight: 600">X {{orderMess.orderLists.length}}</span>
            </div>
            <div class="box_row_100">
              {{$t('ZF')}}：<span style="font-size: 18px;font-weight: 600">{{orderMess.zfje}}</span>
            </div>
          </div>
        </Card>

        <Card style="width:100%;margin-top: 12px">
          <div slot="title" class="box_row">
            <div>
              <h4>
                <Icon type="ios-film-outline"></Icon>
                {{$t('GMYH')}}
              </h4>
            </div>
            <div class="box_row_100" style="text-align: right">
              {{$t('GMSJ')}}:{{moment(orderMess.cjsj).format('YYYY-MM-DD HH:mm:ss')}}
              <!--{{moment(mess.cjsj).format('YYYY-MM-DD HH:mm:ss')}}-->
            </div>
          </div>
          <div class="box_row colCenter">
            <!--<Avatar v-if="winList.himg"-->
                    <!--:src="winList.himg.substring(0,4)==='http'?winList.himg:apis.GETFILEURL+winList.himg"/>-->
            <Avatar src="http://cdnoss.luno.id/cashcash/users/header/2018-11-09/358829b0e01be54345271f77b990c9ee.png"/>
            <div class="box_row_100" style="margin-left: 22px">
              <div class="box_row">
                <div class="box_row_100">
                  <Icon type="ios-contact"  size="26" color="#fe5722"/>
                  <span style="color: orangered">{{orderMess.userName}}</span>
                </div>
                <div class="box_row_100">
                  <Icon type="ios-call" size="26" color="#ff9776"/>
                  <span style="color: orangered">
                    {{orderMess.phone.substring(0,orderMess.phone.length-8)}}
                    ****
                    {{orderMess.phone.substring(orderMess.phone.length-4,orderMess.phone.length)}}
                  </span>
                </div>
              </div>
              <div style="margin-top: 12px">
                <Icon type="md-pin" size="26" color="#ff855f"/>
                {{orderMess.addr.address}}
              </div>
            </div>

          </div>
        </Card>

        <div class="box_col_auto" style="margin-top: 12px">
          <Card>
            <div slot="title" class="box_row">
              {{$t('GMHM')}}
            </div>
            <div>
              <Tag color="cyan" v-for="(it,index) in orderMess.orderLists" :key="index">{{it.num}}</Tag>
            </div>
          </Card>
        </div>
      </div>
    </Drawer>
  </div>
</template>

<script>
  export default {
    name: "orderMess",
    components: {},
    props:{
      orderMess:{}
    },
    data(){
      return{
        showDrawer:false
      }
    },
    created(){
      setTimeout(()=>{
        this.showDrawer = true
      },200)
    },
    methods:{
      close(){
        this.$emit('close')
      },
      visible(val){
        if(val == false){
          setTimeout(()=>{
            this.close()
          },200)
        }
      }
    }
  }
</script>

<style scoped>

</style>
