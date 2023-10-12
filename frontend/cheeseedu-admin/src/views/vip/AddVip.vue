<template>
    <div class="app-container">
      <a-card title="添加会员套餐" style="width: 50%">
        <a-form :label-col="{span:4}" :model="form" ref="formRef" :rules="rules">
          <a-form-item label="套餐名称" name="name">
            <a-input v-model:value="form.name"/>
          </a-form-item>
          <a-form-item label="套餐时长" name="duration">
            <a-input addon-after="天" v-model:value="form.duration"/>
          </a-form-item>
          <a-form-item label="套餐价格" name="price">
            <a-input addon-before="RMB" v-model:value="form.price"/>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="addPackage">添加</a-button>
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
  name:"AddVip",
  setup(){

    const rules = {
      name:[
        {
          required: true,
          message: '请输入套餐的名称',
          trigger: 'blur',
        },
      ],
      duration:[
        {
          required: true,
          message: '请输入正确的套餐时长',
          trigger: 'blur',
          pattern:/(^[1-9]\d*$)/
        },
      ],
      price:[
        {
          required: true,
          message: '请输入正确的套餐价格',
          trigger: 'blur',
          pattern:/(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/
        },
      ]
    }

    const formRef =ref()
    const form = reactive({
      name:undefined,
      duration:undefined,
      price:undefined
    })

    const addPackage = ()=>{
      formRef.value.validate().then(()=>{
        axios.post("/do/admin/addvippackage.do",{
          name:form.name,
          duration:form.duration,
          price:form.price
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
    return{
      form,
      formRef,
      rules,
      addPackage
    }
  }
}
</script>
<style scoped>

</style>