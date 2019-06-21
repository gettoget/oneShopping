<template>
    <div>
        <van-row style="background: #FE5722;height: 40px;padding-top: 6px;position: fixed;width: 100%;z-index:100000" >
            <van-col span="8"><van-icon name="arrow-left" size="30" color="white" @click="onClickLeft"/></van-col>
            <van-col span="8" style="text-align: center"><span style="color: white;text-align: center;font-size: 20px">客服留言</span></van-col>
            <van-col span="8" style="text-align: right;padding-right: 10px"><van-icon name="add-o" size="30" color="white" @click="add"/></van-col>
        </van-row>
        <div style="height: 48px"></div>
        <van-list
                v-model="loading"
                :finished="finished"
                :immediate-check="false"
                finished-text="没有更多了"
                @load="onLoad">
            <list-item v-for="(item,index) in list" :data="item" :key="index"></list-item>
        </van-list>
    </div>
</template>

<script>
    import { Toast } from 'vant';
    import ListItem from "./listItem";
    export default {
        name: "home",
        components: {ListItem},
        data() {
            return {
                list: [],
                loading: false,
                finished: false,
                total:0,
                param:{
                    pageNum:1,
                    pageSize:10,
                }
            };
        },
        created(){
            this.onLoad();
        },
        methods: {
            onLoad() {
                this.loading = true;
                this.$http.post("/app/user/question/newPager",this.param).then((res)=>{
                    if (res.code == 200){
                        for (let r of res.list){
                            r.cjsj = r.cjsj.substr(0,19)
                            this.list.push(r)
                        }
                        if (this.list.length >= res.total){
                            this.finished = true;
                        }
                    }else{
                        Toast(res.message)
                    }
                    this.loading = false;
                })
            },
            onClickLeft() {
                Toast('返回');
            },
            add() {
                this.$router.push({name:'add'})
            },
        }
    }
</script>

<style scoped>

</style>