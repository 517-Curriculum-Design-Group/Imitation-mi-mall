import { Get,Post } from "../../server";

export function getOrderList(){
    return Get("/getOrderList");
}

export function getOrderDetails(id){
    let params = {
        orderId: id
    }
    return Get(`/getUserOrderDetail/${params.orderId}`)
}

export function updateOrderDetails(obj){
    return Post("/ModifyAddressInOrder",obj)
}

export const orderApi = {
    getOrderList,
    getOrderDetails,
    updateOrderDetails
}