<template>
  <div class="container">
    <h1 style="margin-top: 20px">确认订单</h1>
    <a-skeleton :active="true" :loading="vipPackage.id == undefined" :paragraph="{rows:5}">
      <div class="card">
        <div class="card-body">
          <p class="card-title">商品信息</p>
          <div class="vip">
            <div class="vip-info">
              <img src="../../../assets/vip-crown.png" alt=""/>
              <div>
                <p class="vip-title">{{vipPackage.name}}</p>
                <p class="vip-duration">×{{vipPackage.duration}}天</p>
              </div>
            </div>
            <span class="vip-price">¥{{vipPackage.price}}</span>
          </div>
          <a-divider/>
          <div style="text-align: right">
            <div class="order-info">
              <span>共1件商品，商品总金额：</span>
              <span class="order-price">￥{{vipPackage.price}}</span>
            </div>
            <div class="order-info">
              <p>应付：</p>
              <p class="order-price price">￥{{vipPackage.price}}</p>
            </div>
            <a-button shape="round" type="primary" size="large" @click="createOrder">提交订单</a-button>
          </div>
        </div>
      </div>
    </a-skeleton>
  </div>
</template>

<script>
import {ref,reactive} from "vue"
import store from "../../../store/store.js"
import axios from "../../../axios/axios.js"
import {useRouter,useRoute} from "vue-router"
import {message} from "ant-design-vue"
import qs from "qs"
export default{
  name:"CreateVipOrder",
  setup(){
    const router = useRouter();
    const route = useRoute();
    const vipPackage = reactive({
      id:undefined,
      name:undefined,
      price:undefined,
      duration:undefined
    })
    const init = ()=>{
      if (store.state.user.userId == "" || store.state.user.userId == undefined){
        message.warn("请登录后在进行操作");
        router.push({name:'Login'})
        return false;
      }
      if (route.params.id == undefined || route.params.name == undefined || route.params.price == undefined || route.params.duration == undefined){
        message.warn("非法访问")
        router.push("/")
        return false;
      }
      vipPackage.id = route.params.id;
      vipPackage.name = route.params.name;
      vipPackage.price = route.params.price;
      vipPackage.duration = route.params.duration;
    }

    init()

    const createOrder = ()=>{
      axios.post("/do/order/createviporder.do",qs.stringify({
        vipPackageId:vipPackage.id
      })).then(res=>{
        if(res.data.code != 200){
          message.error(res.data.data)
          return false
        }
        router.push({name:'OrderCheckOut',params:{orderNum:res.data.data,image:"vip"}})
      })
    }
    return{
      vipPackage,
      createOrder
    }
  }
}
</script>
<style scoped lang="scss">
.card {
  background-color: #fff;
  margin: 20px auto;
  box-shadow: 0 8px 16px 0 rgb(7 17 27 / 10%);
  border-radius: 8px;
  padding: 36px;

  .card-title {
    font-size: 16px;
    line-height: 16px;
  }

  .vip{
    background-color: #f3f5f7;
    padding: 24px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    img {
      width: 140px;
      height: 80px;
      margin-right: 24px;
    }
    .vip-info {
      display: flex;
    }

    .vip-title {
      margin: 0;
      color: #07111b;
      font-size: 18px;
      font-weight: 600;
    }

    .vip-duration{
      margin-top: 10px;
      color: #999999;
      font-size: 12px;
    }

    .vip-price {
      margin-right: 100px;
      color: #1c1f21;
      font-size: 20px;
      font-weight: 700;
    }
  }
}


.order-price{
  width: 140px;
  text-align: right;
  &.price{
    font-size: 24px;
    font-weight: 700;
    color: #00c691;
  }
}

.order-info{
  display: flex;
  justify-content: end;
  align-items: center;
  color: #07111b;
  font-size: 14px;
  margin: 10px 0 ;
}
.payment{
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>