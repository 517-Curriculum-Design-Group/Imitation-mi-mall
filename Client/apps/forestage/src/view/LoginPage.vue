<script setup>
import 登录 from './Login.vue'
import 注册 from './Register.vue'
import { ref } from 'vue'

const currentTab = ref('登录')

const tabs = { 登录,注册 }
</script>

<template>
<div class="container">
  <aside>
</aside>
<main>
    <div class="head"></div>
    <div class="box">
        <div class="title">
            <button
                     v-for="(_, tab) in tabs"
                     :key="tab"
                     :class="['tab-button',{active: currentTab === tab}]"
                     @click="currentTab = tab"
                     >
                   {{ tab }}
                  </button>
        </div>
        <component :is="tabs[currentTab]" class="tab"></component>
    </div>
</main>  
</div>
</template>

<style scoped>
.container {
    display: flex;
    width: 100vw;
    height: 100vh;
}
aside{
    width:25%;
    height: 100%;
    background-color:var(--hover--color);
}
main{
    display: flex;
    flex-direction: column;
    flex: 1;
    background-color: white;
}
.head{
    height: 12vh;
    width: 100%;
    background-color:var(--hover--color);
}
.box{
    display: flex;
    flex-direction: column;
    width:27vw;
    height:68vh;
    box-shadow: rgba(0, 0, 0, 0.15) 0px 5px 15px 0px;
    padding: 25px;
    margin:  auto;
}
.title{
    width: 100%;
    height:15%; 
}
.tab-button{
    position: relative;
    background: transparent;
    height:100%;
    width: 20%;
    font-size:22px;
    border: none;
    cursor: pointer;
}

.tab-button::before {
    content: "";
    position: absolute;
    bottom: 0;
    left: 100%;
    height: 4px;
    width: 0;
    border-radius: 2px;
    background-color: var(--hover--color);
    transition: all 200ms ease-in-out;
}

.tab-button.active::before {
    left: 0;
    width: 100%;
}

.tab-button.active ~ .tab-button::before {
    left: 0;
}
.tab{
    display: flex;
    flex-direction: column;
    align-items: center;
}
</style>