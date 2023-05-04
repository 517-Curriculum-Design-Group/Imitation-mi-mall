import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import WindiCSS from 'vite-plugin-windicss'
import autoprefixer from 'autoprefixer';
import postcssPxtorem from 'postcss-pxtorem';

import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  css: {
    // 进行 PostCSS 配置
    postcss: {
      plugins: [
        autoprefixer({
          // 指定目标浏览器
          overrideBrowserslist: ['Chrome > 40', 'ff > 31', 'ie 11']
        }),
        postcssPxtorem({
          rootValue : 16, // 换算的基数
            selectorBlackList  : [], // 忽略转换正则匹配项 列入一些ui库, ['.el'] 就是忽略elementUI库
            propList   : ['*'],
        })
      ]
    }
  },
  resolve:{
    alias:{
      "~":path.resolve(__dirname,"src")
    }
  },
  server:{
    proxy:{
      '/api': {
        target: 'http://8.134.87.155:8087/',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      },
    }
  },

  plugins: [
    WindiCSS(),
    vue()
  ],
})
