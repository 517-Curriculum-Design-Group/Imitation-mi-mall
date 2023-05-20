<template>
  <div h="100vh" w="100vw" class="overflow-x-hidden">
    <n-config-provider h="100%" :theme-overrides="themeOverrides">
      <n-notification-provider
        ><router-view></router-view
      ></n-notification-provider>
    </n-config-provider>
  </div>
</template>

<script setup>
const themeOverrides = {
  Tabs: {
    tabTextColorActiveLine: "#ff6700FF",
    tabTextColorHoverLine: "#ff6700FF",
    tabTextColorActiveBar: "#ff6700FF",
    tabTextColorHoverBar: "#ff6700FF",
    tabTextColorActiveCard: "#ff6700FF",
    barColor: "#ff6700FF",
    tabBorderColor: "#FFFFFF00",
  },
};
</script>
