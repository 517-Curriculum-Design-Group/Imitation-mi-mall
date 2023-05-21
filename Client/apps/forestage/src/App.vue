<template>
  <div h="100vh" w="100vw" class="overflow-x-hidden">
    <n-config-provider h="100%" :theme-overrides="themeOverrides">
      <n-dialog-provider>
        <n-notification-provider
          ><router-view></router-view
        ></n-notification-provider>
      </n-dialog-provider>
    </n-config-provider>
  </div>
</template>

<script>
import { useDialog, useMessage } from "naive-ui";
import { provide } from "vue";

const message = useMessage();
const dialog = useDialog();

export default {
  setup() {
    provide("message", message);
    provide("dialog", dialog);
  },
  data() {
    return {
      themeOverrides: {
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
          buttonBoxShadowFocus:
            "inset 0 0 0 1px #FF6700FF, 0 0 0 1px #FF6700FF",
          fontSizeLarge: "16px",
          fontSizeMedium: "16px",
          buttonHeightLarge: "42px",
          buttonHeightMedium: "42px",
        },
        Button: {
          colorPrimary: "#ff6700FF",
          colorHoverPrimary: "#f25807FF",
          colorPressedPrimary: "#f25807FF",
          colorFocusPrimary: "#f25807FF",
          colorDisabledPrimary: "#f25807FF",
          rippleColor: "#f25807FF",
          textColorTextFocusPrimary: "#f25807FF",
          rippleColorPrimary: "#f25807FF",
          textColorGhostDisabledPrimary: "#C55C00FF",
          textColorGhostPrimary: "#f25807FF",
          textColorGhostHoverPrimary: "#f25807FF",
          textColorGhostPressedPrimary: "#f25807FF",
          textColorGhostFocusPrimary: "#f25807FF",
          textColorTextHoverPrimary: "#f25807FF",
          textColorTextPrimary: "#f25807FF",
          textColorTextPressedPrimary: "#f25807FF",
          textColorGhostPressed: "#f25807FF",
          textColorTextPressed: "#f25807FF",
          borderHover: "1px solid #f25807",
          borderPressed: "1px solid #f25807",
          borderFocus: "1px solid #f25807",
          borderDisabled: "1px solid #f25807",
          borderPrimary: "1px solid #f25807",
          borderHoverPrimary: "1px solid #f25807",
          borderPressedPrimary: "1px solid #f25807",
          borderFocusPrimary: "1px solid #f25807",
          borderDisabledPrimary: "1px solid #f25807",
          colorInfo: "#b0b0b0FF",
          colorHoverInfo: "#757575FF",
          colorPressedInfo: "#757575FF",
          colorFocusInfo: "#757575FF",
          colorDisabledInfo: "#757575FF",
          textColorTextHoverInfo: "#757575FF",
          textColorTextPressedInfo: "#757575FF",
          textColorTextFocusInfo: "#757575FF",
          textColorGhostInfo: "#757575FF",
          textColorGhostHoverInfo: "#FFFFFFFF",
          textColorGhostPressedInfo: "#FFFFFFFF",
          textColorGhostFocusInfo: "#FFFFFFFF",
          textColorGhostDisabledInfo: "#FFFFFFFF",
          textColorTextInfo: "#B0B0B0FF",
          rippleColorInfo: "#B0B0B0FF",
          borderInfo: "1px solid #757575",
          borderHoverInfo: "1px solid #757575",
          borderPressedInfo: "1px solid #757575",
          borderFocusInfo: "1px solid #757575",
          borderDisabledInfo: "1px solid #757575",
        },
      },
    };
  },
};
</script>
