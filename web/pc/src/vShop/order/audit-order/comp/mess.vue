<style lang="less">
</style>
<template>
  <div>
    <Modal v-model="showModal" width='900' :closable='mesF' :mask-closable="mesF" :title="'订单详情'">
      <div v-if="SpinShow" style="width:100%;height:100%;position: fixed;top: 0;left:0;z-index: 1111;">
        <Spin fix>
          <Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
          <div style="font-size: 30px;">数据加载中请稍后</div>
        </Spin>
      </div>
      <i-form :model="formItem" :label-width="80">
        <Row>
          <Col span="8">
            <Form-item label="">
              <img :src="phone" alt="">
            </Form-item>
          </Col>
          <Col span="8">
            <Form-item label="">
              <img :src="phone" alt="">
            </Form-item>
          </Col>
          <Col span="8">
            <Form-item label="">
              <img :src="phone" alt="">
            </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="12">
            <Form-item label="订单编号">
              <i-input disabled  :value.sync="formItem.input" v-model="usermes.id" placeholder=""></i-input>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="下单时间">
              <i-input disabled :value.sync="formItem.input" v-model="usermes.cjsj" placeholder=""></i-input>
            </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="12">
            <Form-item label="收货地址">
              <i-input disabled :value.sync="formItem.input" v-model="usermes.proName" placeholder="">123</i-input>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="收货人">
              <i-input disabled :value.sync="formItem.input" v-model="usermes.shr" placeholder=""></i-input>
            </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="12">
            <Form-item label="商品id">
              <i-input disabled :value.sync="formItem.input" v-model="usermes.proId" placeholder="">123</i-input>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="商品名称">
              <i-input disabled :value.sync="formItem.input" v-model="usermes.proName" placeholder=""></i-input>
            </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="12">
            <Form-item label="商品数量">
              <i-input disabled :value.sync="formItem.input" v-model="usermes.gmfs" placeholder="">123</i-input>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="商品规格">
              <i-input disabled :value.sync="formItem.input" v-model="usermes.spgg" placeholder=""></i-input>
            </Form-item>
          </Col>
        </Row>
        <Row>
          <Col span="12">
            <Form-item label="商品单价">
              <i-input disabled :value.sync="formItem.input" v-model="usermes.spdj" placeholder="">123</i-input>
            </Form-item>
          </Col>
          <Col span="12">
            <Form-item label="订单总价">
              <i-input disabled :value.sync="formItem.input" v-model="usermes.zfje" placeholder=""></i-input>
            </Form-item>
          </Col>
        </Row>
      </i-form>
      <div slot='footer'>
        <Button type="default" @click="colse" style="color: #949494">关闭</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
  import phone from '@/assets/img/iphone.jpg'
  export default {
    name: '',
    data() {
      return {
        phone,
        formItem: {
          input: '',
          select: '',
          radio: 'male',
          checkbox: [],
          switch: true,
          date: '',
          time: '',
          slider: [20, 50],
          textarea: ''
        },
        form:{},
        SpinShow: false,
        showModal: true,
        operate: '新建',
        mesF: false,
      }
    },
    props: {
      usermes:{}
    },
    created() {

    },
    mounted() {

    },
    methods: {
      AddDataListOk(){
        var v = this
        v.$parent.compName = ''
      },
      colse() {
        var v = this
        v.$parent.compName = ''
      }
    }
  }
</script>

<style>

</style>
