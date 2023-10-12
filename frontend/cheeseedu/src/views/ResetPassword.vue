<template>
  <div class="resetpassword">
    <div class="panel">
      <div class="logo">
        <img :src="logo" alt="">
      </div>
      <div class="reset-input">
        <h3 style="font-size: 16px;font-weight: 700">
          找回密码
        </h3>
        <a-input style="margin-top: 20px" v-model:value="mobileNumber" size="large" placeholder="手机号码">
          <template #prefix>
            <MobileOutlined style="color: rgba(0, 0, 0, 0.25)"/>
          </template>
        </a-input>
        <a-row :gutter="6" style="margin-top: 20px">
          <a-col :span="16">
            <a-input v-model:value="verifyCode" size="large" placeholder="验证码">
              <template #prefix>
                <MailOutlined style="color: rgba(0, 0, 0, 0.25)"/>
              </template>
            </a-input>
          </a-col>
          <a-col :span="8">
            <a-button size="large" @click="sendVerifyCode" :disabled="sendInterval > 0" style="width: 100%">
              <span v-if="sendInterval>0">{{ sendInterval }}秒</span>
              <span v-else>发送验证码</span>
            </a-button>
          </a-col>
        </a-row>
        <a-input style="margin-top: 20px" size="large" v-model:value="password" type="password" placeholder="新密码">
          <template #prefix>
            <LockOutlined style="color: rgba(0, 0, 0, 0.25)"/>
          </template>
        </a-input>
        <a-input style="margin-top: 20px" size="large" v-model:value="rePassword" type="password" placeholder="确认新密码">
          <template #prefix>
            <LockOutlined style="color: rgba(0, 0, 0, 0.25)"/>
          </template>
        </a-input>
      </div>
      <a-button style="width: 100%;margin-top: 20px" type="primary" size="large" @click="reset">立即重置</a-button>
    </div>
  </div>
</template>

<script>
import {computed, ref} from "vue"
import {message} from "ant-design-vue";
import axios from "../axios/axios.js"
import qs from "qs"
import {MobileOutlined, MailOutlined, LockOutlined} from "@ant-design/icons-vue"
import {useRouter} from "vue-router"
import store from "../store/store";

export default {
  name: "ResetPassword",
  components: {
    MobileOutlined,
    MailOutlined,
    LockOutlined
  },
  setup() {
    const mobileNumber = ref("")
    const verifyCode = ref("")
    const password = ref("")
    const rePassword = ref("")
    const sendInterval = ref(0)
    const hex = /^[1][3,4,5,7,8][0-9]{9}$/
    const router = useRouter()
    const sendVerifyCode = () => {
      if (mobileNumber.value.length < 1) {
        message.warning("请输入手机号码")
        return false;
      }
      if (!hex.test(mobileNumber.value)) {
        message.warning("请输入正确的手机号")
        return false;
      }

      axios.post("/do/login/sendresetverifycode.do",
          qs.stringify({
            "mobileNumber": mobileNumber.value
          })
      ).then((response) => {
        if (response.data.code != "200"){
          message.error(response.data.data)
          return false;
        }
        message.success("验证码发送成功,请注意查看")
        sendInterval.value = 120
        interval = setInterval(() => {
          func()
        }, 1000)
      })
    }

    let interval;
    const func = ()=>{
      sendInterval.value--
      if (sendInterval.value <=0){
        clearInterval(interval)
      }
    }

    const reset = () => {
      if (mobileNumber.value.length < 1 || verifyCode.value.length < 1 || password.value.length < 1 || rePassword.value.length < 1) {
        message.warn("请填写所有内容")
        return false;
      }

      if (password.value !== rePassword.value) {
        message.warn("两次密码输入不一致")
        return false;
      }

      axios.post("/do/login/resetpassword.do", qs.stringify({
        "mobileNumber":mobileNumber.value,
        "verifyCode":verifyCode.value,
        "newpassword":password.value})
      ).then((response) => {
        if (response.data.code != "200"){
          message.error(response.data.data)
          return false;
        }

        message.success("密码修改成功~")
        setTimeout(()=>{
          router.push({name:"Login"})
        },1500)
      })
    }

    const logo = computed(() => {
      return store.state.siteconfig.siteLogo
    })
    return {
      mobileNumber,
      verifyCode,
      password,
      rePassword,
      sendInterval,
      sendVerifyCode,
      reset,
      logo

    }
  }
}
</script>
<style scoped lang="scss">
.resetpassword {
  width: 100%;
  height: 100vh;
  background: #f0f2f5 url("/home/images/login-background.svg") no-repeat 50%;
  overflow: hidden;
  display: flex;
  justify-content: center;
}

.panel {
  margin-top: 80px;
}

.logo {
  display: flex;
  justify-content: center;

  img {
    max-height: 40px;
  }
}

.reset-input {
  max-width: 350px;
  margin-top: 40px;
}
</style>