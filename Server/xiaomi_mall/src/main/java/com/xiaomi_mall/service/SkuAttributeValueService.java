package com.xiaomi_mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.dto.AttributeValueCommit;
import com.xiaomi_mall.enity.SkuAttributeValue;

public interface SkuAttributeValueService extends IService<SkuAttributeValue> {
    Result createNewAttributeValue(AttributeValueCommit attributeValueCommit);
}
