import { useCookies } from '@vueuse/integrations/useCookies'

const tokenkey = "admin-token"
const usertype = "usertype"
const nname = "nickname"
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

export function getNName(){
    return cookie.get(nname)
}

export function setNName(nickname){
    return cookie.set(nname,nickname)
}

export function removeNName(){
    return cookie.remove(nname)
}