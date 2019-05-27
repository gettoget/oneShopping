<template>
  <div>
    <Upload
      ref="upload"
      :action="action"
      :show-upload-list="false"
      :on-success="handleSuccess"
      :on-error="handleError"
      :format="['jpg','jpeg','png']"
      :max-size="2048"
      :on-format-error="handleFormatError"
      :on-exceeded-size="handleMaxSize"
      multiple
      type="drag"
      style="">
        <div style="display: inline-block;text-align: center;
        width: 58px;height:58px;line-height: 58px;">
        <Icon type="ios-cloud-upload" size="40"></Icon>
        </div>
    </Upload>
  </div>
</template>

<script>
  export default {
    name: "index",
    props: {
      upGroup: {
        type: String,
        default: 'cover'// 封面图 cover 内容图 urls 推荐图refUrl
      }
    },
    data() {
      return {
        action: this.apis.UPFILE + this.upGroup,
        defaultList: [
        ],
        imgName: '',
        visible: false,
        uploadList: []
      }
    },
    created() {

    },
    methods: {
      handleSuccess(res, file) {
        // alert("上传成功")
        // console.log(res);
        // console.log(file);
        this.$emit('handleSuccess', res.message)

      },
      handleError(res, file) {
        this.$Message.error('文件上传失败');
        // this.$emit('handleSuccess', 'https://i.loli.net/2017/08/21/599a521472424.jpg')
      },
      handleFormatError(file) {
        this.$Notice.warning({
          title: 'The file format is incorrect',
          desc: 'File format of ' + file.name + ' is incorrect, please select jpg or png.'
        });
      },
      handleMaxSize(file) {
        this.$Notice.warning({
          title: 'Exceeding file size limit',
          desc: 'File  ' + file.name + ' is too large, no more than 2M.'
        });
      },
    }
  }
</script>

<style scoped>

</style>
