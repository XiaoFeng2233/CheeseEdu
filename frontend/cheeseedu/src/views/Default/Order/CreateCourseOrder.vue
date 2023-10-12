<template>
  <div class="container">
    <h1 style="margin-top: 20px">确认订单</h1>
    <a-skeleton :active="true" :loading="loading" :paragraph="{rows:5}">
    <div class="card">
      <div class="card-body">
        <p class="card-title">商品信息</p>
        <div class="course">
          <div class="course-info">
            <img :src="course.image" alt=""/>
            <p class="course-title">{{course.title}}</p>
          </div>
          <span class="course-price">¥{{course.price}}</span>
        </div>
        <a-divider/>
        <div style="text-align: right">
          <div class="order-info">
            <span>共1件商品，商品总金额：</span>
            <span class="order-price">￥{{course.price}}</span>
          </div>
          <div class="order-info">
            <p>应付：</p>
            <p class="order-price price">￥{{course.price}}</p>
          </div>
          <a-button shape="round" type="primary" size="large" @click="createOrder">提交订单</a-button>
        </div>
      </div>
    </div>
    </a-skeleton>
  </div>
</template>

<script>
import store from "../../../store/store.js"
import {useRoute, useRouter} from "vue-router"
import {message} from "ant-design-vue"
import axios from "../../../axios/axios.js"
import {reactive, ref} from "vue";
import qs from "qs"
export default {
  name: "CreateCourseOrder",
  setup(props){
    const loading = ref(true);
    const router = useRouter();
    const route = useRoute()
    const course=reactive({
      id:route.params.courseId,
      title:undefined,
      image:undefined,
      price:undefined,
      type:undefined,
    })
    if (store.state.user.userId == "" || store.state.user.userId == undefined){
      message.warn("请登录后在进行操作");
      router.push({name:'Login'})
      return false;
    }


    if (course.id == "" || course.id==undefined){
      message.warn("非法访问")
      router.push({name:'Home'})
      return  false;
    }


    const init = ()=>{
      let isVip = 0;
      axios.get("/do/user/getuserinfo.do").then(res=>{
        isVip = res.data.data.isVip;
        axios.get("/do/common/getcoursebyid.do/" + course.id).then(res=>{
          course.type = res.data.data.type;
          course.price = res.data.data.price;
          course.title = res.data.data.title;
          course.image = res.data.data.image;

          if(res.data.data.type == 0){
            course.price = 0;
          }

          if (res.data.data.type == 1){
            if (isVip == 1){
              course.price = 0;
            }
          }

          loading.value = false
        })
      })
    }

    init();

    const createOrder = ()=>{
      axios.post("/do/order/createcourseorder.do",qs.stringify({
        courseId:course.id
      })).then(res=>{
        if (res.data.code != 200){
          message.error(res.data.data)
          return false;
        }
        router.push({name:'OrderCheckOut',params:{orderNum:res.data.data,image:course.image}})

      })
    }

    return{
      course,
      createOrder,
      loading
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

  .course {
    background-color: #f3f5f7;
    padding: 24px;
    display: flex;
    align-items: center;
    justify-content: space-between;

    img {
      width: 180px;
      height: 90px;
      margin-right: 24px;
    }

    .course-info {
      display: flex;
    }

    .course-title {
      margin: 0;
      color: #07111b;
      font-size: 16px;
    }

    .course-price {
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