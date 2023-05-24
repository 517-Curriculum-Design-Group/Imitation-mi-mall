package com.xiaomi_mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.Address;

public interface AddressService extends IService<Address> {

    Result getDefaultAddress(Long userId);

    Result addAddress(Address address);

    Result setDefaultAddress(Address address);

    Result getAllAddress(Long userId);

    Result updateAddress(Address address);

    Result hasDefaultAddress();
}


