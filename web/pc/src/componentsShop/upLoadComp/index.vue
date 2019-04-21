<template>
  <div>
    <!--<Upload-->
    <!--ref="upload"-->
    <!--:action="action"-->
    <!--:show-upload-list="false"-->
    <!--:default-file-list="defaultList"-->
    <!--:on-success="handleSuccess"-->
    <!--:on-error="handleError"-->
    <!--:format="['jpg','jpeg','png']"-->
    <!--:max-size="2048"-->
    <!--:on-format-error="handleFormatError"-->
    <!--:on-exceeded-size="handleMaxSize"-->
    <!--multiple-->
    <!--type="drag"-->
    <!--style="">-->
    <div @click="handleError">
      <slot>
          <div style="display: inline-block;text-align: center;
          width: 58px;height:58px;line-height: 58px;">
            <img v-if="FlieUrl" :src="FlieUrl" width="100%" height="100%" alt="">
            <Icon v-else type="ios-cloud-upload" size="40" style="color: #3399ff"></Icon>
          </div>
      </slot>
    </div>
    <!--</Upload>-->
  </div>
</template>

<script>
  export default {
    name: "index",
    props: {
      FlieUrl: {
        type: String,
        default: ''
      },
      VOICE: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        // action:'http://mt.xxpt123.com:81/upload',
        action: '//jsonplaceholder.typicode.com/posts/',
        defaultList: [
          // {
          //   'name': 'a42bdcc1178e62b4694c830f028db5c0',
          //   'url': 'https://o5wwk8baw.qnssl.com/a42bdcc1178e62b4694c830f028db5c0/avatar'
          // },
          // {
          //   'name': 'bc7521e033abdd1e92222d733590f104',
          //   'url': 'https://o5wwk8baw.qnssl.com/bc7521e033abdd1e92222d733590f104/avatar'
          // }
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
        alert("上传成功")
        console.log(res);
        // res.message //返回URL地址
        temp / ce3c0ca016a44795bdce23141f994cce.jpg
        console.log(file);
      },
      handleError() {
        alert("上传失败")
        // console.log(res);
        // console.log(file);
        this.$emit('handleSuccess', 'http://mt.xxpt123.com:81/img/temp/ce3c0ca016a44795bdce23141f994cce.jpg')
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
