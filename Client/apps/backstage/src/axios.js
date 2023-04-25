import axios from "axios"

const service = axios.create({
    baseURL:"localhost:8080",
})

export default service