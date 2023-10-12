<template>
    <div class="app-container">
        <a-card title="订单详情">
          <a-form
              layout="inline"
          >
            <a-form-item label="订单号">
              <a-input placeholder="输入订单号搜索" v-model:value="orderNumValue">
                <template #prefix><UserOutlined style="color: rgba(0, 0, 0, 0.25)" /></template>
              </a-input>
            </a-form-item>
            <a-form-item>
              <a-button
                  type="primary"
                  html-type="submit"
                  @click="searchOrder"
              >
                搜索
              </a-button>
            </a-form-item>
<!--            <a-form-item label="订单状态">-->
<!--              <a-select placeholder="请选择" style="width: 200px">-->
<!--                <a-select-option value="shanghai">已支付</a-select-option>-->
<!--                <a-select-option value="shanghai">未支付</a-select-option>-->
<!--                <a-select-option value="shanghai">待支付</a-select-option>-->
<!--                <a-select-option value="shanghai">已取消</a-select-option>-->
<!--              </a-select>-->
<!--            </a-form-item>-->
<!--            <a-form-item>-->
<!--              <a-button-->
<!--                  type="primary"-->
<!--                  html-type="submit"-->
<!--              >-->
<!--                查找-->
<!--              </a-button>-->
<!--            </a-form-item>-->
          </a-form>
          <a-table style="margin-top: 20px" :dataSource="orderList" :columns="columns" :locale="{emptyText: '暂无数据'}" :pagination="paginationOpt" row-key="id">
            <template #status="{text}">
              <a-tag v-if="text == 0">已超时</a-tag>
              <a-tag v-else color="cyan">未超时</a-tag>
            </template>
            <template #isPayOk="{text}">
              <a-tag color="green" v-if="text == 1">已支付</a-tag>
              <a-tag v-if="text == 0">待支付</a-tag>
            </template>
            <template #action="{text}">
              <a-button type="link" @click="openDetailModal(text)">查看详情</a-button>
            </template>
          </a-table>
        </a-card>
      <a-modal v-model:visible="orderDetailModalVisible" :footer="false" :destroy-on-close="true" width="1200px">
        <OrderDetail :order-id="orderDetailModalId"/>
      </a-modal>
    </div>
</template>

<script>
import axios from "../axios/axios.js"
import {ref,reactive} from "vue";
import qs from "qs";
import {message} from "ant-design-vue";
import OrderDetail from "../components/order/OrderDetail.vue";
export default{
  name:"Order",
  components:{
    OrderDetail
  },
  setup(){
    const columns =  [
      {
        title: 'ID',
        dataIndex: 'id',
        key: 'id',
      },
      {
        title: '订单号',
        dataIndex: 'orderNum',
        key: 'orderNum',
      },
      {
        title: '用户名',
        dataIndex: 'username',
        key: 'username',
      },
      {
        title: '用户ID',
        dataIndex: 'userId',
        key: 'userId',
      },
      {
        title: '订单名称',
        dataIndex: 'orderName',
        key: 'orderName',
      },
      {
        title: '创建时间',
        dataIndex: 'createdTime',
        key: 'createdTime',
      },
      {
        title: '更新时间',
        dataIndex: 'updatedTime',
        key: 'updatedTime',
      },
      {
        title: '金额',
        dataIndex: 'price',
        key: 'price',
      },
      {
        title: '支付状态',
        dataIndex: 'isPayOk',
        key: 'isPayOk',
        slots:{
          customRender:"isPayOk"
        }
      },
      {
        title: '订单状态',
        dataIndex: 'status',
        key: 'status',
        slots:{
          customRender:"status"
        }
      },
      {
        title: '操作',
        dataIndex: 'id',
        key: 'action',
        slots: {
          customRender: "action"
        }
      },
    ]
    const orderList = ref(undefined)
    const paginationOpt = reactive({
      defaultCurrent: 1, // 默认当前页数
      defaultPageSize: 10, // 默认当前页显示数据的大小
      total: 0, // 总数，必须先有
      showSizeChanger: false,
      showQuickJumper: true,
      showTotal: (total) => `共 ${total} 条`, // 显示总数
      onChange: (current, size) => {
        paginationOpt.defaultCurrent = current;
        paginationOpt.defaultPageSize = size;
        axios.post("/do/admin/getorder.do",qs.stringify({
          page:current,
          count:size
        })).then(res=>{
          if (res.data.code != "200"){
            message.error(res.data.data)
            return false;
          }
          orderList.value = res.data.data.records
          paginationOpt.total = res.data.data.total
          paginationOpt.defaultPageSize = res.data.data.size
          paginationOpt.defaultCurrent = res.data.data.current
        })
      },
    })
    const init = ()=>{
      axios.post("/do/admin/getorder.do",qs.stringify({
        page:paginationOpt.defaultCurrent,
        count:paginationOpt.defaultPageSize
      })).then(res=>{
        if (res.data.code != "200"){
          message.error(res.data.data)
          return false;
        }
        orderList.value = res.data.data.records
        paginationOpt.total = res.data.data.total
        paginationOpt.defaultPageSize = res.data.data.size
        paginationOpt.defaultCurrent = res.data.data.current
      })
    }
    init()

    const orderDetailModalVisible = ref(false)
    const orderDetailModalId = ref(undefined)

    const openDetailModal = (id)=>{
      orderDetailModalVisible.value = true
      orderDetailModalId.value = id
    }

    const orderNumValue = ref(undefined)
    const searchOrder = ()=>{
      if (orderNumValue.value == undefined || orderNumValue.value == ""){
        message.warn("请输入订单的单号")
        return false;
      }
      axios.get("/do/admin/getorderbyordernum.do/" + orderNumValue.value).then(res=>{
        let temp = []
        temp.push(res.data.data)
        orderList.value = temp
      })
    }

    return{
      paginationOpt,
      orderList,
      columns,
      openDetailModal,
      orderDetailModalVisible,
      orderDetailModalId,
      searchOrder,
      orderNumValue
    }
  }
}
</script>
<style scoped>

</style>