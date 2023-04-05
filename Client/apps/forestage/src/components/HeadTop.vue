<script setup>
import { ref,reactive } from 'vue'
const goods=reactive([ {name:"1",price:100,count:1},{name:"2",price:500,count:2} ])
// const goods=[]

const totalprice = goods.reduce((pre,now)=>{
    return pre + now.count * now.price
},0)
</script>

<template>
    <div class="container">
    <a-button type="primary" class="cart">购物车</a-button>
    <div class="cartdetail bg-light-50 text-xs" v-if="!goods.length">购物车中还没有商品，赶紧选购吧！</div>
    <div class="cartdetail bg-light-50 text-xs" v-else>
        <div class="goodslist" v-for="(items,index) in goods" :key="idnex">{{ items.name }}</div>
        <div class="total">
            <span class="detail">
                <span class="text-xs">共{{ goods.length }}件商品</span>
                <span class="price text-lg">¥{{ totalprice }}</span>
            </span>
            <a-button type="primary" size="large" class="cart-btn">去购物车结算</a-button>
        </div>
    </div>
    <span class="space"></span>
    <a-button type="default" class="news">消息通知</a-button>
    <span class="seq"></span>
    <router-link to="/loginPage"><a-button type="default" class="register">注册</a-button></router-link>
    <span class="seq"></span>
    <router-link to="/loginPage"><a-button type="default" class="login">登录</a-button></router-link>
    </div>
</template>


<style scoped>
.container {
    display: flex;
    flex-direction: row-reverse;
    align-items: center;
    width:100%;
    height:40px;
    background-color:#333333;
}
.cart{
    font-size: 11px;
    width:120px;
    height: 40px;
    margin-right: 10.1%;
    background-color:#424242;
    border-color:#333333;
    
}
.cart:hover{
    background-color:white;
    color:var(--hover--color);
    border-top-color: #b0b0b0;
    border-bottom-color:white;
}
.register,.login,.news{
    width:fit-content;
    height: 40px;
    text-align: center;
    font-size: 10px;
    color: #b0b0b0;
    background-color:#333333;
    border-color:#333333;
    text-align: center;
    padding: 4px 8px;
}
.register:focus,.login:focus{
    background-color:#333333;
    border-color: #333333;
    color:#b0b0b0;
}
.register:active,.register:hover,
.login:active,.login:hover{
    background-color:#333333;
    border-color: #333333;
    color:white; 
}
.seq{
    height: 40%;
    border:1px solid #424242;
}
.space{
    background-color:transparent;
    height:100%;
    width:1%;
}
.cartdetail{
    display: flex;
    position: absolute;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    visibility: hidden;
    overflow: hidden;
    top:40px;
    right: 10.1%;
    width:20%;
    height: 0;
    min-height: 0;
    /* max-height: 0; */
    z-index: 300;
    will-change: height;
    box-shadow: rgba(0, 0, 0, 0.15) 0px 5px 15px 0px;
    transition: all 250ms linear;
}
.cart:hover + .cartdetail,.cartdetail:hover{
    visibility: visible;
    min-height: 13.1%;
    /* max-height: fit-content; */
}
.goodslist{
    width:100%;
    height: 80%;
}
.total{
    padding: 5%;
    width: 100%;
}
.detail{
    display: flex;
    flex-direction: column;
    float: left;
    height: 100%;
    width: 40%;
}
.price{
    flex: 1;
    color: var(--hover--color);
}
.cart-btn{
    float: right;
    background-color: var(--hover--color);
    border-color: var(--hover--color);
}
</style>