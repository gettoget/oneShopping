<template>
      <div>

        商品管理
        <shop-card v-for="(it,index) in 10"></shop-card>

      </div>
</template>

<script>
  import shopCard from "./comp/shopCard"

    export default {

        name: "index",
        components:{
          shopCard

        }
    }
</script>

<style scoped>

</style>
