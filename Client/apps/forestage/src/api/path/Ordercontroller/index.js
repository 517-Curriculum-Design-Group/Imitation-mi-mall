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

export const orderApi = {
    updateAddress,
    addAddress,
    getOrderList,
    getOrderDetails,
    updateOrderDetails,
    generateOrder
}