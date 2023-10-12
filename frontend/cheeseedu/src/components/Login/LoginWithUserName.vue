<template>
    <div>
      <a-input v-model:value="username" placeholder="用户名或手机号" size="large" >
        <template #prefix>
          <UserOutlined style="color: rgba(0, 0, 0, 0.25)" />
        </template>
      </a-input>
      <a-input-password  v-model:value="password" placeholder="账号密码" size="large" style="margin-top: 20px">
        <template #prefix>
          <LockOutlined style="color: rgba(0, 0, 0, 0.25)"/>
        </template>
      </a-input-password>
      <div class="login-control">
        <a-checkbox v-model:checked="keepLogin" >保持登录</a-checkbox>
        <router-link :to="{name:'ResetPassword'}">忘记密码？</router-link>
      </div>
      <a-button style="margin-top: 20px;width: 100%" type="primary" size="large" @click="login">登录</a-button>
      <router-link :to="{name:'Register'}" class="reg">还没账号？点击注册</router-link>
    </div>
</template>

<script>
import {ref} from "vue";
import {message} from "ant-design-vue";
import axios from "../../axios/axios.js";
import {UserOutlined,LockOutlined} from "@ant-design/icons-vue"
import qs from "qs";
import store from "../../store/store.js";
import jwt_decode from "jwt-decode";
import {useRouter} from "vue-router";

export default{
name:"LoginWithUserName",
  components:{
    UserOutlined,LockOutlined
  },
  setup(){
    const username = ref("")
    const password = ref("")
    const keepLogin = ref(false)

    const router = useRouter()

    const login = ()=>{
      if (username.value.length < 1 || password.value.length < 1) {
        message.warning("用户名或密码不能为空")
        return false;
      }
      axios.post("/do/login/loginwithusername.do",
          qs.stringify({
            username: username.value,
            password: password.value
          })
      ).then((response) => {
        if (response.data.code !== 200) {
          message.error(response.data.data)
          return false;
        }else{
          message.success("登录成功,即将跳转页面~")
          if (keepLogin.value){
            //添加到window.localStorage和Session
            window.localStorage.setItem("token",response.data.data)
            window.sessionStorage.setItem("token",response.data.data)
            //添加到vuex
            const decode = jwt_decode(response.data.data)
            const user = {
              token:response.data.data,
              userName:decode.userName,
              userId:decode.userId,
              isAdmin:decode.isAdmin === 1,
              expireTime:decode.exp,
              nickName:decode.nickname,
              avatar:decode.avatar
            }
            store.commit("user/update",user)
            setTimeout(()=>{
              router.push({name:"Home"})
            },1500)
          }else{
            //仅仅添加到vuex和Session

            window.sessionStorage.setItem("token",response.data.data)
            const decode = jwt_decode(response.data.data)
            const user = {
              token:response.data.data,
              userName:decode.userName,
              userId:decode.userId,
              isAdmin:decode.isAdmin === 1,
              expireTime:decode.exp,
              nickName:decode.nickname,
              avatar:decode.avatar
            }
            store.commit("user/update",user)
            setTimeout(()=>{
              router.push({name:"Home"})
            },1500)
          }
        }
      })
    }
    return{
      username,
      password,
      keepLogin,
      login
    }
  }
}
</script>
<style scoped lang="scss">
.login-control {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  label {
    color: rgba(0, 0, 0, .65)
  }

  a {
    color: #00c691;
  }
}

.reg{
  display: block;
  margin-top: 20px;
  text-align: center;
  color: #00c691;
}


</style>