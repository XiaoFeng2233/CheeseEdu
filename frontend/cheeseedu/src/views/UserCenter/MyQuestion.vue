<template>
    <div>
      <a-skeleton active :paragraph="{rows:10}" :loading="loading">
        <h1 style="text-align: center;color: #888888;font-size: 20px" v-if="questionList.length == 0">暂无数据</h1>
        <div class="card" v-for="item in questionList" v-else>
          <div class="question-title">
            <router-link :to="{name:'Question',params:{id:item.id}}">
              {{item.title}}
            </router-link>
          </div>
          <div class="question-info">
            <div class="question-time">
              {{item.createdTime}}
            </div>
            <div class="question-status">
              <span v-if="item.isSolved == 1">已解决</span>
              <span v-else>未解决</span>
            </div>
          </div>
        </div>
      </a-skeleton>
    </div>
</template>

<script>
import {ref} from "vue"
import axios from "../../axios/axios.js"
export default{
  name:"MyQuestion",
  setup(){
    const loading = ref(true)
    const questionList = ref([])

    const init = ()=>{
      axios.get("/do/question/getallquestionlist.do").then(res=>{
        loading.value = false
        questionList.value = res.data.data
      })
    }

    init()

    return {
      loading,
      questionList
    }
  }
}
</script>
<style scoped lang="scss">
.card{
  padding: 20px;
  box-shadow: 0 2px 8px 2px rgb(0 0 0 / 10%);
  width: 100%;
  margin-bottom: 20px;
  border-radius: 5px;
}

.question-title{
  a{
    color:rgb(39, 39, 42);
    font-size: 16px;
    font-weight: 500;
    &:hover{
      color: #00c691;
    }
  }
}

.question-info{
  .question-time{
    margin: 5px 0;
    text-align: right;
    color: rgb(161, 161, 170);
    font-size: 12px;
  }

  .question-status{
    color: rgb(113, 113, 122);
    font-size: 14px;
  }
}

</style>