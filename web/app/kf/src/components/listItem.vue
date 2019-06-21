<template>
    <div style="padding: 10px;background: #fff;border-bottom: 1px solid #eeeeee">
        <van-row>
            <van-col span="18">
                <van-icon name="clock" size="16" color="green"/>
                <span style="color: #999999;margin-left: 10px">{{data.cjsj}}</span>
            </van-col>
            <van-col span="6" style="text-align: right;padding-right: 10px">
            </van-col>
        </van-row>
        <van-icon name="question" size="16" color="red"/>
        <span style="word-wrap:break-word"><b> : {{data.que}}</b></span>
        <br>
        <van-icon v-if="data.ans != null" name="service" size="16" color="#20B2AA"/>
        <span v-if="data.ans != null" style="word-wrap:break-word"><span> : {{data.ans}}</span></span>
    </div>
</template>

<script>
    export default {
        name: "listItem",
        props:{
            data:{
                type:Object,
                default:function(){return {}}
            }
        }
    }
</script>

<style scoped>

</style>