<template>
  <div class="onePagerSty">
    <Page :total="total" :page-size="size"
          :page-size-opts="opts"
          show-total show-elevator show-sizer
          @on-change="pageNum"
          @on-page-size-change="pageSize"
    />
  </div>
</template>

<script>
  export default {
    name: "onePage",
    props:{
      total:{
        type:Number,
        default:0
      },
      size:{
        type:Number,
        default:1
      },
      opts:{
        type:Array,
        default:()=>{
          return [5,15,20]
        }
      }
    },
    data(){
      return{
        param:{
          pageNum:0,
          pageSize:this.size
        }
      }
    },
    created(){
    },
    methods:{
      pageNum(val){
        this.param.pageNum = val
        this.$emit('chPager',this.param)
      },
      pageSize(size){
        this.param.pageSize = size
        console.log(size);
      }
    }
  }
</script>
<style lang="less">
  .onePagerSty{
    .ivu-page-item{
      border-radius: 32px;
      a{
        color: #a7a8ab;
        font-weight: 600;
      }
    }
    .ivu-page-item-active{
      background-color: #fe5722;
      a{
        color: #fff;
        font-weight: 700;
      }
    }
  }
</style>
