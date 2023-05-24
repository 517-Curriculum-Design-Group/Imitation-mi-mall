package com.xiaomi_mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.constants.SystemConstants;
import com.xiaomi_mall.enity.Address;
import com.xiaomi_mall.mapper.AddressMapper;
import com.xiaomi_mall.service.AddressService;
import com.xiaomi_mall.util.SecurityUtils;
import org.apache.ibatis.ognl.IntHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    @Lazy
    private AddressService addressService;

    @Override
    public Result getDefaultAddress(Long userId) {
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUserId, userId)
                .eq(Address::getIsDefault, SystemConstants.DEFAULT_ADDRESS);
        Address address = addressService.getOne(wrapper);
        return Result.okResult(address);
    }

    @Override
    public Result addAddress(Address address) {
        if(address != null) {
            address.setUserId(SecurityUtils.getUserId());
            save(address);
        }
        return Result.okResult("添加地址成功");
    }

    @Override
    public Result setDefaultAddress(Address address) {
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUserId, address.getUserId());
        //将该用户的所有地址的IsDefault先设置为零
        List<Address> addressList = addressMapper.selectList(wrapper);
        for (Address address1: addressList)
        {
            address1.setIsDefault(0);
        }
        address.setIsDefault(SystemConstants.DEFAULT_ADDRESS);
        addressService.updateBatchById(addressList);
        return Result.okResult("设置成功");
    }

    @Override
    public Result getAllAddress(Long userId) {
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUserId, userId);
        List<Address> addressList = addressMapper.selectList(wrapper);
        return Result.okResult(addressList);

    }

    @Override
    public Result updateAddress(Address address) {
        updateById(address);
        return Result.okResult("修改成功 ");
    }

    @Override
    public Result hasDefaultAddress() {
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUserId, userId);
        List<Address> addressList = addressMapper.selectList(wrapper);
        Map<String, Boolean> res = new LinkedHashMap<>();
        for (Address address : addressList) {
            if(address.getIsDefault() == 1)
            {
                res.put("hasDefaultAddress", true);
                return Result.okResult(res);
            }
        }
        res.put("hasDefaultAddress", false);
        return Result.okResult(res);
    }
}
