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

export function home(){
    return axios.get('/getHome')
}

export function logout(){
    return axios.post('/admin/logout')
}