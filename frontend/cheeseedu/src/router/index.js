import {createRouter, createWebHashHistory} from "vue-router"
import store from "../store/store.js";
import Default from "../views/Default.vue";
import Home from "../views/Default/Home.vue";
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import ResetPassword from "../views/ResetPassword.vue";
import CourseList from "../views/Default/Course/List.vue";
import Course from "../views/Default/Course/Course.vue";
import QuestionList from "../views/Default/Question/List.vue"
import Question from "../views/Default/Question/Question.vue";
import ArticleList from "../views/Default/Article/ArticleList.vue";
import Article from "../views/Default/Article/Article.vue";
import CreateCourseOrder from "../views/Default/Order/CreateCourseOrder.vue";
import CreateVipOrder from "../views/Default/Order/CreateVipOrder.vue";
import OrderCheckOut from "../views/Default/Order/OrderCheckOut.vue";
import UserCenter from "../views/UserCenter.vue";
import UserCenterHome from "../views/UserCenter/Home.vue"
import UserCenterCourse from "../views/UserCenter/Course.vue"
import UserCenterOrder from "../views/UserCenter/Order.vue";
import CourseVideo from "../views/Default/Course/CourseVideo.vue";
import MyQuestion from "../views/UserCenter/MyQuestion.vue"
import Log from "../views/UserCenter/Log.vue"
import Profile from "../views/UserCenter/Profile.vue";
import Setting from "../views/UserCenter/Setting.vue";
import Search from "../views/Default/Search.vue";
import Vip from "../views/Default/Vip.vue";

const routes = [
    {
        path: "",
        component: Default,
        name: Default.name,
        redirect: {name: 'Home'},
        children: [
            {
                path: "/home",
                component: Home,
                name: Home.name,
                meta: {title: "首页"},
            },
            {
                path: "/vip",
                component: Vip,
                name:Vip.name,
                meta: {
                    title:"开通会员"
                }
            },
            {
                path: "/search/:keyword",
                component: Search,
                name: Search.name,
                meta: {title: "搜索"},
            },
            {
                path: "/course/list",
                component: CourseList,
                name: CourseList.name,
                meta: {
                    title: "课程大全"
                }
            },
            {
                path: "/coursevideo/:courseId",
                component: CourseVideo,
                name: CourseVideo.name,
                meta: {
                    title: "课程视频"
                }
            },
            {
                path: "/course/:courseId",
                component: Course,
                name: Course.name,
                meta: {
                    title: "课程详情"
                }
            },
            {
                path: "/question/list",
                component: QuestionList,
                name: QuestionList.name,
                meta: {
                    title: "问题列表"
                }
            },
            {
                path: "/question/:id",
                component: Question,
                name: Question.name,
                meta: {
                    title: "问题详情"
                }
            },
            {
                path: "/articlelist",
                component: ArticleList,
                name: ArticleList.name,
                meta: {title: "文章列表"}
            },
            {
                path: '/article/:articleId',
                component: Article,
                name: Article.name,
                meta: {
                    title: "文章详情"
                }
            },
            {
                path: '/pay/confirm/course',
                component: CreateCourseOrder,
                name: CreateCourseOrder.name,
                meta: {
                    title: "创建订单"
                }
            },
            {
                path:"/pay/confirm/vip",
                component: CreateVipOrder,
                name:CreateVipOrder.name,
                meta:{
                    title:"创建订单"
                }
            },
            {
                path: '/pay/checkout',
                component: OrderCheckOut,
                name: OrderCheckOut.name,
                meta: {
                    title: "收银台"
                }
            }
        ]
    },
    {
        path: "/login",
        component: Login,
        name: Login.name,
        meta: {title: "登录"},
    },
    {
        path: "/register",
        component: Register,
        name: Register.name,
        meta: {title: "注册"},
    },
    {
        path: "/resetpassword",
        component: ResetPassword,
        name: ResetPassword.name,
        meta: {title: "重置密码"},
    },
    {
        path: "/user",
        component: UserCenter,
        name: UserCenter.name,
        redirect: {name: "UserCenterHome"},
        children: [
            {
                path: "home",
                component: UserCenterHome,
                name: UserCenterHome.name,
                meta: {
                    title: "用户中心"
                }
            },
            {
                path: "course",
                component: UserCenterCourse,
                name: UserCenterCourse.name,
                meta: {
                    title: "我的课程"
                }
            },
            {
                path: "order",
                component: UserCenterOrder,
                name: UserCenterOrder.name,
                meta: {
                    title: "我的订单"
                }
            },
            {
                path: "question",
                component: MyQuestion,
                name: MyQuestion.name,
                meta: {
                    title: "我的问题"
                }
            },
            {
                path: "log",
                component: Log,
                name: Log.name,
                meta: {
                    title: "登录日志"
                }
            },
            {
                path: "profile",
                component: Profile,
                name: Profile.name,
                meta: {
                    title: "资料修改"
                }
            },
            {
                path: "setting",
                component: Setting,
                name: Setting.name,
                meta: {
                    title: "安全设置"
                }
            },
        ]
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    /* 路由发生变化修改页面title */
    if (store.state.siteconfig.siteName == undefined) {
        next()
        return false;
    }
    if (to.meta.title) {
        const name = store.state.siteconfig.siteName
        document.title = name + " - " + to.meta.title
    }
    next()
})

export default router;