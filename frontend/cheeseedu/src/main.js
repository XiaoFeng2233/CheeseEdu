import {createApp} from 'vue'
import App from './App.vue'

import Antdv from "ant-design-vue"
import "ant-design-vue/dist/antd.less"

import router from "./router/index.js"
import { ElScrollbar } from 'element-plus';
import 'element-plus/dist/index.css';
import "./styles/index.scss"
import {UserInit} from "./Utils/SiteInit.js"


const app = createApp(App)
app.component(ElScrollbar.name, ElScrollbar);
app.use(router)
app.use(Antdv)
UserInit()
app.mount('#app')
