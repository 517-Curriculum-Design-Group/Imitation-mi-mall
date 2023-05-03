package com.xiaomi_mall.controller;


import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.mapper.OrderMapper;
import com.xiaomi_mall.service.CommentService;
import com.xiaomi_mall.service.OrderService;
import com.xiaomi_mall.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "后台订单管理模块")
public class BackOrderController
{
    @Autowired
    private OrderService orderService;

    @Autowired
    private CommentService commentService;

    /**
     * 后台订单管理
     *
     * @return {@link Result}
     */
    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("订单列表接口")
    @GetMapping("/getBackOrderList")
    public Result getBackOrderList(Integer pageNum, Integer pageSize)
    {
        return orderService.getBackOrderList(pageNum, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("订单发货")
    @GetMapping("/orderDelivery")
    public Result orderDelivery(@RequestBody List<Integer> orderId)
    {
        return orderService.orderDelivery(orderId);
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("获取订单详情接口")
    @GetMapping("/getOrderDetail/{orderId}")
    public Result getOrderDetail(@PathVariable Integer orderId)
    {
        return orderService.getOrderDetail(orderId);
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("删除订单列表接口")
    @DeleteMapping("/deleteOrderList")
    public Result deleteOrderList(@RequestBody List<Integer> orderId) {
        orderService.removeByIds(orderId);
        return Result.okResult();
    }

    /**
     * 后台评论管理
     *
     * @return {@link Result}
     */
    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("评论列表接口")
    @GetMapping("/getBackCommentList")
    public Result getBackCommentList(Integer pageNum, Integer pageSize)
    {
        return commentService.getBackCommentList(pageNum, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("删除评论列表接口")
    @DeleteMapping("/deleteCommentList")
    public Result deleteCommentList(@RequestBody List<Integer> commentId) {
        commentService.removeByIds(commentId);
        return Result.okResult();
    }




}
