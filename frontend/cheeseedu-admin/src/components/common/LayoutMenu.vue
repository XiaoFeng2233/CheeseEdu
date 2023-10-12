<template>
  <div class="menu">
    <h1 class="menu-title">管理后台</h1>
    <a-menu
        id="dddddd"
        theme="dark"
        style="width: 250px"
        v-model:selectedKeys="selectedKey"
        mode="inline"
        @click="handleClick"
    >
      <a-menu-item key="Home">
        <i class="iconfont ico icon-shouye"></i>
        <span>主页</span>
      </a-menu-item>
      <a-menu-item key="Order">
        <i class="iconfont ico icon-dingdan"></i>
        <span>订单</span>
      </a-menu-item>
      <a-menu-item key="Log">
        <i class="iconfont ico icon-shijian"></i>
        <span>登录日志</span>
      </a-menu-item>
      <a-menu-item key="Banner">
        <i class="iconfont ico icon-lunbo"></i>
        <span>轮播图</span>
      </a-menu-item>


      <a-sub-menu key="course">
        <template #icon>
          <i class="iconfont icon-shixijiuye"></i>
        </template>
        <template #title>课程管理</template>
        <a-menu-item key="CourseList">课程列表</a-menu-item>
        <a-menu-item key="AddCourse">添加课程</a-menu-item>
        <a-menu-item key="CourseCategory">分类管理</a-menu-item>
        <a-menu-item key="CourseCommentList">课程评论</a-menu-item>
      </a-sub-menu>

      <a-sub-menu key="question">
        <template #icon>
          <i class="iconfont icon-bangzhuzhongxin3"></i>
        </template>
        <template #title>提问管理</template>
        <a-menu-item key="QuestionList">问题列表</a-menu-item>
      </a-sub-menu>

      <a-sub-menu key="vip">
        <template #icon>
          <i class="iconfont icon-zudui"></i>
        </template>
        <template #title>会员配置</template>
        <a-menu-item key="Vip">会员种类</a-menu-item>
        <a-menu-item key="AddVip">添加种类</a-menu-item>
        <a-menu-item key="VipUser">会员用户</a-menu-item>
      </a-sub-menu>

      <a-sub-menu key="user">
        <template #icon>
          <i class="iconfont icon-zudui"></i>
        </template>
        <template #title>用户管理</template>
        <a-menu-item key="User">用户列表</a-menu-item>
        <a-menu-item key="AddUser">添加用户</a-menu-item>
      </a-sub-menu>

      <a-sub-menu key="article">
        <template #icon>
          <i class="iconfont icon-book2"></i>
        </template>
        <template #title>文章管理</template>
        <a-menu-item key="ArticleList">文章列表</a-menu-item>
        <a-menu-item key="AddArticle">添加文章</a-menu-item>
      </a-sub-menu>

      <a-sub-menu key="config">
        <template #icon>
          <i class="iconfont icon-shezhi"></i>
        </template>
        <template #title>系统设置</template>
        <a-menu-item key="GlobalConfig">全局配置</a-menu-item>
        <a-menu-item key="SmsConfig">短信配置</a-menu-item>
        <a-menu-item key="StorageConfig">存储配置</a-menu-item>
        <a-menu-item key="PaymentConfig">支付配置</a-menu-item>
      </a-sub-menu>


    </a-menu>
  </div>
</template>

<script>
import {ref,watch} from 'vue'
import router from "../../router/index.js"
import {useRoute} from "vue-router"
export default{
  name:"LayoutMenu",
  setup(){
    const selectedKey = ref(['']);
    const route = useRoute()

    watch(()=>route.name,(newval,oldval)=>{
      selectedKey.value = [newval]
    })

    const handleClick = (e) => {
      router.push({name:e.key})
    };

    return{
      selectedKey,handleClick
    }
  },
}
</script>
<style scoped lang="scss">
.iconfont{
  font-size: 20px !important;
}

.ico{
  margin-right: 10px;
}

.menu-title{
  color: white;
  text-align: center;
  margin: 10px auto;
  cursor:pointer;
}

.menu{
  color: red;
}

</style>