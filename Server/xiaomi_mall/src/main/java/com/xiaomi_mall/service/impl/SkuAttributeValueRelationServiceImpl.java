package com.xiaomi_mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.enity.SkuAttributeValueRelation;
import com.xiaomi_mall.mapper.SkuAttributeValueRelationMapper;
import com.xiaomi_mall.service.SkuAttributeValueRelationService;
import org.springframework.stereotype.Service;

@Service
public class SkuAttributeValueRelationServiceImpl extends ServiceImpl<SkuAttributeValueRelationMapper,
        SkuAttributeValueRelation> implements SkuAttributeValueRelationService {
}
