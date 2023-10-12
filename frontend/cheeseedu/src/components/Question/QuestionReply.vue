<template>
  <div class="answer-card" v-if="isLogin">
    <div class="avatar">
      <img :src="avatar" alt="">
    </div>
    <div class="type-area">
      <a-input style="border-radius:5px " v-model:value="content" placeholder="请在此填写你的回答" size="large"/>
      <a-upload-dragger
          accept="image/gif,image/png,image/jpeg"
          style="width: 30%;margin-top: 20px"
          v-model:fileList="fileList"
          name="file"
          :before-upload="beforeUpload"
          :multiple="true"
          :remove="handleRemove"
          @change="handleChange"
      >
        <p class="ant-upload-drag-icon">
          <CloudUploadOutlined/>
        </p>
        <p class="ant-upload-hint">
          拖拽或点击上传图片
        </p>
      </a-upload-dragger>
    </div>
    <div class="action">
      <a-button style="width: 100%" type="primary" size="large" shape="round" @click="submit" :loading="uploading">发布回答</a-button>
    </div>
  </div>
</template>

<script>
import store from "../../store/store"
import jwt_decode from "jwt-decode";
import {useRouter} from "vue-router";
import {ref} from "vue";
import {CloudUploadOutlined} from "@ant-design/icons-vue"
import axios from "../../axios/axios.js"
import qs from "qs"
import {message} from "ant-design-vue"
export default {
  name: "QuestionReply",
  props: {
    questionId: String
  },
  components: {
    CloudUploadOutlined
  },
  setup(props) {
    const avatar = store.state.user.avatar;
    const isLogin = ref(false)
    let token;
    if(window.localStorage["token"] == undefined && window.sessionStorage["token"] == undefined){
      isLogin.value = false;
    }else{
      if(window.localStorage["token"] != undefined){
        token = window.localStorage["token"];
      }

      if(window.sessionStorage["token"] != undefined){
        token = window.sessionStorage["token"];
      }

      const decode = jwt_decode(token)
      if (decode.exp > Date.now() / 1000) {
        isLogin.value = true
      }
    }

    const content = ref(undefined)
    const fileList = ref([]);
    const uploading = ref(false);

    const handleRemove = file => {
      const index = fileList.value.indexOf(file);
      const newFileList = fileList.value.slice();
      newFileList.splice(index, 1);
      fileList.value = newFileList;
    };

    const handleChange = info =>{
      console.log(info)
      let resFileList = [...info.fileList];
      resFileList = resFileList.slice(-5);

      resFileList = resFileList.map(file => {
        if (file.response) {
          file.url = file.response.url;
        }
        return file;
      });
      fileList.value = resFileList;
    }

    const beforeUpload = file => {
      fileList.value = [...fileList.value, file];
      return false;
    };

    const handleUpload = () => {
      uploading.value = true
      const formData = new FormData();
      fileList.value.forEach((elm) =>{
        formData.append("files",elm.originFileObj)
      })
      formData.append("content",content.value)
      formData.append("questionId",props.questionId)
      axios({
        url:"/do/question/replyquestionwithimages.do",
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
      })
    }

    const submit = () => {
      const fileCount = fileList.value.length;
      if (content.value == "" || content.value == undefined){
        message.warn("请输入回答的内容")
        return false;
      }
      if (fileCount == 0){
        //仅回复文字
        axios.post("/do/question/replyquestion.do",qs.stringify({
          "content":content.value,
          "questionId":props.questionId
        })).then(res=>{
          if (res.data.code != 200){
            message.error(res.data.data)
            return false
          }
          message.success(res.data.data)
          setTimeout(()=>{
            location.reload()
          },1500)
        })
      }else{
        //回复文字和图片
        handleUpload()
      }
    }
    return {
      avatar,
      isLogin,
      handleRemove,
      fileList,
      beforeUpload,
      submit,
      content,
      handleChange,
      uploading
    }
  }
}
</script>
<style scoped lang="scss">
.answer-card {
  border: 1px solid #f0f0f0;
  background-color: white;
  padding: 20px;
  margin-top: 20px;
  display: flex;
  align-items: flex-start;

  .avatar {
    width: 50px;

    img {
      width: 100%;
      border-radius: 100%;
    }
  }

  .type-area {
    flex: 1;
    margin: 0 15px;
  }

  .action {
    width: 150px;
  }
}
</style>