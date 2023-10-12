<template>
    <div class="app-container">
      <a-row :gutter="24">
        <a-col :span="12">
          <a-card title="支付宝配置 （暂不支持证书模式）">
            <a-form layout="vertical" :model="form" ref="formRef" :rules="rules">
              <a-form-item label="AppId" required name="AppId">
                <a-input placeholder="支付宝应用Id" v-model:value="form.AppId"/>
              </a-form-item>
              <a-form-item label="应用私钥" required name="merchantPrivateKey">
                <a-input placeholder="应用私钥" v-model:value="form.merchantPrivateKey"/>
              </a-form-item>
              <a-form-item label="支付宝公钥" required name="alipayPublicKey">
                <a-input placeholder="支付宝公钥" v-model:value="form.alipayPublicKey"/>
              </a-form-item>
<!--              <a-form-item label="AES密钥">-->
<!--                <a-input placeholder="应用私钥"/>-->
<!--              </a-form-item>-->
              <a-form-item label="回调网址(请填写一个可以外网访问的URL)" required name="notifyUrl">
                <a-input placeholder="例如:https://www.xxx.com" v-model:value="form.notifyUrl"/>
              </a-form-item>
              <a-form-item>
                <a-button type="primary" style="width: 100%" @click="saveConfig()">保存配置</a-button>
              </a-form-item>
            </a-form>
          </a-card>
        </a-col>
      </a-row>
    </div>
</template>

<script>
import {reactive, ref} from "vue";
import axios from "../../axios/axios.js"
import {message} from "ant-design-vue"
export default{
  name:"PaymentConfig",
  setup(){
    const form = reactive({
      AppId:undefined,
      merchantPrivateKey:undefined,
      alipayPublicKey:undefined,
      notifyUrl:undefined,
    })

    const formRef = ref();

    const rules = {
      AppId:{
        required:true,
        message:"请填写AppId",
        trigger:"blur"
      },
      merchantPrivateKey:{
        required: true,
        message:"请填写应用私钥",
        trigger:"blur"
      },
      alipayPublicKey:{
        required:true,
        message:"请填写支付宝公钥",
        trigger:"blur"
      },
      notifyUrl:{
        required:true,
        message:'请填写回调地址',
        type:"url",
        trigger:"blur"
      }
    }

    const init = ()=>{
      axios.get("/do/admin/config/getpaymentconfig.do").then(res=>{
        form.AppId = res.data.data.appId;
        form.merchantPrivateKey = res.data.data.merchantPrivateKey;
        form.notifyUrl = res.data.data.notifyUrl;
        form.alipayPublicKey = res.data.data.alipayPublicKey
      })
    }
    init()
    const saveConfig = ()=>{
      formRef.value.validate().then(()=>{
        axios.post("/do/admin/config/updatepaymentconfig.do",{
          "alipayPublicKey":form.alipayPublicKey,
          "notifyUrl":form.notifyUrl,
          "merchantPrivateKey":form.merchantPrivateKey,
          "appId":form.AppId
        }).then(res=>{
          if (res.data.code != 200){
            message.error(res.data.data)
            return false;
          }
          message.success(res.data.data)
          setTimeout(()=>{
            location.reload()
          },1500)
        })
      }).catch(err=>{
        return false;
      })
    }
    return{
      form,
      formRef,
      rules,
      saveConfig
    }
  }
}
</script>
<style scoped>

</style>