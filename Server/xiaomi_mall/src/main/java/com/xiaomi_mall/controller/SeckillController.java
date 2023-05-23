package com.xiaomi_mall.controller;

import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.Product;
import com.xiaomi_mall.enity.Seckill;
import com.xiaomi_mall.enity.Sku;
import com.xiaomi_mall.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "商品秒杀模块")
public class SeckillController {

    @Resource
    private SeckillService seckillService;

    @PreAuthorize("hasAuthority('普通用户')")
    @ApiOperation("商品秒杀接口")
    @PostMapping("/seckill")
    public Result seckill(@RequestParam Integer productId){
        return seckillService.seckill(productId);
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("后台添加商品秒杀接口")
    @PostMapping("/addSeckill")
    public Result addSeckill(@RequestBody Seckill seckill){
        return seckillService.addSeckill(seckill);
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("后台获取所有商品秒杀接口")
    @GetMapping("/getSeckillList")
    public Result getSeckillList(){
        return seckillService.getSeckillList();
    }

    @PreAuthorize("hasAuthority('普通用户')")
    @ApiOperation("前台获取所有商品秒杀接口")
    @GetMapping("/getFollSecList")
    public Result getFollSecList(){
        return seckillService.getFollSecList();
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("后台添加商品秒杀时获取所有商品接口")
    @GetMapping("/getSeckillProd")
    public Result getSeckillProd(){
        return seckillService.getSeckillProd();
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("后台添加商品秒杀时选择商品接口")
    @GetMapping("/getSelectProd")
    public Result getSelectProd(@RequestBody Product product){
        return seckillService.getSelectProd(product);
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("后台添加商品秒杀时获取该商品下所有sku接口")
    @GetMapping("/getSeckillSku")
    public Result getSeckillSku(@PathVariable int productId){
        return seckillService.getSeckillSku(productId);
    }

    @PreAuthorize("hasAnyAuthority('普通管理员', '超级管理员')")
    @ApiOperation("后台添加商品秒杀时选取选择该商品下某个sku接口")
    @GetMapping("/getSelectSku")
    public Result getSelectSku(@RequestBody Sku sku){
        return seckillService.getSelectSku(sku);
    }
}
