<template>
  <div>
    <Modal
      v-model="showModal"
      class-name="vertical-center-modal upCarouseModal"
      width="918"
      @on-visible-change="visible"
    >
      <div slot="header">
        <pager-tit :title="$t('TJLBT')"></pager-tit>
      </div>
      <div class="box_row">
        <div style="width: 200px;margin-right: 18px">
          <div>
            <div span="12" class="contentItemSty" v-if="formData.imgUrl">
              <img :src="apis.GETFILEURL+formData.imgUrl" alt="">
              <div class="ingMask">
                <Icon type="ios-trash" size="60" color="#fff" @click.native="formData.imgUrl = ''"/>
              </div>
            </div>
            <div class="" v-else>
              <Tooltip :content="$t('DJSCTP')" placement="top" :transfer="true">
                <up-file-img upGroup="cover" @handleSuccess="handleSuccess">
                  <Button type="text" style="height: 120px;width: 200px">
                    <Icon type="md-cloud-upload" size="80"/>
                  </Button>
                </up-file-img>
              </Tooltip>
            </div>
          </div>
          <div>
            <Select v-model="formData.imgLx" style="width:200px">
              <Option value="1">{{$t('SPTJ')}}</Option>
              <Option value="2">{{$t('HDYM')}}</Option>
              <Option value="3">{{$t('ADDNQD')}}
                <!--{{$t('HDYM')}}-->
              </Option>
            </Select>
          </div>
        </div>
        <div class="box_row_100">
          <div class="box_col" style="max-height: 600px;">
            <div v-if="formData.imgLx === '3'">
              <Input v-model="formData.bz1" type="textarea"
                     :autosize="{minRows: 3,maxRows: 3}" :placeholder="$t('LBBZ1')" />
            </div>
            <div v-else>
              <div>
                <Input v-model="param.proNameLike" clearable
                       :placeholder="$t('SRBDSP')"/>
              </div>

              <div class="box_col_auto shopListRadio">
                <RadioGroup v-model="formData.proId" vertical>
                  <Radio :label="it.id" v-for="(it,index) in ShopList">
                    <Avatar shape="square"
                            :src="it.coverUrl.substring(0,4)==='http'?it.coverUrl:apis.GETFILEURL+it.coverUrl"/>
                    <div class="radioMess">
                      {{it.proName}}
                    </div>
                  </Radio>
                </RadioGroup>
              </div>
            </div>

          </div>
        </div>
      </div>

      <div slot="footer" class="box_row rowRight">
        <Button type="primary" @click="save">save</Button>
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
        ShopList: [],
        formData: {
          imgUrl: "",
          proId: "",
          zt: "0",
          imgLx:"1"
        },
        param: {
          proNameLike: "",
          pageNum: 1,
          pageSize: 999999999
        }
      }
    },
    watch: {
      vertical: function (n, o) {
        console.log(n);
      },
      "param.proNameLike": function (n, o) {
        console.log(n);
        if (n == "") {
          this.ShopList = []
        } else {
          this.getShopList()
        }
      }
    },
    created() {

    },
    methods: {
      save(){
        console.log(this.formData);
        if(this.formData.imgUrl == ""){
          this.$Message.warning("请上传商品图片！！！")
        }else if(this.formData.proId == ""){
          this.$Message.warning("请绑定商品！！！")
        }else {
          this.$http.post("/api/popularimgs/save",this.formData).then(res=>{
            if(res.code == 200){
              this.$emit("saveOK")
            }
          }).catch(err=>{})
        }
      },
      handleSuccess(url) {
        this.formData.imgUrl = url
      },
      visible(val) {
        this.$emit('closeMod')
      },
      getShopList() {
        this.$http.post("/api/proinfo/pager", this.param).then(res => {
          if (res.code == 200) {
            this.ShopList = res.page.list
          }
        }).catch(err => {
        })
      }
    }
  }
</script>

<style lang="less">
  .upCarouseModal {
    .contentItemSty {
      position: relative;
      img {
        width: 100%;
      }
      .ingMask {
        display: none;
        position: absolute;
        left: 0;
        top: 0;
        right: 0;
        bottom: 0;
        z-index: 100;
        background-color: rgba(0, 0, 0, 0.5);
        i {
          position: absolute;
          top: 50%;
          left: 50%;
          transform: translate(-50%, -50%);
          cursor: pointer;
        }
      }
    }
    .contentItemSty:hover {
      .ingMask {
        display: block;
      }
    }
  }

  .shopListRadio {
    .ivu-radio-wrapper {
      height: 40px;
      margin: 8px 0;
      display: flex;
      flex-direction: row;
      align-items: center;
      cursor: pointer;
      .radioMess {
        width: 600px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap
      }
    }
  }
</style>
