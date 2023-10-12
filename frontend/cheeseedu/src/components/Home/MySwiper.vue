<template>
  <div>
  <a-skeleton :paragraph="{ rows: 6 }" v-if="loading" active :title="false"/>
  <swiper v-else class="my-swiper" :pagination="true" :autoplay='{"delay": 2500,"disableOnInteraction": false}' :navigation="true">
    <swiper-slide v-for="item in bannerList">
      <img :src="item.image" alt="" @click="handleClick(item.url)">
    </swiper-slide>
  </swiper>
  </div>
</template>

<script>
import {Swiper,SwiperSlide} from "swiper/vue"
import SwiperCore,{Pagination,Autoplay,Navigation} from "swiper/core"
import 'swiper/swiper.scss';
import "swiper/components/pagination/pagination.min.css"
import "swiper/components/navigation/navigation.min.css"
import axios from "../../axios/axios.js"
import {message} from "ant-design-vue";
import {ref} from "vue";
SwiperCore.use([Pagination,Autoplay,Navigation])
export default{
  name:"MySwiper",
  components:{
    Swiper,SwiperSlide
  },
  setup(){
    const bannerList = ref([])
    const loading = ref(true);
    axios.get("/do/common/getpcbanner.do").then((res)=>{
      if(res.data.code != "200"){
        message.error(res.data.data)
        return false;
      }
      bannerList.value = res.data.data
      loading.value = false
    })

    const handleClick = (url)=>{
      window.open(url);
    }
    return{
      bannerList,
      loading,
      handleClick
    }
  }
}
</script>
<style scoped lang="scss">
.my-swiper{
  cursor:grab;
  height: 500px;
  border-radius: 10px;
  width: 100%;
  img{
    width: 100%;
    height: 100%;
  }

  &:active{
    cursor: grabbing;
  }
}
</style>