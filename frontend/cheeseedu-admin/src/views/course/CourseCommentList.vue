<template>
    <div class="app-container">
      <a-card>
        <a-table :columns="column" row-key="id" :data-source="data">
          <template #star="{text}">
            <a-rate :value="text" allow-half/>
          </template>
          <template #action="{text}">
            <a-button @click="deleteComment(text)">删除</a-button>
          </template>
        </a-table>
      </a-card>
    </div>
</template>

<script>
import axios from "../../axios/axios.js"
import {ref,reactive} from "vue"
import qs from "qs"
import {message} from "ant-design-vue"
export default{
  name:"CourseCommentList",
  setup(){
    const data = ref(undefined)
    const column = [
      {
        title:"用户ID",
        dataIndex:"userId",
        key:"userId",
        width: 80
      },
      {
        title:"用户昵称",
        dataIndex:"nickName",
        key:"nickName",
        width: 150
      },
      {
        title:"课程名称",
        dataIndex:"courseTitle",
        key:"courseTitle",
        width: 200
      },
      {
        title:"评论内容",
        dataIndex:"content",
        key:"content",
      },
      {
        title:"评论等级",
        dataIndex:"star",
        key:"star",
        width: 200,
        slots:{
          customRender:"star"
        }
      },
      {
        title:"发布时间",
        dataIndex:"createdTime",
        key:"createdTime",
        width: 200
      },
      {
        title:"操作",
        dataIndex:"id",
        key:"id",
        width: 80,
        slots:{
          customRender: "action"
        }
      },
    ]

    const paginationOpt = reactive({
      defaultCurrent: 1, // 默认当前页数
      defaultPageSize: 20, // 默认当前页显示数据的大小
      total: 0, // 总数，必须先有
      showSizeChanger: false,
      showQuickJumper: false,
      showTotal: (total) => `共 ${total} 条`, // 显示总数
      onChange: (current, size) => {
        paginationOpt.defaultCurrent = current;
        paginationOpt.defaultPageSize = size;
        getCommentList(current,size)
      },
    })
    const getCommentList = (current,size)=>{
      axios.post("/do/admin/course/getcoursecommentlist.do",qs.stringify({
        page:current,
        count:size
      })).then(res=>{
        data.value = res.data.data.records
        paginationOpt.total = res.data.data.total
        paginationOpt.defaultPageSize = res.data.data.size
        paginationOpt.defaultCurrent = res.data.data.current
        console.log(res)
      })
    }

    getCommentList(paginationOpt.defaultCurrent,paginationOpt.defaultPageSize)

    const deleteComment = (id)=>{
      axios.get("/do/admin/course/deletecommentbyid.do/" + id).then(res=>{
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

    return{
      data,
      column,
      deleteComment
    }
  }
}
</script>
<style scoped>

</style>