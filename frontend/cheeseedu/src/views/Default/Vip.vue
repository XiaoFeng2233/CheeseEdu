<template>
    <div class="container">
      <a-skeleton :loading="vipList == undefined" :paragraph="{ rows: 6 }">
        <div class="msg">
          <img src="../../assets/crown.png" alt="">
          <span>请选择会员套餐</span>
        </div>
        <div class="vip-package">
          <div class="package-item" v-for="item in vipList">
            <div class="name">
              {{ item.name }}
            </div>
            <div class="price-duration">
              <span>¥</span>
              <span class="price">{{item.price}}</span>
              <span>/{{item.duration}}天</span>
            </div>
            <div class="text">
              可免费观看所有会员特权视频
            </div>
            <router-link :to="{name:'CreateVipOrder',params:{id:item.id,name:item.name,price:item.price,duration:item.duration}}">
              <button>购买</button>
            </router-link>
          </div>
        </div>
      </a-skeleton>
    </div>
</template>

<script>
import axios from "../../axios/axios.js"
import {message} from "ant-design-vue"
import {ref} from "vue"
export default{
  name:"Vip",
  setup(){
    const vipList = ref(undefined)
    const init = ()=>{
      axios.get("/do/common/getvippackgelist.do").then(res=>{
        vipList.value = res.data.data
      })
    }
    init()
    return{
      vipList
    }
  }
}
</script>
<style scoped lang="scss">
.msg{
  img{
    width: 50px;
    height: 50px;
  }
  padding: 30px 20px;
  font-size: 30px;
  color: #333333;
  font-weight: 500;
  display: flex;
  align-items: center;
  justify-content: center;
  span{
    margin-left: 20px;
  }
}

.vip-package{
  display: grid;
  grid-template-columns:repeat(3,minmax(0,1fr));
  grid-column-gap: 30px;
  grid-row-gap: 30px;
  text-align: center;
  .package-item{
    background: linear-gradient(135deg,#fff4e4,#fff4e4 0,#fece86);
    border-radius: 15px;
    padding: 30px;
    &:hover{
      box-shadow: rgba(17, 17, 26, 0.1) 0px 0px 16px;
    }
    .name{
      color: #572f1d;
      font-size: 24px;
      font-weight: 700;
      margin-bottom: 5px;

    }
    .price-duration{
      color: #572f1d;
      font-size: 20px;
      font-weight: 600;
      line-height: 48px;
      .price{
        font-size: 48px;
        font-weight: 700;
        margin-left: 5px;
        margin-right: 5px;
      }
    }
  }
  .text{
    margin-bottom: 20px;
  }
  button{
    width: 100%;
    height: 50px;
    background-color: #e1a500;
    border:none;
    color: white;
    font-size: 21px;
    font-weight: 500;
    border-radius: 10px;
    cursor: pointer;
    &:hover{
      background-color: #E7AF1E;
    }
  }
}
</style>