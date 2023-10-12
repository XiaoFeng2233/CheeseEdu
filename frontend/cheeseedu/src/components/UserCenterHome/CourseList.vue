<template>
  <div class="learning">
    <div class="title">
      <span>正在学习</span>
      <router-link :to="{name:'UserCenterCourse'}">查看更多</router-link>
    </div>
    <ul class="learn-list">
      <li class="list-item" v-for="item in props.courseList">
        <img :src="item.courseImage" alt="">
        <div class="course-info">
          <router-link class="course-name" :to="{name:'CourseVideo',params:{'courseId':item.courseId}}">{{item.courseTitle}}</router-link>
          <div class="course-progress">共{{item.totalVideo}}讲 | 已学{{item.learnedVideo}}讲</div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import {ref} from "vue";

export default {
  name: "UserCenterHomeCourseList",
  props:{
    courseList:Array
  },
  setup(props){

    return{
      props
    }
  }
}
</script>
<style scoped lang="scss">
.learning{
  margin-top: 32px;
  width: 100%;
  box-shadow: 0 2px 10px 0 rgb(46 61 72 / 5%);
  border-radius: 15px;
  padding: 20px;
  .title{
    font-size: 20px;
    display: flex;
    justify-content: space-between;
    a{
      font-size: 14px;
      color: #888888;
      cursor: pointer;
      font-weight: 400;
    }
  }
}


.learn-list{
  list-style: none;
  margin: 0;
  padding: 0;

  .list-item{
    margin: 10px 0;
    display: flex;
    flex-direction: row;
    width: 100%;
    img{
      width: 160px;
      height: 80px;
      margin-right: 10px;
    }
  }
}

.course-info{
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  flex:1;
  width: 0;
  .course-name{
    font-size: 17px;
    font-weight: 400;
    color: #404040;
    cursor: pointer;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
    &:hover{
      color: #00c691;
    }
  }
  .course-progress{
    color: #888888;
    font-size: 13px;
    font-weight: 400;
    margin-bottom: 10px;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
  }
  .course-percent{
    color: #888888;
    font-size: 12px;
    font-weight: 400;
  }
}
</style>