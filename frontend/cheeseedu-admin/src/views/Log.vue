<template>
    <div class="app-container">
      <a-card title="登录日志查询">
        <a-form
            layout="inline"
        >
          <a-form-item label="用户Id">
            <a-input v-model:value="userIdInput" placeholder="请输入用户Id查询">
              <template #prefix><UserOutlined style="color: rgba(0, 0, 0, 0.25)"/></template>
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-button
                @click="searchUserLog"
                type="primary"
                html-type="submit"
            >
              搜索
            </a-button>
          </a-form-item>
        </a-form>
        <a-table :pagination="paginationOpt" style="margin-top: 20px" :dataSource="data" :columns="column" :locale="{emptyText: '暂无数据'}" row-key="id"/>
      </a-card>
    </div>
</template>

<script>
import axios from "../axios/axios.js"
import qs from "qs"
import {computed,ref,reactive,watch} from "vue";
import store from "../store/store";
import {message} from "ant-design-vue";
import {UserOutlined} from "@ant-design/icons-vue"

export default{
  name:"Log",
  components:{
    UserOutlined
  },
  setup(){
    const column = [
      {
        title: '用户ID',
        dataIndex: 'userId',
        key: 'userId',
      },
      {
        title: '用户名',
        dataIndex: 'userName',
        key: 'userName',
      },
      {
        title: '登录ip',
        dataIndex: 'ip',
        key: 'ip',
      },
      {
        title: '登录地点',
        dataIndex: 'area',
        key: 'area',
      },
      {
        title: '登录时间',
        dataIndex: 'createdTime',
        key: 'createdTime',
      },
    ]

    const data = ref(undefined)

    const paginationOpt = reactive({
      defaultCurrent: 1, // 默认当前页数
      defaultPageSize: 20, // 默认当前页显示数据的大小
      total: 0, // 总数，必须先有
      showSizeChanger: false,
      showQuickJumper: true,
      showTotal: (total) => `共 ${total} 条`, // 显示总数
      onChange: (current, size) => {
        paginationOpt.defaultCurrent = current;
        paginationOpt.defaultPageSize = size;
        axios.post("/do/admin/getlog.do",qs.stringify({
          userId:userId.value,
          pageNumber:current,
          pageSize:size
        })).then(res=>{
          if (res.data.code != "200"){
            message.error(res.data.data)
            return false;
          }
          data.value = res.data.data.records
          paginationOpt.total = res.data.data.total
          paginationOpt.defaultPageSize = res.data.data.size
          paginationOpt.defaultCurrent = res.data.data.current
        })
      },
    })

    const userId = ref(store.state.user.userId)

    axios.post("/do/admin/getlog.do",qs.stringify({
      userId:userId.value,
      pageNumber:1,
      pageSize:20
    })).then(res=>{
      if (res.data.code != "200"){
        message.error(res.data.data)
        return false;
      }
      data.value = res.data.data.records
      paginationOpt.total = res.data.data.total
      paginationOpt.defaultPageSize = res.data.data.size
      paginationOpt.defaultCurrent = res.data.data.current
    })

    watch(()=>userId.value,()=>{
      axios.post("/do/admin/getlog.do",qs.stringify({
        userId:userId.value,
        pageNumber:1,
        pageSize:20
      })).then(res=>{
        if (res.data.code != "200"){
          message.error(res.data.data)
          return false;
        }
        data.value = res.data.data.records
        paginationOpt.total = res.data.data.total
        paginationOpt.defaultPageSize = res.data.data.size
        paginationOpt.defaultCurrent = res.data.data.current
      })
    })


    const userIdInput = ref()

    const searchUserLog = ()=>{
      userId.value  = userIdInput.value
    }

    return {
      userIdInput,
      column,
      data,
      userId,
      paginationOpt,
      searchUserLog
    }
  }
}
</script>
<style scoped>

</style>