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
  Radio: {
    dotColorActive: "#ff6700FF",
    buttonBorderColorActive: "#ff6700FF",
    buttonTextColorActive: "#ff6700FF",
    buttonTextColorHover: "#ff6700FF",
    buttonColorActive: "#FFF",
    buttonBoxShadowFocus: "inset 0 0 0 1px #FF6700FF, 0 0 0 1px #FF6700FF",
    fontSizeLarge: "16px",
    fontSizeMedium: "16px",
    buttonHeightLarge: "42px",
    buttonHeightMedium: "42px",
  },
};
</script>
