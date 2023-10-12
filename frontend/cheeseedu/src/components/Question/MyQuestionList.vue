<template>
  <a-list size="small" :data-source="list" :locale="{emptyText: '暂无数据'}">
    <template #renderItem="{ item }">
      <a-list-item style="padding: 8px 0 !important;">
        <div style="text-overflow: ellipsis;width: 100%;word-break: keep-all;overflow: hidden">
          <router-link class="link" :to="{name:'Question',params:{id:item.id}}">
            {{item.title}}
          </router-link>
        </div>
      </a-list-item>
    </template>
  </a-list>
</template>

<script>
import {ref} from "vue";
import axios from "../../axios/axios.js"
export default{
  name:"MyQuestionList",
  setup(){
    const list = ref(undefined)
    axios.get("/do/question/getquestionlist.do/" + 5).then(res=>{
      list.value = res.data.data
    })

    return{
      list
    }
  }
}
</script>
<style scoped lang="scss">
.link{
  color: rgba(0, 0, 0, 0.85);
  &:hover{
    color:#00c691
  }
}
</style>