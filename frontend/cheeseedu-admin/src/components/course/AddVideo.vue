<template>
  <div>
    <a-form layout="vertical">
      <a-form-item label="视频标题">
        <a-input placeholder="请输入视频标题" v-model:value="form.name"/>
      </a-form-item>
      <a-form-item label="课程章节">
        <a-select
            placeholder="请选择"
            ref="select"
            style="width: 100%"
            v-model:value="form.chapterId"
        >
          <a-select-option :value="item.id" v-for="item in chapterList">{{item.name}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="上传视频">
        <a-upload-dragger
            @change="handleChange"
            v-model:file-list="fileList"
            :multiple="false"
            accept="video/mp4,.flv,video/webm,video/x-msvideo"
            :remove="handleRemove"
            :before-upload="beforeUpload"
        >
          <p class="ant-upload-drag-icon">
            <inbox-outlined></inbox-outlined>
          </p>
          <p class="ant-upload-text">点击或拖拽文件到此处上传</p>
          <p class="ant-upload-hint">
            仅支持上传一个视频文件,支持MP4 AVI WEBM FLV
          </p>
        </a-upload-dragger>
      </a-form-item>
      <a-form-item>
        <a-button style="width: 100%" type="primary" @click="handleUpload" :disabled="uploading">
          <span v-if="!uploading">立即上传</span>
          <span v-else>视频文件上传中 {{progress}}</span>
        </a-button>
      </a-form-item>
    </a-form>

    <video :src="videoUrl" controls preload="auto" style="display: none" ref="videoRef"></video>
  </div>
</template>

<script>
import {InboxOutlined} from "@ant-design/icons-vue"
import {reactive, ref} from "vue";
import axios from "../../axios/axios.js"
import qs from "qs"
import {message} from "ant-design-vue";
export default {
  name: "AddVideo",
  components:{
    InboxOutlined
  },
  props:{
    courseId:Number,
  },
  setup(props) {
    const videoUrl = ref(undefined)
    const videoRef = ref()
    const form = reactive({
      name:undefined,
      chapterId:undefined
    })
    const fileList = ref([])
    let file = undefined;
    const uploading = ref(false);
    const progress = ref(undefined)
    const handleRemove = file => {
      console.log(file)
    }
    const beforeUpload = fileinfo => {
      file = fileinfo;
      let url = URL.createObjectURL(fileinfo)
      videoUrl.value = url
      return false;
    }

    const handleChange = info =>{
      let resFileList = [...info.fileList];
      resFileList = resFileList.slice(-1);

      resFileList = resFileList.map(file => {
        if (file.response) {
          // Component will show file.url as link
          file.url = file.response.url;
        }

        return file;
      });
      fileList.value = resFileList;
    }


    const handleUpload = ()=>{
      const formData = new FormData();
      formData.append("file",fileList.value[0])
      if (form.chapterId == undefined || form.chapterId == ""){
        message.warn("请选择视频的章节")
        return false;
      }

      if(form.name == undefined || form.name == ""){
        message.warn("请输入视频的标题")
        return false;
      }

      if(fileList.value.length == 0){
        message.warn("请选择要上传的视频文件")
        return false;
      }
      const videoDuration = videoRef.value.duration

      const fileName = fileList.value[0].name
      axios.post("/do/admin/course/getvideouploadurl.do",{
        fileName:fileName,
        videoDuration:videoDuration,
        title:form.name,
        courseId:props.courseId,
        chapterId:form.chapterId
      }).then(res=>{
        if (res.data.code != "200"){
          message.error('后台发生未知错误,请查看日志')
          return false;
        }

        const url = res.data.data.url
        const objName = res.data.data.objectName
        // const data = new FormData()
        // data.append("file",file)
        uploading.value = true
        axios({
          url:url,
          method:"put",
          data:file,
          headers:undefined,
          timeout:0,
          onUploadProgress:(progressEvent)=>{
            let percent = Math.floor((progressEvent.loaded / progressEvent.total) * 10000)/100;
            progress.value = percent + "%"
          }
        }).then(res=>{
          axios.post("/do/admin/course/videouploadsucess.do",qs.stringify({
            objName
          }))
          message.success("视频文件上传成功")
          message.success("视频添加成功")
          setTimeout(()=>{
            location.reload()
          },1500)
        })

      })

    }

    const chapterList = ref(undefined)

    const init = ()=>{
      axios.get("/do/admin/course/getchapterbycourseid.do/" + props.courseId).then(res=>{
        chapterList.value = res.data.data
      })
    }

    init()



    return{
      props,
      fileList,
      uploading,
      handleRemove,
      beforeUpload,
      handleUpload,
      handleChange,
      videoUrl,
      videoRef,
      form,
      chapterList,
      progress
    }
  }
}
</script>
<style scoped>

</style>