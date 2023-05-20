import axios from '~/axios';

export function getcommentlist(){
    return axios.get('/getBackCommentList')
}

export function deletecommentlist(commentId){
    return axios.delete('/deleteCommentList', {
        data: commentId
    },

        {
            headers: {
                "Content-Type": "application/json"
            }
        }
    )
}