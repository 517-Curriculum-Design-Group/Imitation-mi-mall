package com.xiaomi_mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.exception.enity.Sku;
import com.xiaomi_mall.mapper.SkuMapper;
import com.xiaomi_mall.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {
    @Autowired
    private SkuMapper skuMapper;

}
