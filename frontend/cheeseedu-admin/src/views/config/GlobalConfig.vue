<template>
    <div class="app-container">
      <a-card title="全局配置">
        <a-form ref="formRef" :label-col="labelCol" :model="form" :wrapper-col="wrapperCol" :rules="rules">
          <a-form-item label="网站名称" name="siteName">
            <a-input v-model:value="form.siteName"/>
          </a-form-item>
          <a-form-item label="网站关键词" name="siteKeyword">
            <a-input v-model:value="form.siteKeyword"/>
          </a-form-item>
          <a-form-item label="网站介绍" name="siteDescription">
            <a-input v-model:value="form.siteDescription"/>
          </a-form-item>
          <a-form-item label="网站Logo" name="siteLogo">
            <a-input v-model:value="form.siteLogo"/>
          </a-form-item>
          <a-form-item label="用户默认头像" name="defaultAvatar">
            <a-input v-model:value="form.defaultAvatar"/>
          </a-form-item>
          <a-form-item label="Favicon" name="siteIcon">
            <a-input v-model:value="form.siteIcon"/>
          </a-form-item>
          <a-form-item label="页脚HTML代码" name="siteFooter">
            <a-textarea
                :auto-size="{ minRows: 4, maxRows: 10}"
                placeholder="Autosize height based on content lines"
                v-model:value="form.siteFooter"
            />
          </a-form-item>
          <a-form-item label="用户协议内容" :wrapper-col="{span:12}">
            <MyEditor v-model:html="form.siteAgreement" v-model:text="text"/>
          </a-form-item>
          <a-form-item :wrapper-col="{ span: 12, offset:3}">
            <a-button type="primary" style="width: 100%" @click="updateConfig">保存</a-button>
          </a-form-item>
        </a-form>
      </a-card>

    </div>
</template>

<script>
import MyEditor from "../../components/common/MyEditor.vue";
import {reactive, ref} from "vue"
import axios from "../../axios/axios.js"
import {message} from "ant-design-vue"
export default {
  name: "GlobalConfig",
  setup() {
    const html = ref("")
    const text = ref("")

    const rules = {
      defaultAvatar: [
        { required: true, message: '请输入一个有效的默认头像图片地址', trigger: 'blur',type:"url" },
      ],
      siteDescription: [
        { required: true, message: '网站介绍不能为空', trigger: 'blur' },
      ],
      siteFooter: [
        { required: true, message: '页脚HTML代码不能为空', trigger: 'blur' },
      ],
      siteIcon: [
        { required: true, message: '请输入一个有效的ICO图片地址', trigger: 'blur',type:"url" },
      ],
      siteKeyword: [
        { required: true, message: '网站关键词不能为空', trigger: 'blur' },
      ],
      siteLogo: [
        { required: true, message: '请输入一个有效的LOGO图片地址', trigger: 'blur',type:"url" },
      ],
      siteName: [
        { required: true, message: '网站名称不能为空', trigger: 'blur' },
      ],
    }

    const formRef = ref()

    const form = reactive({
      defaultAvatar:"",
      siteAgreement:"",
      siteDescription:"",
      siteFooter:"",
      siteIcon: "",
      siteKeyword: "",
      siteLogo: "",
      siteName: "",
      id:0
    })

    axios.get("/do/admin/config/getglobalconfig.do").then(res=>{
      if (res.data.code != "200"){
        message.error(res.data.data)
        return false;
      }
      form.id = res.data.data.id
      form.defaultAvatar = res.data.data.defaultAvatar
      form.siteAgreement = res.data.data.siteAgreement
      form.siteDescription = res.data.data.siteDescription
      form.siteFooter = res.data.data.siteFooter
      form.siteIcon = res.data.data.siteIcon
      form.siteKeyword = res.data.data.siteKeyword
      form.siteLogo = res.data.data.siteLogo
      form.siteName = res.data.data.siteName

    })

    const updateConfig = ()=>{
      formRef.value.validate().then(()=>{
        if (text.value == undefined || text.value == ""){
          message.warn("用户协议内容不能为空")
          return false;
        }

        axios.post("/do/admin/config/updateglobalconfig.do",{
          id:form.id,
          siteName:form.siteName,
          siteIcon:form.siteIcon,
          siteAgreement:form.siteAgreement,
          siteFooter:form.siteFooter,
          siteDescription:form.siteDescription,
          siteKeyword:form.siteKeyword,
          defaultAvatar:form.defaultAvatar,
          siteLogo:form.siteLogo,
        }).then(res=>{
          if (res.data.code != "200"){
            message.error(res.data.data)
            return false;
          }

          message.success("修改成功")
          setTimeout(()=>{
            location.reload()
          },1500)
        })

      }).catch(err=>{
        return false;
      })
    }

    return {
      labelCol: {span: 3},
      wrapperCol: {
        span: 12
      },
      html,
      text,
      form,
      rules,
      formRef,
      updateConfig
    }
  },
  components:{
    MyEditor
  }
}

</script>
<style scoped lang="scss">

</style>