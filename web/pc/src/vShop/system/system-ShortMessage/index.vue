<template>
    <div class="boxbackborder box_col">
      <pager-tit title="短信管理"></pager-tit>
       <div class="body padding-auto margin-5">
         <div class="demo-split">
           <Split v-model="split1">
             <div slot="left" class="demo-split-pane">
               短信模板
               <div style="padding: 10px">
                 <Input v-model="value6" type="textarea" :rows="4" placeholder="Enter something..." />
               </div>
               <div>
                 <Button type="success" long>SUBMIT</Button>
               </div>
             </div>
             <div slot="right" class="demo-split-pane">
               短信发送记录
               <div style="padding: 10px">
                 <Table :columns="columns1" :data="data1"></Table>
               </div>
             </div>
           </Split>
         </div>
       </div>
    </div>

</template>

<script>
    export default {
        name: "index",
        data() {
          return{
            split1: 0.3,
            value6:'你的短信验证码是:',
            columns1: [
              {
                title: 'Name',
                key: 'name'
              },
              {
                title: 'phone',
                key: 'age'
              },
              {
                title: 'Address',
                key: 'address'
              }
            ],
            data1: [
              {
                name: 'John Brown',
                age: 18812345652,
                address: 'New York No. 1 Lake Park',
                date: '2016-10-03'
              },
              {
                name: 'Jim Green',
                age: 13223233324,
                address: 'London No. 1 Lake Park',
                date: '2016-10-01'
              },
              {
                name: 'Joe Black',
                age: 13043434341,
                address: 'Sydney No. 1 Lake Park',
                date: '2016-10-02'
              },
              {
                name: 'Jon Snow',
                age: 15262332533,
                address: 'Ottawa No. 2 Lake Park',
                date: '2016-10-04'
              }
            ]
          }
        }
    }
</script>

<style scoped>
  .demo-split{
    height: 700px;
    border: 1px solid #dcdee2;
    box-shadow: #000c17;
  }
  .demo-split-pane{
    padding: 20px;
    font-size: 20px;
  }
</style>
