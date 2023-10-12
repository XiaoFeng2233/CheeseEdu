<template>
    <div>
      <div class="search-bar">
        <div class="bar">
          <a-input v-model:value="keyword" size="large" placeholder="输入你想搜索的"/>
          <a-button style="margin-left: 20px;width: 100px" size="large" type="primary" @click="search">搜索</a-button>
        </div>
      </div>
      <div class="container result">
        <a-tabs v-model:activeKey="activeKey">
          <a-tab-pane key="1" tab="课程">
            <img class="no-result" src="../../assets/none-result.png" alt="" v-if="courses.length == 0">
            <div class="course-list" v-else>
              <div class="course-list-item" v-for="item in courses">
                <div class="course-image">
                  <img :src="item.image" alt="">
                </div>
                <div class="course-info">
                  <div class="course-title">
                    <router-link :to="{name:'Course',params:{courseId:item.id}}" class="course-title">
                      {{item.shortDescription}}
                    </router-link>
                  </div>
                  <div class="course-desc">
                    {{item.title}}
                  </div>
                </div>
              </div>
            </div>
          </a-tab-pane>
          <a-tab-pane key="2" tab="提问" force-render>
            <img class="no-result" src="../../assets/none-result.png" alt="" v-if="questions.length == 0">
            <div class="course-list" v-else>
              <div class="course-list-item" v-for="item in questions">
                <div class="course-info">
                  <div class="course-title">
                    <router-link :to="{name:'Question',params:{id:item.id}}" class="course-title">
                      {{item.title}}
                    </router-link>
                  </div>
                  <div class="course-desc">
                    {{item.content}}
                  </div>
                </div>
              </div>
            </div>
          </a-tab-pane>
          <a-tab-pane key="3" tab="文章">
            <img class="no-result" src="../../assets/none-result.png" alt="" v-if="articles.length == 0">
            <div class="course-list" v-else>
              <div class="course-list-item" v-for="item in articles">
                <div class="course-info">
                  <div class="course-title">
                    <router-link :to="{name:'Article',params:{articleId:item.id}}" class="course-title">
                      {{item.title}}
                    </router-link>                  </div>
                  <div class="course-desc" style="width: 100%;overflow: hidden;white-space: nowrap;text-overflow: ellipsis">
                    {{item.text}}
                  </div>
                </div>
              </div>
            </div>
          </a-tab-pane>
        </a-tabs>
      </div>
    </div>
</template>

<script>
import {useRoute,useRouter} from "vue-router"
import axios from "../../axios/axios.js"
import qs from "qs"
import {ref} from "vue"
import {message} from "ant-design-vue"
export default{
  name:"Search",
  setup(){
    const activeKey = ref("1")
    const router = useRouter();
    const route = useRoute();
    const questions = ref([])
    const courses = ref([])
    const articles = ref([])
    const keyword = ref(undefined);
    const init =()=>{
      keyword.value = route.params.keyword
      axios.post("/do/common/search.do",qs.stringify({
        keyword:route.params.keyword
      })).then(res=>{
        questions.value = res.data.data.question
        articles.value = res.data.data.article
        courses.value = res.data.data.course
      })
    }
    init()

    const search = ()=>{
      if (keyword.value == "" || keyword.value == undefined){
        message.warn("请输入想要搜索的关键词")
        return false;
      }
      router.push({name:"Search",params:{"keyword":keyword.value}})
    }
    return{
      questions,
      courses,
      articles,
      activeKey,
      keyword,
      search
    }
  }
}
</script>
<style scoped lang="scss">
.search-bar{
  background-color: white;
  padding: 30px 0;
  .bar{
    width: 500px;
    margin: 0 auto;
    display: flex;
    justify-content: center;
    align-items: center;
  }
}

.result{
  margin-top: 20px;
  background-color: white;
  padding: 10px 20px;
}

.no-result{
  display: block;
  width: 200px;
  margin: 0 auto;
}

.course-list{
  width: 800px;
  .course-list-item{
    padding:10px 10px;
    display: flex;
    .course-image{
      margin-right: 20px;
      width: 150px;
      height: 100px;
      img{
        width: 100%;
        height: 100%;
        border-radius: 10px;
      }
    }
    .course-info{
      display: flex;
      flex:1;
      flex-direction: column;
      .course-title{
        color: #666;
        font-size: 18px;
        font-weight: 600;
        text-overflow: ellipsis;
        width: 100%;
        white-space: nowrap;
        overflow: hidden;
        
      }
      .course-desc{
        font-size: 14px;
        color: #918f8f;
        font-weight: 400;
      }
    }
  }
}
</style>