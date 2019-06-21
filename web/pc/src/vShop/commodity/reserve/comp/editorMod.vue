<template>
  <div>
    <Modal
      class-name="vertical-center-modal editorModSty"
      v-model="showModal"
      :footer-hide="true"
      width="700"
      @on-visible-change="visible"
    >
      <div slot="header">
        <pager-tit title="商品维护"></pager-tit>
      </div>
      <editor :itMess="itMess" @upSuccess="upSuccess"></editor>
    </Modal>
  </div>
</template>

<script>
  import editor from '@/vShop/commodity/NewStock/editor'
  export default {
    name: "editorMod",
    props:{
      itMess:{
        type:Object,
        default:{}
      }
    },
    components:{
      editor
    },
    data(){
      return {
        showModal:true
      }
    },
    methods:{
      visible(val){
        this.$emit("closeMod")
      },
      upSuccess(){
        this.$emit("upSuccess")
      },
    }
  }
</script>

<style lang="less">
.editorModSty{
  .ivu-modal-body{
    padding: 0;
  }
}
</style>
