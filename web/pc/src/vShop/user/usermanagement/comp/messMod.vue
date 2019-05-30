<template>
  <div>
    <Modal
      v-model="showModal"
      :mask-closable="false"
      :footer-hide="true"
      @on-visible-change="visible">
      <div slot="header" class="box_row colBottom">
        <Icon type="md-person" size="22"/>
        <div style="font-weight: 600;font-size: 20px">
          {{itMess.userName}}
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "messMod",
    props: {
      itMess: {
        type: Object,
        default: {}
      }
    },
    data() {
      return {
        showModal: true
      }
    },
    methods: {
      visible(val) {
        console.log(val);
        this.$emit("closeMod")
      }
    }
  }
</script>
