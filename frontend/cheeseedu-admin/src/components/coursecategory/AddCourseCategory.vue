<template>
  <a-form ref="formRef" :label-col="{span:4}" :model="form" :rules="rules">
    <a-form-item label="分类名称" name="name">
      <a-input v-model:value="form.name" placeholder="请输入分类的名称"/>
    </a-form-item>
    <a-form-item label="分类状态" name="status">
      <a-select v-model:value="form.status" placeholder="请选择分类的状态">
        <a-select-option value="1">上架</a-select-option>
        <a-select-option value="0">下架</a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item :wrapper-col="{span:14,offset:4}">
      <a-button type="primary" @click="addCategory">添加</a-button>
    </a-form-item>
  </a-form>
</template>

<script>
import {reactive, ref} from "vue"
import axios from "../../axios/axios.js"
import {message} from "ant-design-vue"
export default {
  name: "AddCourseCategory",
  setup() {
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
        },
      ]
    }

    const formRef = ref()

    const form = reactive({
      name: undefined,
      status: "1"
    })

    const addCategory = () => {
      formRef.value.validate().then(() => {
        console.log(form.status)
        console.log(form.name)
            axios.post("/do/admin/course/addcoursecategory.do", {
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

    return {
      rules,
      formRef,
      form,
      addCategory
    }
  }
}
</script>
<style scoped>

</style>