<template>
  <div class="app-container">
    <a-card title="文章列表">
      <template #extra>
        <router-link :to="{name:'AddArticle'}">
          <a-button type="primary">创建文章</a-button>
        </router-link>
      </template>

      <a-table :locale="{emptyText: '暂无数据'}" :columns="columns" :data-source="data" :pagination="false" row-key="id">
        <template #article-title="{text}">
          <div style="max-width: 300px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis">
            {{text}}
          </div>
        </template>
        <template #cover="{text}">
          <img :src="text" alt="" style="width: 200px;height: 80px">
        </template>

        <template #content="{text}">
          <a-button type="primary" ghost @click="showContent = text;isShowContent = true">点击查看</a-button>
        </template>

        <template #action="{text}">
          <a-button style="margin-right: 10px" type="primary" @click="isShowEditPanel = true;editId = text">
            编辑
          </a-button>
          <a-popconfirm
              title="是否确定删除这篇文章"
              ok-text="确定"
              cancel-text="取消"
              @confirm="confirmDelete(text)"
          >
          <a-button type="danger" ghost>删除</a-button>
          </a-popconfirm>
        </template>
      </a-table>
    </a-card>
    <a-modal v-model:visible="isShowContent" :footer="false" :destroyOnClose="true" width="700px">
      <div v-html="showContent"></div>
    </a-modal>

    <a-modal v-model:visible="isShowEditPanel" :footer="false" :destroyOnClose="true" width="700px">
      <EditArticle :id="editId"/>
    </a-modal>
  </div>
</template>

<script>
import axios from "../../axios/axios.js"
import {ref} from "vue";
import EditArticle from "../../components/article/EditArticle.vue";
import {message} from "ant-design-vue";
export default {
  name: "ArticleList",
  components:{
    EditArticle
  },
  setup() {
    const columns = [
      {
        title: "ID",
        dataIndex: "id",
        key: 'id'
      },
      {
        title: "封面",
        dataIndex: "coverUrl",
        width:200,
        slots:{
          customRender:"cover"
        }
      },
      {
        title: "标题",
        dataIndex: "title",
        width:300,
        slots:{
          customRender: "article-title"
        }
      },
      {
        title: "创建时间",
        dataIndex: "createdTime",
        key: "createdTime"
      },
      {
        title: "内容",
        dataIndex: "content",
        slots:{
          customRender:"content"
        }
      },
      {
        title: "查看人数",
        dataIndex: "viewNumbers",
        key: "viewNumbers"
      },
      {
        title:"操作",
        dataIndex: "id",
        slots:{
          customRender:"action"
        }
      }
    ]

    const data = ref(undefined)

    const init = ()=>{
      axios.get("/do/admin/getarticlelist.do").then(res=>{
        data.value = res.data.data
      })
    }

    init()

    const isShowContent = ref(false)
    const showContent = ref(undefined)

    const confirmDelete = (id)=>{
      axios.get("/do/admin/deletearticlebyid.do/" + id).then(res=>{
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

    const isShowEditPanel = ref(false)
    const editId = ref(undefined)
    return {
      columns,
      data,
      isShowContent,
      showContent,
      isShowEditPanel,
      editId,
      confirmDelete
    }
  }
}
</script>
<style scoped>

</style>