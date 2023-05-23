package com.xiaomi_mall.controller;

import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.Comment;
import com.xiaomi_mall.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "评论模块")
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation("前台评论列表展示")
    @GetMapping("/commentList")
    public Result commentList(int productId, Integer pageNum, Integer pageSize) {
        return commentService.commentList(productId, pageNum, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation("前台评论添加")
    @PostMapping("/addcomment")
    public Result addcomment(@RequestBody Comment comment) {
        return commentService.addcomment(comment);
    }

}
