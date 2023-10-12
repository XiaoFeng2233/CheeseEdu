<template>
  <div>
    <div class="card">
      <div class="card-title">昵称修改</div>
      <div class="nickname">当前昵称 <span>{{ user.nickname }}</span></div>
      <div class="edit-nickname">
        <a-input v-model:value="newNickName" placeholder="请输入新的昵称" style="margin-right: 20px"/>
        <a-button type="primary" @click="changeNickName">修改</a-button>
      </div>
    </div>
    <div class="card">
      <div class="card-title">头像修改</div>
      <div class="avatar">
        当前头像 <img :src="user.avatar" alt="">
      </div>

      <a-upload-dragger
          style="margin-top: 20px"
          accept="image/gif,image/png,image/jpeg"
          :disabled="isDisable"
          name="file"
          :before-upload="beforeUpload"
          :multiple="false"

      >
        <p class="ant-upload-drag-icon">
          <CloudUploadOutlined/>
        </p>
        <p class="ant-upload-text">点击或拖拽图片上传</p>
        <p class="ant-upload-hint">
          支持PNG,JPG,GIF格式图片
        </p>
      </a-upload-dragger>
    </div>
  </div>
</template>

<script>
import {CloudUploadOutlined} from "@ant-design/icons-vue";
import axios from "../../axios/axios.js"
import {reactive, ref} from "vue";
import {message} from "ant-design-vue";
import store from "../../store/store.js";
import {useRoute, useRouter} from "vue-router"

export default {
  name: "Profile",
  components: {
    CloudUploadOutlined
  },
  setup() {
    const router = useRouter();
    const route = useRoute();
    const user = reactive({
      avatar: undefined,
      nickname: undefined,
      isAdmin: undefined,
      mobileNumber: undefined,
      username: undefined,
      isVip: undefined,
      vipExpiredTime: undefined,
      vipPackageId: undefined,
      vipPackageName: undefined
    })
    const init = () => {
      axios.get("/do/user/getuserinfo.do").then(res => {
        user.nickname = res.data.data.nickname
        user.isAdmin = res.data.data.isAdmin
        user.mobileNumber = res.data.data.mobileNumber
        user.username = res.data.data.username
        user.isVip = res.data.data.isVip
        user.vipExpiredTime = res.data.data.vipExpiredTime
        user.vipPackageId = res.data.data.vipPackageId
        user.vipPackageName = res.data.data.vipPackageName
        user.avatar = res.data.data.avatar
      })
    }
    init()

    const isDisable = ref(false)

    const beforeUpload = file => {
      isDisable.value = true;
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        message.error('图像大小不能超过2MB!');
        isDisable.value = false;
        return false
      }
      changeAvatar(file)
      return false;
    };

    const newNickName = ref(undefined);
    const changeNickName = () => {
      if (newNickName.value == undefined || newNickName.value == "") {
        message.warn("请输入新的昵称")
        return false;
      }
      axios.get("/do/user/changenickname.do/" + newNickName.value).then(res => {
        if (res.data.code == 200) {
          message.success("昵称更新成功,请重新登录~")
          store.commit("user/logout");
          window.localStorage.removeItem("token")
          window.sessionStorage.removeItem("token")
          setTimeout(() => {
            router.push({name: "Login"})
          }, 1500)
        }
      })
    }
    const changeAvatar = (file) => {
      const formData = new FormData();
      formData.append("file",file)
      axios({
        method:"POST",
        url:"/do/user/changeavatar.do",
        timeout:0,
        headers: { 'Content-Type': 'multipart/form-data' },
        data:formData
      }).then(res=>{
        if (res.data.code == 200){
          message.success("头像更新成功,请重新登录~")
          store.commit("user/logout");
          window.localStorage.removeItem("token")
          window.sessionStorage.removeItem("token")
          setTimeout(() => {
            router.push({name: "Login"})
          }, 1500)
        }
      })
      return false;
    }
    return {
      user,
      newNickName,
      changeNickName,
      isDisable,
      beforeUpload,
      changeAvatar
    }
  }
}
</script>
<style scoped lang="scss">
.card {
  padding: 20px;
  box-shadow: 0 2px 10px 0 rgb(46 61 72 / 10%);
  border-radius: 8px;
  margin-bottom: 20px;

  .card-title {
    font-size: 16px;
    margin-bottom: 10px;
  }
}

.nickname {
  font-size: 14px;
  color: rgb(107, 114, 128);
  margin-bottom: 10px;

  span {
    color: #07111b;
    font-size: 16px;
  }
}

.edit-nickname {
  display: flex;
}

.avatar {
  color: #6b7280;

  img {
    margin-left: 10px;
    width: 40px;
    height: 40px;
    border-radius: 50%;
  }
}
</style>