<template>
  <div>
    <a-input placeholder="标题" style="margin-top: 20px" v-model:value="form.title"/>
    <a-input placeholder="封面URL" style="margin-top: 20px" v-model:value="form.coverUrl"/>
    <MyEditor style="margin-top: 20px" v-model:html="form.content" v-model:text="text"/>
    <a-button style="margin-top: 20px" type="primary" @click="update">更新文章</a-button>
  </div>
</template>

<script>
import MyEditor from "../common/MyEditor.vue";
import {reactive, ref} from "vue"
import axios from "../../axios/axios.js"
import {message} from "ant-design-vue"
export default {
  name: "EditArticle",
  components: {
    MyEditor
  },
  props: {
    id: Number
  },
  setup(props) {
    const text = ref("")
    const form = reactive({
      content:"",
      title:"",
      coverUrl:""
    })
    const init = () => {
      axios.get("/do/admin/getarticlebyid.do/" + props.id).then(res=>{
        form.title = res.data.data.title
        form.content = res.data.data.content
        form.coverUrl = res.data.data.coverUrl
      })
    }

    const update = ()=>{
      if (form.coverUrl === "" || form.coverUrl === undefined){
        message.warn("封面URL不能为空")
        return false
      }

      if (form.title === "" || form.title === undefined){
        message.warn("标题不能为空")
        return false
      }

      if (text.value === "" || text.value === undefined){
        message.warn("内容不能为空")
        return false
      }

      axios.post("/do/admin/updatearticlebyid.do",{
        "id":props.id,
        "title":form.title,
        "content":form.content,
        "coverUrl":form.coverUrl,
        "text":text.value
      }).then(res=>{
        if (res.data.code != 200){
          message.error(res.data.data)
          return false
        }
        message.success(res.data.data)
        setTimeout(()=>{
          location.reload()
        },1500)
      })
    }

    init()
    return {
      form,
      text,
      update
    }

  }
}
</script>
<style scoped>

</style>