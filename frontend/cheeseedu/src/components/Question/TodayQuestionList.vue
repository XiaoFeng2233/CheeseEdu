<template>
  <a-list size="small" :locale="{emptyText: '暂无数据'}" :data-source="list">
    <template #renderItem="{ item }">
      <a-list-item style="padding: 8px 0 !important;">
        <a-list-item-meta>
          <template #title>
            <router-link class="link" :to="{name:'Question',params:{id:item.id}}">{{ item.title }} </router-link>
          </template>
          <template #avatar>
            <a-avatar :src="item.userAvatar"/>
          </template>
        </a-list-item-meta>
      </a-list-item>
    </template>
  </a-list>
</template>

<script>
import {ref} from "vue";
import axios from "../../axios/axios.js"
export default{
  name:"TodayQuestionList",
  setup(){
    const list = ref(undefined);
    axios.get("/do/common/gettodayquestion.do/" + 5).then(res=>{
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