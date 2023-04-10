package com.xiaomi_mall.controller;

import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.service.OrderService;
import com.xiaomi_mall.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "后台主页")
public class BackHomeController
{
    @Autowired
    UserService userService;


    @PreAuthorize("hasAnyAuthority('超级管理员', '普通管理员')")
    @ApiOperation("用户列表接口")
    @GetMapping("/getHome")
    public Result getHome()
    {
        return userService.getHome();
    }



}
