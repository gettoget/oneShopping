<template>
  <div>
    <Modal
      v-model="showModal"
      title="物流发货"
      :mask-closable="false"
      @on-visible-change="visible"
    >
      <Form>
        <FormItem lable="物流公司">
          <Select value="1">
            <Option value="1">中通快递</Option>
            <Option value="2">韵达快递</Option>
            <Option value="3">百世汇通</Option>
          </Select>
        </FormItem>
        <FormItem lable="物流编号">
          <Input value="" placeholder="物流编号"/>
        </FormItem>
      </Form>
      <div slot="footer">

      </div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "logisticsMess",
    data() {
      return {
        showModal: true
      }
    },
    methods:{
      close(){
        this.$emit("close")
      },
      visible(val){
        this.close()
      }
    }
  }
</script>

<style lang="less">

</style>
