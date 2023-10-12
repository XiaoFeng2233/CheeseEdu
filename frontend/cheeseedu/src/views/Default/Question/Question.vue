<template>
    <div class="container">
      <div class="question-card">
        <a-skeleton :loading="questionLoading" active :paragraph="{ rows: 5}">
        <div class="question-title">
          {{question.title}}
        </div>
        <div class="question-content">
          {{question.content}}
        </div>
        <div class="question-image" v-if="question.images  != undefined || question.images != null">
          <div style="margin-bottom: 20px">图片附件</div>
          <div class="images">
            <img v-for="item in question.images" :src="item" @click="previewImage(item)">
          </div>
        </div>
        <div class="question-info">
          <div>
            <a-avatar size="large" :src="question.userAvatar">
            </a-avatar>
            <span style="font-size: 16px;margin-left: 10px">{{question.userNickName}}</span>
          </div>
          <div>
            <EyeOutlined  style="margin-right: 5px"/>
            <span style="margin-right: 20px">{{question.viewCount}}</span>
            <span>发布于</span>
            <span>{{ question.createdTime.split(' ')[0] }}</span>
          </div>
        </div>
        </a-skeleton>
      </div>
      <QuestionReply :question-id="questionId"/>
      <QuestionReplyList :question-id="questionId"/>
    </div>
</template>

<script>
import {EyeOutlined} from "@ant-design/icons-vue"
import {useRoute,useRouter} from "vue-router"
import {message} from "ant-design-vue"
import axios from "../../../axios/axios.js"
import {reactive, ref} from "vue";
import 'viewerjs/dist/viewer.css'
import { api as viewerApi } from "v-viewer"
import QuestionReplyList from "../../../components/Question/QuestionReplyList.vue";
import QuestionReply from "../../../components/Question/QuestionReply.vue";
export default{
  name:"Question",
  components:{
    EyeOutlined,
    QuestionReplyList,
    QuestionReply
  },
  setup(){
    const route = useRoute();
    const router = useRouter();
    const question = ref();

    const questionId = route.params.id;
    const questionLoading = ref(true)
    const init = ()=>{
      axios({
        url:"/do/common/getquestion.do/" + questionId,
        method:"get",
        responseType: 'json'
      }).then(res=>{
        if (res.data.data == null || res.data.data == undefined){
          message.warn("问题不存在")
          router.push({name:"Home"})
          return false;
        }
        question.value = res.data.data
        question.value.images = JSON.parse(res.data.data.images)
        questionLoading.value = false;
      })

    }

    init()

    const previewImage = (url)=>{
      viewerApi({options: {}, images: [url]})
    }
    return{
      question,
      questionLoading,
      previewImage,
      questionId
    }
  }
}
</script>
<style scoped lang="scss">
  .question-card{
    border: 1px solid #f0f0f0;
    background-color: white;
    padding: 20px;
    margin-top: 20px;
    overflow: hidden;
    .question-title{
      font-size: 26px;
      font-weight: 700;
      color: #2e2e2e;
    }
    .question-content{
      margin-top: 20px;
      color: #333333;
      font-size: 16px;
    }
    .question-image{
      background-color: rgba(237, 237, 239, 0.96);
      padding: 10px;
      margin-top: 20px;
      color: #A1A1AA;
      border-radius: 8px;
    }
    .question-info{
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-top: 20px;
      color: #A1A1AA;
      div:first-child{
        display: flex;
        align-items: center;
      }
    }
  }

  .images{
    img{
      width: 120px;
      height: 120px;
      margin-right: 10px;
      cursor: pointer;
      margin-top: 10px;
    }
  }
</style>