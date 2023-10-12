<template>
  <div class="container">
    <a-skeleton :active="true" :loading="loading.infoLoading" :paragraph="{rows:5}">
      <a-card style="margin-top: 20px">
        <a-row :gutter="24">
          <a-col :span="10">
            <img :src="course.image" alt=""
                 style="width: 100%;height: 100%">
          </a-col>
          <a-col :span="14">
            <div class="course">
              <div class="course-info">
                <h1 class="course-title">
                  {{ course.title }}
                </h1>
                <span class="course-description">
                    {{ course.shortDescription }}
                  </span>
                <div class="course-statistic">
                    <span>
                      共
                      <span>{{course.videoCount}}</span>
                      节课
                    </span>
                  <span style="margin-left: 20px">
                      <span>{{ course.studentNumber }}</span>
                      人已学习
                    </span>
                </div>
                <div class="course-price">
                  <span class="free" v-if="course.type == 0">免费</span>
                  <span class="price" v-else>
                      ¥
                      <span>{{course.price}}</span>
                      <span v-if="course.type == 1">（会员免费）</span>
                    </span>
                </div>
              </div>
              <div class="course-action">
                <a-button @click="createOrder" type="primary" size="large" v-if="course.type == 0">立即学习</a-button>
                <a-button @click="createOrder" type="primary" danger size="large" v-else>立即购买</a-button>
              </div>
            </div>
          </a-col>
        </a-row>
      </a-card>
    </a-skeleton>
    <a-row style="margin-top: 20px" :gutter="24">
      <a-col :span="18">
        <a-skeleton :active="true" :loading="loading.descriptionLoading" :paragraph="{rows:8}">
          <a-card>
            <a-tabs v-model:activeKey="activeKey">
              <a-tab-pane key="description" tab="课程介绍">
                <CourseDescription :content="course.description"/>
              </a-tab-pane>
              <a-tab-pane key="catalogue" tab="章节目录" force-render>
                <CourseCatalogue :course-id="courseId"/>
              </a-tab-pane>
              <a-tab-pane key="comment" tab="用户评价">
                <CourseComment :comment-list="commentList"/>
              </a-tab-pane>
            </a-tabs>
          </a-card>
        </a-skeleton>
      </a-col>
      <a-col :span="6">
        <a-skeleton :active="true" :loading="loading.recommendLoading" :paragraph="{rows:5}">
          <a-card title="推荐课程">
            <ul class="recommend-list">
              <li v-for="item in recommendCourseList">
                <img :src="item.image" alt="" style="width: 100%">
                <div>{{ item.title }}</div>
              </li>
            </ul>
          </a-card>
        </a-skeleton>
      </a-col>
    </a-row>

  </div>
</template>

<script>
import CourseDescription from "../../../components/Course/CourseDescription.vue";
import CourseCatalogue from "../../../components/Course/CourseCatalogue.vue";
import CourseComment from "../../../components/Course/CourseComment.vue";
import {reactive, ref} from "vue"
import axios from "../../../axios/axios.js"
import qs from "qs"
import {useRoute, useRouter} from "vue-router"
import {message} from "ant-design-vue";

export default {
  name: "Course",
  components: {
    CourseDescription,
    CourseCatalogue,
    CourseComment,
  },
  setup() {
    const route = useRoute()
    const router = useRouter()
    const activeKey = ref("description")
    const loading = reactive({
      infoLoading: true,
      descriptionLoading: true,
      recommendLoading: true
    })
    const courseId = route.params.courseId
    const course = ref({})
    const recommendCourseList = ref([])

    axios.get("/do/common/getrandomcourse.do/" + "10").then(res => {
      if (res.data.code != "200") {
        message.error(res.data.data)
        return false
      }
      recommendCourseList.value = res.data.data
      loading.recommendLoading = false
    })

    axios.get("/do/common/getcoursebyid.do/" + courseId).then(res => {
      if (res.data.code != "200") {
        message.error(res.data.data)
        return false
      }
      if (res.data.data == null) {
        message.warn("课程不存在")
        router.push({name: "Home"})
        return false
      }

      course.value = res.data.data
      loading.descriptionLoading = false
      loading.infoLoading = false
    })

    const createOrder = ()=>{
      router.push({name:'CreateCourseOrder',params:{courseId:courseId}})
    }

    const commentList = ref(undefined)
    const getCommentList = ()=>{
      axios.get("/do/common/getcoursecomment.do/" + courseId).then(res=>{
        commentList.value = res.data.data
      })
    }
    getCommentList()

    return {
      route,
      activeKey,
      loading,
      course,
      recommendCourseList,
      createOrder,
      commentList,
      courseId
    }
  }
}
</script>
<style scoped lang="scss">
.course {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
}

.course-info {
  width: 100%;

  .course-title {
    font-size: 24px;
  }

  .course-description {
    color: #999999;
  }

  .course-statistic {
    color: #999999;
    margin-top: 20px;
  }

  .course-price {
    font-size: 24px;
    font-weight: 700;
    margin-top: 10px;

    .free {
      color: #00c691;
    }

    .price {
      color: #ff7875;
    }
  }
}

.recommend-list {
  list-style: none;
  padding: 0;

  li {
    cursor: pointer;
    margin-bottom: 20px;

    img {
      width: 100%;
      max-height: 150px;
    }

    div {
      margin-top: 10px;
      text-overflow: ellipsis;
      overflow: hidden;
      width: 100%;
      white-space: nowrap;
    }
  }
}
</style>