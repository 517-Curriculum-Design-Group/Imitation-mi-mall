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