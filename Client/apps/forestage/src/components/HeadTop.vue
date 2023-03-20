<template>
    <div class="container">
    <a-button type="primary" class="cart">购物车</a-button>
    <a-button type="default" class="register">注册</a-button>
    <router-link to="/loginPage"><a-button type="default" class="login">登录</a-button></router-link>    
    </div>
</template>


<style scoped>
.container {
    width:100%;
    height:40px;
    background-color:#333333
}
.cart{
    font-size: 11px;
    width:120px;
    height: 40px;
    float: right;
    margin-right: 200px;
    background-color:#424242;
    border-color:#333333;
}
.cart:hover{
    background-color:white;
    color:var(--hover--color);
    border-top-color: #b0b0b0;
    border-bottom-color:white;
}

.register{
    width:50px;
    height: 40px;
    float: right;
    text-align: center;
    font-size: 10px;
    color: #b0b0b0;
    background-color:#333333;
    border-color:#333333;
    text-align: center;
    margin-right: 10px;
}
.register:focus{
    background-color:#333333;
    border-color: #333333;
    color:#b0b0b0;
}
.register:active,.register:hover{
    background-color:#333333;
    border-color: #333333;
    color:white; 
}
.login{
    width:50px;
    height: 40px;
    float: right;
    text-align: center;
    font-size: 10px;
    color: #b0b0b0;
    background-color:#333333;
    border-color:#333333;
    text-align: center;
}
.login:active,.login:hover{
    background-color:#333333;
    border-color: #333333;
    color:white;
}
.login:focus{
    background-color:#333333;
    border-color: #333333;
    color:#b0b0b0;
}
</style>