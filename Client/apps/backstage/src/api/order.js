import axios from '~/axios';

export function getorderlist(pageNum, pageSize, status) {
    return axios.get('/getBackOrderList', {
        params: {
            pageNum,
            pageSize,
            status
        }
    })
}

export function ordersend(orderIds){
    return axios.post('/orderDelivery',orderIds)
}

export function getorderdetail(orderId) {
    return axios.get(`/getOrderDetail/${orderId}`)
}

export function deleteorder(orderId){
    return axios.delete('/deleteOrderList', {
        data: orderId
    },

        {
            headers: {
                "Content-Type": "application/json"
            }
        }
    )
}