import axios from "axios";
import { message } from 'ant-design-vue';

//全局地址
axios.defaults.baseURL = window.location.protocol + "//" + window.location.host
//配置请求拦截器
axios.interceptors.request.use(config=>{
    if (window.localStorage.getItem("token") != undefined || window.localStorage.getItem("token") != ""){
        config.headers.token = window.localStorage.getItem("token")
    }
    if (window.sessionStorage.getItem("token") != undefined || window.sessionStorage.getItem("token") != ""){
        config.headers.token = window.sessionStorage.getItem("token")
    }
    return config
})
//配置超时时间
axios.defaults.timeout = 3000
//配置响应拦截器
axios.interceptors.response.use(
    function (response){
        if (response.data.code == "999"){
            message.error("登录凭证已过期或无效")
            window.localStorage.removeItem("token")
            window.location.href = '/'
            return false;
        }
        return response
    },
    function (error){
        message.error("服务器发生未知错误")
        return Promise.reject(error)
    })

export default axios;