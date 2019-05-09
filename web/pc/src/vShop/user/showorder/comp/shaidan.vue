<template>
    <div>
      <Card>
        <p slot="title">{{pagerdata.yhm}}的晒单</p>
        <div>
          <Carousel v-model="value2" loop>
            <CarouselItem>
              <div class="demo-carousel">
                <img style="width: 100%"
                     src="http://img.zcool.cn/community/01c69f594d35d8a8012193a3ed77b3.jpg@1280w_1l_2o_100sh.jpg">
              </div>
            </CarouselItem>
            <CarouselItem>
              <div class="demo-carousel">
                <img style="width: 100%"
                     src="http://img.zcool.cn/community/01c69f594d35d8a8012193a3ed77b3.jpg@1280w_1l_2o_100sh.jpg">
              </div>
            </CarouselItem>
            <CarouselItem>
              <div class="demo-carousel">
                <img style="width: 100%"
                     src="http://img.zcool.cn/community/01c69f594d35d8a8012193a3ed77b3.jpg@1280w_1l_2o_100sh.jpg">
              </div>
            </CarouselItem>
            <CarouselItem>
              <div class="demo-carousel">
                <img style="width: 100%"
                     src="http://img.zcool.cn/community/01c69f594d35d8a8012193a3ed77b3.jpg@1280w_1l_2o_100sh.jpg">
              </div>
            </CarouselItem>
          </Carousel>
          <h4>晒单留言:</h4>
          <h6>{{pagerdata.ly}}</h6>
          <hr>
          <p>date:{{pagerdata.sj}}</p>
          <hr align=center width=200 color=#987cb9 SIZE=1>
        </div>
        <div style="padding-top: 5px">
          <Row>
            <Col span="12" align="center">
              <Button type="success" @click="Shtg">审核通过</Button>
            </Col>
            <Col span="12" align="center">
              <Button type="error" @click="delet">删除记录</Button>
            </Col>
          </Row>
        </div>
      </Card>
    </div>
</template>

<script>
    export default {
        name: "shaidan",
       data(){
          return{
            value2: 0
          }
       },
      props:{
          pagerdata:[]
      },
      created(){
        console.log(this.pagerdata);
      },
      methods:{
        Shtg(){
          this.swal({title:"审核成功！",
            text:"已成功审核数据",
            type:"success"},
            function(){window.location="/"}
          )
        },
        delet(){
          this.swal( {title:"您确定要删除这条数据吗",
              text:"删除后将无法恢复，请谨慎操作！",
              type:"warning",
              showCancelButton:true,
              confirmButtonColor:"#DD6B55",
              confirmButtonText:"确定删除！",
              cancelButtonText:"取消",
              closeOnConfirm:false,
              closeOnCancel:false
            },
            function(isConfirm)
            {
              if(isConfirm)
              {
                this.swal({title:"删除成功！",
                  text:"您已经永久删除了这条数据。",
                  type:"success"},function(){window.location="/video/destroy/"+id})
              }
              else{
                this.swal({title:"已取消",
                  text:"您取消了删除操作！",
                  type:"error"})
              }
            });
        }
      }
    }
</script>

<style scoped>

</style>
