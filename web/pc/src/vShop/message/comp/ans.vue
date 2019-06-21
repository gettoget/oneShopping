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
  <Modal
    :title="'@'+mess.username+' : ' "
    v-model="modal9"
    closable
    @on-ok="OK"
    @on-cancel="cancel"
    :styles="{top: '20px'}">
    <Input v-model="ans" type="textarea" :rows="4" placeholder="Enter something..." />
  </Modal>
    
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
        this.$emit('saveAns', {id:this.mess.id,ans:this.ans})
      },
      cancel(){
        this.$emit("close")
      }
    }
  }
</script>

<style scoped>

</style>
