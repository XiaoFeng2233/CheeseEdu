<template>
  <a-list item-layout="horizontal" :data-source="commentList" :locale="{	emptyText: '暂无数据'}">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-list-item-meta
            :description="item.content"
        >
          <template #title>
            <a href="https://www.antdv.com/">{{ item.nickName }}</a>
            <a-rate disabled	:value="item.star" allow-half style="margin-left: 40px"/>
          </template>
          <template #avatar>
            <a-avatar :src="item.avatar" />
          </template>
        </a-list-item-meta>
      </a-list-item>
    </template>
  </a-list>
</template>

<script>
import {toRefs} from "vue";

export default{
  name:"CourseComment",
  props:{
    commentList:Array
  },
  setup(props){
    const {commentList} = toRefs(props)
    const data = [
      {
        title: 'Ant Design Title 1',
      },
      {
        title: 'Ant Design Title 2',
      },
      {
        title: 'Ant Design Title 3',
      },
      {
        title: 'Ant Design Title 4',
      },
    ];
    return{
      data,
      commentList
    }
  }
}
</script>
<style scoped>

</style>