import { Get, Post,Put } from "../../server";

export function getOrderList() {
    return Get("/getOrderList");
}

export function getOrderDetails(id) {
    let params = {
        orderId: id
    }
    return Get(`/getUserOrderDetail/${params.orderId}`)
}

export function updateOrderDetails(obj) {
    return Post("/ModifyAddressInOrder", obj)
}

export function generateOrder(data) {
    return Post("/generateOrder", data)
}

export function addAddress(data) {
    return Post("/addAddress", data)
}

export function updateAddress(data) {
    return Put("/updateAddress", data)
}


//未开始
export function getFollSecList1() {
    return Get("/getFollSecList1");
}

//已开始
export function getFollSecList2() {
    return Get("/getFollSecList2");
}

//已结束
export function getFollSecList3() {
    return Get("/getFollSecList3");
}

export function seckill(params) {
    return Post("/seckill",undefined,params)
}

export function hasDefaultAddress() {
    return Get("/hasDefaultAddress");
}
export const orderApi = {
    hasDefaultAddress,
    seckill,
    getFollSecList3,
    getFollSecList2,
    getFollSecList1,
    updateAddress,
    addAddress,
    getOrderList,
    getOrderDetails,
    updateOrderDetails,
    generateOrder
}