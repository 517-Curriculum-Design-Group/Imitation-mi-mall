import { useCookies } from '@vueuse/integrations/useCookies'

const tokenkey = "admin-token"
const cookie = useCookies()

export function getToken(){
    return cookie.get(tokenkey)
}

export function setToken(){
    return cookie.set(tokenkey)
}

export function removeToken(){
    return cookie.remove(tokenkey)
}