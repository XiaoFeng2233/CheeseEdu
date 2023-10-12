<template>
    <div class="app-container">
      <a-card>
        <a-button type="primary" @click="isAddCategory = true">添加分类</a-button>
        <a-modal v-model:visible="isAddCategory" title="添加新的课程分类" :footer="null" :destroyOnClose="true">
          <AddCourseCategory/>
        </a-modal>
      </a-card>
      <a-modal v-model:visible="isEditCategory" title="添加新的课程分类" :footer="null" :destroyOnClose="true">
          <EditCourseCategory :id="categoryId"/>
      </a-modal>
      <a-card style="margin-top: 20px" title="所有分类">
        <a-table :columns="columns" :data-source="data" row-key="id" :locale="{emptyText: '暂无数据'}" :pagination="false">
          <template #status="{text}">
            <a-tag color="success" v-if="text == 1">上架</a-tag>
            <a-tag color="error" v-else>下架</a-tag>
          </template>
          <template #action="{text}">
            <a-button style="margin-right: 10px" type="primary" shape="circle" @click="EditCategory(text)">
              <template #icon>
                <EditOutlined />
              </template>
            </a-button>
            <a-button type="primary" danger shape="circle" @click="deleteCategory(text)">
              <template #icon>
                <DeleteOutlined />
              </template>
            </a-button>
          </template>
        </a-table>
      </a-card>
    </div>
</template>

<script>
import {ref} from "vue"
import  axios from "axios"
import {message} from "ant-design-vue";
import {DeleteOutlined,EditOutlined} from "@ant-design/icons-vue"
import AddCourseCategory from "../../components/coursecategory/AddCourseCategory.vue";
import EditCourseCategory from "../../components/coursecategory/EditCourseCategory.vue";
export default{
  name:"CourseCategory",
  components:{
    DeleteOutlined,
    EditOutlined,
    AddCourseCategory,
    EditCourseCategory
  },
  setup(){
    const columns = [
      {
        title:"编号",
        dataIndex:"id",
        key:"id"
      },
      {
        title:"名称",
        dataIndex:"name",
        key:"name"
      },
      {
        title:"状态",
        dataIndex: "status",
        key:"status",
        slots: {customRender: "status"}
      },
      {
        title:"操作",
        dataIndex: 'id',
        slots: {customRender: "action"}
      }
    ]

    const isAddCategory = ref(false)

    const data = ref(undefined)

    axios.get("/do/admin/course/getallcoursecategory.do").then(res=>{
      if(res.data.code != "200"){
        message.error(res.data.data)
        return false;
      }
      data.value = res.data.data
    })

    const categoryId = ref(undefined)
    const isEditCategory = ref(false)
    const EditCategory  = (id)=>{
      categoryId.value = id
      isEditCategory.value = true
    }

    const deleteCategory = (id)=>{
      axios.get("/do/admin/course/deletecategory.do/" + id).then(res=>{
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
      isAddCategory,
      columns,
      data,
      categoryId,
      isEditCategory,
      EditCategory,
      deleteCategory
    }
  }
}
</script>
<style scoped>

</style>