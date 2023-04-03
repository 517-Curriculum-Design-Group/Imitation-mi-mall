<template>
    <div class="container">
       <div class="img"></div>
       <div class="main">
        <div class="head"></div>
        <div class="body">
            <div class="box">
                <div class="title">
                  <button
                     v-for="(_, tab) in tabs"
                     :key="tab"
                     :class="['tab-button', { active: currentTab === tab }]"
                     @click="currentTab = tab"
                     >
                   {{ tab }}
                  </button>
                </div>

                <component :is="tabs[currentTab]" class="tab"></component>

            </div>
        </div>
       </div>
    </div>
</template>

<script setup>
import Login from '../view/Login.vue';
import Register from '../view/Register.vue';
import { ref } from 'vue'

const currentTab = ref('Login')

const tabs = { Login,Register }

</script>

<style scoped>
.container {
  display: flex;
  width: 100%;
  height: 100%;
}
.img {
  width: 375px;
  height: 750px;
  background-color: var(--hover--color);
}
.main {
  display: flex;
  flex: 1;
  flex-direction: column;
  background-color: white;
}
.head {
  height: 80px;
  background-color: blue;
}
.body {
  display: flex;
  justify-content: center;
  align-items: center;
  flex: 1;
}
.box {
  display: flex;
  flex-direction: column;
  background-color: white;
  height: 480px;
  width: 450px;
  box-shadow: 0 20px 50px 0 hsl(0deg 0% 64% / 10%);
  padding: 40px 50px;
}
.title {
    height: 40px;
}

/* .tab-button{
  border: none;
  background-color: white;
  width: fit-content;
  height: 40px;
  font-weight: 500;
  margin-right: 10px;
  cursor: pointer;
}

.tab-button.active{
  color: black;
  border-bottom: 4px solid var(--hover--color);
} */
.tab-button {
  position: relative; 
  border: none;
  background-color: white;
  width: fit-content;
  height: 40px;
  font-weight: 500;
  margin-right: 10px;
  cursor: pointer;
}

.tab-button.active {
  color: black;
}

.tab-button::after {
  content: '';
  position: absolute;
  bottom: -4px; /* 与底部边框重合 */
  left: 0;
  width: 100%;
  height: 4px;
  background-color: var(--hover--color);
  transition: transform 0.3s ease-in-out;
  transform: scaleX(0);
}

.tab-button.active::after {
  transform: scaleX(1);
}
</style>