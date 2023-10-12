<template>
  <div class="app-container">
    <a-modal v-model:visible="modalShow" :title="title" :footer="false" width="700px">
      <div v-html="html"></div>
    </a-modal>
    <a-card title="发布新的文章">
      <a-input v-model:value="title" placeholder="文章标题"/>
      <a-input v-model:value="cover" placeholder="封面图片url" style="margin-top: 20px"/>
      <MyEditor v-model:text="text" v-model:html="html" style="margin-top: 20px"/>
      <a-button type="primary" style="margin-top: 20px;margin-right: 20px" @click="release">发布</a-button>
      <a-button type="primary" ghost style="margin-top: 20px" @click="modalShow = true">预览</a-button>
    </a-card>
  </div>
</template>

<script>
import MyEditor from "../../components/common/MyEditor.vue";
import {message} from "ant-design-vue"
import {ref} from "vue"
import axios from "../../axios/axios.js"

export default {
  name: "AddArticle",
  components: {MyEditor},
  setup() {
    const title = ref("")
    const html = ref("");
    const text = ref("")
    const cover = ref("")
    const modalShow = ref(false)
    const release = () => {
      if (title.value === "" || title.value === undefined) {
        message.warn("文章标题不能为空")
        return false;
      }

      if (text.value === "" || text.value === undefined) {
        message.warn("文章内容不能为空")
        return false;
      }

      if (cover.value === "" || cover.value === undefined){
        message.warn("封面图片URL不能为空")
        return false;
      }

      axios.post("/do/admin/addarticle.do", {
        "title": title.value,
        "content": html.value,
        "coverUrl":cover.value,
        "text":text.value
      }).then(res => {
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

    return {
      html,
      text,
      title,
      modalShow,
      cover,
      release
    }
  }
}
</script>
<style scoped>

</style>