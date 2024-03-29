import axios from "axios"
import { toast } from '~/composables/util'
import { getToken } from '~/composables/auth'


const service = axios.create({
    baseURL:"/api",
})

service.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么


    const token = getToken()
    if(token){
      config.headers["token"] = token 
    }

    return config;
  }, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  });

// 添加响应拦截器
service.interceptors.response.use(function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    return response.data.data;
  }, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    toast(error.response||"操作失败","error")
    // 对响应错误做点什么
    return Promise.reject(error);
  });


export default service