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
          <a-select-option :value="item.id" v-for="item in chapterList">{{ item.name }}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="上传视频">
        <a-upload-dragger
            :multiple="false"
            :disabled="true"
        >
          <p class="ant-upload-drag-icon">
            <CloseOutlined/>
          </p>
          <p class="ant-upload-text">不支持修改视频的文件</p>
        </a-upload-dragger>
      </a-form-item>
      <a-form-item>
        <a-button style="width: 100%" type="primary" @click="update">
          <span>立即修改</span>
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import axios from "../../axios/axios.js"
import {CloseOutlined} from "@ant-design/icons-vue"
import {ref, reactive} from "vue"
import {message} from "ant-design-vue";
import qs from "qs"
export default {
  name: "EditVideo",
  components: {
    CloseOutlined
  },
  props: {
    videoId: Number,

  },
  setup(props) {
    const form = reactive({
      name: undefined,
      chapterId: undefined
    })

    const chapterList = ref(undefined)

    const init = () => {
      axios.get("/do/admin/course/getvideobyid.do/" + props.videoId).then(res => {
        form.name = res.data.data.title;
        form.chapterId = res.data.data.chapterId
        axios.get("/do/admin/course/getchapterbycourseid.do/" + res.data.data.courseId).then(res => {
          chapterList.value = res.data.data
        })
      })
    }

    const update = () => {
      if (form.chapterId == undefined || form.chapterId == "") {
        message.warn("请选择视频的章节")
        return false;
      }

      if (form.name == undefined || form.name == "") {
        message.warn("请输入视频的标题")
        return false;
      }
      axios.post("/do/admin/course/updatevideobyid.do", qs.stringify({
        id:props.videoId,
        title: form.name,
        chapterId: form.chapterId
      })).then(res => {
        if (res.data.code != "200") {
          message.error(res.data.data)
          return false;
        }
        message.success(res.data.data)
        setTimeout(() => {
          location.reload()
        }, 1500)
      })
    }

    init()
    return {
      props,
      form,
      chapterList,
      update
    }
  }
}
</script>
<style scoped>

</style>