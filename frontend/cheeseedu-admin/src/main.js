import { createApp } from 'vue'
import App from './App.vue'


import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.less';
import "./styles/index.scss"

import { ElScrollbar } from 'element-plus';
import 'element-plus/lib/theme-chalk/index.css';
import router from "./router/index.js";

const app = createApp(App)
app.component(ElScrollbar.name, ElScrollbar);
app.use(Antd)
app.use(router)
app.mount('#app')
