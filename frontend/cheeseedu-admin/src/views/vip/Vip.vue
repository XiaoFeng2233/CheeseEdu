<template>
  <div class="app-container">
    <a-row :gutter="[48,48]">
      <a-col :span="6" v-for="item in packetList">
        <a-card hoverable>
          <template #cover>
            <img
                src="../../assets/images/vip.svg"
            />
          </template>
          <template class="ant-card-actions" #actions>
            <a-popconfirm
                title="确定要删除这个套餐吗?"
                ok-text="确定"
                cancel-text="取消"
                @confirm="deletePackage(item.id)"
            >
              <div>
                <DeleteOutlined/>
              </div>
            </a-popconfirm>
            <div @click="isEdit = true;editPackageId = item.id">
              <EditOutlined/>
            </div>
          </template>
          <div class="vip-box">
            <div class="vip-name">
              {{ item.name }}
            </div>
            <div class="vip-time">
              {{ item.duration }}天
            </div>
            <div class="vip-price">
              ¥{{ item.price }}
            </div>
          </div>
        </a-card>
      </a-col>
    </a-row>
    <a-modal v-model:visible="isEdit" title="编辑会员套餐" :footer="false" :destroy-on-close="true">
      <EditVipPackage :id="editPackageId"/>
    </a-modal>
  </div>
</template>

<script>
import {ref} from "vue"
import {EditOutlined, DeleteOutlined} from "@ant-design/icons-vue"
import axios from "../../axios/axios.js"
import {message} from "ant-design-vue"
import EditVipPackage from "../../components/vip/EditVipPackage.vue";
export default {
  name: "Vip",
  components: {
    EditOutlined,
    DeleteOutlined,
    EditVipPackage
  },
  setup() {

    const packetList = ref(undefined)

    axios.get("/do/admin/getallvippackage.do").then(res => {
      packetList.value = res.data.data
    })

    const deletePackage = (id)=>{
      axios.get("/do/admin/deletevippackage.do/" + id).then(res=>{
        if (res.data.code != 200){
          message.error(res.data.data)
          return false
        }

        message.success(res.data.data)
        setTimeout(()=>{
          location.reload()
        },1500)
      })
    }

    const isEdit = ref(false)
    const editPackageId = ref(undefined)

    return {
      packetList,
      deletePackage,
      isEdit,
      editPackageId
    }
  }
}
</script>
<style scoped lang="scss">
.vip-box {
  text-align: center;

  .vip-name {
    font-size: 20px;
    font-weight: 700;
  }

  .vip-time {
    margin: 10px 0;
    font-size: 18px;
  }

  .vip-price {
    font-size: 18px;
    color: #5473e7;
    font-weight: 500;
  }
}
</style>