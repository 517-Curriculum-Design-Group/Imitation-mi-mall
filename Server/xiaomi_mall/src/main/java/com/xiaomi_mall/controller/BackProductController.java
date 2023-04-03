package com.xiaomi_mall.controller;

import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.Category;
import com.xiaomi_mall.service.CategoryService;
import com.xiaomi_mall.service.ProductService;
import com.xiaomi_mall.service.SkuAttributeService;
import com.xiaomi_mall.service.SkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "后台商品管理模块")
public class BackProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SkuAttributeService skuAttributeService;

    /**
     * 后台商品管理
     *
     * @param pageNum     页面num
     * @param pageSize    页面大小
     * @param productName 产品名称
     * @return {@link Result}
     */
    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("商品列表接口")
    @GetMapping("/getProductList")
    public Result getProductList(Integer pageNum, Integer pageSize, String productName) {
        return productService.getProductList(pageNum, pageSize, productName);
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("删除商品接口")
    @DeleteMapping("/deleteProduct")
    public Result deleteProduct(@RequestBody List<Integer> productIds) {
        productService.removeByIds(productIds);
        return Result.okResult();
    }

    /**
     * 后台分类管理
     *
     * @return {@link Result}
     */
    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("分类列表接口")
    @GetMapping("/getBackCategoryList")
    public Result getBackCategoryList() {
        return categoryService.getCategoryList();
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("删除分类接口")
    @DeleteMapping("/deleteCategory/{categoryId}")
    public Result deleteCategory(@PathVariable Integer categoryId) {
        return productService.deleteCategory(categoryId);
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("获取分类详情接口")
    @GetMapping("/getCategoryName/{categoryId}")
    public Result getCategoryName(@PathVariable Integer categoryId) {
        return categoryService.getCategoryName(categoryId);
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("修改分类名称接口")
    @PutMapping("/updateCategoryName")
    public Result updateCategoryName(@RequestBody Category category) {
        return categoryService.updateCategoryName(category);
    }

    /**
     * 后台sku管理
     *
     * @return {@link Result}
     */
    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("规格列表接口")
    @GetMapping("/getSkuList")
    public Result getSkuList(Integer pageNum, Integer pageSize) {
        return skuAttributeService.getSkuList(pageNum, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("删除规格列表接口")
    @DeleteMapping("/deleteSkuList")
    public Result deleteSkuList(@RequestBody List<Integer> attributeId) {
        skuAttributeService.removeByIds(attributeId);
        return Result.okResult();
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("获取规格详情接口")
    @GetMapping("/getSkuDetail/{attributeId}")
    public Result getSkuDetail(@PathVariable Integer attributeId) {
        return skuAttributeService.getSkuDetail(attributeId);
    }
}
