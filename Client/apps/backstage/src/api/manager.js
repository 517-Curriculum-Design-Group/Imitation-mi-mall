import axios from '~/axios';


export function login({
    userName,
    password
}) {
    return axios.post(
        '/admin/login',
        {
            userName,
            password
        }, {
        headers: {
            "Content-Type": "application/json"
        }
    }
    )

}

export function home() {
    return axios.get('/getHome')
}

export function logout() {
    return axios.post('/admin/logout')
}

export function getadminlist(pageNum, pageSize) {
    return axios.get('/getAdminList', {
        params: {
            pageNum,
            pageSize
        }
    })
}

export function findadmin(pageNum, pageSize,nickName) {
    return axios.get('/getAdminList', {
        params: {
            pageNum,
            pageSize,
            nickName
        }
    })
}

export function getskulist(pageNum, pagesize) {
    return axios.get("/getSkuList")
}

export function modifystatus(userId) {
    return axios.put(`/updateUserStatus/${userId}`)
}
export function addAdmin() {
    return axios.post()
}