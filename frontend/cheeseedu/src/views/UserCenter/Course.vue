<template>
  <div>
    <div class="card">
      <h1>我的课程</h1>
      <a-tabs v-model:activeKey="tabActive" size="large">
        <a-tab-pane key="1" tab="全部">
          <ul class="course-list">
            <div style="text-align: center" v-if="courseList.length==0">暂无数据</div>
            <li class="list-item" v-for="item in courseList" v-else>
              <div class="course-info">
                <img :src="item.courseImage" alt="">
                <div class="course">
                  <router-link :to="{name:'CourseVideo',params:{'courseId':item.courseId}}" class="course-name">
                    {{item.courseTitle}}
                  </router-link>
                  <div class="course-percent">
                    <span>共 {{item.totalVideo}} 讲 <span style="margin: 0 5px">|</span> 已学 {{item.learnedVideo}} 讲 </span>
                  </div>
                </div>
              </div>
              <div class="course-action">
                <div class="date">
                  <p>获取时间</p>
                  <p>{{item.createdTime.split(' ')[0]}}</p>
                </div>
                <div class="action">
                  <a-button type="primary" shape="round" @click="jumpToLearn(item.courseId)">开始学习</a-button>
                  <a-button type="text" shape="round" size="small" @click="openCommentDialog(item.courseId)">发表评论</a-button>
                </div>
              </div>
            </li>
          </ul>
        </a-tab-pane>
        <a-tab-pane key="2" tab="未学完" force-render>
          <ul class="course-list">
            <div style="text-align: center" v-if="learningCourseList.length==0">暂无数据</div>
            <li class="list-item" v-for="item in learningCourseList" v-else>
              <div class="course-info">
                <img :src="item.courseImage" alt="">
                <div class="course">
                  <router-link :to="{name:'CourseVideo',params:{'courseId':item.courseId}}" class="course-name">
                    {{item.courseTitle}}
                  </router-link>
                  <div class="course-percent">
                    <span>共 {{item.totalVideo}} 讲 <span style="margin: 0 5px">|</span> 已学 {{item.learnedVideo}} 讲 </span>
                  </div>
                </div>
              </div>
              <div class="course-action">
                <div class="date">
                  <p>获取时间</p>
                  <p>{{item.createdTime.split(' ')[0]}}</p>
                </div>
                <div class="action">
                  <a-button type="primary" shape="round" @click="jumpToLearn(item.courseId)">开始学习</a-button>
                  <a-button type="text" shape="round" size="small" @click="openCommentDialog(item.courseId)">发表评论</a-button>
                </div>
              </div>
            </li>
          </ul>
        </a-tab-pane>
        <a-tab-pane key="3" tab="已学完">
          <ul class="course-list">
            <div style="text-align: center" v-if="learnedCourseList.length==0">暂无数据</div>
            <li class="list-item" v-for="item in learnedCourseList" v-else>
              <div class="course-info">
                <img :src="item.courseImage" alt="">
                <div class="course">
                  <router-link :to="{name:'CourseVideo',params:{'courseId':item.courseId}}" class="course-name">
                    {{item.courseTitle}}
                  </router-link>
                  <div class="course-percent">
                    <span>共 {{item.totalVideo}} 讲 <span style="margin: 0 5px">|</span> 已学 {{item.learnedVideo}} 讲 </span>
                  </div>
                </div>
              </div>
              <div class="course-action">
                <div class="date">
                  <p>获取时间</p>
                  <p>{{item.createdTime.split(' ')[0]}}</p>
                </div>
                <div class="action">
                  <a-button type="primary" shape="round" @click="jumpToLearn(item.courseId)">开始学习</a-button>
                  <a-button type="text" shape="round" size="small" @click="openCommentDialog(item.courseId)">发表评论</a-button>
                </div>
              </div>
            </li>
          </ul>
        </a-tab-pane>
      </a-tabs>
    </div>

    <a-modal v-model:visible="commentDialog" title="发表评价" :footer="false" :destroy-on-close="true">
      <CourseCommentDialog :id="commentId"/>
    </a-modal>
  </div>
</template>

<script>
import {ref} from "vue"
import axios from "../../axios/axios.js"
import CourseCommentDialog from "../../components/Course/CourseCommentDialog.vue";
import {useRouter} from "vue-router"
export default {
  name: "UserCenterCourse",
  components:{
    CourseCommentDialog
  },
  setup() {
    const router = useRouter();
    const tabActive = ref('1')
    const courseList = ref([]);
    const learningCourseList = ref([])
    const learnedCourseList = ref([])
    axios.get("/do/user/getmycourse.do/0").then(res=>{
      if (res.data.data != undefined || res.data.data != null){
        let list = res.data.data;
        list.forEach(item=>{
          if (item.totalVideo == item.learnedVideo){
            learnedCourseList.value.push(item)
          }else{
            learningCourseList.value.push(item)
          }
        })

        courseList.value = res.data.data
      }
    })


    const commentDialog = ref(false)
    const commentId = ref(undefined)
    const openCommentDialog = (id)=>{
      commentDialog.value = true
      commentId.value = id
    }

    const jumpToLearn = (id)=>{
      router.push({name:"CourseVideo",params:{courseId:id}})
    }
    return{
      tabActive,
      courseList,
      learningCourseList,
      learnedCourseList,
      commentDialog,
      commentId,
      openCommentDialog,
      jumpToLearn
    }
  }
}
</script>
<style scoped lang="scss">
.card{
  width: 100%;
  box-shadow: 0 2px 10px 0 rgb(46 61 72 / 5%);
  padding: 20px;
}
.course-list{
  list-style: none;
  padding: 0;
  margin: 0;
  .list-item{
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 20px 0;
    border-bottom: 1px solid #eaeaea;
  }
}
.course-info{
  display: flex;
  width: 60%;
  img{
    width: 120px;
    height: 60px;
    margin-right: 20px;
  }
  .course{
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    flex:1;
    width: 0;
    .course-name{
      width: 100%;
      color: #4c4c4c;
      font-size: 18px;
      overflow: hidden;
      text-overflow: ellipsis;
      word-break: break-all;
      white-space: nowrap;
    }
    .course-percent{
      color: #888888;
      font-size: 14px;
      font-weight: 400;
    }
  }
}

.course-action{
  width: 40%;
  display: flex;
  align-items: center;
  padding: 0 20px;
  justify-content: space-between;
  .date{
    text-align: center;
    p:last-child{
      font-size: 16px;
      font-weight: 400;
    }
    p{
      color: #888888;
      margin: 0;
    }
  }
  .action{
    display: flex;
    flex-direction: column;
  }
}
</style>