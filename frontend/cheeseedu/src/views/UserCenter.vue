<template>
    <div style="min-height: 100vh;background-color: white">
      <UserCenterHeader/>
      <div class="container layout">
        <div class="menu">
          <UserCenterMenu></UserCenterMenu>
        </div>
        <div class="main">
          <router-view/>
        </div>
      </div>
    </div>
</template>

<script>
import UserCenterHeader from "../components/common/UserCenterHeader.vue";
import UserCenterMenu from "../components/common/UserCenterMenu.vue";
import store from "../store/store.js";
export default{
  name:"UserCenter",
  components:{
    UserCenterHeader,
    UserCenterMenu
  },
  setup(){
    //判断是否登录
    if (store.state.user.userId === undefined || store.state.user.token === undefined){
      window.location.href = "/"
    }
  }
}
</script>
<style scoped lang="scss">
.menu{
  width: 200px;
  margin-right: 28px;
}
.layout{
  padding-top: 20px;
  display: flex;
  align-items: flex-start;
  .main{
    width: 100%;
  }
}
</style>