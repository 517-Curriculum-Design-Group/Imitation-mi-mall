import axios from '~/axios';

export function getskulist(pageNum, pageSize) {
    return axios.get('/getSkuList', {
        params: {
            pageNum,
            pageSize
        }
    })
}

export function addsku(attributeName) {
    return axios.post('/createNewAttribute',null,{ params: {
        attributeName
      }})
}

export function addsmallsku(
    attributeId,
    attributeValueName) {
    return axios.post('/createNewAttributeValue',
        {
        attributeId,
        attributeValueName
        },{
        headers: {
            "Content-Type": "application/json"
        }})
}

export function modifystatus(userId) {
    return axios.put(`/updateUserStatus/${userId}`)
}

export function getBackCategoryList() {
    return axios.get('/getBackCategoryList')
}



//小
export function deleteSku(attributeId) {
    return axios.delete('/deleteAttributeValueList', {
        data: attributeId
    },

        {
            headers: {
                "Content-Type": "application/json"
            }
        }
    )
}

//大
export function deleteSkubig(attributeId) {
    return axios.delete(`/deleteAttribute/${attributeId}` )

}