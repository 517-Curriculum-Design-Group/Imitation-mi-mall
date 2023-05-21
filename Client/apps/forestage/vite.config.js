import { defineConfig, loadEnv } from "vite";
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
export default defineConfig(({ command, mode }) => {
  const env = loadEnv(mode, process.cwd(), "");
  console.log(`当前开发的环境为${env.VITE_API_ENV}`)
  const alias = {
    "@": _resolve("src"),
  };
  const plugins = [
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
  ];
  if (command === "serve") {
    // 开发环境不需要代理
    return {
      // dev 独有配置
      plugins: plugins,
      resolve: {
        alias: alias,
      },
      server: {
        port: env.VITE_API_PORT,
        host: true,
        open: true,
        secure: false,
        proxy: {
          // 接口地址代理
          "/api": {
            target: env.VITE_API_HOST,
            changeOrigin: true,
            rewrite: (path) => path.replace(/^\/api/, ""),
          },
        },
      },
    };
  }
});
