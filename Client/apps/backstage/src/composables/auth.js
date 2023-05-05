import { useCookies } from '@vueuse/integrations/useCookies'

const tokenkey = "admin-token"
const usertype = "usertype"
const cookie = useCookies()

export function getToken(){
    return cookie.get(tokenkey)
}

export function setToken(token){
    return cookie.set(tokenkey,token)
}

export function removeToken(){
    return cookie.remove(tokenkey)
}

export function getType(){
    return cookie.get(usertype)
}

export function setType(admintype){
    return cookie.set(usertype,admintype)
}

export function removeType(){
    return cookie.remove(usertype)
}