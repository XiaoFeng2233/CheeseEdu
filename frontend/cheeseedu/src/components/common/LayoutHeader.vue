<template>
  <div class="header">
    <div class="header-container">
      <div class="header-nav">
        <div class="header-nav-logo">
          <router-link :to="{name:'Home'}">
            <img :src="logo" alt="">
          </router-link>
        </div>
        <ul class="header-nav-item">
          <li class="nav-item" :class="{active:routeName == 'Home'}">
            <router-link :to="{name:'Home'}">
              首页
            </router-link>
          </li>
          <li class="nav-item" :class="{active:routeName == 'CourseList'}">
            <router-link :to="{name:'CourseList'}">
              课程
            </router-link>
          </li>
          <li class="nav-item" :class="{active:routeName == 'QuestionList'}">
            <router-link :to="{name:'QuestionList'}">
              问答
            </router-link>
          </li>
          <li class="nav-item" :class="{active:routeName == 'ArticleList'}">
            <router-link :to="{name:'ArticleList'}">文章</router-link>
          </li>
          <li class="nav-item">
            <div class="search-group">
              <input placeholder="输入你想搜索的" v-model="keyword"/>
              <button @click="search"><i class="iconfont icon-searchamagnifyingglass"></i></button>
            </div>
          </li>
        </ul>
      </div>
      <div class="header-action">
        <div class="vip">
          <router-link :to="{name:'Vip'}" class="vip-block">
            <i class="iconfont icon-vip"></i>
            <span>VIP会员</span>
          </router-link>
        </div>
        <a-dropdown v-if="isLogin">
          <div class="header-user">
            <a-avatar :src="user.avatar"/>
            <span>{{ user.nickName }}</span>
            <DownOutlined/>
          </div>
          <template #overlay>
            <a-menu>
              <a href="/admin" class="nav-link">
                <a-menu-item v-if="user.isAdmin" @click="jumpAdmin">
                  <CrownOutlined/>
                  管理员后台
                </a-menu-item>
              </a>
              <router-link :to="{name:'UserCenterHome'}" class="nav-link">
                <a-menu-item>
                  <UserOutlined/>
                  我的主页
                </a-menu-item>
              </router-link>
              <router-link :to="{name:'UserCenterCourse'}" class="nav-link">
                <a-menu-item>
                  <BookOutlined/>
                  我的课程
                </a-menu-item>
              </router-link>
              <router-link :to="{name:'UserCenterSetting'}" class="nav-link">
              <a-menu-item>
                <LockOutlined/>
                修改密码
              </a-menu-item>
              </router-link>
              <a-menu-item @click="logout">
                <LogoutOutlined/>
                安全退出
              </a-menu-item>
            </a-menu>
          </template>
        </a-dropdown>
        <div v-else>
          <router-link :to="{name:'Login'}">登录</router-link>
          <router-link :to="{name:'Register'}">注册</router-link>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import {
  DownOutlined,
  UserOutlined,
  LogoutOutlined,
  LockOutlined,
  BookOutlined,
  CrownOutlined
} from "@ant-design/icons-vue"
import store from "../../store/store.js";
import {ref, computed, watch} from "vue";
import {message} from "ant-design-vue";
import {useRouter, useRoute} from "vue-router"

export default {
  name: "LayoutHeader",
  components: {
    DownOutlined,
    UserOutlined,
    LogoutOutlined,
    LockOutlined,
    BookOutlined,
    CrownOutlined
  },
  setup() {
    const isLogin = ref(false);
    const token = store.state.user.token
    const user = computed(() => store.state.user)
    const router = useRouter()
    const route = useRoute();
    if (token !== undefined) {
      isLogin.value = true
    } else {
      isLogin.value = false
    }

    const jumpAdmin = () => {
      window.location.href = '/admin'
    }

    const logo = computed(() => {
      return store.state.siteconfig.siteLogo
    })

    const logout = () => {
      store.commit("user/logout")
      window.localStorage.removeItem("token")
      window.sessionStorage.removeItem("token")

      isLogin.value = false
      message.success("账号退出成功")
    }

    const routeName = computed(() => {
      return route.name
    })

    const keyword = ref(undefined)
    const search = ()=>{
      if (keyword.value == "" || keyword.value == undefined){
        message.warn("请输入想要搜索的关键词")
        return false;
      }
      router.push({name:"Search",params:{keyword:keyword.value}})
    }
    return {
      isLogin,
      user,
      logo,
      logout,
      jumpAdmin,
      routeName,
      keyword,
      search
    }
  }
}
</script>
<style scoped lang="scss">
.header {
  z-index: 999;
  background-color: white;
  box-shadow: 0 2px 8px #f0f1f2;
  position: sticky;
  top: 0;

  .header-container {
    max-width: 1200px;
    margin: 0 auto;
    display: flex;
    width: 100%;
    justify-content: space-between;
    align-items: center;
  }
}

.header-nav {
  display: flex;
  align-items: center;

  .header-nav-logo {
    padding: 20px 0;
    img {
      max-height: 50px;
    }
  }

  .header-nav-item {
    margin: 0 0 0 10px;
    padding: 0;
    list-style: none;
    display: flex;
    align-items: center;

    .nav-item {
      margin: 0 15px;
      padding: 20px 10px;

      &.active {
        border-top: 2px solid #00c691;

        a {
          color: #00c691;
        }
      }

      &:last-child {
        margin: 0;
      }

      a {
        color: rgba(0, 0, 0, .85);
        font-size: 16px;
      }
    }
  }
}

.header-action {
  display: flex;
  align-items: center;
  a {
    color: rgba(0, 0, 0, .85);
    font-size: 16px;
    padding: 0 10px;

    &:first-child {
      border-right: 2px solid #D9DDE1;
    }
  }
  .vip{
    .vip-block{
      border: none;
      margin-right: 20px;
      color: #e1a500;
      display: flex;
      flex-direction: column;
      align-items: center;
      i{
        font-size: 22px;
        line-height: 20px;
      }
    }
  }
}

.header-user {
  display: flex;
  align-items: center;
  cursor: pointer;

  span {
    margin-left: 5px;
    font-size: 18px;
  }
}

.nav-link {
  color: rgba(0, 0, 0, 0.85);
}

.search-group{
  position: relative;
  border-radius: 20px;
  height: 40px;
  border:1px solid #E5E7EBFF;
  width: 250px;
  padding: 10px 20px;
  display: flex;
  justify-content: start;
  align-items: center;
  input{
    background: none;
    border: none;
    outline: none;
    transition: 0.3s;
    width: 180px;

  }

  button{
    position: absolute;
    top: 5px;
    right: 20px;
    border-radius: 100%;
    outline: none;
    border: none;
    background: none;
    cursor:pointer;
    color: #00c691;
    font-weight: 700;
    transition: 0.3s;
    &:hover{
      background: #00c691;
      color: white;
    }
  }
}
</style>