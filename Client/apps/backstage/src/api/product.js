import axios from '~/axios';

export function getproductlist(pageNum, pageSize) {
    return axios.get('/getProductList', {
        params: {
            pageNum,
            pageSize
        }
    })
}