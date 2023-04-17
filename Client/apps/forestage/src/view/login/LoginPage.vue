<template>
  <div class="flex" h="100%">
    <div w="20%" class="img-url"></div>
    <div class="flex flex-1 flex-col">
      <div p="5" class="flex justify-between items-center h-25">
        <h1>
          <img class="h-10 mr-2" src="../../assets/xiaomilogo.svg" />
          小米账号
        </h1>
        <div class="space-x-8">
          <a href="#">用户协议</a>
          <a href="#">隐私政策</a>
          <a href="#">帮助中心</a>
        </div>
      </div>
      <div class="flex flex-1 justify-center items-center">
        <div class="shadow-lg p-5">
          <button
            v-for="(_, tab) in tabs"
            :key="tab"
            :class="['tab-button', { active: currentTab === tab }]"
            @click="currentTab = tab"
          >
            <h2 class="font-semibold">
              {{ tab === "Login" ? "登录" : "注册" }}
            </h2>
          </button>
          <Transition name="slide-fade" mode="out-in">
            <component
              :is="tabs[currentTab]"
              class="tab min-w[400px] min-h[440px] p-6 df-tabs"
            ></component>
          </Transition>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import Login from "./Login.vue";
import Register from "./Register.vue";
import { ref } from "vue";

const currentTab = ref("Login");

const tabs = { Login, Register };
</script>

<style scoped>
button {
  background: none;
  position: relative;
  cursor: pointer;
}
button::before {
  content: "";
  position: absolute;
  left: 100%;
  bottom: 0;

  height: 4px;
  width: 0;
  border-radius: 4px;
  background-color: var(--text-hover-color);
  transition: all 200ms;
}
button.active::before {
  left: 0;
  width: 100%;
}
button.active ~ button::before {
  left: 0;
}
.img-url {
  background: url("https://cdn.web-global.fds.api.mi-img.com/mcfe--mi-account/static/static/media/banner.92c693b4..jpg")
    no-repeat;
  background-size: 100% 100%;
}

.slide-fade-enter-active,
.slide-fade-leave-active {
  transition: all 300ms cubic-bezier(0.4, 0.4, 0.25, 1.35);
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translate3d(10%, 0, 0);
  opacity: 0;
}
</style>
