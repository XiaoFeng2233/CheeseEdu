<template>
  <div>
    <a-form layout="vertical" ref="formRef" :rules="rules" :model="form">
      <a-form-item label="章节名称" name="name">
        <a-input placeholder="请输入章节名称" v-model:value="form.name"/>
      </a-form-item>
      <a-form-item label="排序" name="index">
        <a-input-number placeholder="请输入排序" style="width: 100%" v-model:value="form.index"/>
      </a-form-item>
      <a-form-item>
        <a-button style="width: 100%" type="primary" @click="addChapter">添加</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import axios from "../../axios/axios.js"
import {reactive,ref} from "vue";
import {message} from "ant-design-vue"
export default {
  name:"AddCourseChapter",
  props: {
    courseId: Number
  },
  setup(props) {
    const form = reactive({
      name:undefined,
      index:0
    })
    const formRef = ref()
    const rules = {
      name:{
        required:true,
        message:"请输入章节的名称"
      },
      index:{
        required: true,
        message:"请输入正确的排序",
        type:"number"
      }
    }
    const addChapter = ()=>{
      formRef.value.validate().then(()=>{
        axios.post("/do/admin/course/addchapter.do",{
          courseId:props.courseId,
          indexNumber:form.index,
          name:form.name
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
      props,
      addChapter,
      rules,
      formRef,
      form
    }
  }
}
</script>
<style scoped>

</style>