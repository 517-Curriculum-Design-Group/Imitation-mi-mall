import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import Unocss from "unocss/vite";
import { presetUno, presetAttributify, presetIcons } from "unocss";
import path from "path";
import autoprefixer from "autoprefixer";
import postcssPxtorem from "postcss-pxtorem";

function _resolve(dir) {
  return path.resolve(__dirname, dir);
}

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    Unocss({
      // 使用Unocss
      presets: [
        presetUno({
          shortcuts: [{ "aspect-square": "aspect-ratio: 1 / 1" }],
          theme: {
            color: {
              orangeColor: "var(--text-hover-color)",
              cardDesc: "var(--text-cardDesc-color)",
            },
          },
        }),
        presetAttributify(),
        presetIcons(),
      ],
    }),
  ],
  resolve: {
    alias: {
      "@": _resolve("src"),
    },
  },
  server: {
    proxy: {
      // 接口地址代理
      "/api": {
        target: "http://8.134.87.155:8087", // 接口的域名
        changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
        rewrite: (path) => path.replace(/^\/api/, ""),
      },
    },
  },
});
