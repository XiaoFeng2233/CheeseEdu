<template>
  <div class="container">
    <h1 style="margin-top: 20px;text-align: center">精选文章</h1>
    <a-row :gutter="[48,24]">
      <a-col :span="12" v-for="item in articleList">
        <router-link :to="{name:'Article',params:{'articleId':item.id}}">
        <a-card hoverable>
          <a-row :gutter="36">
            <a-col :span="10">
              <img style="width: 100%;height: 100%" :src="item.coverUrl"
                   alt="">
            </a-col>
            <a-col :span="14">
              <div class="article-title">
                {{ item.title }}
              </div>
              <div class="article-text">
                {{ item.text }}
              </div>
              <div class="article-info">
                <span>{{ item.createdTime.split(' ')[0] }}</span>
                <span>{{ item.viewNumbers }}人阅读</span>
              </div>
            </a-col>
          </a-row>
        </a-card>
        </router-link>
      </a-col>
    </a-row>
    <div style="margin-top:40px;text-align: center" v-if="pageOps.pageNumber < pageOps.pages">
      <a-button @click="loadMore">加载更多</a-button>
    </div>
    <div style="margin-top: 40px;color: #999999;text-align: center" v-else>没有更多了</div>
  </div>
</template>

<script>
import axios from "../../../axios/axios.js"
import {ref, reactive} from "vue"

export default {
  name: "ArticleList",
  setup() {
    const pageOps = reactive({
      pageSize: 20,
      pageNumber: 1,
      pages: undefined
    })
    const articleList = ref([])
    const getArticleList = (pagenumber, pagesize) => {
      axios.get("/do/common/getallarticle.do/" + pagenumber + "/" + pagesize).then(res => {
        res.data.data.records.forEach((item) => {
          articleList.value.push(item)
        })
        pageOps.pages = res.data.data.pages
      })
    }

    getArticleList(pageOps.pageNumber, pageOps.pageSize)
    const loadMore = () => {
      pageOps.pageNumber++
      getArticleList(pageOps.pageNumber,pageOps.pageSize)
    }
    return {
      articleList,
      pageOps,
      loadMore
    }

  }
}
</script>
<style scoped lang="scss">
.article-list {
  margin-top: 20px;
}

.article-title {
  color: #333333;
  font-size: 20px;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
}

.article-text {
  text-overflow: ellipsis;
  overflow: hidden;
  color: #999999;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  display: -webkit-box;
  height: 45px;
}

.article-info {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  color: #333333;

}
</style>