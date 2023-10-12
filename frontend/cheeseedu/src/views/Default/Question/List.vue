<template>
  <div class="container">
    <a-row :gutter="24" style="margin-top: 20px">
      <a-col :span="18">
        <a-card>
          <a-row :gutter="[36,24]" :wrap="true" class="category-list">
            <a-col>
              <a-button :type="sort == 1 ? 'primary' : 'text'" size="small" @click="sort = 1">综合排序</a-button>
            </a-col>
            <a-col>
              <a-button :type="sort == 2 ? 'primary' : 'text'" size="small" @click="sort = 2">时间排序</a-button>
            </a-col>
            <a-col>
              <a-button :type="sort == 3 ? 'primary' : 'text'" size="small" @click="sort = 3">未解决</a-button>
            </a-col>
            <a-col>
              <a-button :type="sort == 4 ? 'primary' : 'text'" size="small" @click="sort = 4">已解决</a-button>
            </a-col>
          </a-row>
        </a-card>
        <a-card style="margin-top: 20px">
          <a-skeleton active :loading="loading">
          <a-list item-layout="horizontal" size="large" :data-source="questionList" :locale="{emptyText: '暂无数据'}">
            <template #renderItem="{ item }">
              <router-link :to="{name:'Question',params:{id:item.id}}">
                <a-list-item key="item.title" class="list-item">
                  <template #actions>
                  <span><EyeOutlined style="margin-right: 5px"/>{{item.viewCount}}</span>
                    <span><MessageOutlined style="margin-right: 5px"/>{{item.answerCount}}</span>
                    <span v-if="item.isSolved == 1">已解决</span>
                    <span v-else>未解决</span>
                  </template>
                  <a-list-item-meta :description="item.userNickName">
                    <template #title>
                      <span>{{ item.title }}</span>
                    </template>
                    <template #avatar>
                      <a-avatar :src="item.userAvatar"/>
                    </template>
                  </a-list-item-meta>
                  {{ item.content }}
                </a-list-item>
              </router-link>
            </template>
          </a-list>
          </a-skeleton>
        </a-card>
        <a-button style="display: block;margin: 20px auto" v-if="page < pages" @click="loadMore">加载更多</a-button>
        <div style="text-align: center;color: rgb(153, 153, 153);margin: 10px 0;" v-else>没有更多内容了</div>
      </a-col>
      <a-col :span="6">
        <a-card title="我的提问" v-if="isLogin" :body-style="{padding:'10px 24px'}">
          <MyQuestionList />
          <a-button style="margin: 0 auto;display: block;margin-top: 10px" type="primary" @click="createQuestion = true">发起提问</a-button>
          <a-modal v-model:visible="createQuestion" title="发起提问" :footer="false">
            <CreateQuestion/>
          </a-modal>
        </a-card>
        <a-card style="text-align: center" v-else>
          <div style="margin-bottom: 10px;font-size: 18px">登录后可发起提问</div>
          <router-link :to="{name:'Login'}">
            <a-button size="small" type="primary" ghost>立即登录</a-button>
          </router-link>
        </a-card>
        <a-card title="今日热门问题" style="margin-top: 20px" :body-style="{padding:'10px 24px'}">
          <TodayQuestionList />
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import {EyeOutlined, MessageOutlined} from "@ant-design/icons-vue"
import store from "../../../store/store";
import {ref, computed, watch} from "vue";
import axios from "../../../axios/axios.js"
import MyQuestionList from "../../../components/Question/MyQuestionList.vue";
import TodayQuestionList from "../../../components/Question/TodayQuestionList.vue";
import CreateQuestion from "../../../components/Question/CreateQuestion.vue";
export default {
  name: "QuestionList",
  components: {
    EyeOutlined, MessageOutlined,MyQuestionList,TodayQuestionList,CreateQuestion
  },
  setup() {
    const isLogin = ref(false);
    if (store.state.user.userId !== undefined && store.state.user.token !== undefined) {
      isLogin.value = true
    }


    const sort = ref(1);
    const page = ref(1);
    const pages = ref(0);
    const questionList = ref([])
    const loading = ref(true)
    const init = () => {
      axios.get("/do/common/getquestionlistbycondition.do/" + page.value + "/" + 10 + "/" + sort.value).then(res => {
        pages.value = res.data.data.pages
        res.data.data.records.forEach((item) => {
          questionList.value.push(item)
          loading.value = false;
        })
      })
    }
    init()

    const loadMore = () => {
      page.value += 1
      axios.get("/do/common/getquestionlistbycondition.do/" + page.value + "/" + 10 + "/" + sort.value).then(res => {
        pages.value = res.data.data.pages
        res.data.data.records.forEach((item) => {
          questionList.value.push(item)
        })
      })
    }

    watch(sort, (newval, oldval) => {
      questionList.value = []
      page.value = 1
      pages.value = 0
      axios.get("/do/common/getquestionlistbycondition.do/" + page.value + "/" + 10 + "/" + sort.value).then(res => {
        pages.value = res.data.data.pages
        res.data.data.records.forEach((item) => {
          questionList.value.push(item)
        })
      })
    })
     const createQuestion = ref(false)

    return {
      isLogin,
      sort,
      page,
      pages,
      questionList,
      loadMore,
      createQuestion,
      loading
    }
  }
}
</script>
<style scoped lang="scss">
.list-item{
  border-radius: 10px;
  &:hover{
    background: rgba(0, 0, 0, 0.05);
  }
}
</style>