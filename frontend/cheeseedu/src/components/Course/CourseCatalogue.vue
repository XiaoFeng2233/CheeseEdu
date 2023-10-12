<template>
    <div>
      <a-empty  style="text-align: center" v-if="chapters.length <= 0 ">
        <template #description>
        站长还未更新相关章节
        </template>
      </a-empty>
      <a-collapse v-model:activeKey="activeCatalogue" v-else>
        <a-collapse-panel :key="arr.id" :header="arr.name" style="background-color: white" v-for="arr in chapters">
          <a-list size="small" :locale="{emptyText:'暂无数据'}">
              <a-list-item v-for="item in arr.videoList">
                <div style="display: flex;justify-content: space-between;width: 100%;cursor: pointer">
                  <a style="color: rgba(0, 0, 0, 0.85)">{{item.title}}</a>
                  <span>{{item.videoDuration}}</span>
                </div>
              </a-list-item>
          </a-list>
<!--          <a-list size="small" :data-source="arr.videoList" :locale="{emptyText:'暂无数据'}">-->
<!--            <template #renderItem="{ item }">-->
<!--              <a-list-item>-->
<!--                <div style="display: flex;justify-content: space-between;width: 100%;cursor: pointer">-->
<!--                  <a style="color: rgba(0, 0, 0, 0.85)">{{ item }}</a>-->
<!--                  <span>{{item.name}}</span>-->
<!--                </div>-->
<!--              </a-list-item>-->
<!--            </template>-->
<!--          </a-list>-->
        </a-collapse-panel>
      </a-collapse>
    </div>
</template>

<script>
import {ref} from "vue";
import axios from "../../axios/axios.js"
export default{
  name:"Course",
  props:{
    courseId:String
  },
  setup(props){
    const activeCatalogue = ref([""])
    const chapters = ref([])
    const init = ()=>{
      axios.get("/do/common/getcoursechapter.do/" + props.courseId).then(res=>{
        chapters.value = res.data.data
        chapters.value.forEach((e,index)=>{
          if (e.videoList.length>0){
            let videoList = e.videoList
            videoList.forEach((i,ind)=>{
              let duration = i.videoDuration;
              if(duration<=60){
                chapters.value[index].videoList[ind].videoDuration = "00:" + duration
              }else{
                let minute = parseInt(duration/60)
                let second = duration%60
                chapters.value[index].videoList[ind].videoDuration = minute + ":" + second
              }

            })
          }
        })
      })
    }

    init()
    return{
      activeCatalogue,
      props,
      chapters
    }
  }
}
</script>
<style scoped>

</style>