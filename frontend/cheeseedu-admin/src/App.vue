<template>
  <div class="layout">
    <el-scrollbar class="aside">
      <LayoutMenu/>
    </el-scrollbar>
    <div class="container">
      <div class="header">
        <LayoutHeader/>
      </div>
      <el-scrollbar class="main">
        <router-view/>
      </el-scrollbar>
    </div>
  </div>
</template>

<script>
import LayoutMenu from "./components/common/LayoutMenu.vue";
import LayoutHeader from "./components/common/LayoutHeader.vue";
import jwtDecode from "jwt-decode"
import store from "./store/store.js"
export default {
  components: {
    LayoutMenu,
    LayoutHeader
  },
  setup(){
    let token = window.localStorage["token"]
    let sessionToken = window.sessionStorage.getItem("token")
    if (token !== undefined){
      const decode = jwtDecode(token)
      if (decode.exp<Date.now()/1000){
        window.localStorage.removeItem("token")
        window.location.href = "/"
        return false;
      }
      const user = {
        token,
        userName:decode.userName,
        userId:decode.userId,
        isAdmin:decode.isAdmin === 1,
        expireTime:decode.exp,
        nickName:decode.nickname,
        avatar:decode.avatar
      }
      store.commit("user/update",user)
    }else if (sessionToken !== undefined){
      const decode = jwtDecode(sessionToken)
      if (decode.exp<Date.now()/1000){
        window.sessionStorage.removeItem("token")
        window.location.href = "/"
        return false;
      }
      const user = {
        token:sessionToken,
        userName:decode.userName,
        userId:decode.userId,
        isAdmin:decode.isAdmin === 1,
        expireTime:decode.exp,
        nickName:decode.nickname,
        avatar:decode.avatar
      }
      store.commit("user/update",user)
    }else{
      window.location.href = "/"
      return false;
    }



  }
}
</script>

<style lang="scss" scoped>
.layout {
  width: 100%;
  height: 100%;
  display: grid;
  grid-template-columns: 250px 1fr;
  grid-template-rows: 1fr 1fr;
  grid-template-areas: "aside main"
                        "aside main";
}

.aside {
  width: 100%;
  background-color: #3A4167;
  height: 100%;
  overflow: auto;
  grid-area: aside;
}

.container {
  flex:1;
  height: 100%;
  display: flex;
  width: 100%;
  flex-direction: column;
  grid-area: main;
  overflow: auto;

  .header {
    height: 60px;
    width: 100%;
  }

  .main {
    flex: 1;
    background-color: #EDF0F9;
  }
}
</style>
