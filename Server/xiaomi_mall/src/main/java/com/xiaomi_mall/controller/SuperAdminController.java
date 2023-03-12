package com.xiaomi_mall.controller;

import com.xiaomi_mall.config.Result;
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
@Api(tags = "后台中超级管理员独有模块")
public class SuperAdminController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('超级管理员')")
    @ApiOperation("普通管理员列表接口")
    @GetMapping("/getAdminList")
    public Result getAdminList(Integer pageNum, Integer pageSize, String nickName) {
        return userService.getAdminList(pageNum, pageSize, nickName);
    }
}
