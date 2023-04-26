<template>
    <div class="f-header">
        <span>
            小米商城后台
        </span>

    <div class="ml-auto">
                <el-dropdown>
            <span class="el-dropdown-link">
                <el-avatar :size="25" />
            username
            <el-icon class="el-icon--right">
                <arrow-down />
            </el-icon>
            </span>
            <template #dropdown>
            <el-dropdown-menu>
                <el-dropdown-item>修改密码</el-dropdown-item>
                <el-dropdown-item divided>退出登录</el-dropdown-item>
            </el-dropdown-menu>
            </template>
        </el-dropdown>
    </div>
    </div>
</template>

<script setup>
</script>

<style>
.f-header{
    @apply flex items-center bg-indigo-500 text-light-50 fixed top-0 left-0 right-0; 
    height:64px;

}
</style>