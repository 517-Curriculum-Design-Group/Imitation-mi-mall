import { defineStore } from "pinia";

export const useDetailStore = defineStore('useDetailStore',{
    state:()=>{
        return {
            currentProduct:{},
            mostProduct:[]
        }
    },
    actions:{
        getCurrentProduct(){
            return this.currentProduct;
        },
        setCurrentProduct(item){
            Object.assign(this.currentProduct,item)
        },
        getMostProduct(){
            return this.mostProduct;
        },
        setMostProduct(item){
            this.mostProduct = item
        },
    },
    persist:{
        key:'userDetailStore',
        storage:window.sessionStorage,
        path:['currentProduct']
    }
})