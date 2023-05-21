package com.xiaomi_mall.controller;

import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.dto.OrderCommit;
import com.xiaomi_mall.dto.SearchProductDto;
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
import java.util.Map;

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
    public Result getProductDetail(HttpServletRequest request, @PathVariable Integer product_id) {
        return productService.getProductDetail(request, product_id);
    }

    @ApiOperation("查询商品价格")
    @PostMapping("/getProductPrice")
    public Result getProductPrice(@RequestBody Map<String, Object> map) {
        return productService.getProductPrice(map);
    }

    //TODO:
    @ApiOperation("搜索商品")
    @PostMapping("/searchProduct")
    public Result searchProduct(SearchProductDto searchProductDto) {
        //return productService.searchProduct(searchProductDto);
        return Result.okResult("没写完");
    }

    /**
     * 购物车相关
     *
     * @return {@link Result}
     */
    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation("添加单个Sku到购物车")
    @PostMapping("/addSkuToCart")
    public Result addSkuToCart(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        return productService.addSkuToCart(request, map);
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
    public Result deleteCartProduct(HttpServletRequest request, int cartId) {
        cartService.removeById(cartId);
        return cartService.getCartList(request);
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

    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation("查看订单详情")
    @GetMapping("/getUserOrderDetail/{orderId}")
    public Result getUserOrderDetail(HttpServletRequest request, @PathVariable Integer orderId) {
        return orderService.getUserOrderDetail(request, orderId);
    }

    /**
     * 喜欢相关
     *
     * @return {@link Result}
     */
    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation("添加商品到喜欢")
    @PostMapping("/addProductToFavorite")
    public Result addProductToFavorite(HttpServletRequest request, @RequestBody Integer product_id) {
        return productService.addProductToFavorite(request, product_id);
    }

    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation("删除喜欢")
    @DeleteMapping("/deleteProductToFavorite")
    public Result deleteProductToFavorite(HttpServletRequest request, @RequestBody Integer product_id) {
        return productService.deleteProductToFavorite(request, product_id);
    }

    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation("查看喜欢列表")
    @GetMapping("/getFavoriteList")
    public Result getFavoriteList(HttpServletRequest request) {
        return productService.getFavoriteList(request);
    }



}
