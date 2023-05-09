import axios from '~/axios';

export function gettypelist(){
    return axios.get('/getBackCategoryList')
}

export function finduser(pageNum, pageSize, nickName) {
    return axios.get('/getUserList', {
        params: {
            pageNum,
            pageSize,
            nickName
        }
    })
}

export function modifystatus(userId) {
    return axios.put(`/updateUserStatus/${userId}`)
}

export function deleteUser(userIds) {
    return axios.delete('/deleteUser', {
        data: userIds
    },

        {
            headers: {
                "Content-Type": "application/json"
            }
        }
    )
}

export function addtype(typename){
    return axios.post('/',typename)
}