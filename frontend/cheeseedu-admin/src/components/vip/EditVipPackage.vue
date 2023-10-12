<template>
  <div>
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
        <a-button type="primary" @click="editPackage">修改</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import axios from "../../axios/axios.js"
import {reactive, ref} from "vue";
import {message} from "ant-design-vue";
export default {
  name: "EditVipPackage",
  props: {
    id:Number
  },
  setup(props){
    const form = reactive({
      duration:undefined,
      name:undefined,
      id:undefined,
      price:undefined
    })
    const init = ()=>{
      axios.get("/do/admin/getvippackagebyid.do/" + props.id).then(res=>{
        if (res.data.data === null || res.data.data === undefined){
          message.error("后台数据获取失败")
          return false;
        }
        form.name = res.data.data.name;
        form.price = res.data.data.price;
        form.duration = res.data.data.duration;
        form.id = res.data.data.id;
      })
    }

    init()

    const formRef = ref()

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

    const editPackage = ()=>{
      formRef.value.validate().then(()=>{
        axios.post("/do/admin/updatevippackagebyid.do",{
          "name":form.name,
          "duration":form.duration,
          "id":props.id,
          "price":form.price
        }).then(res=>{
          if(res.data.code != 200){
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

    return {
      props,
      form,
      formRef,
      rules,
      editPackage
    }
  }
}
</script>
<style scoped>

</style>