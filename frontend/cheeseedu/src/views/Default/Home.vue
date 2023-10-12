<template>
  <div class="container">
    <img src="https://qr.alipay.com/bax065469mo1wgh7k5xi0036" alt="">
    <MySwiper style="margin-top: 20px;"/>

    <div class="course-list">
      <h1 style="text-align: center">
        新课上线
      </h1>
      <a-skeleton :loading="latestLoading" :active="true">
        <a-row :gutter="[24,24]">
          <a-col :span="6" v-for="item in latestCourse">
            <router-link :to="'/course/' + item.id">
              <a-card hoverable>
                <template #cover>
                  <img
                      style="width: 280px;height: 150px"
                      alt="example"
                      :src="item.image"
                  />
                </template>
                <template class="ant-card-actions" #actions>
                  <div>
                    <UserOutlined/>
                    {{item.studentNumber}}
                  </div>
                  <div class="course-price">
                  <span class="price" v-if="item.type == 2">
                    <span>¥ </span>
                    <span>{{item.price}}</span>
                  </span>
                    <span class="price" v-else-if="item.type == 1">
                    <span>¥ </span>
                    <span>{{item.price}}</span>
                    <span class="vip-free">(会员免费)</span>
                  </span>
                  </div>
                </template>
                <a-card-meta>
                  <template #title>
                    {{ item.title }}
                  </template>
                  <template #description>
                    <div style="text-overflow: ellipsis;white-space: nowrap;overflow: hidden">
                      {{ item.shortDescription }}
                    </div>
                  </template>
                </a-card-meta>
              </a-card>
            </router-link>
          </a-col>
        </a-row>
      </a-skeleton>
    </div>

    <div class="course-list">
      <h1 style="text-align: center">
        免费课程
      </h1>
      <a-skeleton :loading="freeLoading" :active="true">
        <a-row :gutter="[24,24]">
          <a-col :span="6" v-for="item in freeCourse">
            <router-link :to="'/course/' + item.id">
            <a-card hoverable>
              <template #cover>
                <img
                    style="width: 280px;height: 150px"
                    alt="example"
                    :src="item.image"
                />
              </template>
              <template class="ant-card-actions" #actions>
                <div>
                  <UserOutlined/>
                  {{item.studentNumber}}
                </div>
                <div class="course-price">
                  <span class="free">免费</span>
                </div>
              </template>
              <a-card-meta>
                <template #title>
                  {{ item.title }}
                </template>
                <template #description>
                  <div style="text-overflow: ellipsis;white-space: nowrap;overflow: hidden">
                    {{ item.shortDescription }}
                  </div>
                </template>
              </a-card-meta>
            </a-card>
            </router-link>
          </a-col>
        </a-row>
      </a-skeleton>
    </div>


    <div class="article-list">
      <h1 style="text-align: center">
        推荐阅读
      </h1>

      <a-row :gutter="[24,12]">
        <a-col :span="12" v-for="item in articleList">
          <router-link :to="'/article/' + item.id">
          <a-card hoverable>
            <a-row :gutter="36">
              <a-col :span="10">
                <img style="width: 100%;height: 100%" :src="item.coverUrl"
                     alt="">
              </a-col>
              <a-col :span="14">
                <div class="article-title">
                  {{item.title}}
                </div>
                <div class="article-text">
                  {{item.text}}
                </div>
                <div class="article-info">
                  <span>{{item.createdTime.split(' ')[0]}}</span>
                  <span>{{item.viewNumbers}}人阅读</span>
                </div>
              </a-col>
            </a-row>
          </a-card>
          </router-link>
        </a-col>
      </a-row>
    </div>


  </div>
</template>

<script>
import {UserOutlined} from "@ant-design/icons-vue"
import MySwiper from "../../components/Home/MySwiper.vue";
import axios from "../../axios/axios.js"
import {ref, reactive} from "vue"
import {message} from "ant-design-vue"

export default {
  name: "Home",
  components: {
    MySwiper,
    UserOutlined
  },
  setup() {
    const latestLoading = ref(true)
    const freeLoading = ref(true)
    const freeCourse = ref(undefined)
    const latestCourse = ref(undefined)
    axios.get("/do/common/getfreecourse.do").then(res => {
      if (res.data.code != "200") {
        message.error(res.data.data)
        return false;
      }
      freeCourse.value = res.data.data
      freeLoading.value = false
    })
    axios.get("/do/common/getlatestcourse.do").then(res => {
      if (res.data.code != "200") {
        message.error(res.data.data)
        return false;
      }

      latestCourse.value = res.data.data
      latestLoading.value = false
    })

    const articleList = ref(undefined)

    const articleInit = ()=>{
      axios.get("/do/common/getarticlebyviewnumber.do/" + 10).then(res=>{
        articleList.value = res.data.data
      })
    }

    articleInit()

    return {
      latestLoading,
      freeLoading,
      freeCourse,
      latestCourse,
      articleList
    }
  }
}
</script>
<style scoped lang="scss">
.course-list {
  margin-top: 20px;
}

.course-price {
  font-size: 16px;
  font-weight: 700;

  span.free {
    color: #00c691;
  }

  span.price {
    color: #ff7875
  }

  span.vip-free {
    font-weight: normal;
    color: rgba(0, 0, 0, 0.45);
  }
}

.article-list {
  margin-top: 20px;
}

.article-title {
  color: #333333;
  font-size: 20px;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
}

.article-text {
  text-overflow: ellipsis;
  overflow: hidden;
  color: #999999;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  display: -webkit-box;
  height: 45px;
}

.article-info {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  color: #333333;

}
</style>