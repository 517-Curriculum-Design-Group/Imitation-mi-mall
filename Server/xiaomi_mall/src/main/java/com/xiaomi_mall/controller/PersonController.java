package com.xiaomi_mall.controller;

import com.xiaomi_mall.config.MinioConfig;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.dto.UpdatePasswordDto;
import com.xiaomi_mall.enity.User;
import com.xiaomi_mall.enums.AppHttpCodeEnum;
import com.xiaomi_mall.service.UserService;
import com.xiaomi_mall.util.MinioUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@Api(tags = "个人信息模块")
public class PersonController {

    @Autowired
    private UserService userService;
    @Autowired
    private MinioUtil minioUtil;
    @Autowired
    private MinioConfig prop;

    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation(" 查看个人信息")
    @GetMapping("/getPersonInfo")
    public Result getPersonInfo() {
        return userService.getPersonInfo();
    }


    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation(" 修改个人信息")
    @PutMapping("/updatePersonInfo")
    public Result updatePersonInfo(@RequestBody User user) {
        return userService.updatePersonInfo(user);
    }


    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation(" 修改密码")
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        return userService.updatePassword(updatePasswordDto);
    }


    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation(" 上传头像")
    @PostMapping("/upload")
    public Result uploadImg(@RequestBody MultipartFile file) {
        String objectName = minioUtil.upload(file);
        if (null != objectName) {
            String avatar = prop.getEndpoint() + "/" + prop.getBucketName() + "/" + objectName;
            User user = userService.addAdvater();
            user.setAvatar(avatar);
            userService.updateById(user);
            System.out.println(user);
            return Result.okResult(prop.getEndpoint() + "/" + prop.getBucketName() + "/" + objectName);
        }
        return Result.errorResult(AppHttpCodeEnum.UPLOAD_ERROR);
    }
}
