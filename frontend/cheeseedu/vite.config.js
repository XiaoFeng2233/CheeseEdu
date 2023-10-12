import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  server:{
    port:3000,
    host:"0.0.0.0"
  },
  plugins: [vue()],
  css: {
    preprocessorOptions: {
      less: {
        modifyVars: {
          'primary-color': '#00c691',
          'link-color': 'red',
        },
        javascriptEnabled: true,
      }
    },
  },
})
