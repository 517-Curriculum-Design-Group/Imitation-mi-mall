package com.xiaomi_mall.controller;

import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.mapper.ProductMapper;
import com.xiaomi_mall.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(tags = "商品详情模块")
public class ProductController {
    @Autowired
    private ProductService productService;

    @ApiOperation("查询商品详情")
    @GetMapping("/getProductDetail/{product_id}")
    public Result getProductDetail(@PathVariable Integer product_id) {
        return productService.getProductDetail(product_id);
    }
}
