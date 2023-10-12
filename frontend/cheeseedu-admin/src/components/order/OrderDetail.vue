<template>
    <div>
      <a-skeleton :loading="order == undefined">
        <a-descriptions title="订单详细信息" bordered>
          <a-descriptions-item label="订单号" :span="2">{{order.orderNum}}</a-descriptions-item>
          <a-descriptions-item label="用户名">{{order.username}}</a-descriptions-item>
          <a-descriptions-item label="超时状态">
            <a-tag color="green" v-if="order.status==1">正常</a-tag>
            <a-tag color="red" v-else>已超时</a-tag>
          </a-descriptions-item>
          <a-descriptions-item label="支付状态">
            <a-tag color="green" v-if="order.isPayOk==1">已支付</a-tag>
            <a-tag color="red" v-else>未支付</a-tag>
          </a-descriptions-item>
          <a-descriptions-item label="创建时间">{{order.createdTime}}</a-descriptions-item>
          <a-descriptions-item label="金额">¥{{order.price}}</a-descriptions-item>
          <a-descriptions-item label="支付宝流水号" :span="2">{{order.paymentNum}}</a-descriptions-item>
          <a-descriptions-item label="商品类别">
            <span v-if="order.courseId != 0">
              课程
            </span>
            <span v-else-if="order.vipPackageId != 0">
              VIP套餐
            </span>
          </a-descriptions-item>
          <a-descriptions-item label="商品ID">
             <span v-if="order.courseId != 0">
              {{order.courseId}}
            </span>
            <span v-else-if="order.vipPackageId != 0">
              {{order.vipPackageId}}
            </span>
          </a-descriptions-item>
          <a-descriptions-item label="商品名称">
             <span v-if="order.courseId != 0">
              {{order.courseTitle}}
            </span>
            <span v-else-if="order.vipPackageId != 0">
              {{order.vipPackageName}}
            </span>
          </a-descriptions-item>


        </a-descriptions>
      </a-skeleton>
    </div>
</template>

<script>
import {ref,reactive} from "vue"
import axios from "../../axios/axios.js"
export default{
  name:"OrderDetail",
  props:{
    orderId:Number
  },
  setup(props){
    const orderId = props.orderId
    const order = ref(undefined)
    const init = ()=>{
      axios.get("/do/admin/getorderbyid.do/" + orderId).then(res=>{
        order.value = res.data.data
      })
    }

    init()
    return{
      order
    }
  }
}
</script>
<style scoped>

</style>