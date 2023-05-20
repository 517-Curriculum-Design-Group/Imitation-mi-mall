package com.xiaomi_mall.controller;

import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.Address;
import com.xiaomi_mall.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "地址模块")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation("获取默认的地址接口")
    @GetMapping("/getDefaultAddress")
    public Result getDefaultAddress(@RequestParam Long userId) {
        return addressService.getDefaultAddress(userId);
    }

    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation("获取该用户的所有地址接口")
    @GetMapping("/getAllAddress")
    public Result getAllAddress(@RequestParam Long userId) {
        return addressService.getAllAddress(userId);
    }

    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation("添加地址接口")
    @PostMapping("/addAddress")
    public Result addAddress(@RequestBody Address address) {
        return addressService.addAddress(address);
    }

    @PreAuthorize("hasAnyAuthority('普通用户')")
    @ApiOperation("设置默认地址接口")
    @PutMapping("/setDefaultAddress")
    public Result setDefaultAddress(@RequestBody Address address) {
        return addressService.setDefaultAddress(address);
    }
}
