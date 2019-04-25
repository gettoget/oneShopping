<template>
    <div>
      <Row>
        <Col span="6" v-for="item in 8">
          <div style="padding: 5px">
            <Card style="width:240px" shadow>
              <p slot="title">用户名的晒单</p>
              <div>
                <Carousel autoplay v-model="value2" loop>
                  <CarouselItem>
                    <div class="demo-carousel"> <p><img height="150" src="http://img.zcool.cn/community/01c69f594d35d8a8012193a3ed77b3.jpg@1280w_1l_2o_100sh.jpg"></p>
                    </div>
                  </CarouselItem>
                  <CarouselItem>
                    <div class="demo-carousel"> <p><img height="150" src="http://img.zcool.cn/community/01c69f594d35d8a8012193a3ed77b3.jpg@1280w_1l_2o_100sh.jpg"></p>
                    </div>
                  </CarouselItem>
                  <CarouselItem>
                    <div class="demo-carousel"> <p><img height="150" src="http://img.zcool.cn/community/01c69f594d35d8a8012193a3ed77b3.jpg@1280w_1l_2o_100sh.jpg"></p>
                    </div>
                  </CarouselItem>
                  <CarouselItem>
                    <div class="demo-carousel"> <p><img  height="150" src="http://img.zcool.cn/community/01c69f594d35d8a8012193a3ed77b3.jpg@1280w_1l_2o_100sh.jpg"></p>
                    </div>
                  </CarouselItem>
                </Carousel>
                <h4>晒单留言:</h4>
                <h6>'A high quality UI Toolkit based on Vue.js'</h6>
                <hr>
                <p>date:2019-11-15</p>
                <hr align=center width=200 color=#987cb9 SIZE=1>
              </div>
              <div style="padding-top: 5px">
                <Row>
                  <Col span="12" align="center">
                    <Button type="success">审核通过</Button>
                  </Col>
                  <Col align="center">
                    <Button type="error">删除记录</Button>
                  </Col>
                </Row>
              </div>
            </Card>
          </div>
        </Col>
      </Row>
    </div>
</template>

<script>
    export default {
        name: "index",
      data () {
        return {
          value2: 0
        }
      }
    }
</script>

<style scoped>

</style>
