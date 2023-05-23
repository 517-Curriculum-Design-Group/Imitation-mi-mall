<template>
  <div class="f-menu">
    <el-menu default-active="defaultActive" class="border-0" @select="handleSelect">
      <el-menu-item index="/">
        <span>主控台</span>
      </el-menu-item>

      <el-sub-menu index="1">
        <template #title>
          <span>商品管理</span>
        </template>
        <el-menu-item index="/list"> 商品管理</el-menu-item>
        <el-menu-item index="/seckill"> 秒杀商品管理</el-menu-item>
        <el-menu-item index="/category"> 分类管理</el-menu-item>
        <el-menu-item index="/sku"> 规格管理</el-menu-item>
        <!-- <el-sub-menu index="1-4">
          <template #title>item four</template>
          <el-menu-item index="1-4-1">item one</el-menu-item>
        </el-sub-menu> -->
      </el-sub-menu>

      <el-sub-menu index="/user">
        <template #title>
          <span>用户管理</span>
        </template>
        <el-menu-item index="/common"> 用户管理</el-menu-item>
        <el-menu-item v-if="type == '超级管理员'" index="/admincontrol">
          管理员管理</el-menu-item
        >
      </el-sub-menu>

      <el-menu-item index="/order">
        <span>订单管理</span>
      </el-menu-item>

      <el-menu-item index="/comment">
        <span>评论管理</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script setup>
import {ref} from 'vue'
import { getType } from "~/composables/auth";
import { useRouter,useRoute } from "vue-router";

const router = useRouter()
const route = useRoute()

const defaultActive = ref(route.path)
const handleSelect = (e)=>{
  console.log(e)
  router.push(e)
}
const type = unescape(getType());
</script>

<style>
.f-menu {
  width: 250px;
  top: 64px;
  bottom: 0;
  left: 0;
  overflow: auto;
  @apply shadow-md fixed bg-light-50;
}
</style>