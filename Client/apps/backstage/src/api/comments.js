import axios from '~/axios';

export function getcommentlist(pageNum, pageSize) {
    return axios.get('/getBackCommentList', {
        params: {
            pageNum,
            pageSize
        }
    })
}

export function getcommentlevel(pageNum, pageSize, rate) {
    return axios.get('/getBackCommentList', {
        params: {
            pageNum,
            pageSize,
            rate
        }
    })
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