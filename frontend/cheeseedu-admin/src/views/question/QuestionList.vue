<template>
    <div class="app-container">
      <a-card>
        <div style="display: flex;width: 300px">
          <a-input style="margin-right: 10px" placeholder="问题ID" v-model:value="questionId"/>
          <a-button type="primary" @click="searchQuestion">搜索</a-button>
        </div>
      </a-card>

      <a-card style="margin-top: 20px">
        <a-table :pagination="paginationOpt" row-key="id" :dataSource="dataList" :columns="columns" :locale="{emptyText: '暂无数据'}">
          <template #image="{text}">
            <a-image v-for="item in text" :src="item" style="width: 50px;height: 50px"></a-image>
          </template>
          <template #status="{text}">
            <a-tag color="success" v-if="text==1">已解决</a-tag>
            <a-tag color="default" v-else>未解决</a-tag>
          </template>
          <template #action="{text,record}">
            <a-dropdown>
              <template #overlay>
                <a-menu>
                  <a-menu-item key="1" v-if="record.isSolved == 0" @click="updateQuestionSolved(record.id)">设置为已解决</a-menu-item>
                  <a-menu-item key="2" @click="openReplyQuestionModal(text)">查看回答</a-menu-item>
                  <a-popconfirm
                      title="你确定要删除这条提问吗"
                      ok-text="确认"
                      cancel-text="取消"
                      @confirm="confirmDelete(record.id)"
                  >
                  <a-menu-item key="3">删除提问</a-menu-item>
                  </a-popconfirm>
                </a-menu>
              </template>
              <a-button type="primary">
                操作
                <DownOutlined />
              </a-button>
            </a-dropdown>
          </template>
        </a-table>
      </a-card>
      <a-modal v-model:visible="showQuestionReply" title="用户回答" :footer="false" width="900px" :destroy-on-close="true">
        <QuestionReply :question-id="replyQuestionId"/>
      </a-modal>
    </div>
</template>

<script>
import axios from "../../axios/axios.js"
import qs from "qs"
import {ref,reactive} from "vue";
import {message} from "ant-design-vue";
import {DownOutlined} from "@ant-design/icons-vue"
import QuestionReply from "../../components/question/QuestionReply.vue";
export default{
  name:"QuestionList",
  components:{
    DownOutlined,
    QuestionReply
  },
  setup(){
    const columns = [
      {
        title: '用户名',
        dataIndex: 'userName',
        key: 'name',
        width:200
      },
      {
        title: '标题',
        dataIndex: 'title',
        key: 'age',
      },
      {
        title: '图片',
        dataIndex: 'images',
        key: 'images',
        slots: {customRender: "image"}
      },
      {
        title: '浏览量',
        dataIndex: 'viewCount',
        key: 'address',
        width: 100
      },
      {
        title: '回答',
        dataIndex: 'answerCount',
        key: 'answerCount',
        width: 100
      },
      {
        title: '状态',
        dataIndex: 'isSolved',
        key: 'isSolved',
        slots: {customRender: "status"}
      },
      {
        title: '发起时间',
        dataIndex: 'createdTime',
        key: 'createdTime',
      },
      {
        title: '操作',
        dataIndex:'id',
        key: 'action',
        slots: {customRender: "action"}
      },
    ]

    const paginationOpt = reactive({
      defaultCurrent: 1, // 默认当前页数
      defaultPageSize: 10, // 默认当前页显示数据的大小
      total: 0, // 总数，必须先有
      showSizeChanger: false,
      showQuickJumper: true,
      showTotal: (total) => `共 ${total} 条`, // 显示总数
      onChange: (current, size) => {
        paginationOpt.defaultCurrent = current;
        paginationOpt.defaultPageSize = size;
        axios.post("/do/admin/getallquestion.do",qs.stringify({
          page:current,
          count:size
        })).then(res=>{
          if (res.data.code != "200"){
            message.error(res.data.data)
            return false;
          }
          dataList.value = res.data.data.records
          paginationOpt.total = res.data.data.total
          paginationOpt.defaultPageSize = res.data.data.size
          paginationOpt.defaultCurrent = res.data.data.current

          const temp = res.data.data.records
          const tempArray = []
          temp.forEach(elm=>{
            let t = elm.images
            elm.images = JSON.parse(t)
            tempArray.push(elm)
          })

          dataList.value = tempArray
        })
      },
    })

    const dataList = ref([])
    const init = ()=>{
      axios.post("/do/admin/getallquestion.do",qs.stringify({
        count:paginationOpt.defaultPageSize,
        page:paginationOpt.defaultCurrent
      })).then(res=>{
        paginationOpt.total = res.data.data.total
        paginationOpt.defaultPageSize = res.data.data.size
        paginationOpt.defaultCurrent = res.data.data.current

        const temp = res.data.data.records
        const tempArray = []
        temp.forEach(elm=>{
          let t = elm.images
          elm.images = JSON.parse(t)
          tempArray.push(elm)
        })

        dataList.value = tempArray
        console.log(dataList.value)
      })
    }

    const questionId = ref(undefined)
    const searchQuestion = ()=>{
      if (questionId.value == undefined || questionId.value == ""){
        message.warn("问题ID不能为空")
        return false;
      }
      axios.get("/do/admin/getquestionbyid.do/" + questionId.value).then(res=>{
        if (res.data.data == undefined || res.data.data == null){
          message.error("找不到对应的问题")
          return false;
        }

        const temp = []
        temp.push(res.data.data)
        dataList.value = temp
      })
    }

    init()

    const showQuestionReply = ref(false)
    const replyQuestionId = ref(undefined)
    const openReplyQuestionModal = (id)=>{
      showQuestionReply.value = true
      replyQuestionId.value = id
    }

    const updateQuestionSolved = (id)=>{
      axios.get("/do/admin/updatequestionsolved.do/" + id).then(res=>{
        if (res.data.code == 200){
          message.success(res.data.data)
          setTimeout(()=>{
            location.reload()
          },1500)
        }
      })
    }

    const confirmDelete = (id)=>{
      axios.get("/do/admin/deleteQuestion.do/" + id).then(res=>{
        if (res.data.code == 200){
          message.success(res.data.data)
          setTimeout(()=>{
            location.reload()
          },1500)
        }
      })
    }
    return{
      columns,
      dataList,
      paginationOpt,
      questionId,
      searchQuestion,
      showQuestionReply,
      replyQuestionId,
      openReplyQuestionModal,
      updateQuestionSolved,
      confirmDelete
    }
  }
}
</script>
<style scoped>

</style>