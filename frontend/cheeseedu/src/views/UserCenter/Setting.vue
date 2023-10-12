<template>
  <div>
    <a-row :gutter="24">
      <a-col :span="12">
        <div class="card">
          <div class="card-title">手机号码修改</div>
          <div class="mobile-number">
            手机号 <span>{{user.mobileNumber}}</span>
          </div>
          <a-form layout="vertical">
            <a-form-item label="旧的手机号码">
              <a-input size="large" v-model:value="phone.oldphone"/>
            </a-form-item>
            <a-form-item label="登录密码">
              <a-input size="large" v-model:value="phone.password" type="password"/>
            </a-form-item>
            <a-form-item label="新的手机号码">
              <a-input size="large" v-model:value="phone.newphone"/>
            </a-form-item>
            <a-form-item label="验证码">
              <div class="v-code">
                <a-input size="large" v-model:value="phone.verifyCode"/>
                <a-button size="large" ghost :disabled="verifyTime != 0" type="primary" @click="sendVerifyCode()">
                  <span v-if="verifyTime==0">发送验证码</span>
                  <span v-else>{{verifyTime}}</span>
                </a-button>
              </div>
            </a-form-item>
          </a-form>
          <a-button size="large" style="width: 100%" type="primary" @click="updatephone()">更换新的手机号码</a-button>
        </div>
      </a-col>
      <a-col :span="12">
        <div class="card">
          <div class="card-title">密码修改</div>
          <a-form layout="vertical">
            <a-form-item label="旧密码">
              <a-input placeholder="请输入旧密码" size="large" type="password"  v-model:value="password.oldpassword"></a-input>
            </a-form-item>
            <a-form-item label="新密码">
              <a-input placeholder="请输入新密码" size="large" type="password"  v-model:value="password.newpassword"></a-input>
            </a-form-item>
            <a-form-item label="再次输入新密码">
              <a-input placeholder="请再次输入新密码" type="password" size="large" v-model:value="password.repeatpassword"></a-input>
            </a-form-item>
            <a-button style="width: 100%" type="primary" size="large" @click="updatePassword">修改密码</a-button>
          </a-form>
        </div>
      </a-col>

    </a-row>
  </div>
</template>

<script>
import {reactive,ref} from "vue";
import axios from "../../axios/axios";
import {message} from "ant-design-vue"
import qs from "qs"
import store from "../../store/store.js"
import {useRouter} from "vue-router"
export default {
  name: "UserCenterSetting",
  setup() {
    const router = useRouter()
    const phone = reactive({
      oldphone:undefined,
      newphone:undefined,
      verifyCode:undefined,
      password:undefined
    })
    const password = reactive({
      oldpassword:undefined,
      newpassword:undefined,
      repeatpassword:undefined
    })
    const user = reactive({
      avatar:undefined,
      nickname:undefined,
      isAdmin:undefined,
      mobileNumber:undefined,
      username:undefined,
      isVip:undefined,
      vipExpiredTime:undefined,
      vipPackageId:undefined,
      vipPackageName:undefined
    })
    const init = () => {
      axios.get("/do/user/getuserinfo.do").then(res=>{
        user.nickname = res.data.data.nickname
        user.isAdmin = res.data.data.isAdmin
        user.mobileNumber = res.data.data.mobileNumber
        user.username = res.data.data.username
        user.isVip = res.data.data.isVip
        user.vipExpiredTime = res.data.data.vipExpiredTime
        user.vipPackageId = res.data.data.vipPackageId
        user.vipPackageName = res.data.data.vipPackageName
        user.avatar = res.data.data.avatar
      })
    }
    init()

    const updatePassword = ()=>{
      if (password.oldpassword === undefined || password.oldpassword === ""){
        message.warn("旧密码输入不能为空")
        return false;
      }

      if (password.repeatpassword === undefined || password.repeatpassword === "" || password.newpassword === "" || password.newpassword === undefined){
        message.warn("密码输入不能为空")
        return false;
      }

      if (password.oldpassword === password.newpassword){
        message.warn("新旧密码不能一样")
        return false
      }

      if (password.repeatpassword !== password.newpassword){
        message.warn("两次输入密码不一致")
        return false
      }

      axios.post("/do/user/updatepassword.do",qs.stringify({
        "oldpassword":password.oldpassword,
        "newpassword":password.newpassword
      })).then(res=>{
        if (res.data.code != 200){
          message.error(res.data.data)
          return false;
        }
        message.success(res.data.data)
        setTimeout(()=>{
          window.location.href = "/"
          window.localStorage.removeItem("token")
          window.sessionStorage.removeItem("token")
        },1500)
      })
    }
    const verifyTime = ref(0)
    let interval
    const func = ()=>{
      verifyTime.value--
      if (verifyTime.value<=0){
        clearInterval(interval)
      }
    }
    const sendVerifyCode = ()=>{
      axios.get("/do/user/sendresetphoneverifycode.do/" + phone.newphone).then(res=>{
        if (res.data.code != 200){
          message.error(res.data.data)
          return false;
        }
        message.success(res.data.data)
        verifyTime.value = 120
        interval = setInterval(()=>{
          func()
        },1000)
      })
    }

    const updatephone = ()=>{
      if (phone.verifyCode == "" || phone.verifyCode == undefined){
        message.warn("请填写验证码")
        return false
      }
      if (phone.newphone == "" || phone.newphone == undefined){
        message.warn("请填写新的手机号")
        return false
      }
      if (phone.oldphone == "" || phone.oldphone == undefined){
        message.warn("请填写旧的手机号")
        return false
      }
      if (phone.password == "" || phone.password == undefined){
        message.warn("请填写登录密码")
        return false
      }
      axios.post("/do/user/resetphonenumber.do",qs.stringify({
        oldNumber:phone.oldphone,
        newNumber:phone.newphone,
        verifyCode:phone.verifyCode,
        password:phone.password
      })).then(res=>{
        if(res.data.code != 200){
          message.error(res.data.data)
          return false
        }
        message.success(res.data.data)
        window.localStorage.removeItem("token")
        window.sessionStorage.removeItem("token")
        store.commit("user/logout")
        router.push({name:"Login"})
      })
    }
    return{
      user,
      password,
      updatePassword,
      sendVerifyCode,
      phone,
      verifyTime,
      updatephone
    }
  }
}
</script>
<style scoped lang="scss">
.card {
  padding: 20px;
  box-shadow: 0 2px 10px 0 rgb(46 61 72 / 10%);
  border-radius: 8px;
  margin-bottom: 20px;

  .card-title {
    font-size: 16px;
    margin-bottom: 10px;
  }
}

.mobile-number{
  margin-bottom: 20px;
  color: #6b7280;
  span{
    color: #07111b;
    font-size: 16px;
    font-weight: 500;
  }
}

.v-code{
  display: flex;
  input{
    margin-right: 20px;
  }
}
</style>