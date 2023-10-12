<template>
  <div class="answer-card">
    <a-skeleton :loading="replyListLoading" active :paragraph="{ rows: 5}">
      <a-list item-layout="horizontal" :data-source="replyList" :locale="{emptyText: '暂无回答'}">
        <template #renderItem="{ item }">
          <a-list-item>
            <template #actions>
              <div class="images">
                <img :src="i" alt="" v-for="i in item.images" style="width: 50px;height: 50px" @click="previewImage(i)">

              </div>
            </template>
            <a-list-item-meta
            >
              <template #description>
                <span style="margin-right: 20px">{{ item.userNickName }}</span>
                <span>{{ item.createdTime.split(' ')[0] }}</span>
              </template>
              <template #title>
                <span>{{ item.content }}</span>
              </template>
              <template #avatar>
                <a-avatar :src="item.avatar"/>
              </template>
            </a-list-item-meta>
          </a-list-item>
        </template>
      </a-list>
    </a-skeleton>

  </div>
</template>

<script>
import {api as viewerApi} from "v-viewer";
import axios from "../../axios/axios";
import {message} from "ant-design-vue";
import {ref} from "vue";
import {useRoute, useRouter} from "vue-router";

export default {
  name: "QuestionReplyList",
  props:{
    questionId:String
  },
  setup(props) {
    const route = useRoute();
    const router = useRouter();
    const replyList = ref([])
    const replyListLoading = ref(true)
    const init = ()=>{
      axios.get("/do/common/getquestionreply.do/" + props.questionId).then(res=>{
        if (res.data.data == null || res.data.data == undefined){
          message.warn("问题不存在")
          router.push({name:"Home"})
          return false;
        }
        const temp = res.data.data
        temp.forEach(elm=>{
          let t = elm.images
          elm.images = JSON.parse(t)
          replyList.value.push(elm)
        })
        replyListLoading.value = false
      })
    }

    init()
    const previewImage = (url)=>{
      viewerApi({options: {}, images: [url]})
    }

    return{
      previewImage,
      replyListLoading,
      replyList
    }
  }
}
</script>
<style scoped lang="scss">

.answer-card{
  border: 1px solid #f0f0f0;
  background-color: white;
  padding: 20px;
  margin-top: 20px;
}
</style>