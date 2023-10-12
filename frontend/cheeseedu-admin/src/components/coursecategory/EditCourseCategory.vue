<template>
    <a-form :rules="rules" ref="formRef" :label-col="{span:4}" :model="form">
      <a-form-item label="分类名称" name="name">
        <a-input v-model:value="form.name" placeholder="请输入分类的名称"/>
      </a-form-item>
      <a-form-item label="分类状态" name="status">
        <a-select v-model:value="form.status" placeholder="请选择分类的状态">
          <a-select-option :value="1">上架</a-select-option>
          <a-select-option :value="0">下架</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item :wrapper-col="{span:14,offset:4}">
        <a-button type="primary" @click="updateCategory">修改</a-button>
      </a-form-item>
    </a-form>
</template>

<script>
import {reactive,ref} from "vue";
import axios from "../../axios/axios.js"
import {message} from "ant-design-vue";

export default{
  name:"EditCourseCategory",
  props:{
    id:Number
  },
  setup(props){
    const rules = {
      name: [
        {
          required: true,
          message: '请输入课程分类的名称',
          trigger: 'blur',
        },
      ],
      status: [
        {
          required: true,
          message: '请输入课程分类的状态',
          trigger: 'blur',
          type:"integer"
        },
      ]
    }

     const form = reactive({
       name:undefined,
       status:undefined
     })


    axios.get("/do/admin/course/getcoursecategory.do/" + props.id).then(res=>{
      form.name = res.data.data.name
      form.status = res.data.data.status
    })

    const formRef = ref()

    const updateCategory = ()=>{
      formRef.value.validate().then(() => {
            axios.post("/do/admin/course/updatecoursecategory.do", {
              id:props.id,
              name:form.name,
              status:form.status
            }).then(res => {
              if (res.data.code != "200") {
                message.error(res.data.data)
                return false;
              }

              message.success(res.data.data)
              setTimeout(()=>{
                location.reload()
              },1500)
            })
          }
      ).catch(err => {
        return false;
      })
    }
    return{
      rules,
      form,
      formRef,
      updateCategory
    }
  }
}
</script>
<style scoped>

</style>