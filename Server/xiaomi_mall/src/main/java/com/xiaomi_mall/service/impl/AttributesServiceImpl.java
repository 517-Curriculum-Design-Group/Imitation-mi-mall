package com.xiaomi_mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.enity.Attributes;
import com.xiaomi_mall.mapper.AddressMapper;
import com.xiaomi_mall.mapper.AttributesMapper;
import com.xiaomi_mall.service.AttributesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttributesServiceImpl extends ServiceImpl<AttributesMapper, Attributes> implements AttributesService {
    @Autowired
    private AttributesMapper attributesMapper;

}
