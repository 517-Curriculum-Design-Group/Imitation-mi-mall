package com.xiaomi_mall.controller;

import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.User;
import com.xiaomi_mall.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "登录、注册、退出登录接口")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @ApiOperation("登录接口")
    @PostMapping ("/user/login")
    public Result login(@RequestBody User user) {
        return loginService.login(user);
    }

    @ApiOperation("退出登录接口")
    @PostMapping ("/user/logout")
    public Result logout() {
        return loginService.logout();
    }

    @ApiOperation("测试接口1")
    @PreAuthorize("hasAuthority('普通管理员')")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @ApiOperation("测试接口2")
    @PreAuthorize("hasAuthority('普通用户')")
    @GetMapping("/hello2")
    public String hello2() {
        return "hello2";
    }
}
