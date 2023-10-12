<template>
  <div class="login">
    <div class="login-panel">
      <div class="login-logo">
        <img :src="logo" alt="">
      </div>
      <a-tabs v-model:activeKey="activeKey" style="margin-top: 30px;max-width:350px">
        <a-tab-pane key="1" tab="手机号码登录">
          <LoginWithPhoneNumber/>
        </a-tab-pane>
        <a-tab-pane key="2" tab="账号密码登录" force-render>
          <LoginWithUserName/>
        </a-tab-pane>
        <template #renderTabBar="{ DefaultTabBar, ...props }">
          <component
              :is="DefaultTabBar"
              v-bind="props"
              :style="{ zIndex: 1, textAlign: 'center' }"
          />
        </template>
      </a-tabs>

    </div>
  </div>
</template>

<script>
import {computed, ref} from "vue"
import {UserOutlined, LockOutlined, MailOutlined, MobileOutlined} from "@ant-design/icons-vue"
import LoginWithPhoneNumber from "../components/Login/LoginWithPhoneNumber.vue";
import LoginWithUserName from "../components/Login/LoginWithUserName.vue";
import jwt_decode from "jwt-decode";
import {useRouter} from "vue-router"
import store from "../store/store";

export default {
  name: "Login",
  setup() {
    const activeKey = ref("1")

    //检验是否已经登录
    const token = window.localStorage["token"]
    if (token !== undefined){
      const decode = jwt_decode(token)
      if (decode.exp > Date.now()/1000){
        useRouter().push({name:"Home"})
      }
    }
    const logo = computed(() => {
      return store.state.siteconfig.siteLogo
    })
    return {
      activeKey,
      logo
    }
  },
  components: {
    UserOutlined, LockOutlined, MailOutlined, MobileOutlined, LoginWithPhoneNumber, LoginWithUserName
  }
}
</script>
<style scoped lang="scss">
.login {
  width: 100%;
  height: 100vh;
  background: #f0f2f5 url("/home/images/login-background.svg") no-repeat 50%;
  overflow: hidden;
  display: flex;
  justify-content: center;
}

.login-panel {
  margin-top: 80px;
}

.login-logo {
  display: flex;
  justify-content: center;

  img {
    max-height: 40px;
  }
}


</style>