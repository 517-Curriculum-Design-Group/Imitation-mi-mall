import axios from '~/axios';

export function getSeckillList() {
    return axios.get("/getSeckillList")
}

export function getSeckillProd() {
    return axios.get("/getSeckillProd")
}

export function getSeckillSku(productId) {
    return axios.get(`/getSeckillSku/${productId}`)
}

export function addSeckill(seckill){
    return axios.post('/addSeckill',seckill)
}