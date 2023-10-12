<template>
  <div class="app-container">
    <a-card title="对象存储设置" style="width: 50%">
      <a-form layout="vertical">
        <a-form-item label="存储平台">
          <a-select v-model:value="platform" @change="handleChange" placeholder="请选择存储平台">
            <a-select-option value="aliyun">
              阿里云
            </a-select-option>
            <a-select-option value="qcloud">
              腾讯云
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
      <a-form layout="vertical" v-if="platform == 'aliyun'" :rules="AliyunRules" ref="AliyunFormRef" :model="AliyunForm">
        <a-form-item label="AccessKeyId" name="aliyunAccessKeyId">
          <a-input v-model:value="AliyunForm.aliyunAccessKeyId"/>
        </a-form-item>
        <a-form-item label="AccessKeySecret" name="aliyunAccessKeySecret">
          <a-input v-model:value="AliyunForm.aliyunAccessKeySecret"/>
        </a-form-item>
        <a-form-item label="Bucket名称" name="aliyunBucket">
          <a-input v-model:value="AliyunForm.aliyunBucket"/>
        </a-form-item>
        <a-form-item label="Endpoint" name="aliyunEndpoint">
          <a-input v-model:value="AliyunForm.aliyunEndpoint"/>
        </a-form-item>
        <a-form-item :wrapper-col="{span:14,offset:6}">
          <a-button type="primary" @click="AliyunUpdate">保存</a-button>
        </a-form-item>
      </a-form>
      <a-form layout="vertical" v-if="platform == 'qcloud'" :rules="TencentRules" ref="TencentFormRef" :model="TencentForm">
        <a-form-item label="SecretId" name="qcloudSecretId">
          <a-input v-model:value="TencentForm.qcloudSecretId"/>
        </a-form-item>
        <a-form-item label="SecretKey" name="qcloudSecretKey">
          <a-input v-model:value="TencentForm.qcloudSecretKey"/>
        </a-form-item>
        <a-form-item label="Cos地域" name="qcloudCosRegion">
          <a-input v-model:value="TencentForm.qcloudCosRegion"/>
        </a-form-item>
        <a-form-item label="Bucket名称" name="qcloudBucket">
          <a-input v-model:value="TencentForm.qcloudBucket"/>
        </a-form-item>
        <a-form-item :wrapper-col="{span:24,}">
          <a-button type="primary" @click="TencentUpdate" style="width: 100%">保存</a-button>
        </a-form-item>
      </a-form>

    </a-card>
  </div>
</template>

<script>
import {ref, reactive} from "vue"
import axios from "../../axios/axios.js"
import {message} from "ant-design-vue";

export default {
  name: "StorageConfig",
  setup() {
    const platform = ref(null)
    const handleChange = (value) => {
      platform.value = value
    }

    const AliyunRules = {
      aliyunAccessKeyId: [
        {required: true, message: '请输入正确的AccessKeyId', trigger: 'blur'},
      ],
      aliyunAccessKeySecret: [
        {required: true, message: '请输入正确的AccessKeySecret', trigger: 'blur'},
      ],
      aliyunBucket: [
        {required: true, message: '请输入存储的名称', trigger: 'blur'},
      ],
      aliyunEndpoint: [
        {required: true, message: '请输入正确的Endpoint URL地址', trigger: 'blur', type: "url"},
      ],
    }

    const TencentRules = {
      qcloudSecretId:[
        {required: true, message: '请输入正确的SecretId', trigger: 'blur'},
      ],
      qcloudSecretKey:[
        {required: true, message: '请输入正确的SecretKey', trigger: 'blur'},
      ],
      qcloudCosRegion:[
        {required: true, message: '请输入正确的存储地域', trigger: 'blur'},
      ],
      qcloudBucket:[
        {required: true, message: '请输入存储的名称', trigger: 'blur'},
      ]
    }

    const AliyunFormRef = ref()
    const TencentFormRef = ref()

    const AliyunForm = reactive({
      aliyunAccessKeyId: "",
      aliyunAccessKeySecret: "",
      aliyunBucket: "",
      aliyunEndpoint: ""
    })

    const TencentForm = reactive({
      qcloudSecretId: "",
      qcloudSecretKey: "",
      qcloudCosRegion: "",
      qcloudBucket: ""
    })

    axios.get("/do/admin/config/getstorageconfig.do").then(res=>{
      if (res.data.data != null){
        platform.value = res.data.data.platform
        if (res.data.data.platform == "aliyun"){
          AliyunForm.aliyunAccessKeyId = res.data.data.aliyunAccessKeyId
          AliyunForm.aliyunAccessKeySecret = res.data.data.aliyunAccessKeySecret
          AliyunForm.aliyunBucket = res.data.data.aliyunBucket
          AliyunForm.aliyunEndpoint = res.data.data.aliyunEndpoint
        }else if (res.data.data.platform == "qcloud"){
          TencentForm.qcloudSecretId = res.data.data.qcloudSecretId
          TencentForm.qcloudSecretKey = res.data.data.qcloudSecretKey
          TencentForm.qcloudCosRegion = res.data.data.qcloudCosRegion
          TencentForm.qcloudBucket = res.data.data.qcloudBucket
        }else{
          return false;
        }
      }
    })

    const AliyunUpdate = ()=>{
      AliyunFormRef.value.validate().then(()=>{
        axios.post("/do/admin/config/updatestorageconfig.do",{
          platform:"aliyun",
          aliyunAccessKeyId:AliyunForm.aliyunAccessKeyId,
          aliyunAccessKeySecret:AliyunForm.aliyunAccessKeySecret,
          aliyunBucket:AliyunForm.aliyunBucket,
          aliyunEndpoint:AliyunForm.aliyunEndpoint
        }).then(res=>{
          if (res.data.code != "200"){
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

    const TencentUpdate = ()=>{
      TencentFormRef.value.validate().then(()=>{
        axios.post("/do/admin/config/updatestorageconfig.do",{
          platform:"qcloud",
          qcloudSecretId:TencentForm.qcloudSecretId,
          qcloudSecretKey:TencentForm.qcloudSecretKey,
          qcloudCosRegion:TencentForm.qcloudCosRegion,
          qcloudBucket:TencentForm.qcloudBucket,
        }).then(res=>{
          if (res.data.code != "200"){
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


    return {
      platform,
      handleChange,
      AliyunRules,
      TencentRules,
      AliyunFormRef,
      TencentFormRef,
      AliyunForm,
      TencentForm,
      AliyunUpdate,
      TencentUpdate
    }
  }
}
</script>
<style scoped>

</style>