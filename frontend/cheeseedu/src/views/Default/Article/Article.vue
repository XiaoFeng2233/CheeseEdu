<template>
    <div class="container">
    <a-row :gutter="24" style="margin-top: 20px">
      <a-col :span="18">
        <a-card hoverable>
          <template #title>
            <div style="margin: 0;font-size: 22px;font-weight: 700;">
              {{article.title}}
            </div>
          </template>
          <div v-html="article.content">
          </div>
          <div style="text-align: right">
            <div style="color: #999999">发布于：{{article.createdTime}}</div>
            <div style="margin-top: 10px;color: #999999">{{article.viewNumbers}} 人已阅读</div>
          </div>

        </a-card>
      </a-col>
      <a-col :span="6">
        <a-card hoverable title="推荐文章">
          <a-list>
            <a-list-item v-for="item in recommendList">
              <router-link :to="{name:'Article',params:{'articleId':item.id}}" class="article-title">{{item.title}}</router-link>
            </a-list-item>
          </a-list>
        </a-card>
      </a-col>
    </a-row>
    </div>
</template>

<script>
import {useRoute, useRouter} from "vue-router";
import {ref,reactive} from "vue";
import axios from "../../../axios/axios.js"
export default{
  name:"Article",
  setup(){
    const route = useRoute()
    const router = useRouter()
    const articleId = route.params.articleId
    const article = reactive({
      viewNumbers:undefined,
      coverUrl:undefined,
      title:undefined,
      createdTime:undefined,
      updatedTime:undefined,
      content:undefined,
      text:undefined
    })
    const recommendList = ref(undefined);
    const init = ()=>{
      axios.get("/do/common/getarticlebyid.do/" + articleId).then(res=>{
        article.viewNumbers = res.data.data.viewNumbers
        article.coverUrl = res.data.data.coverUrl
        article.title = res.data.data.title
        article.createdTime = res.data.data.createdTime
        article.updatedTime = res.data.data.updatedTime
        article.content = res.data.data.content
        article.text = res.data.data.text
      })

      axios.get("/do/common/getarticlebyviewnumber.do/" + 10).then(res=>{
        recommendList.value = res.data.data
      })
    }

    init()
    return{
      article,
      recommendList
    }
  }
}
</script>
<style scoped lang="scss">
.article-title{
  color: #333333;
  width: 100%;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  &:hover{
    color: #00c691;
  }
}
</style>