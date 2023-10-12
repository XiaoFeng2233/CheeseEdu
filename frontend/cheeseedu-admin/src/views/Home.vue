<template>
    <div class="app-container">
      <a-card title="全局统计" :bordered="false" style="width: 100%">
        <a-row>
          <a-col :span="6">
            <a-statistic title="今日提问数" :value="statistics.todayNewQuestionCount" style="margin-right: 50px" />
          </a-col>
          <a-col :span="6">
            <a-statistic title="今日新用户" :value="statistics.todayNewUsersCount" style="margin-right: 50px" />
          </a-col>
          <a-col :span="6">
            <a-statistic title="今日订单数" :value="statistics.todayOrderCount" style="margin-right: 50px" />
          </a-col>
          <a-col :span="6">
            <a-statistic title="今日订单金额" :value="statistics.todayOrderAmount == null ? 0 : statistics.todayOrderAmount" style="margin-right: 50px" >
              <template #prefix>
                ¥
              </template>
            </a-statistic>
          </a-col>
        </a-row>
      </a-card>
      <a-row style="margin-top: 10px" :gutter="12">
        <a-col :span="12">
          <a-card style="width: 100%" title="待处理问题" :bordered="false">
            <template #extra>
              <router-link :to="{name:'QuestionList'}">更多</router-link>
            </template>
            <a-list size="small" bordered :data-source="questionList" :locale="{emptyText: '暂无数据'}">
              <template #renderItem="{ item }">
                <a-list-item>
                  <a :href="'/#/question/' + item.id" >
                    {{ item.title }}
                  </a>
                </a-list-item>
              </template>
            </a-list>
          </a-card>
        </a-col>
        <a-col :span="12">
          <a-card style="width: 100%" title="应用信息" :bordered="false">
            <a-list bordered>
              <a-list-item>
                <a-row style="width: 100%">
                  <a-col :span="12">系统名称</a-col>
                  <a-col :span="12">CheeseEdu</a-col>
                </a-row>
              </a-list-item>
              <a-list-item>
                <a-row style="width: 100%">
                  <a-col :span="12">系统版本</a-col>
                  <a-col :span="12">V1.0</a-col>
                </a-row>
              </a-list-item>
              <a-list-item>
                <a-row style="width: 100%">
                  <a-col :span="12">开源地址</a-col>
                  <a-col :span="12">
                    <a href="https://gitee.com/xiaofengig/cheese-edu">Gitee</a>
                  </a-col>
                </a-row>
              </a-list-item>
            </a-list>
          </a-card>
        </a-col>

      </a-row>
    </div>
</template>

<script>
import axios from "../axios/axios.js"
import {ref,reactive} from "vue"
export default{
  name:"Home",
  setup() {
    const statistics = reactive({
      todayOrderAmount:undefined,
      todayOrderCount:undefined,
      todayNewUsersCount:undefined,
      todayNewQuestionCount:undefined
    })
    const questionList = ref(undefined)
    const init = ()=>{
      axios.get("/do/admin/statistics.do").then(res=>{
        statistics.todayOrderAmount = res.data.data.todayOrderAmount
        statistics.todayOrderCount = res.data.data.todayOrderCount
        statistics.todayNewUsersCount = res.data.data.todayNewUsersCount
        statistics.todayNewQuestionCount = res.data.data.todayNewQuestionCount
      })

      axios.get("/do/admin/getnosolvedquestion.do/" + 5).then(res=>{
        questionList.value = res.data.data
      })
    }

    init()
    return {
      questionList,
      statistics
    };
  },
}
</script>
<style scoped lang="scss">

</style>