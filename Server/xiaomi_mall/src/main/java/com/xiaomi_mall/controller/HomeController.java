package com.xiaomi_mall.controller;

import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.service.CategoryService;
import com.xiaomi_mall.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "商城首页模块")
public class HomeController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @ApiOperation("获取主页信息")
    @GetMapping("/getUserHome")
    public Result getUserHome() {
        return userService.getUserHome();
    }
    @ApiOperation("查询分类接口")
    @GetMapping("/getCategoryList")
    public Result getCategoryList() {
        return categoryService.getCategoryList();
    }

    @ApiOperation("查询分类对应的商品接口")
    @GetMapping("/getCateProduct")
    public Result getCateProduct(Integer categoryId) {
        return categoryService.getCateProduct(categoryId);
    }



}
