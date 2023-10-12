<template>
  <div class="app-container">
    <a-card title="添加新用户" style="max-width: 40%">
      <a-form :label-col="{span:8}" layout="vertical" ref="formRef" :rules="rules" :model="form">
        <a-form-item label="手机号" name="phoneNumber">
          <a-input v-model:value="form.phoneNumber"/>
        </a-form-item>
        <a-form-item label="用户名" name="userName">
          <a-input v-model:value="form.userName"/>
        </a-form-item>
        <a-form-item label="密码" name="password">
          <a-input v-model:value="form.password"/>
        </a-form-item>
        <a-form-item label="昵称" name="nickName">
          <a-input v-model:value="form.nickName"/>
        </a-form-item>
        <a-form-item label="设置网站管理员" name="isAdmin">
          <a-switch checked-children="是" un-checked-children="否" v-model:checked="form.isAdmin"/>
        </a-form-item>
        <a-form-item label="开通会员" name="isVip">
          <a-switch checked-children="开通" un-checked-children="关闭" v-model:checked="form.isVip"/>
        </a-form-item>
        <a-form-item label="会员级别" name="vipPackage" v-if="form.isVip">
          <a-select style="width: 300px" v-model:value="form.vipPackage" placeholder="请选择要设置的会员级别">
            <a-select-option :value="item.id" v-for="item in vipPackageList">
              {{item.name}}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="到期时间" v-if="form.isVip" name="vipExpiredTime">
          <a-date-picker :disabled-date="disabledDate"  show-time placeholder="请选择到期日期" :locale="locale" v-model:value="form.vipExpiredTime"/>
        </a-form-item>

        <a-form-item>
          <a-button type="primary" @click="addUser">保存</a-button>
        </a-form-item>
      </a-form>
    </a-card>
  </div>
</template>

<script>
import locale from 'ant-design-vue/es/date-picker/locale/zh_CN';
import moment from "moment";
import axios from "../../axios/axios.js"
import {reactive, ref} from "vue"
import {message} from "ant-design-vue"
export default {
  name: "AddUser",
  setup(){
    const disabledDate = current => {
      return current && current < moment().endOf('day');
    };
    const formRef = ref()
    const rules = {
      phoneNumber: [
        {
          required: true,
          message: '请输入正确的手机号码',
          trigger: 'blur',
          pattern:/^1[34578]\d{9}$/
        },
      ],
      userName: [
        {
          required: true,
          message: '请输入正确的用户名',
          trigger: 'blur',
        },
      ],
      password: [
        {
          required: true,
          message: '请输入正确的用户名',
          trigger: 'blur',
        },
      ],
      nickName: [
        {
          required: true,
          message: '请输入正确的用户名',
          trigger: 'blur',
        },
      ],
      vipPackage: [
        {
          required: true,
          message: '请选择会员套餐',
          trigger: 'blur',
          type:'number'
        },
      ],
      vipExpiredTime: [
        {
          required: true,
          message: '请选择到期的时间',
          trigger: 'blur',
          type:'date'
        },
      ],
      isAdmin: [
        {
          required: true,
          message: '请选择是否设置为管理员',
          trigger: 'blur',
          type:"boolean",
        },
      ],
      isVip: [
        {
          required: true,
          message: '请选择是否设置为会员',
          trigger: 'blur',
          type:"boolean",
        },
      ],
    }

    const form = reactive({
      phoneNumber:undefined,
      userName:undefined,
      password:undefined,
      nickName:undefined,
      isVip:false,
      isAdmin:false,
      vipPackage:undefined,
      vipExpiredTime:undefined
    })

    const vipPackageList =ref(undefined)

    const getVipPackage = ()=>{
      axios.get("/do/admin/getallvippackage.do").then(res=>{
        vipPackageList.value = res.data.data
      })
    }

    getVipPackage()

    const addUser = ()=>{
      console.log(form.isVip)
      formRef.value.validate().then(()=>{
        if (form.isVip){
          axios.post("/do/admin/adduser.do",{
            "username":form.userName,
            "password":form.password,
            "nickname":form.nickName,
            "mobileNumber":form.phoneNumber,
            "isVip":1,
            "vipExpiredTime":form.vipExpiredTime.format("yyyy-MM-DD HH:mm:ss"),
            "vipPackageId":form.vipPackage,
            "isAdmin":form.isAdmin ? 1 : 0
          }).then(res=>{
            if(res.data.code != 200){
              message.error(res.data.data)
              return false;
            }else{
              message.success(res.data.data)
              setTimeout(()=>{
                location.reload()
              },1500)
            }
          })
        }else{
          axios.post("/do/admin/adduser.do",{
            "username":form.userName,
            "password":form.password,
            "nickname":form.nickName,
            "mobileNumber":form.phoneNumber,
            "isAdmin":form.isAdmin ? 1 : 0,
            "isVip":0,
          }).then(res=>{
            if(res.data.code != 200){
              message.error(res.data.data)
              return false;
            }else{
              message.success(res.data.data)
              setTimeout(()=>{
                location.reload()
              },1500)
            }
          })
        }
      }).catch(err=>{
        return false;
      })
    }


    return {
      locale,
      formRef,
      rules,
      vipPackageList,
      form,
      disabledDate,
      addUser
    }
  }
}
</script>
<style scoped>

</style>