<template>
  <div>
    <a-table :columns="column" row-key="id" :data-source="data" :pagination="false" :loading="loading"
             :locale="{emptyText: '暂无数据'}">
      <template #event="{text}">
        登入账户
      </template>
    </a-table>
    <p style="color: #888888">仅显示最近20条记录</p>
  </div>
</template>

<script>
import {ref} from "vue";
import axios from "../../axios/axios.js"

export default {
  name: "Log",
  setup() {
    const column = [
      {
        title: '事件',
        dataIndex: 'event',
        slots: {customRender: "event"},
        width: 200
      },
      {
        title: '时间',
        dataIndex: 'createdTime',
        key: 'createdTime',
      },
      {
        title: "登入IP",
        dataIndex: "ip",
        key: "ip"
      },
      {
        title: "登入区域",
        dataIndex: "area",
        key: "area"
      }
    ]

    const data = ref(undefined)
    const loading = ref(true)
    const init = () => {
      axios.get("/do/user/getlog.do").then(res => {
        data.value = res.data.data
        loading.value = false
      })
    }

    init()

    return {
      column,
      data,
      loading
    }
  }

}
</script>
<style scoped>

</style>