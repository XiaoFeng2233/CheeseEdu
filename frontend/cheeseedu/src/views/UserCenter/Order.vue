<template>
    <div>
      <div class="card">
        <h1>我的订单</h1>
      </div>
      <ul class="order-list">
        <li class="list-item" v-for="item in orderList">
          <div class="order-number">
            <i class="iconfont icon-wodedingdan"></i>
            <span class="number">订单编号：{{item.orderNum}}</span>
            <span class="date">{{item.createdTime}}</span>
          </div>
          <a-divider style="margin: 0;"/>
          <div class="order-info">
            <img :src="item.courseImage" alt="" v-if="item.type == 1">
            <img src="../../assets/vip-crown.png" alt="" v-else-if="item.type == 2">
            <div class="order-name">
              <p class="name">
                {{item.orderName}}
              </p>
              <p class="price">¥ {{item.price}}</p>
            </div>
            <div class="order-price">¥ {{item.price}}</div>
            <div class="order-status">
<!--              <span class="expired" v-if="item.status == 0">已过期</span>-->
              <div>
                <div class="pending" v-if="item.isPayOk != 1">
                  <router-link v-if="item.status == 1" :to="{name:'OrderCheckOut',params:{orderNum:item.orderNum,image:item.courseImage == null ? 'vip' : item.courseImage}}">
                    <a-button shape="round" type="primary">立即支付</a-button>
                  </router-link>
                  <span class="expired" v-else-if="item.status == 0">已过期</span>
                  <p v-if="item.status == 1" @click="cancelOrder(item.orderNum)" style="cursor: pointer">取消订单</p>
                </div>
                <span class="success" v-else>支付成功</span>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </div>
</template>

<script>
import axios from "../../axios/axios.js"
import {ref} from "vue"
export default{
  name:"UserCenterOrder",
  setup(){
    const orderList = ref(undefined)
    axios.get("/do/order/getorderlist.do").then(res=>{
      orderList.value = res.data.data
    })

    const cancelOrder = (orderNum)=>{
      axios.get("/do/order/cancelorder.do/" + orderNum).then(res=>{
        location.reload()
      })
    }

    return{
      orderList,
      cancelOrder
    }
  }
}
</script>
<style scoped lang="scss">
.card{
  width: 100%;
  box-shadow: 0 2px 10px 0 rgb(46 61 72 / 5%);
  padding: 20px;
}
.order-list{
  list-style: none;
  padding: 0;
  .list-item{
    margin: 20px 0;
    box-shadow: 0 2px 8px 2px rgb(0 0 0 / 10%);
    padding: 0 32px 32px;
    border-radius: 8px;
    &:hover{
      box-shadow: 0 2px 8px 2px rgb(0 0 0 / 15%);
    }
  }
}

.order-number{
  padding: 28px 0 19px;
  .number{
    margin-right: 40px;
    color: #4d555d;
    font-weight: 700;
    font-size: 12px;
  }
  .date{
    font-size: 12px;
    color: #93999f;
    font-weight: 500;
  }
  .iconfont{
    color: #00c691;
    font-weight: 800;
    margin-right: 10px;
  }
}

.order-info{
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  img{
    width: 180px;
    height: 90px;
  }

  .order-name{
    padding:0 20px;
    flex:1;
    width: 0;
    .name{
      color: #07111b;
      line-height: 22px;
      margin-bottom: 8px;
    }
    .price{
      color: #00c691;
    }
  }
  .order-price{
    width: 200px;
    text-align: center;
    border-left: 1px solid #d9dde1;
    border-right: 1px solid #d9dde1;
    padding-top: 30px;
    color: #00c691;
    font-size: 16px;

  }
  .order-status{
    text-align: center;
    width: 200px;
    padding-top: 30px;
    .expired{
      color: #93999f;
    }
    .success{
      color: #00c691;
      font-weight: 700;
    }
    .pending{
      p{
        cursor: pointer;
        color: #93999f;
        font-size: 12px;
        margin-top: 10px;
      }
    }
  }
}
</style>