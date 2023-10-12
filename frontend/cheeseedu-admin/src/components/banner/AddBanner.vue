<template>
  <a-form layout="horizontal" ref="formRef" :model="form" :label-col="{span:4}" :rules="rules">
    <a-form-item label="选择平台" name="platform">
      <a-select
          placeholder="请选择平台"
          ref="select"
          v-model:value="form.platform"
      >
        <a-select-option value="all">全部</a-select-option>
        <a-select-option value="pc">移动端</a-select-option>
        <a-select-option value="mobile">电脑端</a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item label="名称" name="name">
      <a-input v-model:value="form.name" placeholder="输入排序数字,数字越大越靠前"/>
    </a-form-item>
    <a-form-item label="排序" name="sort">
      <a-input-number v-model:value="form.sort" placeholder="输入排序数字,数字越大越靠前"/>
    </a-form-item>
    <a-form-item label="图片URL" name="imageUrl">
      <a-input v-model:value="form.imageUrl" placeholder="轮播图的图片地址"/>
    </a-form-item>
    <a-form-item label="跳转URL" name="url">
      <a-input v-model:value="form.url" placeholder="点击轮播图的跳转地址"/>
    </a-form-item>
    <a-form-item :wrapper-col="{span:14,offset:4}">
      <a-button type="primary" @click="addBanner">创建</a-button>
    </a-form-item>
  </a-form>
</template>

<script>
import {ref, reactive} from "vue"
import axios from "../../axios/axios.js";
import {message} from "ant-design-vue"

export default {
  name: "AddBanner",
  setup() {
    const formRef = ref()
    const rules = {
      name: [
        {
          required: true,
          message: '请输入轮播图的名称',
          trigger: 'blur',
        },
      ],
      imageUrl: [
        {
          required: true,
          message: '请输入轮播图的图片地址',
          trigger: 'blur',
        },
      ],
      url: [
        {
          required: true,
          message: '请输入跳转的地址',
          trigger: 'blur',
        },
      ],
      platform: [
        {
          required: true,
          message: '请选择平台',
          trigger: 'blur',
        },
      ],
      sort: [
        {
          required: true,
          message: '请输入正确的排序值',
          trigger: 'blur',
          type: "integer"
        },
      ]
    }

    const form = reactive({
      name: "",
      platform: "all",
      sort: "0",
      imageUrl: "",
      url: ""
    })

    const addBanner = () => {

      formRef.value.validate().then(() => {
        axios.post("/do/admin/config/addbanner.do", {
          url: form.url,
          name: form.name,
          image: form.imageUrl,
          platform: form.platform,
          sort: form.sort
        }).then(res => {
          if (res.data.code != "200") {
            message.error(res.data.data)
            return false;
          }
          message.success("添加成功")
          setTimeout(() => {
            location.reload()
          }, 1500)
        })
      }).catch(err=>{
        return false;
      })
    }

    return {
      form,
      formRef,
      rules,
      addBanner
    }
  }
}
</script>
<style scoped>

</style>