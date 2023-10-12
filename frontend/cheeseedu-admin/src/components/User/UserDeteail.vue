<template>
  <div>
    <a-descriptions title="用户信息" bordered v-if="user.id != undefined">
      <a-descriptions-item label="用户名" :span="1">{{user.username}}</a-descriptions-item>
      <a-descriptions-item label="用户ID" :span="1">{{user.id}}</a-descriptions-item>
      <a-descriptions-item label="昵称" :span="1">
        <a-input v-model:value="user.nickname"/>
      </a-descriptions-item>
      <a-descriptions-item label="用户状态">
        <a-badge v-if="user.isLock==0" status="success" />
        <a-badge v-else status="error" />
        <a-select v-model:value="user.isLock">
          <a-select-option :value="0">正常</a-select-option>
          <a-select-option :value="1">封禁</a-select-option>
        </a-select>
      </a-descriptions-item>
      <a-descriptions-item label="手机号码" :span="2">{{user.mobileNumber}}</a-descriptions-item>
      <a-descriptions-item label="头像地址" :span="3">
        <a-input v-model:value="user.avatar"/>
      </a-descriptions-item>
      <a-descriptions-item label="管理员">
        <a-select v-model:value="user.isAdmin">
          <a-select-option :value="0">否</a-select-option>
          <a-select-option :value="1">是</a-select-option>
        </a-select>
      </a-descriptions-item>
      <a-descriptions-item label="注册IP">
        {{user.regIp}}
      </a-descriptions-item>
      <a-descriptions-item label="注册地址">
        {{user.regArea}}
      </a-descriptions-item>
      <a-descriptions-item label="VIP会员">
        <span v-if="user.isVip == 1">是</span>
        <span v-else>否</span>
      </a-descriptions-item>
      <a-descriptions-item label="VIP到期时间">
        <span v-if="user.isVip == 1">{{user.vipExpiredTime}}</span>
        <span v-else>X</span>
      </a-descriptions-item>
      <a-descriptions-item label="VIP套餐">
        <span v-if="user.isVip == 1">{{user.vipPackageName}}</span>
        <span v-else>X</span>
      </a-descriptions-item>
    </a-descriptions>
    <a-button style="margin:20px 0px" type="primary" @click="updateUser">修改</a-button>
  </div>
  <a-descriptions title="设置VIP会员" bordered v-if="user.id != undefined">
    <a-descriptions-item label="选择套餐">
      <a-select style="width: 300px" v-model:value="vipPackageId">
        <a-select-option v-for="item in vipPackageList" :value="item.id">{{item.name}}</a-select-option>
      </a-select>
    </a-descriptions-item>
    <a-descriptions-item label="选择到期时间">
      <a-date-picker :disabled-date="disabledDate"  show-time placeholder="请选择到期日期" :locale="locale" v-model:value="vipExpireTime"/>
    </a-descriptions-item>
  </a-descriptions>
  <a-button type="primary" style="margin: 20px 0px" @click="updateVip">设置</a-button>
</template>

<script>
import axios from "../../axios/axios.js"
import locale from 'ant-design-vue/es/date-picker/locale/zh_CN';
import {reactive,ref} from "vue";
import moment from "moment";
import {message} from "ant-design-vue"
import qs from "qs"
export default {
  name: "UserDetail",
  props: {
    id: String
  },
  setup(props){
    const vipPackageId = ref(undefined);
    const vipExpireTime = ref(undefined)
    const vipPackageList = ref(undefined)
    const user = reactive({
      id:undefined,
      avatar:undefined,
      isAdmin:undefined,
      isLock:undefined,
      mobileNumber:undefined,
      nickname:undefined,
      regArea: undefined,
      regIp: undefined,
      username: undefined,
      vipExpiredTime: undefined,
      isVip: undefined,
      vipPackageId: undefined,
      vipPackageName: undefined
    })
    const init = ()=>{
      axios.get("/do/admin/getallvippackage.do").then(res=>{
        vipPackageList.value = res.data.data
      })
      axios.get("/do/admin/getuserbyid.do/" + props.id).then(res=>{
        user.id = res.data.data.id
        user.avatar = res.data.data.avatar
        user.isAdmin = res.data.data.isAdmin
        user.isLock = res.data.data.isLock
        user.mobileNumber = res.data.data.mobileNumber
        user.nickname = res.data.data.nickname
        user.regArea = res.data.data.regArea
        user.regIp = res.data.data.regIp
        user.username = res.data.data.username
        user.vipExpiredTime = res.data.data.vipExpiredTime
        user.vipPackageId = res.data.data.vipPackageId
        user.vipPackageName = res.data.data.vipPackageName
        user.isVip = res.data.data.isVip
      })
    }

    init()

    const disabledDate = current => {
      return current && current < moment().endOf('day');
    };

    const updateUser = ()=>{
      if (user.nickname == undefined || user.nickname == ""){
        message.warn("用户昵称不能为空")
        return false;
      }
      if (typeof user.isLock != "number"){
        message.warn("用户状态不能为空")
        return false;
      }
      if (user.avatar == undefined || user.avatar == ""){
        message.warn("用户头像地址不能为空")
        return false;
      }
      if (typeof user.isAdmin != "number"){
        message.warn("用户管理员状态不能为空")
        return false;
      }
      axios.post("/do/admin/updateuser.do",{
        id:props.id,
        nickname:user.nickname,
        isLock:user.isLock,
        avatar:user.avatar,
        isAdmin:user.isAdmin
      }).then(res=>{
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

    const updateVip = ()=>{
      axios.post("/do/admin/updateuservip.do",qs.stringify({
        "id":props.id,
        "expireTime":vipExpireTime.value.format("yyyy-MM-DD HH:mm:ss"),
        "vipPackageId":vipPackageId.value
      })).then(res=>{
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
      props,
      user,
      disabledDate,
      vipPackageId,
      vipExpireTime,
      locale,
      vipPackageList,
      updateUser,
      updateVip
    }
  }
}
</script>
<style scoped>

</style>