package com.xiaomi_mall.controller;


import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.service.CommentService;
import com.xiaomi_mall.service.OrderService;
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
    //status -1全部 1-已支付，2-已发货，3-已完成，4-已取消
    public Result getBackOrderList(Integer pageNum, Integer pageSize, Integer status)
    {
        return orderService.getBackOrderList(pageNum, pageSize, status);
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("订单发货")
    @PostMapping("/orderDelivery")
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
    public Result getBackCommentList(Integer pageNum, Integer pageSize, Integer rate)
    {
        return commentService.getBackCommentList(pageNum, pageSize, rate);
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("删除评论列表接口")
    @DeleteMapping("/deleteCommentList")
    public Result deleteCommentList(@RequestBody List<Integer> commentId) {
        commentService.removeByIds(commentId);
        return Result.okResult();
    }




}
