<template>
  <div>
    <Modal
      v-model="showModal"
      :title="$t('WLFH')"
      :mask-closable="false"
      @on-visible-change="visible"
    >
      <Form>
        <FormItem lable="物流公司">
          <Select value="1">
            <Option value="1">{{$t('ZTKD')}}</Option>
            <Option value="2">{{$t('YDKD')}}</Option>
            <Option value="3">{{$t('BSHT')}}</Option>
          </Select>
        </FormItem>
        <FormItem :lable="$t('WLBH')">
          <Input value="" :placeholder="$t('WLBH')"/>
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
