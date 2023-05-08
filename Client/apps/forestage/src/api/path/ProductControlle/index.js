import { Get } from "../../server";

export function getProductDetail(id) {
    let params = {
        productId: id,
    }
    return Get(`/getProductDetail/${params.productId}`)
}

export const userApi = {
    postLogin,
};