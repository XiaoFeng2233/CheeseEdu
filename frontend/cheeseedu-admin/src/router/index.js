import {createRouter, createWebHashHistory} from "vue-router";
import Home from "../views/Home.vue"
import Order from "../views/Order.vue";
import Log from "../views/Log.vue"
import Banner from "../views/Banner.vue";
import GlobalConfig from "../views/config/GlobalConfig.vue";
import SmsConfig from "../views/config/SmsConfig.vue";
import User from "../views/user/User.vue"
import AddUser from "../views/user/AddUser.vue";
import Vip from "../views/vip/Vip.vue";
import AddVip from "../views/vip/AddVip.vue";
import VipUser from "../views/vip/VipUser.vue";
import StorageConfig from "../views/config/StorageConfig.vue";
import PaymentConfig from "../views/config/PaymentConfig.vue";

import CourseCategory from "../views/course/CourseCategory.vue";
import CourseList from "../views/course/CourseList.vue";
import AddCourse from "../views/course/AddCourse.vue";



import ArticleList from "../views/article/ArticleList.vue";
import AddArticle from "../views/article/AddArticle.vue";

import QuestionList from "../views/question/QuestionList.vue";
import CourseCommentList from "../views/course/CourseCommentList.vue";

const routes = [
    {
        path: '',
        component: Home,
        name: Home.name,
        meta:{
            title:"主页"
        }
    },

    {
        path: "/order",
        component: Order,
        name: Order.name,
        meta:{
            title:"订单"
        }
    },
    {
        path: "/log",
        component: Log,
        name: Log.name,
        meta:{
            title:"登录日志"
        }
    },
    {
        path: "/banner",
        component: Banner,
        name: Banner.name,
        meta:{
            title:"轮播图"
        }
    },
    {
        path: "/config/globalconfig",
        component: GlobalConfig,
        name: GlobalConfig.name,
        meta:{
            title:"全局设置"
        }
    },
    {
        path: "/config/smsconfig",
        component: SmsConfig,
        name: SmsConfig.name,
        meta:{
            title:"短信设置"
        }
    },
    {
        path: "/config/storageconfig",
        component: StorageConfig,
        name: StorageConfig.name,
        meta:{
            title:"存储设置"
        }
    },
    {
        path: "/config/paymentconfig",
        component: PaymentConfig,
        name: PaymentConfig.name,
        meta:{
            title:"支付设置"
        }
    },
    {
        path: "/user",
        component: User,
        name: User.name,
        meta:{
            title:"用户列表"
        }
    },
    {
        path: "/user/add",
        component: AddUser,
        name: AddUser.name,
        meta:{
            title:"添加用户"
        }
    },
    {
        path: "/vip",
        component: Vip,
        name: Vip.name,
        meta:{
            title:"VIP套餐"
        }
    },
    {
        path: "/vip/add",
        component: AddVip,
        name: AddVip.name,
        meta:{
            title:"添加VIP套餐"
        }
    },
    {
        path: "/vip/user",
        component: VipUser,
        name: VipUser.name,
        meta:{
            title:"VIP用户"
        }
    },
    {
        path: "/course/coursecategory",
        component: CourseCategory,
        name: CourseCategory.name,
        meta:{
            title:"课程分类"
        }
    },
    {
        path: "/course/coursecomment",
        component: CourseCommentList,
        name: CourseCommentList.name,
        meta:{
            title:"课程评论"
        }
    },
    {
        path: "/course/courselist",
        component: CourseList,
        name: CourseList.name,
        meta:{
            title:"课程列表"
        }
    },
    {
        path: "/course/addcourse",
        component: AddCourse,
        name: AddCourse.name,
        meta:{
            title:"添加课程"
        }
    },
    {
        path: "/article/list",
        component: ArticleList,
        name: ArticleList.name,
        meta:{
            title:"文章列表"
        }

    },
    {
        path:"/article/add",
        component: AddArticle,
        name:AddArticle.name,
        meta:{
            title:"添加文章"
        }
    },
    {
        path:"/question/list",
        component: QuestionList,
        name:QuestionList.name,
        meta:{
            title:"问题列表"
        }
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})
router.beforeEach((to,from,next)=>{
    if (to.meta.title) {
        document.title = to.meta.title
    }
    next()
})
export default router