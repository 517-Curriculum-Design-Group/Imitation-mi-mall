package com.xiaomi_mall.controller;

import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.User;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "后台用户管理模块")
public class BackUserController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;

    @ApiOperation("后台登录接口")
    @PostMapping("/admin/login")
    public Result backLogin(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())) {
            //提示错误
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.backLogin(user);
    }

    @PreAuthorize("hasAnyAuthority('超级管理员', '普通管理员')")
    @ApiOperation("管理员退出登录接口")
    @PostMapping("/admin/logout")
    public Result backLogout() {
        return loginService.backLogout();
    }

    @PreAuthorize("hasAuthority('超级管理员')")
    @ApiOperation("添加管理员接口")
    @PostMapping("/addadmin")
    public Result addadmin(@RequestBody User user) {
        return userService.addadmin(user);
    }

    @PreAuthorize("hasAnyAuthority('超级管理员', '普通管理员')")
    @ApiOperation("用户列表接口")
    @GetMapping("/getUserList")
    public Result getUserList(Integer pageNum, Integer pageSize, String nickName) {
        return userService.getUserList(pageNum, pageSize, nickName);
    }

    @PreAuthorize("hasAnyAuthority('超级管理员', '普通管理员')")
    @ApiOperation("修改用户状态接口")
    @PutMapping("/updateUserStatus/{userId}")
    public Result updateUserStatus(@PathVariable Long userId) {
        return userService.updateUserStatus(userId);
    }

    @PreAuthorize("hasAnyAuthority('超级管理员', '普通管理员')")
    @ApiOperation("删除用户接口")
    @DeleteMapping("/deleteUser")
    public Result deleteUser(@RequestBody List<Long> userIds) {
        userService.removeByIds(userIds);
        return Result.okResult();
    }

//    @PreAuthorize("hasAnyAuthority('超级管理员', '普通管理员')")
//    @ApiOperation("用户列表的修改用户信息接口")
//    @PutMapping("/updateUserInfo")
//    public Result updateUserInfo(@RequestBody User user) {
//        return userService.updateUserInfo(user);
//    }
}
