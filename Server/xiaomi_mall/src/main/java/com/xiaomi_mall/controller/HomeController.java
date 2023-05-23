package com.xiaomi_mall.controller;

import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.service.CategoryService;
import com.xiaomi_mall.service.ProductService;
import com.xiaomi_mall.service.UserService;
import com.xiaomi_mall.vo.CateProductVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "商城首页模块")
public class HomeController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @ApiOperation("获取顶部数据")
    @GetMapping("/getHeaderProducts")
    public Result getHeaderProducts() {
        return userService.getHeaderProducts();
    }


    @ApiOperation("获取左侧数据")
    @GetMapping("/getLeftCategories")
    public Result getLeftCategories() {
        return userService.getLeftCategories();
    }

    @ApiOperation("获取底部数据")
    @GetMapping("/getLowerProducts")
    public Result getLowerProducts() {
        return userService.getLowerProducts();
    }

    @ApiOperation("查询分类接口")
    @GetMapping("/getCategoryList")
    public Result getCategoryList() {
        return categoryService.getCategoryList();
    }

    @ApiOperation("首页搜索商品接口")
    @GetMapping("/getCateProduct")
    public Result getCateProduct(String search) {
        return categoryService.getCateProduct(search);
    }

    @ApiOperation("搜索商品（价格升序）接口")
    @PostMapping("/getCateProductAsc")
    public Result getCateProductAsc(@RequestBody List<CateProductVo> cateProductVos) {
        return productService.getCateProductAsc(cateProductVos);
    }

    @ApiOperation("搜索商品（价格降序）接口")
    @PostMapping("/getCateProductDesc")
    public Result getCateProductDesc(@RequestBody List<CateProductVo> cateProductVos) {
        return productService.getCateProductDesc(cateProductVos);
    }



}
