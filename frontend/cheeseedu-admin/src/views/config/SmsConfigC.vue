<template>
    <div class="app-container">
      <a-card title="短信设置" style="width: 50%">
        <a-form-item label="短信平台">
          <a-select v-model:value="sms_plaform" style="width: 200px" placeholder="请选择短信平台">
<!--            <a-select-option value="aliyun">-->
<!--              阿里云-->
<!--            </a-select-option>-->
<!--            <a-select-option value="qcloud">-->
<!--              腾讯云-->
<!--            </a-select-option>   -->
            <a-select-option value="duanxinbao">
              短信宝
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form layout="vertical" ref="aliyunForm" :model="aliyun" :rules="aliyunRules" v-if="sms_plaform == 'aliyun'">
            <a-form-item label="AccessKeyId" name="aliyunAccessKeyId">
              <a-input v-model:value="aliyun.aliyunAccessKeyId"/>
            </a-form-item>
            <a-form-item label="AccessKeySecret" name="aliyunAccessKeySecret">
              <a-input v-model:value="aliyun.aliyunAccessKeySecret"/>
            </a-form-item>
            <a-form-item label="短信签名" name="aliyunSignName">
              <a-input v-model:value="aliyun.aliyunSignName"/>
            </a-form-item>
            <a-form-item label="注册短信模板ID" name="aliyunRegTemplateId">
              <a-input v-model:value="aliyun.aliyunRegTemplateId"/>
            </a-form-item>
            <a-form-item label="登录短信模板ID" name="aliyunLoginTemplateId">
              <a-input v-model:value="aliyun.aliyunLoginTemplateId"/>
            </a-form-item>
            <a-form-item label="忘记密码短信模板ID" name="aliyunForgetTemplateId">
              <a-input v-model:value="aliyun.aliyunForgetTemplateId"/>
            </a-form-item>
          <a-form-item label="重置手机号短信模板ID" name="aliyunResetTemplateId">
            <a-input v-model:value="aliyun.aliyunResetTemplateId"/>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="update">保存</a-button>
          </a-form-item>
        </a-form>

        <a-form layout="vertical" v-if="sms_plaform == 'qcloud'" ref="tencentForm" :rules="tencentRules" :model="tencent">
          <a-form-item label="SecretId" name="tencentSecretId">
            <a-input v-model:value="tencent.tencentSecretId"/>
          </a-form-item>
          <a-form-item label="SecretKey" name="tencentSecretKey">
            <a-input v-model:value="tencent.tencentSecretKey"/>
          </a-form-item>
          <a-form-item label="AppId" name="tencentSdkAppId">
            <a-input v-model:value="tencent.tencentSdkAppId"/>
          </a-form-item>
          <a-form-item label="短信地区" name="tencentRegion">
            <a-select style="width: 200px" :value="tencent.tencentRegion" @change="handleChange" placeholder="请选择短信平台">
              <a-select-option value="ap-beijing">
                北京
              </a-select-option>
              <a-select-option value="ap-guangzhou">
                广州
              </a-select-option>
              <a-select-option value="ap-nanjing">
                南京
              </a-select-option>
              <a-select-option value="ap-chengdu">
                成都
              </a-select-option>
              <a-select-option value="ap-chongqing">
                重庆
              </a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item label="短信签名" name="tencentSignName">
            <a-input v-model:value="tencent.tencentSignName"/>
          </a-form-item>
          <a-form-item label="注册短信模板ID" name="tencentRegTemplateId" >
            <a-input v-model:value="tencent.tencentRegTemplateId"/>
          </a-form-item>
          <a-form-item label="登录短信模板ID" name="tencentLoginTemplateId">
            <a-input v-model:value="tencent.tencentLoginTemplateId"/>
          </a-form-item>
          <a-form-item label="忘记密码短信模板ID" name="tencentForgetTemplateId">
            <a-input v-model:value="tencent.tencentForgetTemplateId"/>
          </a-form-item>
          <a-form-item label="重置手机号短信模板ID" name="tencentResetTemplateId">
            <a-input v-model:value="tencent.tencentResetTemplateId"/>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="update">保存</a-button>
          </a-form-item>
        </a-form>

      </a-card>
    </div>
</template>

<script>
import {ref,reactive} from "vue"
import axios from "../../axios/axios.js"
import {message} from "ant-design-vue";
export default{
  name:"SmsConfig",
  setup(){
    const aliyunForm = ref()
    const tencentForm = ref()
    const sms_plaform = ref(null)
    const tencent = reactive({
      tencentSecretId:undefined,
      tencentSecretKey:undefined,
      tencentSdkAppId:undefined,
      tencentRegTemplateId:undefined,
      tencentLoginTemplateId:undefined,
      tencentForgetTemplateId:undefined,
      tencentSignName:undefined,
      tencentRegion:undefined,
      tencentResetTemplateId:undefined
    })

    const aliyun = reactive({
      aliyunAccessKeyId:undefined,
      aliyunAccessKeySecret:undefined,
      aliyunSignName:undefined,
      aliyunRegTemplateId:undefined,
      aliyunLoginTemplateId:undefined,
      aliyunForgetTemplateId:undefined,
      aliyunResetTemplateId:undefined
    })


    const init = ()=>{
      axios.get("/do/admin/config/getsmsconfig.do").then(res=>{
        console.log(res)
        sms_plaform.value = res.data.data.smsPlatform;
        tencent.tencentForgetTemplateId = res.data.data.tencentForgetTemplateId;
        tencent.tencentLoginTemplateId = res.data.data.tencentLoginTemplateId;
        tencent.tencentRegTemplateId = res.data.data.tencentRegTemplateId;
        tencent.tencentRegion = res.data.data.tencentRegion;
        tencent.tencentResetTemplateId = res.data.data.tencentResetTemplateId;
        tencent.tencentSdkAppId = res.data.data.tencentSdkAppId;
        tencent.tencentSecretId = res.data.data.tencentSecretId;
        tencent.tencentSecretKey = res.data.data.tencentSecretKey;
        tencent.tencentSignName = res.data.data.tencentSignName;

        aliyun.aliyunAccessKeyId = res.data.data.aliyunAccessKeyId
        aliyun.aliyunAccessKeySecret = res.data.data.aliyunAccessKeySecret
        aliyun.aliyunForgetTemplateId = res.data.data.aliyunForgetTemplateId
        aliyun.aliyunLoginTemplateId = res.data.data.aliyunLoginTemplateId
        aliyun.aliyunRegTemplateId = res.data.data.aliyunRegTemplateId
        aliyun.aliyunResetTemplateId = res.data.data.aliyunResetTemplateId
        aliyun.aliyunSignName = res.data.data.aliyunSignName
      })
    }

    init()

    const handleChange = (value)=>{
      tencent.tencentRegion = value
    }

    const update = ()=>{
      if (sms_plaform.value == "aliyun"){
        aliyunForm.value.validate().then(()=>{
          axios.post("/do/admin/config/updatesmsconfig.do",{
            smsPlatform:"aliyun",
            aliyunAccessKeyId:aliyun.aliyunAccessKeyId,
            aliyunAccessKeySecret:aliyun.aliyunAccessKeySecret,
            aliyunSignName:aliyun.aliyunSignName,
            aliyunRegTemplateId:aliyun.aliyunRegTemplateId,
            aliyunLoginTemplateId:aliyun.aliyunLoginTemplateId,
            aliyunForgetTemplateId:aliyun.aliyunForgetTemplateId,
            aliyunResetTemplateId:aliyun.aliyunResetTemplateId,
          }).then(res=>{
            if (res.data.code != 200){
              return false
            }
            message.success(res.data.data)
            setTimeout(()=>{
              location.reload()
            },1500)
          })
        }).catch(err=>{
          return false;
        })
      }else if (sms_plaform.value=="qcloud"){
        tencentForm.value.validate().then(()=>{
          axios.post("/do/admin/config/updatesmsconfig.do",{
            smsPlatform:"qcloud",
            tencentSecretId:tencent.tencentSecretId,
            tencentSecretKey:tencent.tencentSecretKey,
            tencentSdkAppId:tencent.tencentSdkAppId,
            tencentRegTemplateId:tencent.tencentRegTemplateId,
            tencentLoginTemplateId:tencent.tencentLoginTemplateId,
            tencentForgetTemplateId:tencent.tencentForgetTemplateId,
            tencentSignName:tencent.tencentSignName,
            tencentRegion:tencent.tencentRegion,
            tencentResetTemplateId:tencent.tencentResetTemplateId,
          }).then(res=>{
            if (res.data.code != 200){
              return false
            }
            message.success(res.data.data)
            setTimeout(()=>{
              location.reload()
            },1500)
          })
        }).catch(err=>{
          return false;
        })
      }else{
        return false
      }
    }
    const aliyunRules = {
      aliyunAccessKeyId:{
        required:true,trigger:"blur",message:"该栏目不得为空"
      },
      aliyunAccessKeySecret:{
        required:true,trigger:"blur",message:"该栏目不得为空"
      },
      aliyunForgetTemplateId:{
        required:true,trigger:"blur",message:"该栏目不得为空"
      },
      aliyunLoginTemplateId:{
        required:true,trigger:"blur",message:"该栏目不得为空"
      },
      aliyunRegTemplateId:{
        required:true,trigger:"blur",message:"该栏目不得为空"
      },
      aliyunResetTemplateId:{
        required:true,trigger:"blur",message:"该栏目不得为空"
      },
      aliyunSignName:{
        required:true,trigger:"blur",message:"该栏目不得为空"
      },
    }

    const tencentRules = {
      tencentForgetTemplateId:{
        required:true,trigger:"blur",message:"该栏目不得为空"
      },
      tencentLoginTemplateId:{
        required:true,trigger:"blur",message:"该栏目不得为空"
      },
      tencentResetTemplateId:{
        required:true,trigger:"blur",message:"该栏目不得为空"
      },
      tencentRegTemplateId:{
        required:true,trigger:"blur",message:"该栏目不得为空"
      },
      tencentRegion:{
        required:true,trigger:"blur",message:"该栏目不得为空"
      },
      tencentSdkAppId:{
        required:true,trigger:"blur",message:"该栏目不得为空"
      },
      tencentSecretId:{
        required:true,trigger:"blur",message:"该栏目不得为空"
      },
      tencentSecretKey:{
        required:true,trigger:"blur",message:"该栏目不得为空"
      },
      tencentSignName:{
        required:true,trigger:"blur",message:"该栏目不得为空"
      },
    }
    return{
      sms_plaform,
      tencent,
      aliyun,
      aliyunRules,
      tencentRules,
      aliyunForm,
      tencentForm,
      update,
      handleChange
    }
  }
}
</script>
<style scoped>

</style>