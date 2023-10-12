<template>
    <div>
      <a-form layout="vertical">
        <a-form-item label="评分">
          <a-rate v-model:value="comment.rate" />
        </a-form-item>
        <a-form-item label="评价内容">
          <a-textarea
              v-model:value="comment.comment"
              placeholder="请输入对课程的评价"
              :auto-size="{ minRows: 4, maxRows: 6 }"
          />
        </a-form-item>
        <a-form-item style="text-align: right">
          <a-button type="primary" @click="add">发表评论</a-button>
        </a-form-item>
      </a-form>
    </div>
</template>

<script>
import {reactive} from "vue";
import axios from "../../axios/axios.js"
import qs from "qs"
import {message} from "ant-design-vue";
export default{
  name:"CourseCommentDialog",
  props:{
    id:Number
  },
  setup(props){
    const comment = reactive({
      rate:undefined,
      comment:undefined
    })
    const add = ()=>{
      if (comment.rate != undefined && comment.comment != undefined && comment.comment != ""){
        axios.post("/do/course/addcomment.do",qs.stringify({
          courseId:props.id,
          rating:comment.rate,
          comment:comment.comment
        })).then(res=>{
          if (res.data.code != "200") {
            message.error(res.data.data)
            return false
          }
          message.success(res.data.data)
          setTimeout(()=>{
            location.reload()
          },1500)
          return false;
        })
      }else{
        message.warn("请填写所有内容")
        return false;
      }
    }
    return{
      props,
      comment,
      add
    }
  }
}
</script>
<style scoped>

</style>