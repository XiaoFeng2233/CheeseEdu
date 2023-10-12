<template>
    <div class="container">
      <h1 style="margin-top: 20px">支付中心</h1>
      <div class="card">
        <a-skeleton active :loading="loading">

        <div class="card-title">
          <span>订单号：{{orderNum}}</span>
        </div>
        <div class="order-item">
          <div class="item-info">
            <img :src="image == 'vip' ? '/assets/vip-crown.d71695af.png' : image" alt="">
            <p>{{order.orderName}}</p>
          </div>
          <div class="item-price">
            <span>实付金额：</span>
            <span>¥ {{order.price}}</span>
          </div>
        </div>
        <a-divider/>
        <div class="payment">
          <span>支付方式</span>
          <div>
            <img src="../../../assets/alipay.png" alt="">
          </div>
        </div>
        <a-divider/>
        <div class="pay">
          <div>
            <span>应付金额：</span>
            <span class="price">￥ {{order.price}}</span>
          </div>
          <a-button shape="round" size="large" type="primary" @click="pay" :loading="btnLoading">立即支付</a-button>
        </div>
        </a-skeleton>
      </div>

      <a-modal v-model:visible="payModal" :footer="null">
        <div style="text-align: center">
          <img :src="qrcodeUrl" alt="">
        </div>
        <div style="margin-top: 20px;text-align: center">请使用支付宝扫码支付</div>
      </a-modal>
    </div>
</template>

<script>
import {useRouter,useRoute} from "vue-router"
import {message,notification} from "ant-design-vue"
import axios from "../../../axios/axios.js"
import {reactive,ref} from "vue";
import qs from "qs"
import qrcode from "qrcode"
import {onBeforeUnmount} from "vue"
export default{
  name:"OrderCheckOut",
  setup(){
    const route = useRoute();
    const router = useRouter()
    const loading = ref(true)
    const order = reactive({
      price:undefined,
      isFree:undefined,
      type:undefined,
      isPayOk:undefined,
      orderNum:undefined,
      orderName:undefined,
      userId:undefined,
      status:undefined
    })
    const orderNum = route.params.orderNum
    const image = route.params.image
    if (orderNum === undefined ){
      message.warn("非法请求")
      router.push({name:"Home"})
      return false;
    }

    const init = ()=>{
      axios.get("/do/order/getorder.do/" + orderNum).then(res=>{
        if(res.data.data === undefined || res.data.data === null){
          message.error("订单不存在")
          router.push({name:"UserCenterHome"})
          return false;
        }

        order.orderNum = res.data.data.orderNum
        order.price = res.data.data.price
        order.type = res.data.data.type
        order.isFree = res.data.data.isFree
        order.isPayOk = res.data.data.isPayOk
        order.userId = res.data.data.userId
        order.orderName = res.data.data.orderName
        order.status = res.data.data.status

        if (order.status == 0){
          notification['warning']({
            message: '支付失败',
            description:
                '订单支付已超时',
          });
          router.push({name:"UserCenterOrder"})
          return false;
        }

        if (order.isFree == 1){
          notification['success']({
            message: '交易成功',
            description:
                '该笔订单无需支付',
          });
          router.push({name:"UserCenterOrder"})
          return false;
        }

        if (order.isPayOk == 1){
          notification['warning']({
            message: '支付失败',
            description:
                '该笔订单已支付成功',
          });
          router.push({name:"UserCenterOrder"})
          return false;
        }
        loading.value = false
      })
    }

    init()

    const qrcodeUrl = ref(undefined)
    const payModal = ref(false)
    const btnLoading = ref(false)
    const pay = ()=>{
      btnLoading.value = true
      axios.post("/do/order/getpayurl.do",qs.stringify({
        orderNum:order.orderNum
      })).then(res=>{
        if (res.data.code != 200){
          message.error(res.data.data)
          btnLoading.value = false
          return false;
        }

        qrcode.toDataURL(res.data.data).then(url=>{
          qrcodeUrl.value = url
          payModal.value = true
          btnLoading.value = false
        })

      })
      return false;
    }

    let interval;

    const checkIsPayOk = ()=>{
      interval = setInterval(()=>{
        axios.get("/do/order/checkorder.do/" + orderNum).then(res=>{
          if (res.data.code != 200){
            notification['warning']({
              message: '订单支付失败',
              description:
                  '该订单已过期或不存在',
            });
            router.push({name:"UserCenterOrder"})
            return false;
          }

          if (res.data.data.isPayOk == 1){
            notification['success']({
              message: '订单支付成功',
              description:
                  '订单已经支付成功~',
            });
            router.push({name:"UserCenterOrder"})
            return false;
          }

        })
      },2000)
    }

    checkIsPayOk()


    onBeforeUnmount(()=>{
      clearInterval(interval)
    })

    return{
      orderNum,
      image,
      order,
      loading,
      pay,
      qrcodeUrl,
      payModal,
      btnLoading
    }
  }
}
</script>
<style scoped lang="scss">
.card{
  background-color: white;
  margin-top: 20px;
  margin-left: auto;
  margin-right: auto;
  padding: 0 36px 32px;
  width: 100%;
  box-shadow: 0 8px 16px 0 rgb(7 17 27 / 10%);
  border-radius: 8px;
  .card-title{
    height: 88px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    color: #5e5e5e;
    font-size: 16px;
    span:last-child{
      color:#00c691;
      font-size: 14px;
      cursor: pointer;
    }
  }
}

.order-item{
  background-color: #f3f5f7;
  padding: 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  .item-info{
    width: 750px;
    display: flex;
    img{
      width: 180px;
      height: 90px;
      margin-right: 24px;
    }
    p{
      margin: 0;
      color: #07111b;
      font-size: 16px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }
  .item-price{
    margin-right: 20px;
    font-size: 14px;
    color: #93999f;
    span:last-child{
      color: #00c691;
      font-size: 16px
    ;
    }
  }
}

.payment{
  display: flex;
  align-items: start;
  justify-content: center;
  flex-direction: column;
  span{
    font-size: 16px;
    color: #5e5e5e;
  }
  div{
    margin-top: 10px;
    img{
      border: 2px solid #00c691;
      height:60px;
      border-radius: 8px;
      cursor: pointer;
    }
  }
}

.pay{
  text-align: right;
  div{
    font-size: 14px;
    color: #07111b;
    .price{
      font-size: 24px;
      font-weight: 700;
      color: #00c691;
    }
  }
  button{
    margin-top: 20px;
  }
}
</style>