<template>
    <div>
      <a-form layout="vertical">
        <a-form-item label="问题标题">
          <a-input placeholder="请填写问题的标题" v-model:value="form.title"/>
        </a-form-item>
        <a-form-item label="问题内容">
          <a-textarea
              v-model:value="form.content"
              placeholder="请输入问题的具体内容"
              :auto-size="{ minRows: 3, maxRows: 5 }"
          />
        </a-form-item>
        <a-form-item label="插入图片">
          <a-upload-dragger
              @change="handleChange"
              v-model:fileList="form.images"
              accept="image/gif,image/png,image/jpeg"
              :multiple="true"
              :before-upload="beforeUpload"
              :remove="handleRemove"
          >
            <p class="ant-upload-drag-icon">
              <CloudUploadOutlined />
            </p>
            <p class="ant-upload-hint">
              拖拽或点击上传图片
            </p>
          </a-upload-dragger>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" style="width: 100%" @click="createQuestion" :loading="uploading">
            发布问题
          </a-button>
        </a-form-item>
      </a-form>
    </div>
</template>

<script>
import {reactive, ref} from "vue";
import {CloudUploadOutlined} from "@ant-design/icons-vue"
import axios from "../../axios/axios.js"
import {message} from "ant-design-vue"
export default{
  name:"CreateQuestion",
  components:{
    CloudUploadOutlined
  },
  setup(){
    const form = reactive({
      title:undefined,
      content:undefined,
      images:[]
    })

    const handleRemove = file => {
      const index = form.images.indexOf(file);
      const newFileList = form.images.slice();
      newFileList.splice(index, 1);
      form.images = newFileList;
    };

    const handleChange = info =>{
      let resFileList = [...info.fileList];
      resFileList = resFileList.slice(-5);

      resFileList = resFileList.map(file => {
        if (file.response) {
          file.url = file.response.url;
        }
        return file;
      });
      form.images = resFileList;
    }

    const beforeUpload = file => {
      form.images = [form.images, file];
      return false;
    };

     const uploading = ref(false)

    const createQuestion = ()=>{
      if (form.content == "" || form.content == undefined){
        message.warn("问题内容不得为空")
        return false;
      }
      if (form.title == "" || form.title == undefined){
        message.warn("问题标题不得为空")
        return false;
      }
      let formData = new FormData();
      if (form.images.length == 0){
        formData.append("title",form.title)
        formData.append("content",form.content)
        uploading.value = true
        axios.post("/do/question/createquestion.do",formData).then(res=>{
          if (res.data.code != 200){
            message.error(res.data.data)
            uploading.value = false
            return false
          }
          message.success(res.data.data)
          setTimeout(()=>{
            location.reload()
          },1500)
          return false;
        })
      }else{
        form.images.forEach(elm=>{
          formData.append("file",elm.originFileObj);
        })
        formData.append("title",form.title)
        formData.append("content",form.content)
        uploading.value = true
        axios({
          url:"/do/question/createquestionwithimage.do",
          method:"POST",
          timeout:0,
          headers: { 'Content-Type': 'multipart/form-data' },
          data:formData
        }).then(res=>{
          if (res.data.code != 200){
            message.error(res.data.data)
            uploading.value = false
            return false
          }
          message.success(res.data.data)
          setTimeout(()=>{
            location.reload()
          },1500)
          return false;
        })
      }
    }

    return{
      form,
      createQuestion,
      handleRemove,
      handleChange,
      beforeUpload,
      uploading
    }
  }
}
</script>
<style scoped>

</style>