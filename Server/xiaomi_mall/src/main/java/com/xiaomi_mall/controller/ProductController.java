package com.xiaomi_mall.controller;

import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.dto.OrderCommit;
import com.xiaomi_mall.service.CartService;
import com.xiaomi_mall.service.OrderService;
import com.xiaomi_mall.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@Api(tags = "商品模块")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    @ApiOperation("查询商品详情")
    @GetMapping("/getProductDetail/{product_id}")
    public Result getProductDetail(@PathVariable Integer product_id) {
        return productService.getProductDetail(product_id);
    }

    /**
     * 购物车相关
     *
     * @return {@link Result}
     */
    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation("添加单个Sku到购物车")
    @PostMapping("/addSkuToCart/{sku_id}")
    public Result addSkuToCart(HttpServletRequest request, @PathVariable Integer sku_id) {
        return productService.addSkuToCart(request, sku_id);
    }

    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation("查看购物车列表")
    @GetMapping("/getCartList")
    public Result getCartList(HttpServletRequest request) {
        return cartService.getCartList(request);
    }

    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation("删除购物车中商品")
    @DeleteMapping("/deleteCartProduct")
    public Result deleteCartProduct(@RequestBody List<Integer> cartIds) {
        cartService.removeByIds(cartIds);
        return Result.okResult();
    }


    /**
     * 订单相关
     *
     * @return {@link Result}
     */

    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation("再次确认订单（选择地址在此完成）")
    @PostMapping("/checkOrder")
    public Result checkOrder(HttpServletRequest request) {
        return orderService.checkOrder(request);
    }

    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation("生成订单")
    @PostMapping("/generateOrder")
    public Result generateOrder(HttpServletRequest request, @RequestBody List<OrderCommit> skuIds, Integer addressId) {
        return orderService.generateOrder(request, skuIds, addressId);
    }

    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation("查看订单列表")
    @GetMapping("/getOrderList")
    public Result getOrderList(HttpServletRequest request) {
        return orderService.getOrderList(request);
    }



}
