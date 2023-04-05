package com.xiaomi_mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.Comment;

public interface CommentService extends IService<Comment> {
    Result getBackCommentList(Integer pageNum, Integer pageSize);
}
