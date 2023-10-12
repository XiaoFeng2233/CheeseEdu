<template>
  <div>
    <a-input v-model:value="phoneNumber" placeholder="手机号码" size="large">
      <template #prefix>
        <MobileOutlined style="color: rgba(0, 0, 0, 0.25)"/>
      </template>
    </a-input>
    <a-row :gutter="6" style="margin-top: 20px">
      <a-col :span="16">
        <a-input v-model:value="verifyCode" placeholder="验证码" size="large">
          <template #prefix>
            <MailOutlined style="color: rgba(0, 0, 0, 0.25)"/>
          </template>
        </a-input>
      </a-col>
      <a-col :span="8">
        <a-button size="large" @click="sendVerifyCode" :disabled="sendInterval>0" style="width: 100%">
          <span v-if="sendInterval>0">{{sendInterval}}秒</span>
          <span v-else>获取验证码</span>
        </a-button>
      </a-col>
    </a-row>
    <div class="login-control">
      <a-checkbox v-model:checked="keepLogin">保持登录</a-checkbox>
      <router-link :to="{name:'ResetPassword'}">忘记密码？</router-link>
    </div>
    <a-button style="margin-top: 20px;width: 100%" type="primary" size="large" @click="login">登录</a-button>
    <router-link :to="{name:'Register'}" class="reg">还没账号？点击注册</router-link>

  </div>
</template>

<script>
import {ref, computed} from "vue";
import {message} from "ant-design-vue";
import axios from "../../axios/axios.js";
import {MailOutlined,MobileOutlined} from "@ant-design/icons-vue"
import qs from "qs";
import jwt_decode from "jwt-decode";
import store from "../../store/store.js";
import {useRouter} from "vue-router";


export default {
  name: "LoginWithPhoneNumber",
  components:{
    MailOutlined,MobileOutlined
  },
  setup() {
    const phoneNumber = ref("")
    const verifyCode = ref("")
    const keepLogin = ref(false)
    const sendInterval = ref(0)

    const router = useRouter()

    const login = ()=> {
      let hex = /^[1][3,4,5,7,8][0-9]{9}$/
      if (phoneNumber.value.length < 1) {
        message.warning("请输入手机号码")
        return false;
      }
      if (!hex.test(phoneNumber.value)) {
        message.warning("请输入正确的手机号")
        return false;
      }
      if (verifyCode.value.length < 1) {
        message.warning("请输入验证码")
        return false;
      }
      axios.post("/do/login/loginwithverifycode.do",
          qs.stringify({
            mobileNumber: phoneNumber.value,
            verifyCode: verifyCode.value
          })
      ).then((response) => {
        if (response.data.code != '200') {
          message.error(response.data.data)
          return false;
        } else {
          message.success("登录成功,即将跳转页面~")
          if (keepLogin.value) {
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
          } else {
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
    const sendVerifyCode = () => {
      let hex = /^[1][3,4,5,7,8][0-9]{9}$/
      if (phoneNumber.value.length < 1) {
        message.warning("请输入手机号")
        return false;
      }
      if (!hex.test(phoneNumber.value)) {
        message.warning("请输入正确的手机号")
        return false;
      }

      let interval;
      const func = ()=>{
        sendInterval.value--
        if(sendInterval.value<=0){
          clearInterval(interval)
        }
      }

      axios.post("/do/login/sendloginverifycode.do",
          qs.stringify({
            "mobileNumber": phoneNumber.value
          })
      ).then((response) => {
        if (response.data.code != 200) {
          message.error(response.data.data)
          return false;
        } else {
          //开启定时器
          sendInterval.value = 120
          message.success("验证码发送成功,请注意查看")
          interval = setInterval(() => {
            func()
          }, 1000)
        }
      })
    }
    return {
      phoneNumber,
      verifyCode,
      keepLogin,
      sendInterval,
      login,
      sendVerifyCode
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