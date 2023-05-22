// vite.config.js
import { defineConfig, loadEnv } from "file:///D:/desktop/pg/xiaomi/Imitation-mi-mall/Client/node_modules/.pnpm/registry.npmmirror.com+vite@4.2.0_sass@1.60.0/node_modules/vite/dist/node/index.js";
import vue from "file:///D:/desktop/pg/xiaomi/Imitation-mi-mall/Client/node_modules/.pnpm/registry.npmmirror.com+@vitejs+plugin-vue@4.1.0_vite@4.2.0_vue@3.2.47/node_modules/@vitejs/plugin-vue/dist/index.mjs";
import Unocss from "file:///D:/desktop/pg/xiaomi/Imitation-mi-mall/Client/node_modules/.pnpm/registry.npmmirror.com+unocss@0.50.6_postcss@8.4.21_vite@4.2.0/node_modules/unocss/dist/vite.mjs";
import { presetUno, presetAttributify, presetIcons } from "file:///D:/desktop/pg/xiaomi/Imitation-mi-mall/Client/node_modules/.pnpm/registry.npmmirror.com+unocss@0.50.6_postcss@8.4.21_vite@4.2.0/node_modules/unocss/dist/index.mjs";
import path from "path";
import autoprefixer from "file:///D:/desktop/pg/xiaomi/Imitation-mi-mall/Client/node_modules/.pnpm/registry.npmmirror.com+autoprefixer@10.4.14_postcss@8.4.21/node_modules/autoprefixer/lib/autoprefixer.js";
import postcssPxtorem from "file:///D:/desktop/pg/xiaomi/Imitation-mi-mall/Client/node_modules/.pnpm/registry.npmmirror.com+postcss-pxtorem@6.0.0_postcss@8.4.21/node_modules/postcss-pxtorem/index.js";
var __vite_injected_original_dirname = "D:\\desktop\\pg\\xiaomi\\Imitation-mi-mall\\Client\\apps\\forestage";
function _resolve(dir) {
  return path.resolve(__vite_injected_original_dirname, dir);
}
var vite_config_default = defineConfig(({ command, mode, ssrBuild }) => {
  const env = loadEnv(mode, process.cwd(), "");
  console.log(`\u5F53\u524D\u5F00\u53D1\u7684\u73AF\u5883\u4E3A${env.VITE_API_ENV}`);
  const alias = {
    "@": _resolve("src")
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
              cardDesc: "var(--text-cardDesc-color)"
            }
          }
        }),
        presetAttributify(),
        presetIcons()
      ]
    })
  ];
  if (command === "serve") {
    return {
      // dev 独有配置
      plugins,
      resolve: {
        alias
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
            rewrite: (path2) => path2.replace(/^\/api/, "")
          }
        }
      }
    };
  }
});
export {
  vite_config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcuanMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJEOlxcXFxkZXNrdG9wXFxcXHBnXFxcXHhpYW9taVxcXFxJbWl0YXRpb24tbWktbWFsbFxcXFxDbGllbnRcXFxcYXBwc1xcXFxmb3Jlc3RhZ2VcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkQ6XFxcXGRlc2t0b3BcXFxccGdcXFxceGlhb21pXFxcXEltaXRhdGlvbi1taS1tYWxsXFxcXENsaWVudFxcXFxhcHBzXFxcXGZvcmVzdGFnZVxcXFx2aXRlLmNvbmZpZy5qc1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRDovZGVza3RvcC9wZy94aWFvbWkvSW1pdGF0aW9uLW1pLW1hbGwvQ2xpZW50L2FwcHMvZm9yZXN0YWdlL3ZpdGUuY29uZmlnLmpzXCI7aW1wb3J0IHsgZGVmaW5lQ29uZmlnLCBsb2FkRW52IH0gZnJvbSBcInZpdGVcIjtcclxuaW1wb3J0IHZ1ZSBmcm9tIFwiQHZpdGVqcy9wbHVnaW4tdnVlXCI7XHJcbmltcG9ydCBVbm9jc3MgZnJvbSBcInVub2Nzcy92aXRlXCI7XHJcbmltcG9ydCB7IHByZXNldFVubywgcHJlc2V0QXR0cmlidXRpZnksIHByZXNldEljb25zIH0gZnJvbSBcInVub2Nzc1wiO1xyXG5pbXBvcnQgcGF0aCBmcm9tIFwicGF0aFwiO1xyXG5pbXBvcnQgYXV0b3ByZWZpeGVyIGZyb20gXCJhdXRvcHJlZml4ZXJcIjtcclxuaW1wb3J0IHBvc3Rjc3NQeHRvcmVtIGZyb20gXCJwb3N0Y3NzLXB4dG9yZW1cIjtcclxuXHJcbmZ1bmN0aW9uIF9yZXNvbHZlKGRpcikge1xyXG4gIHJldHVybiBwYXRoLnJlc29sdmUoX19kaXJuYW1lLCBkaXIpO1xyXG59XHJcblxyXG4vLyBodHRwczovL3ZpdGVqcy5kZXYvY29uZmlnL1xyXG5leHBvcnQgZGVmYXVsdCBkZWZpbmVDb25maWcoKHsgY29tbWFuZCwgbW9kZSwgc3NyQnVpbGQgfSkgPT4ge1xyXG4gIGNvbnN0IGVudiA9IGxvYWRFbnYobW9kZSwgcHJvY2Vzcy5jd2QoKSwgXCJcIik7XHJcbiAgY29uc29sZS5sb2coYFx1NUY1M1x1NTI0RFx1NUYwMFx1NTNEMVx1NzY4NFx1NzNBRlx1NTg4M1x1NEUzQSR7ZW52LlZJVEVfQVBJX0VOVn1gKVxyXG4gIGNvbnN0IGFsaWFzID0ge1xyXG4gICAgXCJAXCI6IF9yZXNvbHZlKFwic3JjXCIpLFxyXG4gIH07XHJcbiAgY29uc3QgcGx1Z2lucyA9IFtcclxuICAgIHZ1ZSgpLFxyXG4gICAgVW5vY3NzKHtcclxuICAgICAgLy8gXHU0RjdGXHU3NTI4VW5vY3NzXHJcbiAgICAgIHByZXNldHM6IFtcclxuICAgICAgICBwcmVzZXRVbm8oe1xyXG4gICAgICAgICAgc2hvcnRjdXRzOiBbeyBcImFzcGVjdC1zcXVhcmVcIjogXCJhc3BlY3QtcmF0aW86IDEgLyAxXCIgfV0sXHJcbiAgICAgICAgICB0aGVtZToge1xyXG4gICAgICAgICAgICBjb2xvcjoge1xyXG4gICAgICAgICAgICAgIG9yYW5nZUNvbG9yOiBcInZhcigtLXRleHQtaG92ZXItY29sb3IpXCIsXHJcbiAgICAgICAgICAgICAgY2FyZERlc2M6IFwidmFyKC0tdGV4dC1jYXJkRGVzYy1jb2xvcilcIixcclxuICAgICAgICAgICAgfSxcclxuICAgICAgICAgIH0sXHJcbiAgICAgICAgfSksXHJcbiAgICAgICAgcHJlc2V0QXR0cmlidXRpZnkoKSxcclxuICAgICAgICBwcmVzZXRJY29ucygpLFxyXG4gICAgICBdLFxyXG4gICAgfSksXHJcbiAgXTtcclxuICBpZiAoY29tbWFuZCA9PT0gXCJzZXJ2ZVwiKSB7XHJcbiAgICAvLyBcdTVGMDBcdTUzRDFcdTczQUZcdTU4ODNcdTRFMERcdTk3MDBcdTg5ODFcdTRFRTNcdTc0MDZcclxuICAgIHJldHVybiB7XHJcbiAgICAgIC8vIGRldiBcdTcyRUNcdTY3MDlcdTkxNERcdTdGNkVcclxuICAgICAgcGx1Z2luczogcGx1Z2lucyxcclxuICAgICAgcmVzb2x2ZToge1xyXG4gICAgICAgIGFsaWFzOiBhbGlhcyxcclxuICAgICAgfSxcclxuICAgICAgc2VydmVyOiB7XHJcbiAgICAgICAgcG9ydDogZW52LlZJVEVfQVBJX1BPUlQsXHJcbiAgICAgICAgaG9zdDogdHJ1ZSxcclxuICAgICAgICBvcGVuOiB0cnVlLFxyXG4gICAgICAgIHNlY3VyZTogZmFsc2UsXHJcbiAgICAgICAgcHJveHk6IHtcclxuICAgICAgICAgIC8vIFx1NjNBNVx1NTNFM1x1NTczMFx1NTc0MFx1NEVFM1x1NzQwNlxyXG4gICAgICAgICAgXCIvYXBpXCI6IHtcclxuICAgICAgICAgICAgdGFyZ2V0OiBlbnYuVklURV9BUElfSE9TVCxcclxuICAgICAgICAgICAgY2hhbmdlT3JpZ2luOiB0cnVlLFxyXG4gICAgICAgICAgICByZXdyaXRlOiAocGF0aCkgPT4gcGF0aC5yZXBsYWNlKC9eXFwvYXBpLywgXCJcIiksXHJcbiAgICAgICAgICB9LFxyXG4gICAgICAgIH0sXHJcbiAgICAgIH0sXHJcbiAgICB9O1xyXG4gIH1cclxufSk7XHJcbiJdLAogICJtYXBwaW5ncyI6ICI7QUFBc1gsU0FBUyxjQUFjLGVBQWU7QUFDNVosT0FBTyxTQUFTO0FBQ2hCLE9BQU8sWUFBWTtBQUNuQixTQUFTLFdBQVcsbUJBQW1CLG1CQUFtQjtBQUMxRCxPQUFPLFVBQVU7QUFDakIsT0FBTyxrQkFBa0I7QUFDekIsT0FBTyxvQkFBb0I7QUFOM0IsSUFBTSxtQ0FBbUM7QUFRekMsU0FBUyxTQUFTLEtBQUs7QUFDckIsU0FBTyxLQUFLLFFBQVEsa0NBQVcsR0FBRztBQUNwQztBQUdBLElBQU8sc0JBQVEsYUFBYSxDQUFDLEVBQUUsU0FBUyxNQUFNLFNBQVMsTUFBTTtBQUMzRCxRQUFNLE1BQU0sUUFBUSxNQUFNLFFBQVEsSUFBSSxHQUFHLEVBQUU7QUFDM0MsVUFBUSxJQUFJLG1EQUFXLElBQUksY0FBYztBQUN6QyxRQUFNLFFBQVE7QUFBQSxJQUNaLEtBQUssU0FBUyxLQUFLO0FBQUEsRUFDckI7QUFDQSxRQUFNLFVBQVU7QUFBQSxJQUNkLElBQUk7QUFBQSxJQUNKLE9BQU87QUFBQTtBQUFBLE1BRUwsU0FBUztBQUFBLFFBQ1AsVUFBVTtBQUFBLFVBQ1IsV0FBVyxDQUFDLEVBQUUsaUJBQWlCLHNCQUFzQixDQUFDO0FBQUEsVUFDdEQsT0FBTztBQUFBLFlBQ0wsT0FBTztBQUFBLGNBQ0wsYUFBYTtBQUFBLGNBQ2IsVUFBVTtBQUFBLFlBQ1o7QUFBQSxVQUNGO0FBQUEsUUFDRixDQUFDO0FBQUEsUUFDRCxrQkFBa0I7QUFBQSxRQUNsQixZQUFZO0FBQUEsTUFDZDtBQUFBLElBQ0YsQ0FBQztBQUFBLEVBQ0g7QUFDQSxNQUFJLFlBQVksU0FBUztBQUV2QixXQUFPO0FBQUE7QUFBQSxNQUVMO0FBQUEsTUFDQSxTQUFTO0FBQUEsUUFDUDtBQUFBLE1BQ0Y7QUFBQSxNQUNBLFFBQVE7QUFBQSxRQUNOLE1BQU0sSUFBSTtBQUFBLFFBQ1YsTUFBTTtBQUFBLFFBQ04sTUFBTTtBQUFBLFFBQ04sUUFBUTtBQUFBLFFBQ1IsT0FBTztBQUFBO0FBQUEsVUFFTCxRQUFRO0FBQUEsWUFDTixRQUFRLElBQUk7QUFBQSxZQUNaLGNBQWM7QUFBQSxZQUNkLFNBQVMsQ0FBQ0EsVUFBU0EsTUFBSyxRQUFRLFVBQVUsRUFBRTtBQUFBLFVBQzlDO0FBQUEsUUFDRjtBQUFBLE1BQ0Y7QUFBQSxJQUNGO0FBQUEsRUFDRjtBQUNGLENBQUM7IiwKICAibmFtZXMiOiBbInBhdGgiXQp9Cg==
