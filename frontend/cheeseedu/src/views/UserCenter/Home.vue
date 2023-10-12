<template>
  <div>
    <div class="user">
      <div class="user-info">
        <div class="user-avatar">
          <img :src="user.avatar" alt="">
        </div>
        <div class="user-name">
          {{user.nickName}}
        </div>
      </div>
      <div class="user-statistics">
        <div class="statistic">
          <p>
            <router-link :to="{name:'UserCenterCourse'}" style="color: #00c691">
              {{ courseCount }}
            </router-link>
          </p>
          <span>课程</span>
        </div>
        <div class="statistic">
         <p>
           <router-link :to="{name:'MyQuestion'}" style="color: #00c691">
             {{ questionCount }}
           </router-link>
         </p>
          <span>提问</span>
        </div>
      </div>
    </div>
    <a-row :gutter="24">
      <a-col :span="15">
        <CourseList :course-list="courseList"></CourseList>
      </a-col>
      <a-col :span="9">
        <a-skeleton active :loading="userinfo == undefined">
          <div class="vip">
            <div class="title">
              会员权益
            </div>
            <div class="msg" v-if="userinfo.isVip == 0">你还是不是会员 <router-link :to="{name:'Vip'}" style="color: #00c691">点击开通</router-link></div>
            <div class="box" v-else>
              <div class="name">
                <i class="iconfont icon-shenqingchengweiVIP"></i>
                {{ userinfo.vipPackageName }}
              </div>
              <div class="alert">
                于 {{userinfo.vipExpiredTime.split(' ')[0]}} 到期，距离到期还有 {{vipLeftDays}} 天
              </div>
              <a-button style="width: 100px;text-align: center;margin: 20px auto 0;display: flex;align-items: center;justify-content: center" type="primary" ghost shape="round" @click="renew">
                <i class="iconfont icon-renewal-vehicle" style="font-size: 20px"></i>
                <span>续费</span>
              </a-button>
            </div>
          </div>
        </a-skeleton>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import CourseList from "../../components/UserCenterHome/CourseList.vue";
import store from "../../store/store.js";
import {reactive, ref} from "vue";
import axios from "../../axios/axios";
import {useRouter} from "vue-router"
export default {
  name: "UserCenterHome",
  components:{
    CourseList
  },
  setup(){
    const router = useRouter();
    const courseList = ref(undefined)
    const courseCount = ref(0)
    const questionCount = ref(0)
    const userinfo = ref(undefined)
    const vipLeftDays = ref(undefined)
    const init = ()=>{
      axios.get("/do/user/getmycourse.do/5").then(res=>{
        courseList.value = res.data.data
        courseCount.value = res.data.data.length
      })

      axios.get("/do/question/getallquestionlist.do").then(res=>{
        questionCount.value = res.data.data.length
      })

      axios.get("/do/user/getuservipinfo.do").then(res=>{
        userinfo.value = res.data.data
        let d = new Date(res.data.data.vipExpiredTime)
        let time = d.getTime();
        let nowTime = new Date().getTime()
        vipLeftDays.value = parseInt((time-nowTime)/(24*3600*1000));
      })
    }

    init()

    const user = reactive({
      avatar:store.state.user.avatar,
      nickName:store.state.user.nickName
    })

    const vipModal = ref(false)

    const renew = ()=>{
      axios.get("/do/order/renewvippackage.do").then(res=>{
        router.push({name:"OrderCheckOut",params:{orderNum:res.data.data,image:'vip'}})
      })
    }

    return {
      user,
      courseList,
      courseCount,
      questionCount,
      userinfo,
      vipModal,
      vipLeftDays,
      renew,

    }
  }
}
</script>
<style scoped lang="scss">
.user {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  box-shadow: 0 2px 10px 0 rgb(46 61 72 / 5%);
  border-radius: 15px;
  padding: 20px;
}

.user-info {
  display: flex;
  align-items: center;

  .user-name {
    margin-left: 30px;
    color: #404040;
    font-weight: 500;
    font-size: 24px;
  }

  .user-avatar {
    width: 100px;
    height: 100px;
    border: 1px solid #00c691;
    border-radius: 50%;
    padding: 8px;

    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
    }
  }
}

.user-statistics {
  display: flex;
  align-items: center;

  .statistic {
    margin: 0 20px;
    text-align: center;

    p {
      margin: 0;
      font-size: 24px;
      color: #00c691;
      font-weight: 500;
      font-family: PingFang SC,Avenir,Tahoma,Arial,Lantinghei SC,Microsoft Yahei,Hiragino Sans GB,Microsoft Sans Serif,WenQuanYi Micro Hei,Helvetica,sans-serif;
    }

    span {
      color: #888888;
      font-size: 12px;
      font-weight: 500;
    }
  }
}


.vip{
  margin-top: 32px;
  width: 100%;
  box-shadow: 0 2px 10px 0 rgb(46 61 72 / 5%);
  border-radius: 15px;
  padding: 20px;
  .title{
    font-size: 20px;
  }

  .box{
    padding: 5px 0;
    .name{
      color: #171717;
      font-size: 20px;
      font-weight: 700;
    }
    .alert{
      color: #6c757d;
      font-size: 14px;
      line-height: 18px;
    }
    button{
      margin-top: 20px;
    }
  }
}

.msg{
  margin-top: 40px;
  text-align: center;
  color: #888888;
}
</style>