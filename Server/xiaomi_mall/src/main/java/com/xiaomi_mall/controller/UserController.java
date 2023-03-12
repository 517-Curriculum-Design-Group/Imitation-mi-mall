package com.xiaomi_mall.controller;

import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.exception.enity.User;
import com.xiaomi_mall.enums.AppHttpCodeEnum;
import com.xiaomi_mall.exception.SystemException;
import com.xiaomi_mall.service.LoginService;
import com.xiaomi_mall.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "用户个人模块")
public class UserController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;

    @ApiOperation("登录接口")
    @PostMapping ("/user/login")
    public Result login(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            //提示错误
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @PreAuthorize("hasAuthority('普通用户')")
    @ApiOperation("退出登录接口")
    @PostMapping ("/user/logout")
    public Result logout() {
        return loginService.logout();
    }

    @ApiOperation("普通用户注册接口")
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        return userService.register(user);
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
