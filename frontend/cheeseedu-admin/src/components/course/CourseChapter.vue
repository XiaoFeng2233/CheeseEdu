<template>
    <div>
      <a-button type="primary" @click="openAddChapterModal">添加新的章节</a-button>
      <div style="margin-top: 20px">
        <a-table :columns="columns" :locale="{emptyText: '暂无数据'}" :data-source="data" :pagination="false" row-key="id">
          <template #action="{text}">
            <a-button-group size="small">
              <a-button type="primary" @click="openEditChapterModal(text)">编辑</a-button>
              <a-button danger type="primary" @click="deleteChapter(text)">删除</a-button>
            </a-button-group>
          </template>
        </a-table>
      </div>
      <a-modal v-model:visible="addChapterVisible" title="添加章节" :footer="null" :destroy-on-close="true" :width="400">
        <AddCourseChapter :course-id="props.courseId"/>
      </a-modal>
      <a-modal v-model:visible="editChapterVisible" title="修改章节" :footer="null" :destroy-on-close="true" :width="400">
        <EditCourseChapter :chapter-id="chapterId"/>
      </a-modal>
    </div>
</template>

<script>
import {ref} from "vue"
import axios from "../../axios/axios.js";
import AddCourseChapter from "./AddCourseChapter.vue";
import EditCourseChapter from "./EditCourseChapter.vue";
import {Modal,message} from "ant-design-vue"
import { createVNode } from 'vue';
import { ExclamationCircleOutlined } from '@ant-design/icons-vue';

export default{
  name:"CourseChapter",
  components:{
    AddCourseChapter,
    EditCourseChapter
  },
  props:{
    courseId:Number
  },
  setup(props){
    const columns = [
      {
        title:"章节名称",
        dataIndex:"name",
        key:"name",
        width:300,
      },
      {
        title:"排序",
        dataIndex:"indexNumber",
        key:"indexNumber",
      },
      {
        title:"操作",
        dataIndex:"id",
        slots:{
          customRender:"action"
        }
      }
    ]

    const data = ref([])

    const init = ()=>{
      axios.get("/do/admin/course/getchapterbycourseid.do/" + props.courseId).then(res=>{
        data.value = res.data.data
      })
    }

    init()

    const addChapterVisible = ref(false)
    const courseId = ref(props.courseId)
    const openAddChapterModal = ()=>{
      addChapterVisible.value = true
    }

    const editChapterVisible = ref(false)
    const chapterId = ref(undefined)
    const openEditChapterModal = (id)=>{
      editChapterVisible.value = true
      chapterId.value = id
    }

    const deleteChapter = (id)=>{
      Modal.confirm({
        title: '删除提示',
        icon: createVNode(ExclamationCircleOutlined),
        content: '您确定要删除该章节吗?',
        okText: '确认',
        cancelText: '取消',
        onOk(){
          axios.get("/do/admin/course/deletechapter.do/" + id).then(res=>{
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
      });
    }
    return{
      props,
      columns,
      data,
      addChapterVisible,
      courseId,
      openAddChapterModal,
      editChapterVisible,
      chapterId,
      openEditChapterModal,
      deleteChapter
    }
  }
}
</script>
<style scoped>

</style>