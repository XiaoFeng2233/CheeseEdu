<template>
  <div class="register">
    <div class="register-panel">
      <div class="register-logo">
        <img :src="logo" alt="">
      </div>
      <div class="register-input">
        <h3 style="font-size: 16px;font-weight: 700">
          注册账户
        </h3>
        <a-input style="margin-top: 20px" size="large" placeholder="手机号码" v-model:value="mobileNumber">
          <template #prefix>
            <UserOutlined style="color: rgba(0, 0, 0, 0.25)"/>
          </template>
        </a-input>
        <a-row :gutter="6" style="margin-top: 20px">
          <a-col :span="16">
            <a-input size="large" placeholder="验证码" v-model:value="code">
              <template #prefix>
                <MobileOutlined style="color: rgba(0, 0, 0, 0.25)"/>
              </template>
            </a-input>
          </a-col>
          <a-col :span="8">
            <a-button size="large" style="width: 100%" :disabled="sendInterval>0" @click="sendCode">
              <span v-if="sendInterval>0">{{ sendInterval }}秒</span>
              <span v-else>获取验证码</span>
            </a-button>
          </a-col>
        </a-row>
        <a-input style="margin-top: 20px" size="large" placeholder="用户名" v-model:value="username">
          <template #prefix>
            <UserOutlined style="color: rgba(0, 0, 0, 0.25)"/>
          </template>
        </a-input>
        <a-input style="margin-top: 20px" size="large" placeholder="密码" v-model:value="password" type="password">
          <template #prefix>
            <LockOutlined style="color: rgba(0, 0, 0, 0.25)"/>
          </template>
        </a-input>
        <a-input style="margin-top: 20px" size="large" placeholder="确认密码" v-model:value="rePassword" type="password">
          <template #prefix>
            <LockOutlined style="color: rgba(0, 0, 0, 0.25)"/>
          </template>
        </a-input>
      </div>
      <a-button style="width: 100%;margin-top: 20px" type="primary" size="large" @click="register">注册</a-button>
      <div style="text-align: center;font-size: 12px;color: rgba(0,0,0,.45);margin-top: 10px">注册即代表同意<span
          class="agreement" @click="openModal">《用户协议》</span>
      </div>
      <router-link :to="{name:'Login'}" class="reg">已有账号？立即登录</router-link>    </div>
    <a-modal v-model:visible="agreementVisible" :footer="false">
      <div v-html="agreement"></div>
    </a-modal>
  </div>
</template>

<script>
import {message} from "ant-design-vue"
import {UserOutlined, LockOutlined, MailOutlined, MobileOutlined} from "@ant-design/icons-vue"
import {computed, ref} from "vue"
import axios from "../axios/axios.js";
import qs from "qs"
import {useRouter} from "vue-router"
import store from "../store/store";

export default {
  name: "Register",
  components: {
    UserOutlined,
    LockOutlined,
    MailOutlined,
    MobileOutlined
  },
  setup() {
    const agreementVisible = ref(false)
    const agreement = ref("")
    const router = useRouter()
    const mobileNumber = ref("")
    const code = ref("")
    const username = ref("")
    const password = ref("")
    const rePassword = ref("")
    const hex = /^[1][3,4,5,7,8][0-9]{9}$/
    const sendInterval = ref(0)
    let interval;
    const func = ()=>{
      sendInterval.value--
      console.log(sendInterval)
      if (sendInterval.value <=0){
        clearInterval(interval)
      }
    }
    const register = () => {
      if (mobileNumber.value.length < 1 || code.value.length < 1 || username.value.length < 1 || password.value.length < 1 || rePassword.value.length < 1) {
        message.warn("请填写所有内容")
        return false;
      }
      if (!hex.test(mobileNumber.value)) {
        message.warning("请输入正确的手机号")
        return false;
      }
      if (password.value != rePassword.value) {
        message.warning("两次输入的密码不一致")
        return false;
      }

      axios.post("/do/register/register.do", qs.stringify({
        mobileNumber:mobileNumber.value,
        code:code.value,
        username:username.value,
        password:password.value
      })).then((response) => {
        if (response.data.code != "200") {
          message.error(response.data.data)
          return false;
        }
        message.success("注册成功,即将跳转至登录界面~")
        setTimeout(() => {
          router.push({name: 'Login'})
        }, 1500)
      })
      return false;
    }

    const sendCode = () => {
      if (mobileNumber.value.length < 0) {
        message.warn("请输入手机号码")
        return false;
      }

      if (!hex.test(mobileNumber.value)) {
        message.warning("请输入正确的手机号")
        return false;
      }

      axios.post("/do/register/sendregistercode.do", qs.stringify({
        mobileNumber:mobileNumber.value
      })).then((response) => {
        if (response.data.code != "200") {
          message.error(response.data.data)
          return false
        }
        message.success("验证码发送成功")
        sendInterval.value = 120

        setInterval(() => {
          func()
        }, 1000)
      })
    }

    axios.get("/do/common/getagreement.do").then(res=>{
      agreement.value = res.data.data
    })

    const openModal = ()=>{
      agreementVisible.value=true
    }

    const logo = computed(() => {
      return store.state.siteconfig.siteLogo
    })
    return {
      mobileNumber,
      code,
      username,
      password,
      rePassword,
      sendInterval,
      sendCode,
      register,
      agreementVisible,
      openModal,
      agreement,
      logo
    }
  }
}
</script>
<style scoped lang="scss">
.agreement{
  cursor: pointer;
  color: #07111b;
}
.register {
  width: 100%;
  height: 100vh;
  background: #f0f2f5 url("/home/images/login-background.svg") no-repeat 50%;
  overflow: hidden;
  display: flex;
  justify-content: center;
}

.register-panel {
  margin-top: 80px;
}

.register-logo {
  display: flex;
  justify-content: center;

  img {
    max-height: 40px;
  }
}

.register-input {
  max-width: 350px;
  margin-top: 40px;
}

.reg{
  display: block;
  margin-top: 20px;
  text-align: center;
  color: #00c691;
}
</style>