<style lang="less">
  .vertical-center-modal{
    display: flex;
    align-items: center;
    justify-content: center;

    .ivu-modal{
      top: 0;
    }
  }
</style>
<template>
  <div>
    <Modal
      :title="'@'+mess.username+' : ' "
      v-model="modal9"
      @on-ok="OK"
      @on-cancel="cancel"
    >
      <div style="padding: 10px;font-size: 22px;font-weight: 600">{{mess.content}}</div>
      <Input v-model="ans" type="textarea" :rows="4" placeholder="Enter something..." />
    </Modal>
  </div>
</template>

<script>
  export default {
    props: {
      mess:{}
    },
    data () {
      return {
        modal9: true,
        ans:''
      }
    },
    methods:{
      OK(){
        this.$emit("saveAns",{id:this.mess.id,content:this.ans})
      },
      cancel(){
        this.$emit("close",{})
      }
    }
  }
</script>

<style scoped>

</style>
