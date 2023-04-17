<template>
  <div h="100vh" w="100vw">
    <n-config-provider h="100%">
      <n-notification-provider
        ><router-view></router-view
      ></n-notification-provider>
    </n-config-provider>
  </div>
</template>
