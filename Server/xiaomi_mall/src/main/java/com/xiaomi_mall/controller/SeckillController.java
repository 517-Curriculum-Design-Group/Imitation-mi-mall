package com.xiaomi_mall.controller;

import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "商品秒杀模块")
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    @PreAuthorize("hasAuthority('普通用户')")
    @ApiOperation("商品秒杀接口")
    @PostMapping("/seckill")
    public Result seckill(@RequestParam Integer productId){
        return seckillService.seckill(productId);
    }
}
