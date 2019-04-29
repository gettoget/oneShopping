<template>
    <div>
      <pager-tit title="晒单审核"></pager-tit>
      <Row :gutter="16">
        <Col :span="4" :xxl="4" :xl="6" :lg="8" :md="12" :sm="24" :xs="24"
             v-for="(item,index) in pagerdata">
            <shaidan :pagerdata = pagerdata[index]></shaidan>
        </Col>
      </Row>
    </div>
</template>

<script>
  import shaidan from './comp/shaidan'
    export default {
      name: "index",
      components:{
          shaidan
      },
      data () {
        return {
          value2: 0,
          pagerdata:[
            {
              yhm:'zds',
              tp:'http://img.zcool.cn/community/01c69f594d35d8a8012193a3ed77b3.jpg@1280w_1l_2o_100sh.jpg',
              ly:'z这个不错值得购买',
              sj:'2018-02-23'
            },
            {
              yhm:'ghj',
              tp:'http://img.zcool.cn/community/01c69f594d35d8a8012193a3ed77b3.jpg@1280w_1l_2o_100sh.jpg',
              ly:'z这个不号,不值得购买',
              sj:'2018-02-25'
            },
            {
              yhm:'456412s',
              tp:'http://img.zcool.cn/community/01c69f594d35d8a8012193a3ed77b3.jpg@1280w_1l_2o_100sh.jpg',
              ly:'z这个不错值得购买',
              sj:'2018-08-02'
            }
          ]
        }
      },
      methods:{

      }
    }
</script>
