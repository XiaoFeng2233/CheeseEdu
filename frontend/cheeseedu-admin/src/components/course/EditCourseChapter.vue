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
        <a-button style="width: 100%" type="primary" @click="updateChapter">修改</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import {reactive, ref} from "vue";
import axios from "../../axios/axios.js"
import {message} from "ant-design-vue";
export default {
  name: "EditCourseChapter",
  props: {
    chapterId: Number
  },
  setup(props){
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

    const init = ()=>{
      axios.get("/do/admin/course/getchapterbyid.do/" + props.chapterId).then(res=>{
        form.name = res.data.data.name
        form.index = res.data.data.indexNumber
      })
    }
    init()

    const updateChapter = ()=>{
      formRef.value.validate().then(()=>{
        axios.post("/do/admin/course/updatechapterbyid.do",{
          "name":form.name,
          "indexNumber":form.index,
          "id":props.chapterId
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
      rules,
      form,
      formRef,
      updateChapter
    }
  }
}
</script>
<style scoped>

</style>