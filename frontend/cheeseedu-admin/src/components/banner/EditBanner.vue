<template>
  <a-form ref="formRef" layout="horizontal" :model="form" :label-col="{span:4}" :rules="rules">
    <a-form-item label="轮播图ID">
      <a-input v-model:value="id" disabled/>
    </a-form-item>
    <a-form-item label="选择平台" name="platform">
      <a-select
          placeholder="请选择平台"
          ref="select"
          v-model:value="form.platform"
      >
        <a-select-option value="all">全部</a-select-option>
        <a-select-option value="mobile">移动端</a-select-option>
        <a-select-option value="pc">电脑端</a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item label="名称" name="name">
      <a-input v-model:value="form.name" placeholder="轮播图的名称"/>
    </a-form-item>
    <a-form-item label="排序" name="sort">
      <a-input-number v-model:value="form.sort" placeholder="输入排序数字,数字越大越靠前"/>
    </a-form-item>
    <a-form-item label="图片URL" name="imageUrl">
      <a-input v-model:value="form.imageUrl" placeholder="轮播图的图片地址"/>
    </a-form-item>
    <a-form-item label="跳转URL" name="url">
      <a-input v-model:value="form.url" placeholder="点击轮播图的跳转地址"/>
    </a-form-item>
    <a-form-item :wrapper-col="{span:14,offset:4}">
      <a-button type="primary" @click="updateBanner">保存修改</a-button>
    </a-form-item>
  </a-form>
</template>

<script>
import {reactive, ref, watch} from "vue"
import axios from "../../axios/axios.js"
import qs from "qs"
import {message} from "ant-design-vue";
export default {
  name: "EditBanner",
  props:{
    id:""
  },
  setup(props) {


    const rules = {
      name: [
        {
          required: true,
          message: '请输入轮播图的名称',
          trigger: 'blur',
        },
      ],
      imageUrl:[
        {
          required: true,
          message: '请输入轮播图的图片地址',
          trigger: 'blur',
        },
      ],
      url:[
        {
          required: true,
          message: '请输入跳转的地址',
          trigger: 'blur',
        },
      ],
      platform:[
        {
          required: true,
          message: '请选择平台',
          trigger: 'blur',
        },
      ],
      sort:[
        {
          required: true,
          message: '请输入正确的排序值',
          trigger: 'blur',
          type:"integer"
        },
      ]
    }

    const form = reactive({
      id:"",
      platform:"all",
      sort:"",
      imageUrl:"",
      url:"",
      name:""
    })

    axios.post("/do/admin/config/getbannerbyid.do",qs.stringify({
      id:props.id
    })).then((res)=>{
      if(res.data.code != "200"){
        message.error(res.data.data)
        return false;
      }

      const data = res.data.data
      form.url = data.url
      form.imageUrl = data.image
      form.name = data.name
      form.platform = data.platform
      form.sort = data.sort

    })

    const formRef = ref()

    const updateBanner = ()=>{
      formRef.value.validate().then(()=>{
        axios.post("/do/admin/config/updatebannerbyid.do",{
          id:props.id,
          url:form.url,
          image:form.imageUrl,
          name:form.name,
          platform:form.platform,
          sort:form.sort
        }).then((res)=>{
          if(res.data.code != "200"){
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
      form,
      formRef,
      rules,
      updateBanner
    }
  }
}
</script>
<style scoped>

</style>