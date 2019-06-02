<template>
  <div>
    <Modal
      v-model="showModal"
      class-name="vertical-center-modal"
      width="900"
    >
      <div slot="header">
        <pager-tit title="添加轮播图"></pager-tit>
      </div>
      <div class="box_row">
        <div style="width: 200px">
          upFile
        </div>
        <div class="box_row_100">
          <div class="box_col" style="height: 600px;">
            <div>
              <Input v-model="param.proNameLike" clearable
                     placeholder="请输入您想绑定的上品" />
            </div>

            <div class="box_col_auto shopListRadio">
              <RadioGroup v-model="vertical" vertical>
                <Radio :label="it.id" v-for="(it,index) in ShopList">
                  <Avatar shape="square" :src="it.coverUrl.substring(0,4)==='http'?it.coverUrl:apis.GETFILEURL+it.coverUrl" />
                  <div class="radioMess">
                    {{it.proName}}
                  </div>
                </Radio>
              </RadioGroup>
            </div>

          </div>
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
  import upFileImg from '_p/upLoadComp/index.vue'

  export default {
    name: "addMod",
    components: {
      upFileImg
    },
    data() {
      return {
        showModal: true,
        ShopList:[],
        vertical:"",
        param: {
          proNameLike:"",
          pageNum: 1,
          pageSize: 999999999
        }
      }
    },
    watch:{
      vertical:function (n,o) {
        console.log(n);
      },
      "param.proNameLike":function (n,o) {
        console.log(n);
        if(n==""){
          this.ShopList = []
        }else {
          this.getShopList()
        }
      }
    },
    created(){

    },
    methods:{
      getShopList(){
        this.$http.post("/api/proinfo/pager",this.param).then(res=>{
          if(res.code == 200){
            this.ShopList = res.page.list
          }
        }).catch(err=>{})
      }
    }
  }
</script>

<style lang="less">
.shopListRadio{
  .ivu-radio-wrapper{
    height: 40px;
    margin: 8px 0;
    display: flex;
    flex-direction: row;
    align-items: center;
    cursor: pointer;
    .radioMess{
      width: 600px;
      overflow:hidden;
      text-overflow:ellipsis;
      white-space:nowrap
    }
  }
}
</style>
