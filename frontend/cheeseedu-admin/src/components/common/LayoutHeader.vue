<template>
    <div class="header">
      <a-breadcrumb>
        <a-breadcrumb-item>{{route.meta.title}}</a-breadcrumb-item>
      </a-breadcrumb>
      <a-dropdown placement="bottomLeft">
        <div class="hover-panel">
          <a-avatar :src="user.avatar" />
          <span style="margin: 0 5px 0 5px">{{user.nickName}}</span>
          <DownOutlined />
        </div>
        <template #overlay>
          <a-menu>
            <a-menu-item>
                <ClockCircleOutlined />
                <router-link :to="{name:'Log'}" style="color: #000000">
                  登录日志
                </router-link>
            </a-menu-item>
            <a-menu-item @click="logout">

                <LoginOutlined />
                安全退出

            </a-menu-item>
          </a-menu>
        </template>
      </a-dropdown>
    </div>
</template>

<script>
import {DownOutlined,LockOutlined,LoginOutlined,ClockCircleOutlined} from "@ant-design/icons-vue"
import {computed} from "vue";
import store from "../../store/store.js"
import {useRoute} from "vue-router"
import {watch} from "vue";

export default{
  name:"LayoutHeader",
  setup(){
    let user = computed(()=>store.state.user)
    const logout = ()=>{
      window.location.href = "/"
      window.localStorage.removeItem("token")
    }
    const route = useRoute();

    return {
      user,
      logout,
      route
    }
  },
  components:{
    DownOutlined,LockOutlined,LoginOutlined,ClockCircleOutlined
  }
}
</script>
<style scoped lang="scss">

  .header{
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: space-between;
    padding:5px 30px;
    align-items: center;
    background-color: white;
    box-shadow: 0px 10px 30px 0px rgb(82 63 105 / 8%)
  }

  .hover-panel{
    border-radius: 10px;
    cursor:pointer;
    padding: 10px;
    &:hover{
      background-color: #EEEEEE;
    }
  }
</style>