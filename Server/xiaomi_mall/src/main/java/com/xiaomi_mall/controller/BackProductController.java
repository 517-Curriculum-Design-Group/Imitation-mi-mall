package com.xiaomi_mall.controller;

import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.dto.AttributeValueCommit;
import com.xiaomi_mall.dto.SkuAttribute_ValueDto;
import com.xiaomi_mall.enity.Category;
import com.xiaomi_mall.enity.Product;
import com.xiaomi_mall.enity.SkuAttributeValue;
import com.xiaomi_mall.service.CategoryService;
import com.xiaomi_mall.service.ProductService;
import com.xiaomi_mall.service.SkuAttributeService;
import com.xiaomi_mall.service.SkuAttributeValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private SkuAttributeValueService skuAttributeValueService;

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
    @ApiOperation("新增商品接口")
    @PostMapping("/addNewProduct")
    public Result addNewProduct(@RequestBody Map<String, Object> map) {
        return productService.addNewProduct(map);
    }


    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("查看商品SKU接口")
    @GetMapping("/getProductSku/{productId}")
    public Result getProductSku(@PathVariable Integer productId) {
        return productService.getProductSku(productId);
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("修改商品SKU接口")
    @PostMapping("/modifyProductSku")
    public Result modifyProductSku(@RequestBody Map<String, Object> map) {
        return productService.modifyProductSku(map);
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
    @ApiOperation("删除Attribute接口")
    @DeleteMapping("/deleteAttribute")
    public Result deleteAttribute(@RequestParam Integer attributeId) {
        skuAttributeService.removeById(attributeId);
        List<Integer> attributeValueId = new ArrayList<>();
        for (SkuAttributeValue value: skuAttributeValueService.list()) {
            if (value.getAttributeId() == attributeId)
                attributeValueId.add(value.getValueId());
        }
        skuAttributeValueService.removeByIds(attributeValueId);
        return Result.okResult();
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("删除AttributeValue列表接口")
    @DeleteMapping("/deleteAttributeValueList")
    public Result deleteSkuList(@RequestBody List<Integer> attributeValueId) {
        skuAttributeValueService.removeByIds(attributeValueId);
        return Result.okResult();
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("获取规格详情接口")
    @GetMapping("/getSkuDetail/{attributeId}")
    public Result getSkuDetail(@PathVariable Integer attributeId) {
        return skuAttributeService.getSkuDetail(attributeId);
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("创建Attribute接口")
    @PostMapping("/createNewAttribute")
    public Result createNewAttribute(@RequestBody String attributeName) {
        return skuAttributeService.createNewAttribute(attributeName);
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("创建AttributeValue接口")
    @PostMapping("/createNewAttributeValue")
    public Result createNewAttribute(@RequestBody AttributeValueCommit attributeValueCommit) {
        return skuAttributeValueService.createNewAttributeValue(attributeValueCommit);
    }


    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("修改规格接口")
    @PostMapping("/modifySkuAttribute")
    public Result modifySkuAttribute(@RequestBody SkuAttribute_ValueDto skuAttribute_valueDto)
    {
        return skuAttributeService.modifySkuAttribute(skuAttribute_valueDto);
    }

//    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
//    @ApiOperation("删除商品规格接口")
//    @PostMapping("/deleteSkuAttribute")
//    public Result deleteSkuAttribute(@RequestBody Integer sku_id)
//    {
//        return skuAttributeService.deleteSkuAttribute(sku_id);
//    }
}
