package com.xiaomi_mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.dto.SkuAttribute_ValueDto;
import com.xiaomi_mall.enity.SkuAttribute;

public interface SkuAttributeService extends IService<SkuAttribute> {

    Result getSkuList(Integer pageNum, Integer pageSize);

    Result getSkuDetail(Integer attributeId);


    Result addSkuAttribute(SkuAttribute_ValueDto skuAttribute_valueDto);

    Result modifySkuAttribute(SkuAttribute_ValueDto skuAttribute_valueDto);

    Result createNewAttribute(String attributeName);

//    Result deleteSkuAttribute(Integer sku_id);

}


