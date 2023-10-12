<template>
    <div class="app-container">
      <a-card title="短信设置" style="width: 50%">
       <a-form :label-col="{span: 5}" :rules="rules" ref="duanxinbaoRef" :model="duanxinbao">
         <a-form-item label="短信平台">
           <a-select v-model:value="sms_plaform" style="width: 200px" placeholder="请选择短信平台">
             <a-select-option value="duanxinbao">
               短信宝
             </a-select-option>
           </a-select>
         </a-form-item>
         <a-form-item label="短信宝用户名" name="duanxinbaoUsername">
           <a-input v-model:value="duanxinbao.duanxinbaoUsername"/>
         </a-form-item>
         <a-form-item label="短信宝密码" name="duanxinbaoPassword">
           <a-input v-model:value="duanxinbao.duanxinbaoPassword"/>
         </a-form-item>
         <a-form-item label="短信前缀" name="duanxinbaoPrefix">
           <a-input v-model:value="duanxinbao.duanxinbaoPrefix"/>
         </a-form-item>
         <a-button @click="update" type="primary">更新</a-button>
       </a-form>
      </a-card>
    </div>
</template>

<script>
import axios from "../../axios/axios.js"
import {reactive, ref} from "vue"
import {message} from "ant-design-vue";
export default{
  name:"SmsConfig",
  setup(){
    const duanxinbaoRef = ref()
    const sms_plaform = ref("duanxinbao")
    const duanxinbao = reactive({
      duanxinbaoUsername:undefined,
      duanxinbaoPassword:undefined,
      duanxinbaoPrefix:undefined
    })
    const rules = {
      duanxinbaoUsername:{
        required: true,
        message: '短信宝用户名不能为空',
        trigger: 'blur',
      },
      duanxinbaoPassword:{
        required: true,
        message: '短信宝密码不能为空',
        trigger: 'blur',
      },
      duanxinbaoPrefix:{
        required: true,
        message: '短信宝前缀不能为空',
        trigger: 'blur',
      },
    }

    const init = ()=>{
      axios.get("/do/admin/config/getsmsconfig.do").then(res=>{
        duanxinbao.duanxinbaoPrefix = res.data.data.duanxinbaoPrefix
        duanxinbao.duanxinbaoUsername = res.data.data.duanxinbaoUsername
        duanxinbao.duanxinbaoPassword = res.data.data.duanxinbaoPassword
      })
    }
    init()
    const update = ()=>{
      duanxinbaoRef.value.validate().then(()=>{
        axios.post("/do/admin/config/updatesmsconfig.do",{
          smsPlatform:sms_plaform.value,
          duanxinbaoUsername:duanxinbao.duanxinbaoUsername,
          duanxinbaoPassword:duanxinbao.duanxinbaoPassword,
          duanxinbaoPrefix:duanxinbao.duanxinbaoPrefix
        }).then(res=>{
          if (res.data.code != 200){
            message.error(res.data.data)
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
    }
    return{
      sms_plaform,
      duanxinbaoRef,
      rules,
      duanxinbao,
      update
    }
  }
}
</script>
<style scoped>

</style>