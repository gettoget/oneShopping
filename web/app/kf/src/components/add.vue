<template>
    <div>
        <van-row style="background: #FE5722;height: 40px;padding-top: 6px;position: fixed;width: 100%;z-index:100000" >
            <van-col span="8"><van-icon name="arrow-left" size="30" color="white" @click="onClickLeft"/></van-col>
            <van-col span="8" style="text-align: center"><span style="color: white;text-align: center;font-size: 20px">留言</span></van-col>
            <van-col span="8"></van-col>
        </van-row>
        <div style="height: 40px"></div>
        <van-cell-group>
            <van-field
                    v-model="message"
                    label="留言"
                    type="textarea"
                    placeholder="请输入留言"
                    rows="1"
                    autosize
            />
        </van-cell-group>
        <div style="text-align: center;padding: 10px"><van-button type="primary" size="large" @click="add">提交</van-button></div>
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
                message: "",
            };
        },
        methods: {
            add() {
                this.$http.post("/app/user/question/save",{que:this.message}).then((res)=>{
                    if (res.code == 200){

                        this.$router.back()
                    }else{
                        Toast(res.message)
                    }                })
                // 异步更新数据
                // setTimeout(() => {
                //     for (let i = 0; i < 10; i++) {
                //         this.list.push({que:"queee"+this.list.length + 1,ans:"anssss"+this.list.length + 1,cjsj:'2019-02-02 22:22:22'});
                //     }
                //     // 加载状态结束
                //     this.loading = false;
                //
                //     // 数据全部加载完成
                //     if (this.list.length >= 40) {
                //         this.finished = true;
                //     }
                // }, 500);
            },
            onClickLeft() {
                Toast('返回');
            },
        }
    }
</script>

<style scoped>

</style>