import { ElNotification,ElMessageBox } from 'element-plus'

//消息
export function toast(message,type = 'success'){
    ElNotification({
        message,
        type,
        duration:3000
    })
}

export function showModal(content = "提示内容",type = "warning",title = ""){
    return ElMessageBox.confirm(
        content,
        title,
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type,
        }
      )
}