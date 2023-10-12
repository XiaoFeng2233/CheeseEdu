<template>
  <div class="app-container">
    <a-card>
      <a-button type="primary" @click="openAddBannerModal">添加轮播图</a-button>
      <a-modal v-model:visible="isAddBanner" title="添加新的轮播图" :footer="null" :destroyOnClose="true">
        <AddBanner/>
      </a-modal>
    </a-card>
    <a-card style="margin-top: 20px" title="轮播图列表">
      <a-table style="width: 100%;overflow-x: auto" :columns="column" :data-source="data"
               :locale="{emptyText: '暂无数据'}" row-key="id" :pagination="false">
        <template #platform="{text}">
          <div v-if="text == 'pc'">
            <i class="iconfont icon-desktop" style="margin-right: 10px"></i>
            <span>电脑端</span>
          </div>
          <div v-else-if="text == 'mobile'">
            <i class="iconfont icon-mobile" style="margin-right: 10px"></i>
            <span>移动端</span>
          </div>
          <div v-else>
            <i class="iconfont icon-devices" style="margin-right: 10px"></i>
            <span>全部</span>
          </div>
        </template>
        <template #url="{text}">
          <div style="max-width: 200px">
            <a-tooltip placement="top">
              <template #title>
                <span>{{text}}</span>
              </template>
              <a-typography-paragraph
                  style="width: 300px"
                  :ellipsis="true"
                  :content="text"
              />
            </a-tooltip>
          </div>
        </template>
        <template #image="{text}">
          <a-image :width="200" :src="text"  fallback="data:image/png;base64,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"/>
<!--          <a-button style="margin-left: 20px" type="primary" size="small">复制</a-button>-->
        </template>
        <template #action="{text}">
          <a-button style="margin-right: 10px" type="primary" shape="circle" @click="openEditBanner(text)">
            <template #icon>
              <EditOutlined />
            </template>
          </a-button>
          <a-popconfirm
              title="您确定要删除吗?"
              ok-text="确定"
              cancel-text="取消"
              @confirm="deleteBanner(text)"
          >
            <a-button type="primary" danger shape="circle">
              <template #icon>
                <DeleteOutlined />
              </template>
            </a-button>
          </a-popconfirm>
        </template>
      </a-table>
      <a-modal v-model:visible="isEditBanner" title="添加新的轮播图" :footer="null" :destroyOnClose="true">
        <EditBanner :id="bannerId"/>
      </a-modal>
    </a-card>
  </div>
</template>

<script>
import axios from "../axios/axios.js"
import {ref} from "vue"
import {message} from "ant-design-vue";
import {EditOutlined,DeleteOutlined} from "@ant-design/icons-vue"
import AddBanner from "../components/banner/AddBanner.vue";
import EditBanner from "../components/banner/EditBanner.vue";
export default {
  name: "Banner",
  setup() {
    const isAddBanner = ref(false)
    const openAddBannerModal = ()=>{
      isAddBanner.value = true
    }

    const bannerId = ref(undefined)

    const isEditBanner = ref(false)
    const openEditBanner = (id)=>{
      // alert(id)
      isEditBanner.value = true
      bannerId.value = id
    }

    const column = [
      {
        title: '适用平台',
        dataIndex: 'platform',
        key: 'platform',
        slots: {customRender: "platform"}
      },
      {
        title: '排序',
        dataIndex: 'sort',
        key: 'sort',
      },
      {
        title: '轮播图片',
        dataIndex: 'image',
        key: 'image',
        slots: {customRender: "image"}
      },
      {
        title: '跳转Url',
        dataIndex: 'url',
        key: 'url',
        slots: {customRender: "url"}
      },
      {
        title: '操作',
        dataIndex: 'id',
        key: 'target_url',
        slots: {customRender: "action"}
      },
    ]

    const data = ref(undefined)

    axios.get("/do/admin/config/getallbanner.do").then((res) => {
      if (res.data.code != "200") {
        message.error(res.data.data)
        return false;
      }

      data.value = res.data.data
    })

    const deleteBanner = (id)=>{
      axios.get("/do/admin/config/deletebanner.do/" + id).then(res=>{
        if (res.data.code != "200") {
          message.error(res.data.data)
          return false;
        }
        message.success(res.data.data)
        setTimeout(()=>{
          location.reload()
        },1500)
      })
    }

    return {
      column,
      data,
      isAddBanner,
      openAddBannerModal,
      isEditBanner,
      openEditBanner,
      bannerId,
      deleteBanner
    }
  },
  components:{
    DeleteOutlined,
    EditOutlined,
    AddBanner,
    EditBanner
  }
}
</script>
<style scoped>

</style>