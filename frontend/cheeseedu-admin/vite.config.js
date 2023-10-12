import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import styleImport from 'vite-plugin-style-import'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        vue(),
    ],
    css: {
        preprocessorOptions: {
            less: {
                modifyVars: {
                    'primary-color': '#5473E7',
                    'menu-dark-submenu-bg': '#fff',
                    'menu-dark-bg': '#3A4167',
                },
                javascriptEnabled: true,
            }
        },
    },
})
