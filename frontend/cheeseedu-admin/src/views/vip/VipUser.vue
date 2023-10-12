<template>
    <div class="app-container">
      <a-card title="会员用户列表">
        <a-form layout="inline">
          <a-form-item label="搜索用户名/手机号">
            <a-input v-model:value="param"/>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="getUserByCondition">搜索</a-button>
          </a-form-item>
        </a-form>
        <a-table style="margin-top: 20px" :locale="{emptyText: '暂无数据'}" :columns="columns" :data-source="userList" row-key="id">
          <template #avatar="{text}">
            <a-image
                style="border-radius: 999px"
                :width="50"
                :height="50"
                :src="text"
            />
          </template>
        </a-table>
      </a-card>
    </div>
</template>

<script>
import axios from "../../axios/axios.js"
import {ref} from "vue";
import {message} from "ant-design-vue"
export default{
  name:"VipUser",
  setup(){
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
        title: '会员套餐',
        dataIndex: 'vipPackageName',
        key: 'vipPackageName',
      },
      {
        title: '会员到期时间',
        dataIndex: 'vipExpiredTime',
        key: 'vipExpiredTime',
      },
    ]


    const  userList = ref(undefined);

    const getUserList = (pagenumber,pagesize)=>{
      axios.get("/do/admin/getvipusers.do/" + pagenumber + "/" + pagesize).then(res=>{
        userList.value = res.data.data.records
      })
    }


    getUserList(1,20)

    const param = ref(undefined)

    const getUserByCondition = ()=>{
      if (param.value === undefined || param.value === ""){
        message.warn("搜索内容不能为空")
        return false
      }
      axios.get("/do/admin/getvipusersbyusernameormobilenumber.do/" + param.value).then(res=>{
        userList.value = res.data.data
      })
    }
    return{
      columns,
      userList,
      getUserByCondition,
      param
    }
  }
}
</script>
<style scoped>

</style>