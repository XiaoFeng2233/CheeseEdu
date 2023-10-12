<template>
  <div class="container">
    <a-breadcrumb class="breadcrumb">
      <a-breadcrumb-item>首页</a-breadcrumb-item>
      <a-breadcrumb-item><a href="">课程</a></a-breadcrumb-item>
      <a-breadcrumb-item v-if="courseInfo != undefined">
        <router-link :to="{name:'Course',params:{'courseId':courseInfo.id}}">{{ courseInfo.title }}</router-link>
      </a-breadcrumb-item>
      <a-breadcrumb-item>{{activeVideoName}}</a-breadcrumb-item>
    </a-breadcrumb>

    <a-skeleton :loading="loading" :paragraph="{ rows:15 }">
      <div class="player-card">
        <div class="flex-box">
          <video-component :video-play-url="videoPlayUrl" v-if="videoPlayUrl != undefined"/>
          <video-component :video-play-url="videoPlayUrl" v-else/>
          <el-scrollbar class="course-chapter">
            <div style="text-align: center" v-if="videoList.length == 0">站长还未上传该课程的视频~</div>
            <div class="chapter-item" v-for="item in videoList" v-else>
              <div class="chapter-name">
                {{ item.name }}
              </div>
              <div class="chapter-videos">
                <div @click="changeVideo(i.id,i.title)" class="video-item" :class="{active:i.id == activeVideoId}" v-for="i in item.videoList">
                  <i class="iconfont icon-video1"></i>
                  <span>{{ i.title }}</span>
                </div>
              </div>
            </div>
          </el-scrollbar>
        </div>
      </div>
    </a-skeleton>
  </div>
</template>

<script>
import "vue3-video-play/dist/style.css"
import {reactive} from "vue";
import {useRouter, useRoute} from "vue-router"
import axios from "../../../axios/axios.js"
import {ref,watch} from "vue"
import {message} from "ant-design-vue"
import VideoComponent from "../../../components/Course/VideoComponent.vue";

export default {
  name: "CourseVideo",
  components: {
    VideoComponent,
  },
  setup() {
    const loading = ref(true)
    const router = useRouter();
    const route = useRoute();
    const courseId = route.params.courseId
    const courseInfo = ref(undefined)
    const activeVideoId = ref(undefined)
    const activeVideoName = ref(undefined)
    axios.get("/do/common/getcoursebyid.do/" + courseId).then(res => {
      if (res.data.data == null || res.data.data == undefined) {
        message.warn("课程不存在")
        return false;
      }
      courseInfo.value = res.data.data
    })

    const getVideoPlayUrl = ()=>{
      axios.get("/do/user/getvideoplayurl/" + courseId + "/" + activeVideoId.value).then(res=>{
        if (res.data.data== null || res.data.data == undefined){
          message.error("你还没有拥有该课程")
          router.push({name:"Course",params:{courseId:courseId}})
          return false;
        }
        videoPlayUrl.value = res.data.data
      })
    }


    const videoList = ref([])
    axios.get("/do/user/getcoursevideobycourseid.do/" + courseId).then(res => {
      if (res.data.code != 200) {
        message.error(res.data.data)
        router.push("/")
        return false;
      }
      videoList.value = res.data.data
      for (let i = 0;i<res.data.data.length;i++){
        if (res.data.data[i].videoList.length >0){
          activeVideoName.value = res.data.data[i].videoList[0].title
          activeVideoId.value = res.data.data[i].videoList[0].id
          loading.value = false
          getVideoPlayUrl()
          break
        }
      }
      loading.value = false
    })

    // watch(activeVideoId,(newval,oldval)=>{
    //   // changeVideo(newval)
    // })

    const videoPlayUrl = ref(undefined)

    const changeVideo = (id,title)=>{
      activeVideoId.value = id
      activeVideoName.value = title
      getVideoPlayUrl()
    }
    return {
      loading,
      changeVideo,
      courseInfo,
      videoList,
      activeVideoId,
      activeVideoName,
      videoPlayUrl
    }
  }
}
</script>
<style scoped lang="scss">
.breadcrumb {
  margin-top: 20px;
}

.player-card {
  margin-top: 20px;
  background-color: white;
  height: 500px;
}

.flex-box {
  display: flex;
  height: 100%;

  .course-chapter {
    flex: 1;
    padding: 30px 30px;
    height: 100%;
    overflow: auto;
  }
}

.chapter-item {
  &:first-child {
    margin-top: 0px;
  }

  margin-top: 30px;

  .chapter-name {
    color: #333333;
    font-size: 16px;
    font-weight: 500;
    width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    word-break: break-all;
    white-space: nowrap;
  }

  .chapter-videos {
    .video-item {
      height: 24px;
      margin-top: 20px;
      overflow: hidden;
      display: flex;
      align-items: center;
      cursor: pointer;
      font-size: 14px;

      width: 100%;

      &.active {
        color: #00c691;
      }

      span {
        flex: 1;
        word-break: break-all;
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
      }

      i {
        font-size: 30px;
        margin-right: 5px;
        color: #00c691;
      }
    }
  }
}
</style>