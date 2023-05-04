import { ElNotification } from 'element-plus'

//消息
export function toast(message,type = 'success'){
    ElNotification({
        message,
        type,
        duration:3000
    })
}