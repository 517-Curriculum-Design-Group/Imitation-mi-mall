<template>
  <div class = "f-menu">
    <el-menu
      default-active="2"
      class="border-0"
    >
    <el-menu-item index="1">

        <span>主控台</span>
      </el-menu-item>

      <el-sub-menu index="2">
        <template #title>
          <span>商品管理</span>
        </template>
          <el-menu-item index="2-1">  商品管理</el-menu-item>
          <el-menu-item index="2-2">  分类管理</el-menu-item>
          <el-menu-item index="2-3">  规格管理</el-menu-item>
        <!-- <el-sub-menu index="1-4">
          <template #title>item four</template>
          <el-menu-item index="1-4-1">item one</el-menu-item>
        </el-sub-menu> -->
      </el-sub-menu>

      <el-sub-menu index="3">
        <template #title>
          <span>用户管理</span>
        </template>
          <el-menu-item index="3-1">  用户管理</el-menu-item>
          <el-menu-item index="3-2">  管理员管理</el-menu-item>
      </el-sub-menu>

      <el-menu-item index="4">
        <span>订单管理</span>
      </el-menu-item>

      <el-menu-item index="5">
        <span>评论管理</span>
      </el-menu-item>

    </el-menu>
  </div>
</template>

<script setup>
    
</script>

<style>
.f-menu{
    width:250px;
    top: 64px;
    bottom: 0;
    left: 0;
    overflow: auto;
    @apply shadow-md fixed bg-light-50;
}
</style>