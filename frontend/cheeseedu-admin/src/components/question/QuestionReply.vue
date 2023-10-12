<template>
  <div>
    <a-table :data-source="dataList" :columns="columns" :locale="{emptyText: '暂无数据'}" row-key="id">
      <template #action="{text}">
        <a-button danger @click="deleteReply(text)">删除</a-button>
      </template>
      <template #image="{text}">
        <a-image :src="item" v-for="item in text" style="width: 50px;height: 50px"></a-image>
      </template>
    </a-table>
  </div>
</template>

<script>
import {ref} from "vue"
import axios from "../../axios/axios.js"
import {message} from "ant-design-vue";
export default {
  name: "QuestionReply",
  props: {
    questionId: Number
  },
  setup(props) {
    const dataList = ref([])
    const columns = [
      {
        title: '用户名',
        dataIndex: 'userName',
        key: 'userName',
        width:100
      },
      {
        title: '内容',
        dataIndex: 'content',
        key: 'content',
        width:300
      },
      {
        title: '图片',
        dataIndex: 'images',
        key: 'images',
        slots:{
          customRender: "image"
        }
      },
      {
        title: '发布时间',
        dataIndex: 'createdTime',
        key: 'createdTime',
      },
      {
        title: '操作',
        dataIndex: 'id',
        key: 'id',
        width:100,
        slots:{
          customRender:"action"
        }
      },
    ]

    const init = ()=>{
      axios.get("/do/admin/getallreplybyquestionid.do/" + props.questionId).then(res=>{
        const temp = res.data.data
        temp.forEach(elm=>{
          elm.images = JSON.parse(elm.images)
          dataList.value.push(elm)
        })
      })
    }

    init()
    const deleteReply = (id)=>{
      axios.get("/do/admin/deletequestionreply.do/" + id).then(res=>{
        if (res.data.code == 200){
          message.success(res.data.data)
          setTimeout(()=>{
            location.reload()
          },1500)
        }
      })
    }
    return{
      dataList,
      columns,
      deleteReply
    }
  }
}
</script>
<style scoped>

</style>