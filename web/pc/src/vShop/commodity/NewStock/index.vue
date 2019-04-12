<template>
  <div>
    <div>
      商品上架
    </div>
    <cropper
      :src="exampleImageSrc"
      crop-button-text="确认提交"
      @on-crop="handleCroped"
    ></cropper>
  </div>
</template>

<script>
  import Cropper from '@/components/cropper'

  export default {
    name: "index",
    components: {
      Cropper
    },
    data () {
      return {
        exampleImageSrc: ''
      }
    },
    methods: {
      handleCroped (blob) {
        // const formData = new FormData()
        // formData.append('croppedImg', blob)
        // formData.append('croppedImg', blob)
        console.log(blob);
        // uploadImg(formData).then(() => {
        //   this.$Message.success('Upload success~')
        // })
      }
    }
  }
</script>

<style scoped>

</style>
