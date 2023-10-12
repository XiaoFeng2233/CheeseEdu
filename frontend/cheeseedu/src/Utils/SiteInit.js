import axios from "../axios/axios.js"
import {message} from "ant-design-vue"
import store from "../store/store.js";
import jwt_decode from "jwt-decode";
import router from "../router/index.js";
import {useRoute} from "vue-router";

export const SiteInit = async () => {
    const route = useRoute();
    const res = await axios({
        type: "GET",
        async: false,
        url: "/do/common/getbaseconfig.do"
    })

    if (res.data.code != "200") {
        message.error(res.data.data)
        return data;
    }
    let icoElm = document.querySelector("link[rel*='icon']")
    icoElm.href = res.data.data.siteIcon
    store.commit("siteconfig/update", res.data.data)
    document.title =  res.data.data.siteName +" - " + route.meta.title
}

export const UserInit = ()=>{
    const token = window.localStorage["token"]
    const sessionToken = window.sessionStorage["token"]
    if (token !== undefined) {

        const decode = jwt_decode(token)
        if (decode.exp < Date.now() / 1000) {
            window.localStorage.removeItem("token")
            return false;
        }
        const user = {
            token,
            userName: decode.userName,
            userId: decode.userId,
            isAdmin: decode.isAdmin === 1,
            expireTime: decode.exp,
            nickName: decode.nickname,
            avatar: decode.avatar
        }
        store.commit("user/update", user)
    }

    if (sessionToken !== undefined){
        const decode = jwt_decode(sessionToken)
        if (decode.exp < Date.now() / 1000) {
            window.sessionStorage.removeItem("token")
            return false;
        }
        const user = {
            token:sessionToken,
            userName: decode.userName,
            userId: decode.userId,
            isAdmin: decode.isAdmin === 1,
            expireTime: decode.exp,
            nickName: decode.nickname,
            avatar: decode.avatar
        }
        store.commit("user/update", user)
    }
}


