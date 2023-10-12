<template>
  <div class="menu">
    <div class="menu-item" :class="{active:routeName == 'UserCenterHome'}">
      <router-link :to="{name:'UserCenterHome'}">个人中心</router-link>
    </div>
    <p>学习</p>
    <div class="menu-item" :class="{active:routeName == 'UserCenterCourse'}">
      <router-link :to="{name:'UserCenterCourse'}">我的课程</router-link>
    </div>
    <div class="menu-item" :class="{active:routeName == 'UserCenterOrder'}">
      <router-link :to="{name:'UserCenterOrder'}">我的订单</router-link>
    </div>
    <div class="menu-item" :class="{active:routeName == 'MyQuestion'}">
      <router-link :to="{name:'MyQuestion'}">我的提问</router-link>
    </div>
    <p>账户</p>
    <div class="menu-item" :class="{active:routeName == 'Profile'}">
      <router-link :to="{name:'Profile'}">信息修改</router-link>
    </div>
    <div class="menu-item" :class="{active:routeName == 'UserCenterSetting'}">
      <router-link :to="{name:'UserCenterSetting'}">安全设置</router-link>
    </div>
    <div class="menu-item" :class="{active:routeName == 'Log'}">
      <router-link :to="{name:'Log'}">登录日志</router-link>
    </div>


  </div>
</template>

<script>
import {useRoute} from "vue-router";
import {computed} from "vue";
export default {
  name: "UserCenterMenu",
  setup(){
    const route = useRoute()
    const routeName = computed(()=>{
      return route.name
    })

    return{
      routeName
    }
  }
}
</script>
<style scoped lang="scss">
.menu {
  p {
    padding: 0 26px;
    margin: 10px 0;
    color: #b2b2b2;
  }

  .menu-item {
    padding: 0 24px;

    a {
      font-weight: 400;
      font-size: 19px;
      color: #404040;
      line-height: 50px;
    }

    &:hover {
      background-color: #fbfbfc;
    }

    &.active{
      background-color:#fbfbfc;
      border-left: 3px solid #00c691;
      a{
        color: #00c691;
      }
    }
  }
}
</style>