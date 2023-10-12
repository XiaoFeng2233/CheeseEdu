<template>
    <div class="app-container">
      <a-card title="添加课程" style="max-width: 800px">
        <a-form :label-col="{span:4}" ref="formRef" :model="form" :rules="rules">
          <a-form-item label="课程名称" name="title">
            <a-input v-model:value="form.title" placeholder="请输入课程的名称"/>
          </a-form-item>
          <a-form-item label="选择课程分类" name="category">
            <a-select
                v-model:value="form.category"
                placeholder="请选择课程分类"
                ref="select"
                style="max-width: 150px"
            >
              <a-select-option value="0">暂不分类</a-select-option>
              <a-select-option v-for="(item) in courseCategory" :value="item.id">
                {{item.name}}
              </a-select-option>

            </a-select>
          </a-form-item>
          <a-form-item label="是否上架">
            <a-switch checked-children="上架" un-checked-children="下架" v-model:checked="form.status"/>
          </a-form-item>
          <a-form-item label="封面图片地址" name="image">
            <a-input v-model:value="form.image"/>
          </a-form-item>
          <a-form-item label="收费类型" name="type">
            <a-select
                v-model:value="form.type"
                placeholder="请选择收费类型"
                ref="select"
                style="max-width: 150px"
            >
              <a-select-option value="0">免费</a-select-option>
              <a-select-option value="2">收费</a-select-option>
              <a-select-option value="1">仅会员免费</a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item label="课程价格" name="price" v-if="form.type != 0">
            <a-input prefix="￥" suffix="元" style="max-width: 150px" placeholder="课程价格" v-model:value="form.price"/>
          </a-form-item>
          <a-form-item label="简短介绍" name="short_description">
            <a-textarea :auto-size="{ minRows: 3, maxRows: 6 }" v-model:value="form.short_description"/>
          </a-form-item>
          <a-form-item label="课程介绍">
            <MyEditor v-model:html="form.description" v-model:text="form.description_text"/>
          </a-form-item>
          <a-form-item :wrapper-col="{span:14,offset:4}">
            <a-button type="primary" @click="addCourse">添加</a-button>
          </a-form-item>
        </a-form>
      </a-card>
    </div>
</template>

<script>
import MyEditor from "../../components/common/MyEditor.vue";
import {ref,reactive} from "vue";
import {message} from "ant-design-vue";
import axios from "../../axios/axios.js"
export default{
  name:"AddCourse",
  components:{
    MyEditor
  },
  setup(){

    const courseCategory = ref(undefined)

    axios.get("/do/admin/course/getallcoursecategoryforsale.do").then(res=>{
      if (res.data.code != "200"){
        message.error(res.data.data)
        return false;
      }
      courseCategory.value = res.data.data
    })

    const rules = {
      title: [
        {
          required: true,
          message: '请输入课程的名称',
          trigger: 'blur',
        },
      ],
      image: [
        {
          required: true,
          message: '请输正确的课程封面url',
          trigger: 'blur',
          type:"url"
        },
      ],
      category: [
        {
          required: true,
          message: '请选择课程的分类',
          trigger: 'blur',
          type:"number"
        },
      ],
      type: [
        {
          required: true,
          message: '请选择收费类型',
          trigger: 'blur',
        },
      ],
      price: [
        {
          required: true,
          message: '请输入正确的课程价格',
          trigger: 'blur',
          pattern:/(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/
        },
      ],
      short_description: [
        {
          required: true,
          message: '请输入简短介绍',
          trigger: 'blur',
        },
      ],
    }
    const formRef = ref()
    const form = reactive({
      title:undefined,
      category:undefined,
      status:true,
      image:undefined,
      type:undefined,
      price:undefined,
      short_description:undefined,
      description:undefined,
      description_text:undefined
    })

    const addCourse = ()=>{
      if (form.description == "" || form.description == undefined){
        message.warn("请设置课程介绍")
        return false;
      }
      formRef.value.validate().then(()=>{
        axios.post("/do/admin/course/addcourse.do",{
          title:form.title,
          shortDescription:form.short_description,
          description:form.description,
          type:form.type,
          status:form.status ? 1 : 0,
          categoryId:form.category,
          image:form.image,
          price:form.price
        }).then(res=>{
          if (res.data.code != "200"){
            message.error(res.data.data)
            return false;
          }

          message.success(res.data.data)
          setTimeout(()=>{
            location.reload()
          },1500)
        })
      }).catch(err=>{
        return false;
      })
    }

    return{
      formRef,
      rules,
      form,
      courseCategory,
      addCourse
    }

  }
}
</script>
<style scoped>

</style>