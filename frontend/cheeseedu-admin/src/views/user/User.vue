<template>
  <div class="app-container">
    <a-card title="用户列表">
      <a-form layout="inline">
        <a-form-item label="搜索用户名/手机号">
          <a-input v-model:value="searchUsername"/>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="search">搜索</a-button>
        </a-form-item>
      </a-form>
      <a-table style="margin-top: 20px" :locale="{emptyText: '暂无数据'}" :columns="columns" :dataSource="userList" row-key="id" :pagination="paginationOpt">
        <template #avatar="{text}">
          <a-image
              style="border-radius: 999px"
              :width="50"
              :height="50"
              :src="text"
          />
        </template>
        <template #isvip="{text}">
          <a-tag color="success" v-if="text == 1">是</a-tag>
          <a-tag v-else>否</a-tag>
        </template>
        <template #isLock = "{text}">
          <a-tag color="red" v-if="text == 1">是</a-tag>
          <a-tag v-else>否</a-tag>
        </template>
        <template #action="{text}">
          <a-button style="margin-right: 20px" type="primary" @click="openModal(text)">编辑</a-button>
          <a-popconfirm
              title="确认要删除该用户吗?"
              ok-text="确认"
              cancel-text="取消"
              @confirm="confirmDelete(text)"
          >
          <a-button danger ghost>删除</a-button>
          </a-popconfirm>
        </template>
      </a-table>
    </a-card>
    <a-modal v-model:visible="modalVisible" :footer="false" :destroy-on-close="true" width="1000px">
      <UserDeteail :id="modalId" ></UserDeteail>
    </a-modal>
  </div>
</template>

<script>
import axios from "../../axios/axios.js"
import {reactive, ref} from "vue"
import qs from "qs";
import {message} from "ant-design-vue";
import UserDeteail from "../../components/User/UserDeteail.vue";
export default {
  name: "User",
  components:{
    UserDeteail
  },
  setup() {
    const columns = [
      {
        title: 'ID',
        dataIndex: 'id',
        key: 'id',
      },
      {
        title: '用户名',
        dataIndex: 'username',
        key: 'username',
      },
      {
        title: '头像',
        dataIndex: 'avatar',
        slots: {
          customRender: "avatar"
        }
      },
      {
        title: '昵称',
        dataIndex: 'nickname',
        key: 'nickname',
      },
      {
        title: '手机号',
        dataIndex: 'mobileNumber',
        key: 'mobileNumber',
      },
      {
        title: '注册时间',
        dataIndex: 'createdTime',
        key: 'createdTime',
      },
      {
        title: '注册地点',
        dataIndex: 'regArea',
        key: 'regArea',
      },
      {
        title: '是否Vip',
        dataIndex: 'isVip',
        slots:{
          customRender: "isvip"
        }
      },
      {
        title: '是否封禁',
        dataIndex: 'isLock',
        slots:{
          customRender: "isLock"
        }
      },
      {
        title: '操作',
        dataIndex: 'id',
        slots:{
          customRender:"action"
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

        getUserList(current,size)
      },
    })

    const userList =ref(undefined)

    const getUserList = (pagenumber,pagesize)=>{
      axios.get("/do/admin/getalluser.do/" + pagenumber + "/" + pagesize).then(res=>{
        userList.value = res.data.data.records
        paginationOpt.total = res.data.data.total
        paginationOpt.defaultPageSize = res.data.data.size
        paginationOpt.defaultCurrent = res.data.data.current
      })
    }
    getUserList(1,20)

    const searchUsername = ref(undefined)

    const search = ()=>{
      if (searchUsername.value === undefined || searchUsername.value === ""){
        message.warn("搜索内容不能为空")
        return false
      }

      axios.get("/do/admin/getuserbyusername.do/" + searchUsername.value).then(res=>{
        console.log(res)
        userList.value = res.data.data
      })
    }

    const modalVisible = ref(false)
    const modalId = ref(undefined)
    const openModal = (id)=>{
      modalId.value = id
      modalVisible.value = true
    }
    const confirmDelete  = (id)=>{
      axios.get("/do/admin/deleteuserbyid.do/" + id).then(res=>{
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
      columns,
      userList,
      paginationOpt,
      searchUsername,
      search,
      modalVisible,
      modalId,
      openModal,
      confirmDelete
    }
  }
}
</script>
<style scoped>

</style>