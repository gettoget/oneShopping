<template>
  <div>
    <div>
      商品上架
    </div>
  </div>
</template>

<script>

  export default {
    name: "index",
    components: {
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
