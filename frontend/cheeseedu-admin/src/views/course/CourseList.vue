<template>
    <div class="app-container">
      <a-card>
        <a-button @click="addCourse" type="primary">添加新的课程</a-button>
      </a-card>

      <a-card style="margin-top: 20px" title="课程管理">
        <a-table :locale="{emptyText: '暂无数据'}" :pagination="false" :columns="column" :data-source="data" :scroll="{ x: 1800, y: 500 }" row-key="id">
          <template #image="{text}" style="min-width: 300px">
            <a-image
                :width="200"
                :src="text"
            />
          </template>
          <template #description="{text}">
            <a-button @click="openDescriptionModal(text)">点击查看</a-button>
          </template>
          <template #type="{text}">
            <a-tag color="#87d068" v-if="text==0">免费</a-tag>
            <a-tag color="warning" v-else-if="text==1">会员免费</a-tag>
            <a-tag color="#2db7f5" v-else>收费</a-tag>
          </template>
          <template #status="{text}">
            <a-tag color="green" v-if="text== 1">上架</a-tag>
            <a-tag color="error" v-else>下架</a-tag>
          </template>
          <template #price="{text}">
            <strong style="margin-right: 5px">¥</strong>
            <strong>{{text}}</strong>
          </template>
          <template #action="{text}">
            <a-button-group size="small">
              <a-button type="primary" @click="openEditModal(text)">编辑</a-button>
              <a-button type="primary" @click="openChapterModal(text)">章节</a-button>
              <a-button type="primary" @click="openVideoListModal(text)">视频</a-button>
              <a-popconfirm
                  title="您确定要删除该课程吗?"
                  ok-text="确认"
                  cancel-text="取消"
                  @confirm="deleteCourse(text)"
              >
              <a-button danger type="primary">删除</a-button>
              </a-popconfirm>
            </a-button-group>
          </template>
        </a-table>
      </a-card>
      <a-modal v-model:visible="descriptionVisible" title="课程介绍" :footer="null" :destroy-on-close="true">
        <div v-html="description"></div>
      </a-modal>
      <a-modal v-model:visible="editVisible" title="编辑课程内容" :footer="null" :destroy-on-close="true" :width="700">
        <EditCourse :course-id="editCourseId"/>
      </a-modal>
      <a-modal v-model:visible="chapterVisible" :footer="null" :destroy-on-close="true" :width="700">
        <CourseChapter :course-id="chapterCourseId"/>
      </a-modal>
      <a-modal v-model:visible="videoListVisible" title="课程视频" :footer="null" :destroy-on-close="true" :width="1000">
        <VideoList :course-id="videoListCourseId"/>
      </a-modal>
    </div>
</template>

<script>
import {ref,reactive} from "vue"
import {message} from "ant-design-vue"
import axios from "../../axios/axios.js"
import {useRouter} from "vue-router"
import EditCourse from "../../components/course/EditCourse.vue";
import CourseChapter from "../../components/course/CourseChapter.vue";
import VideoList from "../../components/course/VideoList.vue";
export default{
  name:"CourseList",
  components:{
    EditCourse,
    CourseChapter,
    VideoList
  },
  setup(){
    const data = ref(undefined)

    const column = [
      {
        title:"编号",
        dataIndex:"id",
        key:"id",
        width: 80
      },
      {
        title:"标题",
        dataIndex:"title",
        key:"title"
      },
      {
        title: "课程封面",
        dataIndex: "image",
        key: "image",
        slots: {customRender: "image"},
        width: 250
      },
      {
        title:"课程介绍",
        dataIndex:"description",
        key:"description",
        slots: {customRender: "description"},
      },
      {
        title:"简短介绍",
        dataIndex:"shortDescription",
        key:"shortDescription"
      },
      {
        title:"课程分类",
        dataIndex:"categoryName",
        key:"categoryName",
      },
      {
        title:"课程类型",
        dataIndex:"type",
        key:"type",
        slots: {customRender: "type"},
        width: 100
      },
      {
        title:"课程状态",
        dataIndex:"status",
        key:"status",
        slots: {customRender: "status"},
      },
      {
        title:"售价",
        dataIndex:"price",
        key:"price",
        slots: {customRender: "price"},
        width:100
      },
      {
        title:"发布时间",
        dataIndex:"createdTime",
        key:"createdTime",
        width: 200
      },
      {
        title:"操作",
        dataIndex: 'id',
        slots: {customRender: "action"},
        width: 300
      },
    ]

    axios.get("/do/admin/course/getallcourse.do").then(res=>{
      if (res.data.code != "200"){
        message.error(res.data.data)
        return false;
      }

      data.value = res.data.data
    })

    const router = useRouter()

    const addCourse = ()=>{
      router.push({name:"AddCourse"})
    }

    const descriptionVisible = ref(false)
    const description = ref(undefined)
    const openDescriptionModal = (html)=>{
      descriptionVisible.value = true
      description.value = html
    }

    const editVisible = ref(false)
    const editCourseId = ref(undefined)
    const openEditModal = (id)=>{
      editCourseId.value = id
      editVisible.value = true
    }

    const chapterVisible = ref(false)
    const chapterCourseId = ref(undefined)
    const openChapterModal = (id)=>{
      chapterCourseId.value = id
      chapterVisible.value = true
    }

    const videoListVisible = ref(false)
    const videoListCourseId = ref(undefined)
    const openVideoListModal = (id)=>{
      videoListVisible.value = true
      videoListCourseId.value = id
    }
    const deleteCourse = (id)=>{
      axios.get("/do/admin/course/deletecourse.do" + id).then(res=>{
        if (res.data.code != 200){
          message.error(res.data.data)
          return false;
        }
        message.success(res.data.data)
        setTimeout(()=>{
          location.reload()
        },1500)
      })
    }
    return {
      data,
      column,
      addCourse,
      descriptionVisible,
      description,
      openDescriptionModal,
      editVisible,
      editCourseId,
      openEditModal,
      chapterVisible,
      chapterCourseId,
      openChapterModal,
      videoListVisible,
      videoListCourseId,
      openVideoListModal,
      deleteCourse
    }
  }
}
</script>
<style scoped>

</style>