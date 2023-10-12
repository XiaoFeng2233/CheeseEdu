<template>
    <div>
      <a-button type="primary" style="margin-bottom: 10px" @click="openAddVideoModal">添加视频</a-button>
      <a-table :columns="tableColumn" :dataSource="data" :pagination="false" row-key="id">
        <template #duration="{text}">
          {{text}}秒
        </template>
        <template #action="{text}">
          <a-button-group size="small">
            <a-button type="primary" @click="openEditVideoModal(text)">编辑</a-button>
            <a-button type="primary" danger @click="deleteVideo(text)">删除</a-button>
          </a-button-group>
        </template>
      </a-table>

      <a-modal v-model:visible="addVideoVisible" title="添加视频" :footer="null" :destroy-on-close="true" :width="500">
        <AddVideo :course-id="courseId"/>
      </a-modal>

      <a-modal v-model:visible="editVideoVisible" title="编辑视频信息" :footer="null" :destroy-on-close="true" :width="500">
        <EditVideo :video-id="videoId"/>
      </a-modal>
    </div>
</template>
<script>

import {reactive, ref} from "vue";
import AddVideo from "./AddVideo.vue";
import EditVideo from "./EditVideo.vue";
import axios from "../../axios/axios.js";
import {message} from "ant-design-vue"
export default{
  name:"VideoList",
  components:{
    AddVideo,EditVideo
  },
  props:{
    courseId:Number
  },
  setup(props){
    const tableColumn = [

      {
        title:"视频标题",
        dataIndex:"title",
        key:"title"
      },
      {
        title:"时长",
        dataIndex:"videoDuration",
        slots:{
          customRender: "duration"
        }
      },
      {
        title:"章节名称",
        dataIndex:"chapterName",
        key:"chapterName"
      },
      {
        title:"ObjectName",
        dataIndex:"objectName",
        key:"objectName"
      },
      {
        title:"上架时间",
        dataIndex:"createdTime",
        key:"createdTime"
      },
      {
        title:"操作",
        dataIndex:"id",
        slots:{
          customRender:"action"
        }
      },
    ]

    const data = ref(undefined);

    const addVideoVisible = ref(false)

    const courseId = props.courseId


    const openAddVideoModal = ()=>{
      addVideoVisible.value = true
    }


    const init = ()=>{
      axios.get("/do/admin/course/getvideosbycourseid.do/" + props.courseId).then(res=>{
        data.value = res.data.data
      })
    }

    init()

    const editVideoVisible = ref(false)
    const videoId = ref(undefined)
    const openEditVideoModal = (id)=>{
      editVideoVisible.value = true;
      videoId.value = id
    }

    const deleteVideo = (id)=>{
      axios.get("/do/admin/course/deletevideo.do/" + id).then(res=>{
        if (res.data.code != 200){
          message.error(res.data.data)
          return false
        }
        message.success(res.data.data)
        setTimeout(()=>{
          location.reload()
        },1500)
      })
    }
    return{
      tableColumn,
      data,
      addVideoVisible,
      courseId,
      openAddVideoModal,
      editVideoVisible,
      videoId,
      openEditVideoModal,
      deleteVideo
    }
  }
}
</script>
<style scoped>

</style>