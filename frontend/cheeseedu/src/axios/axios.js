import axios from "axios";
import {message} from 'ant-design-vue';
import store from "../store/store.js"
//全局地址
axios.defaults.baseURL = window.location.protocol + "//" + window.location.host
//添加JWT验证头



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
axios.defaults.timeout = 5000
//配置响应拦截器
axios.interceptors.response.use(
    function (response) {
        if (response.data.code == "999") {
            message.error("请登录后在进行操作")
            store.commit("user/logout")
            window.localStorage.removeItem("token")
            window.sessionStorage.removeItem("token")
            window.location.href = '/'
            return false;
        }
        return response
    },
    function (error) {
        message.error("服务器发生未知错误")
        return Promise.reject(error)
    })

export default axios;