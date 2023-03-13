import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import unocss from "unocss/vite";
import Components from "unplugin-vue-components/vite";
import { AntDesignVueResolver } from "unplugin-vue-components/resolvers";

export default defineConfig({
  plugins: [
    vue(),
    unocss(),
    Components({
      resolvers: [AntDesignVueResolver()],
    }),
  ],
});
