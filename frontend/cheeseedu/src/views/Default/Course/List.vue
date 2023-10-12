<template>
  <div class="container">
    <a-skeleton :loading="loading.categoryLoading" :active="true">
    <a-card style="margin-top: 20px;">
      <div class="course-category">
        <span>分类</span>
        <a-row :gutter="[36,24]" :wrap="true" class="category-list">
          <a-col>
            <a-button :type="0 == activeCategoryId ? 'primary' : 'text'" size="small" @click="activeCategoryId = 0 ">
              不限
            </a-button>
          </a-col>
          <a-col v-for="item in categoryList">
            <a-button :type="item.id == activeCategoryId ? 'primary' : 'text'" size="small"
                      @click="activeCategoryId = item.id">
              {{ item.name }}
            </a-button>
          </a-col>
        </a-row>
      </div>
    </a-card>
    </a-skeleton>
    <a-card style="margin-top: 20px">
      <a-row :gutter="[36,24]" :wrap="true" class="category-list">
        <a-col>
          <a-button :type="sort == 1 ? 'primary' : 'text'" size="small" @click="sort = 1">综合排序</a-button>
        </a-col>
        <a-col>
          <a-button :type="sort == 2 ? 'primary' : 'text'" size="small" @click="sort = 2">最新</a-button>
        </a-col>
        <a-col>
          <a-button :type="sort == 3 ? 'primary' : 'text'" size="small" @click="sort = 3">最热</a-button>
        </a-col>
        <a-col>
          <a-button :type="sort == 4 ? 'primary' : 'text'" size="small" @click="sort = 4">免费</a-button>
        </a-col>
      </a-row>
    </a-card>
    <a-skeleton :loading="loading.courseLoading" :active="true">
    <a-row :gutter="[24,24]" style="padding-top: 20px">
      <a-col v-for="item in courseList" :span="6">
        <router-link :to="'/course/' + item.id">

        <a-card hoverable>
          <template #cover>
            <img
                style="height: 150px;width: 100%"
                alt="example"
                :src="item.image"
            />
          </template>
          <div class="course-title">
            {{ item.title }}
          </div>
          <div class="course-info">
              <span class="course-free" v-if="item.type == 0">
                免费
              </span>
            <span class="course-price" v-else-if="item.type == 1">
                <span>¥ </span>
                <span>{{item.price}}</span>
                <span>(会员免费)</span>
              </span>
            <span class="course-price" v-else-if="item.type == 2">
                <span>¥ </span>
                <span>{{item.price}}</span>
              </span>
            <span class="user-number">
                <span>{{ item.studentNumber }}</span>
                <span>人学过</span>
              </span>
          </div>
        </a-card>
        </router-link>
      </a-col>
    </a-row>
    <a-button style="margin: 20px auto;display: block" @click="loadMore" v-if="pageOps.page < pageOps.pages">加载更多</a-button>
    <div style="text-align: center;color:#999999;margin: 20px auto" v-else>没有更多内容了</div>
    </a-skeleton>
  </div>
</template>

<script>
import {ref, watch, reactive} from "vue"
import axios from "../../../axios/axios.js"
import {message} from "ant-design-vue"
import qs from "qs"

export default {
  name: "CourseList",
  setup() {
    const loading = reactive({
      categoryLoading : true,
      courseLoading : true
    })
    const categoryList = ref(undefined)
    const activeCategoryId = ref(0)
    const courseList = ref([])
    const sort = ref(1)
    const pageOps = reactive({
      page: 1,
      pages: undefined
    })
    axios.get("/do/common/getallcategory.do").then(res => {
      if (res.data.code != "200") {
        message.error(res.data.data)
        return false
      }
      categoryList.value = res.data.data
      loading.categoryLoading = false
    })

    let getCourse = (page, categoryId, sort) => {
      axios.post("/do/common/getcoursebycondition.do", qs.stringify({
        page,
        categoryId,
        sort
      })).then(res => {
        if (res.data.code != "200") {
          message.error(res.data.data)
          return false
        }
        res.data.data.records.forEach((item) => {
          courseList.value.push(item)
        })
        pageOps.pages = res.data.data.pages
        pageOps.page = res.data.data.current
        loading.courseLoading = false
      })
    }

    getCourse(pageOps.page, activeCategoryId.value, sort.value)


    watch(activeCategoryId, (newval, oldval) => {
      courseList.value = []
      pageOps.page = 1
      getCourse(pageOps.page, newval, sort.value)
    })

    watch(sort, (newval, oldval) => {
      courseList.value = []
      pageOps.page = 1
      getCourse(pageOps.page, activeCategoryId.value, newval)
    })


    let loadMore = () => {
      pageOps.page++
      getCourse(pageOps.page,activeCategoryId.value,sort.value)
    }


    return {
      categoryList,
      courseList,
      sort,
      activeCategoryId,
      pageOps,
      loadMore,
      loading
    }
  }
}
</script>
<style scoped lang="scss">
.course-category {
  display: flex;
  width: 100%;

  span {
    margin-right: 20px;
    color: #999999;

  }

  .category-list {
    flex: 1;
    width: 0;
  }
}

.course-title {
  font-size: 16px;
  font-weight: 700;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  display: -webkit-box;
  overflow: hidden;
}

.course-info {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;

  .course-price {
    color: #ff7875;
    font-weight: 700;
  }

  .course-free {
    color: #00c691;
  }

  .user-number {
    color: #999999;
  }
}
</style>