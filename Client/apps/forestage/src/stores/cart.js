import { defineStore } from "pinia";

export const cartStore = defineStore("cart", {
    // other options...
    state: () => {
        return {
            goods: [],
        };
    },
    actions: {
        getGoods() {
            return this.goods;
        },
        setGoods(newGoods) {
            this.goods.length = 0;
            newGoods.forEach(element => {
                this.goods.push(element)
            });
        },
    }
});
