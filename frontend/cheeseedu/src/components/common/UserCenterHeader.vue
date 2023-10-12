<template>
  <div class="header">
    <div class="container header-navbar">
      <div class="header-nav">
        <router-link :to="{name:'Home'}">网站首页</router-link>
        <router-link :to="{name:'CourseList'}">课程</router-link>
        <router-link :to="{name:'QuestionList'}">问答</router-link>
        <router-link :to="{name:'ArticleList'}">文章</router-link>
      </div>
      <div class="header-user">
        <router-link :to="{name:'UserCenterCourse'}">我的课程</router-link>
        <a-dropdown placement="bottomCenter">
          <a-avatar class="avatar" size="large" :src="user.avatar">{{user.nickName}}</a-avatar>
          <template #overlay>
            <a-menu>
              <a href="/admin">
                <a-menu-item v-if="user.isAdmin">
                  <CrownOutlined/>
                  管理员后台
                </a-menu-item>
              </a>
              <router-link :to="{name:'UserCenterHome'}">
                <a-menu-item>
                  <UserOutlined />
                  我的主页
                </a-menu-item>
              </router-link>
              <router-link :to="{name:'UserCenterCourse'}">
              <a-menu-item>
                <BookOutlined />
                我的课程
              </a-menu-item>
              </router-link>
              <router-link :to="{name:'UserCenterSetting'}">
              <a-menu-item>
                <LockOutlined />
                修改密码
              </a-menu-item>
              </router-link>
              <a-menu-item @click="logout">
                <LogoutOutlined />
                安全退出
              </a-menu-item>
            </a-menu>
          </template>
        </a-dropdown>
      </div>
    </div>
  </div>
</template>

<script>
import {CrownOutlined,UserOutlined,BookOutlined,LockOutlined,LogoutOutlined} from "@ant-design/icons-vue"
import Store from "../../store/store.js"
import {computed} from "vue";
import store from "../../store/store.js";
import {message} from "ant-design-vue";
import {useRouter} from "vue-router";

export default {
  name: "UserCenterHeader",
  components:{
    CrownOutlined,
    UserOutlined,
    BookOutlined,
    LockOutlined,
    LogoutOutlined
  },
  setup(){
    const router = useRouter();
    const user = computed(()=>{
      return Store.state.user
    })

    const logout = () => {
      store.commit("user/logout")
      window.localStorage.removeItem("token")
      window.sessionStorage.removeItem("token")
      message.success("账号退出成功")
      router.push({name:"Home"})
    }

    return{
      user,
      logout
    }
  }
}
</script>
<style scoped lang="scss">
.header {
  height: 60px;
  width: 100%;
  background-color: white;
  border-bottom: 1px solid #eaeaea;
}

.header-navbar {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;

  .header-nav {
    width: 250px;
    display: flex;
    justify-content: space-between;
    font-size: 16px;

    a {
      color: #545C63;
    }
  }
}

.header-user {
  a {
    font-size: 16px;
    color: #545c63;
    margin-right: 18px;
  }
  .avatar{
    cursor:pointer;
    transition: 0.1s;
    &:hover{
      border:3px solid #00c691;
    }
  }
}
</style>